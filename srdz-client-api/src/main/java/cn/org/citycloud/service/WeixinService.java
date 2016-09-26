package cn.org.citycloud.service;

import cn.org.citycloud.bean.DistPreOrder;
import cn.org.citycloud.bean.OrderGoods;
import cn.org.citycloud.bean.ShopOrderGoods;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.*;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.*;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/20 9:36
 */
@Service
public class WeixinService {
    private static Logger logger = Logger.getLogger(WeixinService.class);

    @Autowired
    private WechatSalesMemberDao wechatSalesMemberDao;

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private ServiceCenterDao serviceCenterDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private OrderGoodDao orderGoodDao;

    @Autowired
    private SalesOrderInfoDao orderInfoDao;

    @Autowired
    private PayInfoDao payInfoDao;

    @Autowired
    private RegionInfoDao reginDao;

    @Autowired
    private SalesMemberDao salesMemberDao;

    @Autowired
    private DistShopGoodDao distShopGoodDao;

    @Autowired
    private FinAccDao finAccDao;

    /**
     * 1)生成分销预订单信息和订单商品信息
     *
     * @param preOrder
     * @param memberId
     * @param openId
     * @return
     * @throws BusinessErrorException
     */
    @Transactional(rollbackFor = Exception.class)
    public Object submitOrder(DistPreOrder preOrder, int memberId, String openId) throws BusinessErrorException {
        // 判断是否微信环境, 5.0 之后的支持微信支付
        // if (!WeChatUtil.isWeiXin(request))
        // {
        // throw new BusinessErrorException(ErrorCodes.INVALID_PARAMETER,
        // "微信5.0以下不支持微信支付！");
        // }

        // 生成预订单
        // 订单商品
        List<ShopOrderGoods> orderGoodsList = preOrder.getOrderGoodsList();

        // 验证商品数据
        if (orderGoodsList.size() == 0) {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "此订单没有商品数据！");
        }

        // 会员信息
        WechatSalesMember member = wechatSalesMemberDao.findByOpenId(openId);
        if (Constants.MEMBER_STATE_CLOSED == member.getStatus()) {
            throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "此会员已经被禁用！");
        }

        // 生成时间
        Date now = new Date();

        // 验证收货地址
        // 地址码
        long countRegin = reginDao.countByRegionCode(preOrder.getRegionCode());

        // 如果此区县码 不在系统库中，追加此地址码
        if (countRegin == 0) {

            RegionInfo region = new RegionInfo();
            region.setRegionCode(preOrder.getRegionCode());
            region.setRegionName(preOrder.getRegionAreaName());
            region.setRegionLevel(3);

            reginDao.save(region);
        }

        String reginCode = String.valueOf(preOrder.getRegionCode());
        int reginProv = Integer.parseInt(reginCode.substring(0, 2) + "0000");
        int regionCity = Integer.parseInt(reginCode.substring(0, 4) + "00");

        // 预先生成支付单信息 支持 合并支付
        PayInfo payInfo = new PayInfo();
        payInfo.setPayStatus(0);
        payInfo.setPayStyle("3");
        payInfo.setCreateTime(now);
        payInfo.setUpdateDate(now);

        PayInfo newPayInfo = payInfoDao.save(payInfo);

        // 验证商品 是否拆分商品订单
        for (ShopOrderGoods shopOrderGoods : orderGoodsList) {

            int supplierId = shopOrderGoods.getSupplierId();
            // 同一店铺的商品
            List<OrderGoods> orderGoods = shopOrderGoods.getOrderGoods();

            if (orderGoods.size() == 0) {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "此订单没有商品数据！");
            }
            // 供应商信息
            Supplier supplier = supplierDao.findOne(supplierId);
            if (Constants.SUPPLIER_STATUS_VERIFIED != supplier.getStatus()) {
                throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, supplier.getSupplierName() + "暂时不能下单！");
            }

            // 供应商 平台分成
            BigDecimal platformRates = supplier.getSupplierLevel().getPlatformRates();

            // 服务中心信息分成
            ServiceCenter serviceCenter = serviceCenterDao.findOne(supplier.getServiceCenterId());
            BigDecimal serviceCenterRates = serviceCenter.getServiceCenterLevel().getServiceCenterRates();

            // 新建订单
            Order order = new Order();

            // 订单类型
            order.setOrderType(Constants.ORDER_TYPE_DIST);

            // 会员信息
            order.setMemberId(memberId);
            order.setMemberName(member.getMemberTruename());

            // 省
            order.setRegionProv(reginProv);
            // 市
            order.setRegionCity(regionCity);

            order.setContactName(preOrder.getContactsName());
            order.setContactPhone(preOrder.getContactsPhone());
            order.setContactAddress(preOrder.getContactsAddress());
            order.setPostCode(order.getPostCode());
            order.setRegionProvName(preOrder.getRegionProvName());
            order.setRegionCityName(preOrder.getRegionCityName());
            order.setRegionAreaName(preOrder.getRegionAreaName());

            order.setOrderTime(now);
            order.setOrderStatus(Constants.ORDER_STATUS_DEFAULT);
            order.setOrderType(Constants.ORDER_TYPE_NORMAL);

            // 订单金额
            BigDecimal orderPrice = BigDecimal.ZERO;
            BigDecimal payPrice = BigDecimal.ZERO;
            BigDecimal initPrice = BigDecimal.ZERO;

            // 供应商营收
            BigDecimal supplierAmount = BigDecimal.ZERO;

            // 分销商营收
            BigDecimal saleAmount = BigDecimal.ZERO;

            order.setOrderPrice(orderPrice);
            order.setPayPrice(payPrice);
            order.setInitPrice(initPrice);
            order.setSupplierAmount(supplierAmount);

            // 支付单号
            order.setPayId(newPayInfo.getPayId());

            Order newOrder = orderDao.save(order);

            int orderId = newOrder.getOrderId();

            Order generateOrder = orderDao.findOne(orderId);

            // 订单商品信息

            // 订单商品
            List<OrderGood> goodsEntitys = new ArrayList<OrderGood>();
            for (OrderGoods goods : orderGoods) {
                // 获取商品信息
                Good goodInfo = goodsDao.findOne(goods.getGoodsId());

                // 分销店铺商品信息
                DistShopGood distShopGood = distShopGoodDao.findOne(goods.getGoodsId());

                if (goodInfo == null) {
                    throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, goods.getGoodsId() + "商品信息不存在。");
                }
                // 如果商品已经下架 禁止购买和生成订单
                if (Constants.GOODS_STATE_NORMAL != goodInfo.getGoodsStatus()) {
                    throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, goodInfo.getGoodsName() + "已经下架或者禁售。");
                }

                // 自动下架时间
                Date offlineTime = goodInfo.getAutoDownTime();

                if (offlineTime.getTime() < now.getTime()) {
                    throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, goodInfo.getGoodsName() + "已经下架。");
                }

                // 商品已经没有库存了
                if (goodInfo.getInitSaleCount() <= 0) {
                    throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, goodInfo.getGoodsName() + "已经售罄。");
                }

                // 分销商品金额
                BigDecimal goodsTotal = goodInfo.getSalePrice().multiply(new BigDecimal(goods.getGoodsNum()));

                // 分销商品金额
                BigDecimal saleAmountTotal = distShopGood.getShopGoodsPrice()
                        .multiply(new BigDecimal(goods.getGoodsNum()));

                // 累计商品金额
                orderPrice = orderPrice.add(goodsTotal);

                payPrice = saleAmount.add(saleAmountTotal);

                // 生成订单商品实体
                OrderGood entity = new OrderGood();
                entity.setOrderId(orderId);
                entity.setGoodsId(goods.getGoodsId());
                entity.setGoodsName(goodInfo.getGoodsName());
                entity.setGoodsPrice(goodInfo.getSalePrice());
                entity.setGoodsNum(goods.getGoodsNum());
                entity.setGoodsImage(goodInfo.getGoodsImage());
                entity.setStandard(goodInfo.getStandard());
                entity.setGoodsPrice(goodsTotal);
                entity.setGoodsPayPrice(goodsTotal);

                goodsEntitys.add(entity);

                goodInfo.setUpdateTime(now);

                goodsDao.save(goodInfo);

            }

            generateOrder.setOrderPrice(payPrice);

            generateOrder.setPayPrice(payPrice);

            // 平台分成金额
            BigDecimal commisAmount = BigDecimal.ZERO;

            // 服务中心分成金额
            BigDecimal serviceCenterAmount = BigDecimal.ZERO;

            // 计算佣金 无佣金
            if (BigDecimal.ZERO.compareTo(platformRates) == 0) {
                supplierAmount = orderPrice;
            } else {
                // 有佣金
                // 平台分成金额 四舍五入 保留2位小数
                commisAmount = orderPrice.multiply(platformRates).divide(new BigDecimal(100)).setScale(2,
                        BigDecimal.ROUND_HALF_UP);

                // 供应商营收
                supplierAmount = orderPrice.subtract(commisAmount);

                // 服务中心分成金额
                if (BigDecimal.ZERO.compareTo(platformRates) != 0) {
                    serviceCenterAmount = commisAmount.multiply(serviceCenterRates).divide(new BigDecimal(100))
                            .setScale(2, BigDecimal.ROUND_HALF_UP);

                    commisAmount = commisAmount.subtract(serviceCenterAmount);
                }

                // 分销商营收
                saleAmount = payPrice.subtract(orderPrice);

                // 添加 分销订单信息
                SalesOrderInfo orderInfo = new SalesOrderInfo();
                orderInfo.setOrderId(orderId);
                orderInfo.setMemberId(memberId);

                SalesMember salesMember = salesMemberDao.findOne(memberId);
                orderInfo.setSalesMemberName(salesMember.getSalesMemberName());

                // 分成
                orderInfo.setPlatformRates(platformRates);
                orderInfo.setPlatformAmount(commisAmount);
                orderInfo.setServiceCenterRates(serviceCenterRates);
                orderInfo.setServiceCenterAmount(serviceCenterAmount);
                orderInfo.setSaleAmount(saleAmount);

                orderInfoDao.save(orderInfo);
            }
            // 计算物流费用 start

            // 计算物流费用 end

            // 设置供应商营收
            generateOrder.setSupplierAmount(supplierAmount);

            // 批量插入 订单商品
            orderGoodDao.save(goodsEntitys);

            // 更新订单金额
            orderDao.save(generateOrder);

        }

        return payInfo;
    }

    /**
     * 支付结果通知处理(调试之用)
     *
     * @param payId
     */
    @Transactional(rollbackFor = Exception.class)
    public void testHandleWSxpayNotify(int payId) {
        // 验证订单 是否已经支付并处理
        // 商户支付单号
        PayInfo payInfo = payInfoDao.findOne(payId);

        // 用户支付单号无效
        if (payInfo == null) {
            logger.error("用户支付单号" + payId + "无效");
            return;
        }

        if (Constants.ORDER_STATUS_DEFAULT != payInfo.getPayStatus()) {
            return;
        }

        Date now = new Date();
        // 支付方式
        String payStyle = "3";

        // 查询支付单号 关联的订单
        List<Order> orderList = orderDao.findByPayId(payId);

        // 订单支付总金额
        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        for (Order order : orderList) {

            order.setOrderStatus(Constants.ORDER_STATUS_PAYED);
            order.setPayCode(payStyle);
            order.setPayTime(now);

            totalOrderPrice = totalOrderPrice.add(order.getOrderPrice());

            // 订单分成信息
            SalesOrderInfo orderInfo = orderInfoDao.findOne(order.getOrderId());

            // 财务信息入账
            // 平台
            FinAcc srdzFin = finAccDao.findByAccountNoAndAccountType(666, Constants.ACC_TYPE_SRDZ);
            if (srdzFin != null) {
                srdzFin.setAccountIncome(srdzFin.getAccountIncome().add(orderInfo.getPlatformAmount()));
                srdzFin.setAccountBal(srdzFin.getAccountBal().add(orderInfo.getPlatformAmount()));

                finAccDao.save(srdzFin);
            } else {
                FinAcc srdzNewFin = new FinAcc();
                srdzNewFin.setAccountNo(666);
                srdzNewFin.setAccountIncome(orderInfo.getPlatformAmount());
                srdzNewFin.setAccountBal(orderInfo.getPlatformAmount());
                srdzNewFin.setAccountType(Constants.ACC_TYPE_SRDZ);

                finAccDao.save(srdzNewFin);
            }

            // 供应商
            FinAcc supplierFin = finAccDao.findByAccountNoAndAccountType(order.getSupplierId(),
                    Constants.ACC_TYPE_SUPPLIER);
            if (supplierFin != null) {
                supplierFin.setAccountIncome(supplierFin.getAccountIncome().add(order.getSupplierAmount()));
                supplierFin.setAccountBal(supplierFin.getAccountBal().add(order.getSupplierAmount()));

                finAccDao.save(supplierFin);
            } else {
                FinAcc supplierNewFin = new FinAcc();
                supplierNewFin.setAccountNo(order.getSupplierId());
                supplierNewFin.setAccountIncome(orderInfo.getPlatformAmount());
                supplierNewFin.setAccountBal(orderInfo.getPlatformAmount());
                supplierNewFin.setAccountType(Constants.ACC_TYPE_SUPPLIER);

                finAccDao.save(supplierNewFin);
            }

            // 服务中心
            // 供应商
            Supplier supplier = supplierDao.findOne(order.getSupplierId());

            FinAcc ssFin = finAccDao.findByAccountNoAndAccountType(supplier.getServiceCenterId(),
                    Constants.ACC_TYPE_SUPPLIER);
            if (ssFin != null) {
                ssFin.setAccountIncome(ssFin.getAccountIncome().add(orderInfo.getServiceCenterAmount()));
                ssFin.setAccountBal(ssFin.getAccountBal().add(orderInfo.getServiceCenterAmount()));

                finAccDao.save(ssFin);
            } else {
                FinAcc ssNewFin = new FinAcc();
                ssNewFin.setAccountNo(supplier.getServiceCenterId());
                ssNewFin.setAccountIncome(orderInfo.getServiceCenterAmount());
                ssNewFin.setAccountBal(orderInfo.getServiceCenterAmount());
                ssNewFin.setAccountType(Constants.ACC_TYPE_SS);

                finAccDao.save(ssNewFin);
            }

            // 分销商
            FinAcc distFin = finAccDao.findByAccountNoAndAccountType(orderInfo.getMemberId(),
                    Constants.ACC_TYPE_SUPPLIER);
            if (distFin != null) {
                distFin.setAccountIncome(distFin.getAccountIncome().add(orderInfo.getSaleAmount()));
                distFin.setAccountBal(distFin.getAccountBal().add(orderInfo.getSaleAmount()));

                finAccDao.save(supplierFin);
            } else {
                FinAcc distNewFin = new FinAcc();
                distNewFin.setAccountNo(orderInfo.getMemberId());
                distNewFin.setAccountIncome(orderInfo.getSaleAmount());
                distNewFin.setAccountBal(orderInfo.getSaleAmount());
                distNewFin.setAccountType(Constants.ACC_TYPE_DIST);

                finAccDao.save(distNewFin);
            }
        }

        orderDao.save(orderList);

        // 支付方式
        payInfo.setPayStyle(payStyle);
        payInfo.setPayStatus(Constants.PAY_STATE_PAYED);

        payInfo.setPayTime(now);
        payInfo.setPayMoney(totalOrderPrice);

        payInfoDao.save(payInfo);
    }
}

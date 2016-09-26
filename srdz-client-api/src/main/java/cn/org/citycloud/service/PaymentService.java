package cn.org.citycloud.service;

import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.*;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/20 9:18
 */
@Service
public class PaymentService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private PayInfoDao payInfoDao;

    @Autowired
    private FinAccDao finAccDao;

    @Autowired
    private GrowthHistoryDao growthHistoryDao;
    
    @Autowired
    private MemberCouponDao memberCouponDao;

    /**
     * 支付订单
     *
     * @param orderId
     * @param memberId
     * @param memberPhone
     * @throws BusinessErrorException
     */
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(int orderId, int memberId, String memberPhone) throws BusinessErrorException {
    	// 订单信息
        Order order = orderDao.findOne(orderId);

        // 订单已经在走退款流程
        if (0 != order.getBackOrderStatus()) {
            throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单正在退款，无法支付");
        }

        // 订单分成信息
        OrderInfo orderDetail = orderInfoDao.findOne(orderId);

        Date now = new Date();
        // 再次生成单订单 支付单号
        // 支付方式
        String payCode = "2";

        PayInfo payInfo = new PayInfo();
        // TODO 已经支付
        payInfo.setPayStatus(1);
        payInfo.setCreateTime(now);
        payInfo.setUpdateDate(now);

        payInfo.setSupplierId(order.getSupplierId());
        // 支付方式
        payInfo.setPayStyle(payCode);
        //
        payInfo.setPayTime(now);

        // TODO
        // 支付人
        payInfo.setPayMemberId(memberId);
        Member member = memberDao.findOne(memberId);

        payInfo.setPayMemberName(member.getMemberTruename());
        payInfo.setPayMemberPhone(memberPhone);

        payInfo.setPayMoney(order.getPayPrice());

        payInfo.setCompanyName("善融大宗农产品商城");

        PayInfo newPayInfo = payInfoDao.save(payInfo);

        // 更新订单的支付信息
        order.setPayId(newPayInfo.getPayId());
        order.setPayCode(payCode);
        order.setPayTime(now);
        order.setOrderStatus(Constants.ORDER_STATUS_PAYED);

        orderDao.save(order);

        updateFinAcc(order);

        // 添加会员 成长记录
        // 会员信息
        int growth = member.getMemberGrowth() + order.getOrderPrice().intValue();
        member.setMemberGrowth(growth);

        memberDao.save(member);

        // 会员成长记录
        GrowthHistory growthHist = new GrowthHistory();
        growthHist.setMemberId(memberId);
        growthHist.setRecord("订单消费金额" + order.getOrderPrice() + "元");
        growthHist.setGrowth(growth);
        growthHist.setRecordDate(now);

        growthHistoryDao.save(growthHist);
    }

    /**
     * 支付成功后回调函数
     * @param tradeCode
     */
    @Transactional(rollbackFor = Exception.class)
    public void callBackPay(String tradeCode) {
    	
    	logger.info("交易代码："+tradeCode);
    	
        int payId = Integer.valueOf(tradeCode.substring(1));
        
        logger.info("付款Id:"+payId);
        
        int memberId;
        Date now = new Date();
        // 如果是订单再次支付
        if (tradeCode.startsWith("O")) {
            Order order = orderDao.findOne(payId);
            order.setPayTime(now);
            order.setOrderStatus(Constants.ORDER_STATUS_PAYED);
            orderDao.save(order);

            // 修改支付单的信息
            PayInfo payInfo = payInfoDao.findOne(order.getPayId());
            memberId = payInfo.getPayMemberId();
            // 已经支付
            payInfo.setPayStyle(String.valueOf(Constants.PAY_TYPE_PAYBAO));
            payInfo.setPayStatus(1);
            payInfo.setUpdateDate(now);
            payInfo.setPayTime(now);
            payInfoDao.save(payInfo);
            
            //更新优惠券状态
            MemberCoupon memberCoupon =	memberCouponDao.findByOrderId(order.getOrderId());
            if(memberCoupon != null){
            	memberCoupon.setCouponStatus(20); 	//20 表示优惠券已使用
            }

            // 添加会员 成长记录
            // 会员信息
            Member member = memberDao.findOne(memberId);
            int growth = member.getMemberGrowth() + order.getOrderPrice().intValue();
            member.setMemberGrowth(growth);
            memberDao.save(member);

            // 会员成长记录
            GrowthHistory growthHist = new GrowthHistory();
            growthHist.setMemberId(memberId);
            growthHist.setRecord("订单消费金额" + order.getOrderPrice() + "元");
            growthHist.setGrowth(growth);
            growthHist.setRecordDate(now);
            growthHistoryDao.save(growthHist);
        } else { // 如果是合并支付
            List<Order> orders = orderDao.findByPayId(payId);
            // 修改支付单的信息
            PayInfo payInfo = payInfoDao.findOne(payId);
            
            logger.info("查看payInfo:"+payInfo);
            // 已经支付
            payInfo.setPayStyle(String.valueOf(Constants.PAY_TYPE_PAYBAO));
            payInfo.setPayStatus(1);
            payInfo.setUpdateDate(now);
            payInfo.setPayTime(now);
            payInfoDao.save(payInfo);
            
            logger.info("支付订单对象列表:"+orders);
            // 修改订单的信息
            for (Order order : orders) {
                memberId = order.getMemberId();
                order.setPayTime(now);
                order.setOrderStatus(Constants.ORDER_STATUS_PAYED);
                orderDao.save(order);

                // 添加会员 成长记录
                // 会员信息
                Member member = memberDao.findOne(memberId);
                int growth = member.getMemberGrowth() + order.getOrderPrice().intValue();
                member.setMemberGrowth(growth);
                memberDao.save(member);
                
                //更新优惠券状态
                MemberCoupon memberCoupon =	memberCouponDao.findByOrderId(order.getOrderId());
                if(memberCoupon != null){
                	memberCoupon.setCouponStatus(20); 	//20 表示优惠券已使用
                }

                // 会员成长记录
                GrowthHistory growthHist = new GrowthHistory();
                growthHist.setMemberId(memberId);
                growthHist.setRecord("订单消费金额" + order.getOrderPrice() + "元");
                growthHist.setGrowth(growth);
                growthHist.setRecordDate(now);
                growthHistoryDao.save(growthHist);
            }
        }
    }

    /**
     * 支付结果通知处理(调试之用)
     *
     * @param payId
     * @param memberId
     */
    @Transactional(rollbackFor = Exception.class)
    public void testHandleWSxpayNotify(int payId, int memberId) {
        // 验证订单 是否已经支付并处理
        // 商户支付单号
        PayInfo payInfo = payInfoDao.findOne(payId);

        // 用户支付单号无效
        if (payInfo == null) {
            logger.error("用户支付单号" + payId + "无效");
            return;
        }

        if (0 != payInfo.getPayStatus()) {
            return;
        }

        Date now = new Date();
        // 支付方式
        String payStyle = "2";

        // 查询支付单号 关联的订单
        List<Order> orderList = orderDao.findByPayId(payId);

        // 订单支付总金额
        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        for (Order order : orderList) {

            order.setOrderStatus(Constants.ORDER_STATUS_PAYED);
            order.setPayCode(payStyle);
            order.setPayTime(now);

            orderDao.save(order);

            totalOrderPrice = totalOrderPrice.add(order.getOrderPrice());

            updateFinAcc(order);

            // 会员信息
            Member member = memberDao.findOne(memberId);
            int growth = member.getMemberGrowth() + order.getOrderPrice().intValue();
            member.setMemberGrowth(growth);

            memberDao.save(member);

            // 会员成长记录
            GrowthHistory growthHist = new GrowthHistory();
            growthHist.setMemberId(memberId);
            growthHist.setRecord("订单消费金额" + order.getOrderPrice() + "元");
            growthHist.setGrowth(growth);
            growthHist.setRecordDate(now);

            growthHistoryDao.save(growthHist);

        }

        // orderDao.save(orderList);

        // 支付方式
        payInfo.setPayStyle(payStyle);
        payInfo.setPayStatus(Constants.PAY_STATE_PAYED);

        payInfo.setPayTime(now);
        payInfo.setPayMoney(totalOrderPrice);
        payInfo.setPayMemberId(memberId);

        payInfoDao.save(payInfo);
    }

    /**
     * 获取支付单信息
     *
     * @param payId
     * @return
     */
    public Object getPayInfo(int payId) {
        return payInfoDao.findOne(payId);
    }

    /**
     * 再次确认订单是否已经支付成功，如果支付成功订单数据没有更改，则更改
     *
     * @param outTradeNo 支付单号或者订单号
     * @return
     */
    public void ensureAlipay(String outTradeNo) {
        if (outTradeNo != null) {
            int outTradeId = Integer.valueOf(outTradeNo.substring(1));
            if (outTradeNo.startsWith("P")) {
                List<Order> orderList = orderDao.findByPayId(outTradeId);
                for (Order order : orderList) {
                    if (order.getOrderStatus() == Constants.ORDER_STATUS_DEFAULT) {
                        callBackPay(outTradeNo);
                    }
                }
            } else {
                Order order = orderDao.findOne(outTradeId);
                if (order.getOrderStatus() == Constants.ORDER_STATUS_DEFAULT) {
                    callBackPay(outTradeNo);
                }
            }
        }
    }

    /**
     * 修改财务信息，用户确认收货后，将各平台的收益值根据此订单来进行修改,
     * 2016-08-03将供应商收益值的计算改为：供应商收益+运费
     *
     * @param order
     */
    public void updateFinAcc(Order order) {
        Date now = new Date();
        // 财务信息入账
        // 订单分成信息
        OrderInfo orderInfo = orderInfoDao.findOne(order.getOrderId());

        logger.info("订单支付，修改各平台账户金额--------------------------");
        // 平台
        logger.info("修改平台账户金额-------------------------------");
        FinAcc srdzFin = finAccDao.findByAccountNoAndAccountType(666, Constants.ACC_TYPE_SRDZ);
        logger.info("账户当前财产状况, 收入：{}，支出：{}，余额：{}", srdzFin.getAccountIncome(), srdzFin.getAccountPay(), srdzFin.getAccountBal());
        logger.info("账户余额增加：{}， 账户收入增加：{}", orderInfo.getPlatformAmount(), orderInfo.getPlatformAmount());
        if (srdzFin != null) {
            srdzFin.setAccountIncome(srdzFin.getAccountIncome().add(orderInfo.getPlatformAmount()));
            srdzFin.setAccountBal(srdzFin.getAccountBal().add(orderInfo.getPlatformAmount()));
            finAccDao.save(srdzFin);
        } else {
//            FinAcc srdzNewFin = new FinAcc();
//            srdzNewFin.setAccountNo(666);
//            srdzNewFin.setAccountIncome(orderInfo.getPlatformAmount());
//            srdzNewFin.setAccountBal(orderInfo.getPlatformAmount());
//            srdzNewFin.setAccountType(Constants.ACC_TYPE_SRDZ);
//            srdzNewFin.setAccountPay(new BigDecimal(0));
//            srdzNewFin.setUpdateTime(now);
//            finAccDao.save(srdzNewFin);
        }

        // 供应商
        FinAcc supplierFin = finAccDao.findByAccountNoAndAccountType(order.getSupplierId(),
                Constants.ACC_TYPE_SUPPLIER);
        if (supplierFin != null) {
            logger.info("修改供应商账户金额，账户：{}-------------------------------", supplierFin.getAccountNo());
            logger.info("账户当前财产状况, 收入：{}，支出：{}，余额：{}", supplierFin.getAccountIncome(), supplierFin.getAccountPay(), supplierFin.getAccountBal());
            logger.info("账户余额增加：{}， 账户收入增加：{}", order.getSupplierAmount(), order.getSupplierAmount());
            BigDecimal totalIncome = order.getSupplierAmount().add(order.getFlowPrice());
            supplierFin.setAccountIncome(supplierFin.getAccountIncome().add(totalIncome));
            supplierFin.setAccountBal(supplierFin.getAccountBal().add(order.getSupplierAmount()).add(order.getFlowPrice()));

            finAccDao.save(supplierFin);
        } else {
            FinAcc supplierNewFin = new FinAcc();
            supplierNewFin.setAccountNo(order.getSupplierId());
            supplierNewFin.setAccountIncome(order.getSupplierAmount().add(order.getFlowPrice()));
            supplierNewFin.setAccountBal(order.getSupplierAmount().add(order.getFlowPrice()));
            supplierNewFin.setAccountType(Constants.ACC_TYPE_SUPPLIER);
            supplierNewFin.setUpdateTime(now);
            supplierNewFin.setAccountPay(new BigDecimal(0));
            finAccDao.save(supplierNewFin);

            logger.info("修改供应商账户金额，账户：{}-------------------------------", supplierNewFin.getAccountNo());
            logger.info("账户当前财产状况, 收入：{}，支出：{}，余额：{}", 0, 0, 0);
            logger.info("账户余额增加：{}， 账户收入增加：{}", order.getSupplierAmount(), order.getSupplierAmount());
        }

        // 服务中心
        // 供应商
        Supplier supplier = supplierDao.findOne(order.getSupplierId());

        FinAcc ssFin = finAccDao.findByAccountNoAndAccountType(supplier.getServiceCenterId(), Constants.ACC_TYPE_SS);

        if (ssFin != null) {
            logger.info("修改服务中心账户金额，账户：{}-------------------------------", ssFin.getAccountNo());
            logger.info("账户当前财产状况, 收入：{}，支出：{}，余额：{}", ssFin.getAccountIncome(), ssFin.getAccountPay(), ssFin.getAccountBal());
            logger.info("账户余额增加：{}， 账户收入增加：{}", orderInfo.getServiceCenterAmount(), orderInfo.getServiceCenterAmount());
            ssFin.setAccountIncome(ssFin.getAccountIncome().add(orderInfo.getServiceCenterAmount()));
            ssFin.setAccountBal(ssFin.getAccountBal().add(orderInfo.getServiceCenterAmount()));
            finAccDao.save(ssFin);
        } else {
            FinAcc ssNewFin = new FinAcc();
            ssNewFin.setAccountNo(supplier.getServiceCenterId());
            ssNewFin.setAccountIncome(orderInfo.getServiceCenterAmount());
            ssNewFin.setAccountBal(orderInfo.getServiceCenterAmount());
            ssNewFin.setAccountType(Constants.ACC_TYPE_SS);
            ssNewFin.setUpdateTime(now);
            ssNewFin.setAccountPay(new BigDecimal(0));
            finAccDao.save(ssNewFin);

//            logger.info("修改服务中心账户金额，账户：{}-------------------------------", ssFin.getAccountNo());
            logger.info("账户当前财产状况, 收入：{}，支出：{}，余额：{}", 0, 0, 0);
            logger.info("账户余额增加：{}， 账户收入增加：{}", orderInfo.getServiceCenterAmount(), orderInfo.getServiceCenterAmount());
        }
    }
}

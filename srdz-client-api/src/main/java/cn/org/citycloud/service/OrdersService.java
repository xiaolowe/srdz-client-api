package cn.org.citycloud.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.citycloud.utils.MessageFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.bean.DeliveryGoods;
import cn.org.citycloud.bean.GoodsDeliverCost;
import cn.org.citycloud.bean.OrderGoods;
import cn.org.citycloud.bean.PreOrder;
import cn.org.citycloud.bean.ShopOrderGoods;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.Cash;
import cn.org.citycloud.entity.DiscountGood;
import cn.org.citycloud.entity.FlowInfo;
import cn.org.citycloud.entity.FlowTemplate;
import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.Member;
import cn.org.citycloud.entity.MemberAddr;
import cn.org.citycloud.entity.MemberCoupon;
import cn.org.citycloud.entity.Message;
import cn.org.citycloud.entity.Order;
import cn.org.citycloud.entity.OrderGood;
import cn.org.citycloud.entity.OrderInfo;
import cn.org.citycloud.entity.PayInfo;
import cn.org.citycloud.entity.ServiceCenter;
import cn.org.citycloud.entity.ShoppingCart;
import cn.org.citycloud.entity.Supplier;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.CashDao;
import cn.org.citycloud.repository.DiscountGoodDao;
import cn.org.citycloud.repository.FlowInfoDao;
import cn.org.citycloud.repository.FlowTemplateDao;
import cn.org.citycloud.repository.GoodsDao;
import cn.org.citycloud.repository.MemberAddrDao;
import cn.org.citycloud.repository.MemberDao;
import cn.org.citycloud.repository.MessageDao;
import cn.org.citycloud.repository.OrderDao;
import cn.org.citycloud.repository.OrderGoodDao;
import cn.org.citycloud.repository.OrderInfoDao;
import cn.org.citycloud.repository.PayInfoDao;
import cn.org.citycloud.repository.ServiceCenterDao;
import cn.org.citycloud.repository.ShoppingCartDao;
import cn.org.citycloud.repository.SupplierDao;

/**
 * 订单信息Service
 * 
 * @author lanbo
 *
 */
@Component
@Transactional
public class OrdersService {
	
	private static Logger logger = Logger.getLogger(OrdersService.class);

	@Autowired
	private CashDao cashDao;

	@Autowired
	private MemberDao memberDao;

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
	private OrderInfoDao orderInfoDao;

	@Autowired
	private PayInfoDao payInfoDao;

	@Autowired
	private MemberAddrDao memberAddrDao;

	@Autowired
	private ShoppingCartDao cartDao;

	@Autowired
	private CouponService couponService;

	@Autowired
	private FlowTemplateDao flowTemplateDao;

	@Autowired
	private FlowInfoDao flowInfoDao;

	@Autowired
	private DiscountGoodDao discountGoodDao;
	

	@Autowired
	private MessageDao messageDao;

	/**
	 * 根据订单号 获取订单状态 获取订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<Order> findStoreOrderList(int memberId, int status) {
		List<Order> orderList = null;
		if (99 == status) {
			orderList = orderDao.findByMemberIdOrderByCreateTimeDesc(memberId);
		} else {
			orderList = orderDao.findByMemberIdAndOrderStatusOrderByCreateTimeDesc(memberId, status);
		}

		return orderList;
	}

	/**
	 * 订单详情
	 * 
	 * @param storeId
	 * @param memberId
	 * @param id
	 * @return
	 */
	public Order findStoreOrder(int id, int userId) {

		Order order = orderDao.findByOrderIdAndMemberId(id, userId);

		return order;
	}

	/**
	 * 取消订单
	 * 
	 * @throws BusinessErrorException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void cancelOrder(int orderId, int userId) throws BusinessErrorException {

		Order order = orderDao.findByOrderIdAndMemberId(orderId, userId);

		if (order == null)
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单不存在！");

		// 已经付款的订单无法取消
		if (Constants.ORDER_STATUS_DEFAULT != order.getOrderStatus()) {
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "已经付款的订单无法取消！");
		}

		order.setOrderStatus(Constants.ORDER_STATUS_CANCELD);
		order.setUpdateTime(new Date());

		orderDao.save(order);

		// 修改商品的库存
		List<OrderGood> orderGoodList = orderGoodDao.findByOrderId(orderId);
		for (OrderGood orderGood : orderGoodList) {
			Good good = goodsDao.findOne(orderGood.getGoodsId());
			// 判断是否是特惠商品
			if (good.getDiscountFlg() == 1) {
				DiscountGood discountGood = discountGoodDao.findOne(good.getGoodsId());
				discountGood.setAlreadySale(discountGood.getAlreadySale() - orderGood.getGoodsNum());
				discountGood.setSurplus(discountGood.getSurplus() + orderGood.getGoodsNum());
				discountGood.setUpdateTime(new Date());
				discountGoodDao.save(discountGood);
			} else {
				good.setAlreadySale(good.getAlreadySale() - orderGood.getGoodsNum());
				good.setSurplus(good.getSurplus() + orderGood.getGoodsNum());
				good.setUpdateTime(new Date());
				goodsDao.save(good);
			}
		}

		// 发送消息给相应的供应商
		Map<String, String> param = new HashMap<>();
		param.put("orderId", order.getOrderId() + "");
		Supplier supplier = supplierDao.findOne(order.getSupplierId());
		String receiver = supplier.getSupplierName();
		int receiverId = supplier.getSupplierId();
		Message message = MessageFactory.getMessage("取消订单", "cancel_order", param, Constants.MSG_SUPPLIER, receiverId, receiver);
		messageDao.save(message);

		// 发送消息给相应的服务中心
		Map<String, String> data = new HashMap<>();
		data.put("orderId", order.getOrderId() + "");
		ServiceCenter serviceCenter = serviceCenterDao.findOne(supplier.getServiceCenterId());
		String receiverService = serviceCenter.getServiceCenterName();
		int receiverIdService = serviceCenter.getServiceCenterId();
		Message messageService = MessageFactory.getMessage("取消订单", "cancel_order", data, Constants.MSG_SERVICE, receiverIdService, receiverService);
		messageDao.save(messageService);
	}

	/**
	 * 确认收货
	 * 
	 * @throws BusinessErrorException
	 */
	public void confirmOrder(int orderId, int storeId, int memberId) throws BusinessErrorException {

		Order order = orderDao.findByOrderIdAndMemberId(orderId, memberId);

		if (order == null)
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单不存在！");
		if (Constants.ORDER_STATUS_PAYED == order.getOrderStatus()) {
			Date now = new Date();
			order.setOrderStatus(Constants.ORDER_STATUS_EVALUATED);
			order.setGetGoodsTime(now);
			order.setFinishTime(now);
			order.setUpdateTime(now);

			orderDao.save(order);
		} else {
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单无法确认收货！");
		}
	}

	/**
	 * 申请退款 退款状态 （100:待退款 110:退款中 120:待汇款 130:拒绝退款 140:已退款）
	 * 
	 * @throws BusinessErrorException
	 */
	public void refundOrder(int orderId, int memberId, String backOrderCause) throws BusinessErrorException {
		Order order = orderDao.findByOrderIdAndMemberId(orderId, memberId);

		if (order == null)
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单不存在！");

		if (Constants.ORDER_STATUS_PAYED != order.getOrderStatus()) {
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单无法申请退款！");
		}

		// 退款状态
		order.setBackOrderStatus(100);
		order.setBackOrderCause(backOrderCause);
		
		orderDao.save(order);
		
		//生成退款记录
		Cash nCash = new Cash();
		nCash.setOrderId(orderId);
		nCash.setApplyUserId(memberId);
		nCash.setApplyUserName(order.getMemberName());
		nCash.setSupplierId(order.getSupplierId());
		nCash.setConfirmStatus(4);
		nCash.setApplyType(2);
		nCash.setAccountType(new Byte("5"));
		nCash.setApplyMoney(order.getOrderPrice());
		Date now = new Date();
		nCash.setCreateTime(now);
		nCash.setUpdateTime(now);
		nCash.setApplyTime(now);
		cashDao.save(nCash);

		// 发送消息给相应的供应商
		Map<String, String> param = new HashMap<>();
		param.put("userName", order.getMemberPhone());
		param.put("orderId", order.getOrderId() + "");
		param.put("money", order.getOrderPrice().toString());
		Supplier supplier = supplierDao.findOne(order.getSupplierId());
		String receiver = supplier.getSupplierName();
		int receiverId = supplier.getSupplierId();
		Message message = MessageFactory.getMessage("退款", "refund", param, Constants.MSG_SUPPLIER, receiverId, receiver);
		messageDao.save(message);

		// 发送消息给相应的服务中心
		Map<String, String> data = new HashMap<>();
		data.put("userName", order.getMemberPhone());
		data.put("orderId", order.getOrderId() + "");
		data.put("money", order.getOrderPrice().toString());
		ServiceCenter serviceCenter = serviceCenterDao.findOne(supplier.getServiceCenterId());
		String receiverService = serviceCenter.getServiceCenterName();
		int receiverIdService = serviceCenter.getServiceCenterId();
		Message messageService = MessageFactory.getMessage("退款", "refund", data, Constants.MSG_SERVICE, receiverIdService, receiverService);
		messageDao.save(messageService);
	}

	/**
	 * 提交订单
	 *
	 * @param preOrder
	 * @param memberId
	 * @return
	 * @throws BusinessErrorException
     */
	@Transactional(rollbackFor = Exception.class)
	public Object submitOrder(PreOrder preOrder, int memberId) throws BusinessErrorException {
		// 生成预订单
		// 订单商品
		List<ShopOrderGoods> orderGoodsList = preOrder.getOrderGoodsList();

		// 验证商品数据
		if (orderGoodsList.size() == 0) {
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "此订单没有商品数据！");
		}

		// 会员信息
		Member member = memberDao.findOne(memberId);
		if (Constants.MEMBER_STATE_CLOSED == member.getMemberStatus()) {
			throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "此会员已经被禁用！");
		}

		// 生成时间
		Date now = new Date();

		// 预先生成支付单信息 支持 合并支付
		PayInfo payInfo = new PayInfo();
		payInfo.setPayStyle("2");
		payInfo.setPayStatus(0);
		payInfo.setCreateTime(now);
		payInfo.setUpdateDate(now);
		payInfo.setPayMemberId(memberId);
		PayInfo newPayInfo = payInfoDao.save(payInfo);

		// 支付单支付金额
		BigDecimal payMoney = new BigDecimal(0);
		// 所有订单金额金额
		BigDecimal orderPriceAll = new BigDecimal(0);
		// 验证商品 是否拆分商品订单
		if(orderGoodsList != null && orderGoodsList.size() > 0){
			for(int i = 0; i < orderGoodsList.size(); i++){
				
				ShopOrderGoods shopOrderGoods = orderGoodsList.get(i);
				
				int supplierId = shopOrderGoods.getSupplierId();
				// 同一店铺的商品
				List<OrderGoods> orderGoods = shopOrderGoods.getOrderGoods();
	
				if (orderGoods.size() == 0) {
					throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "此订单没有商品数据！");
				}
				// 供应商信息
				Supplier supplier = supplierDao.findOne(supplierId);
				if (Constants.SUPPLIER_STATUS_VERIFIED != supplier.getStatus()) {
					throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, supplier.getSupplierName() + "供应商未审核，不能下单！");
				}
	
				// 供应商 平台分成
				BigDecimal platformRates = supplier.getSupplierLevel().getPlatformRates();
	
				// 服务中心信息分成
				ServiceCenter serviceCenter = serviceCenterDao.findOne(supplier.getServiceCenterId());
				BigDecimal serviceCenterRates = serviceCenter.getServiceCenterLevel().getServiceCenterRates();
	
				// 新建订单
				Order order = new Order();
	
				// 订单类型
				order.setOrderType(Constants.ORDER_TYPE_NORMAL);
	
				order.setSupplierId(supplierId);
				order.setSupplierName(supplier.getSupplierName());
	
				// 会员信息
				order.setMemberId(memberId);
				order.setMemberName(member.getMemberTruename());
				order.setMemberPhone(member.getMemberPhone());
	
				order.setMemberAddrId(preOrder.getMemberAddrId());
				order.setOrderTime(now);
				order.setOrderStatus(Constants.ORDER_STATUS_DEFAULT);
				order.setOrderType(Constants.ORDER_TYPE_NORMAL);
				order.setInitPrice(BigDecimal.ZERO);
	
				// 收货地址
				int memberAddrId = preOrder.getMemberAddrId();
				order.setMemberAddrId(memberAddrId);
				MemberAddr memberAddr = memberAddrDao.findByMemberAddrIdAndMemberId(memberAddrId, memberId);
				if (memberAddr != null) {
					order.setRegionProv(memberAddr.getRegionProv());
					order.setRegionProvName(memberAddr.getRegionProvDetail().getRegionName());
					order.setRegionCity(memberAddr.getRegionCity());
					order.setRegionCityName(memberAddr.getRegionCityDetail().getRegionName());
					order.setRegionArea(memberAddr.getRegionArea());
					order.setRegionAreaName(memberAddr.getRegionAreaDetail().getRegionName());
					order.setContactName(memberAddr.getContactsName());
					order.setContactPhone(memberAddr.getContactsPhone());
					order.setContactAddress(memberAddr.getContactsAddress());
				}
	
				// 订单金额
				BigDecimal orderPrice = BigDecimal.ZERO;
				BigDecimal payPrice = BigDecimal.ZERO;
	
				// 供应商营收
				BigDecimal supplierAmount = BigDecimal.ZERO;
	
				order.setOrderPrice(orderPrice);
				order.setPayPrice(payPrice);
				order.setSupplierAmount(supplierAmount);
	
				// 支付单号
				order.setPayId(newPayInfo.getPayId());
	
				Order newOrder = orderDao.save(order);
	
				int orderId = newOrder.getOrderId();
	
				Order generateOrder = orderDao.findOne(orderId);
	
				// 订单商品信息
				// 物流费用
				BigDecimal flowPrice = BigDecimal.ZERO;
	
				// 订单商品
				List<OrderGood> goodsEntitys = new ArrayList<OrderGood>();
	
				DeliveryGoods deliveryGoods = new DeliveryGoods();
				deliveryGoods.setRegionCity(memberAddr.getRegionCity());
	
				for (OrderGoods goods : orderGoods) {
					// 获取商品信息
					Good goodInfo = goodsDao.findOne(goods.getGoodsId());
	
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
					if (goodInfo.getSurplus() <= 0) {
						throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, goodInfo.getGoodsName() + "已经售罄。");
					}
	
	//				if (goodInfo.getSurplus() < goods.getGoodsNum()) {
	//					throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, goodInfo.getGoodsName() + "库存不足。");
	//				}
	
					// 删除购物车相应商品
					ShoppingCart cart = cartDao.findByMemberIdAndGoodsId(memberId, goods.getGoodsId());
	
					if (cart != null)
						cartDao.delete(cart);
	
					// 计算商品物流费用 Start
					deliveryGoods.setGoodsId(goods.getGoodsId());
					deliveryGoods.setGoodsNum(goods.getGoodsNum());
	
					GoodsDeliverCost deliverCost = calcGoodsDeliveryCosts(deliveryGoods);
					flowPrice = flowPrice.add(deliverCost.getDeliverCost());
					// 计算商品物流费用 End
	
					// 商品金额
					BigDecimal goodsTotal = null;
					
					BigDecimal goodsPrice = null;
	
					// 商品销量 减库存
					// 判断是否是特惠商品
					if (goodInfo.getDiscountFlg() == 1) {
						
						DiscountGood discountGood = discountGoodDao.findOne(goodInfo.getGoodsId());
						goodsTotal = discountGood.getBenefitPrice().multiply(new BigDecimal(goods.getGoodsNum()));
						goodsPrice = discountGood.getBenefitPrice();
						
						discountGood.setAlreadySale(discountGood.getAlreadySale() + goods.getGoodsNum());
						discountGood.setSurplus(discountGood.getSurplus() - goods.getGoodsNum());
						discountGood.setUpdateTime(new Date());
						discountGoodDao.save(discountGood);
					} else {
						
						goodsTotal = goodInfo.getSalePrice().multiply(new BigDecimal(goods.getGoodsNum()));
						goodsPrice = goodInfo.getSalePrice();
						
						goodInfo.setAlreadySale(goodInfo.getAlreadySale() + goods.getGoodsNum());
						goodInfo.setSurplus(goodInfo.getSurplus() - goods.getGoodsNum());
						goodInfo.setUpdateTime(now);
						goodsDao.save(goodInfo);
					}
					
					// 累计商品金额
					orderPrice = orderPrice.add(goodsTotal);
	
					// 生成订单商品实体
					OrderGood entity = new OrderGood();
					entity.setOrderId(orderId);
					entity.setGoodsId(goods.getGoodsId());
					entity.setGoodsName(goodInfo.getGoodsName());
					entity.setGoodsPrice(goodsPrice);
					entity.setGoodsNum(goods.getGoodsNum());
					entity.setGoodsImage(goodInfo.getGoodsImage());
					entity.setStandard(goodInfo.getStandard());
					entity.setGoodsPayPrice(goodsTotal);
					entity.setMemberId(memberId);
	
					goodsEntitys.add(entity);
	
	
				}
	
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
					if (BigDecimal.ZERO.compareTo(flowPrice) > 0) {
	
						supplierAmount = supplierAmount.add(flowPrice);
					}
	
					// 服务中心分成金额
					if (BigDecimal.ZERO.compareTo(platformRates) != 0) {
						serviceCenterAmount = commisAmount.multiply(serviceCenterRates).divide(new BigDecimal(100))
								.setScale(2, BigDecimal.ROUND_HALF_UP);
	
						commisAmount = commisAmount.subtract(serviceCenterAmount);
					}
	
				}
	
				// 物流费用
				if (flowPrice.compareTo(BigDecimal.ZERO) > 0) {
					orderPrice = orderPrice.add(flowPrice);
					payPrice = payPrice.add(flowPrice);
				}
				
				orderPriceAll =  orderPriceAll.add(orderPrice);
				
				logger.info("订单总金额："+orderPriceAll);
				
				int count = orderGoodsList.size() - 1;
				logger.info("i===:"+i+"===size==="+count);
				// 优惠劵 减免订单金额
				if (i == count && preOrder.getCouponId() != 0) {
					MemberCoupon memberCoupon = couponService.validMemberCoupon(preOrder.getCouponId());
					logger.info("满足优惠券条件："+memberCoupon.getCouponCondition());
					if (memberCoupon.getCouponCondition().compareTo(orderPriceAll) < 0 || memberCoupon.getCouponCondition().compareTo(orderPriceAll) == 0) {
						memberCoupon.setOrderId(orderId);
						payPrice = orderPrice.subtract(memberCoupon.getCouponMoney());
						commisAmount = commisAmount.subtract(memberCoupon.getCouponMoney());
						
					} else {
						payPrice = orderPrice;
					}
				} else {
					payPrice = orderPrice;
				}
				
				// 添加 普通订单信息
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(orderId);
				orderInfo.setSupplierId(supplierId);
				orderInfo.setSupplierName(supplier.getSupplierName());
				orderInfo.setPlatformRates(platformRates);
				orderInfo.setPlatformAmount(commisAmount);
				orderInfo.setServiceCenterRates(serviceCenterRates);
				orderInfo.setServiceCenterAmount(serviceCenterAmount);
	
				orderInfoDao.save(orderInfo);
				
				
				generateOrder.setOrderPrice(orderPrice);
				generateOrder.setPayPrice(payPrice);
	
				// 计算物流费用 start
				generateOrder.setFlowPrice(flowPrice);
				// 计算物流费用 end
	
				// 设置供应商营收
				generateOrder.setSupplierAmount(supplierAmount);
	
				payMoney = payMoney.add(payPrice);
	
				payInfo.setPayMoney(payMoney);
				// 批量插入 订单商品
				orderGoodDao.save(goodsEntitys);
	
				payInfoDao.save(payInfo);
	
				// 更新订单金额
				orderDao.save(generateOrder);
				
				// 发送消息给相应的供应商
				Map<String, String> param = new HashMap<>();
				String receiver = supplier.getSupplierName();
				int receiverId = supplierId;
				Message message = MessageFactory.getMessage("生成订单", "new_order", param, Constants.MSG_SUPPLIER, receiverId, receiver);
				messageDao.save(message);

				// 发送消息给相应的服务中心
				Map<String, String> data = new HashMap<>();
				String receiverService = serviceCenter.getServiceCenterName();
				int receiverIdService = serviceCenter.getServiceCenterId();
				Message messageService = MessageFactory.getMessage("生成订单", "new_order", data, Constants.MSG_SERVICE, receiverIdService, receiverService);
				messageDao.save(messageService);
			
			}
		}

		return payInfo;
	}

	/**
	 * 计算商品的物流费用
	 */
	private GoodsDeliverCost calcGoodsDeliveryCosts(DeliveryGoods deliveryGoods) {

		// 物流费用
		GoodsDeliverCost cost = new GoodsDeliverCost();
		cost.setGoodsId(deliveryGoods.getGoodsId());

		// 商品信息
		Good good = goodsDao.findOne(deliveryGoods.getGoodsId());

		// 物流模板信息 包邮
		if (good == null || good.getFlowTemplateId() == 0) {
			cost.setDeliverCost(BigDecimal.ZERO);
			return cost;
		}

		FlowTemplate flowTemp = flowTemplateDao.findByFlowTemplateIdAndSupplierId(good.getFlowTemplateId(),
				good.getSupplierId());

		// 未设定物流模板 包邮
		if (flowTemp == null) {
			cost.setDeliverCost(BigDecimal.ZERO);
			return cost;
		}

		// 物流费用 重量件数标识( 1 重量 2 件数)
		int weightPieceFlag = flowTemp.getWeightPieceFlag();

		// 收货地市
		int regionCity = deliveryGoods.getRegionCity();

		// 物流信息 按照收货地市查询
		Object[] flowInfo = flowInfoDao.findFlownInfoByRegionCity(good.getFlowTemplateId(), regionCity);

		// 计算数值
		// 物流物品
		BigDecimal flowGoods = BigDecimal.ZERO;

		// 物流价格
		BigDecimal flowPrice = BigDecimal.ZERO;

		// 加多少物品
		BigDecimal addFlowGoods = BigDecimal.ZERO;

		// 加多少价格
		BigDecimal addflowPrice = BigDecimal.ZERO;

		// 获取默认物流计算信息
		if (flowInfo.length == 0) {
			FlowInfo defaultFlowInfo = flowInfoDao.findByFlowTemplateIdAndDefaultFlag(good.getFlowTemplateId(), 1);
			flowGoods = defaultFlowInfo.getFlowGoods();
			flowPrice = defaultFlowInfo.getFlowPrice();
			addFlowGoods = defaultFlowInfo.getAddFlowGoods();
			addflowPrice = defaultFlowInfo.getAddGoodsPrice();
		} else {
			Object[] flowInfoArr = (Object[]) flowInfo[0];
			if (flowInfoArr.length == 4) {
				flowGoods = (BigDecimal) flowInfoArr[0];
				flowPrice = (BigDecimal) flowInfoArr[1];
				addFlowGoods = (BigDecimal) flowInfoArr[2];
				addflowPrice = (BigDecimal) flowInfoArr[3];
			}
		}

		if (flowGoods == BigDecimal.ZERO) {
			cost.setDeliverCost(BigDecimal.ZERO);
			return cost;
		}

		// 物流算费
		BigDecimal flowSumPrice = BigDecimal.ZERO;
		// 按重量计算
		if (1 == weightPieceFlag) {

			// 商品规格 重量
			BigDecimal goodsWeight = good.getGoodsWeight();

			// 商品无重量 包邮
			if (goodsWeight == null || goodsWeight == BigDecimal.ZERO) {
				cost.setDeliverCost(BigDecimal.ZERO);
				return cost;
			}

			// 订单商品重量
			BigDecimal presentWeight = goodsWeight.multiply(new BigDecimal(deliveryGoods.getGoodsNum())).setScale(2,
					BigDecimal.ROUND_HALF_UP);

			// 首重

			// 续重
			BigDecimal contHeavy = presentWeight.subtract(flowGoods);

			if (contHeavy.compareTo(BigDecimal.ZERO) <= 0) {
				flowSumPrice = flowPrice;

			} else {
				if (addFlowGoods.intValue() == 0) {
					// 续重价格
					BigDecimal contDown = new BigDecimal(0);

					flowSumPrice = flowPrice.add(contDown.multiply(addflowPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
				} else {
					// 续重价格
					BigDecimal contDown = contHeavy.divide(addFlowGoods, 0, BigDecimal.ROUND_HALF_UP);

					flowSumPrice = flowPrice.add(contDown.multiply(addflowPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
				}

			}
		} else if (2 == weightPieceFlag) {
			// 按件数计算
			int flowGoodsNum = flowGoods.intValue();
			// 不超件数
			if (deliveryGoods.getGoodsNum() - flowGoodsNum <= 0) {

				flowSumPrice = flowPrice;
			} else {
				// 续件
				if (addFlowGoods.intValue() == 0) {
					int countDown = deliveryGoods.getGoodsNum();
					// 2016-07-27 18：22修改by zerobug,注释此行代码
//					int countSum = deliveryGoods.getGoodsNum() - flowGoodsNum % addFlowGoods.intValue();
					int countSum = deliveryGoods.getGoodsNum();
					if (countSum != 0) {
						countDown += 1;
					}

					flowSumPrice = flowPrice.add(addflowPrice.multiply(new BigDecimal(countDown))).setScale(2,
							BigDecimal.ROUND_HALF_UP);
				} else {
					int countDown = deliveryGoods.getGoodsNum() - flowGoodsNum / addFlowGoods.intValue();
					int countSum = deliveryGoods.getGoodsNum() - flowGoodsNum % addFlowGoods.intValue();
					if (countSum != 0) {
						countDown += 1;
					}

					flowSumPrice = flowPrice.add(addflowPrice.multiply(new BigDecimal(countDown))).setScale(2,
							BigDecimal.ROUND_HALF_UP);
				}
			}
			cost.setDeliverCost(flowSumPrice);
			return cost;
		}

		cost.setDeliverCost(flowSumPrice);
		return cost;
	}
}

package cn.org.citycloud.controller;

import cn.org.citycloud.bean.OrdersSearch;
import cn.org.citycloud.bean.Refund;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.Order;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.OrderDao;
import cn.org.citycloud.service.OrdersService;
import cn.org.citycloud.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/orders")
@Api(tags = "个人中心", position = 8, value = "/orders", description = "我的订单模块", consumes = "application/json")
public class OrdersController extends BaseController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private PaymentService paymentService;

	/**
	 * 所有订单
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "所有订单列表", notes = "获取我的订单信息(订单状态：0(已取消)；10(默认):待支付；20:待发货； 40:已发货；50:待评价； 60:已完成； 99:全部)", response = Order.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "status", value = "订单状态(0(已取消)10(默认):未付款;20:已付款;30:已收货;40:已评价;50:补填订单;99:全部)", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query") })
	public Object getOrders(@ApiIgnore @Valid OrdersSearch search) {

		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<Order> specs = new Specification<Order>() {

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 会员
				Path<Integer> member = root.get("memberId");
				where = cb.and(where, cb.equal(member, getMemberId()));

				if (99 != search.getStatus()) {
					// 订单状态
					Path<Integer> orderStatus = root.get("orderStatus");
					where = cb.and(where, cb.equal(orderStatus, search.getStatus()));
				}

				query.where(where);

				List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();

				// 上架时间倒序
				Path<Date> orderTime = root.get("orderTime");
				javax.persistence.criteria.Order orderTimeOrder = cb.desc(orderTime);
				orderList.add(orderTimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<Order> orderList = orderDao.findAll(specs, pageable);

		return orderList;

	}

	/**
	 * 订单详情
	 * 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取订单详情信息", notes = "获取订单详情信息", response = Order.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Order getOrderDetail(@PathVariable int id) {

		return ordersService.findStoreOrder(id, getMemberId());
	}

	/**
	 * 订单确认收货
	 * 
	 * @throws BusinessErrorException
	 */
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/confirm/{orderId}", method = RequestMethod.POST)
	@ApiOperation(value = "订单确认收货", notes = "订单确认收货")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void confirmOrder(@PathVariable int orderId) throws BusinessErrorException {

		Order order = orderDao.findByOrderIdAndMemberId(orderId, getMemberId());

		if (order == null)
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单不存在！");
		if (Constants.ORDER_STATUS_SENDED == order.getOrderStatus()) {
			Date now = new Date();
			order.setOrderStatus(50);
			order.setFinishTime(now);
			order.setGetGoodsTime(new Date());
			order.setUpdateTime(now);
			orderDao.save(order);

			// 修改各平台收益
			paymentService.updateFinAcc(order);
		} else {
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单无法确认收货！");
		}

	}

	/**
	 * 取消订单
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/cancel/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "取消订单", notes = "取消订单")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void cancelOrder(@PathVariable int id) throws BusinessErrorException {

		ordersService.cancelOrder(id, getMemberId());

	}

	/**
	 * 申请退款
	 */
	@RequestMapping(value = "/refund/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "申请退款", notes = "申请退款")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void refundOrder(@PathVariable int id, @Valid @RequestBody Refund refund) throws BusinessErrorException {

		ordersService.refundOrder(id, getMemberId(), refund.getBackOrderCause());

	}

}

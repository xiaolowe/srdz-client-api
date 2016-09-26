package cn.org.citycloud.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import cn.org.citycloud.entity.*;
import cn.org.citycloud.repository.MemberDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import antlr.StringUtils;
import cn.org.citycloud.bean.Evaluate;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.EvaluateGoodDao;
import cn.org.citycloud.repository.OrderDao;
import cn.org.citycloud.repository.OrderGoodDao;
import cn.org.citycloud.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 商品评价控制器
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/evaluates")
@Api(tags="个人中心", position=8, value = "/evaluates", description = "商品评价模块", consumes="application/json")
public class EvaluateController extends BaseController {

	@Autowired
	private EvaluateGoodDao evaluateDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderGoodDao orderGoodDao;

	@Autowired
	private MemberDao memberDao;

	/**
	 * 提交商品评价
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/submit/{orderId}", method = RequestMethod.POST)
	@ApiOperation(value = "提交商品评价", notes = "提交商品评价")
	public void submitGoodsEvaluate(@PathVariable int orderId, @Valid @RequestBody List<Evaluate> evaluateLst)
			throws BusinessErrorException
	{

		// 判断订单是否已经评价过了
		Order order = orderDao.findOne(orderId);

		if (order == null || 60 == order.getOrderStatus())
		{
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单不存在或者已经评估过了");
		}

		if (50 != order.getOrderStatus())
		{
			throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单还不能评价");
		}

		// 提交商品评价
		Date now = new Date();
		List<EvaluateGood> evaluateList = new ArrayList<EvaluateGood>();
		for (Evaluate evaluate : evaluateLst)
		{
			EvaluateGood entity = new EvaluateGood();
			int orderGoodsId = evaluate.getOrderGoodsId();
			OrderGood orderGood = orderGoodDao.findOne(orderGoodsId);

			if(orderGood == null) continue;
			BeanUtils.copyProperties(orderGood, entity);

			entity.setSupplierId(order.getSupplierId());

			// 评分
			entity.setGevalScores(evaluate.getGevalScores());
			// 评价内容
			entity.setGevalContent(evaluate.getGevalContent());

			// 是否匿名
			entity.setGevalIsanonymous(0);
			entity.setGevalAddtime(now);
			// 是否显示
			entity.setGevalStatus(0);
			Member member = memberDao.findOne(getMemberId());
			entity.setMember(member);
			if(org.apache.commons.lang3.StringUtils.isEmpty(order.getMemberName())) {
				
				entity.setMemberTruename(order.getMemberPhone());
			} else {
				entity.setMemberTruename(order.getMemberName());
			}

			evaluateList.add(entity);
		}
		evaluateDao.save(evaluateList);

		// 买家评价 评价状态 0 未评价 1买家评价 2卖家评价 3双方互评
		//        order.setGevalType(1);

		order.setOrderStatus(60);
		order.setUpdateTime(now);

		orderDao.save(order);
	}
}

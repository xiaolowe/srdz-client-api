package cn.org.citycloud.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.BankCardInfo;
import cn.org.citycloud.bean.DistributorsInfo;
import cn.org.citycloud.bean.SalesShopInfo;
import cn.org.citycloud.bean.SalesShopOrdersInfo;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.Member;
import cn.org.citycloud.entity.OrderGood;
import cn.org.citycloud.entity.SalesMember;
import cn.org.citycloud.entity.SalesShop;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.OrderGoodDao;
import cn.org.citycloud.repository.SalesMemberDao;
import cn.org.citycloud.repository.SalesOrderInfoDao;
import cn.org.citycloud.repository.SalesShopDao;
import cn.org.citycloud.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 分销商控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/distributors")
@Api(tags = "分销商", position = 8, value = "/distributors", description = "分销商模块", consumes = "application/json")
public class DistributorsController extends BaseController {

	@Autowired
	private SalesMemberDao salesMemberDao;

	@Autowired
	private SalesShopDao salesShopDao;
	
	@Autowired
	private SalesOrderInfoDao salesOrderInfoDao;
	
	@Autowired
	private OrderGoodDao orderGoodDao;

	@Autowired
	private MemberService memberService;

	/**
	 * 获取分销商信息
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取分销商信息", notes = "获取分销商信息", response = SalesMember.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object getDistributorInfo() {
		SalesMember salesMember = salesMemberDao.findOne(getMemberId());
		if (salesMember == null) {
			salesMember = new SalesMember();
			salesMember.setStatus(0);
		}
		return salesMember;

	}

	/**
	 * 分销信息提交
	 * 
	 * @throws BusinessErrorException
	 * 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "分销信息提交", notes = "分销信息提交", response = SalesMember.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object register(@ApiParam(value = "分销商信息", required = true) @Valid @RequestBody DistributorsInfo info)
			throws BusinessErrorException {

		// 验证会员是否有效用户
		if (!memberService.validateMemberStatus(getMemberId())) {
			throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "此会员不存在或者已经被禁用！");
		}

		SalesMember salesMember = salesMemberDao.findOne(getMemberId());

		if (salesMember != null) {

			throw new BusinessErrorException(ErrorCodes.INVALID_PARAMETER, "您已经提交过分销信息了");
		}
		// 会员信息
		Member member = memberService.getMember(getMemberId());

		// 创建分销商信息
		Date now = new Date();
		salesMember = new SalesMember();
		salesMember.setMemberId(getMemberId());
		salesMember.setMemberTruename(info.getContactName());
		salesMember.setPhone(member.getMemberPhone());
		salesMember.setStatus(Constants.SALES_MEMBER_DAISHENHE);
		salesMember.setIdentityNo(info.getIdentityNo());
		salesMember.setIdentityImage(info.getIdentityImage());

		salesMember.setRegisterTime(now);
		salesMember.setCreateTime(now);
		salesMember.setUpdateTime(now);

		return salesMemberDao.save(salesMember);

	}
	
	/**
	 * 获取分销商账号收益信息
	 * 
	 */
	@RequestMapping(value = "/profit",method = RequestMethod.GET)
	@ApiOperation(value = "获取分销商收益信息", notes = "获取分销商收益信息", response = Map.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object getDistributorFinAccInfo() {
		
		// 我的收益
		BigDecimal myProfit = BigDecimal.ZERO;
		
		// 今日收益
		BigDecimal todayProfit =  BigDecimal.ZERO;
		
		
		Map<String, String> rstMap = new HashMap<String, String>();
		rstMap.put("myProfit", myProfit.toString());
		rstMap.put("todayProfit", todayProfit.toString());

		return rstMap;
	}
	
	/**
	 * 申请提款
	 * 
	 */
	@RequestMapping(value = "/submitWithdraw", method = RequestMethod.POST)
	@ApiOperation(value = "分销商申请提款", notes = "分销商申请提款", response = void.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void submitDistributorCashInfo() {


	}
	
	/**
	 * 历史提款信息
	 * 
	 */
	@RequestMapping(value = "/withdrawHistory", method = RequestMethod.GET)
	@ApiOperation(value = "获取分销商信息", notes = "获取分销商信息", response = SalesMember.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object getDistributorCashHistory() {

		return salesMemberDao.findOne(getMemberId());

	}

	/**
	 * 绑定银行卡
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/bankCard", method = RequestMethod.PUT)
	@ApiOperation(value = "绑定分销商银行卡信息", notes = "绑定分销商银行卡信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void bandBankCard(@ApiParam(value = "分销商信息", required = true) @Valid @RequestBody BankCardInfo info)
			throws BusinessErrorException {

		SalesMember salesMember = salesMemberDao.findOne(getMemberId());

		if (StringUtils.isNotBlank(salesMember.getCardNo())) {
			throw new BusinessErrorException(ErrorCodes.ACCOUNT_ERROR, "绑定银行卡后不能修改！");
		}
		BeanUtils.copyProperties(info, salesMember);
		salesMember.setUpdateTime(new Date());

		salesMemberDao.save(salesMember);
	}

	/**
	 * 分销商店铺装修
	 */
	@RequestMapping(value = "/salesShop", method = RequestMethod.PUT)
	@ApiOperation(value = "分销商店铺装修信息提交", notes = "分销商店铺装修")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void editSalesShop(@ApiParam(value = "店铺装修信息", required = true) @Valid @RequestBody SalesShopInfo info) {

		SalesShop shopInfo = salesShopDao.findByMemberId(getMemberId());

		Date now = new Date();
		if (shopInfo == null) {
			shopInfo = new SalesShop();
			shopInfo.setMemberId(getMemberId());
			shopInfo.setStatus(1);
			shopInfo.setCreateTime(now);
		}
		BeanUtils.copyProperties(info, shopInfo);
		shopInfo.setUpdateTime(now);
		salesShopDao.save(shopInfo);
	}

	/**
	 * 获取分销商店铺信息
	 */
	@RequestMapping(value = "/salesShop", method = RequestMethod.GET)
	@ApiOperation(value = "获取分销商店铺信息", notes = "获取分销商店铺信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object getSalesShop() {

		return salesShopDao.findOne(getMemberId());

	}

	/**
	 * 获取分销商订单信息
	 */
	@RequestMapping(value = "/distOrders", method = RequestMethod.GET)
	@ApiOperation(value = "获取分销商订单信息", notes = "获取分销商订单信息(订单状态：0(已取消)10(默认):未付款;20:已付款;30:已收货;40:已评价;50:补填订单;99:全部)"
	, response = SalesShopOrdersInfo.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "status", value = "订单状态", required = false, dataType = "int", paramType = "query") })
	public Object getSalesShopOrders(@ApiIgnore @RequestParam int status) {
		
		List<Object> tempSalesShopOrders = salesOrderInfoDao.findSalesOrderInfo(getMemberId(), status);
		
		List<SalesShopOrdersInfo> rstList = new ArrayList<SalesShopOrdersInfo>();
		
		for (int i = 0; i < tempSalesShopOrders.size(); i++) {
			Object[] objArray = (Object[]) tempSalesShopOrders.get(i);
			SalesShopOrdersInfo entity = new SalesShopOrdersInfo();
			entity.setOrderId((Integer) objArray[0]);
			entity.setOrderTime((Date) objArray[1]);
			entity.setOrderPrice((BigDecimal) objArray[2]);
			entity.setSaleAmount((BigDecimal) objArray[3]);
			
			// 查询相关联的订单商品
			List<OrderGood> orderGoods = orderGoodDao.findByOrderId(entity.getOrderId());
			
			entity.setOrderGoods(orderGoods);
			
			rstList.add(entity);
		}
		
		return rstList;
	}
}

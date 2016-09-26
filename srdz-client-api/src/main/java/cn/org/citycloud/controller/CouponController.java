package cn.org.citycloud.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.MemberCouponInfo;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.Activity;
import cn.org.citycloud.entity.MemberCoupon;
import cn.org.citycloud.entity.PlatformCoupon;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.ActivityDao;
import cn.org.citycloud.repository.MemberCouponDao;
import cn.org.citycloud.repository.PlatformCouponDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 我的优惠劵控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/accounts/coupons")
@Api(tags = "优惠劵", position = 8, value = "/accounts/coupons", description = "我的优惠劵模块", consumes = "application/json")
public class CouponController extends BaseController {

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private PlatformCouponDao platformCouponDao;

	@Autowired
	private MemberCouponDao memberCouponDao;

	/**
	 * 我的优惠劵列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取我的优惠劵列表", notes = "获取我的优惠劵列表", response = MemberCoupon.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "couponStatus", value = "优惠券状态(99 全部   10 未使用   20 已使用)", required = false, dataType = "int", paramType = "query")})
	public Object getMemberCoupons(@RequestParam(value = "couponStatus", required = false, defaultValue = "99")  int couponStatus) {
		List<MemberCoupon> list = null;
		
		if(couponStatus == 99){
			 list = memberCouponDao.findByMemberIdOrderByCreateDateDesc(getMemberId());
		}else{
			 list = memberCouponDao.findByMemberIdAndCouponStatusOrderByCreateDateDesc(getMemberId(), couponStatus);
		}
		return list;
	}

	/**
	 * 领取优惠劵
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "领取优惠劵", notes = "领取优惠劵", response = MemberCoupon.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object obtainMemberCoupons(@Valid @RequestBody MemberCouponInfo couponInfo) throws BusinessErrorException {

		int couponId = couponInfo.getCouponId();

		// 平台优惠劵
		PlatformCoupon platformCoupon = platformCouponDao.findOne(couponId);

		// 验证平台优惠劵信息
		if (platformCoupon == null) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵活动已经下线或者结束了");
		}
		
		// 优惠劵状态
		if(20 == platformCoupon.getCouponStatus()) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵活动已经停发了");
		}
		
		// 已经发出
		if(platformCoupon.getSendCount() >= platformCoupon.getCouponNumber()) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵活动已经被领完了");
		}
		
		
		// 验证优惠活动
		Activity activity = activityDao.findOne(platformCoupon.getActivityId());

		if (activity.getStatus() == 0) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵活动已经结束了");
		}
		// 活动时间
		Date now = new Date();

		if (activity.getStartTime().getTime() > now.getTime() || now.getTime() > activity.getEndTime().getTime()) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵活动时间已经结束了");
		}
		
		// 验证会员已经领取的优惠劵 每人限领
		long hasObtain = memberCouponDao.countByMemberIdAndCouponId(getMemberId(), couponId);
		
		if(hasObtain - platformCoupon.getLimitGet() >= 0) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵您已经领完了");
		}
		
		MemberCoupon memberCoupon = new MemberCoupon();
		BeanUtils.copyProperties(platformCoupon, memberCoupon);
		memberCoupon.setCouponId(couponId);
		memberCoupon.setMemberId(getMemberId());
		memberCoupon.setCouponStatus(10);
		memberCoupon.setCreateDate(now);
		memberCoupon.setUpdateDate(now);
		
		return memberCouponDao.save(memberCoupon);
	}
}

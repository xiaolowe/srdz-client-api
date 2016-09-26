package cn.org.citycloud.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.MemberCoupon;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.ActivityDao;
import cn.org.citycloud.repository.MemberCouponDao;
import cn.org.citycloud.repository.PlatformCouponDao;

/**
 * 优惠劵信息Service
 * 
 * @author lanbo
 *
 */
@Component
@Transactional
public class CouponService {
	
	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private PlatformCouponDao platformCouponDao;

	@Autowired
	private MemberCouponDao memberCouponDao;
	
	/**
	 * 验证用户优惠劵是否有效
	 * @throws BusinessErrorException 
	 * 
	 */
	public MemberCoupon validMemberCoupon(int couponId) throws BusinessErrorException {
		
		MemberCoupon memberCoupon = memberCouponDao.findOne(couponId);
		
		// 验证平台优惠劵信息
		if (memberCoupon == null) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵信息不存在");
		}
		
		// 优惠劵状态
		if(20 == memberCoupon.getCouponStatus()) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵已经使用过了");
		}
		
		// 有效时间
		Date now = new Date();

		if (memberCoupon.getEffectiveTime().getTime() > now.getTime() || now.getTime() > memberCoupon.getExpirationTime().getTime()) {
			throw new BusinessErrorException(ErrorCodes.COUPON_ERROR, "此优惠劵已经失效了");
		}
		
		return memberCoupon;
	}
}

package cn.org.citycloud.bean;

import javax.validation.constraints.Min;

public class MemberCouponInfo {

	@Min(1)
	private int couponId;

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	
	
}

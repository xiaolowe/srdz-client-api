package cn.org.citycloud.bean;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * 预生成订单Bean
 * 
 * @author lanbo
 *
 */
public class PreOrder {

	// 地址ID
	@Min(1)
	private int memberAddrId;

	@Size(min = 1)
	private List<ShopOrderGoods> orderGoodsList;

	// 优惠劵ID
	private int couponId = 0;

	public int getMemberAddrId() {
		return memberAddrId;
	}

	public void setMemberAddrId(int memberAddrId) {
		this.memberAddrId = memberAddrId;
	}

	public List<ShopOrderGoods> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<ShopOrderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

}

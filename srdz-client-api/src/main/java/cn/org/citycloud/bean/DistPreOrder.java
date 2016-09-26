package cn.org.citycloud.bean;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 预生成订单Bean
 * 
 * @author lanbo
 *
 */
public class DistPreOrder {

	@NotEmpty
	private String contactsName;

	@NotEmpty
	private String contactsPhone;

	@NotEmpty
	private String contactsAddress;

	@Min(1)
	private int regionCode;

	@Length(min = 6, max = 6)
	private String postCode;

	@NotEmpty
	private String regionProvName;

	@NotEmpty
	private String regionCityName;

	@NotEmpty
	private String regionAreaName;

	@Size(min = 1)
	private List<ShopOrderGoods> orderGoodsList;

	// 优惠劵ID
	private int couponId = 0;


	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getContactsAddress() {
		return contactsAddress;
	}

	public void setContactsAddress(String contactsAddress) {
		this.contactsAddress = contactsAddress;
	}

	public int getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getRegionProvName() {
		return regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public String getRegionCityName() {
		return regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public String getRegionAreaName() {
		return regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
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

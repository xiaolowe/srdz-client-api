package cn.org.citycloud.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class MemberAddrInfo {
	
	private int memberAddrId;

	@NotEmpty(message = "联系人不能为空")
	private String contactsName;

	@NotEmpty(message = "联系电话不能为空")
	@Pattern(regexp = "(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}", message = "手机号码格式不正确，请重新输入")
	private String contactsPhone;

	@NotEmpty(message = "收货地址不能为空")
	private String contactsAddress;

	@Length(min = 6, max = 6, message = "邮编格式输入错误")
	private String postCode;

	@Min(value = 100000, message = "省选择错误")
	private int regionProv;

	@Min(value = 100000, message = "市选择错误")
	private int regionCity;

	@Min(value = 100000, message = "地区选择错误")
	private int regionArea;
	
	public int getMemberAddrId() {
		return memberAddrId;
	}

	public void setMemberAddrId(int memberAddrId) {
		this.memberAddrId = memberAddrId;
	}

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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public int getRegionProv() {
		return regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public int getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public int getRegionArea() {
		return regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

}

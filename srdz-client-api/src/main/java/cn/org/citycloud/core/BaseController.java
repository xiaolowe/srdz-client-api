package cn.org.citycloud.core;

/**
 * 控制器基类
 * 
 * @author lanbo
 *
 */
public class BaseController {

	/**
	 * 会员Id
	 */
	private int memberId;

	/**
	 * 手机号
	 */
	private String memberPhone;

	private String accessToken;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}

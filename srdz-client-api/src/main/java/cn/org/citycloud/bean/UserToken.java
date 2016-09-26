package cn.org.citycloud.bean;

import java.io.Serializable;

/**
 * Token Bean
 * 
 * @author lanbo
 *
 */
public class UserToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Token
	 */
	private String token;

	/**
	 * 会员Id
	 */
	private int userId;

	/**
	 * 手机号
	 */
	private String userPhone;

	/**
	 * accessToken
	 */
	private String accessToken;

	/**
	 * 多少秒后过期
	 */
	private long expiresIn;

	/**
	 * 创建时间戳
	 */
	private long createTs;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public long getCreateTs() {
		return createTs;
	}

	public void setCreateTs(long createTs) {
		this.createTs = createTs;
	}

}

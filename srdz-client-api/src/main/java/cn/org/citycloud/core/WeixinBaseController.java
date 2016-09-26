package cn.org.citycloud.core;

/**
 * 微信控制器基类
 * 
 * @author lanbo
 *
 */
public class WeixinBaseController {

	/**
	 * Token
	 */
	private String token;

	/**
	 * 会员Id
	 */
	private int memberId;

	/**
	 * 门店Id
	 */
	private int storeId;

	/**
	 * 微信号
	 */
	private String openId;

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

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

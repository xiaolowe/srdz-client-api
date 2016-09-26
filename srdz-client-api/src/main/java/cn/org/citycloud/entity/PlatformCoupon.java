package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the platform_coupon database table.
 * 
 */
@Entity
@Table(name="platform_coupon")
@NamedQuery(name="PlatformCoupon.findAll", query="SELECT p FROM PlatformCoupon p")
public class PlatformCoupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="coupon_id", unique=true, nullable=false)
	private int couponId;

	@Column(name="activity_id")
	private int activityId;

	@Column(name="coupon_condition", precision=10, scale=2)
	private BigDecimal couponCondition;

	@Column(name="coupon_money", precision=10, scale=2)
	private BigDecimal couponMoney;

	@Column(name="coupon_name", length=50)
	private String couponName;

	@Column(name="coupon_number")
	private int couponNumber;

	@Column(name="coupon_status")
	private int couponStatus;

	@Column(name="coupon_symbol", length=10)
	private String couponSymbol;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="effective_time")
	private Date effectiveTime;

	@Column(name="everyday_get")
	private int everydayGet;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiration_time")
	private Date expirationTime;

	@Column(name="limit_get")
	private int limitGet;

	@Column(name="send_count")
	private int sendCount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

	@Column(name="userd_count")
	private int userdCount;

	public PlatformCoupon() {
	}

	public int getCouponId() {
		return this.couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public int getActivityId() {
		return this.activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public BigDecimal getCouponCondition() {
		return this.couponCondition;
	}

	public void setCouponCondition(BigDecimal couponCondition) {
		this.couponCondition = couponCondition;
	}

	public BigDecimal getCouponMoney() {
		return this.couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getCouponName() {
		return this.couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getCouponNumber() {
		return this.couponNumber;
	}

	public void setCouponNumber(int couponNumber) {
		this.couponNumber = couponNumber;
	}

	public int getCouponStatus() {
		return this.couponStatus;
	}

	public void setCouponStatus(int couponStatus) {
		this.couponStatus = couponStatus;
	}

	public String getCouponSymbol() {
		return this.couponSymbol;
	}

	public void setCouponSymbol(String couponSymbol) {
		this.couponSymbol = couponSymbol;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEffectiveTime() {
		return this.effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public int getEverydayGet() {
		return this.everydayGet;
	}

	public void setEverydayGet(int everydayGet) {
		this.everydayGet = everydayGet;
	}

	public Date getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public int getLimitGet() {
		return this.limitGet;
	}

	public void setLimitGet(int limitGet) {
		this.limitGet = limitGet;
	}

	public int getSendCount() {
		return this.sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getUserdCount() {
		return this.userdCount;
	}

	public void setUserdCount(int userdCount) {
		this.userdCount = userdCount;
	}

}
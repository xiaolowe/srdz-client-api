package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the member_coupon database table.
 * 
 */
@Entity
@Table(name="member_coupon")
@NamedQuery(name="MemberCoupon.findAll", query="SELECT m FROM MemberCoupon m")
public class MemberCoupon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_coupon_id", unique=true, nullable=false)
	private int memberCouponId;

	@Column(name="coupon_id")
	private int couponId;

	@Column(name="coupon_condition", precision=10, scale=2)
	private BigDecimal couponCondition;

	@Column(name="coupon_money", precision=10, scale=2)
	private BigDecimal couponMoney;

	@Column(name="coupon_name", length=50)
	private String couponName;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiration_time")
	private Date expirationTime;

	@Column(name="member_id")
	private int memberId;

	@Column(name="order_id")
	private int orderId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

	public MemberCoupon() {
	}

	public int getCouponId() {
		return this.couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
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

	public Date getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public int getMemberCouponId() {
		return this.memberCouponId;
	}

	public void setMemberCouponId(int memberCouponId) {
		this.memberCouponId = memberCouponId;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
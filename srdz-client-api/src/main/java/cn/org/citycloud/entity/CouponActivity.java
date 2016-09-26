package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the coupon_activity database table.
 * 
 */
@Entity
@Table(name="coupon_activity")
@NamedQuery(name="CouponActivity.findAll", query="SELECT c FROM CouponActivity c")
public class CouponActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="coupon_activity_id", unique=true, nullable=false)
	private int couponActivityId;

	@Column(name="activity_id")
	private int activityId;

	@Column(name="coupon_id")
	private int couponId;

	public CouponActivity() {
	}

	public int getCouponActivityId() {
		return this.couponActivityId;
	}

	public void setCouponActivityId(int couponActivityId) {
		this.couponActivityId = couponActivityId;
	}

	public int getActivityId() {
		return this.activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getCouponId() {
		return this.couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

}
package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the pay_info database table.
 * 
 */
@Entity
@Table(name="pay_info")
@NamedQuery(name="PayInfo.findAll", query="SELECT p FROM PayInfo p")
public class PayInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pay_id", unique=true, nullable=false)
	private int payId;

	@Column(name="company_name", length=50)
	private String companyName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="open_id", length=30)
	private String openId;

	@Column(name="pay_member_id")
	private int payMemberId;

	@Column(name="pay_member_name", length=30)
	private String payMemberName;

	@Column(name="pay_member_phone", length=11)
	private String payMemberPhone;

	@Column(name="pay_money", precision=10, scale=2)
	private BigDecimal payMoney;

	@Column(name="pay_status")
	private int payStatus;

	@Column(name="pay_style", length=10)
	private String payStyle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_time")
	private Date payTime;

	@Column(length=1000)
	private String remark;

	@Column(name="sales_member_id")
	private int salesMemberId;

	@Column(name="supplier_id", nullable=false)
	private int supplierId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

	@Column(name="wechat_pay_sn", length=32)
	private String wechatPaySn;

	public PayInfo() {
	}

	public int getPayId() {
		return this.payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getPayMemberId() {
		return this.payMemberId;
	}

	public void setPayMemberId(int payMemberId) {
		this.payMemberId = payMemberId;
	}

	public String getPayMemberName() {
		return this.payMemberName;
	}

	public void setPayMemberName(String payMemberName) {
		this.payMemberName = payMemberName;
	}

	public String getPayMemberPhone() {
		return this.payMemberPhone;
	}

	public void setPayMemberPhone(String payMemberPhone) {
		this.payMemberPhone = payMemberPhone;
	}

	public BigDecimal getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public int getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStyle() {
		return this.payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSalesMemberId() {
		return this.salesMemberId;
	}

	public void setSalesMemberId(int salesMemberId) {
		this.salesMemberId = salesMemberId;
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getWechatPaySn() {
		return this.wechatPaySn;
	}

	public void setWechatPaySn(String wechatPaySn) {
		this.wechatPaySn = wechatPaySn;
	}

}
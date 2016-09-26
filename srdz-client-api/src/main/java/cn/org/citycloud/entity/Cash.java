package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the cash database table.
 * 
 */
@Entity
@Table(name="cash")
@NamedQuery(name="Cash.findAll", query="SELECT c FROM Cash c")
public class Cash implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cash_id", unique=true, nullable=false)
	private int cashId;

	@Column(name="account_surplus", precision=10, scale=2)
	private BigDecimal accountSurplus;
	
	@Column(name="order_id")
	private int orderId;

	@Column(name="account_type")
	private byte accountType;

	@Column(name="apply_money", precision=10, scale=2)
	private BigDecimal applyMoney;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="apply_time")
	private Date applyTime;

	@Column(name="apply_type")
	private int applyType;

	@Column(name="apply_user_id")
	private int applyUserId;

	@Column(name="apply_user_name", length=20)
	private String applyUserName;

	@Column(name="bank_acc_name", length=50)
	private String bankAccName;

	@Column(name="bank_acc_number", length=50)
	private String bankAccNumber;

	@Column(name="bank_name", length=50)
	private String bankName;

	@Column(name="bank_owner", length=50)
	private String bankOwner;

	@Column(name="company_name", length=50)
	private String companyName;

	@Column(name="confirm_status")
	private int confirmStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="confirm_time")
	private Date confirmTime;

	@Column(name="confirm_user_id")
	private int confirmUserId;

	@Column(name="confirm_user_name", length=30)
	private String confirmUserName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="paying_certificate", length=50)
	private String payingCertificate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="paying_time")
	private Date payingTime;

	@Column(name="paying_user_id")
	private int payingUserId;

	@Column(name="paying_user_name", length=50)
	private String payingUserName;

	@Column(name="sales_member_id")
	private int salesMemberId;

	@Column(name="service_center_id")
	private int serviceCenterId;

	@Column(name="supplier_id")
	private int supplierId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public Cash() {
	}

	public int getCashId() {
		return this.cashId;
	}

	public void setCashId(int cashId) {
		this.cashId = cashId;
	}

	public BigDecimal getAccountSurplus() {
		return this.accountSurplus;
	}

	public void setAccountSurplus(BigDecimal accountSurplus) {
		this.accountSurplus = accountSurplus;
	}

	public byte getAccountType() {
		return this.accountType;
	}

	public void setAccountType(byte accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getApplyMoney() {
		return this.applyMoney;
	}

	public void setApplyMoney(BigDecimal applyMoney) {
		this.applyMoney = applyMoney;
	}

	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public int getApplyType() {
		return this.applyType;
	}

	public void setApplyType(int applyType) {
		this.applyType = applyType;
	}

	public int getApplyUserId() {
		return this.applyUserId;
	}

	public void setApplyUserId(int applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyUserName() {
		return this.applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getBankAccName() {
		return this.bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getBankAccNumber() {
		return this.bankAccNumber;
	}

	public void setBankAccNumber(String bankAccNumber) {
		this.bankAccNumber = bankAccNumber;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankOwner() {
		return this.bankOwner;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getConfirmStatus() {
		return this.confirmStatus;
	}

	public void setConfirmStatus(int confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public int getConfirmUserId() {
		return this.confirmUserId;
	}

	public void setConfirmUserId(int confirmUserId) {
		this.confirmUserId = confirmUserId;
	}

	public String getConfirmUserName() {
		return this.confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPayingCertificate() {
		return this.payingCertificate;
	}

	public void setPayingCertificate(String payingCertificate) {
		this.payingCertificate = payingCertificate;
	}

	public Date getPayingTime() {
		return this.payingTime;
	}

	public void setPayingTime(Date payingTime) {
		this.payingTime = payingTime;
	}

	public int getPayingUserId() {
		return this.payingUserId;
	}

	public void setPayingUserId(int payingUserId) {
		this.payingUserId = payingUserId;
	}

	public String getPayingUserName() {
		return this.payingUserName;
	}

	public void setPayingUserName(String payingUserName) {
		this.payingUserName = payingUserName;
	}

	public int getSalesMemberId() {
		return this.salesMemberId;
	}

	public void setSalesMemberId(int salesMemberId) {
		this.salesMemberId = salesMemberId;
	}

	public int getServiceCenterId() {
		return this.serviceCenterId;
	}

	public void setServiceCenterId(int serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
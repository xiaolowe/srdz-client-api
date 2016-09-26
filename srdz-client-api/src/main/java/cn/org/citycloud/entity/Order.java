package cn.org.citycloud.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id", unique = true, nullable = false)
	private int orderId;

	@Column(name = "back_order_cause", length = 50)
	private String backOrderCause;

	@Column(name = "back_order_status")
	private int backOrderStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirm_date")
	private Date confirmDate;

	@Column(name = "confirm_id")
	private int confirmId;

	@Column(name = "confirm_name", length = 50)
	private String confirmName;

	@Column(name = "contact_address", length = 50)
	private String contactAddress;

	@Column(name = "contact_name", length = 50)
	private String contactName;

	@Column(name = "contact_phone", length = 50)
	private String contactPhone;

	@Column(name = "coupon_price", precision = 10, scale = 2)
	private BigDecimal couponPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	private int exception;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "exception_time")
	private Date exceptionTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finish_time")
	private Date finishTime;

	@Column(name = "flow_company_name", length = 50)
	private String flowCompanyName;

	@Column(name = "flow_number")
	private BigInteger flowNumber;

	@Column(name = "flow_price", precision = 10, scale = 2)
	private BigDecimal flowPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "get_goods_time")
	private Date getGoodsTime;

	@Column(name = "init_price", nullable = false, precision = 10, scale = 2)
	private BigDecimal initPrice;

	@Column(name = "is_flow")
	private int isFlow;

	@Column(name = "member_addr_id")
	private int memberAddrId;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "member_name", length = 50)
	private String memberName;

	@Column(name = "member_phone", length = 50)
	private String memberPhone;

	@Column(name = "order_cause", length = 100)
	private String orderCause;

	@Column(name = "order_price", nullable = false, precision = 10, scale = 2)
	private BigDecimal orderPrice;

	@Column(name = "order_status", nullable = false)
	private int orderStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_time", nullable = false)
	private Date orderTime;

	@Column(name = "order_type")
	private int orderType;

	@Column(name = "pay_code", length = 10)
	private String payCode;

	@Column(name = "pay_id")
	private int payId;

	@Column(name = "pay_price", precision = 10, scale = 2)
	private BigDecimal payPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pay_time")
	private Date payTime;

	@Column(name = "post_code", length = 6)
	private String postCode;

	@Column(name = "pre_pay", length = 100)
	private String prePay;

	@Column(name = "region_area")
	private int regionArea;

	@Column(name = "region_area_name", length = 50)
	private String regionAreaName;

	@Column(name = "region_city")
	private int regionCity;

	@Column(name = "region_city_name", length = 50)
	private String regionCityName;

	@Column(name = "region_prov")
	private int regionProv;

	@Column(name = "region_prov_name", length = 50)
	private String regionProvName;

	@Column(length = 1000)
	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "send_goods_time")
	private Date sendGoodsTime;

	@Column(name = "supplier_amount", precision = 10, scale = 2)
	private BigDecimal supplierAmount;

	@Column(name = "supplier_id")
	private int supplierId;

	@Column(name = "supplier_name", length = 50)
	private String supplierName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	// 关联订单商品
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	@OrderBy("orderGoodsId ASC")
	private Set<OrderGood> orderGoods;

	// 关联评价
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	@OrderBy("gevalId ASC")
	private Set<EvaluateGood> evaluateGoods;

	public Order() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getBackOrderCause() {
		return this.backOrderCause;
	}

	public void setBackOrderCause(String backOrderCause) {
		this.backOrderCause = backOrderCause;
	}

	public int getBackOrderStatus() {
		return this.backOrderStatus;
	}

	public void setBackOrderStatus(int backOrderStatus) {
		this.backOrderStatus = backOrderStatus;
	}

	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public int getConfirmId() {
		return this.confirmId;
	}

	public void setConfirmId(int confirmId) {
		this.confirmId = confirmId;
	}

	public String getConfirmName() {
		return this.confirmName;
	}

	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public BigDecimal getCouponPrice() {
		return this.couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getException() {
		return this.exception;
	}

	public void setException(int exception) {
		this.exception = exception;
	}

	public Date getExceptionTime() {
		return this.exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFlowCompanyName() {
		return this.flowCompanyName;
	}

	public void setFlowCompanyName(String flowCompanyName) {
		this.flowCompanyName = flowCompanyName;
	}

	public BigInteger getFlowNumber() {
		return this.flowNumber;
	}

	public void setFlowNumber(BigInteger flowNumber) {
		this.flowNumber = flowNumber;
	}

	public BigDecimal getFlowPrice() {
		return this.flowPrice;
	}

	public void setFlowPrice(BigDecimal flowPrice) {
		this.flowPrice = flowPrice;
	}

	public Date getGetGoodsTime() {
		return this.getGoodsTime;
	}

	public void setGetGoodsTime(Date getGoodsTime) {
		this.getGoodsTime = getGoodsTime;
	}

	public BigDecimal getInitPrice() {
		return this.initPrice;
	}

	public void setInitPrice(BigDecimal initPrice) {
		this.initPrice = initPrice;
	}

	public int getIsFlow() {
		return this.isFlow;
	}

	public void setIsFlow(int isFlow) {
		this.isFlow = isFlow;
	}

	public int getMemberAddrId() {
		return this.memberAddrId;
	}

	public void setMemberAddrId(int memberAddrId) {
		this.memberAddrId = memberAddrId;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getOrderCause() {
		return this.orderCause;
	}

	public void setOrderCause(String orderCause) {
		this.orderCause = orderCause;
	}

	public BigDecimal getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public int getOrderType() {
		return this.orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getPayCode() {
		return this.payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public int getPayId() {
		return this.payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public BigDecimal getPayPrice() {
		return this.payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPrePay() {
		return this.prePay;
	}

	public void setPrePay(String prePay) {
		this.prePay = prePay;
	}

	public int getRegionArea() {
		return this.regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

	public String getRegionAreaName() {
		return this.regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
	}

	public int getRegionCity() {
		return this.regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionCityName() {
		return this.regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public int getRegionProv() {
		return this.regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public String getRegionProvName() {
		return this.regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSendGoodsTime() {
		return this.sendGoodsTime;
	}

	public void setSendGoodsTime(Date sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	public BigDecimal getSupplierAmount() {
		return this.supplierAmount;
	}

	public void setSupplierAmount(BigDecimal supplierAmount) {
		this.supplierAmount = supplierAmount;
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<OrderGood> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(Set<OrderGood> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public Set<EvaluateGood> getEvaluateGoods() {
		return evaluateGoods;
	}

	public void setEvaluateGoods(Set<EvaluateGood> evaluateGoods) {
		this.evaluateGoods = evaluateGoods;
	}

}
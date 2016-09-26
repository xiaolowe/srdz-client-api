package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the sales_order_info database table.
 * 
 */
@Entity
@Table(name="sales_order_info")
@NamedQuery(name="SalesOrderInfo.findAll", query="SELECT s FROM SalesOrderInfo s")
public class SalesOrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id", unique=true, nullable=false)
	private int orderId;

	@Column(name="member_id", nullable=false)
	private int memberId;

	@Column(name="platform_amount", precision=10, scale=2)
	private BigDecimal platformAmount;

	@Column(name="platform_rates", precision=10, scale=2)
	private BigDecimal platformRates;

	@Column(name="sale_amount", precision=10, scale=2)
	private BigDecimal saleAmount;

	@Column(name="sale_rates", precision=10, scale=2)
	private BigDecimal saleRates;

	@Column(name="sales_member_name", length=50)
	private String salesMemberName;

	@Column(name="service_center_amount", precision=10, scale=2)
	private BigDecimal serviceCenterAmount;

	@Column(name="service_center_rates", precision=10, scale=2)
	private BigDecimal serviceCenterRates;

	@Column(name="wechat_sales_member_id")
	private int wechatSalesMemberId;

	public SalesOrderInfo() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getPlatformAmount() {
		return this.platformAmount;
	}

	public void setPlatformAmount(BigDecimal platformAmount) {
		this.platformAmount = platformAmount;
	}

	public BigDecimal getPlatformRates() {
		return this.platformRates;
	}

	public void setPlatformRates(BigDecimal platformRates) {
		this.platformRates = platformRates;
	}

	public BigDecimal getSaleAmount() {
		return this.saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getSaleRates() {
		return this.saleRates;
	}

	public void setSaleRates(BigDecimal saleRates) {
		this.saleRates = saleRates;
	}

	public String getSalesMemberName() {
		return this.salesMemberName;
	}

	public void setSalesMemberName(String salesMemberName) {
		this.salesMemberName = salesMemberName;
	}

	public BigDecimal getServiceCenterAmount() {
		return this.serviceCenterAmount;
	}

	public void setServiceCenterAmount(BigDecimal serviceCenterAmount) {
		this.serviceCenterAmount = serviceCenterAmount;
	}

	public BigDecimal getServiceCenterRates() {
		return this.serviceCenterRates;
	}

	public void setServiceCenterRates(BigDecimal serviceCenterRates) {
		this.serviceCenterRates = serviceCenterRates;
	}

	public int getWechatSalesMemberId() {
		return this.wechatSalesMemberId;
	}

	public void setWechatSalesMemberId(int wechatSalesMemberId) {
		this.wechatSalesMemberId = wechatSalesMemberId;
	}

}
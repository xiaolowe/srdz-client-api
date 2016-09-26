package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the order_info database table.
 * 
 */
@Entity
@Table(name="order_info")
@NamedQuery(name="OrderInfo.findAll", query="SELECT o FROM OrderInfo o")
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id", unique=true, nullable=false)
	private int orderId;

	@Column(name="platform_amount", precision=10, scale=2)
	private BigDecimal platformAmount;

	@Column(name="platform_rates", precision=10, scale=2)
	private BigDecimal platformRates;

	@Column(name="service_center_amount", precision=10, scale=2)
	private BigDecimal serviceCenterAmount;

	@Column(name="service_center_rates", precision=10, scale=2)
	private BigDecimal serviceCenterRates;

	@Column(name="supplier_id", nullable=false)
	private int supplierId;

	@Column(name="supplier_name", length=50)
	private String supplierName;

	public OrderInfo() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

}
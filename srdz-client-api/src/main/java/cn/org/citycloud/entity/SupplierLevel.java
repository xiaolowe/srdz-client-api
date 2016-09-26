package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the supplier_level database table.
 * 
 */
@Entity
@Table(name="supplier_level")
@NamedQuery(name="SupplierLevel.findAll", query="SELECT s FROM SupplierLevel s")
public class SupplierLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="supplier_level_id", unique=true, nullable=false)
	private int supplierLevelId;

	@Column(name="platform_rates", precision=10, scale=2)
	private BigDecimal platformRates;

	@Column(name="supplier_level_name", length=50)
	private String supplierLevelName;

	public SupplierLevel() {
	}

	public int getSupplierLevelId() {
		return this.supplierLevelId;
	}

	public void setSupplierLevelId(int supplierLevelId) {
		this.supplierLevelId = supplierLevelId;
	}

	public BigDecimal getPlatformRates() {
		return this.platformRates;
	}

	public void setPlatformRates(BigDecimal platformRates) {
		this.platformRates = platformRates;
	}

	public String getSupplierLevelName() {
		return this.supplierLevelName;
	}

	public void setSupplierLevelName(String supplierLevelName) {
		this.supplierLevelName = supplierLevelName;
	}

}
package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the service_center_level database table.
 * 
 */
@Entity
@Table(name="service_center_level")
@NamedQuery(name="ServiceCenterLevel.findAll", query="SELECT s FROM ServiceCenterLevel s")
public class ServiceCenterLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="service_center_level_id", unique=true, nullable=false)
	private int serviceCenterLevelId;

	@Column(name="service_center_level_name", length=50)
	private String serviceCenterLevelName;

	@Column(name="service_center_rates", precision=10, scale=2)
	private BigDecimal serviceCenterRates;

	public ServiceCenterLevel() {
	}

	public int getServiceCenterLevelId() {
		return this.serviceCenterLevelId;
	}

	public void setServiceCenterLevelId(int serviceCenterLevelId) {
		this.serviceCenterLevelId = serviceCenterLevelId;
	}

	public String getServiceCenterLevelName() {
		return this.serviceCenterLevelName;
	}

	public void setServiceCenterLevelName(String serviceCenterLevelName) {
		this.serviceCenterLevelName = serviceCenterLevelName;
	}

	public BigDecimal getServiceCenterRates() {
		return this.serviceCenterRates;
	}

	public void setServiceCenterRates(BigDecimal serviceCenterRates) {
		this.serviceCenterRates = serviceCenterRates;
	}

}
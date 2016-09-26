package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the flow_city database table.
 * 
 */
@Entity
@Table(name="flow_city")
@NamedQuery(name="FlowCity.findAll", query="SELECT f FROM FlowCity f")
public class FlowCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="flow_city_id", unique=true, nullable=false)
	private int flowCityId;

	@Column(name="flow_city_code")
	private int flowCityCode;

	@Column(name="flow_city_name", length=50)
	private String flowCityName;

	@Column(name="flow_info_id")
	private int flowInfoId;

	public FlowCity() {
	}

	public int getFlowCityId() {
		return this.flowCityId;
	}

	public void setFlowCityId(int flowCityId) {
		this.flowCityId = flowCityId;
	}

	public int getFlowCityCode() {
		return this.flowCityCode;
	}

	public void setFlowCityCode(int flowCityCode) {
		this.flowCityCode = flowCityCode;
	}

	public String getFlowCityName() {
		return this.flowCityName;
	}

	public void setFlowCityName(String flowCityName) {
		this.flowCityName = flowCityName;
	}

	public int getFlowInfoId() {
		return this.flowInfoId;
	}

	public void setFlowInfoId(int flowInfoId) {
		this.flowInfoId = flowInfoId;
	}

}
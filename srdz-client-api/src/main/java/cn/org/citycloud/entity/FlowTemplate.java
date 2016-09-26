package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the flow_template database table.
 * 
 */
@Entity
@Table(name="flow_template")
@NamedQuery(name="FlowTemplate.findAll", query="SELECT f FROM FlowTemplate f")
public class FlowTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="flow_template_id", unique=true, nullable=false)
	private int flowTemplateId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="flow_template_name", length=100)
	private String flowTemplateName;

	@Column(name="supplier_id")
	private int supplierId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="weight_piece_flag")
	private int weightPieceFlag;

	public FlowTemplate() {
	}

	public int getFlowTemplateId() {
		return this.flowTemplateId;
	}

	public void setFlowTemplateId(int flowTemplateId) {
		this.flowTemplateId = flowTemplateId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFlowTemplateName() {
		return this.flowTemplateName;
	}

	public void setFlowTemplateName(String flowTemplateName) {
		this.flowTemplateName = flowTemplateName;
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

	public int getWeightPieceFlag() {
		return this.weightPieceFlag;
	}

	public void setWeightPieceFlag(int weightPieceFlag) {
		this.weightPieceFlag = weightPieceFlag;
	}

}
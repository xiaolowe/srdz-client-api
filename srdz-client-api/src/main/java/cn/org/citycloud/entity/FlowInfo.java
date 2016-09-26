package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the flow_info database table.
 * 
 */
@Entity
@Table(name="flow_info")
@NamedQuery(name="FlowInfo.findAll", query="SELECT f FROM FlowInfo f")
public class FlowInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="flow_info_id", unique=true, nullable=false)
	private int flowInfoId;

	@Column(name="add_flow_goods", precision=10, scale=2)
	private BigDecimal addFlowGoods;

	@Column(name="add_goods_price", precision=10, scale=2)
	private BigDecimal addGoodsPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="default_flag")
	private int defaultFlag;

	@Column(name="flow_goods", precision=10, scale=2)
	private BigDecimal flowGoods;

	@Column(name="flow_price", precision=10, scale=2)
	private BigDecimal flowPrice;

	@Column(name="flow_template_id")
	private int flowTemplateId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public FlowInfo() {
	}

	public int getFlowInfoId() {
		return this.flowInfoId;
	}

	public void setFlowInfoId(int flowInfoId) {
		this.flowInfoId = flowInfoId;
	}

	public BigDecimal getAddFlowGoods() {
		return this.addFlowGoods;
	}

	public void setAddFlowGoods(BigDecimal addFlowGoods) {
		this.addFlowGoods = addFlowGoods;
	}

	public BigDecimal getAddGoodsPrice() {
		return this.addGoodsPrice;
	}

	public void setAddGoodsPrice(BigDecimal addGoodsPrice) {
		this.addGoodsPrice = addGoodsPrice;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDefaultFlag() {
		return this.defaultFlag;
	}

	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public BigDecimal getFlowGoods() {
		return this.flowGoods;
	}

	public void setFlowGoods(BigDecimal flowGoods) {
		this.flowGoods = flowGoods;
	}

	public BigDecimal getFlowPrice() {
		return this.flowPrice;
	}

	public void setFlowPrice(BigDecimal flowPrice) {
		this.flowPrice = flowPrice;
	}

	public int getFlowTemplateId() {
		return this.flowTemplateId;
	}

	public void setFlowTemplateId(int flowTemplateId) {
		this.flowTemplateId = flowTemplateId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
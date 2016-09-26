package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the goods_common database table.
 * 
 */
@Entity
@Table(name="goods_common")
@NamedQuery(name="GoodsCommon.findAll", query="SELECT g FROM GoodsCommon g")
public class GoodsCommon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="goods_common_id", unique=true, nullable=false)
	private int goodsCommonId;

	@Column(name="brand_id", nullable=false)
	private int brandId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="goods_addtime")
	private Date goodsAddtime;

	@Column(name="goods_class_id", nullable=false)
	private int goodsClassId;

	@Column(name="goods_class_name", nullable=false, length=50)
	private String goodsClassName;

	@Column(name="goods_class_three_id", nullable=false)
	private int goodsClassThreeId;

	@Column(name="goods_class_three_name")
	private int goodsClassThreeName;

	@Column(name="goods_class_two_id", nullable=false)
	private int goodsClassTwoId;

	@Column(name="goods_class_two_name", nullable=false, length=50)
	private String goodsClassTwoName;

	@Lob
	@Column(name="goods_desc")
	private String goodsDesc;

	@Column(name="goods_image", length=100)
	private String goodsImage;

	@Column(name="goods_name", nullable=false, length=50)
	private String goodsName;

	@Column(name="goods_status", nullable=false)
	private byte goodsStatus;

	@Column(length=50)
	private String standard;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public GoodsCommon() {
	}

	public int getGoodsCommonId() {
		return this.goodsCommonId;
	}

	public void setGoodsCommonId(int goodsCommonId) {
		this.goodsCommonId = goodsCommonId;
	}

	public int getBrandId() {
		return this.brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getGoodsAddtime() {
		return this.goodsAddtime;
	}

	public void setGoodsAddtime(Date goodsAddtime) {
		this.goodsAddtime = goodsAddtime;
	}

	public int getGoodsClassId() {
		return this.goodsClassId;
	}

	public void setGoodsClassId(int goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

	public String getGoodsClassName() {
		return this.goodsClassName;
	}

	public void setGoodsClassName(String goodsClassName) {
		this.goodsClassName = goodsClassName;
	}

	public int getGoodsClassThreeId() {
		return this.goodsClassThreeId;
	}

	public void setGoodsClassThreeId(int goodsClassThreeId) {
		this.goodsClassThreeId = goodsClassThreeId;
	}

	public int getGoodsClassThreeName() {
		return this.goodsClassThreeName;
	}

	public void setGoodsClassThreeName(int goodsClassThreeName) {
		this.goodsClassThreeName = goodsClassThreeName;
	}

	public int getGoodsClassTwoId() {
		return this.goodsClassTwoId;
	}

	public void setGoodsClassTwoId(int goodsClassTwoId) {
		this.goodsClassTwoId = goodsClassTwoId;
	}

	public String getGoodsClassTwoName() {
		return this.goodsClassTwoName;
	}

	public void setGoodsClassTwoName(String goodsClassTwoName) {
		this.goodsClassTwoName = goodsClassTwoName;
	}

	public String getGoodsDesc() {
		return this.goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getGoodsImage() {
		return this.goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public byte getGoodsStatus() {
		return this.goodsStatus;
	}

	public void setGoodsStatus(byte goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
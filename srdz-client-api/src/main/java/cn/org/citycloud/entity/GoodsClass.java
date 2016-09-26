package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the goods_class database table.
 * 
 */
@Entity
@Table(name="goods_class")
@NamedQuery(name="GoodsClass.findAll", query="SELECT g FROM GoodsClass g")
public class GoodsClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="goods_class_id", unique=true, nullable=false)
	private int goodsClassId;

	@Column(name="class_image", length=200)
	private String classImage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="del_flag")
	private int delFlag;

	@Column(name="goods_class_name", nullable=false, length=100)
	private String goodsClassName;

	@Lob
	@Column(name="goods_desc")
	private String goodsDesc;

	@Column(name="parent_id", nullable=false)
	private int parentId;

	@Column(nullable=false)
	private int sort;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public GoodsClass() {
	}

	public int getGoodsClassId() {
		return this.goodsClassId;
	}

	public void setGoodsClassId(int goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

	public String getClassImage() {
		return this.classImage;
	}

	public void setClassImage(String classImage) {
		this.classImage = classImage;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getGoodsClassName() {
		return this.goodsClassName;
	}

	public void setGoodsClassName(String goodsClassName) {
		this.goodsClassName = goodsClassName;
	}

	public String getGoodsDesc() {
		return this.goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
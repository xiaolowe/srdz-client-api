package cn.org.citycloud.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="商品分类Model", description="商品分类数据Model")
public class GoodsClassInfo {

	@ApiModelProperty(value="分类ID")
	private int goodsClassId;

	@ApiModelProperty(value="分类图片")
	private String classImage;

	private Date createTime;

	@ApiModelProperty(value="分类名称")
	private String goodsClassName;

	@ApiModelProperty(value="父分类ID")
	private int parentId;

	@ApiModelProperty(value="排序")
	private int sort;

	private Date updateTime;

	@ApiModelProperty(value="子分类信息")
	private List<GoodsClassInfo> childClasses = new ArrayList<GoodsClassInfo>();

	public int getGoodsClassId() {
		return goodsClassId;
	}

	public void setGoodsClassId(int goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

	public String getClassImage() {
		return classImage;
	}

	public void setClassImage(String classImage) {
		this.classImage = classImage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGoodsClassName() {
		return goodsClassName;
	}

	public void setGoodsClassName(String goodsClassName) {
		this.goodsClassName = goodsClassName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<GoodsClassInfo> getChildClasses() {
		return childClasses;
	}

	public void setChildClasses(List<GoodsClassInfo> childClasses) {
		this.childClasses = childClasses;
	}

}

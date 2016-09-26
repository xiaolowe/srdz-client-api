package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;

public class ShopGoodsSearch extends SliceAndSort {

	@ApiModelProperty(value = "一级分类ID", required = true)
	private int goodsClassId = 0;

	public int getGoodsClassId() {
		return goodsClassId;
	}

	public void setGoodsClassId(int goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

}

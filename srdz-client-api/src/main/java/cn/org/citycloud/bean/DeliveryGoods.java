package cn.org.citycloud.bean;

import javax.validation.constraints.Min;

public class DeliveryGoods {

	@Min(1)
	private int goodsId;

	@Min(1)
	private int goodsNum;

	@Min(value = 100000, message = "市选择错误")
	private int regionCity;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public int getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

}

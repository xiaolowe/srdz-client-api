package cn.org.citycloud.bean;

import java.math.BigDecimal;

public class GoodsDeliverCost {

	private int goodsId;

	private BigDecimal deliverCost;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getDeliverCost() {
		return deliverCost;
	}

	public void setDeliverCost(BigDecimal deliverCost) {
		this.deliverCost = deliverCost;
	}

}

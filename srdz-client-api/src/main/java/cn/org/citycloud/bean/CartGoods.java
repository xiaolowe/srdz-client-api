package cn.org.citycloud.bean;

import javax.validation.constraints.Min;

/**
 * 购物车商品Bean
 * 
 * @author lanbo
 *
 */
public class CartGoods {

	@Min(1)
	private int goodsId;

	@Min(1)
	private int goodsNum;

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

}

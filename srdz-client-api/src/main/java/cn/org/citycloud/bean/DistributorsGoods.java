package cn.org.citycloud.bean;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;

public class DistributorsGoods {

	@Min(1)
	@ApiModelProperty(value="分销商品ID", required=true)
	private int goodsId;

	@Min(1)
	@ApiModelProperty(value="分销商品价格", required=true)
	private BigDecimal shopGoodsPrice;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getShopGoodsPrice() {
		return shopGoodsPrice;
	}

	public void setShopGoodsPrice(BigDecimal shopGoodsPrice) {
		this.shopGoodsPrice = shopGoodsPrice;
	}

}

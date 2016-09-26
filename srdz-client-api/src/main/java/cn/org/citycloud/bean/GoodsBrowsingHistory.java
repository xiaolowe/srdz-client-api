package cn.org.citycloud.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class GoodsBrowsingHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6522808429702436041L;

	@Min(1)
	private int goodsId;

//	@NotEmpty
	private String goodsImage;

//	@NotEmpty
	private String goodsName;

//	@NotNull
	private BigDecimal salePrice;

	private String stardard;

	private Date addDate = new Date();

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getStardard() {
		return stardard;
	}

	public void setStardard(String stardard) {
		this.stardard = stardard;
	}
}

package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the discount_goods database table.
 * 
 */
@Entity
@Table(name="discount_goods")
@NamedQuery(name="DiscountGood.findAll", query="SELECT d FROM DiscountGood d")
public class DiscountGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="goods_id", unique=true, nullable=false)
	private int goodsId;

	@Column(name="already_sale")
	private int alreadySale;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="auto_down_time")
	private Date autoDownTime;

	@Column(name="benefit_price", precision=10, scale=2)
	private BigDecimal benefitPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="goods_add_count")
	private int goodsAddCount;

	@Column(name="goods_verify")
	private int goodsVerify;

	@Column(name="init_price", precision=10, scale=2)
	private BigDecimal initPrice;

	@Column(name="init_sale_count")
	private int initSaleCount;

	@Column(name="sale_rates", precision=10, scale=2)
	private BigDecimal saleRates;

	private int surplus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public DiscountGood() {
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getAlreadySale() {
		return this.alreadySale;
	}

	public void setAlreadySale(int alreadySale) {
		this.alreadySale = alreadySale;
	}

	public Date getAutoDownTime() {
		return this.autoDownTime;
	}

	public void setAutoDownTime(Date autoDownTime) {
		this.autoDownTime = autoDownTime;
	}

	public BigDecimal getBenefitPrice() {
		return this.benefitPrice;
	}

	public void setBenefitPrice(BigDecimal benefitPrice) {
		this.benefitPrice = benefitPrice;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGoodsAddCount() {
		return this.goodsAddCount;
	}

	public void setGoodsAddCount(int goodsAddCount) {
		this.goodsAddCount = goodsAddCount;
	}

	public int getGoodsVerify() {
		return this.goodsVerify;
	}

	public void setGoodsVerify(int goodsVerify) {
		this.goodsVerify = goodsVerify;
	}

	public BigDecimal getInitPrice() {
		return this.initPrice;
	}

	public void setInitPrice(BigDecimal initPrice) {
		this.initPrice = initPrice;
	}

	public int getInitSaleCount() {
		return this.initSaleCount;
	}

	public void setInitSaleCount(int initSaleCount) {
		this.initSaleCount = initSaleCount;
	}

	public BigDecimal getSaleRates() {
		return this.saleRates;
	}

	public void setSaleRates(BigDecimal saleRates) {
		this.saleRates = saleRates;
	}

	public int getSurplus() {
		return this.surplus;
	}

	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
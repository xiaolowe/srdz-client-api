package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * The persistent class for the order_goods database table.
 * 
 */
@Entity
@Table(name = "order_goods")
@NamedQuery(name = "OrderGood.findAll", query = "SELECT o FROM OrderGood o")
public class OrderGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_goods_id", unique = true, nullable = false)
	private int orderGoodsId;

	@Column(name = "goods_id", nullable = false)
	private int goodsId;

	@Column(name = "goods_image", length = 100)
	private String goodsImage;

	@Column(name = "goods_name", nullable = false, length = 50)
	private String goodsName;

	@Column(name = "goods_num", nullable = false)
	private int goodsNum;

	@Column(name = "goods_pay_price", nullable = false, precision = 10, scale = 2)
	private BigDecimal goodsPayPrice;

	@Column(name = "goods_price", nullable = false, precision = 10, scale = 2)
	private BigDecimal goodsPrice;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "order_id", nullable = false)
	private int orderId;

	@Column(name = "promotions_id", nullable = false)
	private int promotionsId;

	@Column(length = 50)
	private String standard;

	// 关联商品大图
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_Id", referencedColumnName = "goods_Id", insertable = false, updatable = false)
	@OrderBy("goodsBannerId ASC")
	private Set<GoodsBanner> goodsBanners;

	public OrderGood() {
	}

	public int getOrderGoodsId() {
		return this.orderGoodsId;
	}

	public void setOrderGoodsId(int orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
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

	public int getGoodsNum() {
		return this.goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public BigDecimal getGoodsPayPrice() {
		return this.goodsPayPrice;
	}

	public void setGoodsPayPrice(BigDecimal goodsPayPrice) {
		this.goodsPayPrice = goodsPayPrice;
	}

	public BigDecimal getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getPromotionsId() {
		return this.promotionsId;
	}

	public void setPromotionsId(int promotionsId) {
		this.promotionsId = promotionsId;
	}

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Set<GoodsBanner> getGoodsBanners() {
		return goodsBanners;
	}

	public void setGoodsBanners(Set<GoodsBanner> goodsBanners) {
		this.goodsBanners = goodsBanners;
	}

}
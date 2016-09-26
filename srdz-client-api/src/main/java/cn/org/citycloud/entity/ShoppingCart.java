package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the shopping_cart database table.
 * 
 */
@Entity
@Table(name = "shopping_cart")
@NamedQuery(name = "ShoppingCart.findAll", query = "SELECT s FROM ShoppingCart s")
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopping_cart_id")
	private int shoppingCartId;

	@Column(name = "cart_status")
	private int cartStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "goods_id")
	private int goodsId;

	@Column(name = "goods_image")
	private String goodsImage;

	@Column(name = "goods_name")
	private String goodsName;

	@Column(name = "goods_num")
	private int goodsNum;

	@Column(name = "goods_pay_price")
	private BigDecimal goodsPayPrice;

	@Column(name = "goods_price")
	private BigDecimal goodsPrice;

	@Lob
	@Column(name = "goods_spec")
	private String goodsSpec;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "open_id")
	private String openId;

	@Column(name = "promotions_id")
	private int promotionsId;

	@Column(name = "supplier_id")
	private int supplierId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	// optional=true：可选，表示此对象可以没有，可以为null；false表示必须存在
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "goods_id", insertable = false, updatable = false)
	private Good goods;

	public ShoppingCart() {
	}

	public int getShoppingCartId() {
		return this.shoppingCartId;
	}

	public void setShoppingCartId(int shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public int getCartStatus() {
		return this.cartStatus;
	}

	public void setCartStatus(int cartStatus) {
		this.cartStatus = cartStatus;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getGoodsSpec() {
		return this.goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getPromotionsId() {
		return this.promotionsId;
	}

	public void setPromotionsId(int promotionsId) {
		this.promotionsId = promotionsId;
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Good getGoods() {
		return goods;
	}

	public void setGoods(Good goods) {
		this.goods = goods;
	}

}
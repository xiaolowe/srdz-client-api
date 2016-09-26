package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the dist_shop_goods database table.
 * 
 */
@Entity
@Table(name = "dist_shop_goods")
@NamedQuery(name = "DistShopGood.findAll", query = "SELECT d FROM DistShopGood d")
public class DistShopGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "goods_id", unique = true, nullable = false)
	private int goodsId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "member_id")
	private int memberId;

	@Column(name="alreadySale")
	private int alreadySale;
	
	@Column(name = "sales_member_name", length = 50)
	private String salesMemberName;

	@Column(name = "shop_goods_price", precision = 10, scale = 2)
	private BigDecimal shopGoodsPrice;
	
	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	// 商品信息
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "goods_id", insertable = false, updatable = false)
	private Good goodDetail;

	public DistShopGood() {
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getSalesMemberName() {
		return this.salesMemberName;
	}

	public void setSalesMemberName(String salesMemberName) {
		this.salesMemberName = salesMemberName;
	}

	public BigDecimal getShopGoodsPrice() {
		return this.shopGoodsPrice;
	}

	public void setShopGoodsPrice(BigDecimal shopGoodsPrice) {
		this.shopGoodsPrice = shopGoodsPrice;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Good getGoodDetail() {
		return goodDetail;
	}

	public void setGoodDetail(Good goodDetail) {
		this.goodDetail = goodDetail;
	}

	public int getAlreadySale() {
		return alreadySale;
	}

	public void setAlreadySale(int alreadySale) {
		this.alreadySale = alreadySale;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
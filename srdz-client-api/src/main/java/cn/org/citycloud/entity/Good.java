package cn.org.citycloud.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the goods database table.
 * 
 */
@Entity
@Table(name = "goods")
@NamedQuery(name = "Good.findAll", query = "SELECT g FROM Good g")
public class Good implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "goods_id", unique = true, nullable = false)
	private int goodsId;

	@Column(name = "already_sale")
	private int alreadySale;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auto_down_time")
	private Date autoDownTime;

	@Column(name = "brand_id", nullable = false)
	private int brandId;

	@Column(name = "comment_count")
	private int commentCount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "discount_flg")
	private int discountFlg;

	@Column(name = "favorites_count")
	private int favoritesCount;

	@Column(name = "flow_template_id")
	private int flowTemplateId;

	@Column(name = "goods_add_count")
	private int goodsAddCount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "goods_addtime")
	private Date goodsAddtime;

	@Column(name = "goods_class_id", nullable = false)
	private int goodsClassId;

	@Column(name = "goods_class_name", length = 50)
	private String goodsClassName;

	@Column(name = "goods_class_three_id")
	private int goodsClassThreeId;

	@Column(name = "goods_class_three_name", length = 50)
	private String goodsClassThreeName;

	@Column(name = "goods_class_two_id", nullable = false)
	private int goodsClassTwoId;

	@Column(name = "goods_class_two_name", length = 50)
	private String goodsClassTwoName;

	@Lob
	@Column(name = "goods_desc")
	private String goodsDesc;

	@Column(name = "goods_image", length = 100)
	private String goodsImage;

	@Column(name = "goods_name", nullable = false, length = 50)
	private String goodsName;

	@Column(name = "goods_status", nullable = false)
	private int goodsStatus;

	@Column(name = "goods_weight", precision = 10, scale = 2)
	private BigDecimal goodsWeight;

	@Column(name = "init_price", precision = 10, scale = 2)
	private BigDecimal initPrice;

	@Column(name = "init_sale_count")
	private int initSaleCount;

	@Column(name = "is_recommend")
	private int isRecommend;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "region_area")
	private int regionArea;

	@Column(name = "region_area_name", length = 50)
	private String regionAreaName;

	@Column(name = "region_city")
	private int regionCity;

	@Column(name = "region_city_name", length = 50)
	private String regionCityName;

	@Column(name = "region_prov")
	private int regionProv;

	@Column(name = "region_prov_name", length = 50)
	private String regionProvName;

	@Column(name = "sale_price", precision = 10, scale = 2)
	private BigDecimal salePrice;

	@Column(name = "sale_rates", precision = 10, scale = 2)
	private BigDecimal saleRates;

	@Column(name = "saler_flg")
	private int salerFlg;

	@Column(length = 50)
	private String standard;

	@Column(name = "supplier_id")
	private int supplierId;

	private int surplus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	// 分销商品
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "goods_id", insertable = false, updatable = false)
	private DistGood distGood;

	// 特惠商品
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "goods_id", insertable = false, updatable = false)
	private DiscountGood discountGood;

	// 品牌
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "brand_id", insertable = false, updatable = false)
	private Brand brand;

	// 关联商品大图
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_id", insertable = false, updatable = false)
	@OrderBy("goodsBannerId ASC")
	private Set<GoodsBanner> goodsBanners;

	// 信誉评价
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_id", insertable = false, updatable = false)
	private Set<EvaluateGood> evaluateGoods;

	// optional=true：可选，表示此对象可以没有，可以为null；false表示必须存在
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "supplier_id", insertable = false, updatable = false)
	private Supplier supplier;

	public Good() {
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

	public int getBrandId() {
		return this.brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDiscountFlg() {
		return this.discountFlg;
	}

	public void setDiscountFlg(int discountFlg) {
		this.discountFlg = discountFlg;
	}

	public int getFavoritesCount() {
		return this.favoritesCount;
	}

	public void setFavoritesCount(int favoritesCount) {
		this.favoritesCount = favoritesCount;
	}

	public int getFlowTemplateId() {
		return this.flowTemplateId;
	}

	public void setFlowTemplateId(int flowTemplateId) {
		this.flowTemplateId = flowTemplateId;
	}

	public int getGoodsAddCount() {
		return this.goodsAddCount;
	}

	public void setGoodsAddCount(int goodsAddCount) {
		this.goodsAddCount = goodsAddCount;
	}

	public Date getGoodsAddtime() {
		return this.goodsAddtime;
	}

	public void setGoodsAddtime(Date goodsAddtime) {
		this.goodsAddtime = goodsAddtime;
	}

	public int getGoodsClassId() {
		return this.goodsClassId;
	}

	public void setGoodsClassId(int goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

	public String getGoodsClassName() {
		return this.goodsClassName;
	}

	public void setGoodsClassName(String goodsClassName) {
		this.goodsClassName = goodsClassName;
	}

	public int getGoodsClassThreeId() {
		return this.goodsClassThreeId;
	}

	public void setGoodsClassThreeId(int goodsClassThreeId) {
		this.goodsClassThreeId = goodsClassThreeId;
	}

	public String getGoodsClassThreeName() {
		return this.goodsClassThreeName;
	}

	public void setGoodsClassThreeName(String goodsClassThreeName) {
		this.goodsClassThreeName = goodsClassThreeName;
	}

	public int getGoodsClassTwoId() {
		return this.goodsClassTwoId;
	}

	public void setGoodsClassTwoId(int goodsClassTwoId) {
		this.goodsClassTwoId = goodsClassTwoId;
	}

	public String getGoodsClassTwoName() {
		return this.goodsClassTwoName;
	}

	public void setGoodsClassTwoName(String goodsClassTwoName) {
		this.goodsClassTwoName = goodsClassTwoName;
	}

	public String getGoodsDesc() {
		return this.goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
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

	public int getGoodsStatus() {
		return this.goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public BigDecimal getGoodsWeight() {
		return this.goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
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

	public int getIsRecommend() {
		return this.isRecommend;
	}

	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getRegionArea() {
		return this.regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

	public String getRegionAreaName() {
		return this.regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
	}

	public int getRegionCity() {
		return this.regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionCityName() {
		return this.regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public int getRegionProv() {
		return this.regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public String getRegionProvName() {
		return this.regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSaleRates() {
		return this.saleRates;
	}

	public void setSaleRates(BigDecimal saleRates) {
		this.saleRates = saleRates;
	}

	public int getSalerFlg() {
		return this.salerFlg;
	}

	public void setSalerFlg(int salerFlg) {
		this.salerFlg = salerFlg;
	}

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
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

	public DistGood getDistGood() {
		return distGood;
	}

	public void setDistGood(DistGood distGood) {
		this.distGood = distGood;
	}

	public DiscountGood getDiscountGood() {
		return discountGood;
	}

	public void setDiscountGood(DiscountGood discountGood) {
		this.discountGood = discountGood;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Set<GoodsBanner> getGoodsBanners() {
		return goodsBanners;
	}

	public void setGoodsBanners(Set<GoodsBanner> goodsBanners) {
		this.goodsBanners = goodsBanners;
	}

	public Set<EvaluateGood> getEvaluateGoods() {
		return evaluateGoods;
	}

	public void setEvaluateGoods(Set<EvaluateGood> evaluateGoods) {
		this.evaluateGoods = evaluateGoods;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
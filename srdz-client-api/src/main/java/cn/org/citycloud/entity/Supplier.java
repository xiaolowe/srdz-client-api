package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	private int supplierId;

	@Column(name = "account_bank")
	@JsonIgnore
	private String accountBank;

	@Column(name = "account_owner")
	@JsonIgnore
	private String accountOwner;

	@Column(name = "bank_name")
	@JsonIgnore
	private String bankName;

	@Column(name = "banner_image")
//	@JsonIgnore
	private String bannerImage;

	@Column(name = "business_license")
	@JsonIgnore
	private String businessLicense;

	@Column(name = "card_no")
	@JsonIgnore
	private String cardNo;

	@Column(name = "card_owner")
	@JsonIgnore
	private String cardOwner;

	@Column(name = "comany_name")
	@JsonIgnore
	private String comanyName;

	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "contact_phone")
	private String contactPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "favorites_count")
	private int favoritesCount;

	@Column(name = "logo_iamge")
	private String logoIamge;

	@Column(name = "org_code")
	@JsonIgnore
	private String orgCode;

	@Column(name = "owner_card_image")
	@JsonIgnore
	private String ownerCardImage;

	@Column(name = "owner_identity")
	@JsonIgnore
	private String ownerIdentity;

	@JsonIgnore
	private String password;

	private String phone;

	private int postcode;

	@Column(name = "region_area")
	private int regionArea;

	@Column(name = "region_area_name")
	private String regionAreaName;

	@Column(name = "region_city")
	private int regionCity;

	@Column(name = "region_city_name")
	private String regionCityName;

	@Column(name = "region_prov")
	private int regionProv;

	@Column(name = "region_prov_name")
	private String regionProvName;

	@Column(name = "sale_address_image")
	private String saleAddressImage;

	@Column(name = "service_center_id")
	private int serviceCenterId;

	private int status;

	@Column(name = "supplier_address")
	private String supplierAddress;

	@Column(name = "supplier_level_id")
	private int supplierLevelId;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "supplier_owner")
	private String supplierOwner;

	@Column(name = "supplier_shop_name")
	private String supplierShopName;

	@Column(name = "tax_register_certifacate")
	@JsonIgnore
	private String taxRegisterCertifacate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	// optional=true：可选，表示此对象可以没有，可以为null；false表示必须存在
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "supplier_level_id", insertable = false, updatable = false)
	private SupplierLevel supplierLevel;

	public Supplier() {
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getAccountBank() {
		return this.accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountOwner() {
		return this.accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public String getBusinessLicense() {
		return this.businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardOwner() {
		return this.cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getComanyName() {
		return this.comanyName;
	}

	public void setComanyName(String comanyName) {
		this.comanyName = comanyName;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getFavoritesCount() {
		return favoritesCount;
	}

	public void setFavoritesCount(int favoritesCount) {
		this.favoritesCount = favoritesCount;
	}

	public String getLogoIamge() {
		return this.logoIamge;
	}

	public void setLogoIamge(String logoIamge) {
		this.logoIamge = logoIamge;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOwnerCardImage() {
		return this.ownerCardImage;
	}

	public void setOwnerCardImage(String ownerCardImage) {
		this.ownerCardImage = ownerCardImage;
	}

	public String getOwnerIdentity() {
		return this.ownerIdentity;
	}

	public void setOwnerIdentity(String ownerIdentity) {
		this.ownerIdentity = ownerIdentity;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPostcode() {
		return this.postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
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

	public String getSaleAddressImage() {
		return this.saleAddressImage;
	}

	public void setSaleAddressImage(String saleAddressImage) {
		this.saleAddressImage = saleAddressImage;
	}

	public int getServiceCenterId() {
		return this.serviceCenterId;
	}

	public void setServiceCenterId(int serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSupplierAddress() {
		return this.supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public int getSupplierLevelId() {
		return this.supplierLevelId;
	}

	public void setSupplierLevelId(int supplierLevelId) {
		this.supplierLevelId = supplierLevelId;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierOwner() {
		return this.supplierOwner;
	}

	public void setSupplierOwner(String supplierOwner) {
		this.supplierOwner = supplierOwner;
	}

	public String getSupplierShopName() {
		return this.supplierShopName;
	}

	public void setSupplierShopName(String supplierShopName) {
		this.supplierShopName = supplierShopName;
	}

	public String getTaxRegisterCertifacate() {
		return this.taxRegisterCertifacate;
	}

	public void setTaxRegisterCertifacate(String taxRegisterCertifacate) {
		this.taxRegisterCertifacate = taxRegisterCertifacate;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public SupplierLevel getSupplierLevel() {
		return supplierLevel;
	}

	public void setSupplierLevel(SupplierLevel supplierLevel) {
		this.supplierLevel = supplierLevel;
	}

}
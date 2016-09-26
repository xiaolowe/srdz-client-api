package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the service_center database table.
 * 
 */
@Entity
@Table(name = "service_center")
@NamedQuery(name = "ServiceCenter.findAll", query = "SELECT s FROM ServiceCenter s")
public class ServiceCenter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_center_id")
	private int serviceCenterId;

	@Column(name = "account_bank")
	private String accountBank;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "business_license")
	private String businessLicense;

	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "card_owner")
	private String cardOwner;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "contact_phone")
	private String contactPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "org_code")
	private String orgCode;

	@Column(name = "owner_card_image")
	private String ownerCardImage;

	@Column(name = "owner_identity")
	private String ownerIdentity;

	@Column(name = "service_center_level_id")
	private int serviceCenterLevelId;

	@Column(name = "service_center_name")
	private String serviceCenterName;

	@Column(name = "service_center_owner")
	private String serviceCenterOwner;

	@Column(name = "service_center_type")
	private int serviceCenterType;

	private int status;

	@Column(name="region_area")
	private int regionArea;

	@Column(name="region_city")
	private int regionCity;

	@Column(name = "tax_register_certifacate")
	private String taxRegisterCertifacate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	// optional=true：可选，表示此对象可以没有，可以为null；false表示必须存在
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "service_center_level_id", insertable = false, updatable = false)
	private ServiceCenterLevel serviceCenterLevel;

	public ServiceCenter() {
	}

	public int getServiceCenterId() {
		return this.serviceCenterId;
	}

	public void setServiceCenterId(int serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public String getAccountBank() {
		return this.accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public int getServiceCenterLevelId() {
		return this.serviceCenterLevelId;
	}

	public void setServiceCenterLevelId(int serviceCenterLevelId) {
		this.serviceCenterLevelId = serviceCenterLevelId;
	}

	public String getServiceCenterName() {
		return this.serviceCenterName;
	}

	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}

	public String getServiceCenterOwner() {
		return this.serviceCenterOwner;
	}

	public void setServiceCenterOwner(String serviceCenterOwner) {
		this.serviceCenterOwner = serviceCenterOwner;
	}

	public int getServiceCenterType() {
		return this.serviceCenterType;
	}

	public void setServiceCenterType(int serviceCenterType) {
		this.serviceCenterType = serviceCenterType;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public ServiceCenterLevel getServiceCenterLevel() {
		return serviceCenterLevel;
	}

	public void setServiceCenterLevel(ServiceCenterLevel serviceCenterLevel) {
		this.serviceCenterLevel = serviceCenterLevel;
	}

	public int getRegionArea() {
		return regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

	public int getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}
}
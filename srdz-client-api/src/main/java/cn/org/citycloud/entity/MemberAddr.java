package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the member_addr database table.
 * 
 */
@Entity
@Table(name = "member_addr")
@NamedQuery(name = "MemberAddr.findAll", query = "SELECT m FROM MemberAddr m")
public class MemberAddr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_addr_id", unique = true, nullable = false)
	private int memberAddrId;

	@Column(name = "contacts_address", length = 50)
	private String contactsAddress;

	@Column(name = "contacts_name", length = 30)
	private String contactsName;

	@Column(name = "contacts_phone", length = 11)
	private String contactsPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "default_flag")
	private int defaultFlag;

	@Column(name = "member_id", nullable = false)
	private int memberId;

	@Column(name = "post_code", length = 6)
	private String postCode;

	@Column(name = "region_area")
	private int regionArea;

	@Column(name = "region_city")
	private int regionCity;

	@Column(name = "region_prov")
	private int regionProv;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "region_prov", referencedColumnName = "region_code", insertable = false, updatable = false)
	private RegionInfo regionProvDetail;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "region_city", referencedColumnName = "region_code", insertable = false, updatable = false)
	private RegionInfo regionCityDetail;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "region_area", referencedColumnName = "region_code", insertable = false, updatable = false)
	private RegionInfo regionAreaDetail;

	public MemberAddr() {
	}

	public int getMemberAddrId() {
		return this.memberAddrId;
	}

	public void setMemberAddrId(int memberAddrId) {
		this.memberAddrId = memberAddrId;
	}

	public String getContactsAddress() {
		return this.contactsAddress;
	}

	public void setContactsAddress(String contactsAddress) {
		this.contactsAddress = contactsAddress;
	}

	public String getContactsName() {
		return this.contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsPhone() {
		return this.contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDefaultFlag() {
		return this.defaultFlag;
	}

	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public int getRegionArea() {
		return this.regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

	public int getRegionCity() {
		return this.regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public int getRegionProv() {
		return this.regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public RegionInfo getRegionProvDetail() {
		return regionProvDetail;
	}

	public void setRegionProvDetail(RegionInfo regionProvDetail) {
		this.regionProvDetail = regionProvDetail;
	}

	public RegionInfo getRegionCityDetail() {
		return regionCityDetail;
	}

	public void setRegionCityDetail(RegionInfo regionCityDetail) {
		this.regionCityDetail = regionCityDetail;
	}

	public RegionInfo getRegionAreaDetail() {
		return regionAreaDetail;
	}

	public void setRegionAreaDetail(RegionInfo regionAreaDetail) {
		this.regionAreaDetail = regionAreaDetail;
	}

}
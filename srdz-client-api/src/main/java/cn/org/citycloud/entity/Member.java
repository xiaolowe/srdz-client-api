package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the member database table.
 * 
 */
@Entity
@Table(name="member")
@NamedQuery(name="Member.findAll", query="SELECT m FROM Member m")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_id", unique=true, nullable=false)
	private int memberId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="member_avatar", length=300)
	private String memberAvatar;

	@Column(name="member_email", length=100)
	private String memberEmail;

	@Column(name="member_growth")
	private int memberGrowth;

	@Column(name="member_level_id")
	private int memberLevelId;

	@Column(name="member_login_ip", length=20)
	private String memberLoginIp;

	@Column(name="member_login_num")
	private int memberLoginNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_login_time")
	private Date memberLoginTime;

	@Column(name="member_name", length=50)
	private String memberName;

	@Column(name="member_old_login_ip", length=20)
	private String memberOldLoginIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_old_login_time")
	private Date memberOldLoginTime;

	@Column(name="member_phone", length=20)
	private String memberPhone;

	@Column(name="member_pwd", length=32)
	private String memberPwd;

	@Column(name="member_sex")
	private int memberSex;

	@Column(name="member_status", nullable=false)
	private int memberStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_time")
	private Date memberTime;

	@Column(name="member_truename", length=20)
	private String memberTruename;

	@Column(name="region_area_name", length=50)
	private String regionAreaName;

	@Column(name="region_city")
	private int regionCity;

	@Column(name="region_city_name", length=50)
	private String regionCityName;

	@Column(name="region_prov")
	private int regionProv;

	@Column(name="region_prov_name", length=50)
	private String regionProvName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

	public Member() {
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMemberAvatar() {
		return this.memberAvatar;
	}

	public void setMemberAvatar(String memberAvatar) {
		this.memberAvatar = memberAvatar;
	}

	public String getMemberEmail() {
		return this.memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public int getMemberGrowth() {
		return this.memberGrowth;
	}

	public void setMemberGrowth(int memberGrowth) {
		this.memberGrowth = memberGrowth;
	}

	public int getMemberLevelId() {
		return this.memberLevelId;
	}

	public void setMemberLevelId(int memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public String getMemberLoginIp() {
		return this.memberLoginIp;
	}

	public void setMemberLoginIp(String memberLoginIp) {
		this.memberLoginIp = memberLoginIp;
	}

	public int getMemberLoginNum() {
		return this.memberLoginNum;
	}

	public void setMemberLoginNum(int memberLoginNum) {
		this.memberLoginNum = memberLoginNum;
	}

	public Date getMemberLoginTime() {
		return this.memberLoginTime;
	}

	public void setMemberLoginTime(Date memberLoginTime) {
		this.memberLoginTime = memberLoginTime;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberOldLoginIp() {
		return this.memberOldLoginIp;
	}

	public void setMemberOldLoginIp(String memberOldLoginIp) {
		this.memberOldLoginIp = memberOldLoginIp;
	}

	public Date getMemberOldLoginTime() {
		return this.memberOldLoginTime;
	}

	public void setMemberOldLoginTime(Date memberOldLoginTime) {
		this.memberOldLoginTime = memberOldLoginTime;
	}

	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberPwd() {
		return this.memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public int getMemberSex() {
		return this.memberSex;
	}

	public void setMemberSex(int memberSex) {
		this.memberSex = memberSex;
	}

	public int getMemberStatus() {
		return this.memberStatus;
	}

	public void setMemberStatus(int memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Date getMemberTime() {
		return this.memberTime;
	}

	public void setMemberTime(Date memberTime) {
		this.memberTime = memberTime;
	}

	public String getMemberTruename() {
		return this.memberTruename;
	}

	public void setMemberTruename(String memberTruename) {
		this.memberTruename = memberTruename;
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

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
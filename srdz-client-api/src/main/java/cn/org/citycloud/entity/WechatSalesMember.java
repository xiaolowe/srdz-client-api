package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the wechat_sales_member database table.
 * 
 */
@Entity
@Table(name="wechat_sales_member")
@NamedQuery(name="WechatSalesMember.findAll", query="SELECT w FROM WechatSalesMember w")
public class WechatSalesMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="wechat_sales_member_id")
	private int wechatSalesMemberId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="member_avatar")
	private String memberAvatar;

	@Column(name="member_email")
	private String memberEmail;

	@Column(name="member_login_ip")
	private String memberLoginIp;

	@Column(name="member_login_num")
	private int memberLoginNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_login_time")
	private Date memberLoginTime;

	@Column(name="member_old_login_ip")
	private String memberOldLoginIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_old_login_time")
	private Date memberOldLoginTime;

	@Column(name="member_pwd")
	private String memberPwd;

	@Column(name="member_truename")
	private String memberTruename;

	@Column(name="open_id")
	private String openId;

	private String phone;

	@Column(name="region_area_name")
	private String regionAreaName;

	@Column(name="region_city")
	private int regionCity;

	@Column(name="region_city_name")
	private String regionCityName;

	@Column(name="region_prov")
	private int regionProv;

	@Column(name="region_prov_name")
	private String regionProvName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_time")
	private Date registerTime;

	private int sex;

	private int status;

	@Column(name="union_id")
	private String unionId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="wechat_aliasname")
	private String wechatAliasname;

	@Column(name="wechat_no")
	private String wechatNo;

	public WechatSalesMember() {
	}

	public int getWechatSalesMemberId() {
		return this.wechatSalesMemberId;
	}

	public void setWechatSalesMemberId(int wechatSalesMemberId) {
		this.wechatSalesMemberId = wechatSalesMemberId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getMemberPwd() {
		return this.memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberTruename() {
		return this.memberTruename;
	}

	public void setMemberTruename(String memberTruename) {
		this.memberTruename = memberTruename;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWechatAliasname() {
		return this.wechatAliasname;
	}

	public void setWechatAliasname(String wechatAliasname) {
		this.wechatAliasname = wechatAliasname;
	}

	public String getWechatNo() {
		return this.wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

}
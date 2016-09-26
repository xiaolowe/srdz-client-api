package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the admin_user database table.
 * 
 */
@Entity
@Table(name="admin_user")
@NamedQuery(name="AdminUser.findAll", query="SELECT a FROM AdminUser a")
public class AdminUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="admin_user_id", unique=true, nullable=false)
	private int adminUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(length=20)
	private String phone;

	@Column(name="role_code")
	private int roleCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_avatar", length=100)
	private String userAvatar;

	@Column(name="user_email", length=20)
	private String userEmail;

	@Column(name="user_name", length=50)
	private String userName;

	@Column(name="user_pwd", length=50)
	private String userPwd;

	@Column(name="user_status")
	private int userStatus;

	@Column(name="user_truename", length=20)
	private String userTruename;

	public AdminUser() {
	}

	public int getAdminUserId() {
		return this.adminUserId;
	}

	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(int roleCode) {
		this.roleCode = roleCode;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserAvatar() {
		return this.userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public int getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserTruename() {
		return this.userTruename;
	}

	public void setUserTruename(String userTruename) {
		this.userTruename = userTruename;
	}

}
package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 * The persistent class for the supplier_user database table.
 * 
 */
@Entity
@Table(name="supplier_user")
@NamedQuery(name="SupplierUser.findAll", query="SELECT s FROM SupplierUser s")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SupplierUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="supplier_user_id")
	private int supplierUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String phone;

	@Column(name="supplier_id")
	private int supplierId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_avatar")
	private String userAvatar;

	@Column(name="user_email")
	private String userEmail;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_pwd")
	@JsonIgnore
	private String userPwd;

	@Column(name="user_status")
	@JsonIgnore
	private int userStatus;

	@Column(name="user_truename")
	private String userTruename;

	public SupplierUser() {
	}

	public int getSupplierUserId() {
		return this.supplierUserId;
	}

	public void setSupplierUserId(int supplierUserId) {
		this.supplierUserId = supplierUserId;
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
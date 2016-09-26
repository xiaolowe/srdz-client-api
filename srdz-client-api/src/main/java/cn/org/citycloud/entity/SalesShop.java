package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sales_shop database table.
 * 
 */
@Entity
@Table(name="sales_shop")
@NamedQuery(name="SalesShop.findAll", query="SELECT s FROM SalesShop s")
public class SalesShop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sales_shop_id", unique=true, nullable=false)
	private int salesShopId;

	@Column(name="banner_image", length=200)
	private String bannerImage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(length=200)
	private String logo;

	@Column(name="member_id")
	private int memberId;

	@Column(name="sales_shop_name", length=50)
	private String salesShopName;

	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public SalesShop() {
	}

	public int getSalesShopId() {
		return this.salesShopId;
	}

	public void setSalesShopId(int salesShopId) {
		this.salesShopId = salesShopId;
	}

	public String getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getSalesShopName() {
		return this.salesShopName;
	}

	public void setSalesShopName(String salesShopName) {
		this.salesShopName = salesShopName;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
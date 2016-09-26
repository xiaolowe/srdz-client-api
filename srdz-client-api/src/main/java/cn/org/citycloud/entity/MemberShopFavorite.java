package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the member_shop_favorites database table.
 * 
 */
@Entity
@Table(name="member_shop_favorites")
@NamedQuery(name="MemberShopFavorite.findAll", query="SELECT m FROM MemberShopFavorite m")
public class MemberShopFavorite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="logo_iamge", length=200)
	private String logoIamge;

	@Column(name="member_id", nullable=false)
	private int memberId;

	@Column(name="supplier_id", nullable=false)
	private int supplierId;

	@Column(name="supplier_shop_name", length=50)
	private String supplierShopName;

	public MemberShopFavorite() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLogoIamge() {
		return this.logoIamge;
	}

	public void setLogoIamge(String logoIamge) {
		this.logoIamge = logoIamge;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierShopName() {
		return this.supplierShopName;
	}

	public void setSupplierShopName(String supplierShopName) {
		this.supplierShopName = supplierShopName;
	}

}
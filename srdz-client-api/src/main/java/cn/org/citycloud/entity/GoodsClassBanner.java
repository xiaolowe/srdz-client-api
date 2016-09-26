package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the goods_class_banner database table.
 * 
 */
@Entity
@Table(name="goods_class_banner")
@NamedQuery(name="GoodsClassBanner.findAll", query="SELECT g FROM GoodsClassBanner g")
public class GoodsClassBanner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="goods_banner_id", unique=true, nullable=false)
	private int goodsBannerId;

	@Column(name="banner_image", length=200)
	private String bannerImage;

	@Column(name="goods_class_id")
	private int goodsClassId;

	public GoodsClassBanner() {
	}

	public int getGoodsBannerId() {
		return this.goodsBannerId;
	}

	public void setGoodsBannerId(int goodsBannerId) {
		this.goodsBannerId = goodsBannerId;
	}

	public String getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public int getGoodsClassId() {
		return this.goodsClassId;
	}

	public void setGoodsClassId(int goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

}
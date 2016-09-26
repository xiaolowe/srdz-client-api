package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the goods_banner database table.
 * 
 */
@Entity
@Table(name="goods_banner")
@NamedQuery(name="GoodsBanner.findAll", query="SELECT g FROM GoodsBanner g")
public class GoodsBanner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="goods_banner_id", unique=true, nullable=false)
	private int goodsBannerId;

	@Column(name="banner_image", length=200)
	private String bannerImage;

	@Column(name="goods_id")
	private int goodsId;

	public GoodsBanner() {
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

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

}
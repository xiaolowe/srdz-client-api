package cn.org.citycloud.bean;

import java.util.ArrayList;
import java.util.List;

import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.GoodsClassBanner;

public class FloorGoodsInfo {
	
	// 商品分类大图
	private List<GoodsClassBanner> banners = new ArrayList<GoodsClassBanner>();
	
	// 商品信息
	private List<Good> goods = new ArrayList<Good>();

	public List<GoodsClassBanner> getBanners() {
		return banners;
	}

	public void setBanners(List<GoodsClassBanner> banners) {
		this.banners = banners;
	}

	public List<Good> getGoods() {
		return goods;
	}

	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}
	
	

}

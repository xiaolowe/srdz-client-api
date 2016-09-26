package cn.org.citycloud.bean;

public class DistributorShopInfo {

	private String salesShopName;

	private String logo;

	private String bannerImage;

	private String template;

	// 全部商品
	private long allGoods = 0L;
	// 上新商品
	private long newArrive = 0L;

	public String getSalesShopName() {
		return salesShopName;
	}

	public void setSalesShopName(String salesShopName) {
		this.salesShopName = salesShopName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public long getAllGoods() {
		return allGoods;
	}

	public void setAllGoods(long allGoods) {
		this.allGoods = allGoods;
	}

	public long getNewArrive() {
		return newArrive;
	}

	public void setNewArrive(long newArrive) {
		this.newArrive = newArrive;
	}

}

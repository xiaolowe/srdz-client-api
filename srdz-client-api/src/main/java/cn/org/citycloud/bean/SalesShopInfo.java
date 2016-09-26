package cn.org.citycloud.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class SalesShopInfo {
	
	@NotBlank(message = "店铺名称不能为空")
	@Length(max = 50)
	@ApiModelProperty(value = "店铺名称", required = true)
	private String salesShopName;
	
	@NotBlank(message = "Logo不能为空")
	@Length(max = 200)
	@ApiModelProperty(value = "Logo", required = true)
	private String logo;
	
	@NotBlank(message = "背景图片不能为空")
	@Length(max = 200)
	@ApiModelProperty(value = "背景图片", required = true)
	private String bannerImage;
	
	@NotBlank(message = "店铺模板不能为空")
	@ApiModelProperty(value = "店铺模板", required = true)
	private String template;

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
	
	

}

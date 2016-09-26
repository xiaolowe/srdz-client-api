package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;

public class ShopSearch extends SliceAndSort {

	@ApiModelProperty(value = "省ID", required = true)
	private int regionProv = 0;

	@ApiModelProperty(value = "市ID", required = true)
	private int regionCity = 0;
	
	@ApiModelProperty(value = "供应商名称", required = false)
	private String supplierName;

	public int getRegionProv() {
		return regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public int getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	

}

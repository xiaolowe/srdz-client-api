package cn.org.citycloud.bean;

import java.util.ArrayList;
import java.util.List;

import cn.org.citycloud.entity.ShoppingCart;

public class ShoppingCartGroupByShop {

	// 供应商ID
	private int supplierId;

	// 供应商名称
	private String supplierName;

	// 店铺购物车商品一览
	private List<ShoppingCart> cartGoods = new ArrayList<ShoppingCart>();

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<ShoppingCart> getCartGoods() {
		return cartGoods;
	}

	public void setCartGoods(List<ShoppingCart> cartGoods) {
		this.cartGoods = cartGoods;
	}

}

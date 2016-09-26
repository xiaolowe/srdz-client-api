package cn.org.citycloud.bean;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderGoods {

	private int supplierId;

	private List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public List<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

}

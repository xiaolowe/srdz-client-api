package cn.org.citycloud.bean;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

public class DeliveryCostsSearch {

	@Size(min = 1)
	private List<DeliveryGoods> deliveryGoodsList = new ArrayList<DeliveryGoods>();

	public List<DeliveryGoods> getDeliveryGoodsList() {
		return deliveryGoodsList;
	}

	public void setDeliveryGoodsList(List<DeliveryGoods> deliveryGoodsList) {
		this.deliveryGoodsList = deliveryGoodsList;
	}

}

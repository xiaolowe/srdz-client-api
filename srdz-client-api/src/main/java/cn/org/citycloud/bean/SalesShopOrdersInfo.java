package cn.org.citycloud.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.org.citycloud.entity.OrderGood;

public class SalesShopOrdersInfo {

	private int orderId;

	private Date orderTime;

	private BigDecimal orderPrice;

	private BigDecimal saleAmount;

	private List<OrderGood> orderGoods = new ArrayList<OrderGood>();

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public List<OrderGood> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGood> orderGoods) {
		this.orderGoods = orderGoods;
	}

}

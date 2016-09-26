package cn.org.citycloud.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 用户商品评价
 * 
 * @author Bean
 *
 */
public class Evaluate {

	@Min(1)
	private int orderGoodsId;

	@Min(1)
	@Max(10)
	private int gevalScores;

	private String gevalContent;

	public int getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(int orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public int getGevalScores() {
		return gevalScores;
	}

	public void setGevalScores(int gevalScores) {
		this.gevalScores = gevalScores;
	}

	public String getGevalContent() {
		return gevalContent;
	}

	public void setGevalContent(String gevalContent) {
		this.gevalContent = gevalContent;
	}

}

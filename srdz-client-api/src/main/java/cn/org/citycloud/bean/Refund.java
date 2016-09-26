package cn.org.citycloud.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class Refund {

	/**
	 * 退款原因
	 */
	@NotEmpty
	private String backOrderCause;

	public String getBackOrderCause() {
		return backOrderCause;
	}

	public void setBackOrderCause(String backOrderCause) {
		this.backOrderCause = backOrderCause;
	}

}

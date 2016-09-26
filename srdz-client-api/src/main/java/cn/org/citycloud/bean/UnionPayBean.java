package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;


public class UnionPayBean {
	
	@ApiModelProperty(value = "商户号码", required = true)
	@NotBlank(message = "商户号码不能为空")
    private String merId;
	
	@ApiModelProperty(value = "交易金额", required = true)
	@NotBlank(message = "交易金额不能为空")
    private String txnAmt;		//单位为分， 不要带小数点
	
	@ApiModelProperty(value = "商户订单号", required = true)
	@NotBlank(message = "商户订单号不能为空")
    private String orderId;
	
    private String accNo;

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}


	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
    
    
	

}

package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;


public class PinanPayBean {
	
	@ApiModelProperty(value = "商户号码", required = true)
	@NotBlank(message = "商户号码不能为空")
    private String masterId;
	
	@ApiModelProperty(value = "交易金额", required = true)
	@NotBlank(message = "交易金额不能为空")
    private String amount;		//单位为分， 不要带小数点
	
	@ApiModelProperty(value = "商户订单号", required = true)
	@NotBlank(message = "商户订单号不能为空")
    private String orderId;
	
	@ApiModelProperty(value = "订单款项描述（商户自定）", required = true)
	@NotBlank(message = "订单款项描述不能为空")
    private String objectName;
    
	@ApiModelProperty(value = "备注（商户自定）", required = true)
	@NotBlank(message = "备注不能为空")
    private String remark;

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}

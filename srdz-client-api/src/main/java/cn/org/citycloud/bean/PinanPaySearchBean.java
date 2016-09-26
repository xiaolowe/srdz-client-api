package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;


public class PinanPaySearchBean {
	
	@ApiModelProperty(value = "商户号码", required = true)
	@NotBlank(message = "商户号码不能为空")
    private String masterId;
	
	@ApiModelProperty(value = "交易日期", required = true)
	@NotBlank(message = "交易日期不能为空")
    private String date;

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	

	

}

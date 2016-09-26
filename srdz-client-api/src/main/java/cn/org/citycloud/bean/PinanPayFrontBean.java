package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;


public class PinanPayFrontBean {
	
	@ApiModelProperty(value = "商户号码", required = true)
	@NotBlank(message = "商户号码不能为空")
    private String orig;
	
	@ApiModelProperty(value = "交易金额", required = true)
	@NotBlank(message = "交易金额不能为空")
    private String sign;

	public String getOrig() {
		return orig;
	}

	public void setOrig(String orig) {
		this.orig = orig;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}		
	


	

}

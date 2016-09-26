package cn.org.citycloud.bean;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class ForgetPwdBean {

	
	@NotBlank(message = "手机号码不能为空")
	@Length(max = 50)
	@ApiModelProperty(value = "手机号码", required = true)
	private String userPhone;
	
	@NotBlank(message = "手机验证码不能为空")
	@Length(max = 6, min = 6)
	@ApiModelProperty(value="手机验证码", required=true)
	private String phoneCode;
	
	@NotBlank(message = "密码不能为空")
	@Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
	@ApiModelProperty(value="密码", required=true)
	private String userPwd;
	
	@NotBlank(message = "确认密码不能为空")
	@Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
	@ApiModelProperty(value="确认密码", required=true)
	private String confirmPwd;

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	
	
	
	
	
}

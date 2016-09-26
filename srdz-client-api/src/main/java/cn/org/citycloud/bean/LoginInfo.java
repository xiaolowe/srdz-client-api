package cn.org.citycloud.bean;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户登录Model", description = "用户登录接口数据Model")
public class LoginInfo {

	@NotBlank(message = "手机号码不能为空")
	@Pattern(regexp = "(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}", message = "手机号码格式不正确，请重新输入")
	@ApiModelProperty(value = "手机号码", required = true)
	private String userPhone;

	@NotBlank(message = "密码不能为空")
	@Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
	@ApiModelProperty(value = "登录密码", required = true)
	private String userPwd;

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

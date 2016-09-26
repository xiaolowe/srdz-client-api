package cn.org.citycloud.bean;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="用户注册Model", description="用户注册接口数据Model")
public class RegisterInfo extends LoginInfo {

	@NotBlank(message = "手机验证码不能为空")
	@Length(max = 6, min = 6)
	@ApiModelProperty(value="手机验证码", required=true)
	private String phoneCode;

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

}

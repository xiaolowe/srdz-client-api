package cn.org.citycloud.bean;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="修改密码Model", description="用户修改密码接口数据Model")
public class UserPwdInfo {

	@NotBlank(message = "原密码不能为空")
	@Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
	@ApiModelProperty(value="原密码", required=true)
	private String oldUserPwd;

	@NotBlank(message = "新的登录密码不能为空")
	@Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
	@ApiModelProperty(value="新的登录密码", required=true)
	private String userPwd;

	@NotBlank(message = "再次输入的密码不能为空")
	@Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
	@ApiModelProperty(value="确认密码", required=true)
	private String confirmPwd;

	public String getOldUserPwd() {
		return oldUserPwd;
	}

	public void setOldUserPwd(String oldUserPwd) {
		this.oldUserPwd = oldUserPwd;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

}

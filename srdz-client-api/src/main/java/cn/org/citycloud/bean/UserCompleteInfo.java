package cn.org.citycloud.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="个人信息完善Model", description="个人信息完善数据Model")
public class UserCompleteInfo {

//	@NotBlank
//	@ApiModelProperty(value="头像", required=true)
//	private String userAvatar;
	
	@NotBlank
	@ApiModelProperty(value="姓名", required=true)
	private String userTruename;
	
	@NotBlank
	@ApiModelProperty(value="昵称", required=true)
	private String nickname;
	
	@NotBlank
	@Pattern(regexp = "(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}", message = "手机号码格式不正确，请重新输入")
	@ApiModelProperty(value="手机号码", required=true)
	private String userPhone;
	
	@Min(1)
	@Max(99)
	@ApiModelProperty(value="年龄", required=true)
	private int age;
	
	@Min(1)
	@Max(2)
	@ApiModelProperty(value="男：1 女：2", required=true)
	private int userSex;
	
	@Length(min=6, max=6)
	private int regionCity;

//	public String getUserAvatar() {
//		return userAvatar;
//	}
//
//	public void setUserAvatar(String userAvatar) {
//		this.userAvatar = userAvatar;
//	}

	public String getUserTruename() {
		return userTruename;
	}

	public void setUserTruename(String userTruename) {
		this.userTruename = userTruename;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public int getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}
	
	
}

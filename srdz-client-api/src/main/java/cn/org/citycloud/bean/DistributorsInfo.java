package cn.org.citycloud.bean;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class DistributorsInfo {

	@NotBlank(message = "姓名不能为空")
	@Length(max = 50)
	@ApiModelProperty(value = "姓名", required = true)
	private String contactName;

	@NotBlank(message = "身份证号码不能为空")
	@Length(max = 18)
	@Pattern(regexp = "\\d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}", message = "请输入正确的身份证号码")
	@ApiModelProperty(value = "身份证号码", required = true)
	private String identityNo;

	@NotBlank(message = "身份证电子档不能为空")
	@Length(max = 200)
	@ApiModelProperty(value = "身份证电子档", required = true)
	private String identityImage;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getIdentityImage() {
		return identityImage;
	}

	public void setIdentityImage(String identityImage) {
		this.identityImage = identityImage;
	}

}

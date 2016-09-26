package cn.org.citycloud.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="供应商注册Model", description="供应商注册接口数据Model")
public class SupplierRegisterInfo extends LoginInfo{
	
	@NotBlank(message = "供应商名称不能为空")
	@Length(max = 50)
	@ApiModelProperty(value="供应商名称", required=true)
	private String supplierName;
	
	@NotBlank(message = "企业名称不能为空")
	@Length(max = 50)
	@ApiModelProperty(value="企业名称", required=true)
	private String comanyName;
	
	@NotBlank(message = "申请人不能为空")
	@Length(max = 50)
	@ApiModelProperty(value="申请人", required=true)
	private String contactName;
	
	@Min(1)
	@ApiModelProperty(value="服务中心ID", required=true)
	private int serviceCenterId;
	
	@Min(1)
	@ApiModelProperty(value="省ID", required=true)
	private int regionProv;
	
	@Min(1)
	@ApiModelProperty(value="市ID", required=true)
	private int regionCity;
	
	@Min(1)
	@ApiModelProperty(value="区ID", required=true)
	private int regionArea;
	
	@NotBlank(message = "手机验证码不能为空")
	@Length(max = 6, min = 6)
	@ApiModelProperty(value="手机验证码", required=true)
	private String phoneCode;

	@NotBlank(message = "确认密码不能为空")
	@Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
	@ApiModelProperty(value="确认密码", required=true)
	private String confirmPwd;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getComanyName() {
		return comanyName;
	}

	public void setComanyName(String comanyName) {
		this.comanyName = comanyName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getServiceCenterId() {
		return serviceCenterId;
	}

	public void setServiceCenterId(int serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public int getRegionProv() {
		return regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public int getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public int getRegionArea() {
		return regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

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

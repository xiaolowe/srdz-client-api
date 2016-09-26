package cn.org.citycloud.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="供应商认证Model", description="供应商认证数据Model")
public class SupplierIdentify {

	@NotBlank(message = "法人姓名不能为空")
	@Length(max=50)
	@ApiModelProperty(value="法人姓名", required=true)
	private String supplierOwner;

	@NotBlank(message = "法人身份证号码不能为空")
	@Length(max=50)
	@ApiModelProperty(value="法人身份证号码", required=true)
	private String ownerIdentity;

	@NotBlank(message = "法人身份证照片不能为空")
	@Length(max=200)
	@ApiModelProperty(value="法人身份证照片", required=true)
	private String ownerCardImage;

	@NotBlank(message = "营业执照照片不能为空")
	@Length(max=200)
	@ApiModelProperty(value="营业执照照片", required=true)
	private String businessLicense;

	@NotBlank(message = "税务登记证不能为空")
	@Length(max=200)
	@ApiModelProperty(value="税务登记证", required=true)
	private String taxRegisterCertifacate;

	@NotBlank(message = "组织结构代码照片不能为空")
	@Length(max=200)
	@ApiModelProperty(value="组织结构代码照片", required=true)
	private String orgCode;

	public String getSupplierOwner() {
		return supplierOwner;
	}

	public void setSupplierOwner(String supplierOwner) {
		this.supplierOwner = supplierOwner;
	}

	public String getOwnerIdentity() {
		return ownerIdentity;
	}

	public void setOwnerIdentity(String ownerIdentity) {
		this.ownerIdentity = ownerIdentity;
	}

	public String getOwnerCardImage() {
		return ownerCardImage;
	}

	public void setOwnerCardImage(String ownerCardImage) {
		this.ownerCardImage = ownerCardImage;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getTaxRegisterCertifacate() {
		return taxRegisterCertifacate;
	}

	public void setTaxRegisterCertifacate(String taxRegisterCertifacate) {
		this.taxRegisterCertifacate = taxRegisterCertifacate;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}

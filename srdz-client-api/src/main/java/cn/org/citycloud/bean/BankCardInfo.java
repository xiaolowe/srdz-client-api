package cn.org.citycloud.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class BankCardInfo {

	@NotBlank(message = "持卡人不能为空")
	@Length(max = 50)
	@ApiModelProperty(value = "持卡人", required = true)
	private String cardOwner;

	@NotBlank(message = "卡号不能为空")
	@Length(max = 50)
	@ApiModelProperty(value = "卡号", required = true)
	private String cardNo;

	@NotBlank(message = "银行不能为空")
	@Length(max = 50)
	@ApiModelProperty(value = "银行", required = true)
	private String bankName;

	@NotBlank(message = "开户行不能为空")
	@Length(max = 50)
	@ApiModelProperty(value = "开户行", required = true)
	private String accountBank;

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

}

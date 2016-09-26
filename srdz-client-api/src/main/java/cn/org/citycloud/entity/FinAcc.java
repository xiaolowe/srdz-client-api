package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the fin_acc database table.
 * 
 */
@Entity
@Table(name="fin_acc")
@NamedQuery(name="FinAcc.findAll", query="SELECT f FROM FinAcc f")
public class FinAcc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fin_acc_id", unique=true, nullable=false)
	private int finAccId;

	@Column(name="account_bal", precision=10, scale=2)
	private BigDecimal accountBal;

	@Column(name="account_income", precision=10, scale=2)
	private BigDecimal accountIncome;

	@Column(name="account_no")
	private int accountNo;

	@Column(name="account_pay", precision=10, scale=2)
	private BigDecimal accountPay;

	@Column(name="account_type")
	private int accountType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public FinAcc() {
	}

	public int getFinAccId() {
		return this.finAccId;
	}

	public void setFinAccId(int finAccId) {
		this.finAccId = finAccId;
	}

	public BigDecimal getAccountBal() {
		return this.accountBal;
	}

	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}

	public BigDecimal getAccountIncome() {
		return this.accountIncome;
	}

	public void setAccountIncome(BigDecimal accountIncome) {
		this.accountIncome = accountIncome;
	}

	public int getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getAccountPay() {
		return this.accountPay;
	}

	public void setAccountPay(BigDecimal accountPay) {
		this.accountPay = accountPay;
	}

	public int getAccountType() {
		return this.accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
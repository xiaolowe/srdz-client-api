package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the growth_history database table.
 * 
 */
@Entity
@Table(name="growth_history")
@NamedQuery(name="GrowthHistory.findAll", query="SELECT g FROM GrowthHistory g")
public class GrowthHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="growth_history_id", unique=true, nullable=false)
	private int growthHistoryId;

	private int growth;

	@Column(name="member_id")
	private int memberId;

	@Column(length=200)
	private String record;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_date")
	private Date recordDate;

	public GrowthHistory() {
	}

	public int getGrowthHistoryId() {
		return this.growthHistoryId;
	}

	public void setGrowthHistoryId(int growthHistoryId) {
		this.growthHistoryId = growthHistoryId;
	}

	public int getGrowth() {
		return this.growth;
	}

	public void setGrowth(int growth) {
		this.growth = growth;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getRecord() {
		return this.record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

}
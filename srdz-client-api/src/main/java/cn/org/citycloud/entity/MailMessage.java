package cn.org.citycloud.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the mail_message database table.
 * 
 */
@Entity
@Table(name = "mail_message")
@NamedQuery(name = "MailMessage.findAll", query = "SELECT m FROM MailMessage m")
public class MailMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mail_message_id", unique = true, nullable = false)
	private int mailMessageId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "message_content", length = 500)
	private String messageContent;

	@Column(length = 50)
	private String receiver;

	@Column(name = "receiver_id")
	private int receiverId;

	@Column(length = 300)
	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "send_time")
	private Date sendTime;

	@Column(length = 50)
	private String sender;

	@Column(name = "sender_id")
	private int senderId;

	private int status;

	@Column(name = "trigger_event", length = 100)
	private String triggerEvent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	@Column(length = 200)
	private String url;

	// 关联回复信息
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "mail_message_id", referencedColumnName = "mail_message_id")
	@OrderBy("replyTime ASC")
	private Set<ReplyMessage> replyMessages;

	public MailMessage() {
	}

	public int getMailMessageId() {
		return this.mailMessageId;
	}

	public void setMailMessageId(int mailMessageId) {
		this.mailMessageId = mailMessageId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMessageContent() {
		return this.messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public int getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTriggerEvent() {
		return this.triggerEvent;
	}

	public void setTriggerEvent(String triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<ReplyMessage> getReplyMessages() {
		return replyMessages;
	}

	public void setReplyMessages(Set<ReplyMessage> replyMessages) {
		this.replyMessages = replyMessages;
	}

}
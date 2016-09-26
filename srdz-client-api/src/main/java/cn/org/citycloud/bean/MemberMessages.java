package cn.org.citycloud.bean;

import java.util.ArrayList;
import java.util.List;

import cn.org.citycloud.entity.MailMessage;
import cn.org.citycloud.entity.Message;

public class MemberMessages {

	/**
	 * 系统小心
	 */
	private List<Message> messagesList = new ArrayList<Message>();

	/**
	 * 站内信
	 */
	private List<MailMessage> mailMessageList = new ArrayList<MailMessage>();

	public List<Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<Message> messagesList) {
		this.messagesList = messagesList;
	}

	public List<MailMessage> getMailMessageList() {
		return mailMessageList;
	}

	public void setMailMessageList(List<MailMessage> mailMessageList) {
		this.mailMessageList = mailMessageList;
	}

}

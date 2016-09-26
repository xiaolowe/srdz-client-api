package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.MailMessage;
import cn.org.citycloud.entity.Message;

public interface MailMessageDao extends JpaRepository<MailMessage, Integer>, JpaSpecificationExecutor<MailMessage> {

	
	public List<MailMessage> findByMemberIdAndStatusOrderBySendTimeDesc(int memberId, int status);
	
	public List<MailMessage> findByMemberIdOrderBySendTimeDesc(int memberId);
	
	public MailMessage findByMailMessageIdAndMemberId(int id, int memberId);
}

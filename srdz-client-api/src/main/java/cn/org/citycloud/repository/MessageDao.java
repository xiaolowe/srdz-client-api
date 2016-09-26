package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Message;

public interface MessageDao extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

	public List<Message> findByReceiverIdAndPlatformAndStatus(int receiverId, int platform, int status);
	
	public List<Message> findByReceiverIdAndPlatform(int receiverId, int platform);
	
	public Message findByMessageIdAndReceiverIdAndPlatform(int id, int receiverId, int platform);
	
}


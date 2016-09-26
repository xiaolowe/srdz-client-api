package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.ReplyMessage;

public interface ReplyMessageDao extends JpaRepository<ReplyMessage, Integer>, JpaSpecificationExecutor<ReplyMessage> {

}

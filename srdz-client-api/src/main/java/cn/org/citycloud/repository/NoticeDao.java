package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Notice;

public interface NoticeDao extends JpaRepository<Notice, Integer>, JpaSpecificationExecutor<Notice> {

}

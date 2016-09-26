package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Content;

public interface ContentDao extends JpaRepository<Content, Integer>, JpaSpecificationExecutor<Content> {

	public List<Content> findByContentModuleIdOrderByCreateDateAsc(int moduleId);
	
}

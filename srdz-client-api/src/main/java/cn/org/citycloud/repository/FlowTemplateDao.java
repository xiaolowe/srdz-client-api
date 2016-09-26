package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.FlowTemplate;

public interface FlowTemplateDao extends JpaRepository<FlowTemplate, Integer>, JpaSpecificationExecutor<FlowTemplate> {

	
	public FlowTemplate findByFlowTemplateIdAndSupplierId(int id, int supplierId);
}


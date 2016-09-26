package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.ServiceCenterLevel;

public interface ServiceCenterLevelDao extends JpaRepository<ServiceCenterLevel, Integer>, JpaSpecificationExecutor<ServiceCenterLevel> {

}


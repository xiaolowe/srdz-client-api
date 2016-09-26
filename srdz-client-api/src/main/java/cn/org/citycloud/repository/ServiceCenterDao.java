package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.ServiceCenter;

public interface ServiceCenterDao extends JpaRepository<ServiceCenter, Integer>, JpaSpecificationExecutor<ServiceCenter> {

	
	public List<ServiceCenter> findByStatusOrderByServiceCenterIdDesc(int status);

	List<ServiceCenter> findByStatusAndRegionArea(int status, int regionArea);

	List<ServiceCenter> findByStatusAndRegionCity(int status, int regionCity);
}

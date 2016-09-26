package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.FlowCity;

public interface FlowCityDao extends JpaRepository<FlowCity, Integer>, JpaSpecificationExecutor<FlowCity> {

}

package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.org.citycloud.entity.FlowInfo;

public interface FlowInfoDao extends JpaRepository<FlowInfo, Integer>, JpaSpecificationExecutor<FlowInfo> {

	@Query(value="SELECT "
			+ "flow_info.flow_goods,"
			+ "flow_info.flow_price,"
			+ "flow_info.add_flow_goods,"
			+ "flow_info.add_goods_price "
			+ "FROM "
			+ "flow_info "
			+ "LEFT JOIN flow_city ON flow_info.flow_info_id = flow_city.flow_info_id "
			+ "WHERE "
			+ "flow_info.flow_template_id = ?1 "
			+ "AND flow_city.flow_city_code = ?2", nativeQuery=true)
	public Object[] findFlownInfoByRegionCity(int tempId, int regionCity);
	
	public FlowInfo findByFlowTemplateIdAndDefaultFlag(int tempId, int defaultFlag);
	
}

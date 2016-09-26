package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.org.citycloud.entity.Good;

public interface GoodsDao extends JpaRepository<Good, Integer>, JpaSpecificationExecutor<Good> {

	@Query(value="SELECT DISTINCT region_prov, region_prov_name FROM goods WHERE goods_class_two_id = ?1", nativeQuery = true)
	List<Object> findProductPlaces(int secondClass);
	
	@Query(value="SELECT DISTINCT region_prov, region_prov_name FROM goods WHERE goods_class_two_id = ?1"
			+ " And discountFlg = ?2", nativeQuery = true)
	List<Object> findProductPlaces(int secondClass, int discountFlg);
	
}

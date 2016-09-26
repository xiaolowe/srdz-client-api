package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.org.citycloud.entity.Activity;

public interface ActivityDao extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {

	@Query(value = "SELECT * FROM activity WHERE start_time < now() AND end_time > now() AND status = 1 order by create_date desc", nativeQuery = true)
	List<Activity> findActivityList();
	
	
	@Query(value = "SELECT * FROM activity WHERE activity_id >=19 order by create_date desc", nativeQuery = true)
	List<Activity> TestfindActivityList();
}

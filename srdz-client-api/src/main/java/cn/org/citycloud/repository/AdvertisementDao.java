package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.org.citycloud.entity.Advertisement;

public interface AdvertisementDao extends JpaRepository<Advertisement, Integer>, JpaSpecificationExecutor<Advertisement> {

	@Query(value = "SELECT * FROM advertisement WHERE start_time < now() AND end_time > now() AND status = 1", nativeQuery = true)
	public List<Advertisement> findAdvertisementList();
	
}

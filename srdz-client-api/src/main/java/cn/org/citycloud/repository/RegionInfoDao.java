package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.org.citycloud.entity.RegionInfo;

public interface RegionInfoDao extends JpaRepository<RegionInfo, Integer>, JpaSpecificationExecutor<RegionInfo> {

	public List<RegionInfo> findByRegionLevel(int regionLevel);

	@Query(value = "SELECT * FROM region_info ri WHERE ri.region_code LIKE ?2% AND ri.region_level=?1", nativeQuery = true)
	public List<RegionInfo> find(int regionLevel, String regionCode);

	public RegionInfo findByRegionCode(int regionCode);
	
	public long countByRegionCode(int regionCode);
}

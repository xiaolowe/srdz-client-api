package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.GrowthHistory;

public interface GrowthHistoryDao extends JpaRepository<GrowthHistory, Integer>, JpaSpecificationExecutor<GrowthHistory> {

}

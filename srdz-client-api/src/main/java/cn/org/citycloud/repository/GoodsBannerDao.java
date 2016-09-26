package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.GoodsBanner;

public interface GoodsBannerDao extends JpaRepository<GoodsBanner, Integer>, JpaSpecificationExecutor<GoodsBanner> {

}

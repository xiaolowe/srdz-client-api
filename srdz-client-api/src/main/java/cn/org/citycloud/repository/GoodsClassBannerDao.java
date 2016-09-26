package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.GoodsClassBanner;

public interface GoodsClassBannerDao extends JpaRepository<GoodsClassBanner, Integer>, JpaSpecificationExecutor<GoodsClassBanner> {

	public List<GoodsClassBanner> findByGoodsClassId(int goodsClassId);
	
}

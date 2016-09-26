package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.DistGood;
import cn.org.citycloud.entity.DistShopGood;

public interface DistShopGoodDao extends JpaRepository<DistShopGood, Integer>, JpaSpecificationExecutor<DistShopGood> {

	public long countByGoodsIdAndMemberId(int goodsId, int memberId);
	
	public DistShopGood findByGoodsIdAndMemberId(int goodsId, int memberId);
	
	// 分销全部商品
	public long countAllDistGoodsByMemberId(int memberId);
	
	// 分销上新商品(7天内上架)
	public long countNewDistGoodsByMemberId(int memberId);
	
}

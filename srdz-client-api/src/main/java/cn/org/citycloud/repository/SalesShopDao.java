package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.SalesShop;

public interface SalesShopDao extends JpaRepository<SalesShop, Integer>, JpaSpecificationExecutor<SalesShop> {

	public SalesShop findByMemberId(int memberId);
	
}

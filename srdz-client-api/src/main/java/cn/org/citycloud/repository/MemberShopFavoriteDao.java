package cn.org.citycloud.repository;

import java.util.List;

import cn.org.citycloud.entity.MemberGoodsFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.MemberShopFavorite;

public interface MemberShopFavoriteDao extends JpaRepository<MemberShopFavorite, Integer>, JpaSpecificationExecutor<MemberShopFavorite> {

	
	public List<MemberShopFavorite> findByMemberId(int memberId);

	MemberShopFavorite findByMemberIdAndSupplierId(int memberId, int supplierId);
}

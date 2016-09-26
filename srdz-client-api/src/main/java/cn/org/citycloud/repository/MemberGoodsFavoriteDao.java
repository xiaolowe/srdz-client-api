package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.MemberGoodsFavorite;

public interface MemberGoodsFavoriteDao  extends JpaRepository<MemberGoodsFavorite, Integer>, JpaSpecificationExecutor<MemberGoodsFavorite> {

	
	public List<MemberGoodsFavorite> findByMemberId(int memberId);

	MemberGoodsFavorite findByMemberIdAndGoodsId(int memberId, int goodsId);
}

package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.MemberCoupon;

public interface MemberCouponDao extends JpaRepository<MemberCoupon, Integer>, JpaSpecificationExecutor<MemberCoupon> {
	
	public List<MemberCoupon> findByMemberIdOrderByCreateDateDesc(int memberId);
	
	public List<MemberCoupon> findByMemberIdAndCouponStatusOrderByCreateDateDesc(int memberId, int couponStatus);
	
	public MemberCoupon findByMemberIdAndCouponId(int memberId, int couponId);
	
	public long countByMemberIdAndCouponId(int memberId, int couponId);
	
	public MemberCoupon findByOrderId(int orderId);
}

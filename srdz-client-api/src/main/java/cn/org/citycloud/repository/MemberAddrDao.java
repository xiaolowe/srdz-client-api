package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.MemberAddr;

public interface MemberAddrDao extends JpaRepository<MemberAddr, Integer>, JpaSpecificationExecutor<MemberAddr> {

	public List<MemberAddr> findByMemberIdOrderByUpdateTimeDesc(int memberId);
	
	public MemberAddr findByMemberAddrIdAndMemberId(int id, int memberId);
	
	public MemberAddr findByMemberIdAndDefaultFlag(int memberId, int defaultFlag);
	
	public long countByMemberId(int memberId);
	
	public List<MemberAddr> findByMemberIdAndMemberAddrIdNot(int memberId, int id);
	
}

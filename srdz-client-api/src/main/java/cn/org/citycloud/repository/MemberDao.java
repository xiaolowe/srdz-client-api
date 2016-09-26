package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Member;

public interface MemberDao extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member> {

	public Member findByMemberPhoneAndMemberPwd(String userPhone,
			String userPwd);

	public Member findByMemberPhone(String userPhone);
	
	public long countByMemberPhone(String userPhone);
}

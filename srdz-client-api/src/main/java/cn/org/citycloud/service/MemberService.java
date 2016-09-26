package cn.org.citycloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.entity.Member;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.MemberDao;

/**
 * 会员Service
 * @author lanbo
 *
 */
@Component
public class MemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	/**
	 * 获取会员信息
	 */
	public Member getMember(int memberId) {
		
		
		return memberDao.getOne(memberId);
	}
	
	
	
	/**
	 * 验证会员是否有效用户
	 * @throws BusinessErrorException 
	 * 
	 */
	public boolean validateMemberStatus(int memberId) {
		
		Member member  = memberDao.findOne(memberId);
		
		if (member == null) {
			return false;
		}
		
		if (Constants.MEMBER_STATE_CLOSED == member.getMemberStatus()) {
			return false;
		}
		
		return true;
	}
	

}

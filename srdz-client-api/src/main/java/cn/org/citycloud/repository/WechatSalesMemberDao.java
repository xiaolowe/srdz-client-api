package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.WechatSalesMember;

public interface WechatSalesMemberDao extends JpaRepository<WechatSalesMember, Integer>, JpaSpecificationExecutor<WechatSalesMember> {

	
	public WechatSalesMember findByOpenId(String openId);
}

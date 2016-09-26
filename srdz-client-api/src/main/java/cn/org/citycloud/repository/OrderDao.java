package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

	
	public List<Order> findByPayId(int payId);
	
	public Order findByOrderIdAndMemberId(int orderId, int memberId);
	
	public List<Order> findByMemberIdOrderByCreateTimeDesc(int memberId);
	
	public List<Order> findByMemberIdAndOrderStatusOrderByCreateTimeDesc(int memberId, int status);
}

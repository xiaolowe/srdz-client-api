package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Cash;

public interface CashDao extends JpaRepository<Cash, Integer>, JpaSpecificationExecutor<Cash>{
    Cash findByOrderId(int orderId);
}

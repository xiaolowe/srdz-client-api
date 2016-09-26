package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.OrderInfo;

public interface OrderInfoDao extends JpaRepository<OrderInfo, Integer>, JpaSpecificationExecutor<OrderInfo> {

}
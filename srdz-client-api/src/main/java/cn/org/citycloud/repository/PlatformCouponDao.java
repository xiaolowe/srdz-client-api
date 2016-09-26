package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.PlatformCoupon;

public interface PlatformCouponDao extends JpaRepository<PlatformCoupon, Integer>, JpaSpecificationExecutor<PlatformCoupon> {

}
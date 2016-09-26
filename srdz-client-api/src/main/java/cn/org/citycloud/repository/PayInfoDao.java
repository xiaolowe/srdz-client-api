package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.PayInfo;

public interface PayInfoDao extends JpaRepository<PayInfo, Integer>, JpaSpecificationExecutor<PayInfo> {

}

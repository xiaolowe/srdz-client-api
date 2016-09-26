package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.SalesMember;

public interface SalesMemberDao extends JpaRepository<SalesMember, Integer>, JpaSpecificationExecutor<SalesMember> {

}

package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Supplier;

public interface SupplierDao extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {

}


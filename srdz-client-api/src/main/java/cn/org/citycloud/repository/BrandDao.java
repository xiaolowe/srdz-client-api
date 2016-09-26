package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.Brand;

public interface BrandDao extends JpaRepository<Brand, Integer>, JpaSpecificationExecutor<Brand> {

}

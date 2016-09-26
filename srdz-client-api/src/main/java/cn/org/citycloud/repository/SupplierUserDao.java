package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.SupplierUser;

public interface SupplierUserDao extends JpaRepository<SupplierUser, Integer>, JpaSpecificationExecutor<SupplierUser> {

	public SupplierUser findByPhone(String phone);
	
}

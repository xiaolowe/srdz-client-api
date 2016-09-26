package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.FinAcc;

public interface FinAccDao extends JpaRepository<FinAcc, Integer>, JpaSpecificationExecutor<FinAcc> {

	public FinAcc findByAccountNoAndAccountType(int no, int accType);
}

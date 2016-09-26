package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.EvaluateGood;

public interface EvaluateGoodDao extends JpaRepository<EvaluateGood, Integer>, JpaSpecificationExecutor<EvaluateGood> {

}

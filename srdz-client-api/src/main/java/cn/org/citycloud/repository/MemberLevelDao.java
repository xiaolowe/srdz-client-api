package cn.org.citycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.MemberLevel;

public interface MemberLevelDao extends JpaRepository<MemberLevel, Integer>, JpaSpecificationExecutor<MemberLevel> {

}

package cn.org.citycloud.repository;

import cn.org.citycloud.entity.Content;
import cn.org.citycloud.entity.DiscountGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/7/11 17:16
 */
public interface DiscountGoodDao extends JpaRepository<DiscountGood, Integer>, JpaSpecificationExecutor<DiscountGood> {
}

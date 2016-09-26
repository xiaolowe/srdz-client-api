package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.org.citycloud.entity.SalesOrderInfo;

public interface SalesOrderInfoDao extends JpaRepository<SalesOrderInfo, Integer>, JpaSpecificationExecutor<SalesOrderInfo> {

	@Query(value="SELECT "
			+ "orders.order_id,"
			+ "orders.order_time,"
			+ "orders.order_price,"
			+ "sales_order_info.sale_amount"
			+ " FROM "
			+ " sales_order_info "
			+ " LEFT JOIN orders ON sales_order_info.order_id = orders.order_id "
			+ " WHERE "
			+ "sales_order_info.member_id = ?1 AND orders.order_status = ?2", nativeQuery=true)
	public List<Object> findSalesOrderInfo(int memberId, int status);
	
	
}

package cn.org.citycloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.entity.ShoppingCart;

public interface ShoppingCartDao extends JpaRepository<ShoppingCart, Integer>, JpaSpecificationExecutor<ShoppingCart> {

	// 购物车所有商品
	public List<ShoppingCart> findByMemberIdOrderBySupplierId(int memberId);
	
	// 购物车商品
	public ShoppingCart findByMemberIdAndGoodsId(int memberId, int goodsId);
	
	// 购物车商品
	public ShoppingCart findByShoppingCartIdAndMemberId(int id, int memberId);
	
	// 分销购物车商品
	public List<ShoppingCart> findByMemberIdAndOpenIdOrderByCreateTimeDesc(int memberId, String openId);
	
	
	// 分销购物车商品
	public ShoppingCart findByOpenIdAndGoodsId(String openId, int goodsId);
	
	// 购物车商品
	public ShoppingCart findByShoppingCartIdAndOpenId(int id, String openId);
}

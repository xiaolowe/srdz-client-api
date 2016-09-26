package cn.org.citycloud.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.BatchDelete;
import cn.org.citycloud.bean.CartGoods;
import cn.org.citycloud.bean.ShoppingCartGroupByShop;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.ShoppingCart;
import cn.org.citycloud.entity.Supplier;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.GoodsDao;
import cn.org.citycloud.repository.ShoppingCartDao;
import cn.org.citycloud.repository.SupplierDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 购物车控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/shoppingCarts")
@Api(tags = "购物车", position = 8, value = "/shoppingCarts", description = "购物车模块", consumes = "application/json")
public class ShoppingCartController extends BaseController {

	@Autowired
	private ShoppingCartDao cartDao;
	
	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 获取购物车商品
	 * 
	 * @return
	 * @throws BusinessErrorException
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取购物车商品", notes = "获取购物车商品", response = ShoppingCartGroupByShop.class, responseContainer = "List")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object getShoppingCart() throws BusinessErrorException {

		return getShoppingCartList();
	}

	/**
	 * 添加购物车商品
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加购物车商品", notes = "添加购物车商品")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void addGoodsToCart(@Valid @RequestBody CartGoods cartGoods) throws BusinessErrorException {

		int goodsId = cartGoods.getGoodsId();
		// 商品信息
		Good goodItem = goodsDao.findOne(goodsId);

		if (goodItem == null) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品信息不存在");
		}

		// 商品状态
		int goodState = goodItem.getGoodsStatus();

		if (Constants.GOODS_STATE_NORMAL != goodState) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架或者禁售。");
		}
		// 自动下架时间
		Date offlineTime = goodItem.getAutoDownTime();
		Date now = new Date();

		if (offlineTime.getTime() < now.getTime()) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架。");
		}

		// 商品已经没有库存了
		if (goodItem.getSurplus() <= 0) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已经售罄。");
		}

//		if (goodItem.getSurplus() < cartGoods.getGoodsNum()) {
//			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "商品库存不够。");
//		}

		//TODO
//		BigDecimal goodsPrice = BigDecimal.ZERO;
//		if(1 == goodItem.getDiscountFlg()) {
//			BigDecimal goodsPrice = goodItem.get
//			
//		} else {
//			
//		}

		BigDecimal num = new BigDecimal(cartGoods.getGoodsNum());
		BigDecimal goodsPayPrice = goodItem.getSalePrice().multiply(num);

		ShoppingCart goods = cartDao.findByMemberIdAndGoodsId(getMemberId(), goodsId);
		// 购物车中已经有的商品
		if (goods != null) {
			goods.setGoodsNum(cartGoods.getGoodsNum() + goods.getGoodsNum());

			// 实际成交价
			goods.setGoodsPayPrice(goods.getGoodsPayPrice().add(goodsPayPrice));
			goods.setUpdateTime(now);

			cartDao.save(goods);
		} else {
			ShoppingCart cartGood = new ShoppingCart();
			cartGood.setGoodsId(cartGoods.getGoodsId());
			cartGood.setSupplierId(goodItem.getSupplierId());
			cartGood.setMemberId(getMemberId());

			cartGood.setGoodsName(goodItem.getGoodsName());
			cartGood.setGoodsPrice(goodItem.getSalePrice());
			cartGood.setGoodsNum(cartGoods.getGoodsNum());
			cartGood.setGoodsImage(goodItem.getGoodsImage());
			// 实际成交价
			cartGood.setGoodsPayPrice(goodsPayPrice);
			cartGood.setGoodsSpec(goodItem.getStandard());
			// 状态
			cartGood.setCartStatus(3);

			cartGood.setCreateTime(now);
			cartGood.setUpdateTime(now);
			cartDao.save(cartGood);
		}

	}

	/**
	 * 删除购物车中的商品
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除购物车中的商品", notes = "删除购物车中的商品", response = ShoppingCartGroupByShop.class, responseContainer = "List")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object deleteGoodsFromCart(@PathVariable int id) {

		ShoppingCart cart = cartDao.findByShoppingCartIdAndMemberId(id, getMemberId());

		if (cart != null)

			cartDao.delete(cart);
		
		return getShoppingCartList();

	}

	/**
	 * 批量删除购物车中的商品
	 */
	@RequestMapping(value = "/batchDelele", method = RequestMethod.POST)
	@ApiOperation(value = "批量删除购物车中的商品", notes = "批量删除购物车中的商品", response = ShoppingCartGroupByShop.class, responseContainer = "List")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object batchDeleteGoodsFromCart(@Valid @RequestBody List<BatchDelete> ids) {

		for (BatchDelete batchDelete : ids) {
			ShoppingCart cart = cartDao.findByShoppingCartIdAndMemberId(batchDelete.getId(), getMemberId());
			if (cart != null)
				cartDao.delete(cart);
		}
		
		return getShoppingCartList();
	}

	/**
	 * 修改商品数量
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/id/{id}/number/{num}", method = RequestMethod.PUT)
	@ApiOperation(value = "修改商品数量", notes = "修改商品数量")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void editCartGoods(@PathVariable int id, @PathVariable int num) throws BusinessErrorException {

		ShoppingCart cartGoods = cartDao.findByShoppingCartIdAndMemberId(id, getMemberId());
		if (cartGoods == null) {
			throw new BusinessErrorException(ErrorCodes.NO_DATA, "此商品不存在或已删除！");
		}

		// 商品信息
		Good goodItem = goodsDao.findOne(cartGoods.getGoodsId());

		if (goodItem == null) {

			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品信息不存在");
		}

		// 商品状态
		int goodState = goodItem.getGoodsStatus();

		if (Constants.GOODS_STATE_NORMAL != goodState) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架或者禁售");
		}
		// 自动下架时间
		Date offlineTime = goodItem.getAutoDownTime();
		Date now = new Date();

		if (offlineTime.getTime() < now.getTime()) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架");
		}

		cartGoods.setGoodsNum(num);

		// 商品数量
		BigDecimal numDec = new BigDecimal(num);
		// 实际成交价
		cartGoods.setGoodsPayPrice(goodItem.getSalePrice().multiply(numDec));

		cartGoods.setUpdateTime(now);

		cartDao.save(cartGoods);
	}
	
	/**
	 * @return 购物车商品列表
	 */
	private List<ShoppingCartGroupByShop> getShoppingCartList() {
		List<ShoppingCart> cartGoods = cartDao.findByMemberIdOrderBySupplierId(getMemberId());

		List<ShoppingCartGroupByShop> rstList = new ArrayList<ShoppingCartGroupByShop>();
		
		Set<Integer> supplierSet = new HashSet<Integer>();
		List<Integer> supplierIdList = new ArrayList<Integer>();
		for (ShoppingCart cartGood : cartGoods) {
			supplierIdList.add(cartGood.getSupplierId());
		}
		
		supplierSet.addAll(supplierIdList);
		for (Integer supplierId : supplierSet) {
			Supplier supplier = supplierDao.findOne(supplierId);
			ShoppingCartGroupByShop groupShopCart = new ShoppingCartGroupByShop();
			groupShopCart.setSupplierId(supplierId);
			groupShopCart.setSupplierName(supplier.getSupplierName());
			
			List<ShoppingCart> shopCartList = new ArrayList<ShoppingCart>();
			for (ShoppingCart cartGood : cartGoods) {
				
				if(cartGood.getSupplierId() == supplierId) {
					shopCartList.add(cartGood);
				}
			}
			groupShopCart.setCartGoods(shopCartList);
			
			rstList.add(groupShopCart);
		}
		
		return rstList;
	}

}

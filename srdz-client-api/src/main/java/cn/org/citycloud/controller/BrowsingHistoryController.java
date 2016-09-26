package cn.org.citycloud.controller;

import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.GoodsBanner;
import cn.org.citycloud.repository.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.fabric.xmlrpc.base.Array;

import cn.org.citycloud.bean.GoodsBrowsingHistory;
import cn.org.citycloud.bean.MemberFavorite;
import cn.org.citycloud.bean.ShoppingCartGroupByShop;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.exception.BusinessErrorException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 我的历史浏览控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/accounts/browsingHistories")
@Api(tags = "个人中心", position = 8, value = "/accounts/browsingHistories", description = "我的历史浏览模块", consumes = "application/json")
public class BrowsingHistoryController extends BaseController {

	@Autowired
	private MemcachedClient cacheClient;

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 获取我的商品历史浏览记录
	 * 
	 * @return
	 * @throws BusinessErrorException
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取我的商品历史浏览记录", notes = "获取我的商品历史浏览记录", response = ShoppingCartGroupByShop.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object getBrowsingHistories()
			throws BusinessErrorException, TimeoutException, InterruptedException, MemcachedException {

		String historyCacheKey = "BrowsingHistory-" + getMemberId();

		Object historyObj = cacheClient.get(historyCacheKey);

		List<GoodsBrowsingHistory> newHistList = new ArrayList<GoodsBrowsingHistory>();

		if (historyObj != null) {
			newHistList = (List<GoodsBrowsingHistory>) historyObj;

		}
		newHistList = newHistList.stream().sorted((p1, p2) -> p2.getAddDate().compareTo(p1.getAddDate())).collect(Collectors.toList());
		return newHistList;
	}

	/**
	 * 添加商品历史浏览记录
	 * 
	 * @return
	 * @throws BusinessErrorException
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加商品历史浏览记录", notes = "添加商品历史浏览记录")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void addBrowsingHistories(@Valid @RequestBody GoodsBrowsingHistory history)
			throws BusinessErrorException, TimeoutException, InterruptedException, MemcachedException {
		Good good = goodsDao.findOne(history.getGoodsId());
		if (good == null) {
			throw new BusinessErrorException(ErrorCodes.NOT_FOUND, "添加商品历史浏览记录失败");
		}
		Set<GoodsBanner> bannerSet = good.getGoodsBanners();
		Iterator<GoodsBanner> iterator = bannerSet.iterator();
		if (iterator.hasNext()) {
			String imagePath = iterator.next().getBannerImage();
			if (!StringUtils.isEmpty(imagePath)) {
				history.setGoodsImage(imagePath);
			}
		}
		history.setAddDate(new Date());
		history.setGoodsName(good.getGoodsName());
		history.setSalePrice(good.getSalePrice());
		history.setStardard(good.getStandard());

		String historyCacheKey = "BrowsingHistory-" + getMemberId();

		Object historyObj = cacheClient.get(historyCacheKey);

		List<GoodsBrowsingHistory> newHistList = new ArrayList<GoodsBrowsingHistory>();
		if (historyObj == null) {
			newHistList.add(history);
		} else {

			newHistList = (List<GoodsBrowsingHistory>) historyObj;

			boolean hasHistory = false;
			for (GoodsBrowsingHistory goodsBrowsingHistory : newHistList) {

				if (goodsBrowsingHistory.getGoodsId() == history.getGoodsId()) {
					hasHistory = true;
				}
			}

			if (!hasHistory) {
				if (newHistList.size() >= 10) {
					newHistList.remove(0);
				}
				newHistList.add(history);
			}
		}
		cacheClient.set(historyCacheKey, 7200, newHistList);
	}

}

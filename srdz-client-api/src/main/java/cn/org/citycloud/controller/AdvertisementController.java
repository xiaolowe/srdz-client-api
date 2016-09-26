package cn.org.citycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.entity.Advertisement;
import cn.org.citycloud.repository.AdvertisementDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页广告banner控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/advertisements")
@Api(tags = "首页广告", position = 1, value = "/advertisements", description = "首页广告模块", consumes = "application/json")
public class AdvertisementController {

	@Autowired
	private AdvertisementDao advertisementDao;

	/**
	 * 获取首页广告banner信息
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "首页广告banner信息", notes = "获取首页广告banner信息", response = Advertisement.class, responseContainer = "List")
	public Object getAdvertisementList() {

		return advertisementDao.findAdvertisementList();
	}
}

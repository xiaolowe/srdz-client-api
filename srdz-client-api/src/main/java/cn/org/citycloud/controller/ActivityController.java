package cn.org.citycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.entity.Activity;
import cn.org.citycloud.repository.ActivityDao;
import cn.org.citycloud.repository.MemberCouponDao;
import cn.org.citycloud.repository.PlatformCouponDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 营销活动控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/activities")
@Api(tags = "优惠劵", position = 8, value = "/activities", description = "营销活动模块", consumes = "application/json")
public class ActivityController {

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private PlatformCouponDao platformCouponDao;

	@Autowired
	private MemberCouponDao memberCouponDao;

	/**
	 * 获取优惠券活动列表信息
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取优惠券活动列表信息", notes = "获取优惠券活动列表信息", response = Activity.class, responseContainer = "List")
	public Object getActivities() {

		return activityDao.findActivityList();
	}

	/**
	 * 获取优惠券活动信息
	 * 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取优惠券活动信息", notes = "获取优惠券活动信息", response = Activity.class)
	public Object getNoticeDetail(@ApiParam(value = "优惠券活动ID", required = true) @PathVariable int id) {

		return activityDao.findOne(id);
	}

}

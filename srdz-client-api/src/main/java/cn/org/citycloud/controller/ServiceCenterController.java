package cn.org.citycloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.ServiceCenterItem;
import cn.org.citycloud.entity.ServiceCenter;
import cn.org.citycloud.repository.ServiceCenterDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 服务中心控制器
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/serviceCenters")
@Api(tags="服务中心", position=8, value = "/serviceCenters", description = "服务中心模块", consumes="application/json")
public class ServiceCenterController {
	
	@Autowired
	private ServiceCenterDao serviceCenterDao;
	
	/**
	 * 获取服务中心列表信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取服务中心列表信息", notes = "获取服务中心列表信息", response = Map.class)
	public Object getServiceCenterList() {
		
		List<ServiceCenter> serviceCenterList = serviceCenterDao.findByStatusOrderByServiceCenterIdDesc(1);
		
		List<ServiceCenterItem> serviceCenterItems = new ArrayList<ServiceCenterItem>();
		for (ServiceCenter serviceCenter : serviceCenterList) {
			ServiceCenterItem item = new ServiceCenterItem();
			BeanUtils.copyProperties(serviceCenter, item);
			
			serviceCenterItems.add(item);
		}
		
		return serviceCenterItems;
	}

	@RequestMapping(value="/area/{areaCode}",method=RequestMethod.GET)
	@ApiOperation(value = "根据区域id获取服务中心列表信息", notes = "根据区域id获取服务中心列表信息", response = Map.class)
	public Object getServiceCenterByArea(@ApiParam(name="areaCode",value="区域id",required=true) @PathVariable int areaCode) {
		List<ServiceCenter> serviceCenterList = serviceCenterDao.findByStatusAndRegionArea(1, areaCode);

		List<ServiceCenterItem> serviceCenterItems = new ArrayList<>();
		for (ServiceCenter serviceCenter : serviceCenterList) {
			ServiceCenterItem item = new ServiceCenterItem();
			BeanUtils.copyProperties(serviceCenter, item);

			serviceCenterItems.add(item);
		}

		return serviceCenterItems;
	}

	@RequestMapping(value="/city/{cityCode}",method=RequestMethod.GET)
	@ApiOperation(value = "根据市id获取服务中心列表信息", notes = "根据市id获取服务中心列表信息", response = Map.class)
	public Object getServiceCenterByCity(@ApiParam(name="cityCode",value="市id",required=true) @PathVariable int cityCode) {
		List<ServiceCenter> serviceCenterList = serviceCenterDao.findByStatusAndRegionCity(1, cityCode);

		List<ServiceCenterItem> serviceCenterItems = new ArrayList<>();
		for (ServiceCenter serviceCenter : serviceCenterList) {
			ServiceCenterItem item = new ServiceCenterItem();
			BeanUtils.copyProperties(serviceCenter, item);

			serviceCenterItems.add(item);
		}

		return serviceCenterItems;
	}

}

package cn.org.citycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.entity.RegionInfo;
import cn.org.citycloud.repository.RegionInfoDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 地区控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/regions")
@Api(tags = "共通接口", position = 1, value = "/regions", description = "省市区模块", consumes = "application/json")
public class RegionInfoController {
	
    @Autowired
    private RegionInfoDao regionInfoDao;
    
    /**
     * 获取省级信息
     * 
     * @param level
     * @return
     */
    @RequestMapping(value = "/prov", method = RequestMethod.GET)
    @ApiOperation(value = "获取省级信息", notes = "获取省级信息", response = RegionInfo.class, responseContainer = "List")
    public Object getProv()
    {
        return regionInfoDao.findByRegionLevel(1);
    }
    
    /**
     * 获取市级信息
     * 
     * @param level
     * @return
     */
    @RequestMapping(value = "/city/{code}", method = RequestMethod.GET)
    @ApiOperation(value = "获取省级信息", notes = "获取省级信息", response = RegionInfo.class, responseContainer = "List")
    public Object getCity(@PathVariable int code)
    {
        String regionCode = code / 10000 + "";
        return regionInfoDao.find(2, regionCode);
    }
    
    /**
     * 获取区级信息
     * 
     * @param level
     * @return
     */
    @RequestMapping(value = "/area/{code}", method = RequestMethod.GET)
    @ApiOperation(value = "获取区级信息", notes = "获取区级信息", response = RegionInfo.class, responseContainer = "List")
    public Object getRegion(@PathVariable int code)
    {
        String regionCode = code / 100 + "";
        return regionInfoDao.find(3, regionCode);
        
    }

}

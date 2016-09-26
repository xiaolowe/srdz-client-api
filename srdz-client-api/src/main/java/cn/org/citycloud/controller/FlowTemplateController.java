package cn.org.citycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.entity.Notice;
import cn.org.citycloud.repository.FlowTemplateDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 物流模板
 * @author lanbo
 *
 */
//@RestController
//@RequestMapping(value = "/flowTemplate")
//@Api(tags = "共通接口", position = 1, value = "/flowTemplate", description = "物流模板模块", consumes = "application/json")
//public class FlowTemplateController {
//	
//	@Autowired
//	private FlowTemplateDao flowTemplateDao;
//	
//	/**
//	 * 获取物流模板信息
//	 * 
//	 */
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	@ApiOperation(value = "获取物流模板信息", notes = "获取物流模板信息", response = Notice.class)
//	public Object getNoticeDetail(@ApiParam(value = "公告ID", required = true) @PathVariable int id) {
//
//		return flowTemplateDao.findOne(id);
//	}
//	
//
//}

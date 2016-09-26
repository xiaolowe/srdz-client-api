package cn.org.citycloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.entity.Content;
import cn.org.citycloud.repository.ContentDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 内容管理控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/contents")
@Api(tags = "系统公告", position = 1, value = "/contents", description = "内容模块", consumes = "application/json")
public class ContentController {

	@Autowired
	private ContentDao contentDao;

	/**
	 * 获取首页尾部内容
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取首页尾部内容", notes = "获取首页尾部内容", response = Content.class, responseContainer = "List")
	public Object getContents() {

		List<Content> helpContent = contentDao.findByContentModuleIdOrderByCreateDateAsc(2);

		List<Content> aboutContent = contentDao.findByContentModuleIdOrderByCreateDateAsc(1);

		List<Content> registerContent = contentDao.findByContentModuleIdOrderByCreateDateAsc(3);

		List<Content> guideContent = contentDao.findByContentModuleIdOrderByCreateDateAsc(4);

		Map<String, List<Content>> data = new HashMap<>();
		data.put("帮助中心", helpContent);
		data.put("关于我们", aboutContent);
		data.put("注册条款", registerContent);
		data.put("使用指南", guideContent);
		return data;

	}

}

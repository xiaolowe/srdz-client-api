package cn.org.citycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.entity.Notice;
import cn.org.citycloud.repository.NoticeDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统公告控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/notices")
@Api(tags = "系统公告", position = 1, value = "/notices", description = "首页公告模块", consumes = "application/json")
public class NoticeController {
	
	@Autowired
	private NoticeDao noticeDao;
	
	/**
	 * 获取首页系统公告信息
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取首页系统公告信息", notes = "获取首页系统公告信息", response = Notice.class, responseContainer = "List")
	public Object getNotices() {

		// 时间倒序排序
		Sort sort = new Sort(Sort.Direction.DESC, "createDate");
		
		return noticeDao.findAll(sort);
	}
	
	/**
	 * 获取首页系统公告信息
	 * 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取系统公告详情信息", notes = "获取系统公告详情信息", response = Notice.class)
	public Object getNoticeDetail(@ApiParam(value = "公告ID", required = true) @PathVariable int id) {

		return noticeDao.findOne(id);
	}

}

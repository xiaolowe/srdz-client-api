//package cn.org.citycloud.controller;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import cn.org.citycloud.entity.Notice;
//import cn.org.citycloud.entity.WechatSalesMember;
//import cn.org.citycloud.repository.WechatSalesMemberDao;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping(value = "/tests")
//public class TestController {
//	
//	@Autowired
//	private WechatSalesMemberDao wechatSalesMemberDao;
//	
//
//	@RequestMapping(method = RequestMethod.POST)
//	@ApiOperation(value = "获取首页系统公告信息", notes = "获取首页系统公告信息", response = Notice.class, responseContainer = "List")
//	public void addUtf8Mb4Test() {
//		WechatSalesMember member = new WechatSalesMember();
//		
//		member.setRegisterTime(new Date());
//		member.setStatus(1);
//		
//		// 火星文
//		member.setWechatAliasname("❤ ●•۰");
//		
//		wechatSalesMemberDao.save(member);
//		
//	}
//}

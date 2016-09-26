package cn.org.citycloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.deser.Deserializers.Base;

import cn.org.citycloud.core.BaseController;
import io.swagger.annotations.Api;

/**
 * 我的消息控制器
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/accounts/information")
@Api(tags="个人中心", position=8, value = "/accounts/information", description = "我的消息模块", consumes="application/json")
public class InformationController extends BaseController {
	
	

}

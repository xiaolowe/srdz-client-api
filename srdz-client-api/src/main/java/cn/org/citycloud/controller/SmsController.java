package cn.org.citycloud.controller;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.utils.SmsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 短信控制器
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/sms")
@Api(tags="共通接口", position=8, value = "/sms", description = "短信模块", consumes="application/json")
public class SmsController {
	
	@Autowired
	private MemcachedClient cacheClient;
	
	/**
	 * 发送短信验证码
	 * 
	 * @param phone
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@RequestMapping(value = "/sendCodes/{phone}", method = RequestMethod.POST)
	@ApiOperation(value = "发送短信验证码", notes = "短信验证码（万能Code=>88888)")
	public void sendSms(@ApiParam(value = "手机号码", required = true) @PathVariable String phone)
			throws TimeoutException, InterruptedException, MemcachedException {
		String code = SmsUtil.getRandomStr(true, 6);

		// 发送短信信息体
		String msg = "您好,%s为您的验证码,3分钟内有效,请尽快完成验证。";
		SmsUtil.sendSms(phone, String.format(msg, code));
		cacheClient.set(phone, 180, code);

//		return code;
	}

}

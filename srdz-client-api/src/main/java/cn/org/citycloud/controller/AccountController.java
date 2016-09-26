package cn.org.citycloud.controller;

import cn.org.citycloud.bean.ForgetPwdBean;
import cn.org.citycloud.bean.RegisterInfo;
import cn.org.citycloud.bean.UserAvatarBean;
import cn.org.citycloud.bean.UserCompleteInfo;
import cn.org.citycloud.bean.UserPwdInfo;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.Member;
import cn.org.citycloud.entity.RegionInfo;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.MemberDao;
import cn.org.citycloud.repository.RegionInfoDao;
import io.swagger.annotations.*;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * 我的账户控制器
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/accounts")
@Api(tags="个人中心", position=8, value = "/accounts", description = "我的账户模块", consumes="application/json")
public class AccountController  extends BaseController {
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MemcachedClient cacheClient;
	
	@Autowired
	private RegionInfoDao regionInfoDao;
	
	/**
	 * 获取个人信息
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取个人信息", notes = "获取个人信息（会员等级V0-->V6 经验值 0>>100>>300>>1000>>3000>>8000>>20000）", response = Member.class)
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public Object getUserInfo() {
		
		return memberDao.findOne(getMemberId());
	}
	
	/**
	 * 获取个人会员等级成长记录
	 * V0-->V6
	 * 0>>100>>300>>1000>>3000>>8000>>20000
	 */
	
	
	
	/**
	 * 个人信息完善
	 * @throws BusinessErrorException 
	 * 
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "完善个人信息", notes = "完善个人信息")
	public void modifyUserInfo(@ApiParam(value = "个人信息", required = true) @Valid @RequestBody UserCompleteInfo info) throws BusinessErrorException {
		
		Member entity = memberDao.findOne(getMemberId());

		BeanUtils.copyProperties(info, entity);
		
		// 所在城市
		int regionCity = info.getRegionCity();
		
		RegionInfo regionInfo = regionInfoDao.findOne(regionCity);
		
		if(regionInfo == null) {
			
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "所在城市信息有误，请重新选择");
		}
		String regingProvStr = StringUtils.substring(String.valueOf(regionInfo), 0, 4) + "00";
		RegionInfo regionProv = regionInfoDao.findOne(Integer.parseInt(regingProvStr));
		
		entity.setRegionProv(regionProv.getRegionCode());
		entity.setRegionProvName(regionProv.getRegionName());
		entity.setRegionCity(regionCity);
		entity.setRegionCityName(regionInfo.getRegionName());
		
		entity.setUpdateDate(new Date());
		memberDao.save(entity);
		
	}

	@RequestMapping(value = "/userAvatar", method = RequestMethod.PUT)
	@ApiOperation(value = "修改个人头像", notes = "修改个人头像")
	public void modifyUserAvatar(@Valid @RequestBody UserAvatarBean userAvatar) {
		Member entity = memberDao.findOne(getMemberId());
		entity.setMemberAvatar(userAvatar.getUserAvatar());
		entity.setUpdateDate(new Date());
		memberDao.save(entity);
	}

	/**
	 * 绑定新的手机号码
	 * @throws BusinessErrorException 
	 * @throws MemcachedException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 */
	@RequestMapping(value = "/rebandUserPhone", method = RequestMethod.POST)
	@ApiOperation(value = "绑定新的手机号码", notes = "绑定新的手机号码")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void rebandUserPhone(@ApiParam(value = "手机号码绑定信息", required = true) @Valid @RequestBody RegisterInfo info) throws BusinessErrorException, TimeoutException, InterruptedException, MemcachedException {
		
		Member userInfo = memberDao.findOne(getMemberId());
		
		if(userInfo == null) {
			throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "用户账号不存在");
		}
		
		long userCount = memberDao.countByMemberPhone(info.getUserPhone());
		if (userCount > 0) {
			throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "该手机号码已经绑定账号");
		}
		String smsMsg = cacheClient.get(info.getUserPhone());
		if (!Constants.MAGIC_CODE.equalsIgnoreCase(info.getPhoneCode())) {
			if (smsMsg == null || !smsMsg.equalsIgnoreCase(info.getPhoneCode())) {
				throw new BusinessErrorException(ErrorCodes.AUTH_CODE_ERROR, "验证码错误，请重新输入");
			}
		}

		if (!info.getUserPwd().equals(info.getConfirmPwd())) {
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "两次输入的密码不一致，请重新输入");
		}
		
		if (!userInfo.getMemberPwd().equals(info.getUserPwd())) {
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "密码输入错误，请重新输入");
		}

		userInfo.setMemberPwd(info.getUserPwd());
		userInfo.setUpdateDate(new Date());
		
		memberDao.save(userInfo);
	}
	
	/**
	 * 修改登录密码
	 * @throws BusinessErrorException 
	 */
	@RequestMapping(value = "/modifyUserPwd", method = RequestMethod.POST)
	@ApiOperation(value = "修改登录密码", notes = "修改登录密码")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void modifyUserPwd(@ApiParam(value = "登录密码修改信息", required = true) @Valid @RequestBody UserPwdInfo info) throws BusinessErrorException {
		
		Member userInfo = memberDao.findOne(getMemberId());
		
		if(userInfo == null) {
			throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "用户账号不存在");
		}
		
		if (!info.getUserPwd().equals(info.getConfirmPwd())) {
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "两次输入的密码不一致，请重新输入");
		}
		
		if (!info.getOldUserPwd().equals(userInfo.getMemberPwd())) {
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "密码输入错误，请重新输入");
		}

		userInfo.setMemberPwd(info.getUserPwd());
		userInfo.setUpdateDate(new Date());
		
		memberDao.save(userInfo);
		
	}
	
	/**
	 * 忘记密码
	 * @throws BusinessErrorException
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@RequestMapping(value = "/forgetpwd", method = RequestMethod.POST)
	@ApiOperation(value = "忘记密码", notes = "忘记密码")
	public Object register(@ApiParam(value = "忘记密码", required = true) @Valid @RequestBody ForgetPwdBean bean)
			throws BusinessErrorException, TimeoutException, InterruptedException, MemcachedException {

		Member userInfo = memberDao.findByMemberPhone(bean.getUserPhone());
		
		if(userInfo == null){
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "该用户不存在，请重新输入");
		}
		
		String smsMsg = cacheClient.get(bean.getUserPhone());

		if (!Constants.MAGIC_CODE.equalsIgnoreCase(bean.getPhoneCode())) {
			if (smsMsg == null || !smsMsg.equalsIgnoreCase(bean.getPhoneCode())) {
				throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "验证码错误，请重新输入");
			}
		}

		if (!bean.getUserPwd().equals(bean.getConfirmPwd())) {
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "两次输入的密码不一致，请重新输入");
		}

		userInfo.setMemberPwd(bean.getUserPwd());
		
		
		return memberDao.save(userInfo);

	}
	
	/**
	 * 退出登录
	 * @throws MemcachedException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ApiOperation(value = "退出登录", notes = "退出登录")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void logout() throws TimeoutException, InterruptedException, MemcachedException {
		
		// 清除用户Token缓存
		cacheClient.delete(getAccessToken());
		
	}
}

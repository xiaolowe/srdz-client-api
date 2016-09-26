package cn.org.citycloud.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import cn.org.citycloud.bean.LoginInfo;
import cn.org.citycloud.bean.RegisterInfo;
import cn.org.citycloud.bean.UserToken;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.Member;
import cn.org.citycloud.entity.MemberLevel;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.MemberDao;
import cn.org.citycloud.repository.MemberLevelDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 用户登录模块控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/users")
@Api(tags="注册登录", position=1, value = "/users", description = "用户注册登录模块", consumes = "application/json")
public class UserController {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemcachedClient cacheClient;
	
	@Autowired
	private MemberLevelDao memberLevelDao;
	
	/**
	 * 用户注册
	 * 
	 * @throws BusinessErrorException
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "用户注册", notes = "用户注册", response = Member.class)
	public Object register(@ApiParam(value = "用户注册信息", required = true) @Valid @RequestBody RegisterInfo info)
			throws BusinessErrorException, TimeoutException, InterruptedException, MemcachedException {

		Member user = memberDao.findByMemberPhone(info.getUserPhone());
		if (user != null) {
			throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "该手机号码已经存在");
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

		Member entity = new Member();
		entity.setMemberPhone(info.getUserPhone());
		entity.setMemberPwd(info.getUserPwd());
		entity.setMemberStatus(Constants.MEMBER_STATE_OPEN);
		
		// 默认等级
//		entity.setLevel(0);
//		entity.setExperience(0);
		Date now = new Date();
		entity.setMemberTime(now);
		entity.setCreateDate(now);
		entity.setUpdateDate(now);
		entity = memberDao.save(entity);

		return entity;
	}

	/**
	 * 客户端用户登录
	 * 
	 * @param info
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Transactional
	@ApiOperation(value = "客户端用户登录", notes = "用户登录时调用（返回userId、userPhone、token）")
	public Object login(@ApiParam(value = "用户登录信息", required = true) @Valid @RequestBody LoginInfo info)
			throws TimeoutException, InterruptedException, MemcachedException, BusinessErrorException {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Member user = memberDao.findByMemberPhoneAndMemberPwd(info.getUserPhone(), info.getUserPwd());

		if (user == null) {
			throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "手机号码或者密码不正确");
		}
		
		if (Constants.MEMBER_STATE_CLOSED == user.getMemberStatus()) {
			throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "此会员已经被禁用！");
		}

		String token = generateToken(user.getMemberId(), Constants.TOKEN_SECRET);
		UserToken userToken = new UserToken();
		userToken.setToken(token);
		userToken.setCreateTs(System.currentTimeMillis());
		userToken.setExpiresIn(Constants.TOKEN_EXPIRES_IN);
		userToken.setUserId(user.getMemberId());
		userToken.setUserPhone(user.getMemberPhone());
		cacheClient.set(token, (int) Constants.TOKEN_EXPIRES_IN, userToken);

		resultMap.put("userId", user.getMemberId());
		resultMap.put("userPhone", user.getMemberPhone());
		resultMap.put("token", token);
		
		/**
		// 会员当天 首次登录 增加 会员成长值 每日登录：+5exp
		long count = userRecordDao.countUserRecord(user.getId(), DateUtil.getDateString(new Date(), DateUtil.FORMAT_DATE));
		
		if(count == 0L) {
			UserRecord record = new UserRecord();
			record.setUserInfoId(user.getId());
			record.setGrowth(5);
			record.setRemark("每日登录");
			record.setRecordType(1);
			
			userRecordDao.save(record);
			
			// 更新会员经验值
			user.setExperience(user.getExperience()+record.getGrowth());
			
			userInfoDao.save(user);
			
		}
		 **/

		return resultMap;

	}
	
	/**
	 * 会员等级信息
	 */
	@RequestMapping(value = "/memberLevel", method = RequestMethod.GET)
	@ApiOperation(value = "获取会员等级信息", notes = "获取会员等级信息", response = MemberLevel.class, responseContainer = "List")
	public Object getMemberLevelList() {

		// 时间倒序排序
		Sort sort = new Sort(Sort.Direction.ASC, "memberLevelId");
		
		return memberLevelDao.findAll(sort);
	}

	/**
	 * 生成token值
	 * 
	 * @param user
	 * @return
	 */
	private String generateToken(int userId, String secret) {
		JWTSigner jwtSigner = new JWTSigner(secret);
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("userId", userId);
		claims.put("crtime", System.currentTimeMillis());
		String token = jwtSigner.sign(claims);
		return token;
	}
}

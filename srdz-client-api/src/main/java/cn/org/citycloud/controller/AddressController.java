package cn.org.citycloud.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.MemberAddrInfo;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.MemberAddr;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.MemberAddrDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 收货地址控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/accounts/addresses")
@Api(tags = "个人中心", position = 8, value = "/accounts/addresses", description = "收货地址模块", consumes = "application/json")
public class AddressController extends BaseController {

	@Autowired
	private MemberAddrDao memberAddrDao;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取个人所有收货地址", notes = "获取个人所有收货地址信息", response = MemberAddr.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public List<MemberAddr> getMemberAddrs() {

		return memberAddrDao.findByMemberIdOrderByUpdateTimeDesc(getMemberId());

	}

	@RequestMapping(value = "/{memberAddrId}", method = RequestMethod.GET)
	@ApiOperation(value = "获取个人收货地址信息", notes = "获取个人收货地址信息", response = MemberAddr.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public MemberAddr getMemberAddrsDetail(@PathVariable int memberAddrId) {

		return memberAddrDao.findOne(memberAddrId);
	}

	/**
	 * 添加个人收货地址
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加个人收货地址", notes = "添加个人收货地址",  response = MemberAddr.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object addMemberAddr(@Valid @RequestBody MemberAddrInfo addr) throws BusinessErrorException {

		if(memberAddrDao.countByMemberId(getMemberId()) >= 10) {
			
			throw new BusinessErrorException(ErrorCodes.WRONG_ADDRESS, "会员收货地址最多有10个");
		}
		
		
		MemberAddr newAddr = new MemberAddr();

		BeanUtils.copyProperties(addr, newAddr);

		newAddr.setMemberId(getMemberId());
		Date now = new Date();
		newAddr.setCreateTime(now);
		newAddr.setUpdateTime(now);

		// 省市区

		// 默认flag
		if (memberAddrDao.countByMemberId(getMemberId()) == 0) {
			newAddr.setDefaultFlag(1);
		}

		memberAddrDao.save(newAddr);
		
		return memberAddrDao.findByMemberIdOrderByUpdateTimeDesc(getMemberId());
	}

	/**
	 * 删除个人收货地址
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除个人收货地址", notes = "删除个人收货地址",  response = MemberAddr.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object deleteMemberAddr(@PathVariable int id) {

		MemberAddr addr = memberAddrDao.findByMemberAddrIdAndMemberId(id, getMemberId());

		if (addr != null)

			memberAddrDao.delete(addr);

		return memberAddrDao.findByMemberIdOrderByUpdateTimeDesc(getMemberId());
	}

	/**
	 * 修改个人收货地址
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "修改个人收货地址", notes = "修改个人收货地址",  response = MemberAddr.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object editMemberAddr(@Valid @RequestBody MemberAddrInfo addr) throws BusinessErrorException {

		if (addr.getMemberAddrId() == 0) {
			throw new BusinessErrorException(ErrorCodes.WRONG_ADDRESS, "未选择要修改的收货地址");
		}
		MemberAddr memberAddr = memberAddrDao.findOne(addr.getMemberAddrId());
		if (memberAddr == null) {
			throw new BusinessErrorException(ErrorCodes.WRONG_ADDRESS, "会员收货地址不存在");
		}
		BeanUtils.copyProperties(addr, memberAddr);

		memberAddrDao.save(memberAddr);
		
		return memberAddrDao.findByMemberIdOrderByUpdateTimeDesc(getMemberId());
	}

	/**
	 * 个人收货地址 设置成默认
	 * @throws BusinessErrorException 
	 * 
	 */
	@RequestMapping(value = "/setDefaultAddr/{memberAddrId}/", method = RequestMethod.PUT)
	@ApiOperation(value = "设置成默认个人收货地址", notes = "设置成默认个人收货地址",  response = MemberAddr.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object setDefaultAddr(@PathVariable int memberAddrId) throws BusinessErrorException {

		MemberAddr memberAddr = memberAddrDao.findByMemberAddrIdAndMemberId(memberAddrId, getMemberId());
		if (memberAddr == null) {
			throw new BusinessErrorException(ErrorCodes.WRONG_ADDRESS, "会员收货地址不存在");
		}

		if (memberAddr.getDefaultFlag() == 1) {
			throw new BusinessErrorException(ErrorCodes.WRONG_ADDRESS, "会员收货地址已经是默认地址");
		}

		// 取消其他默认地址
		MemberAddr defaultMemberAddr = memberAddrDao.findByMemberIdAndDefaultFlag(getMemberId(), 1);

		Date now = new Date();
		if (defaultMemberAddr != null) {
			defaultMemberAddr.setDefaultFlag(0);
			memberAddrDao.save(defaultMemberAddr);
		}

		// 设置成默认地址
		memberAddr.setDefaultFlag(1);
		memberAddr.setUpdateTime(now);
		memberAddrDao.save(memberAddr);
		
		return memberAddrDao.findByMemberIdOrderByUpdateTimeDesc(getMemberId());
	}

}

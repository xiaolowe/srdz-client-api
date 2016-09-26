package cn.org.citycloud.controller;

import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.SupplierRegisterInfo;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.Supplier;
import cn.org.citycloud.entity.SupplierUser;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.RegionInfoDao;
import cn.org.citycloud.repository.SupplierDao;
import cn.org.citycloud.repository.SupplierUserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 供应商控制器
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/suppliers")
@Api(tags="供应商", position=8, value = "/suppliers", description = "供应商模块", consumes="application/json")
public class SupplierController {
	
	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
	private SupplierUserDao supplierUserDao;
	
	@Autowired
	private MemcachedClient cacheClient;
	
	@Autowired
	private RegionInfoDao regionInfoDao;

	/**
	 * 供应商注册
	 * @throws BusinessErrorException 
	 * @throws MemcachedException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "供应商注册", notes = "供应商注册", response = Supplier.class)
	public Object registerSupplier(@ApiParam(value = "供应商注册信息", required = true) @Valid @RequestBody SupplierRegisterInfo info) throws BusinessErrorException, TimeoutException, InterruptedException, MemcachedException {
		
		SupplierUser supplierUser = supplierUserDao.findByPhone(info.getUserPhone());
		
		if (supplierUser != null)
        {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "该手机号已存在");
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
		// 插入供应商信息
		Supplier supplier = new Supplier();
		
		Date now = new Date();
		// 密码
		supplier.setPassword(info.getUserPwd());
		
		supplier.setServiceCenterId(info.getServiceCenterId());
		supplier.setPhone(info.getUserPhone());
		supplier.setSupplierName(info.getSupplierName());
		supplier.setComanyName(info.getComanyName());
		supplier.setContactName(info.getContactName());
		supplier.setContactPhone(info.getUserPhone());
		supplier.setRegionProv(info.getRegionProv());
		supplier.setRegionProvName(regionInfoDao.getOne(info.getRegionProv()).getRegionName());
		supplier.setRegionCity(info.getRegionCity());
		supplier.setRegionCityName(regionInfoDao.getOne(info.getRegionCity()).getRegionName());
		supplier.setRegionArea(info.getRegionArea());
		supplier.setRegionAreaName(regionInfoDao.getOne(info.getRegionArea()).getRegionName());
		
		supplier.setCreateTime(now);
		supplier.setUpdateTime(now);
		
		// 供应商等级 默认
		supplier.setSupplierLevelId(1);
		
		supplier.setStatus(0);
		
		supplier = supplierDao.save(supplier);
		
		// 插入供应商用户信息
		SupplierUser user = new SupplierUser();
		user.setUserName(info.getUserPhone());
		user.setPhone(info.getUserPhone());
		user.setUserPwd(info.getUserPwd());
		user.setSupplierId(supplier.getSupplierId());
		user.setUserStatus(1);
		user.setCreateTime(now);
		user.setUpdateTime(now);
		
		return supplierUserDao.save(user);
	}
}

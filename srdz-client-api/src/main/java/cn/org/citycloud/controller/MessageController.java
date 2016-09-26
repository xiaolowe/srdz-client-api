package cn.org.citycloud.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.MemberMailMessage;
import cn.org.citycloud.bean.MemberMessages;
import cn.org.citycloud.bean.MessageSearch;
import cn.org.citycloud.bean.ReplyMailMessage;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.MailMessage;
import cn.org.citycloud.entity.Member;
import cn.org.citycloud.entity.Message;
import cn.org.citycloud.entity.Order;
import cn.org.citycloud.entity.ReplyMessage;
import cn.org.citycloud.entity.Supplier;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.MailMessageDao;
import cn.org.citycloud.repository.MemberDao;
import cn.org.citycloud.repository.MessageDao;
import cn.org.citycloud.repository.ReplyMessageDao;
import cn.org.citycloud.repository.SupplierDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 我的消息控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/accounts/messages")
@Api(tags = "个人中心", position = 1, value = "/accounts/messages", description = "我的消息模块", consumes = "application/json")
public class MessageController extends BaseController {
	
	@Autowired
	private MailMessageDao mailMessageDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private ReplyMessageDao replyMessageDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private SupplierDao supplierDao;
	
	/**
	 * 联系供应商
	 * 
	 * 发送站内信
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "联系卖家、发送站内信", notes = "联系卖家、发送站内信")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void submitMessages(@Valid @RequestBody MemberMailMessage message) {
		
		MailMessage mailMsg = new MailMessage();
		
		Member member = memberDao.findOne(getMemberId());
		mailMsg.setMemberId(getMemberId());
		mailMsg.setSender(StringUtils.isBlank(member.getMemberName()) ? getMemberPhone() :member.getMemberName());
		mailMsg.setSenderId(getMemberId());
		
		Supplier supplier = supplierDao.findOne(message.getReceiverId());
		mailMsg.setReceiverId(message.getReceiverId());
		mailMsg.setReceiver(supplier.getSupplierName());
		mailMsg.setMessageContent(message.getMessageContent());
		
		mailMsg.setTriggerEvent("站内信");
		Date now = new Date();
		mailMsg.setSendTime(now);
		mailMsg.setCreateDate(now);
		mailMsg.setUpdateDate(now);
		mailMsg.setStatus(1);
		
		mailMessageDao.save(mailMsg);
	}
	
	/**
	 * 回复站内信
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	@ApiOperation(value = "回复站内信", notes = "回复站内信")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void replyMessages(@Valid @RequestBody ReplyMailMessage message) {
		
		Date now = new Date();
		
		MailMessage mailMsg = mailMessageDao.findByMailMessageIdAndMemberId(message.getMailMessageId(), getMemberId());
		
		if(mailMsg == null) {
			return;
		}
		
		ReplyMessage replyMsg = new ReplyMessage();
		replyMsg.setMailMessageId(message.getMailMessageId());
		replyMsg.setMemberId(getMemberId());
		
		replyMsg.setSender(mailMsg.getSender());
		replyMsg.setSenderId(mailMsg.getSenderId());
		replyMsg.setReceiver(mailMsg.getReceiver());
		replyMsg.setReceiverId(mailMsg.getReceiverId());
		
		replyMsg.setTriggerEvent("站内信");
		replyMsg.setReplyContent(message.getMessageContent());
		replyMsg.setReplyTime(now);
		replyMsg.setCreateDate(now);
		replyMsg.setUpdateDate(now);
		replyMsg.setStatus(1);
		replyMsg.setMsgFlag(0);
		
		
		replyMessageDao.save(replyMsg);
	}
	
	/**
	 * 获取我的消息列表(系统消息)
	 */
	@RequestMapping(value = "/system",method = RequestMethod.GET)
	@ApiOperation(value = "获取我的消息列表(系统消息)", notes = "获取我的消息列表（系统消息）")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header"),
	@ApiImplicitParam(name = "status", value = "状态（0  未读   1  已读   99 全部）", required = false, dataType = "int", paramType = "query"),
	@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
	@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query")})
	public Object getSystemMessages(@ApiIgnore @Valid MessageSearch search) {
		
		// 分页
			Pageable pageable = new PageRequest(search.getPage(), search.getSize());

			Specification<Message> specs = new Specification<Message>() {

				@Override
				public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate where = cb.conjunction();

					Path<Integer> receiverId = root.get("receiverId");
					where = cb.and(where, cb.equal(receiverId, getMemberId()));

					if (99 != search.getStatus()) {
						Path<Integer> status = root.get("status");
						where = cb.and(where, cb.equal(status, search.getStatus()));
					}

					query.where(where);

					List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();

					Path<Date> sendTime = root.get("sendTime");
					javax.persistence.criteria.Order sendTimeOrder = cb.desc(sendTime);
					orderList.add(sendTimeOrder);

					// 排序
					query.orderBy(orderList);
					return null;
				}

			};

			Page<Message> messageList = messageDao.findAll(specs, pageable);

			return messageList;
	}
	
	/**
	 * 获取我的消息列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取我的消息列表(站内信)", notes = "获取我的消息列表（站内信）")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header"),
	@ApiImplicitParam(name = "status", value = "状态（0  未读   1  已读   99 全部）", required = false, dataType = "int", paramType = "query"),
	@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
	@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query")})
	public Object getUserMessages(@ApiIgnore @Valid MessageSearch search) {
		
		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<MailMessage> specs = new Specification<MailMessage>() {

			@Override
			public Predicate toPredicate(Root<MailMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				Path<Integer> memberId = root.get("memberId");
				where = cb.and(where, cb.equal(memberId, getMemberId()));

				if (99 != search.getStatus()) {
					Path<Integer> status = root.get("status");
					where = cb.and(where, cb.equal(status, search.getStatus()));
				}

				query.where(where);

				List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();

				Path<Date> sendTime = root.get("sendTime");
				javax.persistence.criteria.Order sendTimeOrder = cb.desc(sendTime);
				orderList.add(sendTimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<MailMessage> mailmessageList = mailMessageDao.findAll(specs, pageable);

		return mailmessageList;
		
		
	}
	
	/**
	 * 阅读我的消息
	 */
	@RequestMapping(value = "/{id}/type/{type}", method = RequestMethod.PUT)
	@ApiOperation(value = "阅读我的消息(消息状态已读)", notes = "阅读我的消息（站内信、系统消息）")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void getUserMessage(@ApiParam(value = "消息ID", required = true) @PathVariable int id, 
			@ApiParam(value = "消息类型（系统消息：1；站内信：2）", required = true) @PathVariable int type) {
		Date now = new Date();
		if(type == 1) {
			
			Message systemMsg = messageDao.findByMessageIdAndReceiverIdAndPlatform(id, getMemberId(), 4);
			
			if(systemMsg != null) {
				systemMsg.setStatus(1);
				systemMsg.setUpdateDate(now);
				
				messageDao.save(systemMsg);
			}
		} else if (type == 2) {
			
			MailMessage mailMsg = mailMessageDao.findByMailMessageIdAndMemberId(id, getMemberId());
			if(mailMsg != null) {
				mailMsg.setStatus(1);
				mailMsg.setUpdateDate(now);
				
				mailMessageDao.save(mailMsg);
			}
		}
	}
	
	/**
	 * 系统消息批量删除
	 * @param bean
	 * @throws BusinessErrorException 
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "系统消息批量删除", notes = "系统消息批量删除")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void removeMessage(@ApiParam(value = "系统消息批量删除,格式 [1,2,3,4,5]") @RequestParam(value = "messageIds")  int[] messageIds) throws BusinessErrorException {
		
		
		for(int i = 0 ; i < messageIds.length; i ++){
			int msgId = messageIds[i];
			try {
				messageDao.delete(msgId);
			} catch (Exception e) {
				throw new BusinessErrorException(ErrorCodes.SYSTEM_ERROR, "该ID不存在！  "+msgId);
			}
			
		}

	}
	
	/**
	 * 站内信消息批量删除
	 * @param bean
	 * @throws BusinessErrorException 
	 */
	@RequestMapping(value = "/mail", method = RequestMethod.DELETE)
	@ApiOperation(value = "站内信消息批量删除", notes = "站内信消息批量删除")
	@ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
	public void removeMailMessage(@ApiParam(value = "站内信消息批量删除,格式 [1,2,3,4,5]") @RequestParam(value = "messageIds")  int[] messageIds) throws BusinessErrorException {
		
		
		for(int i = 0 ; i < messageIds.length; i ++){
			int msgId = messageIds[i];
			try {
				mailMessageDao.delete(msgId);
			} catch (Exception e) {
				throw new BusinessErrorException(ErrorCodes.SYSTEM_ERROR, "该ID不存在！  "+msgId);
			}
			
		}

	}
	
}

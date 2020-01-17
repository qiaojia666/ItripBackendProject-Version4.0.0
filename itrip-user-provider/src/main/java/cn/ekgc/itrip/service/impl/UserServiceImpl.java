package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.UserDao;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.entity.UserLinkUser;
import cn.ekgc.itrip.service.UserService;
import cn.ekgc.itrip.util.ActiveCodeUtil;
import cn.ekgc.itrip.util.ConstantUtil;
import cn.ekgc.itrip.util.communication.email.EmailUtil;
import cn.ekgc.itrip.util.communication.sms.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <b>用户子项目业务层接口实现类</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private SMSUtil smsUtil;
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * <b>校验是否可以注册使用</b>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserCodeForRegistry(String userCode) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		// 进行查询
		List<User> userList = userDao.findUserListByQuery(queryMap);

		if (userList != null && userList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * <b>使用电子邮件完成用户注册</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean registryUser(User user) throws Exception {
		try {
			// 默认激活状态为未激活：0
			user.setActivated(0);
			// 保存用户信息
			userDao.saveUser(user);
			// 生成激活码
			String activeCode = ActiveCodeUtil.createActiveCode();
			if (user.getUserCode().matches(ConstantUtil.REGEX_EMAIL)) {
				// 发送邮件
				emailUtil.sendEmail(user.getUserCode(), activeCode);
			} else {
				smsUtil.sendSMS(user.getUserCode(), activeCode);
			}
			// 将激活码保存到Redis中
			redisTemplate.opsForValue().set(user.getUserCode(), activeCode);
			// 对于该存入redis的key设置过期时间
			redisTemplate.expire(user.getUserCode(), ConstantUtil.ACTIVE_CODE_TIMEOUT * 60, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <b>用户进行账户激活</b>
	 * @param userCode
	 * @param activeCode
	 * @return
	 * @throws Exception
	 */
	public boolean activateUser(String userCode, String activeCode) throws Exception {
		try {
			// 从redis中获得用户的激活码，和用户所提供的进行比对
			String registryCode = redisTemplate.opsForValue().get(userCode);
			if (activeCode.equals(registryCode)) {
				// 激活码正确，进行用户激活操作
				User user = new User();
				user.setUserCode(userCode);
				user.setActivated(1);
				userDao.updateUser(user);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <b>使用userCode和userPassword查找登录用户</b>
	 * @param userCode
	 * @param userPassword
	 * @return
	 * @throws Exception
	 */
	public User getUserForLogin(String userCode, String userPassword) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		queryMap.put("userPassword", userPassword);
		// 进行查询
		List<User> userList = userDao.findUserListByQuery(queryMap);
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}


	public List<UserLinkUser> getLinkUserListByLogin(Map<String, Object> queryMap) throws Exception {
		List<UserLinkUser> linkUserList = userDao.findLinkUserListByQuery(queryMap);
		return linkUserList;
	}

	public User getUserByUserCode(String userCode) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		// 进行查询
		List<User> userList = userDao.findUserListByQuery(queryMap);
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	/**
	 * <b>添加用户联系人</b>
	 * @param userLinkUser
	 * @return
	 * @throws Exception
	 */
	public boolean addaddUserLinkUser(UserLinkUser userLinkUser) throws Exception {
		try {
			userDao.saveUserLinkUser(userLinkUser);
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <b>修改用户联系人</b>
	 * @param userLinkUser
	 * @return
	 */
	public boolean modifyUserLinkUser(UserLinkUser userLinkUser) throws Exception {
		try {
			userDao.modifyUserLinkUser(userLinkUser);
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <b>删除用户联系人</b>
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean delUserLinkUser(Long[] ids) throws Exception {
		Long id = null;
		try {
			for (int i = 0; i < ids.length; i++){
				id = ids[i];
				userDao.delUserLinkUser(id);
			}
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}

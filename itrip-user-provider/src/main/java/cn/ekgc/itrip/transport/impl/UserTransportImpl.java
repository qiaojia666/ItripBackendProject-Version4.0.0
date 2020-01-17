package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.entity.UserLinkUser;
import cn.ekgc.itrip.service.UserService;
import cn.ekgc.itrip.user.transport.UserTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>用户模块传输层接口</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@RestController("userTransport")
@RequestMapping("/user")
public class UserTransportImpl implements UserTransport {
	@Autowired()
	private UserService userService;

	/**
	 * <b>校验是否可以注册使用</b>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkUserCodeForRegistry", method = RequestMethod.POST)
	public boolean checkUserCodeForRegistry(@RequestParam String userCode) throws Exception {
		return userService.checkUserCodeForRegistry(userCode);
	}

	/**
	 * <b>使用电子邮件完成用户注册</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registryUser", method = RequestMethod.POST)
	public boolean registryUser(@RequestBody User user) throws Exception {
		return userService.registryUser(user);
	}

	/**
	 * <b>为使用电子邮件注册用户进行激活操作</b>
	 * @param userCode
	 * @param activeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activateUser", method = RequestMethod.POST)
	public boolean activateUser(@RequestParam String userCode, @RequestParam String activeCode) throws Exception {
		return userService.activateUser(userCode, activeCode);
	}

	/**
	 * <b>使用userCode和userPassword进行登录</b>
	 * @param userCode
	 * @param userPassword
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User getUserForLogin(@RequestParam String userCode, @RequestParam String userPassword) throws Exception {
		return userService.getUserForLogin(userCode, userPassword);
	}


	@RequestMapping(value = "/queryuserlinkuser", method = RequestMethod.POST)
	public List<UserLinkUser> getLinkUserListByLogin(@RequestBody Map<String, Object> queryMap) throws Exception {
		return userService.getLinkUserListByLogin(queryMap);
	}

	@RequestMapping(value = "/queryUserByUserCode", method = RequestMethod.POST)
	public User getUserByUserCode(@RequestParam String userCode) throws Exception {
		return userService.getUserByUserCode(userCode);
	}

	/**
	 * <b>添加用户联系人</b>
	 * @param userLinkUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/adduserlinkuser", method = RequestMethod.POST)
	public boolean addUserLinkUser(@RequestBody UserLinkUser userLinkUser) throws Exception {
		return userService.addaddUserLinkUser(userLinkUser);
	}

	/**
	 * <b>修改用户联系人</b>
	 * @param userLinkUser
	 * @return
	 */
	@RequestMapping(value = "/modifyuserlinkuser", method = RequestMethod.POST)
	public boolean modifyUserLinkUser(@RequestBody UserLinkUser userLinkUser) throws Exception {
		return userService.modifyUserLinkUser(userLinkUser);
	}

	/**
	 * <b>删除用户联系人</b>
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deluserlinkuser",method = RequestMethod.POST)
	public boolean delUserLinkUser(@RequestParam Long[] ids) throws Exception {
		return userService.delUserLinkUser(ids);
	}

}

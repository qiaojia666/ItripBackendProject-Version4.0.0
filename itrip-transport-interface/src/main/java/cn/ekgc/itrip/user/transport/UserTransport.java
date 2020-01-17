package cn.ekgc.itrip.user.transport;

import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.entity.UserLinkUser;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>用户模块传输层接口</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@FeignClient(name = "itrip-user-provider")
@RequestMapping("/user")
public interface UserTransport {
	/**
	 * <b>校验是否可以注册使用</b>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkUserCodeForRegistry", method = RequestMethod.POST)
	boolean checkUserCodeForRegistry(@RequestParam String userCode) throws Exception;

	/**
	 * <b>使用电子邮件完成用户注册</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registryUser", method = RequestMethod.POST)
	boolean registryUser(@RequestBody User user) throws Exception;

	/**
	 * <b>为使用电子邮件注册用户进行激活操作</b>
	 * @param userCode
	 * @param activeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activateUser", method = RequestMethod.POST)
	boolean activateUser(@RequestParam String userCode, @RequestParam String activeCode) throws Exception;

	/**
	 * <b>使用userCode和userPassword进行登录</b>
	 * @param userCode
	 * @param userPassword
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	User getUserForLogin(@RequestParam String userCode, @RequestParam String userPassword) throws Exception;

	@RequestMapping(value = "/queryuserlinkuser", method = RequestMethod.POST)
	List<UserLinkUser> getLinkUserListByLogin(@RequestBody Map<String, Object> queryMap) throws Exception;

	@RequestMapping(value = "/queryUserByUserCode", method = RequestMethod.POST)
	User getUserByUserCode(@RequestParam String userCode) throws Exception;

	/**
	 * <b>添加用户联系人</b>
	 * @param userLinkUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/adduserlinkuser", method = RequestMethod.POST)
	boolean addUserLinkUser(@RequestBody UserLinkUser userLinkUser) throws Exception;

	/**
	 * <b>修改用户联系人</b>
	 * @param userLinkUser
	 * @return
	 */
	@RequestMapping(value = "/modifyuserlinkuser",method = RequestMethod.POST)
	boolean modifyUserLinkUser(@RequestBody UserLinkUser userLinkUser) throws Exception;

	/**
	 * <b>删除用户联系人</b>
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deluserlinkuser",method = RequestMethod.POST)
	boolean delUserLinkUser(@RequestParam Long[] ids) throws Exception;
}

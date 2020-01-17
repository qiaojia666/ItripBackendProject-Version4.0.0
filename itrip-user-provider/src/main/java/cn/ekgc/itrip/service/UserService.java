package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.entity.UserLinkUser;

import java.util.List;
import java.util.Map;

/**
 * <b>用户子项目业务层接口</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
public interface UserService {
	/**
	 * <b>校验是否可以注册使用</b>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	boolean checkUserCodeForRegistry(String userCode) throws Exception;

	/**
	 * <b>使用电子邮件完成用户注册</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean registryUser(User user) throws Exception;

	/**
	 * <b>用户进行账户激活</b>
	 * @param userCode
	 * @param activeCode
	 * @return
	 * @throws Exception
	 */
	boolean activateUser(String userCode, String activeCode) throws Exception;

	/**
	 * <b>使用userCode和userPassword查找登录用户</b>
	 * @param userCode
	 * @param userPassword
	 * @return
	 * @throws Exception
	 */
	User getUserForLogin(String userCode, String userPassword) throws Exception;


	List<UserLinkUser> getLinkUserListByLogin(Map<String, Object> queryMap) throws Exception;

	User getUserByUserCode(String userCode) throws Exception;

	/**
	 * <b>添加用户联系人</b>
	 * @param userLinkUser
	 * @return
	 * @throws Exception
	 */
	boolean addaddUserLinkUser(UserLinkUser userLinkUser) throws Exception;

	/**
	 * <b>修改用户联系人</b>
	 * @param userLinkUser
	 * @return
	 */
	boolean modifyUserLinkUser(UserLinkUser userLinkUser) throws Exception;

	/**
	 * <b>删除用户联系人</b>
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	boolean delUserLinkUser(Long[] ids) throws Exception;
}

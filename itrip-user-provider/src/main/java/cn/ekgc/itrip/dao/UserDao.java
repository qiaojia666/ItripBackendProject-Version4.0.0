package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.entity.UserLinkUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <b>用户子项目数据持久层接口</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@Repository
public interface UserDao {
	/**
	 * <b>根据查询条件查询用户信息列表</b>
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	List<User> findUserListByQuery(Map<String, Object> queryMap) throws Exception;

	/**
	 * <b>保存用户信息</b>
	 * @param user
	 * @throws Exception
	 */
	void saveUser(User user) throws Exception;

	/**
	 * <b>更新用户信息</b>
	 * @param user
	 * @throws Exception
	 */
	void updateUser(User user) throws Exception;


	List<UserLinkUser> findLinkUserListByQuery(Map<String, Object> queryMap) throws Exception;

	/**
	 * <b>添加用户联系人</b>
	 * @param userLinkUser
	 * @throws Exception
	 */
	void saveUserLinkUser(UserLinkUser userLinkUser) throws Exception;


	/**
	 * <b>修改用户联系人</b>
	 * @param userLinkUser
	 * @return
	 */
	void modifyUserLinkUser(UserLinkUser userLinkUser) throws Exception;

	/**
	 * <b>删除用户联系人</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean delUserLinkUser(Long id) throws Exception;
}

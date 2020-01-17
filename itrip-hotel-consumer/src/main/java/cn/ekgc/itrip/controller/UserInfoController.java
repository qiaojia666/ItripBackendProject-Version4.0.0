package cn.ekgc.itrip.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.enums.SuccessEnum;
import cn.ekgc.itrip.hotel.transport.HotelOrderTransport;
import cn.ekgc.itrip.pojo.entity.UserLinkUser;
import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.user.transport.UserTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import java.lang.reflect.Array;
import java.util.*;

/**
 * <b>用户信息控制器</b>
 */
@RestController("userInfoController")
@RequestMapping("/biz/api/userinfo")
public class UserInfoController extends BaseController {
	@Autowired
	private UserTransport userTransport;
	@Autowired
	private HotelOrderTransport hotelOrderTransport;

	@RequestMapping(value = "/queryuserlinkuser", method = RequestMethod.POST)
	public ResponseResult<Object> queryUserLinkUser(@RequestBody SearchUserLinkUserVO vo) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		// 获得所有的Cookie
		Cookie[] cookies = request.getCookies();
		String userCode = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user")) {
				userCode = cookie.getValue();
			}
		}
		queryMap.put("userCode",userCode);
		String linkUserName = null;
		if (vo.getLinkUserName()!=null){
			if (!vo.getLinkUserName().equals("")){
				linkUserName ="%" + vo.getLinkUserName() + "%";
				queryMap.put("linkUserName",linkUserName);
				List<UserLinkUser> linkUserList = userTransport.getLinkUserListByLogin(queryMap);
				return new ResponseResult<Object>(SuccessEnum.SUCCESS_TRUE, linkUserList);
			}
		}
		List<UserLinkUser> linkUserList = userTransport.getLinkUserListByLogin(queryMap);
		return new ResponseResult<Object>(SuccessEnum.SUCCESS_TRUE, linkUserList);


	}
	@RequestMapping(value = "/adduserlinkuser", method = RequestMethod.POST)
	public ResponseResult<Object> addUserLinkUser(@RequestBody AddUserLinkUserVO addUserLinkUserVO) throws Exception{
		// 获得所有的Cookie
		Cookie[] cookies = request.getCookies();
		String userCode = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user")) {
				userCode = cookie.getValue();
			}
		}
		UserLinkUser userLinkUser = new UserLinkUser();
		userLinkUser.setLinkUserName(addUserLinkUserVO.getLinkUserName());
		userLinkUser.setUserId(userTransport.getUserByUserCode(userCode).getId());
		userLinkUser.setLinkIdCard(addUserLinkUserVO.getLinkIdCard());
		userLinkUser.setLinkPhone(addUserLinkUserVO.getLinkPhone());
		if(userTransport.addUserLinkUser(userLinkUser)) {
			return new ResponseResult<Object>(SuccessEnum.SUCCESS_TRUE, "新增常用联系人成功");
		}
		return new ResponseResult<>(SuccessEnum.SUCCESS_FALSE);
	}

	/**
	 * <b>修改用户联系人</b>
	 * @param modifyUserLinkUserVO
	 * @return
	 */
	@RequestMapping(value = "/modifyuserlinkuser",method = RequestMethod.POST)
	public ResponseResult<Object> modifyUserLinkUser(@RequestBody ModifyUserLinkUserVO modifyUserLinkUserVO) throws Exception{
		// 获得所有的Cookie
		Cookie[] cookies = request.getCookies();
		String userCode = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user")) {
				userCode = cookie.getValue();
			}
		}
		UserLinkUser userLinkUser = new UserLinkUser();
		userLinkUser.setLinkUserName(modifyUserLinkUserVO.getLinkUserName());
		userLinkUser.setUserId(userTransport.getUserByUserCode(userCode).getId());
		userLinkUser.setLinkIdCard(modifyUserLinkUserVO.getLinkIdCard());
		userLinkUser.setLinkPhone(modifyUserLinkUserVO.getLinkPhone());
		userLinkUser.setId(modifyUserLinkUserVO.getId());
		if(userTransport.modifyUserLinkUser(userLinkUser)) {
			return new ResponseResult<Object>(SuccessEnum.SUCCESS_TRUE, "修改常用联系人成功");
		}
		return new ResponseResult<>(SuccessEnum.SUCCESS_FALSE);
	}

	/**
	 * <b>删除用户联系人</b>
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deluserlinkuser",method = RequestMethod.GET)
	public ResponseResult<Object> delUserLinkUser(Long[] ids) throws Exception{
		// 获得所有的Cookie
		Cookie[] cookies = request.getCookies();
		String userCode = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user")) {
				userCode = cookie.getValue();
			}
		}
		List<Long> idsList = new ArrayList<>();
		System.out.println(1);
		for (int i = 0; i < ids.length; i++){
			idsList.add(ids[i]);
		}
		List orderIdsList = hotelOrderTransport.getLinkUserIdsListByOrderStatus(userCode);
		idsList.retainAll(orderIdsList);
		if ((idsList.size())==0){
			if (userTransport.delUserLinkUser(ids)){
				return new ResponseResult<>(SuccessEnum.SUCCESS_TRUE,"删除常用联系人成功");
			}else {
				return new ResponseResult<>(SuccessEnum.SUCCESS_FALSE,"删除常用联系人失败");
			}
		}else {
			return new ResponseResult<>(SuccessEnum.SUCCESS_FALSE,"删除常用联系人失败");
		}

	}
}

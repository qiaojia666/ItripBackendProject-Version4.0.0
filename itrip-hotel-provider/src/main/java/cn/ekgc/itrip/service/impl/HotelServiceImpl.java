package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.HotelDao;
import cn.ekgc.itrip.dao.LabelDicDao;
import cn.ekgc.itrip.pojo.entity.Hotel;
import cn.ekgc.itrip.pojo.entity.LabelDic;
import cn.ekgc.itrip.pojo.vo.SearchDetailsHotelVO;
import cn.ekgc.itrip.pojo.vo.SearchPolicyHotelVO;
import cn.ekgc.itrip.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>酒店业务层接口实现类</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@Service("hotelService")
@Transactional
public class HotelServiceImpl implements HotelService {
	@Autowired
	private HotelDao hotelDao;
	@Autowired
	private LabelDicDao labelDicDao;

	/**
	 * <b>根据酒店id查询酒店特色、商圈、酒店名称</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	public Hotel queryHotelVideoDescByHotelId(Long hotelId) throws Exception {
		// 封装查询参数
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", hotelId);
		List<Hotel> hotelList = hotelDao.findHotelListByQuery(queryMap);
		if (hotelList != null && hotelList.size() > 0) {
			return hotelList.get(0);
		}
		return null;
	}

	/**
	 * <b>根据酒店id查询酒店政策</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	public String queryHotelPolicy(Long hotelId) throws Exception {
		// 封装查询参数
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", hotelId);
		List<Hotel> hotelList = hotelDao.findHotelListByQuery(queryMap);

		if (hotelList != null && hotelList.size() > 0) {
			Hotel hotel = hotelList.get(0);
			return hotel.getHotelPolicy();
		}
		return "";
	}

	/**
	 * <b>根据主键查询酒店详情描述</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	public List<SearchDetailsHotelVO> queryHotelDetails(Long hotelId) throws Exception {
		// 封装查询参数
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", hotelId);
		List<Hotel> hotelList = hotelDao.findHotelListByQuery(queryMap);
		// 查询酒店特色列表
		List<LabelDic> labelDicList = labelDicDao.getHotelFeatureByHotelId(hotelId);

		List<SearchDetailsHotelVO> detailsHotelVOList = new ArrayList<>();
		if (hotelList != null && hotelList.size() > 0) {
			Hotel hotel = hotelList.get(0);
			detailsHotelVOList.add(new SearchDetailsHotelVO("酒店介绍", hotel.getDetails()));
			for (LabelDic labelDic : labelDicList) {
				detailsHotelVOList.add(new SearchDetailsHotelVO(labelDic.getName(), labelDic.getDescription()));
			}
		}
		return detailsHotelVOList;
	}

	/**
	 * <b>根据酒店id查询酒店设施</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	public String queryHotelFacilities(Long hotelId) throws Exception {
		// 封装查询参数
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", hotelId);
		List<Hotel> hotelList = hotelDao.findHotelListByQuery(queryMap);

		if (hotelList != null && hotelList.size() > 0) {
			Hotel hotel = hotelList.get(0);
			return hotel.getFacilities();
		}
		return "";
	}
}

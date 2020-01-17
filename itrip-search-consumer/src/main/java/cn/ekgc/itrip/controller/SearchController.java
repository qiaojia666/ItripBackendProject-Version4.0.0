package cn.ekgc.itrip.controller;


import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.enums.SuccessEnum;
import cn.ekgc.itrip.pojo.vo.ItripHotelVO;
import cn.ekgc.itrip.pojo.vo.ResponseResult;
import cn.ekgc.itrip.pojo.vo.SearchHotCityVO;
import cn.ekgc.itrip.search.transport.SearchTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>酒店搜索控制器</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@RestController("searchController")
@RequestMapping("/search/api")
public class SearchController extends BaseController {
	@Autowired
	private SearchTransport searchTransport;

	/**
	 * <b>根据查询视图，查询搜索热门城市</b>
	 * @param searchHotCityVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "hotellist/searchItripHotelListByHotCity",method = RequestMethod.POST)
	public ResponseResult<Object> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO) throws Exception{
		if (searchHotCityVO.getCityId() != null && searchHotCityVO.getCityId() > 0 &&searchHotCityVO.getCount() != null
		 && searchHotCityVO.getCount() > 0){
			List<ItripHotelVO> itripHotelVOList = searchTransport.searchItripHotelListByHotCity(searchHotCityVO);
			return new ResponseResult<>(SuccessEnum.SUCCESS_TRUE,itripHotelVOList);
		}else {
			return new ResponseResult<>(SuccessEnum.SUCCESS_FALSE,"请填写有效信息");
		}
	}
}

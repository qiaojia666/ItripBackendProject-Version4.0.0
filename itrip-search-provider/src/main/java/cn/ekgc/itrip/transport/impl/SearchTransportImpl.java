package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.pojo.vo.ItripHotelVO;
import cn.ekgc.itrip.pojo.vo.SearchHotCityVO;
import cn.ekgc.itrip.search.transport.SearchTransport;
import cn.ekgc.itrip.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>搜索项目传输层接口实现类</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@RestController("searchTransport")
@RequestMapping("/search")
public class SearchTransportImpl implements SearchTransport {
	@Autowired
	private SearchService searchService;
	/**
	 * <b>根据热门城市搜索视图查询酒店列表</b>
	 * @param searchHotCityVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchItripHotelListByHotCity",method = RequestMethod.POST)
	public List<ItripHotelVO> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO) throws Exception {
		return searchService.searchItripHotelListByHotCity(searchHotCityVO);
	}
}

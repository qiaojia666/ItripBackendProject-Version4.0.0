package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.pojo.vo.ItripHotelVO;
import cn.ekgc.itrip.pojo.vo.SearchHotCityVO;
import cn.ekgc.itrip.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>搜索项目业务层接口实现类</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@Service("searchService")
@Transactional
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SolrClient solrClient;
	/**
	 * <b>根据热门城市搜索视图查询酒店列表</b>
	 * @param searchHotCityVO
	 * @return
	 */
	public List<ItripHotelVO> searchItripHotelListByHotCity(SearchHotCityVO searchHotCityVO) throws Exception {
		// 对于Spring Boot注入的SolrClient就是HttpSolrClient对象，进行强制类型转换
		HttpSolrClient httpSolrClient = (HttpSolrClient) solrClient;
		httpSolrClient.setParser(new XMLResponseParser());
		//创建solr的查询参数
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("cityId:" + searchHotCityVO.getCityId());
		solrQuery.setRows(searchHotCityVO.getCount());
		// 使用SolrClient进行查询，QueryResponse
		QueryResponse queryResponse = solrClient.query(solrQuery);
		// 通过使用QueryResponse提取结果
		return queryResponse.getBeans(ItripHotelVO.class);
	}
}

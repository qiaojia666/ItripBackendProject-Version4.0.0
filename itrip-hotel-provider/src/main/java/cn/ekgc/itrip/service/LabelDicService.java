package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.LabelDic;
import cn.ekgc.itrip.pojo.vo.LabelDicVO;

import java.util.List;

/**
 * <b>系统字典业务层接口</b>
 * @author Qiaojia
 * @since 4.0.0
 * @version 4.0.0
 */
public interface LabelDicService {
	/**
	 * <b>查询酒店特色列表</b>
	 * @return
	 * @throws Exception
	 */
	List<LabelDic> queryHotelFeature() throws Exception;

	/**
	 * <b>根据父级主键查询视图列表</b>
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<LabelDicVO> queryLabelDicVOListByParent(Long parentId) throws Exception;
}

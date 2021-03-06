package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.Comment;
import cn.ekgc.itrip.pojo.entity.ScoreComment;
import cn.ekgc.itrip.pojo.vo.CommentCountVO;
import cn.ekgc.itrip.pojo.vo.Page;
import cn.ekgc.itrip.pojo.vo.SearchCommentVO;

/**
 * <b>评论业务层接口</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
public interface CommentService {

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param queryVO
	 * @return
	 * @throws Exception
	 */
	Page<Comment> getCommentListByPage(SearchCommentVO queryVO) throws Exception;

	/**
	 * <b>据酒店id查询酒店平均分</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	ScoreComment getHotelScoreByHotelId(Long hotelId) throws Exception;

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	CommentCountVO getCommentCountByHotelId(Long hotelId) throws Exception;
}

package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.hotel.transport.CommentTransport;
import cn.ekgc.itrip.pojo.entity.Comment;
import cn.ekgc.itrip.pojo.entity.ScoreComment;
import cn.ekgc.itrip.pojo.vo.CommentCountVO;
import cn.ekgc.itrip.pojo.vo.Page;
import cn.ekgc.itrip.pojo.vo.SearchCommentVO;
import cn.ekgc.itrip.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <b>评论业务层接口实现类</b>
 * @author Qiaojia
 * @version 4.0.0
 * @since 4.0.0
 */
@RestController("commentTransport")
@RequestMapping("/comment")
public class CommentTransportImpl implements CommentTransport {
	@Autowired
	private CommentService commentService;

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param queryVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getcommentlist", method = RequestMethod.POST)
	public Page<Comment> getCommentListByPage(@RequestBody SearchCommentVO queryVO) throws Exception {
		return commentService.getCommentListByPage(queryVO);
	}

	/**
	 * <b>据酒店id查询酒店平均分</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gethotelscore", method = RequestMethod.POST)
	public ScoreComment getHotelScoreByHotelId(@RequestParam Long hotelId) throws Exception {
		return commentService.getHotelScoreByHotelId(hotelId);
	}

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getcount", method = RequestMethod.POST)
	public CommentCountVO getCommentCountByHotelId(@RequestParam Long hotelId) throws Exception {
		return commentService.getCommentCountByHotelId(hotelId);
	}
}

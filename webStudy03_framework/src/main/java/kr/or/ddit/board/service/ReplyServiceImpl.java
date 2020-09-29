package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyServiceImpl implements IReplyService {
	IReplyDAO replyDAO = new ReplyDAOImpl();

	@Override
	public ServiceResult createReply(ReplyVO reply) {
		int rowcnt = replyDAO.insertReply(reply);
		ServiceResult result = ServiceResult.FAILED;
		if(rowcnt>0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public int readReplyCount(PagingVO<ReplyVO> pagingVO) {
		return replyDAO.selectReplyCount(pagingVO);
	}

	@Override
	public List<ReplyVO> readReplyList(PagingVO<ReplyVO> pagingVO) {
		return replyDAO.selectReplyList(pagingVO);
	}

	@Override
	public ServiceResult modifyReply(ReplyVO reply) {
		int rowcnt = replyDAO.updateReply(reply);
		ServiceResult result = ServiceResult.INVALIDPASSWORD;
		if(rowcnt>0) result = ServiceResult.OK;
		return result;
	}

	@Override
	public ServiceResult removeReply(ReplyVO reply) {
		int rowcnt = replyDAO.deleteReply(reply);
		ServiceResult result = ServiceResult.INVALIDPASSWORD;
		if(rowcnt>0) result = ServiceResult.OK;
		return result;
	}

	

}

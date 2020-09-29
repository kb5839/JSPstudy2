package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

public interface IBoardDAO {
	public int insertBoard(BoardVO board, SqlSession session);
	public int selectBoardCount(PagingVO<BoardVO> pagingVO);
	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO);
	public BoardVO selectBoard(int bo_no);
	public int incrementHit(int bo_no);
	public int updateBoard(BoardVO board, SqlSession session);
	public int deleteBoard(int bo_no, SqlSession session);	
}

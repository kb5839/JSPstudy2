package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

public interface IBoardService {
	public ServiceResult createBoard(BoardVO board);
	public int readBoardCount(PagingVO<BoardVO> pagingVO);
	public List<BoardVO> readBoardList(PagingVO<BoardVO> pagingVO);
	public BoardVO readBoard(int bo_no);
	public ServiceResult modifyBoard(BoardVO board);
	public ServiceResult removeBoard(BoardVO board);
}

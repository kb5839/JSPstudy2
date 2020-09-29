package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.BoardVO;

public class AttatchDAOImpl implements IAttatchDAO {

	@Override
	public int insertAttatchs(BoardVO board, SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AttatchVO selectAttatch(int att_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteAttatchs(BoardVO board, SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}

}

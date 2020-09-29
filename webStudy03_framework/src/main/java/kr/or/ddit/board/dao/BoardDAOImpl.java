package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

public class BoardDAOImpl implements IBoardDAO {
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertBoard(BoardVO board, SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int selectBoardCount(PagingVO<BoardVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IBoardDAO mapper = session.getMapper(IBoardDAO.class);
			return mapper.selectBoardCount(pagingVO);		
		}
	}

	@Override
	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IBoardDAO mapper = session.getMapper(IBoardDAO.class);
			return mapper.selectBoardList(pagingVO);		
		}
	}

	@Override
	public BoardVO selectBoard(int bo_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IBoardDAO mapper = session.getMapper(IBoardDAO.class);
			return mapper.selectBoard(bo_no);		
		}
	}

	@Override
	public int incrementHit(int bo_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);	
		){
			IBoardDAO mapper = session.getMapper(IBoardDAO.class);
			return mapper.incrementHit(bo_no);		
		}
	}

	@Override
	public int updateBoard(BoardVO board, SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoard(int bo_no, SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}

}

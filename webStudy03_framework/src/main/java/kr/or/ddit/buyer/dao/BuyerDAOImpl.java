package kr.or.ddit.buyer.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerDAOImpl implements IBuyerDAO {
	
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int selectBuyerCount(PagingVO<BuyerVO> pagingVO) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();	
		){
			IBuyerDAO mapper = sqlSession.getMapper(IBuyerDAO.class);
			return mapper.selectBuyerCount(pagingVO);
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList(PagingVO<BuyerVO> pagingVO) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();	
		){
			IBuyerDAO mapper = sqlSession.getMapper(IBuyerDAO.class);
			return mapper.selectBuyerList(pagingVO);					
		}
	}

	@Override
	public BuyerVO selectBuyer(String buyer_id) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();	
		){
			IBuyerDAO mapper = sqlSession.getMapper(IBuyerDAO.class);
			return mapper.selectBuyer(buyer_id);
		}
	}

	@Override
	public int insertBuyer(BuyerVO buyer) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession(true);	
		){
			IBuyerDAO mapper = sqlSession.getMapper(IBuyerDAO.class);
			return mapper.insertBuyer(buyer);
		}
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession(true);	
		){
			IBuyerDAO mapper = sqlSession.getMapper(IBuyerDAO.class);
			return mapper.updateBuyer(buyer);
		}
	}

}

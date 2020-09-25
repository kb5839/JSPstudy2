package kr.or.ddit.prod.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdDAOImpl implements IProdDAO {

	private SqlSessionFactory sqlSessionFactory = 
			CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public ProdVO selectProd(String prod_id) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){		
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.selectProd(prod_id);
		}
	}
	
	@Override
	public int insertProd(ProdVO prod, SqlSession session) {
		return session.insert("kr.or.ddit.prod.dao.IProdDAO.insertProd", prod);	
	}

	@Override
	public int selectProdCount(PagingVO<ProdVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){		
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.selectProdCount(pagingVO);
		}
	}

	@Override
	public List<ProdVO> selectProdList(PagingVO<ProdVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){		
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.selectProdList(pagingVO);
		}
	}

	@Override
	public int updateProd(ProdVO prod, SqlSession session) {
		return session.insert("kr.or.ddit.prod.dao.IProdDAO.updateProd", prod);
	}

}










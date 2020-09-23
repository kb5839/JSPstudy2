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
	public int insertProd(ProdVO prod) {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);
		){		
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.insertProd(prod);
		}
	}

	@Override
	public int selectProdCount(PagingVO<ProdVO> pagingVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ProdVO> selectProdList(PagingVO<ProdVO> paingVO) {
		// TODO Auto-generated method stub
		return null;
	}

}










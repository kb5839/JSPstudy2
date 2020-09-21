package kr.or.ddit.prod.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.member.dao.IMemberDAO;
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

}










package kr.or.ddit.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;

public class OthersDAOImpl implements IOthersDAO {

	private SqlSessionFactory sqlSessionFactory = 
			CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public List<Map<String,Object>> selectLprodGuList() {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);
		){		
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectLprodGuList();
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList() {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);
		){		
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectBuyerList();
		}
	}

}

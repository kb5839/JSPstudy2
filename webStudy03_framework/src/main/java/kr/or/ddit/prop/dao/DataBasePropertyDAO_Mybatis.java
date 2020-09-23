package kr.or.ddit.prop.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.commons.dao.IZipCodeSearchDAO;
import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyDAO_Mybatis implements IDataBasePropertyDAO {
	
private static DataBasePropertyDAO_Mybatis self;
	
	private DataBasePropertyDAO_Mybatis() {
		super();
	}
	
	public static DataBasePropertyDAO_Mybatis getInstance() {
		if(self==null) self = new DataBasePropertyDAO_Mybatis();
		return self;
	}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public List<DataBasePropertyVO> selectDataBaseProperties(DataBasePropertyVO param) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IDataBasePropertyDAO mapper = session.getMapper(IDataBasePropertyDAO.class);
			return mapper.selectDataBaseProperties(param);
		}
	}

	@Override
	public List<String> selectAllProperty_names() {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IDataBasePropertyDAO mapper = session.getMapper(IDataBasePropertyDAO.class);
			return mapper.selectAllProperty_names();
		}
	}

}

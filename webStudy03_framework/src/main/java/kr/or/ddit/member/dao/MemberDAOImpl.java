package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

public class MemberDAOImpl implements IMemberDAO {
	
	private static MemberDAOImpl self;
	
	private MemberDAOImpl() {
		super();
	}
	
	public static MemberDAOImpl getInstance() {
		if(self==null)
			self = new MemberDAOImpl();
		return self;
	}

	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public int insertMember(MemberVO member) {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);
		){		
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			return mapper.insertMember(member);
		}
	}

	@Override
	public int selectMemberCount(PagingVO<MemberVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){		
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			return mapper.selectMemberCount(pagingVO);
		}
	}
	
	@Override
	public List<MemberVO> selectMemberList(PagingVO pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){		
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			return mapper.selectMemberList(pagingVO);
		}
	}

	@Override
	public MemberVO selectMember(String mem_id) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){		
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			return mapper.selectMember(mem_id);
		}
	}

	@Override
	public int updateMember(MemberVO member) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){		
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			int rowcnt = mapper.updateMember(member);
			session.commit();
			return rowcnt;
		}
	}

	@Override
	public int deleteMember(String mem_id) {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);
		){		
			IMemberDAO mapper = session.getMapper(IMemberDAO.class);
			return mapper.deleteMember(mem_id);
		}
	}

}

package kr.or.ddit.member.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.MemberVO;

public class MemberDAOImplTest {
	IMemberDAO dao;
	MemberVO testMember;
	@Before
	public void setUp() throws Exception {
		dao = MemberDAOImpl.getInstance();
		testMember = MemberVO.builder()
					.mem_id("a009")
					.mem_name("테스트명")
					.mem_pass("java")
					.mem_regno1("111111")
					.mem_regno2("1111111")
					.mem_zip("0000")
					.mem_add1("대전 대흥동")
					.mem_add2("영민빌딩")
					.mem_comtel("000-000-0000")
					.mem_hometel("000-000-0000")
					.mem_mail("aa@naver.com")
					.build();
	}

	@Test
	public void testInsertMember() {
		int rowcnt = dao.insertMember(testMember);
		assertNotEquals(0, rowcnt);
	}

	@Test
	public void testSelectMemberList() {
		List<MemberVO> list = dao.selectMemberList();
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}

	@Test(timeout=2000)
	public void testSelectMember() {
		MemberVO member = dao.selectMember("a001");
		assertNotNull(member);
		member = dao.selectMember("asdfasdfasf");
		assertNull(member);
	}

	@Test
	public void testUpdateMember() {
		int rowcnt = dao.updateMember(testMember);
		assertEquals(0, rowcnt);
	}

//	@Test(expected=SQLException.class)
	@Test(expected=PersistenceException.class)
	public void testDeleteMember() {
		int rowcnt = dao.deleteMember("a001");
	}
}











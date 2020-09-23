package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.ProdVO;

public class ProdDAOImplTest {
	IProdDAO dao;
	ProdVO testProd;

	@Before
	public void setUp() throws Exception {
		dao = new ProdDAOImpl();
		testProd = ProdVO.builder()
					.prod_name("신규상품")
					.prod_lgu("P101")
					.prod_buyer("P10101")
					.prod_outline("상품개요")
					.prod_img("가상이미지경로???")
					.prod_cost(3000)
					.prod_price(3000)
					.prod_sale(3000)
					.prod_totalstock(3000)
					.prod_properstock(3000)
					.prod_color("색상")
					.prod_unit("단위")
					.prod_delivery("배송방법")
					.prod_detail("상세정보")
					.build();
	}

	@Test
	public void testSelectProd() {
		ProdVO prod = dao.selectProd("P101000001");
		assertNotNull(prod);
		assertNotEquals(0, prod.getMemberList().size());
	}

	@Test
	public void testInsertProd() {
		int rowcnt = dao.insertProd(testProd);
		assertEquals(1, rowcnt);
	}

}

package kr.or.ddit.prod.dao;

import kr.or.ddit.vo.ProdVO;

/**
 * 상품관리 Persistence Layer
 *
 */
public interface IProdDAO {
	public ProdVO selectProd(String prod_id);
}

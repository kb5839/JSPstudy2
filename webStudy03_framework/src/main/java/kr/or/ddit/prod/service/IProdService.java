package kr.or.ddit.prod.service;

import kr.or.ddit.vo.ProdVO;

/**
 * 상품관리 Business Logic Layer
 *
 */
public interface IProdService {
	/**
	 * 상품 상세 조회
	 * @param prod_id
	 * @return 존재하지 않는다면, CustomException 발생.
	 */
	public ProdVO retrieveProd(String prod_id);
}

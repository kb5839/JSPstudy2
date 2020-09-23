package kr.or.ddit.prod.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	
	IProdDAO dao = new ProdDAOImpl();

	@Override
	public ProdVO retrieveProd(String prod_id) {
		ProdVO prod = dao.selectProd(prod_id);
		if(prod==null)
			throw new CustomException( prod_id+"는 존재하지 않는 상품임.");
		return prod;
	}

	@Override
	public ServiceResult createProd(ProdVO prod) {
		int rowcnt = dao.insertProd(prod);
		ServiceResult result = ServiceResult.FAILED;
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}
		return result;
	}
}

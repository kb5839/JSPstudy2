package kr.or.ddit.buyer.service;

import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.vo.BuyerVO;

public class BuyerServiceImpl implements IBuyerService{
	
	IBuyerDAO dao = new BuyerDAOImpl();
	
	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		BuyerVO buyer = dao.selectBuyer(buyer_id);
		if(buyer == null) {
			throw new CustomException(buyer_id+"에 해당하는 거래처는 없음.");
		}
		return buyer;
	}
}















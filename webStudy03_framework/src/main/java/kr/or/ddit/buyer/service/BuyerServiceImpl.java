package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerServiceImpl implements IBuyerService {
	IBuyerDAO buyerDAO = new BuyerDAOImpl();
	@Override
	public int retrieveBuyerCount(PagingVO<BuyerVO> pagingVO) {
		return buyerDAO.selectBuyerCount(pagingVO);
	}

	@Override
	public List<BuyerVO> retrieveBuyerList(PagingVO<BuyerVO> pagingVO) {
		return buyerDAO.selectBuyerList(pagingVO);
	}

	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		BuyerVO buyer = buyerDAO.selectBuyer(buyer_id);
		if(buyer==null) throw new CustomException("없음.");
		return buyer;
	}

	@Override
	public ServiceResult createBuyer(BuyerVO buyer) {
		int rowcnt = buyerDAO.insertBuyer(buyer);
		ServiceResult result = ServiceResult.OK;
		if(rowcnt<= 0) result = ServiceResult.FAILED;
		return result;
	}

	@Override
	public ServiceResult modifyBuyer(BuyerVO buyer) {
		retrieveBuyer(buyer.getBuyer_id());
		int rowcnt = buyerDAO.updateBuyer(buyer);
		ServiceResult result = ServiceResult.OK;
		if(rowcnt <= 0) result = ServiceResult.FAILED;
		return result;
	}

}

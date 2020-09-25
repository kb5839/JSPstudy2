package kr.or.ddit.prod.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.listener.SampleListener;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	
	IProdDAO dao = new ProdDAOImpl();
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	File folder;
	
	public ProdServiceImpl() {
		super();
		String folerURL = "/prodImages";
		String folerPath = SampleListener.currentContext.getRealPath(folerURL);
		folder = new File(folerPath);
	}

	@Override
	public ProdVO retrieveProd(String prod_id) {
		ProdVO prod = dao.selectProd(prod_id);
		if(prod==null)
			throw new CustomException( prod_id+"는 존재하지 않는 상품임.");
		return prod;
	}

	@Override
	public ServiceResult createProd(ProdVO prod) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			int rowcnt = dao.insertProd(prod, session);
			if(prod.getProd_image()!=null) {
				prod.getProd_image().saveToRealPath(folder);
			}
			ServiceResult result = ServiceResult.FAILED;
			if(rowcnt>0) {
				result = ServiceResult.OK;
				session.commit();
			}
			return result;
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int retrieveProdCount(PagingVO<ProdVO> pagingVO) {
		return dao.selectProdCount(pagingVO);
	}

	@Override
	public List<ProdVO> retrieveProdList(PagingVO<ProdVO> pagingVO) {
		return dao.selectProdList(pagingVO);
	}

	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			int rowcnt = dao.updateProd(prod, session);
			if(prod.getProd_image()!=null) {
				prod.getProd_image().saveToRealPath(folder);
			}
			ServiceResult result = ServiceResult.FAILED;
			if(rowcnt>0) {
				result = ServiceResult.OK;
				session.commit();
			}
			return result;
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}















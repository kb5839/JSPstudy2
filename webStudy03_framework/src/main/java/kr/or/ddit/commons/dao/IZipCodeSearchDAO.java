package kr.or.ddit.commons.dao;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipCodeVO;

public interface IZipCodeSearchDAO {
	public int selectTotalCount(PagingVO pagingVO);
	public List<ZipCodeVO> selectZipcodeList(PagingVO pagingVO);
}

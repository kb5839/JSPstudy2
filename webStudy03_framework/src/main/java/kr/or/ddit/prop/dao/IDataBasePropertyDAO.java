package kr.or.ddit.prop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.DataBasePropertyVO;

public interface IDataBasePropertyDAO {
	public List<DataBasePropertyVO> selectDataBaseProperties(@Param("propParam") DataBasePropertyVO param);
	public List<String> selectAllProperty_names();
}

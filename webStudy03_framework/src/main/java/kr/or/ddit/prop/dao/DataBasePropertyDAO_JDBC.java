package kr.or.ddit.prop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyDAO_JDBC implements IDataBasePropertyDAO{
	
	@Override
	public List<String> selectAllProperty_names() {
		List<String> list = new ArrayList<>();
		try(
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();	
		){
			String sql = " SELECT PROPERTY_NAME FROM DATABASE_PROPERTIES ";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString(1));
			}
			return list;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<DataBasePropertyVO> selectDataBaseProperties(DataBasePropertyVO param) {
		
		String sql = "SELECT PROPERTY_NAME, PROPERTY_VALUE, DESCRIPTION FROM DATABASE_PROPERTIES";
		StringBuffer where = null;
		if(param!=null) {
			where = new StringBuffer();
			String ptrn = " %s LIKE '%%%s%%' ";
			if(StringUtils.isNoneBlank(param.getProperty_name())) {
				where.append(String.format(ptrn, "PROPERTY_NAME", param.getProperty_name()));
			}
			if(StringUtils.isNoneBlank(param.getProperty_value())) {
				if(where.length()>0) {
					where.append(" AND ");
				}
				where.append(String.format(ptrn, "PROPERTY_VALUE", param.getProperty_value()));
			}
			if(StringUtils.isNoneBlank(param.getDecription())) {
				if(where.length()>0) {
					where.append(" AND ");
				}
				where.append(String.format(ptrn, "DESCRIPTION", param.getDecription()));
			}
			if(where.length()>0) {
				where.insert(0, " WHERE ");
			}
		}
		sql = sql + where;
		
		System.out.println(sql);
		
		List<DataBasePropertyVO> list = new ArrayList<>();

		try(
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
		){
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(
					DataBasePropertyVO.getBuilder()
									.property_name(rs.getString("PROPERTY_NAME"))
									.property_value(rs.getString("PROPERTY_VALUE"))
									.decription(rs.getString("DESCRIPTION"))
									.build());
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return list;
	}

}

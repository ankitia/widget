package com.ia.IMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ia.Dao.ZumperDao;
import com.ia.modal.MasterZumperURL;
import com.ia.modal.ZumperAmenitiesData;
import com.ia.modal.ZumperData;
import com.mysql.jdbc.Statement;


@Component("zumperDao")
public class ZumperImpl implements ZumperDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertZumperData(ZumperData zumperData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into zumper_data(address,agent_company_name,agent_hours,agent_lst,agent_name,agent_phone,amenities_lst,breadcumb_text,description,phone_number,price,summary,title,todo,url,url_id,user_id,walkscore,walkscore_description,ipaddress) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, zumperData.getAddress());
			ps.setString(2, zumperData.getAgent_company_name());
			ps.setString(3, zumperData.getAgent_hours());
			ps.setString(4, zumperData.getAgent_lst());
			ps.setString(5, zumperData.getAgent_name());
			ps.setString(6, zumperData.getAgent_phone());
			ps.setString(7, zumperData.getAmenities_lst());
			ps.setString(8, zumperData.getBreadcumb_text());
			ps.setString(9, zumperData.getDescription());
			ps.setString(10, zumperData.getPhone_number());
			ps.setString(11, zumperData.getPrice());
			ps.setString(12, zumperData.getSummary());
			ps.setString(13, zumperData.getTitle());
			ps.setString(14, zumperData.getTodo());
			ps.setString(15, zumperData.getUrl());
			ps.setString(16, zumperData.getUrl_id());
			ps.setString(17, zumperData.getUser_id());
			ps.setString(18, zumperData.getWalkscore());
			ps.setString(19, zumperData.getWalkscore_description());
			ps.setString(20, zumperData.getIpaddress());
			
			status = ps.executeUpdate();
			con.commit();		
			 try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	status = generatedKeys.getInt(1);
		            } else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
		        }catch (Exception e) {
		        	e.printStackTrace();
				}
			 con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Status zumperDataData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterZumperURL> getZumperUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterZumperURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_zumper_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_zumper_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				sql = "select m.* from master_zumper_url m where  m.status='Done' and  m.user_id = ? and m.master_zumper_url_id not in (select url_id from zumper_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_zumper_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterZumperURL masterURL = new MasterZumperURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_zumper_url_id")));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				data.add(masterURL);
			}
			con.close();			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return data;
		
	}
	
	@Override
	public List<ZumperData> getZumperData(int userId) {
		// TODO Auto-generated method stub
			List<ZumperData> zumperDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from zumper_data where user_id = ? order by bing_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ZumperData zumperData = new ZumperData();
				zumperData.setZumperId(rs.getInt("zumper_data_id"));
				zumperData.setAddress(rs.getString("address"));;
				zumperData.setAgent_hours(rs.getString("agent_hours"));;
				zumperData.setUrl(rs.getString("url"));
				zumperData.setUrl_id(rs.getString("url_id"));
				zumperDatas.add(zumperData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return zumperDatas;
	}

	@Override
	public int insertZumperAmenitiesData(ZumperAmenitiesData amenitiesData) {
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into zumper_amenities_list(header,amenities,zumper_id) value(?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, amenitiesData.getHeader());
			ps.setString(2, amenitiesData.getAmenities());
			ps.setInt(3, amenitiesData.getZumperId());
			status = ps.executeUpdate();
			con.commit();		
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return status;
	}

}


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

import com.ia.Dao.ZillowDao;
import com.ia.modal.MasterZillowURL;
import com.ia.modal.ZillowData;
import com.mysql.jdbc.Statement;

  
@Component("zillowDao")
public class ZillowImpl implements ZillowDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertZillowData(ZillowData zillowData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into zillow_data(address_div,agent_listing,data_lst,fact_list,home_values,image0,image1,image2,image3,image4,monthly_cost,nearby_school,popup_div,premier_leader,rental_home_values,sub_section_container0,sub_section_container1,sub_section_container2,sub_section_container3,sub_section_container4,sub_section_container5,sub_section_container6,sub_section_container7,sub_section_container8,sub_section_container9,sub_section_container10,sub_section_container11,sub_section_container12,sub_section_container13,sub_section_container14,sub_section_container15,sub_section_container16,sub_section_container17,sub_section_container18,sub_section_container19,sub_section_container20,sub_section_container21,sub_section_container22,todo,url_id,user_id,url,ipaddress) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, zillowData.getAddress_div());
			ps.setString(2, zillowData.getAgent_listing());
			ps.setString(3, zillowData.getData_lst());
			ps.setString(4, zillowData.getFact_list());
			ps.setString(5, zillowData.getHome_values());
			ps.setString(6, zillowData.getImage0());
			ps.setString(7, zillowData.getImage1());
			ps.setString(8, zillowData.getImage2());
			ps.setString(9, zillowData.getImage3());
			ps.setString(10, zillowData.getImage4());
			ps.setString(11, zillowData.getMonthly_cost());
			ps.setString(12, zillowData.getNearby_school());
			ps.setString(13, zillowData.getPopup_div());
			ps.setString(14, zillowData.getPremier_leader());
			ps.setString(15, zillowData.getRental_home_values());
			ps.setString(16, zillowData.getSub_section_container0());
			ps.setString(17, zillowData.getSub_section_container1());
			ps.setString(18, zillowData.getSub_section_container2());
			ps.setString(19, zillowData.getSub_section_container3());
			ps.setString(20, zillowData.getSub_section_container4());
			ps.setString(21, zillowData.getSub_section_container5());
			ps.setString(22, zillowData.getSub_section_container6());
			ps.setString(23, zillowData.getSub_section_container7());
			ps.setString(24, zillowData.getSub_section_container8());
			ps.setString(25, zillowData.getSub_section_container9());
			ps.setString(26, zillowData.getSub_section_container10());
			ps.setString(27, zillowData.getSub_section_container11());
			ps.setString(28, zillowData.getSub_section_container12());
			ps.setString(29, zillowData.getSub_section_container13());
			ps.setString(30, zillowData.getSub_section_container14());
			ps.setString(31, zillowData.getSub_section_container15());
			ps.setString(32, zillowData.getSub_section_container16());
			ps.setString(33, zillowData.getSub_section_container17());
			ps.setString(34, zillowData.getSub_section_container18());
			ps.setString(35, zillowData.getSub_section_container19());
			ps.setString(36, zillowData.getSub_section_container20());
			ps.setString(37, zillowData.getSub_section_container21());
			ps.setString(38, zillowData.getSub_section_container22());
			ps.setString(39, zillowData.getTodo());
			ps.setString(40, zillowData.getUrl_id());
			ps.setString(41, zillowData.getUser_id());
			ps.setString(42, zillowData.getUrl());
			ps.setString(43, zillowData.getIpaddress());
			
			
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
		System.out.println("Status insertZillowData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterZillowURL> getZillowUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterZillowURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_zillow_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_zillow_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_zillow_url m where  m.status='Done' and  m.user_id = ? and m.master_zillow_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_zillow_url m where  m.status='Done' and  m.user_id = ? and m.master_zillow_url_id not in (select url_id from bing_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_zillow_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterZillowURL masterURL = new MasterZillowURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_zillow_url_id")));
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
	public List<ZillowData> getZillowData(int userId,String action) {
		// TODO Auto-generated method stub
			List<ZillowData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from zillow_data where user_id = ? order by zillow_id desc limit 0,10";
			
			if(action.equalsIgnoreCase("all")) {
				sql = "select * from zillow_data where user_id = ? order by zillow_id desc limit 35000,20000";
			}else if(action.equalsIgnoreCase("display")){
				sql = "select * from zillow_data where user_id = ? order by zillow_id desc limit 0,10";
			}
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ZillowData bingData = new ZillowData();
				bingData.setZillowId(rs.getInt("zillow_id"));
				bingData.setData_lst(rs.getString("data_lst"));;
				bingData.setSub_section_container0(rs.getString("sub_section_container0"));;
				bingData.setPopup_div(rs.getString("popup_div"));
				bingData.setUrl(rs.getString("url"));
				bingData.setUrl_id(rs.getString("url_id"));
				bingDatas.add(bingData);
				System.out.println(rs.getInt("zillow_id"));
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bingDatas;
	}

	 

}


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

import com.ia.Dao.GovShopDao;
import com.ia.modal.GovShopContactList;
import com.ia.modal.GovShopData;
import com.ia.modal.MasterData;
import com.mysql.jdbc.Statement;


@Component("govShopDao")
public class GovShopImpl implements GovShopDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertGovShopData(GovShopData govShopData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into govshop_data(title,address,textual_overview,duns,cage_code,type,employees,sectors_served,company_security_level,credit_card_usage,annual_revenue,year_founded,country_of_origin,public_sector_interest,spec_title,spec_textual,url_id,contact_info_lst,remarks,url,user_id,ipaddress,website) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, govShopData.getTitle());
			ps.setString(2, govShopData.getAddress());
			ps.setString(3, govShopData.getTextual_overview());
			ps.setString(4, govShopData.getDuns());
			ps.setString(5, govShopData.getCage_code());
			ps.setString(6, govShopData.getType());
			ps.setString(7, govShopData.getEmployees());
			ps.setString(8, govShopData.getSectors_served());
			ps.setString(9, govShopData.getCompany_security_level());
			ps.setString(10, govShopData.getCredit_card_usage());
			ps.setString(11, govShopData.getAnnual_revenue());
			ps.setString(12, govShopData.getYear_founded());
			ps.setString(13, govShopData.getCountry_of_origin());
			ps.setString(14, govShopData.getPublic_sector_interest());
			ps.setString(15, govShopData.getSpec_title());
			ps.setString(16, govShopData.getSpec_textual());
			ps.setString(17, govShopData.getUrl_id());
			ps.setString(18, govShopData.getContact_info_lst());
			ps.setString(19, govShopData.getRemarks());
			ps.setString(20, govShopData.getUrl());
			ps.setString(21, govShopData.getUser_id());
			ps.setString(22, govShopData.getIpaddress());
			ps.setString(23, govShopData.getWebsite());
			
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
		System.out.println("Status BingDataData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterData> getGovShopUrlList(int userId,String action){
		// TODO Auto-generated method stub
		List<MasterData> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_govshop_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_govshop_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_govshop_url m where  m.status='Done' and  m.user_id = ? and m.master_govshop_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_govshop_url m where  m.status='Done' and  m.user_id = ? and m.master_govshop_url_id not in (select url_id from bing_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_govshop_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterData masterURL = new MasterData();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_govshop_url_id")));
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
	public List<GovShopData> getGovShopData(int userId) {
			List<GovShopData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from govshop_data where user_id = ? order by govshop_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				GovShopData bingData = new GovShopData();
				bingData.setGovId(rs.getInt("govshop_data_id"));
				bingData.setTitle(rs.getString("title"));;
				bingData.setAddress(rs.getString("address"));;
				bingData.setUrl(rs.getString("url"));
				bingData.setUrl_id(rs.getString("url_id"));
				bingDatas.add(bingData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bingDatas;
		
	}

	@Override
	public int insertGovShopDetailData(GovShopContactList contactList) {
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into govshop_contact_info_list(contact_title,contact_box_name,contact_box_position,contact_box_address,contact_box_phone,contact_box_email,govshop_id) value(?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, contactList.getContact_title());
			ps.setString(2, contactList.getContact_box_name());
			ps.setString(3, contactList.getContact_box_position());
			ps.setString(4, contactList.getContact_box_address());
			ps.setString(5, contactList.getContact_box_phone());
			ps.setString(6, contactList.getContact_box_email());
			ps.setInt(7, contactList.getGovshop_id());
			status = ps.executeUpdate();
			con.commit();		
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public MasterData getGovShop(String url) {
		MasterData masterData = new MasterData();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from master_govshop_url where url = ? order by master_govshop_url_id desc limit 0,1";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,url);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				masterData.setUrlId(rs.getInt("master_govshop_url_id"));
				masterData.setUrl(rs.getString("url"));;
				masterData.setUserId(rs.getInt("user_id"));;
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return masterData;
	}

}


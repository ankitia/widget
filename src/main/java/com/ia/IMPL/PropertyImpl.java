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

import com.ia.Dao.PropertyDao;
import com.ia.modal.MasterGoogleURL;
import com.ia.modal.Property;
import com.mysql.jdbc.Statement;


@Component("propertyDao")
public class PropertyImpl implements PropertyDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertPropertyData(Property property) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into property_data(name,rating,industry,address,phone_number,timing,direction,website,url,record_start_number,ipaddress,user_id,url_id,current_page_no,last_page_no,is_success) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, property.getName());
			ps.setString(2, property.getRating());
			ps.setString(3, property.getIndustry());
			ps.setString(4, property.getAddress());
			ps.setString(5, property.getPhone_number());
			ps.setString(6, property.getTiming());
			ps.setString(7, property.getDirection());
			ps.setString(8, property.getWebsite());
			ps.setString(9, property.getUrl());
			ps.setString(10, property.getRecord_start_number());
			ps.setString(11, property.getIpaddress());
			ps.setString(12, property.getUser_id());
			ps.setString(13, property.getUrl_id());
			ps.setString(14, property.getCurrent_page_no());
			ps.setString(15, property.getLast_page_no());
			ps.setString(16, property.getIs_success());
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
		System.out.println("Status PropertyData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterGoogleURL> getGoogleUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterGoogleURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_google_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_google_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_google_url m where  m.status='Done' and  m.user_id = ? and m.master_google_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_google_url m where  m.status='Done' and  m.user_id = ? and m.master_google_url_id not in (select url_id from property_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_google_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterGoogleURL masterURL = new MasterGoogleURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_google_url_id")));
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
	public List<Property> getGoogleData(int userId) {
		// TODO Auto-generated method stub
			List<Property> properties = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from property_data where user_id = ? order by property_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Property property = new Property();
				property.setPropertyId(rs.getInt("property_id"));
				property.setName(rs.getString("name"));;
				property.setRating(rs.getString("rating"));;
				property.setIndustry(rs.getString("industry"));;
				property.setUrl(rs.getString("url"));
				property.setUrl_id(rs.getString("url_id"));
				properties.add(property);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return properties;
	}

}

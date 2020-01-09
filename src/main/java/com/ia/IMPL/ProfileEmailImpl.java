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

import com.ia.Dao.ProfileEmailDao;
import com.ia.modal.MasterProfileEmailURL;
import com.ia.modal.ProfileEmail;
import com.mysql.jdbc.Statement;

  
@Component("profileEmailDao")
public class ProfileEmailImpl implements ProfileEmailDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertProfileEmailData(ProfileEmail profileEmail) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into profile_email_data(name,designation,company_name,company_url,location_name,links,root_url,url_id,user_id,ipaddress,url,message,full_designation) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, profileEmail.getName());
			ps.setString(2, profileEmail.getDesignation());
			ps.setString(3, profileEmail.getCompany_name());
			ps.setString(4, profileEmail.getCompany_url());
			ps.setString(5, profileEmail.getLocation_name());
			ps.setString(6, profileEmail.getLinks());
			ps.setString(7, profileEmail.getRoot_url());
			ps.setString(8, profileEmail.getUrl_id());
			ps.setString(9, profileEmail.getUser_id());
			ps.setString(10, profileEmail.getIpaddress());
			ps.setString(11, profileEmail.getUrl());
			ps.setString(12, profileEmail.getMessage());
			ps.setString(13, profileEmail.getFull_designation());
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
		System.out.println("Status insertProfileEmailData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterProfileEmailURL> getProfileEmailUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterProfileEmailURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_profile_email_data where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_profile_email_data where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_zillow_url m where  m.status='Done' and  m.user_id = ? and m.master_zillow_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_profile_email_data m where  m.status='Done' and  m.user_id = ? and m.master_profile_email_data_id not in (select url_id from profile_email_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_profile_email_data where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterProfileEmailURL masterURL = new MasterProfileEmailURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_profile_email_data_id")));
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
	public List<ProfileEmail> getProfileEmailData(int userId) {
		// TODO Auto-generated method stub
			List<ProfileEmail> profileEmails = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from profile_email_data where user_id = ? order by profile_email_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ProfileEmail profileEmail = new ProfileEmail();
				profileEmail.setProfileId(rs.getInt("profile_email_data_id"));
				profileEmail.setName(rs.getString("name"));;
				profileEmail.setDesignation(rs.getString("designation"));;
				profileEmail.setCompany_name(rs.getString("company_name"));
				profileEmail.setCompany_url(rs.getString("company_url"));
				profileEmail.setRoot_url(rs.getString("root_url"));
				profileEmail.setUrl_id(rs.getString("url_id"));
				profileEmails.add(profileEmail);
				System.out.println(rs.getInt("profile_email_data_id"));
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return profileEmails;
	}

	

	@Override
	public MasterProfileEmailURL getMasterURLDetail(String url) {
		// TODO Auto-generated method stub
		List<MasterProfileEmailURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_profile_email_data where url='"+url+"' order by  master_profile_email_data_id desc limit 0,1";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterProfileEmailURL masterURL = new MasterProfileEmailURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_profile_email_data_id")));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				masterURL.setStatus(rs.getString("status"));
				data.add(masterURL);
			}
			con.close();			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		if(data.size() >0 )		
			return data.get(0);
		else 
			return null;
			
	}

	

	 

}


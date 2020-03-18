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

import com.ia.Dao.MantaDao;
import com.ia.modal.MantaData;
import com.ia.modal.MasterMantaURL;
import com.mysql.jdbc.Statement;


@Component("mantaDao")
public class MantaImpl implements MantaDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertMantaData(MantaData mantaData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into manta_data(header_title,header_address,title,about_phone,about_website,about_address,description,short_description,long_description,reviews,main_phone,website,contact_name,contact_email,contact_job_title,address,direction_link,location_type,year_established,annual_revenue,employees,sic_code,naics_code,business_categories,url,url_id,user_id,remarks,ipaddress,alt_title,about_street_name,about_city,about_state,about_zipcode,street_name,city,state,zipcode,about_category_txt,president,contact,contact_info,detailed_information,services,facebook_link,twitter_link,contact_info_name,contact_info_designation,contact_info_phone,contact_info_email,contact_info_name1,contact_info_designation1,contact_info_phone1,contact_info_email1,contact_info_name2,contact_info_designation2,contact_info_phone2,contact_info_email2) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			//header_title,header_address,title,about_phone,about_website,about_address,description,short_description,long_description,reviews,main_phone,website,contact_name,contact_email,contact_job_title,address,direction_link,location_type,year_established,annual_revenue,employees,sic_code,naics_code,business_categories,url,url_id,user_id,remarks,ipaddress
			
			ps.setString(1, mantaData.getHeader_title());
			ps.setString(2, mantaData.getHeader_address());
			ps.setString(3, mantaData.getTitle());
			ps.setString(4, mantaData.getAbout_phone());
			ps.setString(5, mantaData.getAbout_website());
			ps.setString(6, mantaData.getAbout_address());
			ps.setString(7, mantaData.getDescription());
			ps.setString(8, mantaData.getShort_description());
			ps.setString(9, mantaData.getLong_description());
			ps.setString(10, mantaData.getReviews());
			ps.setString(11, mantaData.getMain_phone());
			ps.setString(12, mantaData.getWebsite());
			ps.setString(13, mantaData.getContact_name());
			ps.setString(14, mantaData.getContact_email());
			ps.setString(15, mantaData.getContact_job_title());
			ps.setString(16, mantaData.getAddress());
			ps.setString(17, mantaData.getDirection_link());
			ps.setString(18, mantaData.getLocation_type());
			ps.setString(19, mantaData.getYear_established());
			ps.setString(20, mantaData.getAnnual_revenue());
			ps.setString(21, mantaData.getEmployees());
			ps.setString(22, mantaData.getSic_code());
			ps.setString(23, mantaData.getNaics_code());
			ps.setString(24, mantaData.getBusiness_categories());
			ps.setString(25, mantaData.getUrl());
			ps.setString(26, mantaData.getUrl_id());
			ps.setString(27, mantaData.getUser_id());
			ps.setString(28, mantaData.getRemarks());
			ps.setString(29, mantaData.getIpaddress());
			ps.setString(30, mantaData.getAlt_title());
			ps.setString(31, mantaData.getAbout_street_name());
			ps.setString(32, mantaData.getAbout_city());
			ps.setString(33, mantaData.getAbout_state());
			ps.setString(34, mantaData.getAbout_zipcode());
			ps.setString(35, mantaData.getStreet_name());
			ps.setString(36, mantaData.getCity());
			ps.setString(37, mantaData.getState());
			ps.setString(38, mantaData.getZipcode());
			ps.setString(39, mantaData.getAbout_category_txt());
			ps.setString(40, mantaData.getPresident());
			ps.setString(41, mantaData.getContact());
			ps.setString(42, mantaData.getContact_info());
			ps.setString(43, mantaData.getDetailed_information());
			ps.setString(44, mantaData.getServices());
			ps.setString(45, mantaData.getFacebook_link());
			ps.setString(46, mantaData.getTwitter_link());
			ps.setString(47, mantaData.getContact_info_name());
			ps.setString(48, mantaData.getContact_info_designation());
			ps.setString(49, mantaData.getContact_info_phone());
			ps.setString(50, mantaData.getContact_info_email());
			ps.setString(51, mantaData.getContact_info_name1());
			ps.setString(52, mantaData.getContact_info_designation1());
			ps.setString(53, mantaData.getContact_info_phone1());
			ps.setString(54, mantaData.getContact_info_email1());
			ps.setString(55, mantaData.getContact_info_name2());
			ps.setString(56, mantaData.getContact_info_designation2());
			ps.setString(57, mantaData.getContact_info_phone2());
			ps.setString(58, mantaData.getContact_info_email2());
			
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
		System.out.println("Status insertMantaData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterMantaURL> getMantaUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterMantaURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_manta_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_manta_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				sql = "select m.* from master_manta_url m where  m.status='Done' and  m.user_id = ? and m.master_manta_url_id not in (select url_id from manta_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_manta_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterMantaURL masterURL = new MasterMantaURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_manta_url_id")));
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
	public List<MantaData> getMantaData(int userId) {
		// TODO Auto-generated method stub
			List<MantaData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from manta_data where user_id = ? order by manta_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				MantaData bingData = new MantaData();
				bingData.setMantaId(rs.getInt("manta_data_id"));
				bingData.setAbout_phone(rs.getString("about_phone"));;
				bingData.setTitle(rs.getString("title"));;
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
	public int insertManta(String url, int google_manta_id) {
		// TODO Auto-generated method stub
				int status = 0; 
				try (Connection con = (Connection) dataSource.getConnection()){
					String sql = "insert into master_manta_url(url,google_manta_id) value(?,?)";
					PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, url);
					ps.setInt(2, google_manta_id);
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
				System.out.println("Status insertMantaData:::"+status);
				return status;
	}

	 

}


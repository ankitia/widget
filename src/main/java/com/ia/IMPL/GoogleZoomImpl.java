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

import com.ia.Dao.GoogleZoomDao;
import com.ia.modal.GoogleZoominfoData;
import com.ia.modal.MasterGoogleZoomInfoURL;
import com.mysql.jdbc.Statement;


@Component("googleZoomDao")
public class GoogleZoomImpl implements GoogleZoomDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertGoogleZoomData(GoogleZoominfoData zoominfoData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into google_zoominfo_data(text,link,char_match_10,char_match_15,char_match_20,search_string,endpoint_type,url,url_id,user_id,ipaddress,exact_match,remarks,is_zoominfo_company_link,is_zoominfo_link,box_company_name,box_website,box_direction,box_rating,box_total_reviews,box_type,box_address,box_hours,box_phone,box_stock_price,box_ceo,box_founder,box_founded,box_headquarters,box_subsidiaries,box_revenue,box_parent_organization,box_description,box_facebook_link,box_twitter_link,box_linkedin_link,box_instagram_link,box_youtube_link,box_pinterest,is_manta_link,is_manta_company_link,address_box_link,address_box_text,address_box_review) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, zoominfoData.getText());
			ps.setString(2, zoominfoData.getLink());
			ps.setString(3, zoominfoData.getChar_match_10());
			ps.setString(4, zoominfoData.getChar_match_15());
			ps.setString(5, zoominfoData.getChar_match_20());
			ps.setString(6, zoominfoData.getSearch_string());
			ps.setString(7, zoominfoData.getEndpoint_type());
			ps.setString(8, zoominfoData.getUrl());
			ps.setString(9, zoominfoData.getUrl_id());
			ps.setString(10, zoominfoData.getUser_id());
			ps.setString(11, zoominfoData.getIpaddress());
			ps.setString(12, zoominfoData.getExact_match());
			ps.setString(13, zoominfoData.getRemarks());
			ps.setString(14, zoominfoData.getIs_zoominfo_company_link());
			ps.setString(15, zoominfoData.getIs_zoominfo_link());
			ps.setString(16, zoominfoData.getBox_company_name());
			ps.setString(17, zoominfoData.getBox_website());
			ps.setString(18, zoominfoData.getBox_direction());
			ps.setString(19, zoominfoData.getBox_rating());
			ps.setString(20, zoominfoData.getBox_total_reviews());
			ps.setString(21, zoominfoData.getBox_type());
			ps.setString(22, zoominfoData.getBox_address());
			ps.setString(23, zoominfoData.getBox_hours());
			ps.setString(24, zoominfoData.getBox_phone());
			ps.setString(25, zoominfoData.getBox_stock_price());
			ps.setString(26, zoominfoData.getBox_ceo());
			ps.setString(27, zoominfoData.getBox_founder());
			ps.setString(28, zoominfoData.getBox_founded());
			ps.setString(29, zoominfoData.getBox_headquarters());
			ps.setString(30, zoominfoData.getBox_subsidiaries());
			ps.setString(31, zoominfoData.getBox_revenue());
			ps.setString(32, zoominfoData.getBox_parent_organization());
			ps.setString(33, zoominfoData.getBox_description());
			ps.setString(34, zoominfoData.getBox_facebook_link());
			ps.setString(35, zoominfoData.getBox_twitter_link());
			ps.setString(36, zoominfoData.getBox_linkedin_link());
			ps.setString(37, zoominfoData.getBox_instagram_link());
			ps.setString(38, zoominfoData.getBox_youtube_link());
			ps.setString(39, zoominfoData.getBox_pinterest());
			ps.setString(40, zoominfoData.getIs_manta_link());
			ps.setString(41, zoominfoData.getIs_manta_company_link());
			ps.setString(42, zoominfoData.getAddress_box_link());
			ps.setString(43, zoominfoData.getAddress_box_text());
			ps.setString(44, zoominfoData.getAddress_box_review());
			
			
			
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
		System.out.println("Status GoogleZoominfoData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterGoogleZoomInfoURL> getGoogleZoomUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterGoogleZoomInfoURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_google_zoominfo_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_google_zoominfo_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_google_zoominfo_url m where  m.status='Done' and  m.user_id = ? and m.master_google_zoominfo_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_google_zoominfo_url m where  m.status='Done' and  m.user_id = ? and m.master_google_zoominfo_url_id not in (select url_id from google_zoominfo_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_google_zoominfo_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterGoogleZoomInfoURL masterURL = new MasterGoogleZoomInfoURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_google_zoominfo_url_id")));
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
	public List<GoogleZoominfoData> getGoogleZoomData(int userId) {
		// TODO Auto-generated method stub
			List<GoogleZoominfoData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from google_zoominfo_data where user_id = ? order by google_zoominfo_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				GoogleZoominfoData bingData = new GoogleZoominfoData();
				bingData.setGoogleZoomId(rs.getInt("google_zoominfo_data_id"));
				bingData.setText(rs.getString("text"));
				bingData.setLink(rs.getString("link"));
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
	public int insertGoogleZoomData(String url,int zoomId) {
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into master_zoominfo_url(url,master_google_zoominfo_url_id) value(?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, url);
			ps.setInt(2, zoomId);
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
		System.out.println("Status insertGoogleZoomData:::"+status);
		return status;
	}

	

	 

}


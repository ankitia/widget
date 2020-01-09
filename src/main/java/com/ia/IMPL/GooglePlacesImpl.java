package com.ia.IMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ia.Dao.GooglePlacesDao;
import com.ia.modal.GooglePlace;
import com.ia.modal.MasterGooglePlaceURL;


@Component("googlePlacesDao")
public class GooglePlacesImpl implements GooglePlacesDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 

	@Override
	public boolean insertGooglePlace(GooglePlace googlePlace) {
		int status = 0;
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "insert into google_place(property_name,website,direction,rating,total_rating,industry,phone_number,address,detail_title,detail_website,detail_directions,detail_industry,\n" + 
					"detail_rating,detail_address,detail_hours,detail_phone_number,social_links,root_url,ipaddress,user_id,url_id) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, googlePlace.getProperty_name());
			ps.setString(2, googlePlace.getWebsite());
			ps.setString(3, googlePlace.getDirection());
			ps.setString(4, googlePlace.getRating());
			ps.setString(5, googlePlace.getTotal_rating());
			ps.setString(6, googlePlace.getIndustry());
			ps.setString(7, googlePlace.getPhone_number());
			ps.setString(8, googlePlace.getAddress());
			ps.setString(9, googlePlace.getDetail_title());
			ps.setString(10, googlePlace.getDetail_website());
			ps.setString(11, googlePlace.getDetail_directions());
			ps.setString(12, googlePlace.getDetail_industry());
			ps.setString(13, googlePlace.getDetail_rating());
			ps.setString(14, googlePlace.getDetail_address());
			ps.setString(15, googlePlace.getDetail_hours());
			ps.setString(16, googlePlace.getDetail_phone_number());
			ps.setString(17, googlePlace.getSocial_links());
			ps.setString(18, googlePlace.getRoot_url());
			ps.setString(19, googlePlace.getIpaddress());
			ps.setString(20, googlePlace.getUser_id());
			ps.setString(21, googlePlace.getUrl_id());
			status = ps.executeUpdate();
			con.close();	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("insertGooglePlace :::"+status);
		if(status > 0)
			return true;
		else		
			return false;
		
	}	
	
	@Override
	public List<MasterGooglePlaceURL> getGooglePlaceUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterGooglePlaceURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_google_place_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_google_place_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_google_place_url m where  m.status='Done' and  m.user_id = ? and m.master_google_place_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_google_place_url m where  m.status='Done' and  m.user_id = ? and m.master_google_place_url_id not in (select url_id from google_place where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_google_place_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterGooglePlaceURL masterURL = new MasterGooglePlaceURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_google_place_url_id")));
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
	public List<GooglePlace> getGoogleMapsData(int userId) {
		// TODO Auto-generated method stub
			List<GooglePlace> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from google_place where user_id = ? order by google_place_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				GooglePlace bingData = new GooglePlace();
				bingData.setGooglePlaceId(rs.getInt("google_place_id"));
				bingData.setProperty_name(rs.getString("property_name"));
				bingData.setWebsite(rs.getString("website"));
				bingData.setDirection(rs.getString("direction"));
				bingData.setUrl_id(rs.getString("url_id"));
				bingData.setRating(rs.getString("rating"));
				bingData.setRoot_url(rs.getString("root_url"));
				bingDatas.add(bingData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bingDatas;
	}

	
 

}


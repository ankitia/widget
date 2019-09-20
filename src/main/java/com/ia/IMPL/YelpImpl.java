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

import com.ia.Dao.YelpDao;
import com.ia.modal.MasterYelpURL;
import com.ia.modal.YelpData;
import com.mysql.jdbc.Statement;

  
@Component("yelpDao")
public class YelpImpl implements YelpDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertYelpData(YelpData yelpData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into yelp_data(title,review,star_rating,category,address,direction,phone,website,owner,root_url,url_id,user_id,ipaddress) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, yelpData.getTitle());
			ps.setString(2, yelpData.getReview());
			ps.setString(3, yelpData.getStar_rating());
			ps.setString(4, yelpData.getCategory());
			ps.setString(5, yelpData.getAddress());
			ps.setString(6, yelpData.getDirection());
			ps.setString(7, yelpData.getPhone());
			ps.setString(8, yelpData.getWebsite());
			ps.setString(9, yelpData.getOwner());
			ps.setString(10, yelpData.getRoot_url());
			ps.setString(11, yelpData.getUrl_id());
			ps.setString(12, yelpData.getUser_id());
			ps.setString(13, yelpData.getIpaddress());
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
		System.out.println("Status insertYelpData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterYelpURL> getYelpUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterYelpURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_yelp_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_yelp_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_zillow_url m where  m.status='Done' and  m.user_id = ? and m.master_zillow_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_yelp_url m where  m.status='Done' and  m.user_id = ? and m.master_yelp_url_id not in (select url_id from yelp_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_yelp_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterYelpURL masterURL = new MasterYelpURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_yelp_url_id")));
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
	public List<YelpData> getYelpData(int userId) {
		// TODO Auto-generated method stub
			List<YelpData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from yelp_data where user_id = ? order by yelp_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				YelpData yelpData = new YelpData();
				yelpData.setYelpId(rs.getInt("yelp_data_id"));
				yelpData.setAddress(rs.getString("address"));;
				yelpData.setDirection(rs.getString("direction"));;
				yelpData.setOwner(rs.getString("owner"));
				yelpData.setRoot_url(rs.getString("root_url"));
				yelpData.setUrl_id(rs.getString("url_id"));
				bingDatas.add(yelpData);
				System.out.println(rs.getInt("yelp_data_id"));
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bingDatas;
	}

	@Override
	public List<MasterYelpURL> exportMasterYelpUrlList() {
		// TODO Auto-generated method stub
		List<MasterYelpURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_yelp_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterYelpURL masterURL = new MasterYelpURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_yelp_url_id")));
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
		return data;
	}

	 

}


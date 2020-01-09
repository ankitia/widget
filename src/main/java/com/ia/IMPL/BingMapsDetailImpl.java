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

import com.ia.Dao.BingMapsDetailDao;
import com.ia.modal.BingMapsDetail;
import com.ia.modal.MasterBingMapsDetailURL;
import com.mysql.jdbc.Statement;


@Component("bingMapsDetailDao")
public class BingMapsDetailImpl implements BingMapsDetailDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertBingMapsData(BingMapsDetail bingData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into bing_maps_detail(address,website,phone,images,remark,root_url,url_id,user_id,ipaddress,category) value(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, bingData.getAddress());
			ps.setString(2, bingData.getWebsite());
			ps.setString(3, bingData.getPhone());
			ps.setString(4, bingData.getImages());
			ps.setString(5, bingData.getRemark());
			ps.setString(6, bingData.getRoot_url());
			ps.setString(7, bingData.getUrl_id());
			ps.setString(8, bingData.getUser_id());
			ps.setString(9, bingData.getIpaddress());
			ps.setString(10, bingData.getCategory());
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
		System.out.println("Status insertBingMapsDetailData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterBingMapsDetailURL> getBingMapsUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterBingMapsDetailURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_bing_maps_detail_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_bing_maps_detail_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_bing_maps_detail_url m where  m.status='Done' and  m.user_id = ? and m.master_bing_maps_detail_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_bing_maps_detail_url m where  m.status='Done' and  m.user_id = ? and m.master_bing_maps_detail_url_id not in (select url_id from bing_maps_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_bing_maps_detail_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterBingMapsDetailURL masterURL = new MasterBingMapsDetailURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_bing_maps_detail_url_id")));
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
	public List<BingMapsDetail> getBingMapsData(int userId) {
		// TODO Auto-generated method stub
			List<BingMapsDetail> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from bing_maps_detail where user_id = ? order by bing_maps_detail_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				BingMapsDetail bingData = new BingMapsDetail();
				bingData.setBingMapsId(rs.getInt("bing_maps_detail_id"));
				bingData.setAddress(rs.getString("address"));
				bingData.setWebsite(rs.getString("website"));
				bingData.setPhone(rs.getString("phone"));
				bingData.setUrl_id(rs.getString("url_id"));
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


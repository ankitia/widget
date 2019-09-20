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

import com.ia.Dao.MapsDao;
import com.ia.modal.MapsData;
import com.ia.modal.MapsPlaceData;
import com.ia.modal.MapsTileData;
import com.ia.modal.MasterMapsURL;
import com.mysql.jdbc.Statement;

  
@Component("mapsDao")
public class MapsImpl implements MapsDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertMapsData(MapsData mapsData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into maps_data(place_data,tile_data,raw_text,root_url,url_id,user_id,ipaddress) value(?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, mapsData.getPlace_data());
			ps.setString(2, mapsData.getTile_data());
			ps.setString(3, mapsData.getRaw_text());
			ps.setString(4, mapsData.getRoot_url());
			ps.setString(5, mapsData.getUrl_id());
			ps.setString(6, mapsData.getUser_id());
			ps.setString(7, mapsData.getIpaddress());
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
		System.out.println("Status insertMapsData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterMapsURL> getMapsUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterMapsURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_maps_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_maps_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_maps_url m where  m.status='Done' and  m.user_id = ? and m.master_maps_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_maps_url m where  m.status='Done' and  m.user_id = ? and m.master_maps_url_id not in (select url_id from bing_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_maps_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterMapsURL masterURL = new MasterMapsURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_maps_url_id")));
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
	public List<MapsData> getMapsData(int userId) {
		// TODO Auto-generated method stub
			List<MapsData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from maps_data where user_id = ? order by maps_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				MapsData bingData = new MapsData();
				bingData.setMapsId(rs.getInt("maps_data_id"));
				bingData.setRoot_url(rs.getString("root_url"));;
				bingData.setUrl_id(rs.getString("url_id"));
				bingDatas.add(bingData);
				System.out.println(rs.getInt("maps_data_id"));
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bingDatas;
	}

	@Override
	public int insertMapsPlaceData(MapsPlaceData mapsPlaceData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into maps_place_data(name,query,categories,domain,reviews,phone,maps_data_id) value(?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, mapsPlaceData.getName());
			ps.setString(2, mapsPlaceData.getQuery());
			ps.setString(3, mapsPlaceData.getCategories());
			ps.setString(4, mapsPlaceData.getDomain());
			ps.setString(5, mapsPlaceData.getReviews());
			ps.setString(6, mapsPlaceData.getPhone());
			ps.setInt(7, mapsPlaceData.getMapsId());
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
	public int insertMapsTileData(MapsTileData mapsTileData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into maps_tile_data(name,rating,location,detail,opening_time,maps_data_id) value(?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, mapsTileData.getName());
			ps.setString(2, mapsTileData.getRating());
			ps.setString(3, mapsTileData.getLocation());
			ps.setString(4, mapsTileData.getDetail());
			ps.setString(5, mapsTileData.getOpening_time());
			ps.setInt(6, mapsTileData.getMapsId());
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
	public List<MasterMapsURL> exportMapsDataUrlList() {
		// TODO Auto-generated method stub
		List<MasterMapsURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_maps_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterMapsURL masterURL = new MasterMapsURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_maps_url_id")));
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


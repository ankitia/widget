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

import com.ia.Dao.BingMapsDao;
import com.ia.modal.BingMapsData;
import com.ia.modal.MasterBingMapsURL;
import com.mysql.jdbc.Statement;


@Component("bingMapsDao")
public class BingMapsImpl implements BingMapsDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertBingMapsData(BingMapsData bingData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into bing_maps_data(query,entry_point,title,entity_id,filter_url_param,url,url_id,user_id,ipaddress,address,remark,property_type,sub_title,top_fact_lst,snippet_text,fact_lst,links,price_sold,category) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, bingData.getQuery());
			ps.setString(2, bingData.getEntry_point());
			ps.setString(3, bingData.getTitle());
			ps.setString(4, bingData.getEntity_id());
			ps.setString(5, bingData.getFilter_url_param());
			ps.setString(6, bingData.getUrl());
			ps.setString(7, bingData.getUrl_id());
			ps.setString(8, bingData.getUser_id());
			ps.setString(9, bingData.getIpaddress());
			ps.setString(10, bingData.getAddress());
			ps.setString(11, bingData.getRemark());
			ps.setString(12, bingData.getProperty_type());
			ps.setString(13, bingData.getSub_title());
			ps.setString(14, bingData.getTop_fact_lst());
			ps.setString(15, bingData.getSnippet_text());
			ps.setString(16, bingData.getFact_lst());
			ps.setString(17, bingData.getLinks());
			ps.setString(18, bingData.getPrice_sold());
			ps.setString(19, bingData.getCategory());
			
			
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
		System.out.println("Status BingMapsData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterBingMapsURL> getBingMapsUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterBingMapsURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_bing_maps_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_bing_maps_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_bing_maps_url m where  m.status='Done' and  m.user_id = ? and m.master_bing_maps_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_bing_maps_url m where  m.status='Done' and  m.user_id = ? and m.master_bing_maps_url_id not in (select url_id from bing_maps_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_bing_maps_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterBingMapsURL masterURL = new MasterBingMapsURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_bing_maps_url_id")));
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
	public List<BingMapsData> getBingMapsData(int userId) {
		// TODO Auto-generated method stub
			List<BingMapsData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from bing_maps_data where user_id = ? order by bing_maps_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				BingMapsData bingData = new BingMapsData();
				bingData.setBingMapsId(rs.getInt("bing_maps_data_id"));
				bingData.setQuery(rs.getString("query"));;
				bingData.setEntry_point(rs.getString("entry_point"));;
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

	
 

}


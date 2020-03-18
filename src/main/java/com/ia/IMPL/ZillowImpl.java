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

import com.ia.Dao.ZillowDao;
import com.ia.modal.MasterZillowURL;
import com.ia.modal.ZillowData;
import com.ia.modal.ZillowFeatureData;
import com.mysql.jdbc.Statement;


  
@Component("zillowDao")
public class ZillowImpl implements ZillowDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertZillowData(ZillowData zillowData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into zillow_data(ds_price,ds_bed_bath_living_area,ds_address,ds_status,ds_zestimate_value,ds_morgage,ds_overview_states,ds_overview_text,listing_agents,fact_lst,feature_lst,zestimate_value,estimated_sales_range,zestimate_history,zestimate_model_text,estimated_monthly_cost,monthly_cost_lst,rent_zestimate,nearby_school_lst,nearby_school_description,neighborhood_prediction_text,zestimate_median_score,neighborhood_travel_score,listing_provided_by,nearby_neighborhood_txt,url_id,user_id,url,ipaddress) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
			ps.setString(1, zillowData.getDs_price());
			ps.setString(2, zillowData.getDs_bed_bath_living_area());
			ps.setString(3, zillowData.getDs_address());
			ps.setString(4, zillowData.getDs_status());
			ps.setString(5, zillowData.getDs_zestimate_value());
			ps.setString(6, zillowData.getDs_morgage());
			ps.setString(7, zillowData.getDs_overview_states());
			ps.setString(8, zillowData.getDs_overview_text());
			ps.setString(9, zillowData.getListing_agents());
			ps.setString(10, zillowData.getFact_lst());
			ps.setString(11, zillowData.getFeature_lst());
			ps.setString(12, zillowData.getZestimate_value());
			ps.setString(13, zillowData.getEstimated_sales_range());
			ps.setString(14, zillowData.getZestimate_history());
			ps.setString(15, zillowData.getZestimate_model_text());
			ps.setString(16, zillowData.getEstimated_monthly_cost());
			ps.setString(17, zillowData.getMonthly_cost_lst());
			ps.setString(18, zillowData.getRent_zestimate());
			ps.setString(19, zillowData.getNearby_school_lst());
			ps.setString(20, zillowData.getNearby_school_description());
			ps.setString(21, zillowData.getNeighborhood_prediction_text());
			ps.setString(22, zillowData.getZestimate_median_score());
			ps.setString(23, zillowData.getNeighborhood_travel_score());
			ps.setString(24, zillowData.getListing_provided_by());
			ps.setString(25, zillowData.getNearby_neighborhood_txt());
			ps.setString(26, zillowData.getUrl_id());
			ps.setString(27, zillowData.getUser_id());
			ps.setString(28, zillowData.getUrl());
			ps.setString(29, zillowData.getIpaddress());
			
			
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
		System.out.println("Status insertZillowData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterZillowURL> getZillowUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterZillowURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_zillow_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_zillow_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_zillow_url m where  m.status='Done' and  m.user_id = ? and m.master_zillow_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_zillow_url m where  m.status='Done' and  m.user_id = ? and m.master_zillow_url_id not in (select url_id from bing_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_zillow_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterZillowURL masterURL = new MasterZillowURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_zillow_url_id")));
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
	public List<ZillowData> getZillowData(int userId,String action) {
		// TODO Auto-generated method stub
			List<ZillowData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from zillow_data where user_id = ? order by zillow_id desc limit 0,10";
			
			if(action.equalsIgnoreCase("all")) {
				//sql = "select * from zillow_data where user_id = ? and  zillow_id > 15000 limit 15000	";
				sql = "select * from zillow_data where  zillow_id = ?";
				
			}else if(action.equalsIgnoreCase("display")){
				sql = "select * from zillow_data where user_id = ? order by zillow_id desc limit 0,10";
			}
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ZillowData bingData = new ZillowData();
				bingData.setZillowId(rs.getInt("zillow_id"));
				bingData.setDs_address(rs.getString("ds_address"));;
				//bingData.setSub_section_container0(rs.getString("sub_section_container0"));;
				bingData.setDs_morgage(rs.getString("ds_morgage"));
				bingData.setUrl(rs.getString("url"));
				bingData.setUrl_id(rs.getString("url_id"));
				bingDatas.add(bingData);
				System.out.println(rs.getInt("zillow_id"));
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bingDatas;
	}

	@Override
	public int insertZillowFeatureData(ZillowFeatureData zillowData) {
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into zillow_feature_list(key_1,key_2,key_value,zillow_id) value(?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
			ps.setString(1, zillowData.getKey_1());
			ps.setString(2, zillowData.getKey_2());
			ps.setString(3, zillowData.getKey_value());
			ps.setString(4, zillowData.getZillow_id());
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
		System.out.println("Status insertZillowData:::"+status);
		return status;
	}

	 

}


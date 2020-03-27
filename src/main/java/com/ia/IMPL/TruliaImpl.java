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

import com.ia.Dao.TruliaDao;
import com.ia.modal.MasterTruliaURL;
import com.ia.modal.TruliaData;
import com.mysql.jdbc.Statement;


@Component("truliaDao")
public class TruliaImpl implements TruliaDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertTruliaData(TruliaData truliaData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into trulia_data(agent_phone,breadcrumb_text,broker_link,broker_name,broker_phone,city_state,description,feature_lst,headline,market_price,mortgage_estimation,neighborhood_link,price_trends_lst,property_tax_lst,provider_info_text,todo,total_bath,total_beds,total_sqft,url,url_id,user_id,what_local_say_lst,ipaddress,home_details,public_records,units_count) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, truliaData.getAgent_phone());
			ps.setString(2, truliaData.getBreadcrumb_text());
			ps.setString(3, truliaData.getBroker_link());
			ps.setString(4, truliaData.getBroker_name());
			ps.setString(5, truliaData.getBroker_phone());
			ps.setString(6, truliaData.getCity_state());
			ps.setString(7, truliaData.getDescription());
			ps.setString(8, truliaData.getFeature_lst());
			ps.setString(9, truliaData.getHeadline());
			ps.setString(10, truliaData.getMarket_price());
			ps.setString(11, truliaData.getMortgage_estimation());
			ps.setString(12, truliaData.getNeighborhood_link());
			ps.setString(13, truliaData.getPrice_trends_lst());
			ps.setString(14, truliaData.getProperty_tax_lst());
			ps.setString(15, truliaData.getProvider_info_text());
			ps.setString(16, truliaData.getTodo());
			ps.setString(17, truliaData.getTotal_bath());
			ps.setString(18, truliaData.getTotal_beds());
			ps.setString(19, truliaData.getTotal_sqft());
			ps.setString(20, truliaData.getUrl());
			ps.setString(21, truliaData.getUrl_id());
			ps.setString(22, truliaData.getUser_id());
			ps.setString(23, truliaData.getWhat_local_say_lst());
			ps.setString(24, truliaData.getIpaddress());
			ps.setString(25, truliaData.getHome_details());
			ps.setString(26, truliaData.getPublic_records());
			ps.setString(27, truliaData.getUnits_count());
			
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
		System.out.println("Status insertTruliaData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterTruliaURL> getTruliaUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterTruliaURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_trulia_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_trulia_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				sql = "select m.* from master_trulia_url m where  m.status='Done' and  m.user_id = ? and m.master_trulia_url_id not in (select url_id from trulia_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_trulia_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterTruliaURL masterURL = new MasterTruliaURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_trulia_url_id")));
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
	public List<TruliaData> getTruliaData(int userId) {
		// TODO Auto-generated method stub
			List<TruliaData> truliaDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from trulia_data where user_id = ? order by trulia_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				TruliaData truliaData = new TruliaData();
				truliaData.setTruliaId(rs.getInt("trulia_data_id"));
				truliaData.setBreadcrumb_text(rs.getString("breadcrumb_text"));;
				truliaData.setBroker_name(rs.getString("broker_name"));;
				truliaData.setUrl(rs.getString("url"));
				truliaData.setUrl_id(rs.getString("url_id"));
				truliaDatas.add(truliaData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return truliaDatas;
	}

 

}


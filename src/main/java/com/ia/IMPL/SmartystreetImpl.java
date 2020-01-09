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

import com.ia.Dao.SmartystreeDao;
import com.ia.modal.MasterSmartystreetURL;
import com.ia.modal.SmartystreetData;
import com.mysql.jdbc.Statement;


@Component("smartystreeDao")
public class SmartystreetImpl implements SmartystreeDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertSmartystreetData(SmartystreetData smartystreetData) {
		// TODO Auto-generated method stub
		System.out.println("Data::"+smartystreetData.getUrl_id() +"--"+smartystreetData.getUser_id());
		
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into smartystreet_data(entered_address,found_addresses,delivery_line,city_state_zip,found_address_status,building_default,carrier_route,congressional_district,latitude,coordinate_precision,country_name,country_fips,elot_sequence,elot_sort,observes_dst,rdi,record_type,time_zone,zip_type,active,cmra,dpv_match_code,ews_match,lacslink_code,lacslink_indicator,suitelink_match,vacant,dpv_footnotes,general_footnotes,urbanization,primary_number,street_predirection,street_name,street_postdirection,street_suffix,secondary_designator,secondary_number,extra_secondary_designator,extra_secondary_number,pmb_designator,pmb_number,city,default_city_name,state,zip_code,plus_4_code,delivery_point,check_digit,root_url,url_id,user_id,ipaddress,longitude,remarks) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, smartystreetData.getEntered_address());ps.setString(2, smartystreetData.getFound_addresses());
			ps.setString(3, smartystreetData.getDelivery_line());ps.setString(4, smartystreetData.getCity_state_zip());
			ps.setString(5, smartystreetData.getFound_address_status());ps.setString(6, smartystreetData.getBuilding_default());
			ps.setString(7, smartystreetData.getCarrier_route());ps.setString(8, smartystreetData.getCongressional_district());
			ps.setString(9, smartystreetData.getLatitude());ps.setString(10, smartystreetData.getCoordinate_precision());
			ps.setString(11, smartystreetData.getCountry_name());ps.setString(12, smartystreetData.getCountry_fips());
			ps.setString(13, smartystreetData.getElot_sequence());ps.setString(14, smartystreetData.getElot_sort());
			ps.setString(15, smartystreetData.getObserves_dst());ps.setString(16, smartystreetData.getRdi());
			ps.setString(17, smartystreetData.getRecord_type());ps.setString(18, smartystreetData.getTime_zone());
			ps.setString(19, smartystreetData.getZip_type());ps.setString(20, smartystreetData.getActive());
			ps.setString(21, smartystreetData.getCmra());ps.setString(22, smartystreetData.getDpv_match_code());
			ps.setString(23, smartystreetData.getEws_match());ps.setString(24, smartystreetData.getLacslink_code());
			ps.setString(25, smartystreetData.getLacslink_indicator());ps.setString(26, smartystreetData.getSuitelink_match());
			ps.setString(27, smartystreetData.getVacant());ps.setString(28, smartystreetData.getDpv_footnotes());
			ps.setString(29, smartystreetData.getGeneral_footnotes());ps.setString(30, smartystreetData.getUrbanization());
			ps.setString(31, smartystreetData.getPrimary_number());ps.setString(32, smartystreetData.getStreet_predirection());
			ps.setString(33, smartystreetData.getStreet_name());
			ps.setString(34, smartystreetData.getStreet_postdirection());
			ps.setString(35, smartystreetData.getStreet_suffix());
			ps.setString(36, smartystreetData.getSecondary_designator());
			ps.setString(37, smartystreetData.getSecondary_number());
			ps.setString(38, smartystreetData.getExtra_secondary_designator());
			ps.setString(39, smartystreetData.getExtra_secondary_number());
			ps.setString(40, smartystreetData.getPmb_designator());
			ps.setString(41, smartystreetData.getPmb_number());
			ps.setString(42, smartystreetData.getCity());
			ps.setString(43, smartystreetData.getDefault_city_name());
			ps.setString(44, smartystreetData.getState());
			ps.setString(45, smartystreetData.getZip_code());
			ps.setString(46, smartystreetData.getPlus_4_code());
			ps.setString(47, smartystreetData.getDelivery_point());
			ps.setString(48, smartystreetData.getCheck_digit());
			ps.setString(49, smartystreetData.getRoot_url());
			ps.setString(50, smartystreetData.getUrl_id());
			ps.setString(51, smartystreetData.getUser_id());
			ps.setString(52, smartystreetData.getIpaddress());
			ps.setString(53, smartystreetData.getLongitude());
			ps.setString(54, smartystreetData.getRemarks());
			
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
		System.out.println("Status smartystreetDataData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterSmartystreetURL> getSmartystreetUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterSmartystreetURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_smartystreet_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_smartystreet_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_spokeo_url m where  m.status='Done' and  m.user_id = ? and m.master_spokeo_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_smartystreet_url m where  m.status='Done' and  m.user_id = ? and m.master_smartystreet_url_id not in (select url_id from smartystreet_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_smartystreet_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterSmartystreetURL masterURL = new MasterSmartystreetURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_smartystreet_url_id")));
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
	public List<SmartystreetData> getSmartystreetData(int userId) {
			List<SmartystreetData> spokeoDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from smartystreet_data where user_id = ? order by smartystreet_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SmartystreetData spokeoData = new SmartystreetData();
				spokeoData.setSmartystreetId(rs.getInt("smartystreet_data_id"));
				spokeoData.setEntered_address(rs.getString("entered_address"));
				spokeoData.setFound_addresses(rs.getString("found_addresses"));
				spokeoData.setRoot_url(rs.getString("root_url"));
				spokeoData.setUrl_id(rs.getString("url_id"));
				spokeoData.setUser_id(rs.getString("user_id"));
				spokeoDatas.add(spokeoData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return spokeoDatas;
	}

	


	 

}


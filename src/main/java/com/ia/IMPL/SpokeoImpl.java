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

import com.ia.Dao.SpokeoDao;
import com.ia.modal.MasterSpokeoURL;
import com.ia.modal.SpokeoData;
import com.mysql.jdbc.Statement;


@Component("spokeoDao")
public class SpokeoImpl implements SpokeoDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertSpokeoData(SpokeoData spokeoData) {
		// TODO Auto-generated method stub
		System.out.println("Data::"+spokeoData.getUrl_id() +"--"+spokeoData.getUser_id());
		
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into spokeo_data(address,details_lst,building_value,last_sold_price,last_sold_period,year_bulit,living_area,lot_size,bathrooms,building_type,country,units,home_value,bedrooms,home_type,heating,cooling,parking,stories,structure,fireplace,root_url,url_id,user_id,ipaddress,remarks) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, spokeoData.getAddress());
			ps.setString(2, spokeoData.getDetails_lst());
			ps.setString(3, spokeoData.getBuilding_value());
			ps.setString(4, spokeoData.getLast_sold_price());
			ps.setString(5, spokeoData.getLast_sold_period());
			ps.setString(6, spokeoData.getYear_bulit());
			ps.setString(7, spokeoData.getLiving_area());
			ps.setString(8, spokeoData.getLot_size());
			ps.setString(9, spokeoData.getBathrooms());
			ps.setString(10, spokeoData.getBuilding_type());
			ps.setString(11, spokeoData.getCountry());
			ps.setString(12, spokeoData.getUnits());
			ps.setString(13, spokeoData.getHome_value());
			ps.setString(14, spokeoData.getBedrooms());
			ps.setString(15, spokeoData.getHome_type());
			ps.setString(16, spokeoData.getHeating());
			ps.setString(17, spokeoData.getCooling());
			ps.setString(18, spokeoData.getParking());
			ps.setString(19, spokeoData.getStories());
			ps.setString(20, spokeoData.getStructure());
			ps.setString(21, spokeoData.getFireplace());
			ps.setString(22, spokeoData.getRoot_url());
			ps.setString(23, spokeoData.getUrl_id());
			ps.setString(24, spokeoData.getUser_id());
			ps.setString(25, spokeoData.getIpaddress());
			ps.setString(26, spokeoData.getRemarks());
			 
			
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
		System.out.println("Status spokeoDataData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterSpokeoURL> getSpokeoUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterSpokeoURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_spokeo_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_spokeo_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_spokeo_url m where  m.status='Done' and  m.user_id = ? and m.master_spokeo_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_spokeo_url m where  m.status='Done' and  m.user_id = ? and m.master_spokeo_id not in (select url_id from spokeo_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_spokeo_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterSpokeoURL masterURL = new MasterSpokeoURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_spokeo_id")));
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
	public List<SpokeoData> getSpokeoData(int userId) {
		// TODO Auto-generated method stub
			List<SpokeoData> spokeoDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from spokeo_data where user_id = ? order by spokeo_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				SpokeoData spokeoData = new SpokeoData();
				spokeoData.setSpokeoId(rs.getInt("spokeo_data_id"));
				spokeoData.setAddress(rs.getString("address"));;
				spokeoData.setLast_sold_period(rs.getString("last_sold_period"));;
				spokeoData.setRoot_url(rs.getString("root_url"));
				spokeoData.setUrl_id(rs.getString("url_id"));
				spokeoData.setUser_id(rs.getString("user_id"));
				spokeoDatas.add(spokeoData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return spokeoDatas;
	}

	@Override
	public MasterSpokeoURL getSpokeoUrl(String url) {
		List<MasterSpokeoURL> spokeoDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from master_spokeo_url where url = '"+url+"' order by master_spokeo_id desc limit 0,1";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			/*ps.setString(1,url);*/
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				MasterSpokeoURL spokeoData = new MasterSpokeoURL();
				spokeoData.setUrlId(rs.getInt("master_spokeo_id"));
				/*spokeoData.setAddress(rs.getString("address"));;
				spokeoData.setLast_sold_period(rs.getString("last_sold_period"));;
				spokeoData.setRoot_url(rs.getString("root_url"));*/
				spokeoData.setUserId(rs.getInt("user_id"));
				spokeoDatas.add(spokeoData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		if(spokeoDatas.size() > 0)
			return spokeoDatas.get(0);
		else 
			return null;
	}



	 

}


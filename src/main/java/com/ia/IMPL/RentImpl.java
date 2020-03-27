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

import com.ia.Dao.RentDao;
import com.ia.modal.MasterRentURL;
import com.ia.modal.RentData;
import com.mysql.jdbc.Statement;


@Component("rentDao")
public class RentImpl implements RentDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertRentData(RentData rentData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into rent_data(address,amenities_lst,bath_text,bed_text,building_type,description,description_bottom_txt,heighlights_lst,neighborhood_name,pet_policy_lst,phone_number,property_managed_by,sqft_text,title,todo,total_units,units_available,url,ipaddress,user_id,url_id) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, rentData.getAddress());
			ps.setString(2, rentData.getAmenities_lst());
			ps.setString(3, rentData.getBath_text());
			ps.setString(4, rentData.getBed_text());
			ps.setString(5, rentData.getBuilding_type());
			ps.setString(6, rentData.getDescription());
			ps.setString(7, rentData.getDescription_bottom_txt());
			ps.setString(8, rentData.getHeighlights_lst());
			ps.setString(9, rentData.getNeighborhood_name());
			ps.setString(10, rentData.getPet_policy_lst());
			ps.setString(11, rentData.getPhone_number());
			ps.setString(12, rentData.getProperty_managed_by());
			ps.setString(13, rentData.getSqft_text());
			ps.setString(14, rentData.getTitle());
			ps.setString(15, rentData.getTodo());
			ps.setString(16, rentData.getTotal_units());
			ps.setString(17, rentData.getUnits_available());
			ps.setString(18, rentData.getUrl());
			ps.setString(19, rentData.getIpaddress());
			ps.setString(20, rentData.getUser_id());
			ps.setString(21, rentData.getUrl_id());
			
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
		System.out.println("Status zumperDataData:::"+status);
		return status;
	}
	
	@Override
	public List<com.ia.modal.MasterRentURL> getRentUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterRentURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_rent_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_rent_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				sql = "select m.* from master_rent_url m where  m.status='Done' and  m.user_id = ? and m.master_rent_url_id not in (select url_id from zumper_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_rent_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterRentURL masterURL = new MasterRentURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_rent_url_id")));
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
	public List<RentData> getRentData(int userId) {
		// TODO Auto-generated method stub
			List<RentData> rentDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from rent_data where user_id = ? order by rent_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				RentData rentData = new RentData();
				rentData.setRentId(rs.getInt("rent_data_id"));
				rentData.setAddress(rs.getString("address"));;
				rentData.setBed_text(rs.getString("bed_text"));;
				rentData.setUrl(rs.getString("url"));
				rentData.setUrl_id(rs.getString("url_id"));
				rentDatas.add(rentData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return rentDatas;
	}

	@Override
	public int insertRentAmenitiesData(String header,int rentId) {
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into rent_amenities_data(header,rent_id) value(?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, header);
			ps.setInt(2, rentId);
			status = ps.executeUpdate();
			con.commit();		
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return status;
	}

}


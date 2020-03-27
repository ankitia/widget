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

import com.ia.Dao.ZoomInfoDao;
import com.ia.modal.MasterZoomInfoURL;
import com.ia.modal.ZoomInfoData;
import com.mysql.jdbc.Statement;


@Component("zoomInfoDao")
public class ZoomInfoImpl implements ZoomInfoDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertZoomData(ZoomInfoData zoominfoData) {
		// TODO Auto-generated method stub
				int status = 0; 
				try (Connection con = (Connection) dataSource.getConnection()){
					String sql = "insert into zoom_info_data(url_id,user_id,remarks,company_name,headquarters_address,phone,website,employees,revenue,description,sic_code,naics_code,industries,facebook_link,twitter_link,linkedin_link,url,ipaddress) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, zoominfoData.getUrl_id());
					ps.setString(2, zoominfoData.getUser_id());
					ps.setString(3, zoominfoData.getRemarks());
					ps.setString(4, zoominfoData.getCompany_name());
					ps.setString(5, zoominfoData.getHeadquarters_address());
					ps.setString(6, zoominfoData.getPhone());
					ps.setString(7, zoominfoData.getWebsite());
					ps.setString(8, zoominfoData.getEmployees());
					ps.setString(9, zoominfoData.getRevenue());
					ps.setString(10, zoominfoData.getDescription());
					ps.setString(11, zoominfoData.getSic_code());
					ps.setString(12, zoominfoData.getNaics_code());
					ps.setString(13, zoominfoData.getIndustries());
					ps.setString(14, zoominfoData.getFacebook_link());
					ps.setString(15, zoominfoData.getTwitter_link());
					ps.setString(16, zoominfoData.getLinkedin_link());
					ps.setString(17, zoominfoData.getUrl());
					ps.setString(18, zoominfoData.getIpaddress());
					
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
				System.out.println("Status ZoomInfoData:::"+status);
				return status;
	}
	
	@Override
	public List<MasterZoomInfoURL> getZoomUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterZoomInfoURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_zoominfo_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_zoominfo_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_zoominfo_url m where  m.status='Done' and  m.user_id = ? and m.master_zoominfo_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_zoominfo_url m where  m.status='Done' and  m.user_id = ? and m.master_zoominfo_url_id not in (select url_id from zoom_info_data  where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_zoominfo_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterZoomInfoURL masterURL = new MasterZoomInfoURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_zoominfo_url_id")));
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
	public List<ZoomInfoData> getZoomData(int userId) {
		// TODO Auto-generated method stub
			List<ZoomInfoData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from zoom_info_data where user_id = ? order by zoom_info_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ZoomInfoData bingData = new ZoomInfoData();
				bingData.setZoomId(rs.getInt("zoom_info_data_id"));
				bingData.setCompany_name(rs.getString("company_name"));;
				bingData.setPhone(rs.getString("phone"));;
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


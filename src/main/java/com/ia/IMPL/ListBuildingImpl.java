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

import com.ia.Dao.ListBuildingDao;
import com.ia.modal.ListBuilding;
import com.ia.modal.ListBuildingView;
import com.ia.modal.MasterListBuildingURL;
import com.mysql.jdbc.Statement;


@Component("listBuildingDao")
public class ListBuildingImpl implements ListBuildingDao {
	
	@Autowired
	DataSource dataSource;
		
	Connection con;

	@Override
	public List<MasterListBuildingURL> getListBuildingUrlList(int userId, String action) {
		List<MasterListBuildingURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_list_building_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_list_building_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				sql = "select m.* from master_list_building_url m where  m.status='Done' and  m.user_id = ? and m.master_list_url_id not in (select url_id from list_building_details where user_id=?) ";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_list_building_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}else if(action.equalsIgnoreCase("missedCheck")) {
				sql = "select master_list_url_id,lb.url,lb.user_id, IF(TRIM(REPLACE(REPLACE(lb.total_result_no,'Total results',''),'\\n', ''))=count(url_id), \"YES\", IF(TRIM(REPLACE(REPLACE(lb.total_result_no,'Total results',''),'\\n', ''))<=count(url_id),\"YES\",\"NO\")) as result,count(url_id),TRIM(REPLACE(REPLACE(lb.total_result_no,'Total results',''),'\\n', '')) as total,url_id from list_building_details lb, master_list_building_url mlbu where lb.user_id=? and mlbu.master_list_url_id = lb.url_id group by url_id HAVING  result='NO'";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterListBuildingURL masterListBuildingURL = new MasterListBuildingURL();
				masterListBuildingURL.setListBuildUrlId(Long.parseLong(rs.getString("master_list_url_id")));
				masterListBuildingURL.setUrl((rs.getString("url")));
				masterListBuildingURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				data.add(masterListBuildingURL);
			}
			con.close();			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return data;
	}

	@Override
	public List<ListBuildingView> getListBuildingData(int userId,String action) {
		List<ListBuildingView> listBuildings = new ArrayList<>();
		try (Connection con = (Connection) dataSource.getConnection()){
			
			//String sql = "select * from list_building_details  group by list_id limit 0,10";
			String sql = "select company_name,name,master_list_url_id,mlbu.url,lb.user_id, IF(TRIM(REPLACE(REPLACE(lb.total_result_no,'Total results',''),'\\n', ''))=count(url_id), \"YES\", IF(TRIM(REPLACE(REPLACE(lb.total_result_no,'Total results',''),'\\n', ''))<=count(url_id),\"YES\",\"NO\")) as result,count(url_id) scrap_count,TRIM(REPLACE(REPLACE(lb.total_result_no,'Total results',''),'\\n', '')) as total,url_id from list_building_details lb, master_list_building_url mlbu where lb.user_id=? and mlbu.master_list_url_id = lb.url_id group by url_id HAVING  result='"+action+"'";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ListBuildingView details = new ListBuildingView();
				details.setName(rs.getString("name"));
				details.setCompany_name(rs.getString("company_name"));;
				details.setUrl(rs.getString("url"));;
				details.setTotalRecord(rs.getString("total"));
				details.setScrapCount(rs.getString("scrap_count"));
				details.setListId(rs.getInt("master_list_url_id"));
				details.setUserId(userId+"");
				listBuildings.add(details);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return listBuildings;
	}

	@Override
	public int getCurrentDateCount(int userId) {
		// TODO Auto-generated method stub
		try (Connection con = (Connection) dataSource.getConnection()){
			String  sql = "select count(user_id) as count from list_building_details where user_id = ? and CURDATE()=date_format(created_date,'%Y-%m-%d')";	
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("count");				
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
		return 0;
	}

	@Override
	public int insertListBuild(ListBuilding listBuilding) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			
			String sql = "insert into list_building_details(name,new_link,company_link,company_name,company_tenure,contact_location,contact_designation,url,url_id,user_id,ipaddress,total_result_no,total_changed_job_no,page_number,record_no) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, listBuilding.getName());
			ps.setString(2, listBuilding.getNew_link());
			ps.setString(3, listBuilding.getCompany_link());
			ps.setString(4, listBuilding.getCompany_name());
			ps.setString(5, listBuilding.getCompany_tenure());
			ps.setString(6, listBuilding.getContact_location());
			ps.setString(7, listBuilding.getContact_designation());
			ps.setString(8, listBuilding.getUrl());
			ps.setString(9, listBuilding.getUrl_id());
			ps.setString(10, listBuilding.getUser_id());
			ps.setString(11, listBuilding.getIpaddress());
			ps.setString(12, listBuilding.getTotal_result_no());
			ps.setString(13, listBuilding.getTotal_changed_job_no());
			ps.setString(14, listBuilding.getPage_number());
			ps.setString(15, listBuilding.getRecord_no());
			 
			status = ps.executeUpdate();
			con.commit();		
			 try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	status = generatedKeys.getInt(1);
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
		        }catch (Exception e) {
					// TODO: handle exception
		        	e.printStackTrace();
				}
			 con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("Status :::"+status);
		return status;
	}

	@Override
	public String getListBuildMissedCount(int urlId) {
		int totalCount = 0;
		String total = "";
		try (Connection con = (Connection) dataSource.getConnection()){
			String  sql = "select count(list_id) count,total_result_no from list_building_details where  url_id =? group by page_number,record_no ";	
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,urlId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				totalCount++;
				total = rs.getString("total_result_no");
			}
			
			 
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
		return ""+totalCount+" scraped out of "+total;
	}

 
	
	

}

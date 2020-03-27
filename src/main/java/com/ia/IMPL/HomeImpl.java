
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
import org.springframework.transaction.annotation.Transactional;

import com.ia.Dao.HomeDao;
import com.ia.modal.Category;
import com.ia.modal.CompanyAffiliate;
import com.ia.modal.CompanyDetails;
import com.ia.modal.CompanyLocation;
import com.ia.modal.ListContacts;
import com.ia.modal.MasterURL;
import com.ia.modal.MasterURLProfile;
import com.ia.modal.Scrap;
import com.ia.modal.User;
import com.ia.modal.UserDetail;
import com.mysql.jdbc.Statement;

@Component("homeDao")
public class HomeImpl implements HomeDao {

	@Autowired
	DataSource dataSource;
		
	Connection con;
	
	@Override
	public List<String> getData() {
		// TODO Auto-generated method stub
			List<String> data = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from login";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("username"));
				data.add(rs.getString("username"));
				
			}
			 con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
			
		return data;
	}

	@Override
	public User checkValidUser(String userName, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from user where useremail = ? and binary password = ?";
			PreparedStatement  ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setUserId(rs.getInt("user_id"));
				user.setUserRole(rs.getString("user_role"));
				user.setApprovedLink(rs.getString("approved_link"));
				user.setApprovedLink2(rs.getString("approved_link_scrap2"));
				user.setApprovedLink3(rs.getString("approved_link_scrap3"));
				user.setCompanyLink(rs.getString("company_link"));
			} 
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean insert(User user) {
		// TODO Auto-generated method stub
		int status = 0;
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "insert into user(fname,lname,useremail,password) value(?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, user.getFname());
			ps.setString(2, user.getLname());
			ps.setString(3, user.getUserEmail());
			ps.setString(4, user.getPassword());
			status = ps.executeUpdate();
			con.close();	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("Status :::"+status);
		if(status > 0)
			return true;
		else		
			return false;
	}

	@Override
	public boolean categoryInsert(Category category) {
		// TODO Auto-generated method stub
		
		int status = 0;
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "insert into category(name,cat_path,cat_desc) values(?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,category.getCatName()); 
			ps.setString(2,category.getCatPath());
			ps.setString(3,category.getCatDesc());
			status = ps.executeUpdate();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		;
		System.out.println("Status :::"+status);
		if(status > 0)
			return true;
		else		
			return false;
		
	}
	
	@Override
	public List<Category> getCategory() {
		// TODO Auto-generated method stub
			List<Category> data = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from category";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Category category = new Category();
				category.setCategoryId(Integer.parseInt(rs.getString("category_id")));
				category.setCatName(rs.getString("name"));
				category.setCatDesc(rs.getString("cat_desc"));
				category.setCatPath(rs.getString("cat_path"));
				data.add(category);
				
			}
			con.close();
			System.out.println("Size:: "+rs.last());
			System.out.println("Size:: "+rs.getRow());
			 
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return data;
	}

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from user";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setUserEmail(rs.getString("useremail"));
				user.setPassword(rs.getString("password"));
				user.setMobileNumber(rs.getString("mobile_number"));
				user.setUserId(rs.getInt("user_id"));
				users.add(user);
			} 
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error ::::");
			
			e.printStackTrace();
		}
		return users;
	}
	
	@Transactional
	@Override
	public boolean insertScrap(Scrap scrap) {
		// TODO Auto-generated method stub
		int status = 0;
		try (Connection con = (Connection) dataSource.getConnection()){
			
			String sql = "insert into scrap(name,current_org,current_position,location,url,ipaddress,user_id,contact_url,url_id,past_org,past_position,remarks) value(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, scrap.getName());
			ps.setString(2, scrap.getCurrent_org());
			ps.setString(3, scrap.getCurrent_position());
			ps.setString(4, scrap.getLocation());
			ps.setString(5, scrap.getUrl());
			ps.setString(6, scrap.getIpaddress());
			ps.setString(7, scrap.getUser_id());
			ps.setString(8, scrap.getContact_url());
			ps.setLong(9, scrap.getUrl_id());
			ps.setString(10, scrap.getPast_org());
			ps.setString(11, scrap.getPast_position());
			ps.setString(12, scrap.getRemarks());
			status = ps.executeUpdate();
			con.commit();		
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("Status insertScrap:::"+status);
		if(status > 0)
			return true;
		else		
			return false;
	}

	@Override
	public boolean insertlist() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean insertListContacts(ListContacts listContacts) {
		int status = 0;
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "insert into list_contacts(full_name,head_title,head_location,company_name,university,university_url,current_title,current_company,current_company_link,current_duration_start,current_duration_end,current_location,past_title,past_company,past_company_link,past_duration_start,past_duration_end,past_location,url,user_id,url_id,ipaddress,remarks) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,listContacts.getFull_name());
			ps.setString(2,listContacts.getHead_title());
			ps.setString(3,listContacts.getHead_location());
			ps.setString(4,listContacts.getCompany_name());
			ps.setString(5,listContacts.getUniversity());
			ps.setString(6,listContacts.getUniversity_url());
			ps.setString(7,listContacts.getCurrent_title());
			ps.setString(8,listContacts.getCurrent_company());
			ps.setString(9,listContacts.getCurrent_company_link());
			ps.setString(10,listContacts.getCurrent_duration_start());
			ps.setString(11,listContacts.getCurrent_duration_end());
			ps.setString(12,listContacts.getCurrent_location());
			ps.setString(13,listContacts.getPast_title());
			ps.setString(14,listContacts.getPast_company());
			ps.setString(15,listContacts.getPast_company_link());
			ps.setString(16,listContacts.getPast_duration_start());
			ps.setString(17,listContacts.getPast_duration_end());
			ps.setString(18,listContacts.getPast_location());
			ps.setString(19,listContacts.getUrl());
			ps.setString(20,listContacts.getUser_id());
			ps.setString(21,listContacts.getUrl_id());
			ps.setString(22,listContacts.getIpaddress());
			ps.setString(23,listContacts.getRemarks());
			status = ps.executeUpdate();
			con.commit();
			con.close();
					
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Status :::"+status);
		if(status > 0)
			return true;
		else		
			return false;
	}
	
	@Override
	public int getTotalCount(int userId,String action) {
		// TODO Auto-generated method stub
			int totalRecord = 0;
		try(Connection con = (Connection) dataSource.getConnection()) {
			
			String sql = "";
			if(action.equalsIgnoreCase("scrap")) {
				sql = "select count(distinct(url_id)) as total from scrap where user_id = ?";	
			}else if(action.equalsIgnoreCase("listContacts")) {
				sql = "select count(distinct(url_id)) as total from list_contacts where user_id = ?";
			}else if(action.equals("scrap_log")) {
				sql = "select count(distinct(url_id)) as total from scrap1 where user_id = ?";	
			}else if(action.equals("scrap_log2")) {
				sql = "select count(distinct(url_id)) as total from scrap2 where user_id = ?";	
			}else if(action.equals("scrap_log3")) {
				sql = "select count(distinct(url_id)) as total from scrap3 where user_id = ?";	
			}else if(action.equals("companyData")) {
				sql = "select count(distinct(url_id)) as total from company_detail where user_id = ?";	
			}else if(action.equals("company_log")) {
				//sql = "select count(distinct(url_id)) as total from company_detail_log where user_id = ?";	
				sql = "select count(distinct(cdl.url_id)) as total_fi, (count(distinct(cdl.url_id)) - company_link_temp)  as total from company_detail_log cdl,user u   where cdl.user_id=u.user_id  and cdl.user_id = ?";
			}else if(action.equalsIgnoreCase("listBuild")) {
				sql = "select count(distinct(url_id)) as total from list_building_details where user_id = ?";
			}else if(action.equalsIgnoreCase("googleData")) {
				sql = "select count(distinct(url_id)) as total from property_data where user_id = ?";
			}else if(action.equalsIgnoreCase("bingData")) {
				sql = "select count(distinct(url_id)) as total from bing_data where user_id = ?";
			}else if(action.equalsIgnoreCase("zillowData")) {
				sql = "select count(distinct(url_id)) as total from zillow_data where user_id = ?";
			}else if(action.equalsIgnoreCase("yelpData")) {
				sql = "select count(distinct(url_id)) as total from yelp_data where user_id = ?";
			}else if(action.equalsIgnoreCase("mapsData")) {
				sql = "select count(distinct(url_id)) as total from maps_data where user_id = ?";
			}else if(action.equalsIgnoreCase("profileEmailData")) {
				sql = "select count(distinct(url_id)) as total from profile_email_data where user_id = ?";
			}else if(action.equalsIgnoreCase("bingMapsData")) {
				sql = "select count(distinct(url_id)) as total from bing_maps_data where user_id = ?";
			}else if(action.equalsIgnoreCase("bingMapsDetail")) {
				sql = "select count(distinct(url_id)) as total from bing_maps_detail where user_id = ?";
			}else if(action.equalsIgnoreCase("googlePlaceData")) {
				sql = "select count(distinct(url_id)) as total from google_place where user_id = ?";
			}else if(action.equalsIgnoreCase("spokeoData")) {
				sql = "select count(distinct(url_id)) as total from spokeo_data where user_id = ?";
			}else if(action.equalsIgnoreCase("Smartystreet")) {
				sql = "select count(distinct(url_id)) as total from smartystreet_data where user_id = ?";
			}else if(action.equalsIgnoreCase("govShopData")) {
				sql = "select count(distinct(url_id)) as total from govshop_data where user_id = ?";
			}else if(action.equalsIgnoreCase("zoomData")) {
				sql = "select count(distinct(url_id)) as total from zoom_info_data where user_id = ?";
			}else if(action.equalsIgnoreCase("googleZoomData")) {
				sql = "select count(distinct(url_id)) as total from google_zoominfo_data where user_id = ?";
			}else if(action.equalsIgnoreCase("mantaData")) {
				sql = "select count(distinct(url_id)) as total from manta_data where user_id = ?";
			}else if(action.equalsIgnoreCase("zumperData")) {
				sql = "select count(distinct(url_id)) as total from zumper_data where user_id = ?";
			}else if(action.equalsIgnoreCase("rentData")) {
				sql = "select count(distinct(url_id)) as total from rent_data where user_id = ?";
			}else if(action.equalsIgnoreCase("truliaData")) {
				sql = "select count(distinct(url_id)) as total from trulia_data where user_id = ?";
			}
			
			
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				totalRecord =  rs.getInt("total");
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return totalRecord;
	}
	
	
	
	@Override
	public List<Scrap> getScrapData(int userId) {
		// TODO Auto-generated method stub
			List<Scrap> scraps = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from scrap where user_id = ? order by scrap_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Scrap scrap = new Scrap();
				scrap.setScrapId(rs.getInt("scrap_id"));
				scrap.setName(rs.getString("name"));;
				scrap.setCurrent_org(rs.getString("current_org"));;
				scrap.setCurrent_position(rs.getString("current_position"));;
				scrap.setUrl(rs.getString("url"));
				scrap.setUrl_id(rs.getInt("url_id"));
				scraps.add(scrap);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return scraps;
	}

	@Override
	public List<UserDetail> getUserDetails(String action) {
		// TODO Auto-generated method stub
		List<UserDetail> users = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select u.fname,u.lname,u.useremail,u.mobile_number,u.password,count(s.user_id) as total from scrap s,user u where u.user_id=s.user_id  group by  s.user_id";
			
			if(action.equalsIgnoreCase("userVerification")) {
				sql = "select u.fname,u.lname,u.useremail,u.mobile_number,u.password,count(s.user_id) as total,(select count(master_url_id) from master_url pe where status='Active' and s.user_id = pe.user_id) as pending  from scrap s,user u where u.user_id=s.user_id  group by  s.user_id";
			}else if(action.equalsIgnoreCase("companyDetails")) {
				sql = "select u.fname,u.lname,u.useremail,u.mobile_number,u.password,count(s.user_id) as total,(select count(company_url_id) from master_company_url pe where status='Active' and s.user_id = pe.user_id) as pending  from company_detail s,user u where u.user_id=s.user_id  group by  s.user_id";
			}else if(action.equalsIgnoreCase("fullDetails")) {
				sql = "select u.fname,u.lname,u.useremail,u.mobile_number,u.password,count(s.user_id) as total,(select count(master_url_id) from master_url_profile pe where status='Active' and s.user_id = pe.user_id) as pending  from list_contacts s,user u where u.user_id=s.user_id  group by  s.user_id";
			}else if(action.equalsIgnoreCase("profileEmail")) {
				sql = "select u.fname,u.lname,u.useremail,u.mobile_number,u.password,count(s.user_id) as total,(select count(master_profile_email_data_id) from master_profile_email_data pe where status='Active' and s.user_id = pe.user_id) as pending from profile_email_data s,user u where u.user_id=s.user_id  group by  s.user_id";
			}
			
			System.out.println("sql-->"+sql);
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserDetail user = new UserDetail();
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setUserEmail(rs.getString("useremail"));
				user.setPassword(rs.getString("password"));
				user.setMobileNumber(rs.getString("mobile_number"));
				user.setTotal(rs.getString("total"));
				user.setPending(rs.getString("pending")); 
				users.add(user);
			} 
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error ::::");
			
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public List<MasterURL> getUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_url m where  m.status='Done' and  m.user_id = ? and m.master_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_url m where  m.status='Done' and  m.user_id = ? and m.master_url_id not in (select url_id from scrap where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterURL masterURL = new MasterURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_url_id")));
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
	public boolean updateUrlStatus(long urlId, String status,String action) {
		// TODO Auto-generated method stub
		int queryStatus = 0;
		try(Connection con = (Connection) dataSource.getConnection()) {
			
			String sql = "";
			if(action.equalsIgnoreCase("scrap")) {
				sql = "update  master_url set status = ? where master_url_id = ?";
			}else if(action.equalsIgnoreCase("listContacts")){
				sql = "update  master_url_profile set status = ? where master_url_id = ?";
			}else if(action.equalsIgnoreCase("companyData")){
				sql = "update  master_company_url set status = ? where company_url_id = ?";
			}else if(action.equalsIgnoreCase("listBuild")){
				sql = "update  master_list_building_url set status = ? where master_list_url_id = ?";
			}else if(action.equalsIgnoreCase("googleData")){
				sql = "update  master_google_url set status = ? where master_google_url_id = ?";
			}else if(action.equalsIgnoreCase("bingData")){
				sql = "update  master_bing_url set status = ? where master_bing_url_id = ?";
			}else if(action.equalsIgnoreCase("zillowData")){
				sql = "update master_zillow_url set status = ? where master_zillow_url_id = ?";
			}else if(action.equalsIgnoreCase("yelpData")){
				sql = "update master_yelp_url set status = ? where master_yelp_url_id = ?";
			}else if(action.equalsIgnoreCase("mapsData")){
				sql = "update master_maps_url set status = ? where master_maps_url_id = ?";
			}else if(action.equalsIgnoreCase("profileEmailData")){
				sql = "update master_profile_email_data set status = ? where master_profile_email_data_id = ?";
			}else if(action.equalsIgnoreCase("bingMapsData")){
				sql = "update master_bing_maps_url set status = ? where master_bing_maps_url_id = ?";
			}else if(action.equalsIgnoreCase("bingMapsDetail")){
				sql = "update master_bing_maps_detail_url set status = ? where master_bing_maps_detail_url_id = ?";
			}else if(action.equalsIgnoreCase("googlePlaceData")){
				sql = "update master_google_place_url set status = ? where master_google_place_url_id = ?";
			}else if(action.equalsIgnoreCase("spokeoData")){
				sql = "update master_spokeo_url set status = ? where master_spokeo_id = ?";
			}else if(action.equalsIgnoreCase("smartyStreetData")){
				sql = "update master_smartystreet_url set status = ? where master_smartystreet_url_id = ?";
			}else if(action.equalsIgnoreCase("govShopData")){
				sql = "update master_govshop_url set status = ? where master_govshop_url_id = ?";
			}else if(action.equalsIgnoreCase("googleZoomData")){
				sql = "update master_google_zoominfo_url set status = ? where master_google_zoominfo_url_id = ?";
			}else if(action.equalsIgnoreCase("zoomData")){
				sql = "update master_zoominfo_url set status = ? where master_zoominfo_url_id = ?";
			}else if(action.equalsIgnoreCase("mantaData")){
				sql = "update master_manta_url set status = ? where master_manta_url_id = ?";
			}else if(action.equalsIgnoreCase("zumperData")){
				sql = "update master_zumper_url set status = ? where master_zumper_url_id = ?";
			}else if(action.equalsIgnoreCase("rentData")){
				sql = "update master_rent_url set status = ? where master_rent_url_id = ?";
			}else if(action.equalsIgnoreCase("truliaData")){
				sql = "update master_trulia_url set status = ? where master_trulia_url_id = ?";
			}
			
			
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setLong(2, urlId);
			queryStatus = ps.executeUpdate();
			con.commit();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println(urlId+"--Status :::"+queryStatus+"---"+action+"---"+status);
		if(queryStatus > 0)
			return true;
		else		
			return false;
	}

	@Override
	public List<MasterURLProfile> getProfileUrlList(int userId, String action) {
		List<MasterURLProfile> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_url_profile where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_url_profile where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				sql = "select m.* from master_url_profile m where  m.status='Done' and  m.user_id = ? and m.master_url_id not in (select url_id from list_contacts where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterURLProfile masterURL = new MasterURLProfile();
				masterURL.setMasterUrlId(Long.parseLong(rs.getString("master_url_id")));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(Integer.parseInt(rs.getString("user_id")));
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
	public String getQueryTime(String action,String totalHour,int userId) {
		String time = "";
		try(Connection con = (Connection) dataSource.getConnection()) {
			String sql = "";
			PreparedStatement ps = null;
			if(action.equalsIgnoreCase("scrap")) {
				sql = "select count(*) as cnt from  scrap where created_date >= DATE_SUB(NOW(),INTERVAL ? HOUR) and user_id = ?";				
			}else if(action.equalsIgnoreCase("listContacts")) {
				sql = "select count(*) as cnt from  list_contacts where created_date >= DATE_SUB(NOW(),INTERVAL ? HOUR) and user_id = ?";
			}else if(action.equalsIgnoreCase("companyData")) {
				sql = "select count(*) as cnt from  company_detail where created_date >= DATE_SUB(NOW(),INTERVAL ? HOUR) and user_id = ?";
			}else if(action.equalsIgnoreCase("listBuild")) {
				sql = "select count(*) as cnt from  list_building_details where created_date >= DATE_SUB(NOW(),INTERVAL ? HOUR) and user_id = ?";
			}
			
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, totalHour);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				time = rs.getString("cnt");
			}
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return time;
	}

	@Override
	public List<ListContacts> getListContacts(int userId) {
		List<ListContacts> liContacts = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from list_contacts where user_id = ? order by list_contacts_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ListContacts contacts = new ListContacts();
				contacts.setFull_name(rs.getString("full_name"));;
				contacts.setHead_title(rs.getString("head_title"));;
				contacts.setHead_location(rs.getString("head_location"));;
				contacts.setUrl(rs.getString("url"));
				contacts.setListId(rs.getInt("list_contacts_id"));
				liContacts.add(contacts);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return liContacts;
	}

	@Override
	public String getActiveUsers() {
		String userIds="";
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select group_concat(user_id) as user_id from user where status = 'Active'";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userIds = rs.getString("user_id");				
			} 
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userIds;
	}

	@Override
	public boolean updateLinkScore(int userId, String total,String action) {
		System.out.println(userId +"  --"+total);
		int queryStatus = 0;
		try(Connection con = (Connection) dataSource.getConnection()) {
			String sql = "update  user set approved_link = ? where user_id = ?";
			if(action.equalsIgnoreCase("scrap1")) {
				sql = "update  user set approved_link = ? where user_id = ?";
			}else if(action.equalsIgnoreCase("scrap2")) {
				sql = "update  user set approved_link_scrap2 = ? where user_id = ?";
			}else if(action.equalsIgnoreCase("scrap3")) {
				sql = "update  user set approved_link_scrap3 = ? where user_id = ?";
			}else if(action.equalsIgnoreCase("company_log")) {
				sql = "update  user set company_link = ? where user_id = ?";
			}
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, total);
			ps.setLong(2, userId);
			queryStatus = ps.executeUpdate();
			ps.close();
			con.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("updateLinkScore Status :::"+queryStatus);
		if(queryStatus > 0)
			return true;
		else		
			return false;
	}

	@Override
	public boolean setPendingLink(String action, int userId,int limit) {
		int queryStatus = 0;
		try(Connection con = (Connection) dataSource.getConnection()) {
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("resetScrap")) {
				sql = "update master_url set user_id = 0 where user_id = ? and status = 'Active'";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
			}else if(action.equalsIgnoreCase("assignScrap")){
				sql = "UPDATE master_url SET user_id=? 	WHERE master_url_id IN (SELECT master_url_id FROM (SELECT master_url_id FROM master_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("resetCompany")) {
				sql = "update master_company_url set user_id = 0 where user_id = ? and status = 'Active'";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
			}else if(action.equalsIgnoreCase("assignCompany")){
				sql = "UPDATE master_company_url SET user_id=? 	WHERE company_url_id IN (SELECT company_url_id FROM (SELECT company_url_id FROM master_company_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignListBuild")){
				sql = "UPDATE master_list_building_url SET user_id=? 	WHERE master_list_url_id IN (SELECT master_list_url_id FROM (SELECT master_list_url_id FROM master_list_building_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignUserProfile")){
				sql = "UPDATE master_url_profile SET user_id=? 	WHERE master_url_id IN (SELECT master_url_id FROM (SELECT master_url_id FROM master_url_profile where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignGoogle")){
				sql = "UPDATE master_google_url SET user_id=? 	WHERE master_google_url_id IN (SELECT master_google_url_id FROM (SELECT master_google_url_id FROM master_google_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignBingData")){
				sql = "UPDATE master_bing_url SET user_id=? 	WHERE master_bing_url_id IN (SELECT master_bing_url_id FROM (SELECT master_bing_url_id FROM master_bing_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignZillowData")){
				sql = "UPDATE master_zillow_url SET user_id=? 	WHERE master_zillow_url_id IN (SELECT master_zillow_url_id FROM (SELECT master_zillow_url_id FROM master_zillow_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignYelpData")){
				sql = "UPDATE master_yelp_url SET user_id=? 	WHERE master_yelp_url_id IN (SELECT master_yelp_url_id FROM (SELECT master_yelp_url_id FROM master_yelp_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignMapsData")){
				sql = "UPDATE master_maps_url SET user_id=? 	WHERE master_maps_url_id IN (SELECT master_maps_url_id FROM (SELECT master_maps_url_id FROM master_maps_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignProfileEmailData")){
				sql = "UPDATE master_profile_email_data SET user_id=? 	WHERE master_profile_email_data_id IN (SELECT master_profile_email_data_id FROM (SELECT master_profile_email_data_id FROM master_profile_email_data where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignBingMapsData")){
				sql = "UPDATE master_bing_maps_url SET user_id=? 	WHERE master_bing_maps_url_id IN (SELECT master_bing_maps_url_id FROM (SELECT master_bing_maps_url_id FROM master_bing_maps_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignBingMapsDetail")){
				sql = "UPDATE master_bing_maps_detail_url SET user_id=? WHERE master_bing_maps_detail_url_id IN (SELECT master_bing_maps_detail_url_id FROM (SELECT master_bing_maps_detail_url_id FROM master_bing_maps_detail_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignGooglePlaceData")){
				sql = "UPDATE master_google_place_url SET user_id=? WHERE master_google_place_url_id IN (SELECT master_google_place_url_id FROM (SELECT master_google_place_url_id FROM master_google_place_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignSpokeoData")){
				sql = "UPDATE master_spokeo_url SET user_id=? WHERE master_spokeo_id IN (SELECT master_spokeo_id FROM (SELECT master_spokeo_id FROM master_spokeo_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignSmartyStreetData")){
				sql = "UPDATE master_smartystreet_url SET user_id=? WHERE master_smartystreet_url_id IN (SELECT master_smartystreet_url_id FROM (SELECT master_smartystreet_url_id FROM master_smartystreet_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignGovShopData")){
				sql = "UPDATE master_govshop_url SET user_id=? WHERE master_govshop_url_id IN (SELECT master_govshop_url_id FROM (SELECT master_govshop_url_id FROM master_govshop_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignGoogleZoomData")){
				sql = "UPDATE master_google_zoominfo_url SET user_id=? WHERE master_google_zoominfo_url_id IN (SELECT master_google_zoominfo_url_id FROM (SELECT master_google_zoominfo_url_id FROM master_google_zoominfo_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignZoomData")){
				sql = "UPDATE master_zoominfo_url SET user_id=? WHERE master_zoominfo_url_id IN (SELECT master_zoominfo_url_id FROM (SELECT master_zoominfo_url_id FROM master_zoominfo_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignMantaData")){
				sql = "UPDATE master_manta_url SET user_id=? WHERE master_manta_url_id IN (SELECT master_manta_url_id FROM (SELECT master_manta_url_id FROM master_manta_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignZumperData")){
				sql = "UPDATE master_zumper_url SET user_id=? WHERE master_zumper_url_id IN (SELECT master_zumper_url_id FROM (SELECT master_zumper_url_id FROM master_zumper_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignRentData")){
				sql = "UPDATE master_rent_url SET user_id=? WHERE master_rent_url_id IN (SELECT master_rent_url_id FROM (SELECT master_rent_url_id FROM master_rent_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}else if(action.equalsIgnoreCase("assignTruliagData")){
				sql = "UPDATE master_trulia_url SET user_id=? WHERE master_trulia_url_id IN (SELECT master_trulia_url_id FROM (SELECT master_trulia_url_id FROM master_trulia_url where user_id=0 and status = 'Active' LIMIT 0, ?  ) tmp )";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, limit);
			}
			queryStatus = ps.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("Status :::"+queryStatus);
		if(queryStatus > 0)
			return true;
		else		
			return false;
	}

	@Override
	public int insertCompany(CompanyDetails companyDetails) {
		// TODO Auto-generated method stub
			int status = 0;
			try (Connection con = (Connection) dataSource.getConnection()){
				
				String sql = "insert into company_detail(company_name,company_location,employee_count,company_url,company_headquater,year_founded,company_size,"
						+ "company_speciality,company_confirmed_location,affiliate_company,showcase_page,url,url_id,user_id,ipaddress,li_co_id,company_type,company_stock_name,company_industry,phone_number,employees_link,remarks) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, companyDetails.getCompany_name());
				ps.setString(2, companyDetails.getCompany_location());
				ps.setString(3, companyDetails.getEmployee_count());
				ps.setString(4, companyDetails.getCompany_url());
				ps.setString(5, companyDetails.getCompany_headquater());
				ps.setString(6, companyDetails.getYear_founded());
				ps.setString(7, companyDetails.getCompany_size());
				ps.setString(8, companyDetails.getCompany_speciality());
				ps.setString(9, "");
				ps.setString(10, "");
				ps.setString(11, companyDetails.getShowcase_page());
				ps.setString(12, companyDetails.getUrl());
				ps.setString(13, companyDetails.getUrl_id());
				ps.setString(14, companyDetails.getUser_id());
				ps.setString(15, companyDetails.getIpaddress());
				ps.setString(16, companyDetails.getCompany_li_id());
				ps.setString(17, companyDetails.getCompany_type());
				ps.setString(18, companyDetails.getCompany_stock_name());
				ps.setString(19, companyDetails.getCompany_industry());
				ps.setString(20, companyDetails.getPhone_number());
				ps.setString(21, companyDetails.getEmployees_link());
				ps.setString(22, companyDetails.getRemarks());
				
				status = ps.executeUpdate();
				con.commit();		
				 try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
			            if (generatedKeys.next()) {
			            	status = generatedKeys.getInt(1);
			            }
			            else {
			                throw new SQLException("Creating user failed, no ID obtained.");
			            }
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
	public boolean insertCompanyLocation(CompanyLocation companyLocation) {
		int status = 0;
		try (Connection con = (Connection) dataSource.getConnection()){
			
			String sql = "insert into company_location(company_id,country,geographic_area,city,postal_code,description,headquarter,line1,line2,li_co_id) value(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, companyLocation.getCompany_id());
			ps.setString(2, companyLocation.getCountry());
			ps.setString(3, companyLocation.getGeographic_area());
			ps.setString(4, companyLocation.getCity());
			ps.setString(5, companyLocation.getPostal_code());
			ps.setString(6, companyLocation.getDescription());
			ps.setString(7, companyLocation.getHeadquarter());
			ps.setString(8, companyLocation.getLine1());
			ps.setString(9, companyLocation.getLine2());
			ps.setString(10, companyLocation.getLi_id());
			status = ps.executeUpdate();
			con.commit();		
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("Status :::"+status);
		if(status > 0)
			return true;
		else		
			return false;
	}

	@Override
	public boolean insertCompanyAffiliate(CompanyAffiliate companyAffiliate) {
		int status = 0;
		try (Connection con = (Connection) dataSource.getConnection()){			
			String sql = "insert into company_affiliate(company_id,company_name,company_link,company_description,li_co_id) value(?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, companyAffiliate.getCompany_id());
			ps.setString(2, companyAffiliate.getCompany_name());
			ps.setString(3, companyAffiliate.getCompany_link());
			ps.setString(4, companyAffiliate.getCompany_description());
			ps.setString(5, companyAffiliate.getLi_id());
			status = ps.executeUpdate();
			con.commit();		
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("Status :::"+status);
		if(status > 0)
			return true;
		else		
			return false;
	}
	
	@Override
	public List<CompanyDetails> getCompanyDetails() {
		List<CompanyDetails> companyDetails = new ArrayList<>();
		try (Connection con = (Connection) dataSource.getConnection()){
			
			String sql = "select * from company_detail limit 0,1";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				CompanyDetails details = new CompanyDetails();
				details.setCompany_confirmed_location(rs.getString("company_confirmed_location"));;
				companyDetails.add(details);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return companyDetails;
	}


	
	
	@Override
	public List<Scrap> exportScrapData(String startDate,String endDate) {
		// TODO Auto-generated method stub
			List<Scrap> scraps = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from scrap where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Scrap scrap = new Scrap();
				scrap.setScrapId(rs.getInt("scrap_id"));
				scrap.setName(rs.getString("name"));;
				scrap.setCurrent_org(rs.getString("current_org"));;
				scrap.setLocation(rs.getString("location"));
				scrap.setUser_id(rs.getString("user_id"));
				scrap.setCurrent_position(rs.getString("current_position"));;
				scrap.setIpaddress(rs.getString("ipaddress"));
				scrap.setContact_url(rs.getString("contact_url"));
				scrap.setUrl(rs.getString("url"));
				scrap.setUrl_id(rs.getInt("url_id")); 
				scrap.setCreated_date(rs.getString("created_date"));
				scrap.setRemarks(rs.getString("remarks"));
				scrap.setPast_org(rs.getString("past_org"));
				scrap.setPast_position(rs.getString("past_position"));
				scraps.add(scrap);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		System.out.println("Size ::"+scraps.size());
		
		return scraps;
	}

	
	@Override
	public List<MasterURL> exportMasterURL() {
		List<MasterURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
		while (rs.next()) {
			MasterURL masterURL = new MasterURL();
			masterURL.setUrlId(Long.parseLong(rs.getString("master_url_id")));
			masterURL.setUrl((rs.getString("url")));
			masterURL.setStatus(rs.getString("status"));
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
	public boolean reActiveMasterURL(String ids,String action) {
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "";
			if(action.equalsIgnoreCase("masterScrap")) {
				sql = "update master_url set status='Active' where master_url_id in ("+ids+")";
			}else if(action.equalsIgnoreCase("masterListBuild")) {
				sql = "update master_list_building_url set status='Active' where master_list_url_id in ("+ids+")";	
			}else if(action.equalsIgnoreCase("masterCompany")) {
				sql = "update master_company_url set status='Active' where company_url_id in ("+ids+")";
			}
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			status = ps.executeUpdate();
			con.commit();		
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("Status list:::"+status);
		return false;
	}

	@Override
	public List<MasterURLProfile> exportMasterURLProfile() {
		List<MasterURLProfile> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_url_profile";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
		while (rs.next()) {
			MasterURLProfile masterURL = new MasterURLProfile();
			masterURL.setMasterUrlId(Long.parseLong(rs.getString("master_url_id")));
			masterURL.setUrl((rs.getString("url")));
			masterURL.setStatus(rs.getString("status"));
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
	public List<ListContacts> exportListContacts(String startDate, String endDate) {
		List<ListContacts> liContacts = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from list_contacts  where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ListContacts contacts = new ListContacts();
				contacts.setFull_name(rs.getString("full_name"));;
				contacts.setHead_title(rs.getString("head_title"));;
				contacts.setHead_location(rs.getString("head_location"));;
				contacts.setCompany_name(rs.getString("head_location"));
				contacts.setUniversity(rs.getString("university"));
				contacts.setUniversity_url(rs.getString("university_url"));
				contacts.setCurrent_title(rs.getString("current_title"));
				contacts.setCurrent_company(rs.getString("current_company"));
				contacts.setCurrent_company_link(rs.getString("current_company_link"));
				contacts.setCurrent_duration_start(rs.getString("current_duration_start"));
				contacts.setCurrent_duration_end(rs.getString("current_duration_end"));
				contacts.setCurrent_location(rs.getString("current_location"));
				contacts.setPast_title(rs.getString("past_title"));
				contacts.setPast_company(rs.getString("past_company"));
				contacts.setPast_company_link(rs.getString("past_company_link"));
				contacts.setPast_duration_start(rs.getString("past_duration_start"));
				contacts.setPast_duration_end(rs.getString("past_duration_end"));
				contacts.setPast_location(rs.getString("past_location"));
				contacts.setUrl(rs.getString("url"));
				contacts.setCreatedDate(rs.getString("created_date"));
				contacts.setUrl_id(rs.getString("url_id"));
				contacts.setUser_id(rs.getString("user_id"));
				liContacts.add(contacts);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return liContacts;
	}

	@Override
	public int fileUploadDataBase(String url,String tableName) {
		int status = 0;
		try {
			Connection con = (Connection) dataSource.getConnection();
			//String sql = "LOAD DATA LOCAL INFILE '"+ filePath +"' INTO table "+ tableName +" FIELDS TERMINATED BY ','";
			String sql = "insert into "+tableName+"(url) values(?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, url);
			/*ps.setString(2, tableName);*/
			status = ps.executeUpdate();
			con.close();	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Status :::"+status);
		 return status;	
		
	}

	@Override
	public boolean reActiveAllMasterURL(String tableName) {
		int queryStatus = 0;
		try(Connection con = (Connection) dataSource.getConnection()) {
			String sql = "update  "+ tableName +" set user_id=0 where status='Active'";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			queryStatus = ps.executeUpdate();
			con.commit();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(queryStatus > 0)
			return true;
		else		
			return false;
	}



	
}

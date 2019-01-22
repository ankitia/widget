
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
			System.out.println("Size:: "+rs.last());
			System.out.println("Size:: "+rs.getRow());
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
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
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
			
			String sql = "insert into scrap(name,current_org,current_position,location,url,ipaddress,user_id,contact_url,url_id) value(?,?,?,?,?,?,?,?,?)";
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
			status = ps.executeUpdate();
			con.commit();		
			
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
	public boolean insertlist() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean insertListContacts(ListContacts listContacts) {
		int status = 0;
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "insert into list_contacts(full_name,head_title,head_location,company_name,university,university_url,current_title,current_company,current_company_link,current_duration_start,current_duration_end,current_location,past_title,past_company,past_company_link,past_duration_start,past_duration_end,past_location,url,user_id,url_id,ipaddress) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			}else if(action.equals("companyData")) {
				sql = "select count(distinct(url_id)) as total from company_detail where user_id = ?";	
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
	public List<UserDetail> getUserDetails() {
		// TODO Auto-generated method stub
		List<UserDetail> users = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select u.fname,u.lname,u.useremail,u.mobile_number,u.password,count(s.user_id) as total from scrap s,user u where u.user_id=s.user_id  group by  s.user_id";
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
			}
			
			
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setLong(2, urlId);
			queryStatus = ps.executeUpdate();
			con.commit();
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
				sql = "select m.* from master_url_profile m where  m.status='Done' and  m.user_id = ? and m.master_url_id not in (select url_id from scrap where user_id=?);";
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
			}
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, totalHour);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				time = rs.getString("cnt");
			}
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
			System.out.println("Error ::::");
			
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
						+ "company_speciality,company_confirmed_location,affiliate_company,showcase_page,url,url_id,user_id,ipaddress,li_co_id) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			
			String sql = "insert into company_location(company_id,country,geographic_area,city,postal_code,description,headquarter,line1,line2) value(?,?,?,?,?,?,?,?,?)";
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
			status = ps.executeUpdate();
			con.commit();		
			
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
			String sql = "insert into company_affiliate(company_id,company_name,company_link,company_description) value(?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, companyAffiliate.getCompany_id());
			ps.setString(2, companyAffiliate.getCompany_name());
			ps.setString(3, companyAffiliate.getCompany_link());
			ps.setString(4, companyAffiliate.getCompany_description());
			status = ps.executeUpdate();
			con.commit();		
			
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


	
	
	


	
}

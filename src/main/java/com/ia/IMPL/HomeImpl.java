
package com.ia.IMPL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ia.Dao.HomeDao;
import com.ia.modal.Category;
import com.ia.modal.ListContacts;
import com.ia.modal.MasterURL;
import com.ia.modal.MasterURLProfile;
import com.ia.modal.Scrap;
import com.ia.modal.User;
import com.ia.modal.UserDetail;

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
			String sql = "select * from user where useremail = '"+ userName +"' and binary password = '"+ password +"'";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setUserId(rs.getInt("user_id"));
				user.setUserRole(rs.getString("user_role"));
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
			String sql = "insert into user(fname,lname,useremail,password) value('"+user.getFname()+"','"+user.getLname()+"','"+user.getUserEmail()+"','"+user.getPassword()+"')";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
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
			String sql = "select u.fname,u.lname,u.useremail,u.password,count(s.user_id) as total from scrap s,user u where u.user_id=s.user_id  group by  s.user_id";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserDetail user = new UserDetail();
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setUserEmail(rs.getString("useremail"));
				user.setPassword(rs.getString("password"));
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
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterURL masterURL = new MasterURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_url_id")));
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
	public boolean updateUrlStatus(long urlId, String status,String action) {
		// TODO Auto-generated method stub
		int queryStatus = 0;
		try(Connection con = (Connection) dataSource.getConnection()) {
			
			String sql = "";
			if(action.equalsIgnoreCase("scrap")) {
				sql = "update  master_url set status = ? where master_url_id = ?";
			}else if(action.equalsIgnoreCase("listContacts")){
				sql = "update  master_url_profile set status = ? where master_url_id = ?";
			}
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setLong(2, urlId);
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
	
	
	


	
}

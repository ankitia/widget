package com.ia.IMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ia.Dao.CompanyDao;
import com.ia.modal.CompanyDetails;
import com.ia.modal.MasterCompanyURL;


@Component("companyDao")
public class CompanyImpl implements CompanyDao {
	
	@Autowired
	DataSource dataSource;
		
	Connection con;

	@Override
	public List<MasterCompanyURL> getCompanyUrlList(int userId, String action) {
		List<MasterCompanyURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_company_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_company_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				sql = "select m.* from master_company_url m where  m.status='Done' and  m.user_id = ? and m.company_url_id not in (select url_id from company_detail where user_id=?)";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_company_url where status = 'Active' and user_id = ? limit 0,6";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterCompanyURL masterCompanyURL = new MasterCompanyURL();
				masterCompanyURL.setCompanyUrlId(Long.parseLong(rs.getString("company_url_id")));
				masterCompanyURL.setUrl((rs.getString("url")));
				masterCompanyURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				data.add(masterCompanyURL);
			}
			con.close();			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return data;
	
	}

	@Override
	public List<CompanyDetails> getCompanyData(int userId) {
		List<CompanyDetails> companyDetails = new ArrayList<>();
		try (Connection con = (Connection) dataSource.getConnection()){
			
			//String sql = "select *,count(cl.company_id) as locations from company_detail cd LEFT JOIN company_location cl ON cl.company_id=cd.company_id where \n" + 
			//		"user_id = ?  group by cd.company_id limit 0,10";
			String sql = "select *,count(cl.company_id) as locations from master_company_url mu,company_detail cd LEFT JOIN company_location cl ON \n" + 
					"cl.company_id=cd.company_id where mu.company_url_id = cd.url_id and   cd.user_id = ?  group by cd.company_id limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CompanyDetails details = new CompanyDetails();
				details.setCompany_name(rs.getString("company_name"));;
				details.setCompany_location(rs.getString("company_location"));;
				details.setUrl(rs.getString("url"));;
				details.setCompany_id(rs.getInt("company_id"));
				details.setLocationCount(rs.getString("locations"));
				details.setCompany_li_id(rs.getString("li_co_id"));
				companyDetails.add(details);
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return companyDetails;
	}

	@Override
	public int getCurrentDateCount(int userId) {
		try (Connection con = (Connection) dataSource.getConnection()){
			String  sql = "select count(user_id) as count from company_detail where user_id = ? and CURDATE()=date_format(created_date,'%Y-%m-%d')";	
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("count");				
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
		return 0;
	}
	
	
	@Override
	public List<CompanyDetails> exportCompanyData(String startDate,String endDate) {
		List<CompanyDetails> companyDetails = new ArrayList<>();
		try (Connection con = (Connection) dataSource.getConnection()){
			
			//String sql = "select *,count(cl.company_id) as locations from company_detail cd LEFT JOIN company_location cl ON cl.company_id=cd.company_id where \n" + 
			//		"user_id = ?  group by cd.company_id limit 0,10";
			String sql = "select * from company_detail where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CompanyDetails details = new CompanyDetails();
				details.setCompany_id(rs.getInt("company_id"));
				details.setCompany_name(rs.getString("company_name"));;
				details.setCompany_location(rs.getString("company_location"));;
				details.setEmployee_count(rs.getString("employee_count"));
				details.setCompany_url(rs.getString("company_url"));
				details.setCompany_headquater(rs.getString("company_headquater"));
				details.setYear_founded(rs.getString("year_founded"));
				details.setCompany_size(rs.getString("company_size"));
				details.setCompany_speciality(rs.getString("company_speciality"));
				details.setCompany_type(rs.getString("company_type"));
				details.setCompany_industry(rs.getString("company_industry"));
				details.setUrl(rs.getString("url"));
				details.setUser_id(rs.getString("user_id"));
				details.setUrl_id(rs.getString("url_id"));
				details.setCompany_li_id(rs.getString("li_co_id"));
				details.setCreated_date(rs.getString("created_date"));
				companyDetails.add(details);
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return companyDetails;
	}

	@Override
	public List<MasterCompanyURL> exportMasterCompanyData() {
		List<MasterCompanyURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_company_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterCompanyURL masterCompanyURL = new MasterCompanyURL();
				masterCompanyURL.setCompanyUrlId(Long.parseLong(rs.getString("company_url_id")));
				masterCompanyURL.setUrl((rs.getString("url")));
				masterCompanyURL.setStatus(rs.getString("status"));
				masterCompanyURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				data.add(masterCompanyURL);
			}
			con.close();			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return data;
	}
	

}

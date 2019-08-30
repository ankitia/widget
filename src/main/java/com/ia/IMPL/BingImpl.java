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

import com.ia.Dao.BingDao;
import com.ia.modal.BingData;
import com.ia.modal.BingPageUrlsData;
import com.ia.modal.MasterBingURL;
import com.mysql.jdbc.Statement;


@Component("bingDao")
public class BingImpl implements BingDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;
	 
	@Override
	public int insertBingData(BingData bingData) {
		// TODO Auto-generated method stub
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into bing_data(page_urls,entity_title,entity_sub_title,innercards_lst,vlist_lst,official_site,wikipedia,twitter,facebook,linkedin,revenue,founded,headquarters,ceo,founders,url,url_id,user_id,ipaddress) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, bingData.getPage_urls());
			ps.setString(2, bingData.getEntity_title());
			ps.setString(3, bingData.getEntity_sub_title());
			ps.setString(4, bingData.getInnercards_lst());
			ps.setString(5, bingData.getVlist_lst());
			ps.setString(6, bingData.getOfficial_site());
			ps.setString(7, bingData.getWikipedia());
			ps.setString(8, bingData.getTwitter());
			ps.setString(9, bingData.getFacebook());
			ps.setString(10, bingData.getLinkedin());
			ps.setString(11, bingData.getRevenue());
			ps.setString(12, bingData.getFounded());
			ps.setString(13, bingData.getHeadquarters());
			ps.setString(14, bingData.getCeo());
			ps.setString(15, bingData.getFounders());
			ps.setString(16, bingData.getUrl());
			ps.setString(17, bingData.getUrl_id());
			ps.setString(18, bingData.getUser_id());
			ps.setString(19, bingData.getIpaddress());
			
			
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
		System.out.println("Status BingDataData:::"+status);
		return status;
	}
	
	@Override
	public List<MasterBingURL> getBingUrlList(int userId,String action) {
		// TODO Auto-generated method stub
		List<MasterBingURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "";
			if(action.equalsIgnoreCase("active")) {
				sql = "select * from master_bing_url where status = 'Active' and user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
					
			}else if(action.equalsIgnoreCase("all")){
				sql = "select * from master_bing_url where user_id = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
			}else if(action.equalsIgnoreCase("missed")) {
				//sql = "select m.* from master_bing_url m where  m.status='Done' and  m.user_id = ? and m.master_bing_url_id not in (select group_concat(url_id) from scrap where user_id=?)  limit 0,20;";
				sql = "select m.* from master_bing_url m where  m.status='Done' and  m.user_id = ? and m.master_bing_url_id not in (select url_id from bing_data where user_id=?);";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);
				ps.setInt(2,userId);	
			}else if(action.equalsIgnoreCase("display")) {
				sql = "select * from master_bing_url where status = 'Active' and user_id = ? limit 0,10";
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setInt(1,userId);				
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterBingURL masterURL = new MasterBingURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_bing_url_id")));
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
	public List<BingData> getBingData(int userId) {
		// TODO Auto-generated method stub
			List<BingData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from bing_data where user_id = ? order by bing_data_id desc limit 0,10";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				BingData bingData = new BingData();
				bingData.setBingId(rs.getInt("bing_data_id"));
				bingData.setEntity_title(rs.getString("entity_title"));;
				bingData.setEntity_sub_title(rs.getString("entity_sub_title"));;
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

	@Override
	public int insertBingPageData(BingPageUrlsData bingPageUrlsData) {
		int status = 0; 
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "insert into bing_page_data(text_data,link,bing_data_id,type) value(?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, bingPageUrlsData.getText());
			ps.setString(2, bingPageUrlsData.getLink());
			ps.setInt(3, bingPageUrlsData.getBingId());
			ps.setString(4, bingPageUrlsData.getType());
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


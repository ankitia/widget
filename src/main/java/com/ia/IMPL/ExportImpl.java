package com.ia.IMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ia.Dao.ExportDao;
import com.ia.modal.BingMapsData;
import com.ia.modal.BingPageUrlsData;
import com.ia.modal.GooglePlace;
import com.ia.modal.ListBuilding;
import com.ia.modal.MapsTileData;
import com.ia.modal.MasterBingMapsURL;
import com.ia.modal.MasterData;
import com.ia.modal.MasterGoogleURL;
import com.ia.modal.MasterListBuildingURL;
import com.ia.modal.MasterMapsURL;
import com.ia.modal.MasterProfileEmailURL;
import com.ia.modal.MasterSmartystreetURL;
import com.ia.modal.MasterSpokeoURL;
import com.ia.modal.MasterYelpURL;
import com.ia.modal.ProfileEmail;
import com.ia.modal.SmartystreetData;
import com.ia.modal.SpokeoData;
import com.ia.modal.YelpData;

@Component("exportDao")
public class ExportImpl implements ExportDao {
	
	@Autowired
	DataSource dataSource;

	Connection con;

	@Override
	public List<GooglePlace> exportGooglePlaceData(String startDate, String endDate) {
		// TODO Auto-generated method stub
		List<GooglePlace> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from google_place where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				GooglePlace bingData = new GooglePlace();
				bingData.setGooglePlaceId(rs.getInt("google_place_id"));
				bingData.setProperty_name(rs.getString("property_name"));
				bingData.setWebsite(rs.getString("website"));
				bingData.setDirection(rs.getString("direction"));
				bingData.setRating(rs.getString("rating"));
				bingData.setTotal_rating(rs.getString("total_rating"));
				bingData.setDirection(rs.getString("direction"));
				bingData.setIndustry(rs.getString("industry"));
				bingData.setPhone_number(rs.getString("phone_number"));
				bingData.setAddress(rs.getString("address"));
				bingData.setDetail_title(rs.getString("detail_title"));
				bingData.setDetail_website(rs.getString("detail_website"));
				bingData.setDetail_directions(rs.getString("detail_directions"));
				bingData.setDetail_industry(rs.getString("detail_industry"));
				bingData.setDetail_rating(rs.getString("detail_rating"));
				bingData.setDetail_address(rs.getString("detail_address"));
				bingData.setDetail_hours(rs.getString("detail_hours"));
				bingData.setDetail_phone_number(rs.getString("detail_phone_number"));
				bingData.setSocial_links(rs.getString("social_links"));
				bingData.setCreatedDate(rs.getString("created_date"));
				bingData.setUser_id(rs.getString("user_id"));
				bingData.setUrl_id(rs.getString("url_id"));
				bingData.setRating(rs.getString("rating"));
				bingData.setRoot_url(rs.getString("root_url"));
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
	public List<MasterSmartystreetURL> exportMasterSmartystreetData() {
		// TODO Auto-generated method stub
		List<MasterSmartystreetURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_smartystreet_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterSmartystreetURL masterCompanyURL = new MasterSmartystreetURL();
				masterCompanyURL.setUrlId(Long.parseLong(rs.getString("master_smartystreet_url_id")));
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

	@Override
	public List<SmartystreetData> exportSmartystreetData(String startDate, String endDate) {
		List<SmartystreetData> spokeoDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql ="select * from smartystreet_data where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SmartystreetData smartystreetData = new SmartystreetData();
				smartystreetData.setSmartystreetId(rs.getInt("smartystreet_data_id"));
				smartystreetData.setEntered_address(rs.getString("entered_address"));
				smartystreetData.setFound_addresses(rs.getString("found_addresses"));
				smartystreetData.setDelivery_line(rs.getString("delivery_line"));
				smartystreetData.setCity_state_zip(rs.getString("city_state_zip"));
				smartystreetData.setFound_address_status(rs.getString("found_address_status"));
				smartystreetData.setBuilding_default(rs.getString("building_default"));
				smartystreetData.setCarrier_route(rs.getString("carrier_route"));
				smartystreetData.setCongressional_district(rs.getString("congressional_district"));
				smartystreetData.setLatitude(rs.getString("latitude"));
				smartystreetData.setLongitude(rs.getString("longitude"));
				smartystreetData.setCoordinate_precision(rs.getString("coordinate_precision"));
				smartystreetData.setCountry_name(rs.getString("country_name"));
				smartystreetData.setCountry_fips(rs.getString("country_fips"));
				smartystreetData.setElot_sequence(rs.getString("elot_sequence"));
				smartystreetData.setElot_sort(rs.getString("elot_sort"));
				smartystreetData.setObserves_dst(rs.getString("observes_dst"));
				smartystreetData.setRdi(rs.getString("rdi"));
				smartystreetData.setRecord_type(rs.getString("record_type"));
				smartystreetData.setTime_zone(rs.getString("time_zone"));
				smartystreetData.setZip_type(rs.getString("zip_type"));
				smartystreetData.setActive(rs.getString("active"));
				smartystreetData.setCmra(rs.getString("cmra"));
				smartystreetData.setDpv_match_code(rs.getString("dpv_match_code"));
				smartystreetData.setEws_match(rs.getString("ews_match"));
				smartystreetData.setLacslink_code(rs.getString("lacslink_code"));
				smartystreetData.setLacslink_indicator(rs.getString("lacslink_indicator"));
				smartystreetData.setSuitelink_match(rs.getString("suitelink_match"));
				smartystreetData.setVacant(rs.getString("vacant"));
				smartystreetData.setDpv_footnotes(rs.getString("dpv_footnotes"));
				smartystreetData.setGeneral_footnotes(rs.getString("general_footnotes"));
				smartystreetData.setUrbanization(rs.getString("urbanization"));
				smartystreetData.setPrimary_number(rs.getString("primary_number"));
				smartystreetData.setStreet_predirection(rs.getString("street_predirection"));
				smartystreetData.setStreet_name(rs.getString("street_name"));
				smartystreetData.setStreet_postdirection(rs.getString("street_postdirection"));
				smartystreetData.setStreet_suffix(rs.getString("street_suffix"));
				smartystreetData.setSecondary_designator(rs.getString("secondary_designator"));
				smartystreetData.setSecondary_number(rs.getString("secondary_number"));
				smartystreetData.setExtra_secondary_designator(rs.getString("extra_secondary_designator"));
				smartystreetData.setExtra_secondary_number(rs.getString("extra_secondary_number"));
				smartystreetData.setPmb_designator(rs.getString("pmb_designator"));
				smartystreetData.setPmb_number(rs.getString("pmb_number"));
				smartystreetData.setCity(rs.getString("city"));
				smartystreetData.setDefault_city_name(rs.getString("default_city_name"));
				smartystreetData.setState(rs.getString("state"));
				smartystreetData.setZip_code(rs.getString("zip_code"));
				smartystreetData.setPlus_4_code(rs.getString("plus_4_code"));
				smartystreetData.setDelivery_point(rs.getString("delivery_point"));
				smartystreetData.setCheck_digit(rs.getString("check_digit"));
				smartystreetData.setRoot_url(rs.getString("root_url"));
				smartystreetData.setUrl_id(rs.getString("url_id"));
				smartystreetData.setUser_id(rs.getString("user_id"));
				smartystreetData.setRemarks(rs.getString("remarks"));
				smartystreetData.setCreated_date(rs.getString("created_date"));
				spokeoDatas.add(smartystreetData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return spokeoDatas;
	}

	@Override
	public List<MasterSpokeoURL> exportMasterSpokeoUrlList() {
		List<MasterSpokeoURL> spokeoDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from master_spokeo_url";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MasterSpokeoURL spokeoData = new MasterSpokeoURL();
				spokeoData.setUrlId(rs.getInt("master_spokeo_id"));
				spokeoData.setUrl(rs.getString("url"));
				spokeoData.setStatus(rs.getString("status"));
				spokeoData.setUserId(rs.getInt("user_id"));
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
	public List<SpokeoData> exportSpokeoData(String startDate, String endDate) {
		List<SpokeoData> spokeoDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from spokeo_data where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				SpokeoData spokeoData = new SpokeoData();
				spokeoData.setSpokeoId(rs.getInt("spokeo_data_id"));
				spokeoData.setAddress(rs.getString("address"));
				
				spokeoData.setDetails_lst(rs.getString("details_lst"));
				spokeoData.setBuilding_value(rs.getString("building_value"));
				spokeoData.setYear_bulit(rs.getString("year_bulit"));
				spokeoData.setLiving_area(rs.getString("living_area"));
				spokeoData.setLot_size(rs.getString("lot_size"));
				spokeoData.setBathrooms(rs.getString("bathrooms"));
				spokeoData.setBuilding_type(rs.getString("building_type"));
				spokeoData.setCountry(rs.getString("country"));
				spokeoData.setUnits(rs.getString("units"));
				spokeoData.setHome_value(rs.getString("home_value"));
				spokeoData.setBedrooms(rs.getString("bedrooms"));
				spokeoData.setHome_type(rs.getString("home_type"));
				spokeoData.setHeating(rs.getString("heating"));
				spokeoData.setCooling(rs.getString("cooling"));
				spokeoData.setParking(rs.getString("parking"));
				spokeoData.setStories(rs.getString("stories"));
				spokeoData.setStructure(rs.getString("structure"));
				spokeoData.setFireplace(rs.getString("fireplace"));
				spokeoData.setCreated_date(rs.getString("created_date"));
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
	public List<MasterProfileEmailURL> exportMasterProfileEmailUrlList() {
		// TODO Auto-generated method stub
		List<MasterProfileEmailURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_profile_email_data";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterProfileEmailURL masterURL = new MasterProfileEmailURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_profile_email_data_id")));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				masterURL.setStatus(rs.getString("status"));
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
	public List<ProfileEmail> exportProfileEmailDataList(String startDate, String endDate) {
		List<ProfileEmail> profileEmails = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from profile_email_data  where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ProfileEmail profileEmail = new ProfileEmail();
				profileEmail.setProfileId(rs.getInt("profile_email_data_id"));
				profileEmail.setName(rs.getString("name"));;
				profileEmail.setDesignation(rs.getString("designation"));;
				profileEmail.setCompany_name(rs.getString("company_name"));
				profileEmail.setCompany_url(rs.getString("company_url"));
				profileEmail.setRoot_url(rs.getString("root_url"));
				profileEmail.setUrl_id(rs.getString("url_id"));
				profileEmail.setLocation_name(rs.getString("location_name"));
				profileEmail.setLinks(rs.getString("links")); 
				profileEmail.setUser_id(rs.getString("user_id"));
				profileEmail.setCreated_date(rs.getString("created_date"));
				profileEmail.setUrl(rs.getString("url"));
				profileEmail.setMessage(rs.getString("message"));
				profileEmail.setFull_designation(rs.getString("full_designation"));
				profileEmails.add(profileEmail);
				System.out.println(rs.getInt("profile_email_data_id"));
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return profileEmails;
	}

	@Override
	public List<ListBuilding> exportListBuilding(String startDate, String endDate) {
		List<ListBuilding> listBuildings = new ArrayList<>();
		try (Connection con = (Connection) dataSource.getConnection()){
			String sql = "select * from list_building_details where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ListBuilding details = new ListBuilding();
				details.setName(rs.getString("name"));
				details.setNew_link(rs.getString("new_link"));
				details.setCompany_link(rs.getString("company_link"));
				details.setCompany_name(rs.getString("company_name"));;
				details.setCompany_tenure(rs.getString("company_tenure"));
				details.setContact_location(rs.getString("contact_location"));
				details.setContact_designation(rs.getString("contact_designation"));
				details.setUser_id(rs.getString("user_id"));
				details.setUrl_id(rs.getString("url_id"));
				details.setTotal_result_no(rs.getString("total_result_no"));
				details.setTotal_changed_job_no(rs.getString("total_changed_job_no"));
				details.setUrl(rs.getString("url"));;
				details.setListId(rs.getInt("url_id"));
				details.setCreated_date(rs.getString("created_date"));
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
	public List<MasterListBuildingURL> exportMasterListBuilding() {
		List<MasterListBuildingURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_list_building_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterListBuildingURL masterListBuildingURL = new MasterListBuildingURL();
				masterListBuildingURL.setListBuildUrlId(Long.parseLong(rs.getString("master_list_url_id")));
				masterListBuildingURL.setUrl((rs.getString("url")));
				masterListBuildingURL.setStatus(rs.getString("status"));
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
	public List<MasterBingMapsURL> exportMasterBingMapsUrlList() {
		List<MasterBingMapsURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_bing_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterBingMapsURL masterCompanyURL = new MasterBingMapsURL();
				masterCompanyURL.setUrlId(Long.parseLong(rs.getString("master_bing_url_id")));
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

	@Override
	public List<BingMapsData> exportBingMapsData(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<MasterGoogleURL> exportMasterGoogleUrlList() {
		// TODO Auto-generated method stub
		
		List<MasterGoogleURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_google_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterGoogleURL masterURL = new MasterGoogleURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_google_url_id")));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				masterURL.setStatus(rs.getString("status"));
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
	public List<MasterMapsURL> exportMapsDataUrlList() {
		// TODO Auto-generated method stub
		List<MasterMapsURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_maps_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterMapsURL masterURL = new MasterMapsURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_maps_url_id")));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				masterURL.setStatus(rs.getString("status"));
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
	public List<MasterYelpURL> exportMasterYelpUrlList() {
		// TODO Auto-generated method stub
		List<MasterYelpURL> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from master_yelp_url";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterYelpURL masterURL = new MasterYelpURL();
				masterURL.setUrlId(Long.parseLong(rs.getString("master_yelp_url_id")));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				masterURL.setStatus(rs.getString("status"));
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
	public List<MasterData> exportMasterData(String tableName,String idName) {
		// TODO Auto-generated method stub
		List<MasterData> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from "+tableName;
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MasterData masterURL = new MasterData();
				masterURL.setUrlId(Long.parseLong(rs.getString(idName)));
				masterURL.setUrl((rs.getString("url")));
				masterURL.setUserId(rs.getString("user_id").trim()!=""?Integer.parseInt(rs.getString("user_id")): 0);
				masterURL.setStatus(rs.getString("status"));
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
	public List<YelpData> exportYelpData(String startDate, String endDate) {
		List<YelpData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from yelp_data where DATE_FORMAT(created_date,'%m/%d/%Y') BETWEEN ? AND ?";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,startDate);
			ps.setString(2,endDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				YelpData yelpData = new YelpData();
				yelpData.setYelpId(rs.getInt("yelp_data_id"));
				yelpData.setTitle(rs.getString("title"));
				yelpData.setReview(rs.getString("review"));
				yelpData.setStar_rating(rs.getString("star_rating"));
				yelpData.setCategory(rs.getString("category"));
				yelpData.setDirection(rs.getString("direction"));
				yelpData.setPhone(rs.getString("phone"));
				yelpData.setWebsite(rs.getString("website"));
				yelpData.setUser_id(rs.getString("user_id"));
				yelpData.setCreated_date(rs.getString("created_date"));
				yelpData.setAddress(rs.getString("address"));;
				yelpData.setDirection(rs.getString("direction"));;
				yelpData.setOwner(rs.getString("owner"));
				yelpData.setRoot_url(rs.getString("root_url"));
				yelpData.setUrl_id(rs.getString("url_id"));
				bingDatas.add(yelpData);
			}
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bingDatas;
	}

	@Override
	public List<BingPageUrlsData> exportBingData(String startDate, String endDate) {
		List<BingPageUrlsData> bingDatas = new ArrayList<>();
		try {
			Connection con = (Connection) dataSource.getConnection();
			String sql = "select * from bing_page_data";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			/*ps.setString(1,startDate);
			ps.setString(2,endDate);*/
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BingPageUrlsData bingData = new BingPageUrlsData();
				bingData.setText(rs.getString("text_data"));
				bingData.setLink(rs.getString("link"));
				bingData.setType(rs.getString("type"));
				bingData.setLocation(rs.getString("location"));
				bingData.setDescription(rs.getString("description"));
				bingData.setPhone_number(rs.getString("phone_number"));
				bingData.setAddress_location(rs.getString("address_location"));
				bingData.setRating(rs.getString("rating"));
				bingData.setBingId(rs.getInt("bing_data_id"));
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
	public List<MapsTileData> exportMapsTileData() {
		List<MapsTileData> data = new ArrayList<>();
		try(Connection con = (Connection) dataSource.getConnection();) {
			ResultSet rs = null;
			PreparedStatement ps = null;
			String sql = "select * from maps_tile_data";
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MapsTileData mapsTileData = new MapsTileData();
				mapsTileData.setMapsId(Integer.parseInt(rs.getString("maps_tile_data_id")));
				mapsTileData.setName((rs.getString("name")));
				mapsTileData.setRating(rs.getString("rating"));
				mapsTileData.setLocation(rs.getString("location"));
				mapsTileData.setDetail(rs.getString("detail"));
				mapsTileData.setOpening_time(rs.getString("opening_time"));
				data.add(mapsTileData);
			}
			con.close();			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return data;
	}
}

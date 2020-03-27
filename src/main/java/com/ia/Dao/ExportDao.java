package com.ia.Dao;

import java.util.List;

import com.ia.list.modal.RentDataList;
import com.ia.list.modal.ZumperDataList;
import com.ia.modal.BingMapsData;
import com.ia.modal.BingPageUrlsData;
import com.ia.modal.GooglePlace;
import com.ia.modal.GoogleZoominfoData;
import com.ia.modal.GovShopData;
import com.ia.modal.ListBuilding;
import com.ia.modal.MantaData;
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
import com.ia.modal.ZoomInfoData;

public interface ExportDao {
	
	List<GooglePlace> exportGooglePlaceData(String startDate,String endDate);
	
	//ProfileEmail
	List<MasterProfileEmailURL> exportMasterProfileEmailUrlList();
	List<ProfileEmail> exportProfileEmailDataList(String startDate,String endDate);
	
	//Spokeo
	List<MasterSpokeoURL> exportMasterSpokeoUrlList();
	List<SpokeoData> exportSpokeoData(String startDate,String endDate);
	
	//Smartystreet 
	List<MasterSmartystreetURL> exportMasterSmartystreetData();
	List<SmartystreetData> exportSmartystreetData(String startDate,String endDate);
	
	//ListBuilding
	List<ListBuilding> exportListBuilding(String startDate,String endDate);
	List<MasterListBuildingURL> exportMasterListBuilding();
	
	//BingMaps
	List<MasterBingMapsURL> exportMasterBingMapsUrlList();
	List<BingMapsData> exportBingMapsData(String startDate,String endDate);
	
	//GoogleURL
	List<MasterGoogleURL> exportMasterGoogleUrlList();
	
	//Google Maps
	List<MasterMapsURL> exportMapsDataUrlList();
	List<MapsTileData> exportMapsTileData(String startDate, String endDate);
	
	//Yelp
	List<MasterYelpURL> exportMasterYelpUrlList();
	
	//Zillow
	List<MasterData> exportMasterData(String tableName,String idName);
	
	//Yelp Data
	List<YelpData> exportYelpData(String startDate,String endDate);
	
	//Bing Data
	List<BingPageUrlsData> exportBingData(String startDate,String endDate);
	
	//Govshop Data
	List<GovShopData> exportGovShopData(String startDate,String endDate);
	
	//GoogleZoomInfo Data
	List<GoogleZoominfoData> exportGoogleZoomInfoData(String startDate,String endDate);
	
	//ZoomInfo Data
	List<ZoomInfoData> exportZoomInfoData(String startDate,String endDate);
	
	//Manta Data
	List<MantaData> exportMantaData(String startDate,String endDate);
	
	
	//Rent Data
	List<RentDataList> exportRentData(String startDate,String endDate);
	
	//Zumper Data
	List<ZumperDataList> exportZumperData(String startDate,String endDate);
		
}


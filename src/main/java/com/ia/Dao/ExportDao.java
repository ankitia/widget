package com.ia.Dao;

import java.util.List;

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
	List<MapsTileData> exportMapsTileData();
	
	//Yelp
	List<MasterYelpURL> exportMasterYelpUrlList();
	
	//Zillow
	List<MasterData> exportMasterData(String tableName,String idName);
	
	//Yelp Data
	List<YelpData> exportYelpData(String startDate,String endDate);
	
	//Bing Data
	List<BingPageUrlsData> exportBingData(String startDate,String endDate);
	
	
}


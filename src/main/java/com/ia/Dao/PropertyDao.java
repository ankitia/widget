package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterGoogleURL;
import com.ia.modal.Property;


public interface PropertyDao {
	
	public int insertPropertyData(Property property);
	
	List<MasterGoogleURL> getGoogleUrlList(int userId,String action);
	
	List<Property> getGoogleData(int userId);
	
	List<MasterGoogleURL> exportMasterGoogleUrlList();
	
	/*List<MasterListBuildingURL> getListBuildingUrlList(int userId,String action);
	
	List<ListBuildingView> getListBuildingData(int userId,String action); 
	
	List<ListBuilding> exportListBuilding(String startDate,String endDate);
	
	int getCurrentDateCount(int userId);
	
	
	
	public String getListBuildMissedCount(int urlId);
	
	public int getTotalListBuildCount(int userId);

	List<MasterListBuildingURL> exportMasterListBuilding();
*/	
	
}


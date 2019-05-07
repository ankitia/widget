package com.ia.Dao;

import java.util.List;

import com.ia.modal.ListBuilding;
import com.ia.modal.ListBuildingView;
import com.ia.modal.MasterListBuildingURL;


public interface ListBuildingDao {
	
	List<MasterListBuildingURL> getListBuildingUrlList(int userId,String action);
	
	List<ListBuildingView> getListBuildingData(int userId,String action); 
	
	List<ListBuilding> exportListBuilding(String startDate,String endDate);
	
	int getCurrentDateCount(int userId);
	
	public int insertListBuild(ListBuilding listBuilding);
	
	public String getListBuildMissedCount(int urlId);
	
	public int getTotalListBuildCount(int userId);

	List<MasterListBuildingURL> exportMasterListBuilding();
	
	boolean reActiveLinks(String ids);
	
}


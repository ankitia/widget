package com.ia.Dao;

import java.util.List;

import com.ia.modal.ListBuilding;
import com.ia.modal.MasterListBuildingURL;


public interface ListBuildingDao {
	
	List<MasterListBuildingURL> getListBuildingUrlList(int userId,String action);
	
	List<ListBuilding> getListBuildingData(int userId); 
	
	int getCurrentDateCount(int userId);
	
	public int insertListBuild(ListBuilding listBuilding);

}

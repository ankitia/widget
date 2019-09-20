package com.ia.Dao;

import java.util.List;

import com.ia.modal.MapsData;
import com.ia.modal.MapsPlaceData;
import com.ia.modal.MapsTileData;
import com.ia.modal.MasterMapsURL;



public interface MapsDao {
	
	public int insertMapsData(MapsData mapsData);
	
	public int insertMapsPlaceData(MapsPlaceData mapsPlaceData);
	
	public int insertMapsTileData(MapsTileData mapsTileData);
	
	List<MasterMapsURL> getMapsUrlList(int userId,String action);
	
	List<MapsData> getMapsData(int userId);
		
	List<MasterMapsURL> exportMapsDataUrlList(); 
}


package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterZumperURL;
import com.ia.modal.ZumperAmenitiesData;
import com.ia.modal.ZumperData;


public interface ZumperDao {
	
	public int insertZumperData(ZumperData zumperData);
	
	public int insertZumperAmenitiesData(ZumperAmenitiesData amenitiesData);
	
	List<MasterZumperURL> getZumperUrlList(int userId,String action);
	
	List<ZumperData> getZumperData(int userId);
	
	 
}


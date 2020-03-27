package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterRentURL;
import com.ia.modal.RentData;


public interface RentDao {
	
	public int insertRentData(RentData rentData);
	
	public int insertRentAmenitiesData(String amenitiesData,int rentId);
	
	List<MasterRentURL> getRentUrlList(int userId,String action);
	
	List<RentData> getRentData(int userId);
	
	 
}


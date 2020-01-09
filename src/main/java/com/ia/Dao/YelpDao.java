package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterYelpURL;
import com.ia.modal.YelpData;


public interface YelpDao {
	
	public int insertYelpData(YelpData yelpData);
	
	List<MasterYelpURL> getYelpUrlList(int userId,String action);
	
	List<YelpData> getYelpData(int userId);
		
		 
}


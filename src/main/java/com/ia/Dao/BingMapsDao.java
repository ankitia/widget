package com.ia.Dao;

import java.util.List;

import com.ia.modal.BingMapsData;
import com.ia.modal.MasterBingMapsURL;


public interface BingMapsDao {
	
	public int insertBingMapsData(BingMapsData bingData);
	
	/*public int insertBingMapsData(BingPageUrlsData bingPageUrlsData);*/
	
	List<MasterBingMapsURL> getBingMapsUrlList(int userId,String action);
	
	List<BingMapsData> getBingMapsData(int userId);
	
	
	 
}


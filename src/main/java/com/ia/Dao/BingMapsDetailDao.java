package com.ia.Dao;

import java.util.List;

import com.ia.modal.BingMapsDetail;
import com.ia.modal.MasterBingMapsDetailURL;


public interface BingMapsDetailDao {
	
	public int insertBingMapsData(BingMapsDetail bingData);
	
	/*public int insertBingMapsData(BingPageUrlsData bingPageUrlsData);*/
	
	List<MasterBingMapsDetailURL> getBingMapsUrlList(int userId,String action);
	
	List<BingMapsDetail> getBingMapsData(int userId);
	
	 
}


package com.ia.Dao;

import java.util.List;

import com.ia.modal.BingData;
import com.ia.modal.BingPageUrlsData;
import com.ia.modal.MasterBingURL;


public interface BingDao {
	
	public int insertBingData(BingData bingData);
	
	public int insertBingPageData(BingPageUrlsData bingPageUrlsData);
	
	List<MasterBingURL> getBingUrlList(int userId,String action);
	
	List<BingData> getBingData(int userId);
	
	 
}


package com.ia.Dao;

import java.util.List;

import com.ia.modal.BingData;
import com.ia.modal.BingPageUrlsData;
import com.ia.modal.MasterBingURL;
import com.ia.modal.MasterZoomInfoURL;
import com.ia.modal.ZoomInfoData;


public interface ZoomInfoDao {
	
	public int insertZoomData(ZoomInfoData zoominfoData);
	
	List<MasterZoomInfoURL> getZoomUrlList(int userId,String action);
	
	List<ZoomInfoData> getZoomData(int userId);
	
	 
}


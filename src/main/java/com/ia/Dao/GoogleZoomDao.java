package com.ia.Dao;

import java.util.List;

import com.ia.modal.GoogleZoominfoData;
import com.ia.modal.MasterGoogleZoomInfoURL;


public interface GoogleZoomDao {
	
	public int insertGoogleZoomData(GoogleZoominfoData zoominfoData);
	
	List<MasterGoogleZoomInfoURL> getGoogleZoomUrlList(int userId,String action);
	
	List<GoogleZoominfoData> getGoogleZoomData(int userId);
	
	public int insertGoogleZoomData(String url,int zoomId);
	
	
	 
}


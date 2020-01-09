package com.ia.Dao;

import java.util.List;

import com.ia.modal.GooglePlace;
import com.ia.modal.MasterGooglePlaceURL;


public interface GooglePlacesDao {
	
	public boolean insertGooglePlace(GooglePlace googlePlace);
	
	List<MasterGooglePlaceURL> getGooglePlaceUrlList(int userId,String action);
	
	List<GooglePlace> getGoogleMapsData(int userId);
}


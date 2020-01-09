package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterSpokeoURL;
import com.ia.modal.SpokeoData;


public interface SpokeoDao {
	
	public int insertSpokeoData(SpokeoData spokeoData);
	
	List<MasterSpokeoURL> getSpokeoUrlList(int userId,String action);
	
	List<SpokeoData> getSpokeoData(int userId);
	
	MasterSpokeoURL getSpokeoUrl(String url);
	 
	
}


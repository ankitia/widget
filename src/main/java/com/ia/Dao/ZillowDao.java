package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterZillowURL;
import com.ia.modal.ZillowData;


public interface ZillowDao {
	
	public int insertZillowData(ZillowData zillowData);
	
	List<MasterZillowURL> getZillowUrlList(int userId,String action);
	
	List<ZillowData> getZillowData(int userId,String action);
		
	 
}


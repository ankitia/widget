package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterZillowURL;
import com.ia.modal.ZillowData;
import com.ia.modal.ZillowFeatureData;


public interface ZillowDao {
	
	public int insertZillowData(ZillowData zillowData);

	public int insertZillowFeatureData(ZillowFeatureData zillowData);
	
	List<MasterZillowURL> getZillowUrlList(int userId,String action);
	
	List<ZillowData> getZillowData(int userId,String action);
		
	 
}


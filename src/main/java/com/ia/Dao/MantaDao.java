package com.ia.Dao;

import java.util.List;

import com.ia.modal.MantaData;
import com.ia.modal.MasterMantaURL;


public interface MantaDao {
	
	public int insertMantaData(MantaData mantaData);
	
	List<MasterMantaURL> getMantaUrlList(int userId,String action);
	
	List<MantaData> getMantaData(int userId);
	
	public int insertManta(String url,int google_manta_id); 
	
}


package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterSmartystreetURL;
import com.ia.modal.SmartystreetData;


public interface SmartystreeDao {
	
	public int insertSmartystreetData(SmartystreetData smartystreetData);
	
	List<MasterSmartystreetURL> getSmartystreetUrlList(int userId,String action);
	
	List<SmartystreetData> getSmartystreetData(int userId);
	
	
}


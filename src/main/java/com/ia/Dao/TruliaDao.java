package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterTruliaURL;
import com.ia.modal.TruliaData;


public interface TruliaDao {
	
	public int insertTruliaData(TruliaData truliaData);
	
	/*public int insertBingPageData(BingPageUrlsData bingPageUrlsData);*/
	
	List<MasterTruliaURL> getTruliaUrlList(int userId,String action);
	
	List<TruliaData> getTruliaData(int userId);
	
	 
}


package com.ia.Dao;

import java.util.List;

import com.ia.modal.GovShopContactList;
import com.ia.modal.GovShopData;
import com.ia.modal.MasterData;


public interface GovShopDao {
	
	public int insertGovShopData(GovShopData govShopData);
	
	public int insertGovShopDetailData(GovShopContactList contactList);
	
	List<MasterData> getGovShopUrlList(int userId,String action);
	
	List<GovShopData> getGovShopData(int userId);
	
	MasterData getGovShop(String url);
	
	 
}


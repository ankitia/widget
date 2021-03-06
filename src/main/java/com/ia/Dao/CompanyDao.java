package com.ia.Dao;

import java.util.List;

import com.ia.modal.CompanyDetails;
import com.ia.modal.CompanyLocation;
import com.ia.modal.MasterCompanyURL;


public interface CompanyDao {
	
	List<MasterCompanyURL> getCompanyUrlList(int userId,String action);
	
	List<CompanyDetails> getCompanyData(int userId);
	
	int getCurrentDateCount(int userId);
	
	List<CompanyDetails> exportCompanyData(String startDate,String endDate);
	
	List<MasterCompanyURL> exportMasterCompanyData();
	
	List<CompanyLocation> exportCompanyLocations(String exportUrlList);

}

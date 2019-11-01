package com.ia.Dao;

import java.util.List;

import com.ia.modal.Category;
import com.ia.modal.CompanyAffiliate;
import com.ia.modal.CompanyDetails;
import com.ia.modal.CompanyLocation;
import com.ia.modal.ListContacts;
import com.ia.modal.MasterURL;
import com.ia.modal.MasterURLProfile;
import com.ia.modal.Scrap;
import com.ia.modal.User;
import com.ia.modal.UserDetail;

public interface HomeDao {

	List<String> getData();
	
	User checkValidUser(String userName,String password);
	
	boolean insert(User user);
	
	boolean categoryInsert(Category category);
	
	List<Category> getCategory();
	
	List<User> getUserList();
	
	
	List<UserDetail> getUserDetails();
	
	boolean insertScrap(Scrap scrap);
	
	int insertCompany(CompanyDetails companyDetails);
	
	boolean insertCompanyLocation(CompanyLocation companyLocation);
	
	boolean insertCompanyAffiliate(CompanyAffiliate companyAffiliate);
	
	List<CompanyDetails> getCompanyDetails();
	
	
	boolean insertListContacts(ListContacts listContacts);
	
	boolean insertlist();
	
	int getTotalCount(int userId,String action);
	
	List<Scrap> getScrapData(int userId);
	
	List<MasterURL> getUrlList(int userId,String action);
	
	List<MasterURLProfile> getProfileUrlList(int userId,String action);
	
	List<ListContacts> getListContacts(int userId);
	
	
	boolean updateUrlStatus(long urlId,String status,String action);
	
	/*boolean updateProfileUrlStatus(long urlId,String status);*/
	
	String getQueryTime(String action,String totalHour,int userId);
	
	String getActiveUsers();
	
	boolean updateLinkScore(int userId,String total,String action);

	boolean setPendingLink(String action,int userId,int limit);
	
	/* Export query */
	List<Scrap> exportScrapData(String startDate,String endDate);
	
	
	List<MasterURL> exportMasterURL();
	
	boolean reActiveMasterURL(String ids,String action);
 	
	List<MasterURLProfile> exportMasterURLProfile();
	
	List<ListContacts> exportListContacts(String startDate,String endDate);
	
}

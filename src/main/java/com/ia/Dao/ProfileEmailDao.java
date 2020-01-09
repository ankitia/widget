package com.ia.Dao;

import java.util.List;

import com.ia.modal.MasterProfileEmailURL;
import com.ia.modal.ProfileEmail;


public interface ProfileEmailDao {
	
	public int insertProfileEmailData(ProfileEmail profileEmail);
	
	List<MasterProfileEmailURL> getProfileEmailUrlList(int userId,String action);
	
	List<ProfileEmail> getProfileEmailData(int userId);
	
	MasterProfileEmailURL getMasterURLDetail(String url);
	
	
}


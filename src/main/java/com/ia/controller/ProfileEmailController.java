package com.ia.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.Dao.HomeDao;
import com.ia.Dao.ProfileEmailDao;
import com.ia.modal.MasterProfileEmailURL;
import com.ia.modal.ProfileEmail;

@Controller
public class ProfileEmailController {
	
	@Autowired
	ProfileEmailDao profileEmailDao; 
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterProfileEmailURL")
	public String masterProfileEmailURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",profileEmailDao.getProfileEmailUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",profileEmailDao.getProfileEmailUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"profileEmailData"));
		model.addAttribute("userVerificationAll",profileEmailDao.getProfileEmailUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",profileEmailDao.getProfileEmailUrlList(0, "active").size());
		
		return "admin/profile_email_url";
	}
	
	
	@RequestMapping(value="profileEmailVerificationLog")
	public String profileEmailVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"profileEmail"));		
		model.addAttribute("getScrap", profileEmailDao.getProfileEmailData(userId));		
		
		System.out.println("This is profile_email_verification_log  "+userId);
		return "admin/profile_email_verification_log";
	}
	
	@RequestMapping(value="profileEmailVerificationMissed")
	public String profileEmailVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",profileEmailDao.getProfileEmailUrlList(userId,"missed"));	
		System.out.println("This is profileEmailVerificationMissed  "+userId);
		return "admin/profile_email_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertProfileEmailsData", method=RequestMethod.POST)
 	@ResponseBody public String insertProfileEmailsData(ProfileEmail profileEmail,HttpServletRequest request) 
	{
		 if(profileEmail.getUser_id()==null || profileEmail.getUser_id().equalsIgnoreCase("0") || profileEmail.getUser_id().equalsIgnoreCase("")) {
	        	profileEmail.setUser_id("1");
	        }
			 System.out.println(profileEmail.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = profileEmail.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	profileEmail.setUrl_id("0");
	        }
	        profileEmail.setIpaddress(request.getRemoteAddr());
	        
	                
	        MasterProfileEmailURL ma =  profileEmailDao.getMasterURLDetail(profileEmail.getUrl());
	        
	        if(ma!=null) {
	        	profileEmail.setUrl_id(ma.getUrlId()+"");
		        profileEmail.setUser_id(ma.getUserId()+"");
	        }else {
	        	profileEmail.setUrl_id(0+"");
		        profileEmail.setUser_id(0+"");
	        }
	        
	        if(!profileEmail.getMessage().equalsIgnoreCase("") && profileEmail.getMessage().equalsIgnoreCase("You've reached the use limit on Sales Navigator for Gmail. Please try again later.")) {
	        	System.out.println(profileEmail.getMessage());
	        }else {
	        	profileEmailDao.insertProfileEmailData(profileEmail);	
	        }
	        
	        
	        return "";
	}

}

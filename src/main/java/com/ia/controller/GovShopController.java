package com.ia.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.Dao.GovShopDao;
import com.ia.Dao.HomeDao;
import com.ia.modal.GovShopContactList;
import com.ia.modal.GovShopData;
import com.ia.modal.MasterData;

@Controller
public class GovShopController {
	
	@Autowired
	GovShopDao govShopDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterGovShopURL")
	public String masterGovShopURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",govShopDao.getGovShopUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",govShopDao.getGovShopUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"govShopData"));
		model.addAttribute("userVerificationAll",govShopDao.getGovShopUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",govShopDao.getGovShopUrlList(0, "active").size());
		
		return "admin/govshop_url";
	}
	
	
	@RequestMapping(value="govShopVerificationLog")
	public String govShopVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"govShopData"));		
		model.addAttribute("getScrap", govShopDao.getGovShopData(userId));		
		
		System.out.println("This is govShopVerificationLog  "+userId);
		return "admin/govshop_verification_log";
	}
	
	@RequestMapping(value="govShopVerificationMissed")
	public String govShopVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",govShopDao.getGovShopUrlList(userId,"missed"));	
		System.out.println("This is govShopVerificationMissed  "+userId);
		return "admin/govshop_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertGovShopData", method=RequestMethod.POST)
 	@ResponseBody public String insertGovShopData(GovShopData govShopData,HttpServletRequest request) 
	{
		System.out.println(govShopData.getUrl()+"--------"+ govShopData.getUser_id()+"----"+govShopData.getUrl_id());
		
		 MasterData data = govShopDao.getGovShop(govShopData.getUrl());
	        if(data!=null) {
	        	govShopData.setUser_id(data.getUserId()+"");
	        	govShopData.setUrl_id(data.getUrlId()+"");
	        }else {
	        	govShopData.setUser_id("0");
	        	govShopData.setUrl_id("0");
	        }
	        
	        
	        govShopData.setIpaddress(request.getRemoteAddr());
	        int bingId = govShopDao.insertGovShopData(govShopData);
	        
	        try {
	        	  	
	         	  JSONArray jsonArr = new JSONArray(govShopData.getContact_info_lst());
		      	  for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			    	   	GovShopContactList bingPageUrlsData = new GovShopContactList();
			    	   	
			    	   	if(!jsonObj.isNull("contact_title"))
			    	   		bingPageUrlsData.setContact_title(jsonObj.get("contact_title")+"");
			    	   	if(!jsonObj.isNull("contact_box_name"))
			    	   		bingPageUrlsData.setContact_box_name(jsonObj.get("contact_box_name")+"");
			    	   	if(!jsonObj.isNull("contact_box_position"))
			    	   		bingPageUrlsData.setContact_box_position(jsonObj.get("contact_box_position")+"");
			    	   	if(!jsonObj.isNull("contact_box_address"))
			    	   		bingPageUrlsData.setContact_box_address(jsonObj.get("contact_box_address")+"");
			    	   	if(!jsonObj.isNull("contact_box_phone"))
			    	   		bingPageUrlsData.setContact_box_phone(jsonObj.get("contact_box_phone")+"");
			    	   	if(!jsonObj.isNull("contact_box_email"))
			    	   		bingPageUrlsData.setContact_box_email(jsonObj.get("contact_box_email")+"");
			    	    
			    	   		bingPageUrlsData.setGovshop_id(bingId);
			    	   		govShopDao.insertGovShopDetailData(bingPageUrlsData);
			    	   	
		      	  }	
			  } catch (Exception e) {
					e.printStackTrace();
				}
	        return "";
	}

}

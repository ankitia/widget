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

import com.ia.Dao.HomeDao;
import com.ia.Dao.PropertyDao;
import com.ia.modal.Property;

@Controller
public class PropertyController {
	
	@Autowired
	HomeDao homeDao;
	
	@Autowired
	PropertyDao propertyDao;

	@CrossOrigin
	@RequestMapping(value="propertyData", method=RequestMethod.POST)
 	@ResponseBody public String propertyData(Property property,HttpServletRequest request) 
	{
		
		 if(property.getUser_id()==null || property.getUser_id().equalsIgnoreCase("0") || property.getUser_id().equalsIgnoreCase("")) {
        	property.setUser_id("1");
        }
		 System.out.println(property.getUrl_id()+"--"+request.getAttribute("url_id"));
        String urlId = property.getUrl_id()+"";
        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
        	property.setUrl_id("0");
        }
        
        
		
		property.setIpaddress(request.getRemoteAddr());
		
		try {
         	  JSONArray jsonArr = new JSONArray(property.getPlace_data());
	      	  for (int i = 0; i < jsonArr.length(); i++) {			            
		    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
		    	   	
		    	   	if(!jsonObj.isNull("name"))
		    	   		property.setName(jsonObj.get("name")+"");
		    	   	if(!jsonObj.isNull("rating"))
		    	   		property.setRating(jsonObj.get("rating")+"");
		    	   	if(!jsonObj.isNull("industry"))
		    	   		property.setIndustry(jsonObj.get("industry")+"");
		    	   	if(!jsonObj.isNull("address"))
		    	   		property.setAddress(jsonObj.get("address")+"");
		    	   	if(!jsonObj.isNull("phone_number"))
		    	   		property.setPhone_number(jsonObj.get("phone_number")+"");
		    	   	if(!jsonObj.isNull("timing"))
		    	   		property.setTiming(jsonObj.get("timing")+"");
		    	   	if(!jsonObj.isNull("website"))
		    	   		property.setWebsite(jsonObj.get("website")+"");
		    	   	if(!jsonObj.isNull("direction"))
		    	   		property.setDirection(jsonObj.get("direction")+"");
		    	   	
		    	   	System.out.println(property.getName());
		    	   	propertyDao.insertPropertyData(property);
		    	   	
	      	  }	
		  } catch (Exception e) {
				e.printStackTrace();
			}
		return "";
	}
	
	@RequestMapping(value="masterGoogleURL")
	public String masterGoogleURL(HttpServletRequest reques,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",propertyDao.getGoogleUrlList(userId,"display"));
		
		/*model.addAttribute("userLastHour",homeDao.getQueryTime("scrap", "1", userId));
		model.addAttribute("userTotalHour",propertyDao.getQueryTime("scrap", "8", userId));*/
		
		model.addAttribute("userVerificationActive",propertyDao.getGoogleUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"googleData"));
		model.addAttribute("userVerificationAll",propertyDao.getGoogleUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",propertyDao.getGoogleUrlList(0, "active").size());
		
		return "admin/google_url";
	}
	
	@RequestMapping(value="googleVerificationLog")
	public String googleVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"googleData"));		
		model.addAttribute("getScrap", propertyDao.getGoogleData(userId));		
		
		System.out.println("This is google_verification_log  "+userId);
		return "admin/google_verification_log";
	}
	
	@RequestMapping(value="googleVerificationMissed")
	public String googleVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",propertyDao.getGoogleUrlList(userId,"missed"));	
		System.out.println("This is googleVerificationMissed  "+userId);
		return "admin/google_verification_missed";
	}
	
}

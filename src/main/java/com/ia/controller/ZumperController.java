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
import com.ia.Dao.ZumperDao;
import com.ia.modal.ZumperAmenitiesData;
import com.ia.modal.ZumperData;

@Controller
public class ZumperController {
	
	@Autowired
	ZumperDao zumperDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterZumperURL")
	public String masterZumperURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",zumperDao.getZumperUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",zumperDao.getZumperUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"zumperData"));
		model.addAttribute("userVerificationAll",zumperDao.getZumperUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",zumperDao.getZumperUrlList(0, "active").size());
		
		return "admin/zumper_url";
	}
	
	
	@RequestMapping(value="zumperVerificationLog")
	public String zumperVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"zumperData"));		
		model.addAttribute("getScrap", zumperDao.getZumperData(userId));		
		
		System.out.println("This is zumperVerificationMissed  "+userId);
		return "admin/zumper_verification_log";
	}
	
	@RequestMapping(value="zumperVerificationMissed")
	public String zumperVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",zumperDao.getZumperUrlList(userId,"missed"));	
		System.out.println("This is zumperVerificationMissed  "+userId);
		return "admin/zumper_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertZumperData", method=RequestMethod.POST)
 	@ResponseBody public String insertZumperData(ZumperData zumperData,HttpServletRequest request) 
	{
		 if(zumperData.getUser_id()==null || zumperData.getUser_id().equalsIgnoreCase("0") || zumperData.getUser_id().equalsIgnoreCase("")) {
	        	zumperData.setUser_id("1");
	        }
			 System.out.println(zumperData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = zumperData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	zumperData.setUrl_id("0");
	        }
	        zumperData.setIpaddress(request.getRemoteAddr());
	        
	        int zumperId = zumperDao.insertZumperData(zumperData);
	        
	        try {
	        	  	
	         	  JSONArray jsonArr = new JSONArray(zumperData.getAmenities_lst());
		      	  for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			    	
			    	   	ZumperAmenitiesData amenitiesData = new ZumperAmenitiesData();
			    	   	
			    	   	if(!jsonObj.isNull("header"))
			    	   		amenitiesData.setHeader(jsonObj.get("header")+"");
			    	   	if(!jsonObj.isNull("amenities"))
			    	   		amenitiesData.setAmenities(jsonObj.get("amenities")+"");
			    	   		
			    	   	amenitiesData.setZumperId(zumperId);
			    	   	zumperDao.insertZumperAmenitiesData(amenitiesData);
			    	   	
		      	  }	
			  } catch (Exception e) {
					e.printStackTrace();
				}
	         
	        
	        return "";
	}

}

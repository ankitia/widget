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
import com.ia.Dao.RentDao;
import com.ia.modal.RentData;
import com.ia.modal.ZumperAmenitiesData;

@Controller
public class RentController {
	
	@Autowired
	RentDao rentDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterRentURL")
	public String masterRentURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",rentDao.getRentUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",rentDao.getRentUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"rentData"));
		model.addAttribute("userVerificationAll",rentDao.getRentUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",rentDao.getRentUrlList(0, "active").size());
		
		return "admin/rent_url";
	}
	
	
	@RequestMapping(value="rentVerificationLog")
	public String rentVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"rentData"));		
		model.addAttribute("getScrap", rentDao.getRentData(userId));		
		
		System.out.println("This is rentVerificationLog  "+userId);
		return "admin/rent_verification_log";
	}
	
	@RequestMapping(value="rentVerificationMissed")
	public String rentVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",rentDao.getRentUrlList(userId,"missed"));	
		System.out.println("This is rentVerificationMissed  "+userId);
		return "admin/rent_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertRentData", method=RequestMethod.POST)
 	@ResponseBody public String insertRentData(RentData rentData,HttpServletRequest request) 
	{
		 if(rentData.getUser_id()==null || rentData.getUser_id().equalsIgnoreCase("0") || rentData.getUser_id().equalsIgnoreCase("")) {
	        	rentData.setUser_id("1");
	        }
	        String urlId = rentData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	rentData.setUrl_id("0");
	        }
	        rentData.setIpaddress(request.getRemoteAddr());
	        
	        int rentId = rentDao.insertRentData(rentData);
	        
	        try {
	         	  JSONArray jsonArr = new JSONArray(rentData.getAmenities_lst());
		      	  for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			    	
			    	   	ZumperAmenitiesData amenitiesData = new ZumperAmenitiesData();
			    	   	
			    	   	if(!jsonObj.isNull("header"))
			    	   		amenitiesData.setHeader(jsonObj.get("header")+"");
			    	   		
			    	   	rentDao.insertRentAmenitiesData(amenitiesData.getHeader(),rentId);
		      	  }	
			  } catch (Exception e) {
					e.printStackTrace();
				}
	        return "";
	}

}

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
import com.ia.Dao.MapsDao;
import com.ia.modal.MapsData;
import com.ia.modal.MapsPlaceData;
import com.ia.modal.MapsTileData;

@Controller
public class MapsController {
	
	 
	@Autowired
	MapsDao mapsDao; 
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterMapsURL")
	public String masterYelpURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",mapsDao.getMapsUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",mapsDao.getMapsUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"mapsData"));
		model.addAttribute("userVerificationAll",mapsDao.getMapsUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",mapsDao.getMapsUrlList(0, "active").size());
		
		return "admin/maps_url";
	}
	
	
	@RequestMapping(value="mapsVerificationLog")
	public String yelpVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"mapsData"));		
		model.addAttribute("getScrap", mapsDao.getMapsData(userId));		
		
		System.out.println("This is mapsVerificationLog  "+userId);
		return "admin/maps_verification_log";
	}
	
	@RequestMapping(value="mapsVerificationMissed")
	public String yelpVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",mapsDao.getMapsUrlList(userId,"missed"));	
		System.out.println("This is mapsVerificationMissed  "+userId);
		return "admin/maps_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertMapsData", method=RequestMethod.POST)
 	@ResponseBody public String insertYelpData(MapsData mapsData,HttpServletRequest request) 
	{
		 if(mapsData.getUser_id()==null || mapsData.getUser_id().equalsIgnoreCase("0") || mapsData.getUser_id().equalsIgnoreCase("")) {
	        	mapsData.setUser_id("1");
	        }
			 System.out.println(mapsData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = mapsData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	mapsData.setUrl_id("0");
	        }
	        mapsData.setIpaddress(request.getRemoteAddr());
	        
	        int mapsId = mapsDao.insertMapsData(mapsData);
	        
	        
	        try {
	        	   JSONArray jsonArr = new JSONArray(mapsData.getPlace_data());
			       for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);

			            MapsPlaceData mapsPlaceData = new MapsPlaceData();
			            if(!jsonObj.isNull("name"))
			            	mapsPlaceData.setName(jsonObj.get("name")+"");
			            	mapsPlaceData.setMapsId(mapsId);
			            if(!jsonObj.isNull("query"))
			            	mapsPlaceData.setQuery(jsonObj.get("query")+"");
			            if(!jsonObj.isNull("categories"))
			            	mapsPlaceData.setCategories(jsonObj.get("categories")+"");
			            if(!jsonObj.isNull("domain"))
			            	mapsPlaceData.setDomain(jsonObj.get("domain")+"");
			            if(!jsonObj.isNull("reviews"))
			            	mapsPlaceData.setReviews(jsonObj.get("reviews")+"");
			            if(!jsonObj.isNull("phone"))
			            	mapsPlaceData.setPhone(jsonObj.get("phone")+"");

			            mapsDao.insertMapsPlaceData(mapsPlaceData);
			        }
			       
			       
			       jsonArr = new JSONArray(mapsData.getTile_data());
			       for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);			            
			            MapsTileData tileData = new MapsTileData();
			            	tileData.setMapsId(mapsId);
			            if(!jsonObj.isNull("name"))
			            	tileData.setName(jsonObj.get("name")+"");
			            if(!jsonObj.isNull("rating"))
			            	tileData.setRating(jsonObj.get("rating")+"");
			            if(!jsonObj.isNull("location"))
			            	tileData.setLocation(jsonObj.get("location")+"");
			            if(!jsonObj.isNull("location"))
			            	tileData.setDetail(jsonObj.get("detail")+"");
			            if(!jsonObj.isNull("opening_time"))
			            	tileData.setOpening_time(jsonObj.get("opening_time")+"");
			            
			            
			            	mapsDao.insertMapsTileData(tileData);
			        }
			      
	        } catch (Exception e) {
				e.printStackTrace();
			}
			       
	        return "";
	}

}

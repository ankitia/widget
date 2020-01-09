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

import com.ia.Dao.GooglePlacesDao;
import com.ia.Dao.HomeDao;
import com.ia.modal.GooglePlace;

@Controller
public class GooglePlaceController {

	@Autowired
	GooglePlacesDao googlePlacesDao;
	
	@Autowired
	HomeDao homeDao;
	
	
	@RequestMapping(value="masterGooglePlace")
	public String masterGooglePlace(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",googlePlacesDao.getGooglePlaceUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",googlePlacesDao.getGooglePlaceUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"googlePlaceData"));
		model.addAttribute("userVerificationAll",googlePlacesDao.getGooglePlaceUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",googlePlacesDao.getGooglePlaceUrlList(0, "active").size());
		
		return "admin/google_place_url";
	}
	
	@RequestMapping(value="googlePlaceVerificationLog")
	public String googlePlaceVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"googlePlaceData"));		
		model.addAttribute("getScrap", googlePlacesDao.getGoogleMapsData(userId));		
		
		System.out.println("This is googlePlaceVerificationLog  "+userId);
		return "admin/google_place_verification_log";
	}
	
	
	@RequestMapping(value="googlePlaceVerificationMissed")
	public String googlePlaceVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",googlePlacesDao.getGooglePlaceUrlList(userId,"missed"));	
		System.out.println("This is googlePlacesDao missed  "+userId);
		return "admin/google_place_verification_missed";
	}
	
	
	@CrossOrigin
	@RequestMapping(value="insertGooglePlace", method=RequestMethod.POST)
 	@ResponseBody public String insertGooglePlace(GooglePlace googlePlace,HttpServletRequest request) 
	{
		System.out.println("Hello");
		 if(googlePlace.getUser_id()==null || googlePlace.getUser_id().equalsIgnoreCase("0") || googlePlace.getUser_id().equalsIgnoreCase("")) {
	        	googlePlace.setUser_id("1");
	        }
			 System.out.println(googlePlace.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = googlePlace.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	googlePlace.setUrl_id("0");
	        }
	        googlePlace.setIpaddress(request.getRemoteAddr());
	        
	        googlePlacesDao.insertGooglePlace(googlePlace);
	        
	        return "true";
	}   
	
}

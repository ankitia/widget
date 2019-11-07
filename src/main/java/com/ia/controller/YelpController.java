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
import com.ia.Dao.YelpDao;
import com.ia.modal.YelpData;

@Controller
public class YelpController {
	
	@Autowired
	YelpDao yelpDao; 
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterYelpURL")
	public String masterYelpURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",yelpDao.getYelpUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",yelpDao.getYelpUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"yelpData"));
		model.addAttribute("userVerificationAll",yelpDao.getYelpUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",yelpDao.getYelpUrlList(0, "active").size());
		
		return "admin/yelp_url";
	}
	
	
	@RequestMapping(value="yelpVerificationLog")
	public String yelpVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"yelpData"));		
		model.addAttribute("getScrap", yelpDao.getYelpData(userId));		
		
		System.out.println("This is yelpVerificationLog  "+userId);
		return "admin/yelp_verification_log";
	}
	
	@RequestMapping(value="yelpVerificationMissed")
	public String yelpVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",yelpDao.getYelpUrlList(userId,"missed"));	
		System.out.println("This is yelpVerificationMissed  "+userId);
		return "admin/yelp_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertYelpData", method=RequestMethod.POST)
 	@ResponseBody public String insertYelpData(YelpData yelpData,HttpServletRequest request) 
	{
		 if(yelpData.getUser_id()==null || yelpData.getUser_id().equalsIgnoreCase("0") || yelpData.getUser_id().equalsIgnoreCase("")) {
	        	yelpData.setUser_id("1");
	        }
			 System.out.println(yelpData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = yelpData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	yelpData.setUrl_id("0");
	        }
	        yelpData.setIpaddress(request.getRemoteAddr());
	        
	        yelpDao.insertYelpData(yelpData);
	        
	        
	        
	        return "";
	}

}

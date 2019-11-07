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

import com.ia.Dao.BingMapsDao;
import com.ia.Dao.HomeDao;
import com.ia.modal.BingMapsData;

@Controller
public class BingMapsController {
	
	@Autowired
	HomeDao homeDao;
	
	@Autowired
	BingMapsDao bingMapsDao; 
	
	@RequestMapping(value="masterBingMaps")
	public String masterBingMaps(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",bingMapsDao.getBingMapsUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",bingMapsDao.getBingMapsUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"bingMapsData"));
		model.addAttribute("userVerificationAll",bingMapsDao.getBingMapsUrlList(userId,"all").size());
		
		
		
		model.addAttribute("getTotalActiveLink",bingMapsDao.getBingMapsUrlList(0, "active").size());
		
		return "admin/bing_maps_url";
	}
	
	
	@RequestMapping(value="bingMapsVerificationLog")
	public String bingMapsVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"bingData"));		
		model.addAttribute("getScrap", bingMapsDao.getBingMapsData(userId));		
		
		System.out.println("This is bingMapsVerificationLog  "+userId);
		return "admin/bing_maps_verification_log";
	}
	
	@RequestMapping(value="bingMapsVerificationMissed")
	public String bingMapsVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",bingMapsDao.getBingMapsUrlList(userId,"missed"));	
		System.out.println("This is bingMapsVerificationMissed  "+userId);
		return "admin/bing_maps_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertBingMapsData", method=RequestMethod.POST)
 	@ResponseBody public String insertBingMapsData(BingMapsData bingData,HttpServletRequest request) 
	{
		 if(bingData.getUser_id()==null || bingData.getUser_id().equalsIgnoreCase("0") || bingData.getUser_id().equalsIgnoreCase("")) {
	        	bingData.setUser_id("1");
	        }
			 System.out.println(bingData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = bingData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	bingData.setUrl_id("0");
	        }
	        bingData.setIpaddress(request.getRemoteAddr());
	        
	        bingMapsDao.insertBingMapsData(bingData);
	        return "";
	}

}

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

import com.ia.Dao.BingMapsDetailDao;
import com.ia.Dao.HomeDao;
import com.ia.modal.BingMapsDetail;

@Controller
public class BingMapsDetailController {
	
	@Autowired
	HomeDao homeDao;
	
	@Autowired
	BingMapsDetailDao bingMapsDetailDao; 
	
	@RequestMapping(value="masterBingMapsDetail")
	public String masterBingDetailMaps(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",bingMapsDetailDao.getBingMapsUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",bingMapsDetailDao.getBingMapsUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"bingMapsDetail"));
		model.addAttribute("userVerificationAll",bingMapsDetailDao.getBingMapsUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",bingMapsDetailDao.getBingMapsUrlList(0, "active").size());
		
		return "admin/bing_maps_detail_url";
	}
	
	
	@RequestMapping(value="bingMapsDetailVerificationLog")
	public String bingMapsDetailVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"bingMapsDetail"));		
		model.addAttribute("getScrap", bingMapsDetailDao.getBingMapsData(userId));		
		
		System.out.println("This is bing_maps_detail_verification_log  "+userId);
		return "admin/bing_maps_detail_verification_log";
	}
	
	@RequestMapping(value="bingMapsDetailVerificationMissed")
	public String bingMapsDetailVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",bingMapsDetailDao.getBingMapsUrlList(userId,"missed"));	
		System.out.println("This is bing_maps_detail_verification_missed  "+userId);
		return "admin/bing_maps_detail_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertBingMapsDetailData", method=RequestMethod.POST)
 	@ResponseBody public String insertBingMapsDetailData(BingMapsDetail bingData,HttpServletRequest request) 
	{
		 if(bingData.getUser_id()==null || bingData.getUser_id().equalsIgnoreCase("0") || bingData.getUser_id().equalsIgnoreCase("")) {
	        	bingData.setUser_id("1");
	        }
			 
	        String urlId = bingData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	bingData.setUrl_id("0");
	        }
	        bingData.setIpaddress(request.getRemoteAddr());
	        try {
			      bingMapsDetailDao.insertBingMapsData(bingData);
	        }catch (Exception e) {
				e.printStackTrace();
			}
	        
	        return "";
	}

}


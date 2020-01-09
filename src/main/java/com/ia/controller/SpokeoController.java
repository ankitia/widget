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
import com.ia.Dao.SpokeoDao;
import com.ia.modal.MasterSpokeoURL;
import com.ia.modal.SpokeoData;

@Controller
public class SpokeoController {
	
	@Autowired
	SpokeoDao spokeoDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterSpokeoURL")
	public String masterBingURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",spokeoDao.getSpokeoUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",spokeoDao.getSpokeoUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"spokeoData"));
		model.addAttribute("userVerificationAll",spokeoDao.getSpokeoUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",spokeoDao.getSpokeoUrlList(0, "active").size());
		
		return "admin/spokeo_url";
	}
	
	
	@RequestMapping(value="spokeoVerificationLog")
	public String spokeoVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"spokeoData"));		
		model.addAttribute("getScrap", spokeoDao.getSpokeoData(userId));		
		
		System.out.println("This is spokeo_verification_log  "+userId);
		return "admin/spokeo_verification_log";
	}
	
	@RequestMapping(value="spokeoVerificationMissed")
	public String spokeoVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",spokeoDao.getSpokeoUrlList(userId,"missed"));	
		System.out.println("This is spokeo_verification_missed  "+userId);
		return "admin/spokeo_verification_missed";
	}
		
	@CrossOrigin
	@RequestMapping(value="insertSpokeoData", method=RequestMethod.POST)
 	@ResponseBody public String insertSpokeoData(SpokeoData spokeoData,HttpServletRequest request) 
	{
		MasterSpokeoURL data = spokeoDao.getSpokeoUrl(spokeoData.getRoot_url());
		if(data!=null) {
        	spokeoData.setUrl_id(data.getUrlId()+"");
	        spokeoData.setUser_id(data.getUserId()+"");
        }else {
        	spokeoData.setUrl_id(0+"");
	        spokeoData.setUser_id(0+"");
        }
		
		/* if(spokeoData.getUser_id()==null || spokeoData.getUser_id().equalsIgnoreCase("0") || spokeoData.getUser_id().equalsIgnoreCase("")) {
	        	spokeoData.setUser_id("1");
	        }
			 System.out.println(spokeoData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        if(spokeoData.getUrl_id().equalsIgnoreCase("null") || spokeoData.getUrl_id().equalsIgnoreCase("0") || spokeoData.getUrl_id().equalsIgnoreCase("")) {
	        	spokeoData.setUrl_id("0");
	        }*/
	        spokeoData.setIpaddress(request.getRemoteAddr());
	        
	        int bingId = spokeoDao.insertSpokeoData(spokeoData);
	        return bingId+"";
	}

}

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
import com.ia.Dao.SmartystreeDao;
import com.ia.modal.SmartystreetData;

@Controller
public class SmartystreetController {
	
	 @Autowired
	 SmartystreeDao smartystreeDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterSmartystreetDataURL")
	public String masterSmartystreetDataURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",smartystreeDao.getSmartystreetUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",smartystreeDao.getSmartystreetUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"Smartystreet"));
		model.addAttribute("userVerificationAll",smartystreeDao.getSmartystreetUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",smartystreeDao.getSmartystreetUrlList(0, "active").size());
		
		return "admin/smartystreet_url";
	}
	
	
	@RequestMapping(value="smartystreetVerificationLog")
	public String smartystreetVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"Smartystreet"));		
		model.addAttribute("getScrap", smartystreeDao.getSmartystreetData(userId));		
		
		System.out.println("This is smartystreetVerificationLog  "+userId);
		return "admin/smartystreet_verification_log";
	}
	
	@RequestMapping(value="smartystreetVerificationMissed")
	public String smartystreetVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",smartystreeDao.getSmartystreetUrlList(userId,"missed"));	
		System.out.println("This is getSmartystreetUrlList  "+userId);
		return "admin/smartystreet_verification_missed";
	}
	
	@CrossOrigin 
	@RequestMapping(value="insertSmartystreetData", method=RequestMethod.POST)
 	@ResponseBody public String insertSmartystreetData(SmartystreetData smartystreetData,HttpServletRequest request) 
	{
		 if(smartystreetData.getUser_id()==null || smartystreetData.getUser_id().equalsIgnoreCase("0") || smartystreetData.getUser_id().equalsIgnoreCase("")) {
	        	smartystreetData.setUser_id("1");
	        }
			 System.out.println(smartystreetData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = smartystreetData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	smartystreetData.setUrl_id("0");
	        }
	        smartystreetData.setIpaddress(request.getRemoteAddr());
	        
	        smartystreeDao.insertSmartystreetData(smartystreetData);
	        return "";
	}

}

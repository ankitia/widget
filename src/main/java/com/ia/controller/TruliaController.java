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
import com.ia.Dao.TruliaDao;
import com.ia.modal.TruliaData;

@Controller
public class TruliaController {
	
	@Autowired
	TruliaDao truliaDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterTruliaURL")
	public String masterTruliaURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",truliaDao.getTruliaUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",truliaDao.getTruliaUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"truliaData"));
		model.addAttribute("userVerificationAll",truliaDao.getTruliaUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",truliaDao.getTruliaUrlList(0, "active").size());
		
		return "admin/trulia_url";
	}
	
	
	@RequestMapping(value="truliaVerificationLog")
	public String truliaVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"truliaData"));		
		model.addAttribute("getScrap", truliaDao.getTruliaData(userId));		
		
		System.out.println("This is truliaVerificationLog  "+userId);
		return "admin/trulia_verification_log";
	}
	
	@RequestMapping(value="truliaVerificationMissed")
	public String truliaVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",truliaDao.getTruliaUrlList(userId,"missed"));	
		System.out.println("This is truliaVerificationMissed  "+userId);
		return "admin/trulia_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertTruliaData", method=RequestMethod.POST)
 	@ResponseBody public String insertTruliaData(TruliaData truliaData,HttpServletRequest request) 
	{
		 if(truliaData.getUser_id()==null || truliaData.getUser_id().equalsIgnoreCase("0") || truliaData.getUser_id().equalsIgnoreCase("")) {
	        	truliaData.setUser_id("1");
	        }
			 System.out.println(truliaData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = truliaData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	truliaData.setUrl_id("0");
	        }
	        truliaData.setIpaddress(request.getRemoteAddr());
	        
	        int bingId = truliaDao.insertTruliaData(truliaData);
	        
	        
	        
	        return "";
	}

}

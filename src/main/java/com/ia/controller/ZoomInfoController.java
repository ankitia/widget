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

import com.ia.Dao.BingDao;
import com.ia.Dao.HomeDao;
import com.ia.Dao.ZoomInfoDao;
import com.ia.modal.ZoomInfoData;

@Controller
public class ZoomInfoController {
	
	@Autowired
	BingDao bingDao;
	
	@Autowired
	HomeDao homeDao;
	
	@Autowired
	ZoomInfoDao zoomInfoDao;
	
	@RequestMapping(value="masterZoomInfoURL")
	public String masterZoomInfoURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",zoomInfoDao.getZoomUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",zoomInfoDao.getZoomUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"zoomData"));
		model.addAttribute("userVerificationAll",zoomInfoDao.getZoomUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",zoomInfoDao.getZoomUrlList(0, "active").size());
		
		return "admin/zoom_info_url";
	}
	
	
	@RequestMapping(value="zoomVerificationLog")
	public String zoomVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"zoomData"));		
		model.addAttribute("getScrap", zoomInfoDao.getZoomData(userId));		
		
		System.out.println("This is zoomVerificationLog  "+userId);
		return "admin/zoom_ingo_verification_log"; 
	}
	
	@RequestMapping(value="zoomVerificationMissed")
	public String zoomVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",zoomInfoDao.getZoomUrlList(userId,"missed"));	
		System.out.println("This is zoomVerificationMissed  "+userId);
		return "admin/zoom_info_verification_missed";
	}
	
	

	@CrossOrigin
	@RequestMapping(value="insertZoomData", method=RequestMethod.POST)
 	@ResponseBody public String insertZoomData(ZoomInfoData zoominfoData,HttpServletRequest request) 
	{
		 zoominfoData.setIpaddress(request.getRemoteAddr());
		
		 zoomInfoDao.insertZoomData(zoominfoData);
		
		return "";
	}
	
}

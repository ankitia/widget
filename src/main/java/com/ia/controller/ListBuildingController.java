package com.ia.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.Dao.CompanyDao;
import com.ia.Dao.HomeDao;
import com.ia.Dao.ListBuildingDao;
import com.ia.modal.ListBuilding;

@Controller
public class ListBuildingController {
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	ListBuildingDao listBuildingDao; 
	
	@Autowired
	HomeDao homeDao;
	 
	@RequestMapping(value="/listBuildingUrl")
	public String listBuildingUrl(HttpSession session,Model model) {
		
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("listVerificationActive",listBuildingDao.getListBuildingUrlList(userId,"active").size());
		model.addAttribute("listVerificationApproved",homeDao.getTotalCount(userId,"listBuild"));
		
		model.addAttribute("urlList",listBuildingDao.getListBuildingUrlList(userId,"display"));
		
		
		model.addAttribute("getTotalActiveLink",listBuildingDao.getListBuildingUrlList(0, "active").size());
		
		model.addAttribute("userLastHour",homeDao.getQueryTime("listBuild", "1", userId));
		model.addAttribute("userTotalHour",homeDao.getQueryTime("listBuild", "8", userId));
		
		
		
		model.addAttribute("listVerificationAll",listBuildingDao.getListBuildingUrlList(userId,"all").size());	
		
		return "admin/list_build_url";
	}
	
	@RequestMapping(value="listBuildVerificationLog")
	public String listBuildVerificationLog(Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"listBuild"));		
		model.addAttribute("getCompany", listBuildingDao.getListBuildingData(userId));		
		
		System.out.println("This is user_verification_log  "+userId);
		return "admin/listbuild_verification_log";
	}
	
	@RequestMapping(value="listBuildVerificationMissed")
	public String listBuildVerificationMissed(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",listBuildingDao.getListBuildingUrlList(userId,"missed"));	
		System.out.println("This is listBuildVerificationMissed  "+userId);
		return "admin/listbuild_verification_missed";
	}
	 
	@CrossOrigin
	@RequestMapping(value="listBuildData")
 	@ResponseBody public String listBuildData(ListBuilding listBuilding,HttpServletRequest request,HttpSession session) 
	{
		String ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
        System.out.println(listBuilding.getUser_id());
        
        if(listBuilding.getUser_id()==null || listBuilding.getUser_id().equalsIgnoreCase("null") || listBuilding.getUser_id().equalsIgnoreCase("")) {
        	listBuilding.setUser_id("1");
        }
        
         if(listBuilding.getUrl_id()==null || listBuilding.getUrl_id().equalsIgnoreCase("") || listBuilding.getUrl_id().equalsIgnoreCase("null")) {
        	 listBuilding.setUrl_id("0");
         }  
        
        listBuilding.setIpaddress(request.getRemoteAddr());
        return listBuildingDao.insertListBuild(listBuilding)+"";
	}

}

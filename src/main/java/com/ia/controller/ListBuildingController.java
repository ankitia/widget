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
	
	@RequestMapping(value="getListBuildMissedCount")
	@ResponseBody public String getListBuildMissedCount(String urlId) {
		return listBuildingDao.getListBuildMissedCount(Integer.parseInt(urlId))+"";
	}
	
	 
	@CrossOrigin
	@RequestMapping(value="salesBuildDatas")
 	@ResponseBody public String listBuildData(ListBuilding listBuilding,HttpServletRequest request) 
	{
		String ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
        //System.out.println(listBuilding.getTotal_result_no()+"---"+ listBuilding.getUser_id()+ "-----------"+listBuilding.getSales_data());
        
        if(listBuilding.getUser_id()==null || listBuilding.getUser_id().equalsIgnoreCase("null") || listBuilding.getUser_id().equalsIgnoreCase("")) {
        	listBuilding.setUser_id("1");
        }
        
         if(listBuilding.getUrl_id()==null || listBuilding.getUrl_id().equalsIgnoreCase("") || listBuilding.getUrl_id().equalsIgnoreCase("null")) {
        	 listBuilding.setUrl_id("0");
         }
        
         listBuilding.setIpaddress(request.getRemoteAddr());
         try {
      	   JSONArray jsonArr = new JSONArray(listBuilding.getSales_data());
		       for (int i = 0; i < jsonArr.length(); i++) {			            
		    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
		    	   	
		    	   	if(!jsonObj.isNull("name"))
		    	   		listBuilding.setName(jsonObj.get("name")+"");
	    	   		if(!jsonObj.isNull("name_link"))
	    	   			listBuilding.setNew_link(jsonObj.get("name_link")+"");
    	   			if(!jsonObj.isNull("company_name"))
    	   				listBuilding.setCompany_name(jsonObj.get("company_name")+"");
	   				if(!jsonObj.isNull("company_link"))
	   					listBuilding.setCompany_link(jsonObj.get("company_link")+"");
   					if(!jsonObj.isNull("company_tenure"))
   						listBuilding.setCompany_tenure(jsonObj.get("company_tenure")+"");
					if(!jsonObj.isNull("contact_designation"))
						listBuilding.setContact_designation(jsonObj.get("contact_designation")+"");
					if(!jsonObj.isNull("contact_location"))
						listBuilding.setContact_location(jsonObj.get("contact_location")+"");
					if(!jsonObj.isNull("record_no"))
						listBuilding.setRecord_no(jsonObj.get("record_no")+"");
							
		    	   	
		    	   	listBuildingDao.insertListBuild(listBuilding);
		       }
         } catch (Exception e) {
				e.printStackTrace();
			}
        
        return "true";
	}

}

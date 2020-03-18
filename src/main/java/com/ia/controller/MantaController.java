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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.Dao.HomeDao;
import com.ia.Dao.MantaDao;
import com.ia.modal.MantaData;

@Controller
public class MantaController {
	
	@Autowired
	MantaDao mantaDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterMantaURL")
	public String masterMantaURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",mantaDao.getMantaUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",mantaDao.getMantaUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"mantaData"));
		model.addAttribute("userVerificationAll",mantaDao.getMantaUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",mantaDao.getMantaUrlList(0, "active").size());
		
		return "admin/manta_url";
	}
	
	
		@RequestMapping(value="mantaVerificationLog")
	public String mantaVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"mantaData"));		
		model.addAttribute("getScrap", mantaDao.getMantaData(userId));		
		
		System.out.println("This is mantaVerificationLog  "+userId);
		return "admin/manta_verification_log";
	}
	
	@RequestMapping(value="mantaVerificationMissed")
	public String mantaVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",mantaDao.getMantaUrlList(userId,"missed"));	
		System.out.println("This is mantaVerificationMissed  "+userId);
		return "admin/manta_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertMantaData", method=RequestMethod.POST)
 	@ResponseBody public String insertMantaData(MantaData mantaData,HttpServletRequest request) 
	{
		System.out.println("manta call");
		
		 if(mantaData.getUser_id()==null || mantaData.getUser_id().equalsIgnoreCase("0") || mantaData.getUser_id().equalsIgnoreCase("")) {
	        	mantaData.setUser_id("1");
	        }
			 System.out.println(mantaData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = mantaData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	mantaData.setUrl_id("0");
	        }
	        mantaData.setIpaddress(request.getRemoteAddr());
	        
	        try {
	        	JSONArray jsonArr =  null;
	        	 
	        	
	        	if(mantaData.getContact_info().length() > 0) {
	        	   jsonArr = new JSONArray(mantaData.getContact_info());
			       for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			            
			    	   if(i==0) {
			    		   if(!jsonObj.isNull("name"))
				            	mantaData.setContact_info_name(jsonObj.get("name")+"");
				            if(!jsonObj.isNull("designation"))
				            	mantaData.setContact_info_designation(jsonObj.get("designation")+"");
				            if(!jsonObj.isNull("phone"))
				            	mantaData.setContact_info_phone(jsonObj.get("phone")+"");
				            if(!jsonObj.isNull("email"))
				            	mantaData.setContact_info_email(jsonObj.get("email")+"");
			    	   }else if(i==1) {
			    		   if(!jsonObj.isNull("name"))
				            	mantaData.setContact_info_name1(jsonObj.get("name")+"");
				            if(!jsonObj.isNull("designation"))
				            	mantaData.setContact_info_designation1(jsonObj.get("designation")+"");
				            if(!jsonObj.isNull("phone"))
				            	mantaData.setContact_info_phone1(jsonObj.get("phone")+"");
				            if(!jsonObj.isNull("email"))
				            	mantaData.setContact_info_email1(jsonObj.get("email")+"");
			    	   }else if(i==2) {
			    		   if(!jsonObj.isNull("name"))
				            	mantaData.setContact_info_name2(jsonObj.get("name")+"");
				            if(!jsonObj.isNull("designation"))
				            	mantaData.setContact_info_designation2(jsonObj.get("designation")+"");
				            if(!jsonObj.isNull("phone"))
				            	mantaData.setContact_info_phone2(jsonObj.get("phone")+"");
				            if(!jsonObj.isNull("email"))
				            	mantaData.setContact_info_email2(jsonObj.get("email")+"");
			    	   }
			        }
	        	}
	         
				} catch (Exception e) {
					e.printStackTrace();
				}
	        
	        mantaDao.insertMantaData(mantaData);
	        
	        return "";
	}

}

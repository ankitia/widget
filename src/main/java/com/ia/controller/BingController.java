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

import com.ia.Dao.BingDao;
import com.ia.Dao.HomeDao;
import com.ia.modal.BingData;
import com.ia.modal.BingPageUrlsData;

@Controller
public class BingController {
	
	@Autowired
	BingDao bingDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterBingURL")
	public String masterBingURL(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",bingDao.getBingUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",bingDao.getBingUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"bingData"));
		model.addAttribute("userVerificationAll",bingDao.getBingUrlList(userId,"all").size());
		
		
		
		model.addAttribute("getTotalActiveLink",bingDao.getBingUrlList(0, "active").size());
		
		return "admin/bing_url";
	}
	
	
	@RequestMapping(value="bingVerificationLog")
	public String googleVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"bingData"));		
		model.addAttribute("getScrap", bingDao.getBingData(userId));		
		
		System.out.println("This is bingVerificationLog  "+userId);
		return "admin/bing_verification_log";
	}
	
	@RequestMapping(value="bingVerificationMissed")
	public String bingVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",bingDao.getBingUrlList(userId,"missed"));	
		System.out.println("This is bingVerificationMissed  "+userId);
		return "admin/google_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertBingData", method=RequestMethod.POST)
 	@ResponseBody public String insertBingData(BingData bingData,HttpServletRequest request) 
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
	        
	        int bingId = bingDao.insertBingData(bingData);
	        
	        try {
	        	  	
	         	  JSONArray jsonArr = new JSONArray(bingData.getPage_urls());
		      	  for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			    	
			    	   	BingPageUrlsData bingPageUrlsData = new BingPageUrlsData();
			    	   	
			    	   	if(!jsonObj.isNull("text"))
			    	   		bingPageUrlsData.setText(jsonObj.get("text")+"");
			    	   	if(!jsonObj.isNull("link"))
			    	   		bingPageUrlsData.setLink(jsonObj.get("link")+"");
			    	   		
			    	   		bingPageUrlsData.setType("PAGEURL");
			    	   		bingPageUrlsData.setBingId(bingId);
			    	   	
			    	   	bingDao.insertBingPageData(bingPageUrlsData);
			    	   	
		      	  }	
			  } catch (Exception e) {
					e.printStackTrace();
				}
	        
	        try {
	         	  JSONArray jsonArr = new JSONArray(bingData.getInnercards_lst());
		      	  for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			    	
			    	   	BingPageUrlsData bingPageUrlsData = new BingPageUrlsData();
			    	   	
			    	   	if(!jsonObj.isNull("text"))
			    	   		bingPageUrlsData.setText(jsonObj.get("text")+"");
			    	   	if(!jsonObj.isNull("link"))
			    	   		bingPageUrlsData.setLink(jsonObj.get("link")+"");
			    	   		
			    	   		bingPageUrlsData.setType("INNERCARDS");
			    	   		bingPageUrlsData.setBingId(bingId);
			    	   	
			    	   	bingDao.insertBingPageData(bingPageUrlsData);
			    	   	
		      	  }	
			  } catch (Exception e) { 
					e.printStackTrace();
				}
	        
	        return "";
	}

}

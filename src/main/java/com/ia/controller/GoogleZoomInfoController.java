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

import com.ia.Dao.GoogleZoomDao;
import com.ia.Dao.HomeDao;
import com.ia.Dao.MantaDao;
import com.ia.modal.GoogleZoominfoData;

@Controller
public class GoogleZoomInfoController {

	@Autowired
	HomeDao homeDao;
	
	@Autowired
	GoogleZoomDao googleZoomDao; 
	
	@Autowired
	MantaDao mantaDao;
	
 	
	@RequestMapping(value="masterGoogleZoom")
	public String masterGoogleZoom(HttpServletRequest reques,Model model,HttpSession session)
	{
	
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",googleZoomDao.getGoogleZoomUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",googleZoomDao.getGoogleZoomUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"googleZoomData"));
		model.addAttribute("userVerificationAll",googleZoomDao.getGoogleZoomUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",googleZoomDao.getGoogleZoomUrlList(0, "active").size());
		
		return "admin/google_zoom_url";
	}
	
	
	@RequestMapping(value="googleZoomVerificationLog")
	public String googleZoomVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"googleZoomData"));		
		model.addAttribute("getScrap", googleZoomDao.getGoogleZoomData(userId));		
		
		System.out.println("This is googleZoomVerificationLog  "+userId);
		return "admin/google_zoom_verification_log";
	}
	
	@RequestMapping(value="googleZoomVerificationMissed")
	public String googleZoomVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",googleZoomDao.getGoogleZoomUrlList(userId,"missed"));	
		System.out.println("This is googleZoomVerificationMissed  "+userId);
		return "admin/google_zoom_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertGoogleZoomData", method=RequestMethod.POST)
 	@ResponseBody public String insertGoogleZoomData(GoogleZoominfoData zoominfoData,HttpServletRequest request) 
	{
		
		 if(zoominfoData.getUser_id()==null || zoominfoData.getUser_id().equalsIgnoreCase("0") || zoominfoData.getUser_id().equalsIgnoreCase("")) {
	        	zoominfoData.setUser_id("1");
	        }
	        String urlId = zoominfoData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	zoominfoData.setUrl_id("0");
	        }
	        zoominfoData.setIpaddress(request.getRemoteAddr());
	        
	        try {
	        	   JSONArray jsonArr = new JSONArray(zoominfoData.getLinks());
			       for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			    	   	
			    	    if(!jsonObj.isNull("link"))
			    	    	zoominfoData.setLink(jsonObj.get("link")+"");
			    	    if(!jsonObj.isNull("text"))
			    	    	zoominfoData.setText(jsonObj.get("text")+"");
			    	    if(!jsonObj.isNull("exact_match"))
			    	    	zoominfoData.setExact_match(jsonObj.get("exact_match")+"");
			    	    if(!jsonObj.isNull("10_char_match"))
			    	    	zoominfoData.setChar_match_10(jsonObj.get("10_char_match")+"");
			    	    if(!jsonObj.isNull("15_char_match"))
			    	    	zoominfoData.setChar_match_15(jsonObj.get("15_char_match")+"");
			    	    if(!jsonObj.isNull("20_char_match"))
			    	    	zoominfoData.setChar_match_20(jsonObj.get("20_char_match")+"");
			    	    if(!jsonObj.isNull("is_zoominfo_link"))
			    	    	zoominfoData.setIs_zoominfo_link(jsonObj.get("is_zoominfo_link")+"");
			    	    if(!jsonObj.isNull("endpoint_type"))
			    	    	zoominfoData.setEndpoint_type(jsonObj.get("endpoint_type")+"");
			    	    if(!jsonObj.isNull("is_zoominfo_company_link"))
			    	    	zoominfoData.setIs_zoominfo_company_link(jsonObj.get("is_zoominfo_company_link")+"");
			    	    if(!jsonObj.isNull("is_manta_company_link"))
			    	    	zoominfoData.setIs_manta_company_link(jsonObj.get("is_manta_company_link")+"");
			    	    if(!jsonObj.isNull("is_manta_link"))
			    	    	zoominfoData.setIs_manta_link(jsonObj.get("is_manta_link")+"");
			    	    
			    	    
			    	    int googleZoomId = googleZoomDao.insertGoogleZoomData(zoominfoData);
			    	    
			    	    if(i==0 && zoominfoData.getIs_zoominfo_company_link().equalsIgnoreCase("yes") ) {
			    	    	googleZoomDao.insertGoogleZoomData(zoominfoData.getLink(),googleZoomId);
			    	    }
			    	   
			    	    if(zoominfoData.getIs_manta_company_link().equalsIgnoreCase("yes")) {
			    	    	mantaDao.insertManta(zoominfoData.getLink(), googleZoomId);
			    	    }
			    	    
			       }
			        
	        }catch (Exception e) {
				e.printStackTrace();
			}
	        
	        return "";
	}
	
	
	
	
}

package com.ia.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

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
import com.ia.Dao.ZillowDao;
import com.ia.modal.CompanyLocation;
import com.ia.modal.ZillowData;
import com.ia.modal.ZillowFeatureData;

@Controller
public class ZillowController {

	@Autowired
	ZillowDao zillowDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterZillowURL")
	public String masterZillowURL(HttpServletRequest reques,Model model,HttpSession session)
	{
		session.setAttribute("userId",1);
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",zillowDao.getZillowUrlList(userId,"display"));
		
		model.addAttribute("userVerificationActive",zillowDao.getZillowUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"zillowData"));
		model.addAttribute("userVerificationAll",zillowDao.getZillowUrlList(userId,"all").size());
		model.addAttribute("getTotalActiveLink",zillowDao.getZillowUrlList(0, "active").size());
		
		return "admin/zillow_url";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertZillow", method=RequestMethod.POST)
 	@ResponseBody public String insertZillow(ZillowData zillowData,HttpServletRequest request) 
	{
	        zillowData.setIpaddress(request.getRemoteAddr());
	        /*zillowData.setUser_id("0");
	        zillowData.setUrl_id("1");*/
	        int zillowId = zillowDao.insertZillowData(zillowData);
	        
	        try {
	        	JSONArray jsonArr =  null;
	        	 
	        	
	        	if(zillowData.getFeature_lst().length() > 0) {
	        	   jsonArr = new JSONArray(zillowData.getFeature_lst());
			       for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);
			    	   	Iterator keys =jsonObj.keys();
			    	    while(keys.hasNext()) {
			    	    	   // loop to get the dynamic key
			    	    	   String currentDynamicKey = (String)keys.next();
			    	    	   // get the value of the dynamic key
			    	    	   JSONObject currentDynamicValue = jsonObj.getJSONObject(currentDynamicKey);
			    	    	   /*System.out.println("Key ::"+currentDynamicKey+" --value-- "+currentDynamicValue);*/
			    	    	   Iterator keys1 =currentDynamicValue.keys();
			    	    	   while(keys1.hasNext()) {
			    	    		   String currentDynamicKey1 = (String)keys1.next();
			    	    		   /*JSONObject currentDynamicValue1 = currentDynamicValue.getJSONObject(currentDynamicKey1);*/
			    	    		   /*System.out.println("key1--------->"+currentDynamicKey1+" ---value1 ---");
			    	    		   System.out.println("key1--------->"+currentDynamicValue.get(currentDynamicKey1)+" ---value1 ---");*/
			    	    		   
			    	    		   ZillowFeatureData featureData = new ZillowFeatureData();
			    	    		   featureData.setKey_1(currentDynamicKey);
			    	    		   featureData.setKey_2(currentDynamicKey1);
			    	    		   featureData.setKey_value(currentDynamicValue.get(currentDynamicKey1)+"");
			    	    		   featureData.setZillow_id(zillowId+"");
			    	    		   
			    	    		   zillowDao.insertZillowFeatureData(featureData);
			    	    	   }
			    	    	   // do something here with the value...
			    	    	 }
			    	   	
			        }
	        	}
	        } catch (Exception e) {
					e.printStackTrace();
				}
	        
	        
	        
	        
	        
	        
	       
	        
	        return "";
	}       
	
	@RequestMapping(value="downloadZillowFile", method=RequestMethod.GET)
	public String downloadZillowFile(ZillowData zillowData,HttpServletRequest request) {

		/*for (int j = 14394; j < 20000; j++) {
			
			List<ZillowData> zillowDatas = zillowDao.getZillowData(j,"all");
			for (int i = 0; i < zillowDatas.size(); i++) {
				File file = new File("/home/ankit/Desktop/zillow_output/zillow_"+zillowDatas.get(i).getZillowId()+".txt");
		        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
		            String contents = zillowDatas.get(i).getData_lst()+""+zillowDatas.get(i).getPopup_div()+"</zillowlink>"+zillowDatas.get(i).getUrl();
		            writer.write(contents);
		            System.out.println(file.getAbsolutePath());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
			
		}*/
	    
	System.out.println("Done");
		
		return "";
	}
	
}

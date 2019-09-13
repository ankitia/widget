package com.ia.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

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
import com.ia.Dao.ZillowDao;
import com.ia.modal.ZillowData;

@Controller
public class ZillowController {

	@Autowired
	ZillowDao zillowDao;
	
	@Autowired
	HomeDao homeDao;
	
	@RequestMapping(value="masterZillowURL")
	public String masterZillowURL(HttpServletRequest reques,Model model,HttpSession session)
	{
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
		 if(zillowData.getUser_id()==null || zillowData.getUser_id().equalsIgnoreCase("0") || zillowData.getUser_id().equalsIgnoreCase("")) {
	        	zillowData.setUser_id("1");
	        }
			 System.out.println(zillowData.getUrl_id()+"--"+request.getAttribute("url_id"));
	        String urlId = zillowData.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	zillowData.setUrl_id("0");
	        }
	        zillowData.setIpaddress(request.getRemoteAddr());
	        zillowData.setUser_id("0");
	        zillowData.setUrl_id("1");
	        int bingId = zillowDao.insertZillowData(zillowData);
	        
	        return "";
	}       
	
	@RequestMapping(value="downloadZillowFile", method=RequestMethod.GET)
	public String downloadZillowFile(ZillowData zillowData,HttpServletRequest request) {

		List<ZillowData> zillowDatas = zillowDao.getZillowData(0,"all");
		for (int i = 0; i < zillowDatas.size(); i++) {
			File file = new File("/home/jaynil/Desktop/zillow_output/zillow_"+zillowDatas.get(i).getZillowId()+".txt");
	        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
	            String contents = zillowDatas.get(i).getData_lst()+""+zillowDatas.get(i).getPopup_div()+"</zillowlink>"+zillowDatas.get(i).getUrl();
	            writer.write(contents);
	            System.out.println(file.getAbsolutePath());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		 
	    
	System.out.println("Done");
		
		return "";
	}
	
}

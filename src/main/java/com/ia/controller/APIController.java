package com.ia.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.Dao.HomeDao;
import com.ia.modal.MasterURL;

@Controller
public class APIController {

	@Autowired	 
	HomeDao homeDao;

	
	@CrossOrigin
	@RequestMapping(value="userUrlAPI/{userId}")
	@ResponseBody public List<MasterURL> userUrlAPI(@PathVariable("userId") String userId,Model model,HttpSession session)
	{
		return homeDao.getUrlList(Integer.parseInt(userId),"display");
	}
	
	@RequestMapping(value = "/updateUrlStatusAPI/{action}/{urlId}/{status}")
	@ResponseBody String updateUrlStatusAPI(@PathVariable("action") String action,@PathVariable("urlId") String urlId,@PathVariable("status") String status) {
		
		
		if(action.equalsIgnoreCase("scrap")) {
			return  homeDao.updateUrlStatus(Long.parseLong(urlId), status,"scrap")+"";	
		}
		return "false";
	}
	
}

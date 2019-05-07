package com.ia.controller;

import java.net.SocketException;
import java.net.UnknownHostException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.Dao.CompanyDao;
import com.ia.Dao.HomeDao;
import com.ia.Dao.ListBuildingDao;
import com.ia.modal.CompanyAffiliate;
import com.ia.modal.CompanyDetails;
import com.ia.modal.CompanyLocation;

@Controller
public class CompanyController {
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	HomeDao homeDao;
	
	@Autowired
	ListBuildingDao listBuildingDao;
	
	@RequestMapping(value="/companyUrl")
	public String companyUrl(HttpSession session,Model model) {
		
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("userVerificationActive",companyDao.getCompanyUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"companyData"));
		
		model.addAttribute("urlList",companyDao.getCompanyUrlList(userId,"display"));
		model.addAttribute("getTotalActiveLink",companyDao.getCompanyUrlList(0, "active").size());
		model.addAttribute("userLastHour",homeDao.getQueryTime("companyData", "1", userId));
		model.addAttribute("userTotalHour",homeDao.getQueryTime("companyData", "8", userId));
		model.addAttribute("userVerificationAll",companyDao.getCompanyUrlList(userId,"all").size());	
		
		return "admin/company_url";
	}
	
	
	@RequestMapping(value="companyVerificationLog")
	public String companyVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"companyData"));		
		model.addAttribute("getCompany", companyDao.getCompanyData(userId));		
		System.out.println("This is user_verification_log  "+userId);
		return "admin/company_verification_log";
	}
	
	@RequestMapping(value="companyVerificationMissed")
	public String userVerificationMissed(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",companyDao.getCompanyUrlList(userId,"missed"));	
		System.out.println("This is company_verification_missed  "+userId);
		return "admin/company_verification_missed";
	}
	
	@CrossOrigin
	@RequestMapping(value="insertCompany" , method = RequestMethod.POST)
 	@ResponseBody public String insertCompany(CompanyDetails companyDetails,HttpServletRequest request,HttpSession session) throws UnknownHostException, SocketException
	{
		System.out.println("This is call compnay");
	        if(companyDetails.getUser_id()==null || companyDetails.getUser_id().equalsIgnoreCase("0") || companyDetails.getUser_id().equalsIgnoreCase("")) {
	        	companyDetails.setUser_id("1");
	        }
	        
	        String urlId = companyDetails.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	companyDetails.setUrl_id("0");
	        }
	        
	        companyDetails.setIpaddress(request.getRemoteAddr());
	        int companyId = homeDao.insertCompany(companyDetails);
	        //String s = "[{\"country\":\"US\",\"geographicArea\":\"IL\",\"city\":\"Oakbrook Terrace\",\"postalCode\":\"60181\",\"description\":\"Indusa Technical Corp. is a CMM Level 4 certified Software Solutions and Consulting Company, focused on providing cutting edge technology solutions to clients, worldwide.\",\"$type\":\"com.linkedin.voyager.organization.OrganizationAddress\",\"headquarter\":true,\"line2\":\"Suite 350\",\"line1\":\"1 TransAm Plaza Drive\"},{\"country\":\"IN\",\"geographicArea\":\"Gujarat\",\"city\":\"Ahmedabad\",\"postalCode\":\"380054\",\"description\":\"Indusa Technical Corp. is a CMM Level 4 certified Software Solutions and Consulting Company, focused on providing cutting edge technology solutions to clients, worldwide.\",\"$type\":\"com.linkedin.voyager.organization.OrganizationAddress\",\"headquarter\":false,\"line2\":\"SG Road\",\"line1\":\"2nd Floor, GNFC Info tower\"},{\"country\":\"GB\",\"geographicArea\":\"London\",\"city\":\"Hammersmith\",\"postalCode\":\"W1B 2HA\",\"description\":\"Indusa Technical Corp. is a CMM Level 4 certified Software Solutions and Consulting Company, focused on providing cutting edge technology solutions to clients, worldwide.\",\"$type\":\"com.linkedin.voyager.organization.OrganizationAddress\",\"headquarter\":false,\"line2\":\"Hammersmith\",\"line1\":\"1 Lyric Square, Suite 03-25\"}]";
		    
	        try {
	        	   JSONArray jsonArr = new JSONArray(companyDetails.getCompany_confirmed_location());
			       for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);

			            CompanyLocation companyLocation = new CompanyLocation();
			            
			            if(!jsonObj.isNull("city"))
			            	companyLocation.setCity(jsonObj.get("city")+"");
			            	companyLocation.setCompany_id(companyId);
			            if(!jsonObj.isNull("country"))
			            	companyLocation.setCountry(jsonObj.get("country")+"");
			            if(!jsonObj.isNull("description"))
			            	companyLocation.setDescription(jsonObj.get("description")+"");
			            if(!jsonObj.isNull("geographicArea"))
			            	companyLocation.setGeographic_area(jsonObj.get("geographicArea")+"");
			            if(!jsonObj.isNull("postalCode"))
			            	companyLocation.setPostal_code(jsonObj.get("postalCode")+"");
			            if(!jsonObj.isNull("headquarter"))
			            	companyLocation.setHeadquarter(jsonObj.get("headquarter")+"");
			            if(!jsonObj.isNull("line1"))
			            	companyLocation.setLine1(jsonObj.get("line1")+"");
			            if(!jsonObj.isNull("line2"))
			            	companyLocation.setLine2(jsonObj.get("line2")+"");
			            
			            homeDao.insertCompanyLocation(companyLocation);
			        }
			       
			       jsonArr = new JSONArray(companyDetails.getAffiliate_company());
			       for (int i = 0; i < jsonArr.length(); i++) {			            
			    	   	JSONObject jsonObj = jsonArr.getJSONObject(i);			            
			            CompanyAffiliate companyAffiliate = new CompanyAffiliate();
			            	companyAffiliate.setCompany_id(companyId);
			            if(!jsonObj.isNull("company_name"))
			            	companyAffiliate.setCompany_name(jsonObj.get("company_name")+"");
			            if(!jsonObj.isNull("company_link"))
			            	companyAffiliate.setCompany_link(jsonObj.get("company_link")+"");
			            if(!jsonObj.isNull("company_description"))
			            	companyAffiliate.setCompany_description(jsonObj.get("company_description")+"");				            
			            homeDao.insertCompanyAffiliate(companyAffiliate);
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}
	        
		return "true";
	}
	
	
	@RequestMapping(value="getMoreLinks")
	@ResponseBody public String getMoreLinks(HttpSession session,@RequestParam("tableName") String action) {
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		/*int missedLink = companyDao.getCompanyUrlList(userId,"missed").size();
		if(missedLink > 2) {
		}*/
		if(action.equalsIgnoreCase("scrap")) {
				
			return homeDao.setPendingLink("assignScrap", userId, 50)+"";
				
		}else if(action.equalsIgnoreCase("companyData")) {
			if(companyDao.getCurrentDateCount(userId) > 4000){
				return "Your daily 4000 limit exceeded (Per day limit 4000)";
			}else	
				return homeDao.setPendingLink("assignCompany", userId, 50)+"";	
		}else if(action.equalsIgnoreCase("listBuild")) {
			if(listBuildingDao.getListBuildingUrlList(userId, "active").size() < 2) {
				return homeDao.setPendingLink("assignListBuild", userId, 2)+"";	
			}
		}else if(action.equalsIgnoreCase("userProfile")) {
			return homeDao.setPendingLink("assignUserProfile", userId, 50)+"";
		}
		return "";
				
	}

}

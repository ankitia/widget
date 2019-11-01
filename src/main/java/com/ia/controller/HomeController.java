package com.ia.controller;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.ia.Dao.CompanyDao;
import com.ia.Dao.HomeDao;
import com.ia.Dao.ListBuildingDao;
import com.ia.Dao.MapsDao;
import com.ia.Dao.PropertyDao;
import com.ia.Dao.YelpDao;
import com.ia.modal.Category;
import com.ia.modal.CompanyDetails;
import com.ia.modal.CompanyLocation;
import com.ia.modal.ListBuilding;
import com.ia.modal.ListContacts;
import com.ia.modal.MasterCompanyURL;
import com.ia.modal.MasterGoogleURL;
import com.ia.modal.MasterListBuildingURL;
import com.ia.modal.MasterMapsURL;
import com.ia.modal.MasterURL;
import com.ia.modal.MasterURLProfile;
import com.ia.modal.MasterYelpURL;
import com.ia.modal.Scrap;
import com.ia.modal.User;

@Controller
public class HomeController {

	@Autowired	 
	HomeDao homeDao;
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	PropertyDao propertyDao; 
	
	@Autowired
	MapsDao mapsDao; 
	
	@Autowired
	YelpDao yelpDao; 
	
	@Autowired
	ListBuildingDao  listBuildingDao; 
	
	
	
	@RequestMapping(value="/")
	public String home(Model model,HttpSession session) {
		
		/*MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName;
		try {
			objectName = new ObjectName("Catalina:type=Manager,context=/,host=localhost");
			int activeSessions = (Integer) mBeanServer.getAttribute(objectName, "activeSessions");
			
			System.out.println("activeSessions--->>>>>"+activeSessions);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(session.getAttribute("userRole")!=null) {
			if(session.getAttribute("userRole").toString().equalsIgnoreCase("1")) {
				 return "redirect:admindashboard";
			 }else {
				 return "redirect:dashboard";
			 }	
		}
		//return "front/index";
		return "admin/login";
	}
	
	@RequestMapping(value="checkValidUser")
	@ResponseBody public String checkValidUser(HttpServletRequest request,HttpSession session) {
		
		User user = homeDao.checkValidUser(request.getParameter("password"),request.getParameter("userPassword"));
		
		if(user!=null) {
			
			session.setAttribute("username", user.getFname() +" "+ user.getLname());
			
			return "true";
		}else		
			return  "false";
		
	}
	
	@RequestMapping(value="i")
	public String signup(HttpServletRequest request,HttpSession session)
	{
		
		User user = new User();
		user.setFname(request.getParameter("fname"));
		user.setLname(request.getParameter("lname"));
		user.setPassword(request.getParameter("pass"));
		user.setUserEmail(request.getParameter("email"));
		
		return "redirect:/";
	}
	
	@RequestMapping(value="signout")
	public String signout(HttpSession session)
	{
		
		session.setAttribute("username", null);
		return "redirect:/";
				
				
	}
	
	
	
	
	@CrossOrigin
	@RequestMapping(value="getData" , method = RequestMethod.POST)
 	@ResponseBody public String getData(Scrap scrap,HttpServletRequest request,HttpSession session) throws UnknownHostException, SocketException
	{
	        if(scrap.getUser_id()==null || scrap.getUser_id().equalsIgnoreCase("0") || scrap.getUser_id().equalsIgnoreCase("")) {
	        	scrap.setUser_id("1");
	        }
	        String urlId = scrap.getUrl_id()+"";
	        if(urlId.equalsIgnoreCase("null") || urlId==null ) {
	        	scrap.setUrl_id(0);
	        }
	        
	        scrap.setIpaddress(request.getRemoteAddr());
		return homeDao.insertScrap(scrap)+"";
	}
	
	@CrossOrigin
	@RequestMapping(value="listContacts")
 	@ResponseBody public String listContacts(ListContacts listContacts,HttpServletRequest request,HttpSession session) 
	{
		String ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
        System.out.println(listContacts.getUser_id());
        
        if(listContacts.getUser_id()==null || listContacts.getUser_id().equalsIgnoreCase("null") || listContacts.getUser_id().equalsIgnoreCase("")) {
        	listContacts.setUser_id("1");
        }
        
         if(listContacts.getUrl_id()==null || listContacts.getUrl_id().equalsIgnoreCase("") || listContacts.getUrl_id().equalsIgnoreCase("null")) {
        	 listContacts.setUrl_id("0");
         }
        
        listContacts.setIpaddress(request.getRemoteAddr());
        return homeDao.insertListContacts(listContacts)+"";
	}

	/* Start Admin controller */
	@RequestMapping(value="login")
	public String login()
	{
		
		

		
		System.out.println("This is callllllllllllllllllllllllllllllll");
		return "admin/login";
	}
	
	@RequestMapping(value = "/updateUrlStatus")
	@ResponseBody String updateUrlStatus(HttpServletRequest request) {
		
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("scrap")) {
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"scrap")+"";	
		}else if(action.equalsIgnoreCase("listContacts")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"listContacts")+"";
		}else if(action.equalsIgnoreCase("companyData")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"companyData")+"";
		}else if(action.equalsIgnoreCase("listBuild")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"listBuild")+"";
		}else if(action.equalsIgnoreCase("googleData")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"googleData")+"";
		}else if(action.equalsIgnoreCase("bingData")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"bingData")+"";
		}else if(action.equalsIgnoreCase("zillowData")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"zillowData")+"";
		}else if(action.equalsIgnoreCase("yelpData")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"yelpData")+"";
		}else if(action.equalsIgnoreCase("mapsData")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"mapsData")+"";
		}else if(action.equalsIgnoreCase("profileEmailData")){
			return  homeDao.updateUrlStatus(Long.parseLong(request.getParameter("urlId")), request.getParameter("status"),"profileEmailData")+"";
		}
		
		return "false";
	}
	
	@RequestMapping(value = "/checkUser", method = RequestMethod.POST )
	public String checkUser(HttpServletRequest request,Model model,HttpSession session,HttpServletResponse response)  {
		
		System.out.println(request.getParameter("Email")+"---------"+request.getParameter("password"));
		 User user = homeDao.checkValidUser(request.getParameter("Email"), request.getParameter("password"));
		 
		 System.out.println("User ::"+user.getUserId());
		 
		 if(user!=null && user.getUserId()>0) {		 
			 session.setAttribute("userName", request.getParameter("Email"));
			 session.setAttribute("userId", user.getUserId());
			 session.setAttribute("userRole", user.getUserRole());
			 session.setAttribute("approvedLink", user.getApprovedLink());
			 session.setAttribute("approvedLink2", user.getApprovedLink2());
			 /*session.setAttribute("approvedLink3", user.getApprovedLink3());*/
			 session.setAttribute("companyLink", user.getCompanyLink()); 
			 System.out.println("Login user Id::"+ user.getUserId());
			 
			 Cookie ck=new Cookie("userId",user.getUserId()+"");//creating cookie object  
			 response.addCookie(ck);//adding cookie in the response  

			 
			 String userRole = user.getUserRole();
			 System.out.println(user +"--------------");
			 if(userRole.equalsIgnoreCase("1")) {
				 return "redirect:admindashboard";
			 }else {
				 
			 }
			 
			 return "redirect:dashboard";
		 }else {
			
			model.addAttribute("message", "Please enter valid username and password");
			return "admin/login";
		}		
	}
	
	
	
	@RequestMapping(value="logout")
	public String logout(HttpSession session)
	{
		session.setAttribute("userName", null);
		session.setAttribute("userId", null);
		session.setAttribute("userRole", null);
		 
		return "admin/login";
	}
	
	@RequestMapping(value="manageUser")
	public String user(Model model)
	{
		model.addAttribute("userList",homeDao.getUserList());
		
		return "admin/manageUser";
	}
	
	
	@RequestMapping(value="admindashboard")
	public String admindashboard(HttpServletRequest requestm,Model model)
	{
		model.addAttribute("userList", homeDao.getUserDetails());		
		System.out.println("This is admin dashboard -- ");
		return "admin/admindashboard";
	}
	
	@RequestMapping(value="userAssigned-ia")
	public String userAssigned(HttpServletRequest requestm,Model model)
	{
		model.addAttribute("userList", homeDao.getUserList());		
		return "admin/user_assigned";
	}
	
	@RequestMapping(value="getTotalCount")
	@ResponseBody public String getTotalCount(String action) {
		
		
		if(action.equalsIgnoreCase("userProfile")) {
			return homeDao.getUrlList(0,"all").size()+"";	
		}else if(action.equalsIgnoreCase("companyData")) {
			return companyDao.getCompanyUrlList(0,"all").size()+"";
		}
		return "";
	}
	
	
	
	@RequestMapping(value="setPendingLink")
	@ResponseBody public String getUserActiveLink(HttpServletRequest request) {
		
		String dataProcess = request.getParameter("dataProcess");
		String action = request.getParameter("action");
		if(dataProcess.equalsIgnoreCase("userProfile")) {
			
			if(action.equalsIgnoreCase("reset")) {
				action = "resetScrap";
			}else {
				action = "assignScrap";
			}			
			
		}else if(dataProcess.equalsIgnoreCase("companyData")) {
			
			if(action.equalsIgnoreCase("reset")) {
				action = "resetCompany";
			}else {
				action = "assignCompany";
			}
		}
		
		System.out.println("action------------"+action);
		
		return homeDao.setPendingLink(action, Integer.parseInt(request.getParameter("userId")),Integer.parseInt(request.getParameter("limit")))+"";
	}
	
	
	@RequestMapping(value="userUrl")
	public String userUrl(HttpServletRequest reques,Model model,HttpSession session)
	{
		System.out.println("User url");
		
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",homeDao.getUrlList(userId,"display"));
		
		model.addAttribute("userLastHour",homeDao.getQueryTime("scrap", "1", userId));
		model.addAttribute("userTotalHour",homeDao.getQueryTime("scrap", "8", userId));
		
		model.addAttribute("userVerificationActive",homeDao.getUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"scrap"));
		model.addAttribute("userVerificationAll",homeDao.getUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",homeDao.getUrlList(0, "active").size());
		
		return "admin/user_url";
	}
	
	
	
	
	@RequestMapping(value="userProfile")
	public String userProfile(HttpServletRequest reques,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",homeDao.getProfileUrlList(userId,"active"));
		
		model.addAttribute("userProfileLastHour",homeDao.getQueryTime("listContacts", "1", userId));
		model.addAttribute("userProfileTotalHour",homeDao.getQueryTime("listContacts", "8", userId));
		
		model.addAttribute("userProfileActive",homeDao.getProfileUrlList(userId,"active").size());
		model.addAttribute("userProfileApproved",homeDao.getTotalCount(userId,"listContacts"));
		model.addAttribute("userProfileAll",homeDao.getProfileUrlList(userId,"all").size());
		
		model.addAttribute("getTotalActiveLink",homeDao.getProfileUrlList(0, "active").size());
		
		return "admin/user_profile";
	}	
	
	@RequestMapping(value="dashboard")
	public String dashboard(HttpServletRequest requestm,Model model,HttpSession session)
	{
		if(session.getAttribute("userId")!=null) {
		
			int userId = Integer.parseInt(session.getAttribute("userId")+"");
			
			
			model.addAttribute("userVerificationActive",homeDao.getUrlList(userId,"active").size());
			model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"scrap"));
			/*model.addAttribute("userVerificationMissed",homeDao.getUrlList(userId,"missed").size());*/
				
			model.addAttribute("userVerificationAll",homeDao.getUrlList(userId,"all").size());
			/*model.addAttribute("getScrap", homeDao.getScrapData(userId));*/ 		
			/*model.addAttribute("userLastHour",homeDao.getQueryTime("scrap", "1", userId));
			model.addAttribute("userTotalHour",homeDao.getQueryTime("scrap", "8", userId));
			*/
			
			
			model.addAttribute("userProfileActive",homeDao.getProfileUrlList(userId,"active").size());
			model.addAttribute("userProfileApproved",homeDao.getTotalCount(userId,"listContacts"));
			model.addAttribute("userProfileAll",homeDao.getProfileUrlList(userId,"all").size());
			
			
			/*model.addAttribute("userProfileLastHour",homeDao.getQueryTime("listContacts", "1", userId));
			model.addAttribute("userProfileTotalHour",homeDao.getQueryTime("listContacts", "8", userId));
			*/
			
			model.addAttribute("userVerificationApprovedLog",homeDao.getTotalCount(userId,"scrap_log"));
			model.addAttribute("userVerificationApprovedLog2",homeDao.getTotalCount(userId,"scrap_log2"));
			/*model.addAttribute("userVerificationApprovedLog3",homeDao.getTotalCount(userId,"scrap_log3"));*/
			model.addAttribute("companyVerification",homeDao.getTotalCount(userId,"company_log"));
			
			
			model.addAttribute("companyVerificationActive",companyDao.getCompanyUrlList(userId,"active").size());
			model.addAttribute("companyVerificationApproved",homeDao.getTotalCount(userId,"companyData"));
			model.addAttribute("companyVerificationAll",companyDao.getCompanyUrlList(userId,"all").size());
			/*model.addAttribute("companyLastHour",homeDao.getQueryTime("companyData", "1", userId));
			model.addAttribute("companyTotalHour",homeDao.getQueryTime("companyData", "8", userId));
			*/			
			
			/* Start List Building  */
		 
			String[] empId = {"G002","E00591","E00592","E00593","E00469","E00590","E00652","E00662","E00112","E00422", "E00468","E00588", "E00101", "E00471", "E00014", "E00017","E00069","E00127","E00205","E00207","E00198","E00044","E00246","E00001","E00063"};
			boolean showList = false; 
			for (int i = 0; i < empId.length; i++) {
				if(session.getAttribute("userName").toString().equalsIgnoreCase(empId[i])) {
					showList = true;
				}
			}
			if(showList) {
				model.addAttribute("listBuildVerificationActive",listBuildingDao.getListBuildingUrlList(userId,"active").size());
				model.addAttribute("listBuildVerificationApproved",homeDao.getTotalCount(userId,"listBuild"));
				model.addAttribute("listBuildVerificationAll",listBuildingDao.getListBuildingUrlList(userId,"all").size());
				model.addAttribute("listBuildLastHour",homeDao.getQueryTime("listBuild", "1", userId));
				model.addAttribute("listBuildTotalHour",homeDao.getQueryTime("listBuild", "8", userId));	
			}
			
			/* End List Building */ 
			/*User user = homeDao.checkValidUser("", "","user",userId);
			model.addAttribute("userDetail",user);
			
			session.setAttribute("approvedLink", user.getApprovedLink());
			/* session.setAttribute("approvedLink2", user.getApprovedLink2());
			 session.setAttribute("approvedLink3", user.getApprovedLink3());
			 session.setAttribute("companyLink", user.getCompanyLink());*/
			

			System.out.println("This is dashboard  "+userId);
			return "admin/dashboard";	
		}else {
			return "redirect:login";	
		}
		
	}
	
	
	@RequestMapping(value="updateLinkScore" , method = RequestMethod.POST)
	@ResponseBody public String updateLinkScore(HttpSession session,HttpServletRequest request) {
		if(session.getAttribute("userId")!=null) {
			int userId = Integer.parseInt(session.getAttribute("userId")+"");
			return homeDao.updateLinkScore(userId, request.getParameter("total"),request.getParameter("action"))+"";
		}
		return "false";
	}
	
	@RequestMapping(value="userVerificationLog")
	public String userVerificationLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("total",homeDao.getTotalCount(userId,"scrap"));		
		model.addAttribute("getScrap", homeDao.getScrapData(userId));		
		
		System.out.println("This is user_verification_log  "+userId);
		return "admin/user_verification_log";
	}
	
	@RequestMapping(value="userProfileLog")
	public String userProfileLog(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("total",homeDao.getTotalCount(userId,"listContacts"));		
		model.addAttribute("getContacts", homeDao.getListContacts(userId));		
		
		System.out.println("This is user_verification_log  "+userId);
		return "admin/user_profile_verification_log";
	}
	
	@RequestMapping(value="userVerificationMissed")
	public String userVerificationMissed(Model model,HttpSession session)
	{ 
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",homeDao.getUrlList(userId,"missed"));	
		System.out.println("This is user_verification_missed  "+userId);
		return "admin/user_verification_missed";
	}
	
	@RequestMapping(value="userProfileMissed")
	public String userProfileMissed(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",homeDao.getProfileUrlList(userId,"missed"));	
		System.out.println("This is userProfileMissed  "+userId);
		return "admin/user_profile_missed";
	}

	
	
	@RequestMapping(value="category")
	public String category(Model model)
	{
		model.addAttribute("categoryList",homeDao.getCategory());
		
		System.out.println("This is category"+homeDao.getCategory().size());
		return "admin/manageCategory";
	}
	
	@RequestMapping(value="manageCategory")
	public String manageCategory(Model model)
	{
			
		System.out.println("This is dashboard"+homeDao.getCategory().size());
		return "admin/category";
	}
	
	@RequestMapping(value="categoryInsert")
	public String categoryInsert(Category category) {
		
		
		System.out.println("Categoty :::"+category.getCatName());
		
		homeDao.categoryInsert(category);
		 
		return "redirect:category";
	}
	
	
	@RequestMapping(value="report")
	public String createreport(HttpServletRequest request,HttpServletResponse response) {
		
		
		
		return "admin/adminreport";
	}
	
	@RequestMapping(value="downloadReport")
	@ResponseBody public void downloadReport(HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println(request.getParameter("action") +"--"+request.getParameter("exportStartDate")+" --- "+request.getParameter("exportEndDate"));
		
	   String csvFileName = "Export_Data.csv";
	   String headerKey = "Content-Disposition";
       String headerValue = String.format("attachment; filename=\"%s\"",csvFileName);
       response.setHeader(headerKey, headerValue);
	  
       // uses the Super CSV API to generate CSV data from the model data
       ICsvBeanWriter csvWriter;
       
       String action = request.getParameter("action");
       
       if(action.equalsIgnoreCase("scrap")) {
    	   csvFileName = "User_Verification.csv";
    	   try {
   			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
   			String[] header = { "scrapId","name","current_org","current_position","location","url","ipaddress","user_id","contact_url","url_id","created_date"};
   	        csvWriter.writeHeader(header);
   	        List<Scrap> tempUrls = homeDao.exportScrapData(request.getParameter("exportStartDate"),request.getParameter("exportEndDate"));
   	        for (Scrap aBook : tempUrls) {
   	            csvWriter.write(aBook, header);
   	        }
   	     csvWriter.close();
			
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
    	   
       }else if(action.equalsIgnoreCase("companyDetails")) {
    	   	csvFileName = "Company_Data.csv";
    	   
    	   try {
      			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
      			String[] header = { "company_id","company_name","company_location","employee_count","company_url","company_headquater","year_founded","company_size","company_speciality","url","url_id","user_id","company_li_id","locationCount","company_type","company_stock_name","company_industry","phone_number","ipaddress","created_date"};
      	        csvWriter.writeHeader(header);
      	        List<CompanyDetails> tempUrls = companyDao.exportCompanyData(request.getParameter("exportStartDate"),request.getParameter("exportEndDate"));
      	        for (CompanyDetails aBook : tempUrls) {
      	            csvWriter.write(aBook, header);
      	        }
      	     csvWriter.close();
   			
      		} catch (IOException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
    	   
    	   
       }else if(action.equalsIgnoreCase("listBuilding")) {
    	   csvFileName = "List_Build_Data.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "listId","name","new_link","company_link","company_name","company_tenure","contact_location","contact_designation","url","url_id","user_id","sales_data","total_result_no","total_changed_job_no","created_date"};
     	        csvWriter.writeHeader(header);
     	        List<ListBuilding> tempUrls = listBuildingDao.exportListBuilding(request.getParameter("exportStartDate"),request.getParameter("exportEndDate"));
     	        for (ListBuilding aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
  			
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
    	   
       }else if(action.equalsIgnoreCase("masterScrap")) {
    	   
    	   csvFileName = "Master_User_Verification.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "urlId","url","userId","status"};
     	        csvWriter.writeHeader(header);
     	        List<MasterURL> tempUrls = homeDao.exportMasterURL();
     	        for (MasterURL aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
  			
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
    	   
       }else if(action.equalsIgnoreCase("masterCompany")) {
    	   csvFileName = "Master_Company_Details.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "companyUrlId","url","userId","status"};
     	        csvWriter.writeHeader(header);
     	        List<MasterCompanyURL> tempUrls = companyDao.exportMasterCompanyData();
     	        for (MasterCompanyURL aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }else if(action.equalsIgnoreCase("masterListBuild")) {
    	   csvFileName = "Master_List_Build.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "listBuildUrlId","url","userId","status"};
     	        csvWriter.writeHeader(header);
     	        List<MasterListBuildingURL> tempUrls = listBuildingDao.exportMasterListBuilding();
     	        for (MasterListBuildingURL aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }else if(action.equalsIgnoreCase("companyLocations")) {
    	   
    	   String exportUrlList = request.getParameter("exportUrlList");
    	   
    	   csvFileName = "Master_List_Build.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "company_location_id","company_id","country","geographic_area","postal_code","description","city","headquarter","line1","line2","li_id"};
     	        csvWriter.writeHeader(header);
     	        List<CompanyLocation> tempUrls = companyDao.exportCompanyLocations(exportUrlList);
     	        for (CompanyLocation aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }else if(action.equalsIgnoreCase("masterGoogleData")) {
    	   csvFileName = "Master_List_Build.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "urlId","url","userId","status"};
     	        csvWriter.writeHeader(header);
     	        List<MasterGoogleURL> tempUrls = propertyDao.exportMasterGoogleUrlList();
     	        for (MasterGoogleURL aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }else if(action.equalsIgnoreCase("masterGoogleMaps")) {
    	   csvFileName = "Master_List_Build.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "urlId","url","userId","status"};
     	        csvWriter.writeHeader(header);
     	        List<MasterMapsURL> tempUrls = mapsDao.exportMapsDataUrlList();
     	        for (MasterMapsURL aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }else if(action.equalsIgnoreCase("masterYelpData")) {
    	   csvFileName = "Master_List_Build.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "urlId","url","userId","status"};
     	        csvWriter.writeHeader(header);
     	        List<MasterYelpURL> tempUrls = yelpDao.exportMasterYelpUrlList();
     	        for (MasterYelpURL aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }else if(action.equalsIgnoreCase("masterFullDetails")) {
    	   csvFileName = "masterFullDetails.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "masterUrlId","url","userId","status"};
     	        csvWriter.writeHeader(header);
     	        List<MasterURLProfile> tempUrls = homeDao.exportMasterURLProfile();
     	        for (MasterURLProfile aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }else if(action.equalsIgnoreCase("fullDetails")) {
    	   csvFileName = "fullDetails.csv";
      	   try {
     			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
     			String[] header = { "full_name","head_title","head_location","company_name","university","university_url","current_title","current_company","current_company_link","current_duration_start","current_duration_end","current_location","past_title","past_company","past_company_link","past_duration_start","past_duration_end","past_location","url","user_id","url_id","createdDate"};
     	        csvWriter.writeHeader(header);
     	        List<ListContacts> tempUrls = homeDao.exportListContacts(request.getParameter("exportStartDate"),request.getParameter("exportEndDate"));
     	        for (ListContacts aBook : tempUrls) {
     	            csvWriter.write(aBook, header);
     	        }
     	     csvWriter.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
       }
       
       
		
	}
	
	@RequestMapping(value="reset")
	public String reset(HttpServletRequest request,HttpServletResponse response) {
		
		
		
		return "admin/adminreset";
	}
	
	@RequestMapping(value="resetLinks")
	@ResponseBody public String resetLinks(HttpServletRequest request) {
		
		String action = request.getParameter("reportList");
		System.out.println(request.getParameter("masterIds")+"--"+action);
		return homeDao.reActiveMasterURL(request.getParameter("masterIds"), action)+"";	
		
	}
	
	/* End Admin controller */
	
	 
}

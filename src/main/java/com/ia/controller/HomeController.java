package com.ia.controller;

import java.net.SocketException;
import java.net.UnknownHostException;

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

import com.ia.Dao.HomeDao;
import com.ia.modal.Category;
import com.ia.modal.ListContacts;
import com.ia.modal.Scrap;
import com.ia.modal.User;

@Controller
public class HomeController {

	@Autowired	 
	HomeDao homeDao;
	
	@RequestMapping(value="/")
	public String home(Model model,HttpSession session) {
		

		
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
	
	@RequestMapping(value="signup")
	public String signup(HttpServletRequest request,HttpSession session)
	{
		
		User user = new User();
		user.setFname(request.getParameter("fname"));
		user.setLname(request.getParameter("lname"));
		user.setPassword(request.getParameter("pass"));
		user.setUserEmail(request.getParameter("email"));
		
		if(homeDao.insert(user)) {
			session.setAttribute("username", user.getFname() +" "+ user.getLname());
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="signout")
	public String signout(HttpSession session)
	{
		
		session.setAttribute("username", null);
		return "redirect:/";
				
				
	}
	
	@CrossOrigin
	@RequestMapping(value="getData")
 	@ResponseBody public String getData(Scrap scrap,HttpServletRequest request,HttpSession session) throws UnknownHostException, SocketException
	{
	        if(scrap.getUser_id()==null || scrap.getUser_id().equalsIgnoreCase("0") || scrap.getUser_id().equalsIgnoreCase("")) {
	        	scrap.setUser_id("1");
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

	@RequestMapping(value="whatsup")
	public String whatsup()
	{
		return "front/whatsup";
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
		System.out.println("This is admin dashboard -- "+homeDao.getUserDetails().size());
		return "admin/admindashboard";
	}
	
	@RequestMapping(value="userUrl")
	public String userUrl(HttpServletRequest reques,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",homeDao.getUrlList(userId,"active"));
		
		model.addAttribute("userLastHour",homeDao.getQueryTime("scrap", "1", userId));
		model.addAttribute("userTotalHour",homeDao.getQueryTime("scrap", "8", userId));
		
		return "admin/user_url";
	}
	
	
	@RequestMapping(value="userProfile")
	public String userProfile(HttpServletRequest reques,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		model.addAttribute("urlList",homeDao.getProfileUrlList(userId,"active"));
		
		model.addAttribute("userProfileLastHour",homeDao.getQueryTime("listContacts", "1", userId));
		model.addAttribute("userProfileTotalHour",homeDao.getQueryTime("listContacts", "8", userId));
		
		return "admin/user_profile";
	}
	
	@RequestMapping(value="dashboard")
	public String dashboard(HttpServletRequest requestm,Model model,HttpSession session)
	{
		int userId = Integer.parseInt(session.getAttribute("userId")+"");
		
		
		model.addAttribute("userVerificationActive",homeDao.getUrlList(userId,"active").size());
		model.addAttribute("userVerificationApproved",homeDao.getTotalCount(userId,"scrap"));
		model.addAttribute("userVerificationAll",homeDao.getUrlList(userId,"all").size());
		model.addAttribute("getScrap", homeDao.getScrapData(userId));		
		model.addAttribute("userLastHour",homeDao.getQueryTime("scrap", "1", userId));
		model.addAttribute("userTotalHour",homeDao.getQueryTime("scrap", "8", userId));
		
		
		
		model.addAttribute("userProfileActive",homeDao.getProfileUrlList(userId,"active").size());
		model.addAttribute("userProfileApproved",homeDao.getTotalCount(userId,"listContacts"));
		model.addAttribute("userProfileAll",homeDao.getProfileUrlList(userId,"all").size());
		
		
		model.addAttribute("userProfileLastHour",homeDao.getQueryTime("listContacts", "1", userId));
		model.addAttribute("userProfileTotalHour",homeDao.getQueryTime("listContacts", "8", userId));
		
		
		System.out.println("This is dashboard  "+userId);
		return "admin/dashboard";
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
	public String userVerificationMissed(HttpServletRequest requestm,Model model,HttpSession session)
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
		System.out.println("This is user_verification_missed  "+userId);
		return "admin/user_profile_missed";
	}

	
	
	@RequestMapping(value="category")
	public String category(Model model)
	{
		model.addAttribute("categoryList",homeDao.getCategory());
		
		System.out.println("This is dashboard"+homeDao.getCategory().size());
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
	
	/* End Admin controller */
	
}

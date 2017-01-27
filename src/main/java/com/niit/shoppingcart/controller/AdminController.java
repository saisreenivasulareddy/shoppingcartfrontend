package com.niit.shoppingcart.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niit.shoppingcartbackend.dao.CategoryDAO;
import com.niit.shoppingcartbackend.dao.ProductDAO;
import com.niit.shoppingcartbackend.dao.SupplierDAO;
import com.niit.shoppingcartbackend.dao.UserDAO;
import com.niit.shoppingcartbackend.model.Category;
import com.niit.shoppingcartbackend.model.Product;
import com.niit.shoppingcartbackend.model.Supplier;
import com.niit.shoppingcartbackend.model.User;

@Controller
public class AdminController {
	private static Logger log = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value ="Admin" )
	public ModelAndView AdminPage() {
		ModelAndView mv= new ModelAndView("/Admin");
		mv.addObject("showadminPage", "true");
		return mv;
}
	@RequestMapping(value = "/login_session_attributes")
	public String login_session_attributes(HttpSession session) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userDAO.get(username);
		session.setAttribute("userid", user.getId());
		session.setAttribute("name", user.getUsername());
		session.setAttribute("LoggedIn", "true");

		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
		.getAuthentication().getAuthorities();
		String role="ROLE_USER";
		for (GrantedAuthority authority : authorities) 
		{
		  
		     if (authority.getAuthority().equals(role)) 
		     {
		    	 session.setAttribute("UserLoggedIn", "true");
		    	// session.setAttribute("cartsize",cartDAO.getsize((int) session.getAttribute("userid")));
		    	 return "redirect:/";
		     }
		     else 
		     {
		    	 session.setAttribute("Administrator", "true");
			 return "/Admin";
		     }
	}
		return "/home";
	
	}
	@RequestMapping(value="/loginError")
	public String errorpage(RedirectAttributes attributes){
		attributes.addFlashAttribute("error", "The Username or Password you entered is incorrect please try again");
		return "redirect:/login";
	}
	
	
	

}
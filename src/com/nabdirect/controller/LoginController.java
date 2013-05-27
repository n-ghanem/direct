/**
 * 
 */
package com.nabdirect.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginPage(@RequestParam(value="error", required=false) boolean error,
			@RequestParam(value="openid", defaultValue="") String openid,@RequestParam(value="accountId", defaultValue="unknown") String accountID,ModelMap modelMap) {		
		ModelAndView modelNview = new ModelAndView();
		modelNview.addObject("userID", accountID);
		modelNview.addObject("openid_url", openid);		
		if (error || accountID.equals("invalid")) {
			modelNview.addObject("errorMessage", "Unsuccessful, Invalid Open ID");
			modelNview.addObject("userID", "");
		} else {
			modelNview.addObject("errorMessage","Log In Successfull!");
		}
		
		return modelNview;
	}
}
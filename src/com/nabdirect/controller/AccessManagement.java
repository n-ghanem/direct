package com.nabdirect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nabdirect.helper.OAuthHandler;
import com.nabdirect.helper.Helper;
import com.nabdirect.helper.XMLProcessor;

@Controller
public class AccessManagement {
	OAuthHandler oauth = new OAuthHandler();
	Helper helper=new Helper();
	XMLProcessor xmlProcessor= new XMLProcessor();
	
	@RequestMapping(value="/assign", method=RequestMethod.GET) 
	public @ResponseBody String assign(@RequestParam(value="token", defaultValue="") String token){
		String evBody= "401";
		OAuthHandler oauth= new OAuthHandler();
		evBody= oauth.getEvent(token);	
		if(!evBody.equals("401")){
		String type=xmlProcessor.getEventType(evBody);
		if (type.equals(Helper.USER_ASSIGNMENT)){
			return helper.assignUser(evBody);
		}else{
			String errorMessage="Event Type "+type+"is not configured";
			String errorCode="CONFIGURATION_ERROR";
			
			return helper.getErrorTemplate(errorCode,errorMessage);
			
		 }
		}else{
			String errorMessage="UNAOTHOURIZED";
			String errorCode="CONFIGURATION_ERROR";
			
			return helper.getErrorTemplate(errorCode,errorMessage);	
			}
		}
	
	
	@RequestMapping(value="/unassign", method=RequestMethod.GET) 
	public @ResponseBody String unassign(@RequestParam(value="token", defaultValue="") String token){
		String evBody= "401";
		OAuthHandler oauth= new OAuthHandler();
		evBody= oauth.getEvent(token);	
		if(!evBody.equals("401")){
			String type=xmlProcessor.getEventType(evBody);
			if (type.equals(Helper.USER_UNASSIGNMENT)){
				return helper.unAssignUser(evBody);
			}else{
				String errorMessage="Event Type "+type+"is not configured";
				String errorCode="CONFIGURATION_ERROR";
				
				return helper.getErrorTemplate(errorCode,errorMessage);
				
			}
		}else{
			String errorMessage="UNAOTHOURIZED";
			String errorCode="CONFIGURATION_ERROR";
			
			return helper.getErrorTemplate(errorCode,errorMessage);	
			}
	}
}

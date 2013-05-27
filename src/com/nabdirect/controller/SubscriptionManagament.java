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
public class SubscriptionManagament {
	
	OAuthHandler oauth = new OAuthHandler();
	Helper helper=new Helper();
	XMLProcessor xmlProcessor= new XMLProcessor();
	@RequestMapping(value= "/create", method= RequestMethod.GET)
	public @ResponseBody String subscribeO(@RequestParam(value="token", defaultValue="") String token) {
		String evBody= "401";
		OAuthHandler oauth= new OAuthHandler();		
		evBody= oauth.getEvent(token);
		if(!evBody.equals("401")){
		String type=xmlProcessor.getEventType(evBody);
		if (type.equals(Helper.SUBSCRIPTION_ORDER)){
			return helper.createOrder(evBody);
		}else{
			String errorMessage="Event Type "+type+" is not configured";
			String errorCode="CONFIGURATION_ERROR";
			
		//	return helper.getErrorTemplate(errorCode,errorMessage);
			return helper.getSuccessTemplate(true,"hdfsdfsdf" );
		 }
	}else{
		String errorMessage="UNAOTHOURIZED";
		String errorCode="CONFIGURATION_ERROR";
		
		return helper.getErrorTemplate(errorCode,errorMessage);	
		}
	}
	
	
	@RequestMapping(value= "/change", method= RequestMethod.GET)
	public @ResponseBody String changeO(@RequestParam(value="token", defaultValue="") String token) {

		String evBody= "401";
		evBody= oauth.getEvent(token);
		if(!evBody.equals("401")){
		String type=xmlProcessor.getEventType(evBody);
		if (type.equals(Helper.SUBSCRIPTION_CHANGE)){
			return helper.changeOrder(evBody);
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
	

	@RequestMapping(value= "/cancel", method= RequestMethod.GET)
	public @ResponseBody String cancelO(@RequestParam(value="token", defaultValue="") String token) {
		String evBody= "401";
		OAuthHandler oauth= new OAuthHandler();
		evBody= oauth.getEvent(token);	
		if(!evBody.equals("401")){
		String type=xmlProcessor.getEventType(evBody);
		if (type.equals(Helper.SUBSCRIPTION_CANCEL)){
			
			
			return helper.cancelOrder(evBody);
			
	
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
	
	@RequestMapping(value= "/status", method= RequestMethod.GET)
	public @ResponseBody String statusChecker(@RequestParam(value="token", defaultValue="") String token) {
		String evBody= "401";		
		evBody= oauth.getEvent(token);
		if(!evBody.equals("401")){
		String type=xmlProcessor.getEventType(evBody);
		if ((type.equals(Helper.SUBSCRIPTION_NOTICE) )|| (type.equals(Helper.DEACTIVATED) )|| (type.equals(Helper.REACTIVATED))|| (type.equals(Helper.CLOSED))){
			return helper.statusChecker(evBody);
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

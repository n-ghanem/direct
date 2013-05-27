package com.nabdirect.helper;

public class Helper {
	public static final  String SUBSCRIPTION_ORDER= "SUBSCRIPTION_ORDER";
	public static final  String SUBSCRIPTION_CHANGE= "SUBSCRIPTION_CHANGE";
	public static final  String SUBSCRIPTION_CANCEL= "SUBSCRIPTION_CANCEL";
	public static final  String SUBSCRIPTION_NOTICE="SUBSCRIPTION_NOTICE";
	public static final  String USER_ASSIGNMENT= "USER_ASSIGNMENT";
	public static final  String USER_UNASSIGNMENT= "USER_UNASSIGNMENT";
		
	public static final  String DEACTIVATED= "DEACTIVATED";
	public static final  String REACTIVATED= "REACTIVATED";
	public static final  String CLOSED="CLOSED";
	
	XMLProcessor xmlProcessor= new XMLProcessor();

	public String createOrder(String xml) {
		String flag="create";
		boolean success=true;
		String accountID=xmlProcessor.parser(xml,flag);	
		if(accountID.length()!=0){
		  return getSuccessTemplate(success,accountID);	
		}
		else{
			success=false;
			String message="Unknown Error happened";
			String errorCode="UNKNOWN_ERROR";//TODO handle the exact error
			return getErrorTemplate(errorCode,message);
		}
	}
	
	public String changeOrder(String xml) {
		String flag="change";
		boolean success=true;
		xmlProcessor.parser(xml,flag);	
		return	getSuccessTemplate1(success);
		
	}
	
	public String cancelOrder(String xml) {
		String flag="cancel";
		boolean success=true;
		String accountID=xmlProcessor.parser(xml,flag);	
		if(accountID.length()!=0){
			  return getSuccessTemplate(success,accountID);	
			
			}
			else{
				success=false;
				String message="Unknown Error happened";
				String errorCode="UNKNOWN_ERROR";//TODO handle the exact error
				return getErrorTemplate(errorCode,message);
		}	
	}
	
	public String statusChecker(String xml) {
		String flag="status";
		boolean success=true;
		String accountID= xmlProcessor.parser(xml,flag);	//handle if its true
		if(accountID.length()!=0){
			  return getSuccessTemplate(success,accountID);	
			}
			else{
				success=false;
				String message="Unknown Error happened";
				String errorCode="UNKNOWN_ERROR";//TODO handle the exact error
				return getErrorTemplate(errorCode,message);
		}		
	}
	
	public String unAssignUser(String xml) {
		String flag="anassign";
		boolean success=true;
		xmlProcessor.parser(xml,flag);	//handle if its true
   	   return	getSuccessTemplate1(success);
   	  
	}
	
	public String assignUser(String xml) {
		String flag="assign";
		boolean success=true;
		String accountID= xmlProcessor.parser(xml,flag);	//handle if its true
		if(accountID.length()!=0){
			  return getSuccessTemplate(success,accountID);	
			}
			else{
				success=false;
				String message="Unknown Error happened";
				String errorCode="UNKNOWN_ERROR";//TODO handle the exact error
				return getErrorTemplate(errorCode,message);
		}		
	}
	
	
	
	
	public String getErrorTemplate(String errorCode,String errorMessage){
		String  errorTemplate=
				"<result>" +
				    "<success>false</success>" +
				    "<errorCode>"+errorCode+"</errorCode>" +
				    "<message>"+errorMessage+"</message>" +
				"</result>";
		return errorTemplate;
	}
	
	public String getSuccessTemplate(boolean success,String accId ){
		String successTemplate=
				"<result>" +
				    "<success>"+success+"</success>" +
					"<accountIdentifier>"+accId+"</accountIdentifier>"+
				"</result>"; 
		
		return successTemplate;
	}
	private String getSuccessTemplate1(boolean success){
		return "<result><success>"+success+"</success></result>";
	}

}

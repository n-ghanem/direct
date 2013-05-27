package com.nabdirect.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class DataCenter {
	private ConcurrentHashMap<String, ArrayList<HashMap<String, String>>> dataHashMap;
	ArrayList<HashMap<String,String>> tempList;
	private static DataCenter dataObject;
	private DataCenter() {
		dataHashMap=new ConcurrentHashMap<String,ArrayList<HashMap<String,String>>>();
	}
	
	public static synchronized DataCenter getSingletonObject() {
		if (dataObject == null) {
			  dataObject = new DataCenter();
		}
		return dataObject;
	}
	
	public void setData(String accountIdenfier,ArrayList<HashMap<String,String>> ar){
			tempList=new ArrayList<HashMap<String, String>>();
			if (dataHashMap.containsKey(accountIdenfier)){
				tempList=dataHashMap.get(accountIdenfier);
				tempList.addAll(ar);
				dataHashMap.put(accountIdenfier,tempList);
			}else{
				dataHashMap.put(accountIdenfier,ar);
			}
    }
	
	public boolean hasAccountIdentifier(String accountIdenfier){
		//check if it has the id in data center
		boolean hasIt=true;		
		if (dataHashMap.containsKey(accountIdenfier)) 
			return true;				
		
		return hasIt;
	}
	
	public boolean cancelSubs(String accountIdenfier){
		boolean isCanceled=true;
	    if (dataHashMap.containsKey(accountIdenfier))
	    	dataHashMap.remove(accountIdenfier);		
		
		return isCanceled;		
	}	
   
	public void changeOrder( String accountIdentifier,String editionCode){

		tempList=new ArrayList<HashMap<String, String>>();
		if (dataHashMap.containsKey(accountIdentifier)){
			tempList=dataHashMap.get(accountIdentifier);
			for (HashMap<String,String> th: tempList){
				if (th.containsKey("editionCode")){
					th.put("editionCode", editionCode);
					
				}else{
					th.put("editionCode", editionCode);
				}
				
			}
			
		}
	}
}

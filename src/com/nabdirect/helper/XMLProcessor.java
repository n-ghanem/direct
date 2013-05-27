package com.nabdirect.helper;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLProcessor {
	private String event_type= "";
	private String subscrition= "";
	//creator
	private String email= "";	
	private String firstName="";	
	private String lastName="";
	private String openId="";
	private String language="";
	private String noticeType="";
	private String status="";
	private String editionCode="";
	
	private String accountIdentifier="";
	
    private final String PAYLOAD="payload";
	
	private final String ACCOUNT="account";
	private final String ORDER="order";
	private final String STATUS="status";
	private final String CREATOR="creator";
	private final String NOTICE="notice";
	
    private String uuid="";
	
	private final String EMAIL="email";	
	private final String FIRST_NAME="firstName";
	private final String LAST_NAME="lastName";
	private final String OPEN_ID="openId";
	private final String LANGUAGE="language";
	private final String UUID="uuid";
	private final String ACCOUNTIDENTIFIER="accountIdentifier";
	private final String COMPANY="compnay";
	private final String EDITION_CODE="editionCode";
	private final String USER="user";	
	
	
	
	private final String TYPE= "type";
	
	ArrayList<HashMap<String,String>> userStuff;
	
	DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = null;
	StringReader sReader = null;

	public XMLProcessor() {
		
	}
	
	public void setPayLoad(NodeList payloadLN){
		int length=payloadLN.item(0).getChildNodes().getLength();
		for (int i=0; i< length;i++){
			Node node=payloadLN.item(0).getChildNodes().item(i);
			String node_name=node.getNodeName();
			if(node_name.equals(ACCOUNT)){	
				NodeList accountNL= node.getChildNodes();
				for (int k=0;k<accountNL.getLength();k++){
					if(ACCOUNTIDENTIFIER.equals(accountNL.item(k).getNodeName())){
						accountIdentifier=accountNL.item(k).getTextContent();
					}
					if(STATUS.equals(accountNL.item(k).getNodeName())){
						status=accountNL.item(k).getTextContent();
					}
				}				
			}
			if(node_name.equals(NOTICE)){// for notice
				NodeList noticeNL= node.getChildNodes();
				for (int k=0;k<noticeNL.getLength();k++){
					if(TYPE.equals(noticeNL.item(k).getNodeName())){
					noticeType=noticeNL.item(k).getTextContent();
					}
			  }
			}
			if(node_name.equals(ORDER)){//account change
				NodeList orderNL= node.getChildNodes();
				for (int k=0;k<orderNL.getLength();k++){
					if(EDITION_CODE.equals(orderNL.item(k).getNodeName())){
					   editionCode= orderNL.item(k).getTextContent();
					}
			  }
			}
			if(node_name.equals(COMPANY)){//create account
				NodeList companyNL= node.getChildNodes();
				//TODO parse 
			}
			if(node_name.equals(USER)){//create account for the assignee user and put them in arraylist
				NodeList userNL= node.getChildNodes();
				userStuff= setCreator(userNL, false);
				
			}
			
			
		}
	}
	
	public ArrayList<HashMap<String,String>> setCreator(NodeList creatorLN, Boolean isAccID){
		ArrayList<HashMap<String,String>> ar=new ArrayList<HashMap<String,String>>();
		HashMap<String,String> tempMap=new HashMap<String,String>();
		
		int length=creatorLN.item(0).getChildNodes().getLength();
		for (int i=0; i< length;i++){
			Node node=creatorLN.item(0).getChildNodes().item(i);	
			String node_name=node.getNodeName();
			if(node_name.equals(EMAIL)){
				email=node.getTextContent();
				if(email.length()>0){
				tempMap.put(EMAIL, email);
            	ar.add(tempMap);
            	tempMap.clear();
				}
			}
            if(node_name.equals(FIRST_NAME)){
            	firstName=node.getTextContent();
            	if(firstName.length()>0){
            	tempMap.put(FIRST_NAME, firstName);
            	ar.add(tempMap);
            	tempMap.clear();
            	}
			}
			if(node_name.equals(LAST_NAME)){
				lastName=node.getTextContent();		
						}
			if(node_name.equals(OPEN_ID)){
				openId=node.getTextContent();
				if(openId.length()>0){
				tempMap.put(OPEN_ID, openId);
            	ar.add(tempMap);
            	tempMap.clear();
				}
			}
			if(node_name.equals(LANGUAGE)){
				language=node.getTextContent();
				if(language.length()>0){
				tempMap.put(LANGUAGE, language);
            	ar.add(tempMap);
            	tempMap.clear();
				}
			}
			if(node_name.equals(UUID)){				
				uuid=node.getTextContent();
				if(uuid.length()>0){
				tempMap.put(UUID, uuid);
            	ar.add(tempMap);
            	tempMap.clear();
				}
			}
			if(isAccID){
			String ss=firstName+lastName;
			accountIdentifier=email+"_"+ss.length();
			}
		}
		return ar;
	}
	
	
	public String getEventType(String appdirXML){
		String eventType="";
		try {
			sReader= new StringReader(appdirXML);
			InputSource inputSource = new InputSource(sReader);
			docBuilder= docFactory.newDocumentBuilder();
			Document doc= docBuilder.parse(inputSource);
			NodeList nList= doc.getElementsByTagName(TYPE);
			if (nList.getLength() >= 1){
				eventType= nList.item(0).getTextContent();
			}
		} catch (ParserConfigurationException ignored) {
		} catch (SAXException ignored) {
		} catch (IOException ignored) {
		}  finally {
			if (sReader != null){
				sReader.close();
			}
		}
		return eventType;
	}
	
	
	
	public String parser(String appdirXML,String flag){
		String accountid="";
		try {
			sReader= new StringReader(appdirXML);
			InputSource inputSource = new InputSource(sReader);
			docBuilder= docFactory.newDocumentBuilder();
			Document doc= docBuilder.parse(inputSource);

			
			if(flag.equals("create")){//parse and save in db
				NodeList creatorNL = doc.getElementsByTagName(CREATOR);			
				ArrayList<HashMap<String,String>> ar=setCreator(creatorNL,true);						
				DataCenter dc=DataCenter.getSingletonObject();
				accountid=accountIdentifier;
				dc.setData(accountIdentifier, ar);
			}
			
			if(flag.equals("change")){
				NodeList payloadNL = doc.getElementsByTagName(PAYLOAD);
				setPayLoad(payloadNL);//will get the editionCode and account identifier
				DataCenter dc=DataCenter.getSingletonObject();
				if (dc.hasAccountIdentifier(accountIdentifier)){// if the accoutnID existi in db
					dc.changeOrder(accountIdentifier,editionCode);//update editioncode
					
						accountid=accountIdentifier;
				}
									
				
			}						
			if(flag.equals("cancel")){//parse payload and delete from db
				NodeList payloadNL = doc.getElementsByTagName(PAYLOAD);
				setPayLoad(payloadNL);
				DataCenter dc=DataCenter.getSingletonObject();
				boolean isCanceled=dc.cancelSubs(accountIdentifier);
				if(isCanceled){
					accountid=accountIdentifier;
				}				
				
			}
			
			if(flag.equals("status")){
				NodeList payloadNL = doc.getElementsByTagName(PAYLOAD);
				setPayLoad(payloadNL);
				DataCenter dc=DataCenter.getSingletonObject();
				if (dc.hasAccountIdentifier(accountIdentifier)){// if the accoutnID exist in db
					//TODO
					// will get the status and the notice type
					//update in dc
					
					accountid=accountIdentifier;				
				
			}	
			}
			
			
			if(flag.equals("assign")){
				NodeList payloadNL = doc.getElementsByTagName(PAYLOAD);
				setPayLoad(payloadNL);//will set the accountIdentifier and userstuff arraylist
				DataCenter dc=DataCenter.getSingletonObject();
							
				accountid=accountIdentifier;
				dc.setData(accountIdentifier, userStuff);
			}
			
			if(flag.equals("unassign")){
				NodeList payloadNL = doc.getElementsByTagName(PAYLOAD);
				setPayLoad(payloadNL);
				DataCenter dc=DataCenter.getSingletonObject();
				boolean isUnassigned=dc.cancelSubs(accountIdentifier);
				if(isUnassigned){
					accountid=accountIdentifier;
				}
			}
			
			
		} catch (ParserConfigurationException ignore) {
		} catch (SAXException ignore) {
		} catch (IOException ignore) {
		}  finally {
			if (sReader != null){
				sReader.close();
			}
		}
		return accountid;
	}

}
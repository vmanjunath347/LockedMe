package com.lockers.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class UserProccess {
	
	private String username;
	private HashMap<String, String> userDatabase = new HashMap <String,String>();
	
	public  void userProcesses(Login loginObj) {
		
		String loginAttemptStatus=loginObj.getLoginAttempt();
		
		if(loginAttemptStatus.equals("Failed"))
			return;
		
		username=loginObj.getUsername();
		
		distplayUserOptions();
		operations();
		
	}
	
	private void distplayUserOptions() {
		System.out.println("\n-----------------------------------------------");
		System.out.println("Enter 1 to add a new website credential");
		System.out.println("Enter 2 to add an existing website credential");
		System.out.println("-----------------------------------------------");
		
	}
	
	private void operations() {
		
		Scanner input=new Scanner(System.in);
		String inputNumber = input.nextLine();
		
		switch(inputNumber) {
		case "1":
			addNewWebsite(input);
			break;
		case "2":
			deleteWebsite(input);
			break;
		default:
			System.out.println("Invalid input");
		}
		
	}
	
	private void deleteWebsite(Scanner input) {
		System.out.println("Enter the website name");
		String website=input.nextLine();
		
		if(website.equals("")) {
			System.out.println("Website name is empty");
			return;
		}
		
		try {
			getUserDataBase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(websiteAlreadyExists(website)==false){
			System.out.println("Website not found");
			return;
		}
		else {
			deleteWebsiteFromDatbase(website);
		}
	}
	
	private void addNewWebsite(Scanner input1) {
		
		System.out.println("Enter the website name");
		//Scanner input1= new Scanner(System.in);
		
		String website=input1.nextLine();
		
		try {
			getUserDataBase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(website.equals("")) {
			System.out.println("Website name is empty");
			return;
		}
		
		try {
			getUserDataBase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(websiteAlreadyExists(website)==true){
			System.out.println("Website already exists in database");
			return;
		}
		
		System.out.println("Enter wesite's password");
		String password1=input1.nextLine();
		
		if(password1.equals("")) {
			System.out.println("Password cannot be empty");
			return;
		}
		
		System.out.println("Enter website's password once again");
		String password2=input1.nextLine();
		
		if(password1.equals(password2)) {
			addwebsiteDetails(website,password2);
			System.out.println("Website details entered sussessfully");
		}
		else
			System.out.println("website's passwords entered doesn't match");
		
	}
	
	private void deleteWebsiteFromDatbase(String website) {
		userDatabase.remove(website);
		overRightusersDatabase();
	}
	
	private boolean websiteAlreadyExists(String website) {
		if(userDatabase.containsKey(website))
			return true;
		return false;
	}
	
	private void addwebsiteDetails(String webSite, String password) {
		File fileObj = new File(username);
		
		FileWriter fileWriter = null;
		
		try {
				if(fileObj.exists()) {
				fileWriter = new FileWriter(fileObj,true);
				Scanner scannerReader = new Scanner(fileObj);
				if(scannerReader.hasNext())
					fileWriter.append("\n"+webSite);
				else
					fileWriter.append(webSite);
				fileWriter.append("\n"+password);
				
			}else {
				throw new FileNotFoundException("File is not Available with name "+fileObj.getName());
			}
			
		}
		catch (IOException e) {
			System.out.println("An Error Occurred");
			//e.printStackTrace();
		} 
		try {
				fileWriter.close();
			} 
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void overRightusersDatabase() {
		File fileObj = new File(username);
		
		FileWriter fileWriter = null;
		
		try {
			if(fileObj.exists()) {
					
				fileWriter = new FileWriter(fileObj);
				
				Iterator<Entry<String, String>> iterator = userDatabase.entrySet().iterator();
				
				int iterationCounter=0;
				
				while(iterator.hasNext()) {
					Map.Entry mapElemnt = (Map.Entry)iterator.next();
					if(iterationCounter!=0)	
						fileWriter.append("\n"+(String) mapElemnt.getKey());
					else	
						fileWriter.append((String) mapElemnt.getKey());
					fileWriter.append("\n"+(String) mapElemnt.getValue());
					iterationCounter++;
				}
				System.out.println("website and password deleted successfully");
				
			}else {
				throw new FileNotFoundException("File is not Available with name "+fileObj.getName());
			}
			
		}
		catch (IOException e) {
			System.out.println("An Error Occurred");
			//e.printStackTrace();
		} 
		try {
				fileWriter.close();
			} 
		catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getUserDataBase() throws FileNotFoundException {
		File fileObj = new File(username);
		try {
			
			if(fileObj.exists()==false)
				fileObj.createNewFile();
			Scanner scannerReader = new Scanner(fileObj);
			int lineCounter=0;
			String tempKey=new String("");
			while(scannerReader.hasNextLine()) {
				if(lineCounter%2==0)
					tempKey=scannerReader.nextLine();
				else if(!tempKey.equals(""))
					userDatabase.put(tempKey, scannerReader.nextLine());
				lineCounter++;
			}
			scannerReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
	}

}

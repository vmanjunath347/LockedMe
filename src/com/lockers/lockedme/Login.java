package com.lockers.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Login {
	
	private String username;
	private String password;
	
	private String inputUserName;
	private String inputPassword;
	
	private String loginAttempt = new String("Failed");
	
	private HashMap<String, String> allCredentials = new HashMap<String,String>();
	
	private void setAllUserCredentionals() {
		File fileObj = new File("database.txt");
		Scanner scannerReader;
		try {
			scannerReader = new Scanner(fileObj);
			int lineCounter=0;
			String tempUser= new String("");
			while(scannerReader.hasNextLine()) {
				
				if(lineCounter%2==0)
					tempUser=scannerReader.nextLine();
				else if(lineCounter%2!=0)
					allCredentials.put(tempUser, scannerReader.nextLine());
				
				lineCounter++;	
			}
			scannerReader.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public void login() {
		System.out.println("==========================================");
		System.out.println("*                                        *");
		System.out.println("*        Welcome to Login Page           *");
		System.out.println("*                                        *");
		System.out.println("==========================================");
		
		System.out.println("Enter your Username:");
		
		Scanner input = new Scanner(System.in);
		inputUserName = input.nextLine();
		
		if(inputUserName.equals("")) {
			System.out.println("Username is empty");
			return;
		}
		
		if(usernameDoesnotExists()==true) {
			System.out.println("Username Invalid");
			return;
		}
		
		System.out.println("Enter your Password:");
		String inputPassword = input.nextLine();
		
		if(inputPassword.equals("")) {
			System.out.println("Password is empty");
			return;
		}
		
		if(passwordCheck()==true) {
			System.out.println("Login Success");
			loginAttempt="Success";
			username=inputUserName;
			password=inputPassword;
			//loginResult.put("password", inputPassword);
			return;
		}
		
		else {
			System.out.println("Incorrect Password");
			return;
		}
		
		
		
	}
	
	private boolean passwordCheck() {
		if(allCredentials.get(inputUserName).equals(inputPassword))
			return true;
		return false;
	}
	
	private boolean usernameDoesnotExists() {
		if(allCredentials.containsKey(inputUserName))
			return false;
		return true;
	}
}

package com.lockers.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

// Handles the login operations of a user
public class Login {
	
	private String username;  //Stores the Confirmed user name;
	private String password;  //Stores the confirmed password;
	
	private String inputUserName; //Stores the user name entered by the user;
	private String inputPassword; //Stores the password entered by the user;
	
	private String loginAttempt = new String("Failed");  //Stores the status of the login attempt
	
	
	//getter method for username
	public String getUsername() {
		return username;
	}

	//getter method for login attempt status
	public String getLoginAttempt() {
		return loginAttempt;
	}

	//stores all the web sites as key and user name and password in an array as value
	private HashMap<String, String> allCredentials = new HashMap<String,String>(); 
	
	//fetches all user credentials from database file to hashmap allCredentials
	private void setAllUserCredentionals() {
		File fileObj = new File("database.txt");
		Scanner scannerReader;
		try {
			if(fileObj.exists()==false)
				fileObj.createNewFile();
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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//deals with the login operations at the user end
	public void login() {
		System.out.println("==========================================");
		System.out.println("*                                        *");
		System.out.println("*        Welcome to Login Page           *");
		System.out.println("*                                        *");
		System.out.println("==========================================");
		
		System.out.println("Enter your Username:");
		
		Scanner input = new Scanner(System.in);
		inputUserName = input.nextLine();// input user name
		
		//if username is blank, return
		if(inputUserName.equals("")) {
			System.out.println("Username is empty");
			tryAgaintoLogin(input);// runs log in operation again
			return;
		}
		
		setAllUserCredentionals();//fetches all user credentials from database file to hashmap allCredentials
		
		//if username already exists in database returns
		if(usernameDoesnotExists()==true) {
			System.out.println("Username Invalid ");
			tryAgaintoLogin(input);// runs log in operation again
			return;
		}
		
		System.out.println("Enter your Password:");
		inputPassword = input.nextLine();//inputs password
		
		//if input password is empty, returns
		if(inputPassword.equals("")) {
			System.out.println("Password is empty");
			tryAgaintoLogin(input);
			return;
		}
		
		//if password matched, login
		if(passwordCheck()==true) {
			System.out.println("Login Success");
			loginAttempt="Success";// login status is changd to success
			username=inputUserName;// added username is given as final username
			password=inputPassword;// added password is given as final pasdword
			return;
		}
		
		else {
			System.out.println("Incorrect Password");
			tryAgaintoLogin(input);//runs log in again
			return;
		}
		
		
		
	}
	
	//gives an option to run login operation again or exit
	private void tryAgaintoLogin(Scanner input) {
		System.out.println("------------");
		System.out.println("Enter 1 to try again");
		System.out.println("Enter 2 to Exit");
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			login();
			break;
		case "2":
			System.out.println("Exited");
			return;
			//sbreak;
		default:
			System.out.println("Invalid Input");
			tryAgaintoLogin(input);
			//System.out.println("--------------------");
			break;
		}
	}
	
	//checks if the entered password matches the password assigned to that username in hasmap
	private boolean passwordCheck() {	
		
		if(allCredentials.get(inputUserName).equals(inputPassword))
			return true;
		return false;
	}
	
	//checks if a key == username exists in hasmap
	private boolean usernameDoesnotExists() {
		if(allCredentials.containsKey(inputUserName))
			return false;
		return true;
	}
}

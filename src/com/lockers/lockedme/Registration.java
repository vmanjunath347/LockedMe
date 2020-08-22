package com.lockers.lockedme;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

//for methods asociated with registeration
public class Registration {
	
	private HashMap<String, String> allUserCredentails = new HashMap<String,String>();//to store username and passwords of already existing user.
	
	//fetches user name and password from database file and adds them to the hashmap
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
					allUserCredentails.put(tempUser, scannerReader.nextLine());
				
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

	//handles registration operation at user interface
	public void fetchAuthInfo() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("*******************************");
		System.out.println("*                             *");
		System.out.println("*Welcome To Registeration Page*");
		System.out.println("*                             *");
		System.out.println("*******************************");
		
		System.out.println("\n**********Enter User Name**********");
		
		String userName= input.nextLine();
		
		//returns if entered username is null
		if(userName.equals("")) {
			System.out.println("Username is emply.");
			tryAgaintoRegister(input);// runs fetchAuthInfo again
			return;
		}
		
		setAllUserCredentionals();
		
		//check if username already exists
		if(userNameExists(userName)==true) {
			System.out.println("Username already exists.");
			tryAgaintoRegister(input);// runs fetchAuthInfo again
			return;
		}
		
		System.out.println("**********Enter a password**********");
		System.out.println("\nNote: Password should be atleast 6 characters long,");
		System.out.println("should have atleast 1 uppercase character,");
		System.out.println("should have atleast 1 number value.");
		
		String password= input.nextLine();
		
		//if entered password is null, returns
		if(password.equals("")) {
			System.out.println("Password is emply.");
			tryAgaintoRegister(input);// runs fetchAuthInfo again
			return;
		}
		
		//if passes doesnt pass the crieteria, returns
		if(paswordCheck(password)==false) {
			tryAgaintoRegister(input);// runs fetchAuthInfo again
			return;
		}
		System.out.println("Confirm password");
		String password2=input.nextLine();
		
		//if both the password matches registeration happens
		if(password.equals(password2))
			addnewUser(userName,password);
		else {
			System.out.println("Passwords doesn't match");
			tryAgaintoRegister(input);// runs fetchAuthInfo again
		}
	}
	
	// runs fetchAuthInfo again
	private void tryAgaintoRegister(Scanner input) {
		System.out.println("------------");
		System.out.println("Enter 1 to try again");
		System.out.println("Enter 2 to Exit");
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			fetchAuthInfo();
			break;
		case "2":
			System.out.println("Exited");
			return;
			//sbreak;
		default:
			System.out.println("Invalid Input");
			tryAgaintoRegister(input);
			//System.out.println("--------------------");
			break;
		}
	}
	
	// checks password criteria
	private boolean paswordCheck(String psswrd) {
		if(psswrd.length()<6) {
			System.out.println("Password should be atleast 6 characters long");
			return false;
		}
		if(Pattern.matches(".*[0-9].*", psswrd)==false) {
			System.out.println("Password should have atleast 1 integer");
			return false;
		}
		if(Pattern.matches(".*[A-Z].*", psswrd)==false) {
			System.out.println("Password should have atleast 1 uppercase character.");
			return false;
		}
		return true;
	}
	
	//checks if username already exists
	private boolean userNameExists(String user) {
		if(allUserCredentails.containsKey(user))
			return true;
		return false;
	}
	
	//adds new user to the database file
	private void addnewUser(String user,String passwrd) {
		
		File fileObj = new File("database.txt");
		
		FileWriter fileWriter = null;
		
		try {
			if(fileObj.exists()) {
				fileWriter = new FileWriter(fileObj,true);
				Scanner scannerReader = new Scanner(fileObj);
				if(scannerReader.hasNext())
					fileWriter.append("\n"+user);
				else
					fileWriter.append(user);
				fileWriter.append("\n"+passwrd);
				System.out.println("Successfully Registered");
				
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
	
	

}

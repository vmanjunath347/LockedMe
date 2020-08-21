package com.lockers.lockedme;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Registration {
	
	private HashMap<String, String> allUserCredentails = new HashMap<String,String>();
	

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
					allUserCredentails.put(tempUser, scannerReader.nextLine());
				
				lineCounter++;	
			}
			scannerReader.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		
	}

	public void fetchAuthInfo() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("*******************************");
		System.out.println("*                             *");
		System.out.println("*Welcome To Registeration Page*");
		System.out.println("*                             *");
		System.out.println("*******************************");
		
		System.out.println("\n**********Enter User Name**********");
		
		String userName= input.nextLine();
		
		if(userName.equals("")) {
			System.out.println("Username is emply.");
			return;
		}
		
		setAllUserCredentionals();
		
		//check if username already exists
		if(userNameExists(userName)==true) {
			System.out.println("Username already exists.");
			return;
		}
		
		System.out.println("**********Enter a password**********");
		System.out.println("\nNote: Password should be atleast 6 characters long,");
		System.out.println("should have atleast 1 uppercase character,");
		System.out.println("should have atleast 1 integer.");
		
		String password= input.nextLine();
		
		if(password.equals("")) {
			System.out.println("Password is emply.");
			return;
		}
		
		if(paswordCheck(password)==false) {
			return;
		}
		
		addnewUser(userName,password);
		
	}
	
	
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
	
	private boolean userNameExists(String user) {
		if(allUserCredentails.containsKey(user))
			return true;
		return false;
	}
	
	
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

package com.lockers.lockedme;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		
		// displays welcome message
		welcomeScreen();
		//performs and login and registeration operations according to user input
		authentication();
		
	
	}
	
	// displays welcome message
	public static void welcomeScreen() {
		System.out.println("********************************************");
		System.out.println("*                                          *");
		System.out.println("*         Welcome to LockedMe.com          *");
		System.out.println("*           Your personal locker           *");
		System.out.println("********************************************");
	}
	
	//performs and login and registeration operations according to user input
	public static void authentication() {
		
		System.out.println("\n \nEnter 1 to Login");
		System.out.println("Enter 2 to Register");
		System.out.println("Enter 3 to Exit");
		
		Scanner input = new Scanner(System.in);
		
		String inputNumber = input.nextLine();//user input
		
		switch(inputNumber) {
		
		case "1":
			//login
			Login loginObj= new Login();
			loginObj.login();// Handles the login operations of a user
			
			UserProccess processObj = new UserProccess();
			processObj.userProcesses(loginObj);
			break;
			
		case "2":
			//register
			Registration Registration = new Registration();
			Registration.fetchAuthInfo();//handles registeration operation at user interface
			authenticationAfterRegistration();//provides user option to log in after registration or exit program
			break;
		case "3":
			System.out.println("exit success");
			break;
		default:
			System.out.println("Invalid Input. \nRun code again to try again");
			tryagain(input);//runs autentication again
		}
		
		
	}
	
	//provides user option to log in after registration or exit program
    public static void authenticationAfterRegistration() {
		
		System.out.println("\n \nEnter 1 to Login");
		System.out.println("Enter 2 to Exit");
		
		Scanner input = new Scanner(System.in);
		
		String inputNumber = input.nextLine();//user input
		
		switch(inputNumber) {
		
		case "1":
			//login
			Login loginObj= new Login();
			loginObj.login();// Handles the login operations of a user
			
			UserProccess processObj = new UserProccess();
			processObj.userProcesses(loginObj);
			break;
			
		case "2":
			System.out.println("exit success");
			break;
		default:
			System.out.println("Invalid Input. \nRun code again to try again");
			tryagain(input);//runs autentication again
		}
		
		
	}
	
	//runs autentication again
	public static void tryagain(Scanner input) {
		System.out.println("\n---------------------");
		System.out.println("Enter 1 to try again.");
		System.out.println("Enter 2 to exit.");
		
		String userinput= input.nextLine();
			
		switch(userinput) {
		case "1":
			authentication();
			break;
		case "2":
			System.out.println("exit success");
			return;
		default:
			System.out.println("Invalid Input");
		}
	}

}

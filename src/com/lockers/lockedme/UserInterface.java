package com.lockers.lockedme;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		
		welcomeScreen();
		
		authentication();
		
	
	}
	
	public static void welcomeScreen() {
		System.out.println("********************************************");
		System.out.println("*                                          *");
		System.out.println("*         Welcome to LockedMe.com          *");
		System.out.println("*                                          *");
		System.out.println("********************************************");
	}
	
	public static void authentication() {
		
		System.out.println("\n \nEnter 1 to Login");
		System.out.println("Enter 2 to Register");
		
		Scanner input = new Scanner(System.in);
		
		String inputNumber = input.nextLine();
		
		switch(inputNumber) {
		
		case "1":
			//login
			Login loginObj= new Login();
			loginObj.login();
			
			UserProccess processObj = new UserProccess();
			processObj.userProcesses(loginObj);
			break;
			
		case "2":
			//register
			Registration Registration = new Registration();
			Registration.fetchAuthInfo();
			break;
		
		default:
			System.out.println("Invalid Input. \nRun code again to try again");
		}
		
		
	}

}

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

//deals with the tasks a user can perform once login
public class UserProccess {
	
	private String username;// stores the username of the login user
	private HashMap<String, String[]> userDatabase = new HashMap <String,String[]>();// stores the website details of the log in user from the user file
	
	//stores all the web sites as key and user name and password in an array as value
	private HashMap<String, String> allCredentials = new HashMap<String,String>(); 
		
	//this method is called outsie class to run any user process
	public  void userProcesses(Login loginObj) {
		
		String loginAttemptStatus=loginObj.getLoginAttempt();// collect the login attempt status
		
		//return if log in is failed
		if(loginAttemptStatus.equals("Failed"))
			return;
		
		username=loginObj.getUsername();// gets login user name.
		
		distplayUserOptions();// displays the option of tasks available to the user.
		
	}
	
	// displays the option of tasks available to the user.
	private void distplayUserOptions() {
		System.out.println("\n-----------------------------------------------");
		System.out.println("Enter 1 to add a new website credential");
		System.out.println("Enter 2 to remove an existing website credential");
		System.out.println("Enter 3 to change an existing website password");
		System.out.println("Enter 4 to display all website details");
		System.out.println("Enter 5 to logout");
		System.out.println("Enter * to delete your lockedMe account");
		System.out.println("-----------------------------------------------");
		operations(); // performs operation based on users input.
	}
	
	// performs operation based on users input.
	private void operations() {
		
		Scanner input=new Scanner(System.in);
		String inputNumber = input.nextLine();//collects user input
		
		//chooses tast based on user input
		switch(inputNumber) {
		case "1":
			addNewWebsite(input);// adds a new website to the user's database
			break;
		case "2":
			deleteWebsite(input);// deletes a website from user's database
			break;
		case "3":
			changeWebsitePassword(input); // changes password of a selected website in user's database.
			break;
		case "4":
			displayWebsiteDetails(); // diaplays all the websites along with it's username and password in users database
			break;
		case "*":
			delteLockedmeAccount(input); //deletes locked me account
			break;
		case "5":
			System.out.println("Log out successfull");
			return;
		default:
			System.out.println("Invalid input");
		}
		
	}
	
	//deletes locked me account
	private void delteLockedmeAccount(Scanner input) {
		System.out.println("press 1 to confirm account deletion");
		System.out.println("press 2 to go back to the main menu");
		System.out.println("press 3 to Logout");
		
		String userInput = input.nextLine();
		switch(userInput) {
		case "1":
			deleteUserFile(); //deletes file with user details
			deleteUserDetailsFromDb();// deletes user details from main database
			System.out.println("Account Deleted Successfully");
			break;
		case "2":
			distplayUserOptions();// displays the option of tasks available to the user.
			break;
		case "3":
			System.out.println("Logout Successful");
			break;
		default: 
			System.out.println("Invalid input");
			delteLockedmeAccount(input);
		}
	}
	
	
	// diaplays all the websites along with it's username and password in users database
	private void displayWebsiteDetails() {
		
		// pulls all data of the user from his/her data base to the hashmap "userDatabase"
		try {
			getUserDataBase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// if user has'nt stores anything yet displays "No info available"
		if(userDatabase.isEmpty())
			System.out.println("No info avilable");
		
		else {
			System.out.println("----------------------------");
			displayWebsiteDetailsFromDatabase();//displays all the website in the user database 
			System.out.println("----------------------------");
		}
		tryAgainDisplayWebsites();//gives an option to the user to run displayWebsiteDetails again
		
	}
	
	// //gives an option to the user to run displayWebsiteDetails again
	private void tryAgainDisplayWebsites(){
		System.out.println("------------");
		System.out.println("Enter 1 to display websites");
		System.out.println("Enter 2 to get back to main menu");
		System.out.println("Enter 3 to Logout");
		Scanner input = new Scanner(System.in);
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			displayWebsiteDetails();
			break;
		case "2":
			distplayUserOptions();
			break;
		case "3":
			System.out.println("Log out success");
			return;
			//sbreak;
		default:
			System.out.println("Invalid Input");
			tryAgainDisplayWebsites();
			//System.out.println("--------------------");
			break;
		}
	}
	
	
	// changes password of a selected website in user's database.
	private void changeWebsitePassword(Scanner input) {
		System.out.println("Enter the website name");
		String website=input.nextLine();
		
		//returns if user enters an empty website name
		if(website.equals("")) {
			System.out.println("Website name is empty");
			tryAgainChangeWebsitePassword(input);// gives an option to the user to run changeWebsitePassword again
			return;
		}
		
		// pulls all data of the user from his/her data base to the hashmap "userDatabase"
		try {
			getUserDataBase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//returns of no website by the given input is found
		if(websiteAlreadyExists(website)==false){
			System.out.println("Website not found");
			tryAgainChangeWebsitePassword(input);//gives an option to the user to run displayWebsiteDetails again
			return;
		}
		
		System.out.println("Enter wesite's new password");
		String password1=input.nextLine();// user inputs password the 1st time
		
		//returns if password field is empty
		if(password1.equals("")) {
			System.out.println("Password cannot be empty");
			tryAgainChangeWebsitePassword(input);//gives an option to the user to run displayWebsiteDetails again
			return;
		}
		
		System.out.println("Enter website's new password once again");
		String password2=input.nextLine();// user inputs the password the second time.
		
		//if both the passwords entered matches password of the website is changed
		if(password1.equals(password2)) {
			changeWebsitePasswordFromDatbase(website,password2);//assigns new password to the given website.
			System.out.println("Website's password changed successfully");
			tryAgainChangeWebsitePassword(input);//gives an option to the user to run displayWebsiteDetails again
		}
		else {
			System.out.println("website's passwords entered doesn't match");
			tryAgainChangeWebsitePassword(input);//gives an option to the user to run displayWebsiteDetails again
		}
	}
	
	//gives an option to the user to run displayWebsiteDetails again
	private void tryAgainChangeWebsitePassword(Scanner input){
		System.out.println("------------");
		System.out.println("Enter 1 to change password of a website");
		System.out.println("Enter 2 to get back to main menu");
		System.out.println("Enter 3 to Logout");
		
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			changeWebsitePassword(input);
			break;
		case "2":
			distplayUserOptions();
			break;
		case "3":
			System.out.println("Log out success");
			return;
			//sbreak;
		default:
			System.out.println("Invalid Input");
			tryAgainChangeWebsitePassword(input);
			//System.out.println("--------------------");
			break;
		}
	}
	
	// deletes a website from user's database
	private void deleteWebsite(Scanner input) {
		
		//user enters the webite to be deleted
		System.out.println("Enter the website name");
		String website=input.nextLine();
		
		//if user enters blank website name, returna
		if(website.equals("")) {
			System.out.println("Website name is empty");
			tryAgainDeleteWebsite(input);// gives an option to the user to run deleteWebsite again
			return;
		}
		
		// pulls all data of the user from his/her data base to the hashmap "userDatabase"
		try {
			getUserDataBase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// returns if the website does'nt exist in the user database
		if(websiteAlreadyExists(website)==false){
			System.out.println("Website not found");
			tryAgainDeleteWebsite(input); // gives an option to the user to run deleteWebsite again
			return;
		}
		deleteWebsiteFromDatbase(website);// detes the given wesite from the user's database
		System.out.println("website details have been removed from database");
		tryAgainDeleteWebsite(input); // gives an option to the user to run deleteWebsite again
		
	}
	
	// gives an option to the user to run deleteWebsite again
	private void tryAgainDeleteWebsite(Scanner input){
		System.out.println("------------");
		System.out.println("Enter 1 to delete a website");
		System.out.println("Enter 2 to get back to main menu");
		System.out.println("Enter 3 to Logout");
		
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			deleteWebsite(input);
			break;
		case "2":
			distplayUserOptions();
			break;
		case "3":
			System.out.println("Log out success");
			return;
			//sbreak;
		default:
			System.out.println("Invalid Input");
			tryAgainDeleteWebsite(input);
			//System.out.println("--------------------");
			break;
		}
	}
	
	//deals with the operation of adding a new website to the user's database
	private void addNewWebsite(Scanner input1) {
		
		System.out.println("Enter the website name");
		//Scanner input1= new Scanner(System.in);
		
		String website=input1.nextLine();
		
		
		//returns if website entered is empty
		if(website.equals("")) {
			System.out.println("Website name is empty");
			tryAgainAddNewWebsite(input1); // gives the user an option to run addNewWebsite again
			return;
		}
		
		//// pulls all data of the user from his/her data base to the hashmap "userDatabase"
		try {
			getUserDataBase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//returns if website doesn'nt exist
		if(websiteAlreadyExists(website)==true){
			System.out.println("Website already exists in database");
			tryAgainAddNewWebsite(input1);// gives the user an option to run addNewWebsite again
			return;
		}
		
		//gets websites username from user
		System.out.println("Enter wesite's Username");
		String username1=input1.nextLine();
		
		//returns if username is empty
		if(username1.equals("")) {
			System.out.println("Username cannot be empty");
			tryAgainAddNewWebsite(input1);// gives the user an option to run addNewWebsite again
			return;
		}
		
		//gets uer entters password
		System.out.println("Enter wesite's password");
		String password1=input1.nextLine();
		
		//if user entered password is empty returns.
		if(password1.equals("")) {
			System.out.println("Password cannot be empty");
			tryAgainAddNewWebsite(input1);// gives the user an option to run addNewWebsite again
			return;
		}
		
		//gets password from user for a second time
		System.out.println("Enter website's password once again");
		String password2=input1.nextLine();
		
		//if both the passwords match, website details are added to user database
		if(password1.equals(password2)) {
			addwebsiteDetails(website,username1,password2);// adds given website details to users database
			System.out.println("Website details entered sussessfully");
			tryAgainAddNewWebsite(input1); // gives the user an option to run addNewWebsite again
		}
		else {
			System.out.println("website's passwords entered doesn't match");
			tryAgainAddNewWebsite(input1); // gives the user an option to run addNewWebsite again
		}
		
	}
	
	// gives the user an option to run addNewWebsite again
	private void tryAgainAddNewWebsite(Scanner input){
		System.out.println("------------");
		System.out.println("Enter 1 to add a new website");
		System.out.println("Enter 2 to get back to main menu");
		System.out.println("Enter 3 to Logout");
		
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			addNewWebsite(input);
			break;
		case "2":
			distplayUserOptions();
			break;
		case "3":
			System.out.println("Log out success");
			return;
			//sbreak;
		default:
			System.out.println("Invalid Input");
			tryAgainAddNewWebsite(input);
			//System.out.println("--------------------");
			break;
		}
	}
	
	// dispalys the websites in user database along with its details if available
	private void displayWebsiteDetailsFromDatabase() {
		
		Iterator<Entry<String, String[]>> iterator = userDatabase.entrySet().iterator();
		
		while(iterator.hasNext()) {
			System.out.println("----------------------------");
			Map.Entry mapElemnt = (Map.Entry)iterator.next();
			System.out.println("Website: "+mapElemnt.getKey());
			String tempArray[]=(String[]) mapElemnt.getValue();
			System.out.println("Username: "+tempArray[0]);
			System.out.println("Password: "+tempArray[1]);
			System.out.println("----------------------------");
		}
		
	}
	
	//deletes the given website from user database
	private void deleteWebsiteFromDatbase(String website) {
		userDatabase.remove(website);
		overRightusersDatabase();
	}
	
	//changes password of the given website from the database
	private void changeWebsitePasswordFromDatbase(String website ,String password) {
		String tempValueArray[]=userDatabase.get(website);
		tempValueArray[1]=password;
		userDatabase.replace(website, tempValueArray);
		overRightusersDatabase();
	}
	
	//checks if the given website exists in db
	private boolean websiteAlreadyExists(String website) {
		if(userDatabase.containsKey(website))
			return true;
		return false;
	}
	
	//adds the given website details to db
	private void addwebsiteDetails(String webSite, String tempUsername, String password) {
		File fileObj = new File(username+".txt");
		
		FileWriter fileWriter = null;
		
		try {
				if(fileObj.exists()) {
				fileWriter = new FileWriter(fileObj,true);
				Scanner scannerReader = new Scanner(fileObj);
				if(scannerReader.hasNext())
					fileWriter.append("\n"+webSite);
				else
					fileWriter.append(webSite);
				fileWriter.append("\n"+tempUsername);
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
	
	//updates the user db file with new values in the hashmap
	private void overRightusersDatabase() {
		File fileObj = new File(username+".txt");
		
		FileWriter fileWriter = null;
		
		try {
			if(fileObj.exists()) {
					
				fileWriter = new FileWriter(fileObj);
				
				Iterator<Entry<String, String[]>> iterator = userDatabase.entrySet().iterator();
				
				int iterationCounter=0;
				
				while(iterator.hasNext()) {
					Map.Entry mapElemnt = (Map.Entry)iterator.next();
					if(iterationCounter!=0)	
						fileWriter.append("\n"+(String) mapElemnt.getKey());
					else	
						fileWriter.append((String) mapElemnt.getKey());
					String tempArray[]=(String[]) mapElemnt.getValue();
					fileWriter.append("\n"+tempArray[0]);
					fileWriter.append("\n"+tempArray[1]);
					iterationCounter++;
				}
				
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
	
	//gets all the website details from user db file to a hasmap
	private void getUserDataBase() throws FileNotFoundException {
		File fileObj = new File(username+".txt");
		try {
			
			if(fileObj.exists()==false)
				fileObj.createNewFile();
			Scanner scannerReader = new Scanner(fileObj);
			
			while(scannerReader.hasNextLine()) {
				String tempKey=scannerReader.nextLine();
				String usernameAndPwd[]= new String[2];
				usernameAndPwd[0]=scannerReader.nextLine();
				usernameAndPwd[1]=scannerReader.nextLine();
				userDatabase.put(tempKey, usernameAndPwd);
			}
			scannerReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
	}
	
	// deletes user details from main database
	private void deleteUserDetailsFromDb() {
		getAllUserCredentionals();//fetches user name and password from database file and adds them to the hashmap
		//removes user key from hashmap
		if(allCredentials.containsKey(username))
			allCredentials.remove(username);
		setAllUserCredentionals(); //adds updated usermap to db;
	}
	
	//adds updated usermap to db;
	private void setAllUserCredentionals() {
		File fileObj = new File("database.txt");
		
		FileWriter fileWriter = null;
		
		try {
			if(fileObj.exists()) {
					
				fileWriter = new FileWriter(fileObj);
				
				Iterator<Entry<String, String>> iterator = allCredentials.entrySet().iterator();
				
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
	
	//deletes file with user details
	private void deleteUserFile() {
		File fileObj = new File(username+".txt");
		if(fileObj.exists()==true)
			fileObj.delete();
	}
	
	//fetches user name and password from database file and adds them to the hashmap
		private void getAllUserCredentionals() {
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

}

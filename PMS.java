import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class PMS {

	public static void main(String[] args) {
		
		int searchProject;
		//The default option is set to to 0
		int mainSelection = 0;
		
		//Initiate program
		do
		{
			//User is prompted to select an option from the menu
			Scanner userInput = new Scanner(System.in);
			System.out.println("Please select one of the following options: " 
								+ "\n1. Create new project."
								+ "\n2. Change the due date of a project"
								+ "\n3. Change the total due for a project"
								+ "\n4. Update the contractor's contact details"
								+ "\n5. Finalise a project"
								+ "\n6. See list of incomplete projects"
								+ "\n7. See a list of projects that are passed due"
								+ "\n0. Quit program\n");
			
			//The user's input is stored as a variable
			mainSelection = userInput.nextInt();
			
			switch(mainSelection)
			{
				//This controls the process if option 1 is selected to create a new project
				
						
				case 1: //Capture the project number.
						int projectNumber = Project.captureProjectNumber();
						//Capture the customer's first name.
						String custFirstName = Customer.captureFirstName();
						//Capture the customer's last name.
						String custLastName = Customer.captureLastName();
						//Capture the customer's telephone number
						String custTelNumber = Customer.captureTel();
						//Capture the customer's email address
						String custEmail = Customer.captureEmail();
						//Capture the customer's physical address
						String custAddress = Customer.captureAddress();
						
				
						//This creates a new customer object
						Customer customerDetails = new Customer(custFirstName, custLastName, custTelNumber, custEmail, custAddress, projectNumber);
						
						//This prints the captured details in a user friendly manner.
						System.out.println(customerDetails.toString());
						
						//The new customer details are written to the customer table in the poisedpms database.
						toCustDB(custFirstName, custLastName, custTelNumber, custEmail, custAddress, projectNumber);
						
						System.out.println("");
						
						//Capture the project name.
						String projectName = Project.captureProjectName();
						//Capture the building type.
						String buildingType = Project.captureBuildType();
						//Capture the address where the project will be conducted.
						String address = Project.captureAddress();
						//Capture the ERF number
						int erfNumber = Project.captureERF();
						//Capture the amount due for the project.
						int feeDue = Project.captureFee();
						//Capture the amount already paid towards the project.
						int feePaid = Project.captureFeePaid();
						//Capture the due date of the project
						String dueDate = Project.captureDate();
						
						/*If the project name is blank, assign the project name to the building
						type  + customer last name.*/
						if(projectName == " ")
							projectName = buildingType + " " + custLastName;
				
						//New project information is created.
						Project newProject = new Project(projectNumber, projectName, buildingType, address, erfNumber, feeDue, feePaid, dueDate);
					
						//The details of the project are printed.
						System.out.println(newProject.toString());
						
						//Submit the project details to the poisedpms database in the projects folder.
						toProjDB(projectNumber, projectName, buildingType, address, erfNumber, feeDue, feePaid, dueDate);
						
						System.out.println("");
						//Capture the architect's first name.
						String archFirstName = Architect.captureFirstName();
						//Capture the architect's last name.
						String archLastName = Architect.captureLastName();
						//Capture the architect's telephone number.
						String archTelNumber = Architect.captureTel();
						//Capture the architect's email address.
						String archEmail = Architect.captureEmail();
						//Capture the architect's physical address
						String archAddress = Architect.captureAddress();
														
						//This creates a new architect object
						Architect architectDetails = new Architect(archFirstName, archLastName, archTelNumber, archEmail, archAddress, projectNumber);
						//The architect's details are displayed in a user friendly manner
						System.out.println(architectDetails.toString());
						
						//The architect's details are written to the architect table in the poisedpms database
						toArchDB(archFirstName, archLastName, archTelNumber, archEmail, archAddress, projectNumber);
						
						System.out.println("");
						//Capture the contractor's first name.
						String contrFirstName = Contractor.captureFirstName();
						//Capture the contractor's last name.
						String contrLastName = Contractor.captureLastName();
						//Capture the contractor's telephone number.
						String contrTelNumber = Contractor.captureTel();
						//Capture the contractor's email address.
						String contrEmail = Contractor.captureEmail();
						//Capture the contractor's physical address.
						String contrAddress = Contractor.captureAddress();
						
						//This creates a new architect object
						Contractor contractorDetails = new Contractor(contrFirstName, contrLastName, contrTelNumber, contrEmail, contrAddress, projectNumber);
						//The architect's details are printed in a user friendly manner.
						System.out.println(contractorDetails.toString());
						
						//The architect's details are written to the contractor table in the poisedpms database.
						toContDB(contrFirstName, contrLastName, contrTelNumber, contrEmail, contrAddress, projectNumber);
						System.out.println("");
						;break;
		
				case 2:	//prompt the user to enter a project number
						searchProject = getProject();
						
						try {
							// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
							//Use username "otheruser", password "swordfish".
							Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
					
							// Create a direct line to the database for running our queries
							Statement statement = connection.createStatement();
							ResultSet results;
							int rowsAffected;
					
							// executeQuery: runs a SELECT statement and returns the results.
							results = statement.executeQuery("SELECT proj_num, proj_name, due_date FROM projects WHERE proj_num = "+searchProject+"");
			
							//Loop over the results, printing them all.
							while(results.next())
							{
								System.out.println("Project number:\t"+results.getString("proj_num") + "\nProject name:\t" 
										+ results.getString("proj_name") + "\nDue date:\t" + results.getString("due_date"));
							}//End of while
					
							//Capture the due date of the project
							String newDate = Project.captureDate();
									
							//Change due date:
							rowsAffected = statement.executeUpdate("UPDATE projects SET due_date='"+newDate+"' WHERE proj_num="+searchProject);
							System.out.println ("Query complete, " + rowsAffected + " rows updated.");
							System.out.println("");
							// executeQuery: runs a SELECT statement and returns the results.
							results = statement.executeQuery("SELECT proj_num, proj_name, due_date FROM projects WHERE proj_num = "+searchProject+"");
			
							//Loop over the results, printing them all.
							while(results.next())
							{
								System.out.println("Project number:\t"+results.getString("proj_num") + "\nProject name:\t" 
										+ results.getString("proj_name") + "\nDue date:\t" + results.getString("due_date"));
							}//End of while
							System.out.println("");
					
							//Close up our connections
							results.close();
							statement.close();
							connection.close();
						}//End of try
						catch(SQLException e) {
							e.printStackTrace();
							System.out.println("An error occured");
				
						}//End of catch
						break;
					
				
					
				case 3: //prompt the user to enter a project number
						searchProject = getProject();
					
						try {
							// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
							//Use username "otheruser", password "swordfish".
							Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
			
							// Create a direct line to the database for running our queries
							Statement statement = connection.createStatement();
							ResultSet results;
							int rowsAffected;
			
							// executeQuery: runs a SELECT statement and returns the results.
							results = statement.executeQuery("SELECT proj_num, proj_name, proj_fee FROM projects WHERE proj_num = "+searchProject+"");
	
							//Loop over the results, printing them all.
							while(results.next())
							{
								System.out.println("Project number:\t"+results.getString("proj_num") 
									+ "\nProject name:\t" + results.getString("proj_name") 
									+ "\nProject fee:\t" + results.getString("proj_fee"));
							}//End of while
			
							//Capture the amount due for the project
							int newFee = Project.captureFee();
							
							//Change due date:
							rowsAffected = statement.executeUpdate("UPDATE projects SET proj_fee="+newFee+" WHERE proj_num="+searchProject);
							System.out.println ("Query complete, " + rowsAffected + " rows updated.");
							System.out.println("");
							//executeQuery: runs a SELECT statement and returns the results.
							results = statement.executeQuery("SELECT proj_num, proj_name, proj_fee FROM projects WHERE proj_num = "+searchProject+"");
	
							//Loop over the results, printing them all.
							while(results.next())
							{
								System.out.println("Project number:\t"+results.getString("proj_num") + "\nProject name:\t" 
										+ results.getString("proj_name") + "\nProject fee:\t" + results.getString("proj_fee"));
							}//End of while
							System.out.println("");
			
							//Close up our connections
							results.close();
							statement.close();
							connection.close();
						}//End of try
						catch(SQLException e) {
							e.printStackTrace();
							System.out.println("An error occured");
		
						}//End of catch
										
					;break;
				
				case 4: //prompt the user to enter a project number
						searchProject = getProject();
				
						try {
							// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
							//Use username "otheruser", password "swordfish".
							Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
			
							// Create a direct line to the database for running our queries
							Statement statement = connection.createStatement();
							ResultSet results;
							int rowsAffected;
			
							// executeQuery: runs a SELECT statement and returns the results.
							results = statement.executeQuery("SELECT cont_fn, cont_ln, cont_tel FROM contractor WHERE id = "+searchProject);
	
							//Loop over the results, printing them all.
							while(results.next())
							{
								System.out.println("Contractor name:\t"+results.getString("cont_fn") 
									+ " " + results.getString("cont_ln") 
									+ "\nTelephone number:\t" + results.getString("cont_tel"));
							}//End of while
			
							//Capture the amount due for the project
							String newTel = Contractor.captureTel();
							
							//Change due date:
							rowsAffected = statement.executeUpdate("UPDATE contractor SET cont_tel="+newTel+" WHERE id="+searchProject);
							System.out.println ("Query complete, " + rowsAffected + " rows updated.");
							System.out.println("");
							//executeQuery: runs a SELECT statement and returns the results.
							results = statement.executeQuery("SELECT cont_fn, cont_ln, cont_tel FROM contractor WHERE id = "+searchProject+"");
	
							//Loop over the results, printing them all.
							while(results.next())
							{
								System.out.println("Contractor name:\t"+results.getString("cont_fn") 
									+ " " + results.getString("cont_ln") 
								+	 "\nTelephone number:\t" + results.getString("cont_tel"));
							}//End of while
							System.out.println("");
			
							//Close up our connections
							results.close();
							statement.close();
							connection.close();
						}//End of try
						catch(SQLException e) {
							e.printStackTrace();
							System.out.println("An error occured");
		
						}//End of catch
				
						break;
					
				case 5: //prompt the user to enter a project number
						searchProject = getProject();
					
						try {
							// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
							//Use username "otheruser", password "swordfish".
							Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
		
							// Create a direct line to the database for running our queries
							Statement statement = connection.createStatement();
							ResultSet results;
							int rowsAffected;
		
							// executeQuery: runs a SELECT statement and returns the results.
							results = statement.executeQuery("SELECT proj_num, proj_name, proj_fee, fee_paid, finalised FROM projects WHERE proj_num ="+searchProject);

							//Loop over the results, printing them all.
							while(results.next())
							{
								System.out.println("Project number:\t"+results.getString("proj_num") 
									+ "\nProject name:\t" + results.getString("proj_name") 
									+ "\nProject fee:\t" + results.getString("proj_fee")
									+ "\nFee paid:\t" + results.getString("fee_paid")
									+ "\nFinalised:\t" + results.getString("finalised"));
							}//End of while
							
							//A value of 1 for the confirmation will serve as confirmation that the project should be finalised.
							int confirmation = requestConfirmation(searchProject);
							
							if(confirmation == 1)
							{
								LocalDate todaysDate = LocalDate.now();
								//Change the finalised status to yes
								rowsAffected = statement.executeUpdate("UPDATE projects SET finalised='yes', due_date='Completed: "+todaysDate+"' WHERE proj_num="+searchProject);
								System.out.println ("Query complete, " + rowsAffected + " rows updated.");
								System.out.println("");
								
								//executeQuery: runs a SELECT statement and returns the results.
								results = statement.executeQuery("SELECT proj_num, proj_name, proj_fee, fee_paid FROM projects WHERE proj_num = "+searchProject+"");
								while(results.next()) {
									//Check the amount due for the project
									int checkFee = results.getInt("proj_fee");
									System.out.println("Project fee" + checkFee);
									//Check the amount already paid
									int checkPaid = results.getInt("fee_paid");
									System.out.println("\nAmount paid: " + checkPaid);
									//Check if there is an oustanding balance
									int amountOutstanding = checkFee - checkPaid;
									System.out.println("\nAmount oustanding "+amountOutstanding);
								
								
									//If there is a outstanding balance, then generate an invoice
									if(amountOutstanding > 0)
									{
										//executeQuery: runs a SELECT statement and returns the results.
										results = statement.executeQuery("SELECT cust_fn, cust_ln, cust_tel, cust_email FROM customer WHERE id = "+searchProject+"");
										//Loop over the results, printing them all.
										while(results.next())
										{
											System.out.println("==========INVOICE==========\nCustomer name:\t\t"+results.getString("cust_fn") + " "+results.getString("cust_ln") 	
												+ "\nTelephone number:\t"+results.getString("cust_tel") 
												+ "\nEmail address:\t\t" + results.getString("cust_email")
												+ "\nAmount outstanding:\tR " + amountOutstanding+"\n");
										}//End of while
									}//End of if()
								}//end of while
								
														
							}
							
								
							//Close up our connections
							results.close();
							statement.close();
							connection.close();
						}//End of try
						catch(SQLException e) {
							e.printStackTrace();
							System.out.println("An error occured");
	
						}//End of catch
					
					
						;break;
					
				case 6: try {
						// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
						//Use username "otheruser", password "swordfish".
						Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");

						// Create a direct line to the database for running our queries
						Statement statement = connection.createStatement();
						ResultSet results;
						int rowsAffected;

						// executeQuery: runs a SELECT statement and returns the results.
						results = statement.executeQuery("SELECT proj_num, proj_name, build_type, proj_addr, erf_num, proj_fee, fee_paid, due_date FROM projects WHERE finalised ='no'");

						//Loop over the results, printing them all.
						while(results.next())
						{
							System.out.println("Project number:\t"+results.getString("proj_num") 
								+ "\nProject name:\t" + results.getString("proj_name")
								+ "\nBuilding type:\t" + results.getString("build_type") 
								+ "\nAdress:\t" + results.getString("proj_addr")
								+ "\nERF number:\t" + results.getString("erf_num") 
								+ "\nProject fee:\t" + results.getString("proj_fee")
								+ "\nFee paid:\t" + results.getString("fee_paid")
								+ "\nDue date:\t" + results.getString("due_date"));
						}//End of while
										
					//Close up our connections
					results.close();
					statement.close();
					connection.close();
				}//End of try
				catch(SQLException e) {
					e.printStackTrace();
					System.out.println("An error occured");

				}//End of catch
								
				;break;
						
				case 7: LocalDate todaysDate = LocalDate.now();
						try {
							// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
							//Use username "otheruser", password "swordfish".
							Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");

							// Create a direct line to the database for running our queries
							Statement statement = connection.createStatement();
							ResultSet results;
							int rowsAffected;

					// executeQuery: runs a SELECT statement and returns the results.
					results = statement.executeQuery("SELECT proj_num, proj_name, build_type, proj_addr, erf_num, proj_fee, fee_paid, due_date FROM projects WHERE finalised ='no' AND due_date > '"+todaysDate+"'");

					//Loop over the results, printing them all.
					while(results.next())
					{
						System.out.println("Project number:\t"+results.getString("proj_num") 
							+ "\nProject name:\t" + results.getString("proj_name")
							+ "\nBuilding type:\t" + results.getString("build_type") 
							+ "\nAdress:\t" + results.getString("proj_addr")
							+ "\nERF number:\t" + results.getString("erf_num") 
							+ "\nProject fee:\t" + results.getString("proj_fee")
							+ "\nFee paid:\t" + results.getString("fee_paid")
							+ "\nDue date:\t" + results.getString("due_date"));
					}//End of while
									
				//Close up our connections
				results.close();
				statement.close();
				connection.close();
			}//End of try
			catch(SQLException e) {
				e.printStackTrace();
				System.out.println("An error occured");

			}//End of catch
			;break;
						
				default: System.out.println("Program ended");break;
			}//End of switch.
		}while(mainSelection !=0);//End of Do.	
		
		
	}//End of Main
	
	private static int requestConfirmation(int searchProject) {
		Scanner confirmInput = new Scanner(System.in);
		System.out.println("Are you sure you would like to finalise project "+searchProject+"?"
				+"\n1) Yes"
				+"\n2) No");
		int confirm = confirmInput.nextInt();
		return confirm;
		
	}//End of requestConfirmation()

	//Step 1: Request user to enter a new date.
	//Step 2: Return the new date.
	private static String getDate() {
		Scanner dateInput = new Scanner(System.in);
		System.out.println("Please enter a new project due date: ");
		String date = dateInput.nextLine();
		
		return date;
	}//End of getDate()
	
	//Step 1: The user is requested to iput an project number
	//Step 2: return the project number
	private static int getProject() {
		
		Scanner projectNumberInput = new Scanner(System.in);
		System.out.println("Please enter your project number");
		int searchProject = projectNumberInput.nextInt();
		
		return searchProject;
	}//End of getProject()
	
	//This writes project details to the projects table
	//This writes project details to the projects table
	public static void toProjDB(int projectNumber2, String projectName2, String buildingType2, String address,
			int erfNumber2, double feeDue, double feePaid2, String dueDate){
	
		try {
			// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
			//Use username "otheruser", password "swordfish".
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
								
			// Create a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			
			ResultSet results;
			int rowsAffected;
					
			//Add a new book:
			rowsAffected = statement.executeUpdate("INSERT INTO projects VALUES ("+projectNumber2+", '"+projectName2+"', '"+buildingType2+"', '"+address+"', "+erfNumber2+", "+feeDue+", "+feePaid2+", '"+dueDate+"')");
			System.out. println("Query complete, " + rowsAffected + " rows added.");
			Project.printAllFromTable(statement);
			
			//Close up our connections
			statement.close();
			connection.close();
			}//End of try
			catch(SQLException e) {
			//We only want to catch a SQLException - anything else is off-limits for now.
			e.printStackTrace();
			
			}//End of catch
		
	}//End of toProjDB()
	
	//This writes customer details to the customer table
	private static void toCustDB(String firstName2, String lastName2, String telNumber, String email2, String address2, int projectNumber) {
		try {
		// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
		//Use username "otheruser", password "swordfish".
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
							
		// Create a direct line to the database for running our queries
		Statement statement = connection.createStatement();
		
		ResultSet results;
		int rowsAffected;
				
		//Add a new customer:
		rowsAffected = statement.executeUpdate("INSERT INTO contractor VALUES ('"+firstName2+"', '"+lastName2+"', '"+telNumber+"', '"+email2+"', '"+address2+"', "+projectNumber+")");
		System.out. println("Query complete, " + rowsAffected + " rows added.");
		//printAllFromTable(statement);
		
		//Close up our connections
		statement.close();
		connection.close();
		}//End of try
		catch(SQLException e) {
		//We only want to catch a SQLException - anything else is off-limits for now.
		e.printStackTrace();
		
		}//End of catch
		
	}//End of toCustDB()
	
	//This writes project details to the architect table
	private static void toArchDB(String firstName2, String lastName2, String telNumber, String email2, String address2, int projectNumber) {
		try {
		// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
		//Use username "otheruser", password "swordfish".
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
							
		// Create a direct line to the database for running our queries
		Statement statement = connection.createStatement();
		
		ResultSet results;
		int rowsAffected;
				
		//Add a new book:
		rowsAffected = statement.executeUpdate("INSERT INTO architect VALUES ('"+firstName2+"', '"+lastName2+"', '"+telNumber+"', '"+email2+"', '"+address2+"', "+projectNumber+")");
		System.out. println("Query complete, " + rowsAffected + " rows added.");
		//printAllFromTable(statement);
		
		//Close up our connections
		statement.close();
		connection.close();
		}//End of try
		catch(SQLException e) {
		//We only want to catch a SQLException - anything else is off-limits for now.
		e.printStackTrace();
		
		}//End of catch
		
	}//End of toArchDB()
	
	//This writes project details to the contractor table
	private static void toContDB(String firstName2, String lastName2, String telNumber, String email2, String address2, int projectNumber) {
		try {
		// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
		//Use username "otheruser", password "swordfish".
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false" ,"otheruser" ,"swordfish");
							
		// Create a direct line to the database for running our queries
		Statement statement = connection.createStatement();
		
		ResultSet results;
		int rowsAffected;
				
		//Add a new book:
		rowsAffected = statement.executeUpdate("INSERT INTO contractor VALUES ('"+firstName2+"', '"+lastName2+"', '"+telNumber+"', '"+email2+"', '"+address2+"', "+projectNumber+")");
		System.out. println("Query complete, " + rowsAffected + " rows added.");
		//printAllFromTable(statement);
		
		//Close up our connections
		statement.close();
		connection.close();
		}//End of try
		catch(SQLException e) {
		//We only want to catch a SQLException - anything else is off-limits for now.
		e.printStackTrace();
		
		}//End of catch
		
	}//End of toContDB()

}//End of PMS Class

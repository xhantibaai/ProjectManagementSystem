	/*The below program allows a user to:
	 *1. Input data for a new project to be created,
	 *2. Change the due date of a project
	 *3. Update a contractor's contact details
	 *4. Finalise a project.*/

	//The below will be used to create a txt file
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
	
public class Project {
	int projectNumber;
	static String projectName; 
	String buildingType;
	String projectPhysicalAddress;
	static String deadlineDate;
	int erfNumber;
	double projectFee;
	double feePaid;
		
	//The project number is returned when called
	public int getProjectNumber(){
		return projectNumber;
	}
		
	//The project name is returned when called
	public String getProjectName(){
		return projectName;
	}
		
	//The building type is returned when called
	public String getBuildingType(){
		return buildingType;
	}
		
	//The physical address is returned when called
	public String getProjectPhysicalAddress(){
		return projectPhysicalAddress;
	}
		
	//The project deadline is returned when called
	public String getDeadlineDate(){
		return deadlineDate;
	}
		
	//The ERF number is returned when called
	public int getErfNumber(){
		return erfNumber;
	}
		
	//The project fee is returned when called.
	public double getProjectFee(){
		return projectFee;
	}
		
	//The fee already paid is returned when called.
	public double getFeePaid(){
		return feePaid;
	}
		
	//This method appends the project details to the class field.
	public Project(int projectNumber, String projectName, 
			String buildingType, String projectPhysicalAddress, 
			 int erfNumber, double projectFee, double feePaid, String deadlineDate) {
		
		this.projectNumber = projectNumber;
		this.projectName = projectName;
		this.buildingType = buildingType;
		this.projectPhysicalAddress = projectPhysicalAddress;
		this.deadlineDate = deadlineDate;
		this.erfNumber = erfNumber;
		this.projectFee = projectFee;
		this.feePaid = feePaid;
	}
		
	//The project details are put in string format with headings.
	public String toString() {
		return String.format("Project number:\t\t%s"
				+ "\nProject name:\t\t%s"
				+ "\nBuilding Type:\t\t%s"
				+ "\nPhysical Address:\t%s"
				+ "\nDeadline Date:\t\t%s"
				+"\nERF Number:\t\t%s"
				+"\nProject Fee:\t\t%s"
				+"\nFee Paid:\t\t%s",projectNumber,projectName,buildingType,projectPhysicalAddress,deadlineDate,erfNumber,projectFee,feePaid);
	}
		
			
	public static void main(String[] args)
	{
		int projectNumber = captureProjectNumber();
		String projectName = captureProjectName();
		String buildingType = captureBuildType();
		String address = captureAddress();
		int erfNumber = captureERF();
		int feeDue = captureFee();
		int feePaid = captureFeePaid();
		String dueDate = captureDate();
		
		//New project information is created.
		Project newProject = new Project(projectNumber, projectName, buildingType, address, erfNumber, feeDue, feePaid, dueDate);
			
		//The details of the project are printed.
		System.out.println(newProject.toString());
		
		toProjDB(projectNumber, projectName, buildingType, address, erfNumber, feeDue, feePaid, dueDate);
			
		
	}//End of main

	
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
			printAllFromTable(statement);
			
			//Close up our connections
			statement.close();
			connection.close();
			}//End of try
			catch(SQLException e) {
			//We only want to catch a SQLException - anything else is off-limits for now.
			e.printStackTrace();
			
			}//End of catch
		
	}//End of toProjDB()

	public static String captureDate() {
		
		//Capture the year the project is due.
		Scanner yearInput = new Scanner(System.in);
		System.out.println("Please enter the year the project is due, e.g. 2020: ");
		String year = yearInput.nextLine();
		
		//Capture the month the project is due.
		Scanner monthInput = new Scanner(System.in);
		System.out.println("Please enter the numeric month the project is due, e.g. 10: ");
		String month = monthInput.nextLine();
		
		//Capture the day the project is due
		Scanner dayInput = new Scanner(System.in);
		System.out.println("Please enter the calendar day the project is due, e.g. 24: ");
		String day = yearInput.nextLine();
		
		//Arrange date in the format YYY-MM-DD.
		String date = year + "-" + month + "-" + day;
		
		//Return date in the correct format.
		return date;
	}

	public static int captureFeePaid() {
		Scanner paidInput = new Scanner(System.in);
		System.out.println("Please enter the amount already paid towards the project: ");
		int paid = paidInput.nextInt();
		
		return paid;
	}

	public static int captureFee() {
		Scanner feeInput = new Scanner(System.in);
		System.out.println("Please enter the amount due for the project: ");
		int fee = feeInput.nextInt();
		
		return fee;
	}

	//Step 1: Request user to enter ERF number
	//Step 2: Return ERF number
	public static int captureERF() {
		Scanner erfInput = new Scanner(System.in);
		System.out.println("Please enter the erf number: ");
		int erf = erfInput.nextInt();
		
		return erf;
	}

	public static String captureAddress() {
		Scanner addressInput = new Scanner(System.in);
		System.out.println("Please enter the address where the project will take place: ");
		String address = addressInput.nextLine();
		
		return address;
	}

	public static String captureBuildType() {
		Scanner buildTypeInput = new Scanner(System.in);
		System.out.println("Please enter the building type: ");
		String buildType = buildTypeInput.nextLine();
		
		return buildType;
	}

	public static String captureProjectName() {
		Scanner projectNameInput = new Scanner(System.in);
		System.out.println("Please enter the project name: ");
		String projectName = projectNameInput.nextLine();
		
		return projectName;
	}

	public static int captureProjectNumber() {
		Scanner projectInput = new Scanner(System.in);
		System.out.println("Please enter the project number: ");
		int projectNumber = projectInput.nextInt();
		
		return projectNumber;
		
	}//End of cap
	
	/**
	* Method printing all values in all rows.
	* Takes a statement to try to avoid spreading DB access too far.
	*
	* @param a statement on an existing connection
	* @throws SQLException
	*/
	public static void printAllFromTable(Statement statement) throws SQLException {
		ResultSet results = statement.executeQuery("SELECT proj_num, proj_name, build_type, proj_addr, erf_num, proj_fee, fee_paid, due_date FROM projects" );
		while (results.next()) {
			System.out.println ("\nRecords from the database:\n"
						+results.getString("proj_num") + ", " 
						+ results.getString("proj_name") + ", "
						+ results.getString("build_type") + ", "
						+ results.getString("proj_addr") + ", "
						+ results.getString("erf_num") + ", "
						+ results.getString("proj_fee") + ", "
						+ results.getString("fee_paid") + ", "
						+ results.getString("due_date"));
		}//End of while()
	}//End of printAllFromTable()
		
}//End of Project class.



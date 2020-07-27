import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Contractor {
	String firstName;
	String lastName;
	String telephone;	
	String email;
	String address;
	static int projectNumber;
		
		
	//The first name is returned when called
	public String getFirstName(){
		return firstName;
	} 
		
	//The last name is returned when called
	public String getLastName(){
		return lastName;
	}
		
	//The telephone number is returned when called
	public String getTelephone(){
		return telephone;
	} 
		
	//The email address is returned when called
	public String getEmail(){
		return email;
	}
		
	//The address is returned when called
	public String getAddress(){
		return address;
	} 
		
	//This appends the required details to the class field
	public Contractor(String firstName, String lastName, String telephone, 
						String email, String address, int projectNumber){
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
		this.projectNumber = projectNumber;
		
	}//End of Architect()
		
	//This arranges the input details in a user friendly format.
	public String toString()
	{
		return String.format("First name:\t\t%s"
							+ "\nLast name:\t\t%s"
							+ "\nTelephone number:\t%s"
							+ "\nEmail address:\t\t%s"
							+ "\nPhysical Address:\t%s"
							+"\nProject ID:\t%s", firstName,lastName,telephone,email,address, projectNumber);
	}//End of toString()
		
	public static void main(String[] args) {
		//This creates a new project object
						
		String contrFirstName = captureFirstName();
		String contrLastName = captureLastName();
		String contrTelNumber = captureTel();
		String contrEmail = captureEmail();
		String contrAddress = captureAddress();
		
		//This creates a new architect 
		Contractor contractorDetails = new Contractor(contrFirstName, contrLastName, contrTelNumber, contrEmail, contrAddress, projectNumber);
		
		System.out.println(contractorDetails.toString());
		
		toContDB(contrFirstName, contrLastName, contrTelNumber, contrEmail, contrAddress, projectNumber);
			
	}//End of main()

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
		printAllFromTable(statement);
		
		//Close up our connections
		statement.close();
		connection.close();
		}//End of try
		catch(SQLException e) {
		//We only want to catch a SQLException - anything else is off-limits for now.
		e.printStackTrace();
		
		}//End of catch
		
	}//End of toContDB()

	/**
	* Method printing all values in all rows.
	* Takes a statement to try to avoid spreading DB access too far.
	*
	* @param a statement on an existing connection
	* @throws SQLException
	*/
	public static void printAllFromTable(Statement statement) throws SQLException {
		ResultSet results = statement.executeQuery("SELECT cont_fn, cont_ln, cont_tel, cont_email, cont_phys_add, id FROM contractor");
		while (results.next()) {
			System.out.println ("\nRecords from the database:\n"
						+results.getString("cont_fn") + ", " 
						+ results.getString("cont_ln") + ", "
						+ results.getString("cont_tel") + ", "
						+ results.getString("cont_email") + ", "
						+ results.getString("cont_phys_add")+ ", "
						+ results.getInt(projectNumber));
		}//End of while()
	}//End of printAllFromTable()
	

	//Step 1: Request user to enter contractor's physical address
		//Step 2: Return the last name
	public static String captureAddress() {
		Scanner addressInput = new Scanner(System.in);
		System.out.println("Please enter the contractor's physical address: ");
		String address = addressInput.nextLine();
		
		return address;
	}//End of captureAddress()

	//Step 1: Request user to enter contractor's email address
		//Step 2: Return the last name
	public static String captureEmail() {
		Scanner emailInput = new Scanner(System.in);
		System.out.println("Please enter the contractor's email address: ");
		String email = emailInput.nextLine();
		
		return email;
	}//End of captureEmail()
	
	//Step 1: Request user to enter contractor's telephone number
	//Step 2: Return the last name
	public static String captureTel() {
		Scanner telInput = new Scanner(System.in);
		System.out.println("Please enter the contractor's telephone number: ");
		String tel = telInput.nextLine();
		
		return tel;
	}//End of captureTel()

	//Step 1: Request user to enter contractor's last name
	//Step 2: Return the last name
	public static String captureLastName() {
		Scanner lastNameInput = new Scanner(System.in);
		System.out.println("Please enter the contractor's last name: ");
		String lastName = lastNameInput.nextLine();
		
		return lastName;
	}//End of captureLastName()
	
	//Step 1: Request user to enter contractor's first name
	//Step 2: Return the first name
	public static String captureFirstName() {
		Scanner firstNameInput = new Scanner(System.in);
		System.out.println("Please enter the contractor's first name: ");
		String firstName = firstNameInput.nextLine();
		
		return firstName;
	}//End of captureFirstName()

}//End of Contractor

import java.util.Formatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Architect {

	String archFirstName;
	String archLastName;
	String archTelephone;	
	String archEmail;
	String archAddress;
	static int projectNumber;
		
		
	//The first name is returned when called
	public String getFirstName(){
		return archFirstName;
	} 
		
	//The last name is returned when called
	public String getLastName(){
		return archLastName;
	}
		
	//The telephone number is returned when called
	public String getTelephone(){
		return archTelephone;
	} 
		
	//The email address is returned when called
	public String getEmail(){
		return archEmail;
	}
		
	//The address is returned when called
	public String getAddress(){
		return archAddress;
	} 
	
	public int getProjectID() {
		return projectNumber;
	}
		
	//This appends the required details to the class field
	public Architect(String firstName, String lastName, String telephone, 
						String email, String address, int projectNumber){
		this.archFirstName = archFirstName;
		this.archLastName = archLastName;
		this.archTelephone = archTelephone;
		this.archEmail = archEmail;
		this.archAddress = archAddress;
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
							+ "\nProject ID:\t%s", archFirstName,archLastName,archTelephone,archEmail,archAddress,projectNumber);
	}//End of toString()
		
	public static void main(String[] args) {
		//This creates a new project object
						
		String archFirstName = captureFirstName();
		String archLastName = captureLastName();
		String archTelNumber = captureTel();
		String archEmail = captureEmail();
		String archAddress = captureAddress();
				
		//This creates a new architect 
		Architect architectDetails = new Architect(archFirstName, archLastName, archTelNumber, archEmail, archAddress, projectNumber);
		
		System.out.println(architectDetails.toString());
		
		toArchDB(archFirstName, archLastName, archTelNumber, archEmail, archAddress, projectNumber);
			
	}//End of main()

	private static int captureArchID() {
		// TODO Auto-generated method stub
		return 0;
	}

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
		printAllFromTable(statement);
		
		//Close up our connections
		statement.close();
		connection.close();
		}//End of try
		catch(SQLException e) {
		//We only want to catch a SQLException - anything else is off-limits for now.
		e.printStackTrace();
		
		}//End of catch
		
	}//End of toArchDB()

	/**
	* Method printing all values in all rows.
	* Takes a statement to try to avoid spreading DB access too far.
	*
	* @param a statement on an existing connection
	* @throws SQLException
	*/
	public static void printAllFromTable(Statement statement) throws SQLException {
		ResultSet results = statement.executeQuery("SELECT arch_fn, arch_ln, arch_tel, arch_email, arch_phys_add, id FROM architect" );
		while (results.next()) {
			System.out.println ("\nRecords from the database:\n"
						+results.getString("arch_fn") + ", " 
						+ results.getString("arch_ln") + ", "
						+ results.getString("arch_tel") + ", "
						+ results.getString("arch_email") + ", "
						+ results.getString("arch_phys_add")+","
						+results.getInt(projectNumber));
		}//End of while()
	}//End of printAllFromTable()

	public static String captureAddress() {
		Scanner addressInput = new Scanner(System.in);
		System.out.println("Please enter the architect's physical address: ");
		String address = addressInput.nextLine();
		
		return address;
	}

	public static String captureEmail() {
		Scanner emailInput = new Scanner(System.in);
		System.out.println("Please enter the architect's email address: ");
		String email = emailInput.nextLine();
		
		return email;
	}

	public static String captureTel() {
		Scanner telInput = new Scanner(System.in);
		System.out.println("Please enter the architect's telephone number: ");
		String tel = telInput.nextLine();
		
		return tel;
	}

	//Step 1: Request user to enter architect's last name
	//Step 2: Return the last name
	public static String captureLastName() {
		Scanner lastNameInput = new Scanner(System.in);
		System.out.println("Please enter the architect's last name: ");
		String lastName = lastNameInput.nextLine();
		
		return lastName;
	}
	
	//Step 1: Request user to enter architect's first name
	//Step 2: Return the first name
	public static String captureFirstName() {
		Scanner firstNameInput = new Scanner(System.in);
		System.out.println("Please enter the architect's first name: ");
		String firstName = firstNameInput.nextLine();
		
		return firstName;
	}
		
}//End of Architect class

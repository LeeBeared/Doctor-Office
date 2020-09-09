import java.sql.*;
public class personnel_db_manager {

		//CONSTAST to hold the url 
		// that will be used to connect to the database
	   public final String DB_URL = 
	                "jdbc:ucanaccess://personnel.accdb;newdatabaseversion=V2010";

	   //Object that will be used to connect to the database
	   @SuppressWarnings("unused")
	private static Connection conn;

	   /**
	      Constructor
	      	:In Constructor the program will connect to the database
	      Throws exceptions from any connection errors
	    */
	   public personnel_db_manager() throws SQLException
	   {// Create the connect to the database by
		   //getting the connection to the constant DB_URL
		      conn = DriverManager.getConnection(DB_URL);
		   try{
			   create_employee_table();
		   }catch(SQLException e){};
      
	   }
	   public void create_employee_table() throws SQLException{
		   Statement sql_statment = conn.createStatement();

		   //Execute the creation of the table
		   sql_statment.execute("CREATE TABLE Employee ("+
	   				"employeeID INTEGER PRIMARY KEY, "+
	   				"employeeName CHAR(50), "+
	   				"employeePosition CHAR(100), "+
	   				"employeeHourlypayRate DOUBLE)");
		   //Close the resources 
		   sql_statment.close();
		   //Tell the user the employee table is being created
		   System.out.print("\nCreating the Employee table inside the Personnel Database");
		   System.out.print("\nDone creating the Employee Table");
	   }
 
	   /* Insert_query will create a sql insert query based on the parameters provide
	    * and executes the create sql statement
	    * The sql statement will insert a record
	    */
	   public void insert_query(String employee_name, String employee_position,String hourly_pay) 
	                      throws SQLException
	   {
		   // String variable that will be used in the insert query.
		   // Follow appropriate sql rules when creating the string
		   String sql_query_string = "INSERT INTO Employee (employeeName, employeePosition,employeeHourlypayRate)"
		   		+  "VALUES (?, ?, ?)";  
	                     
		   // Create a Prepared Statement object.This will house the sql statement
		   PreparedStatement sql_statement = conn.prepareStatement(sql_query_string);

		   //Insert the parameters into the sql statement variable in the order the parameters
		   // are defined.
		   //So ?1 is parameters 1, ?2 is parameters 2 etc.
		   //Use appropriate set calls for the parameters
		   sql_statement.setString(1, employee_name);
		   sql_statement.setString(2, employee_position);
		   sql_statement.setString(3, hourly_pay);

		   //Execute the statement and then close it
		   sql_statement.executeUpdate();
		   sql_statement.close();
	   }
	   public static ResultSet select_employee(String employee_name, String employee_position, String employee_pay)throws SQLException
		 {	
			// String variable that will be used in the select query.
			// Follow appropriate sql rules when creating the string
			 String sql_select_string = "SELECT employeeID as ID, employeeName as Name, employeePosition as Position, employeeHourlypayRate as HourlyPay"
						+ " from Employee"
					 +" where employeeName Like ? AND employeePosition Like ? AND employeeHourlypayRate Like ?";
			 
			// Create a Prepared Statement object.This will house the sql statement
			 PreparedStatement sql_select_statement = conn.prepareStatement(sql_select_string);
			 
			//Insert the parameters into the sql statement variable in the order the parameters
			// are defined.
			//So ?1 is parameters 1, ?2 is parameters 2 etc.
			//Use appropriate set calls for the parameters
			 sql_select_statement.setString(1, "%"+employee_name+"%");
			 sql_select_statement.setString(2, "%"+employee_position+"%");
			 sql_select_statement.setString(3, "%"+employee_pay+"%");
			 /* Execute the query inside the sql select statement.
			 * Since the users need the results of the query return that
			 */
			return sql_select_statement.executeQuery();
			 
		 }
	   /* update_query will create a sql insert query based on the parameters provide
	    * and executes the create sql statement
	    * 
	    * The sql statement will update a record
	    */
	   public void update_query(String employee_name, String employee_position, String employee_pay, double employ_id)throws SQLException
	   {
		   // String variable that will be used in the update query.
		   // Follow appropriate sql rules when creating the string
	  		String sql_update_string = "update Employee set "
	   				+ "employeeName =  ?, employeePosition = ?, employeeHourlypayRate = ?"
	   				+"WHERE employeeID = ?";
	   						
		   
		   
		   // Create a Statement object.
		   PreparedStatement sql_statement = conn.prepareStatement(sql_update_string);
		   //Insert the parameters into the sql statement variable in the order the parameters
		   // are defined.
		   //So ?1 is parameters 1, ?2 is parameters 2 etc.
		   //Use appropriate set calls for the parameters
		   sql_statement.setString(1, employee_name);
		   sql_statement.setString(2, employee_position);
		   sql_statement.setString(3, employee_pay);
		   sql_statement.setDouble(4, employ_id);

		   //Execute the statement and then close it
		   sql_statement.executeUpdate();
		   sql_statement.close();
	   }
	   /* select_for_update will create a sql select query based on the parameters provide
	    * and executes the create sql statement
	    * 
	    * Return: Is the results from the select query
	    */
		public static ResultSet select_for_update(String employ_id) 
		           throws SQLException
	   {
			// String variable that will be used in the select query.
			   // Follow appropriate sql rules when creating the string
			String select_query_string = "SELECT employeeID as ID, employeeName as Name, employeePosition as Position, employeeHourlypayRate as HourlyPay "
					+ "from Employee where employeeID = ?";
			
			// Create a Prepared Statement object.This will house the sql statement
			PreparedStatement query_statement = conn.prepareStatement(select_query_string);
			
			//Insert the parameters into the sql statement variable in the order the parameters
			// are defined.
			//So ?1 is parameters 1, ?2 is parameters 2 etc.
			//Use appropriate set calls for the parameters
			query_statement.setString(1, employ_id);
			
			/* Execute the query inside the query statement.
			 * Since the users need the results of the query return that
			 */
			return query_statement.executeQuery();
	   }

}

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class update_employee_program extends JFrame {

	employee_info_panel_builder employees_info;
	JPanel button_panel;
	JPanel list_panel; 
	String search_str = "";   
	JScrollPane scroll_pane;    
	JTable employee_table;
	String emply_id;
	public update_employee_program(){
		//Set the title for the program
	    setTitle("Update employee");
	      
	    //Set default close action
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      
	    //Set employees_info to be a new info panel builder
	    employees_info = new employee_info_panel_builder();
	    
	    //Call the build panel methods
	    build_button_panel();
	    build_list_panel();
		
		//Set a layout for the program
	    setLayout(new BorderLayout());
		
	    // Add panels to the pane
	    add(employees_info, BorderLayout.NORTH);
	    add(list_panel, BorderLayout.CENTER);
	    add(button_panel, BorderLayout.SOUTH);
	    
		//Pack and set the visibility to true
	    pack();
	    setVisible(true);
	}
	public void build_button_panel(){
	 	//Make button panel a new JPanel
		button_panel = new JPanel();
      
	 // Create a submit button along with an action listener for it
      JButton submit_button = new JButton("Submit");
      submit_button.addActionListener(new submit_button_listener());
      // Create a search button along with an action listener for it
      JButton search_button = new JButton("Search");
      search_button.addActionListener(new search_button_listener());
      // Create a update button along with an action listener for it
      JButton updateButton = new JButton("Update");
      updateButton.addActionListener(new update_button_listener());
      
      // Create a clear button along with an action listener for it
      JButton clear_button = new JButton("Clear");
      clear_button.addActionListener(new clear_button_listener());

      // Create a exit button along with an action listener for it
      JButton close_button = new JButton("Close Update");
      close_button.addActionListener(new close_button_listener());
      
      //Add the created buttons to the button panel 
      button_panel.add(search_button);
      button_panel.add(submit_button);
      button_panel.add(updateButton);
      button_panel.add(clear_button);
      button_panel.add(close_button);
}
	private void build_list_panel()
	   {
	      try
	      {
	         //Set list panel to a new panel
	         list_panel = new JPanel();
	         
	         // Add a border to the list panel
	         list_panel.setBorder(BorderFactory.createTitledBorder("Employee Information"));
	         
	         
	         //Create object to access database.This is an personnel_db_manager object
	         personnel_db_manager employee_get_info = new personnel_db_manager();
	         
	         //Create a result set based on the return of a select_employee method inside the object employee_get_info
	         ResultSet employee_info = employee_get_info.select_employee("","","");
	         
	         //Set employee table to hold the information from the employee_info after using rs2XML
		     employee_table = new JTable(DbUtils.resultSetToTableModel(employee_info));
		     //Set scroll_pane to be a new scroll_pane based on the employee table
		     scroll_pane = new JScrollPane(employee_table);
		     //Add the created scroll pane to the panel
		     list_panel.add(scroll_pane);
		     
		    //Add a listener to the employee table, so that when clicks a entry we know which one
		     employee_table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	                public void valueChanged(ListSelectionEvent event) {
	                	//Check to see if the user has selected a employee
	                	if (employee_table.getSelectedRow() > -1) {
		                	//Get the id value of the selected employee
		                    emply_id = employee_table.getValueAt(employee_table.getSelectedRow(), 0).toString();
	                	}
	                }
	            });			         
	          }
	      //Catch for sql errors
	      catch(SQLException ex)
	      {
	         // If an Sql error occurs tell the user
	        JOptionPane.showMessageDialog(null,ex.toString());
	      }
	   }
	private class clear_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	         // Call the clear method in employee info
	    	  //this will clear the fields
	    	  employees_info.clear();
	      }
	   }
	 private class close_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	    	//Dispose of the Jframe
	         dispose();
	      }
	   }
	 private class submit_button_listener implements ActionListener
	   {
		 @SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent e)
	      {
			//Variables
			 String error_string="";
			 boolean data_error=false;
			 
			
	        try{
	        	// Get the information from the employee fields
	        	// Get the information from the employee fields
				String emply_name_info = employees_info.get_employee_name_();
				String emply_position_info = employees_info.get_employee_position();
				String emply_pay_info = employees_info.get_employee_hourly_pay();
				
				//Parse value to double so it can be used in sql query
		        Double selected_id = Double.parseDouble(emply_id);
		        //Call the test number data and give the pay and a true.
		        //This will cause a number format error when the pay is parsed to a double
		        employees_info.test_number_data(true, emply_pay_info);
		      //Run test to match length requirements inside the db
				if(emply_name_info.length()<0||emply_name_info.length()>255)
					{data_error=true;error_string="\nThe minum length for Name is 1 and MAX is 255";}
				if(emply_position_info.length()<0||emply_position_info.length()>255)
					{data_error=true;error_string="\nThe minum length for Position is 1 and MAX is 255";}
				if(emply_pay_info.length()<0||emply_pay_info.length()>255)
					{data_error=true;error_string="\nThe minum length for Hourly Pay is 1 and MAX is 255";
					}

	        	//Check to see if data_error is false
	        	if(!data_error){
		        	//Make a new personnel_db_manager
	        		personnel_db_manager emply_manager = new personnel_db_manager();
					//Update the employee by calling update query inside the emply_manager
					emply_manager.update_query(emply_name_info, emply_position_info, emply_pay_info, selected_id);
		       	
					//Create a new  result set object beside on the select_employee method
					//This is used to update the table inside the program
		        	 ResultSet searchInfo = emply_manager.select_employee("", "", "");

					 //Reset the employee table to show the update information to the user
		            employee_table.setModel(DbUtils.resultSetToTableModel(searchInfo));
		            employees_info.clear();
				
		            //If there are any errors tell the user so
	        	}else{JOptionPane.showMessageDialog(null,"Data Errors:"+error_string);}
			 //Catch for sql errors
			 }catch(SQLException ex){
				 ex.printStackTrace();
		            System.exit(0);
		     //Catch for Number format Exception
			 }catch(NumberFormatException e1){
				//Tell the user to remover characters inside the Pay field
				 JOptionPane.showMessageDialog(null, "Please remove letters from the zip field");}

	      }
	   }
	 private class update_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  try {
	    		  	//Create a new result set variable object
	    		  	ResultSet update_emply_info = null;
	    		    //Create object to access database.This is an personnel_db_manager object
	    		  	personnel_db_manager employee_get_info = new personnel_db_manager();
	    		  //Create a result set based on the return of a select_for_update method inside the object employee_get_info
					update_emply_info = employee_get_info.select_for_update(emply_id);
					
					//Loop while the employee still has information
					while (update_emply_info.next()) {
						//Set the field inside the employee info panel 
						//to be the employee information
						//Get the information from the employee_get_info object
						employees_info.set_employee_name(update_emply_info.getString("Name"));
						employees_info.set_employee_position(update_emply_info.getString("Position"));
						employees_info.set_employee_hourly(update_emply_info.getString("HourlyPay"));					
					}
				//Catch for any sql errors
	            } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	      }
	   } 
	 private class search_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	    	//Try catch for any sql errors
				 try{
					 //Variables
					 ResultSet all_employees = null;
					 String[] fields_info={"","",""};
					 
					// Get the information from the employee fields
					String emply_name_info = employees_info.get_employee_name_();
					String emply_position_info = employees_info.get_employee_position();
					String emply_pay_info = employees_info.get_employee_hourly_pay();
					
							
					//Make a new employee table manager
					personnel_db_manager empl_manager = new personnel_db_manager();
					
					//Test to see which fields has information and add that info to the fields info array
					if(emply_name_info.length()>0){fields_info[0]=emply_name_info;}
					if(emply_position_info.length()>0){fields_info[1]=emply_position_info;}
					if(emply_pay_info.length()>0){employees_info.test_number_data(true, emply_pay_info);fields_info[2]=emply_pay_info;}
					
					//Set all_employee to be based on the select_employee method
					//This is used to update the table inside the program
					//Use the fields info array this will reset the table to
					//that result set return
					all_employees = empl_manager.select_employee(fields_info[0],fields_info[1],fields_info[2]);
					
		
					 //Reset the employee table to show the update information to the user
					employee_table.setModel(DbUtils.resultSetToTableModel(all_employees));
						

				
				 //Catch for sql errors
				 }catch(SQLException ex){
					 ex.printStackTrace();
			            System.exit(0);
			     //Catch for Number format Exception
				 }catch(NumberFormatException e1){
					 //Tell the user to remover characters inside the Pay field 
					 JOptionPane.showMessageDialog(null, "Pay field is either empty or has letters in it that needs to be removed");}
				 }
	      }
	   
} 


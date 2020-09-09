import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.proteanit.sql.DbUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

@SuppressWarnings("serial")
public class insert_employee_program extends JFrame{
	//Variables 
	employee_info_panel_builder employee_info;
	JPanel button_panel;
	JPanel table_panel;
	JScrollPane scroll_pane;
	JTable employee_table;
	
	// Constructor
	insert_employee_program(){
		//Set the title for the program
	      setTitle("Add employee");
	      
	      //Tell the program the default close action
	      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      
	      //Set employee_info to be a new info panel builder
	      employee_info = new employee_info_panel_builder();
	      
	     //Call the build button panel
	      build_button_panel();
	      build_table_panel();
	      
	      //Create a new border layout
	      setLayout(new BorderLayout());
	      
	      // Add panels to the pane
	      add(employee_info, BorderLayout.NORTH);
	      add(employee_table,BorderLayout.CENTER);
	      add(button_panel, BorderLayout.SOUTH);
	      
	      //Pack and set the visibility to true
	      pack();
	      setVisible(true);
	}
	/* build_button_panel Will build a panel 
	 * that will house buttons such as Submit,Clear  and Exit
	 * 
	 */
	public void build_button_panel(){
		 	//Make button panel a new JPanel
			button_panel = new JPanel();
	      
	      // Create a submit button along with an action listener for it
	      JButton submit_button = new JButton("Submit");
	      submit_button.addActionListener(new submit_button_listener());
	      
	      // Create a clear button along with an action listener for it
	      JButton clear_button = new JButton("Clear");
	      clear_button.addActionListener(new clear_button_listener());

	      //Create a close button along with an action listener for it
	      JButton close_button = new JButton("Close Insert");
	      close_button.addActionListener(new close_button_listener());
	      
	      //Add the buttons to the button panel 
	      button_panel.add(submit_button);
	      button_panel.add(clear_button);
	      button_panel.add(close_button);
	}
	 private class clear_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	         // Call the clear method in employee info
	    	  //this will clear the fields
	    	  employee_info.clear();
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
	 private class submit_button_listener implements ActionListener{
		 public void actionPerformed(ActionEvent e)
	      {
			 //Try catch for any sql errors
			 try{
				 //Variables
				 String error_string="";
				 boolean data_error=false;
				 
				// Get the information from the employee fields
				String emply_name_info = employee_info.get_employee_name_();
				String emply_position_info = employee_info.get_employee_position();
				String emply_pay_info = employee_info.get_employee_hourly_pay();
				
				employee_info.test_number_data(false, emply_pay_info);
				
				//Run test to match length requirements inside the db
				if(emply_name_info.length()<0||emply_name_info.length()>255)
					{data_error=true;error_string="\nThe minum length for Name is 1 and MAX is 255";}
				if(emply_position_info.length()<0||emply_position_info.length()>255)
					{data_error=true;error_string="\nThe minum length for Position is 1 and MAX is 255";}
				if(emply_pay_info.length()<0||emply_pay_info.length()>255)
					{data_error=true;error_string="\nThe minum length for Hourly Pay is 1 and MAX is 255";
					}
				//Check to see if data error is false
				//This means that there are no length errors
				if(!data_error){
					//Make a new employee table manager
					personnel_db_manager empl_manager = new personnel_db_manager();
					
					//Call the insert query giving
					//it the information from the fields
					empl_manager.insert_query(emply_name_info,emply_position_info,emply_pay_info);
					//Call the clear in employee info 
					//to clear the fields 
					employee_info.clear();
					 
					///Tell the user employee was added
					JOptionPane.showMessageDialog(null, "Employee Added!");
					
					//Create a new  result set object beside on the select_employee method
					//This is used to update the table inside the program
		        	 ResultSet all_employees = empl_manager.select_employee("","","");

					 //Reset the employee table to show the update information to the user
		            employee_table.setModel(DbUtils.resultSetToTableModel(all_employees));
					
				//Else tell the user there was a data error and the errors
				}else{JOptionPane.showMessageDialog(null, "Data Error:"+error_string);}
			
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
	 private void build_table_panel()
	   {
	      try
	      {
	         //Set list panel to a new panel
	         table_panel = new JPanel();
	         
	         // Add a border to the list panel
	         table_panel.setBorder(BorderFactory.createTitledBorder("Employee Table"));
	         
	         
	         //Create object to access database.This is an employee table object
	         personnel_db_manager employee_get_info = new personnel_db_manager();
	         
	         //Create a result set based on the return of a select_employee method inside the object employee_get_info
	         ResultSet employee_info = employee_get_info.select_employee("","","");
	         
	         //Set employee table to hold the information from the employee_info after using rs2XML
		     employee_table = new JTable(DbUtils.resultSetToTableModel(employee_info));
		     //Set scroll_pane to be a new scroll_pane based on the employee table
		     scroll_pane = new JScrollPane(employee_table);
		     //Add the created scroll pane to the panel
		     table_panel.add(scroll_pane);
		     			         
	          }
	      //Catch for sql errors
	      catch(SQLException ex)
	      {
	         // If an Sql error occurs tell the user
	        JOptionPane.showMessageDialog(null,ex.toString());
	      }
	   }
}
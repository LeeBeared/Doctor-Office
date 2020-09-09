import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class search_employee_program extends JFrame{
	//Variables 
		employee_info_panel_builder employee_info;
		JPanel button_panel;
		JPanel table_panel;
		JScrollPane scroll_pane;
		JTable employee_table;
	search_employee_program(){
		//Set the title for the program
	      setTitle("Add employee");
	      
	      //Tell the program the default close action
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      
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
	      JButton search_button = new JButton("Search");
	      search_button.addActionListener(new search_button_listener());
	      
	      // Create a clear button along with an action listener for it
	      JButton clear_button = new JButton("Clear");
	      clear_button.addActionListener(new clear_button_listener());

	      //Create a close button along with an action listener for it
	      JButton close_button = new JButton("Close Search");
	      close_button.addActionListener(new close_button_listener());
	      
	      //Add the buttons to the button panel 
	      button_panel.add(search_button);
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
	    	//Make a new personnel_db_manager
				personnel_db_manager empl_manager = null;
				try {
					empl_manager = new personnel_db_manager();
					employee_table.setModel(DbUtils.resultSetToTableModel(empl_manager.select_employee("","","")));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	      }
	   }
	 private class close_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	    	 //Dispose of the JFrame
	         dispose();
	      }
	   }
	 private class search_button_listener implements ActionListener{
		 public void actionPerformed(ActionEvent e)
	      {
			 //Try catch for any sql errors
			 try{
				 //Variables
				 ResultSet all_employees = null;
				 String[] fields_info={"","",""};
				 
				// Get the information from the employee fields
				String emply_name_info = employee_info.get_employee_name_();
				String emply_position_info = employee_info.get_employee_position();
				String emply_pay_info = employee_info.get_employee_hourly_pay();
				
						
				//Make a new personnel_db_manager
				personnel_db_manager empl_manager = new personnel_db_manager();
				

				if(emply_name_info.length()>0){fields_info[0]=emply_name_info;}
				if(emply_position_info.length()>0){fields_info[1]=emply_position_info;}
				if(emply_pay_info.length()>0){employee_info.test_number_data(true, emply_pay_info);fields_info[2]=emply_pay_info;}
				
				//Set all_employee to be based on the select_employee method
				//This is used to update the table inside the program
				//Also use all blank string for the call
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

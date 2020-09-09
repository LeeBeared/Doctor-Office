import java.awt.GridLayout;
import javax.swing.*;
@SuppressWarnings("serial")
public class employee_info_panel_builder extends JPanel{

		//Variables 
	   private JTextField employee_name_field;   
	   private JTextField employee_position_field;     
	   private JTextField employee_hourly_pay_field;       

	   
	   public employee_info_panel_builder()
	   {
		   

	      JLabel name_prompt = new JLabel("Name");
	      employee_name_field = new JTextField();
	      JLabel position_prompt = new JLabel("Position");
	      employee_position_field = new JTextField();
	      JLabel hourly_pay_prompt = new JLabel("Hourly Pay");
	      employee_hourly_pay_field = new JTextField();

	      
	      
	      //Create a grid layout with 4 columns and 2 row
	      setLayout(new GridLayout(4, 2));
	      //Create a Titled border for the panel
	      setBorder(BorderFactory.createTitledBorder("Enter Employee's Information"));
	      
	     //Add labels and fields to the panel.
	      //In the order that they were defined in 
	      add(name_prompt);
	      add(employee_name_field);
	      add(position_prompt);
	      add(employee_position_field);
	      add(hourly_pay_prompt);
	      add(employee_hourly_pay_field);
	   }
	 //Setters to set the text fields
	   public void set_employee_name(String employee_name_str)
	   	{employee_name_field.setText(employee_name_str);}
	   public void set_employee_position(String employee_position_str)
	   	{employee_position_field.setText(employee_position_str);}
	   public void set_employee_hourly(String employee_hourly_str)
	   	{employee_hourly_pay_field.setText(employee_hourly_str);}
	   
	   
	   @SuppressWarnings("unused")
	public void test_number_data(boolean make_double, String needs_testing)throws NumberFormatException{
		
		   if(make_double){double double_testing = Double.parseDouble(needs_testing);}
		   else{int int_testing= Integer.parseInt(needs_testing); }
	   }
	   
	   
	   // Getters will return the value of the 
	   // in which the method is named after.
	   //Such as get employee num will return the value of
	   //the employee number field
	   public String get_employee_name_()
	   	{return employee_name_field.getText();}
	   public String get_employee_position()
	   	{return employee_position_field.getText();}
	   public String get_employee_hourly_pay()
	   	{return employee_hourly_pay_field.getText();}
	        
	  /* Clear method will reset all fields
	   * in the panel to be either "" or 0 
	   */
	   public void clear()
	   {
		   employee_name_field.setText("");
		   employee_position_field.setText("");
		   employee_hourly_pay_field.setText("");

	   }
}

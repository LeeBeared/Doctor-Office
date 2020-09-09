import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Barrineau_17_8 extends JFrame{

	JPanel option_panel;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Barrineau_17_8 db_usage = new Barrineau_17_8();
	}
	Barrineau_17_8(){
		//Set the title for the program
	      setTitle("Employee");
	      
	      //Tell the program the default close action
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      
	 
	      
	     //Call the build button panel
	      build_option_panel();
	      
	      //Create a new border layout
	      setLayout(new BorderLayout());
	      
	      // Add panels to the pane
	      add(option_panel, BorderLayout.CENTER);
	      
	      //Pack and set the visibility to true
	      pack();
	      setVisible(true);
	}
	public void build_option_panel(){
		//Make option panel a new JPanel
		option_panel = new JPanel();
      
      // Create a insert button along with an action listener for it
      JButton insert_button = new JButton("Insert Employee");
      insert_button.addActionListener(new insert_button_listener());
   // Create a search button along with an action listener for it
      JButton search_button = new JButton("Search Employee");
      search_button.addActionListener(new search_button_listener());
   // Create a update button along with an action listener for it
      JButton update_button = new JButton("Update Employee");
      update_button.addActionListener(new update_button_listener());

      setLayout(new GridLayout(2, 2));
      //Add the buttons to the button panel 
      option_panel.add(insert_button);
      option_panel.add(search_button);
      option_panel.add(update_button);

	}
	private class insert_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	         new insert_employee_program();
	      }
	   }
	private class search_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	         new search_employee_program();
	      }
	   }
	private class update_button_listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	         new update_employee_program();
	      }
	   }
}

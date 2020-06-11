package view;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Error;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchStudentFrame extends CustomLibraryFrame {

	private JTextField textFieldSearchByID;
	private JTextField textFieldSearchByName;

	/**
	 * Launch the application.
	 */
	public static void SearchStudentScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchStudentFrame frame = new SearchStudentFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					Error newError = new Error(e.getMessage());
					newError.NewError();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchStudentFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JRadioButton rdbtnSearchByID = new CustomTransparentRadioButton("Search By ID:");
		rdbtnSearchByID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetTextFieldSearchByName(false);
				SetTextFieldSearchByID(true);
				
				
			}
		});
		
		rdbtnSearchByID.setSelected(true);
		rdbtnSearchByID.setBounds(0, 7, 109, 23);

		m_Panel.add(rdbtnSearchByID);
		
		textFieldSearchByID = new JTextField();
		textFieldSearchByID.setColumns(10);
		textFieldSearchByID.setBounds(10, 37, 180, 20);
		m_Panel.add(textFieldSearchByID);
		
		JRadioButton rdbtnSearchByFirstAndLastName = new CustomTransparentRadioButton("Search By First And Last Name:");
		rdbtnSearchByFirstAndLastName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetTextFieldSearchByName(true);
				SetTextFieldSearchByID(false);
			}
		});
		rdbtnSearchByFirstAndLastName.setBounds(0, 98, 210, 23);
		m_Panel.add(rdbtnSearchByFirstAndLastName);
		
		textFieldSearchByName = new JTextField();
		textFieldSearchByName.setEditable(false);
		textFieldSearchByName.setColumns(10);
		textFieldSearchByName.setBounds(10, 128, 180, 20);
		m_Panel.add(textFieldSearchByName);
		
		JButton btnSearchStudent = new CustomLibraryButton("Search Student");
		btnSearchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String textFromTextField;
				String stringOfInformation;
				model.Student student;
				
				if (rdbtnSearchByID.isSelected())
				{

					textFromTextField = textFieldSearchByID.getText();					
					student = controller.LibraryController.ControllerSearchStudentByID(textFromTextField);
					
					if(student.GetStudentID() != 0)
					{
						
						stringOfInformation = controller.LibraryController.studentInformationToAppend(student);
						JOptionPane.showMessageDialog(null, stringOfInformation);
					}
				}
				else // Search by first and last names
				{			
					textFromTextField = textFieldSearchByName.getText();
					stringOfInformation = controller.LibraryController.ControllerSearchStudentByName(textFromTextField);
					
					if(stringOfInformation.intern() != "".intern())
					{
					JOptionPane.showMessageDialog(null, stringOfInformation);
					}	
				}
			}
		});
		btnSearchStudent.setBounds(238, 227, 180, 23);
		m_Panel.add(btnSearchStudent);
		this.setResizable(false);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnSearchByFirstAndLastName);
		group.add(rdbtnSearchByID);
	}
	
	private void SetTextFieldSearchByID(boolean i_State)
	{
		textFieldSearchByID.setEditable(i_State);
	}
	
	private void SetTextFieldSearchByName(boolean i_State)
	{
		textFieldSearchByName.setEditable(i_State);
	}
}

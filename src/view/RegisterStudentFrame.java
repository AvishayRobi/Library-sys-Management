package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class RegisterStudentFrame extends CustomLibraryFrame {

	private JTextField textFieldCourses;
	private JTextField textFieldPhoneNumber;
	private JTextField textFieldLastName;
	private JTextField textFieldFirstName;
	private JTextField textFieldID;

	/**
	 * Launch the application.
	 */
	public static void RegisterStudentScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterStudentFrame frame = new RegisterStudentFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterStudentFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setForeground(Color.WHITE);
		lblID.setBounds(10, 14, 74, 14);
		m_Panel.add(lblID);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setBounds(10, 47, 74, 14);
		m_Panel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setBounds(10, 78, 74, 14);
		m_Panel.add(lblLastName);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setBounds(10, 109, 97, 14);
		m_Panel.add(lblPhoneNumber);
		
		JLabel lblSelectStudentCourses = new JLabel("Student Courses:");
		lblSelectStudentCourses.setForeground(Color.WHITE);
		lblSelectStudentCourses.setBounds(10, 140, 113, 14);
		m_Panel.add(lblSelectStudentCourses);
		
		JButton btnNewButton = new CustomLibraryButton("Register Student");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				StringBuilder studentInformation = new StringBuilder();
				boolean isSuccessRegisterStudent;

				
				// Getting information from text fields
				studentInformation = fetchStudentInfoFromFrame();
				
				// Sending information to Library Controller
				try
				{
					isSuccessRegisterStudent = controller.LibraryController.ControllerRegisterStudent(studentInformation);
				} 
				catch (Exception e1)
				{
					view.PopUpMessage.ShowPopUpMessage(e1.getMessage());
					isSuccessRegisterStudent = false;
				}
				
				// Clear all text fields
				clearTextFields();

				// Pop up the result to user
				JOptionPane.showMessageDialog(null, textToPopUp(isSuccessRegisterStudent));
			}
		});
		btnNewButton.setBounds(127, 200, 180, 23);
		m_Panel.add(btnNewButton);
		
		textFieldCourses = new JTextField();
		textFieldCourses.setColumns(10);
		textFieldCourses.setBounds(127, 137, 180, 20);
		textFieldCourses.setToolTipText("Example: course1,course2,cours3");
		m_Panel.add(textFieldCourses);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setColumns(10);
		textFieldPhoneNumber.setBounds(127, 106, 180, 20);
		m_Panel.add(textFieldPhoneNumber);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(127, 75, 180, 20);
		m_Panel.add(textFieldLastName);
		
		textFieldFirstName = new JTextField();
		textFieldFirstName.setColumns(10);
		textFieldFirstName.setBounds(127, 44, 180, 20);
		m_Panel.add(textFieldFirstName);
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		textFieldID.setBounds(127, 11, 180, 20);
		m_Panel.add(textFieldID);
		
		//setBounds(100, 100, 450, 300);
	}
	
	private StringBuilder fetchStudentInfoFromFrame()
	{
		
		boolean isCourseTextFieldEmpty;
		isCourseTextFieldEmpty = textFieldCourses.getText().equals("");
		StringBuilder strStudentInfo = new StringBuilder();
		
		// Insert student id
		strStudentInfo.append(textFieldID.getText());
		strStudentInfo.append(",");
		
		// Insert student phone number
		strStudentInfo.append(textFieldPhoneNumber.getText());
		strStudentInfo.append(",");
		
		// Insert student first name
		strStudentInfo.append(textFieldFirstName.getText());
		strStudentInfo.append(",");
		
		// Insert student last name
		strStudentInfo.append(textFieldLastName.getText());
		strStudentInfo.append(",");
		
		if(!isCourseTextFieldEmpty)
		{
			int i;
			String[] splittedCourses = textFieldCourses.getText().split(",");
			
			for(i = 0; i < splittedCourses.length - 1; i++)
			{
				splittedCourses[i] = splittedCourses[i].trim();
				strStudentInfo.append(splittedCourses[i]);
				strStudentInfo.append("|");
			}
			
			splittedCourses[i] = splittedCourses[i].trim();
			strStudentInfo.append(splittedCourses[i]);			
		}
		
		
		return strStudentInfo;
	}
	
	// Clear firstName, lastName, phoneNumber fields
	private void clearTextFields()

	{
		textFieldID.setText("");
		textFieldFirstName.setText("");
		textFieldLastName.setText("");
		textFieldPhoneNumber.setText("");
		textFieldCourses.setText("");
	}
	
	// Prompting user whether register is successful or not
	private String textToPopUp(boolean i_RegisterState)
	{
		String textToShow;
		
		if(i_RegisterState)
		{
			textToShow = "Register successful";
		}
		else
		{
			textToShow = "Register unsuccessful";
		}
		
		return textToShow;
	}

}

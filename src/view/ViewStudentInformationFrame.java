package view;


import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Error;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewStudentInformationFrame extends CustomLibraryFrame
{

	private JTextField textFieldStudentID;

	/**
	 * Launch the application.
	 */
	public static void ViewStudentInformationScreen() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try
				{
					ViewStudentInformationFrame frame = new ViewStudentInformationFrame();
					frame.setVisible(true);
				} 
				catch (Exception e)
				{
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
	public ViewStudentInformationFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblStudentID = new JLabel("Enter Student ID:");
		lblStudentID.setBounds(10, 11, 100, 14);
		lblStudentID.setForeground(Color.WHITE);
		m_Panel.add(lblStudentID);
		
		textFieldStudentID = new JTextField();
		textFieldStudentID.setBounds(125, 8, 180, 20);
		textFieldStudentID.setColumns(10);
		m_Panel.add(textFieldStudentID);
		
		JButton btnShowInformation = new CustomLibraryButton("Show Student Information");
		btnShowInformation.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				
				model.Student student;
				String studentID;
			
				studentID = textFieldStudentID.getText();
				student = model.Library.SearchStudentByID(studentID);
				
				if (student.GetFirstName().intern() != "".intern())
				{
					student.ShowInformation();
				}

			}
		});
		btnShowInformation.setBounds(127, 227, 210, 23);
		m_Panel.add(btnShowInformation);
	}

}

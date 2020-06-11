package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Error;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class UnregisterStudentFrame extends CustomLibraryFrame {

	private JTextField textFieldStudentID;

	/**
	 * Launch the application.
	 */
	public static void UnregisterStudentScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnregisterStudentFrame frame = new UnregisterStudentFrame();
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
	public UnregisterStudentFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		JLabel lblStudentID = new JLabel("Student ID:");
		lblStudentID.setForeground(Color.WHITE);
		lblStudentID.setBounds(10, 14, 73, 14);
		m_Panel.add(lblStudentID);
		
		textFieldStudentID = new JTextField();
		textFieldStudentID.setColumns(10);
		textFieldStudentID.setBounds(118, 11, 180, 20);
		m_Panel.add(textFieldStudentID);
		
		JButton btnUnregisterStudent = new CustomLibraryButton("Unregister Student");
		btnUnregisterStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String textFromField;
				model.Student student = new model.Student();			
				textFromField = textFieldStudentID.getText();
				student = controller.LibraryController.ControllerSearchStudentByID(textFromField);
				
				if(student.GetStudentID() != 0)
				{
					try
					{
						model.Library.RemoveStudent(student);
					} 
					catch (IOException e1)
					{
						e1.printStackTrace();
						Error newError = new Error(e1.getMessage());
						newError.NewError();
						return;
					}	
				}
			}
		});
		btnUnregisterStudent.setBounds(244, 230, 180, 23);

		m_Panel.add(btnUnregisterStudent);
	}

}

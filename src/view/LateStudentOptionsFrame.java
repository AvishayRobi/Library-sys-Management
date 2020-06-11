package view;


import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Error;
import model.LateStudent;
import model.Library;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class LateStudentOptionsFrame extends CustomLibraryFrame
{
	private JTextField textFieldStudentID;
	model.LateStudent lateStudent = new model.LateStudent();

	/**
	 * Launch the application.
	 */
	public static void LateStudentOptionsScreen() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try {
					LateStudentOptionsFrame frame = new LateStudentOptionsFrame();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LateStudentOptionsFrame()
	{		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel lblStudentID = new JLabel("Enter Student ID:");
		lblStudentID.setBounds(10, 11, 100, 14);
		lblStudentID.setForeground(Color.WHITE);
		m_Panel.add(lblStudentID);
		
		textFieldStudentID = new JTextField();
		textFieldStudentID.setBounds(115, 8, 160, 20);
		textFieldStudentID.setColumns(10);
		m_Panel.add(textFieldStudentID);		
		
		JButton btnSendSmsWarning = new CustomLibraryButton("Send SMS Warning");
		btnSendSmsWarning.setBounds(119, 108, 180, 23);
		btnSendSmsWarning.setVisible(false);
		btnSendSmsWarning.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
			lateStudent.SendAlertSMS();	
			}
		});
		m_Panel.add(btnSendSmsWarning);

		JButton btnBlockPortal = new CustomLibraryButton("Block Portal");
		btnBlockPortal.setBounds(119, 157, 180, 23);
		btnBlockPortal.setVisible(false);
		btnBlockPortal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lateStudent.BlockPersonalPortal();
			}
		});
		m_Panel.add(btnBlockPortal);

		JButton btnNewButton = new CustomLibraryButton("Search");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{		
				boolean isStudentExists =false;
				model.Student lateStudentInfo;
				
				String lateStudentID = textFieldStudentID.getText();
				
				lateStudentInfo = Library.SearchStudentByID(lateStudentID);
				isStudentExists = lateStudentInfo.GetFirstName().intern() != "".intern();
				
				if(isStudentExists)
				{					
					lateStudent = new LateStudent(lateStudentInfo);
					try {
						showPopUpIfStudentIsntLate(lateStudentID);
					} catch (NumberFormatException | IOException | ParseException e1) {
						model.Error newError = new Error("student isn't late");
						newError.NewError();
						e1.printStackTrace();
					}
					
					btnSendSmsWarning.setVisible(true);
					btnBlockPortal.setVisible(true);
				}
				else
				{
					btnSendSmsWarning.setVisible(false);
					btnBlockPortal.setVisible(false);
				}						
			}
		});
		btnNewButton.setBounds(296, 7, 89, 23);
		m_Panel.add(btnNewButton);		
	}
	
	private void showPopUpIfStudentIsntLate(String i_StudentID) throws NumberFormatException, IOException, ParseException 
	{
		boolean isStudentLateInReturningBook = Library.IsStudentLate(i_StudentID);
		if (!isStudentLateInReturningBook) 
		{
			PopUpMessage.ShowPopUpMessage("Warning: Student is not late!");
		}
	}
}

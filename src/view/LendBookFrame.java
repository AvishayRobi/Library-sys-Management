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
import java.io.IOException;
import java.awt.event.ActionEvent;

public class LendBookFrame extends CustomLibraryFrame 
{

	private JTextField textFieldStudentID;
	private JTextField textFieldBookID;

	/**
	 * Launch the application.
	 */
	public static void LendBookScreen()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					LendBookFrame frame = new LendBookFrame();
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
	public LendBookFrame() 
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblStudentID = new JLabel("Enter Student ID:");
		lblStudentID.setBounds(10, 11, 113, 14);
		lblStudentID.setForeground(Color.WHITE);
		m_Panel.add(lblStudentID);
		
		textFieldStudentID = new JTextField();
		textFieldStudentID.setBounds(171, 8, 180, 20);
		textFieldStudentID.setColumns(10);
		m_Panel.add(textFieldStudentID);

		
		JLabel lblBookID = new JLabel("Enter Book ID:");
		lblBookID.setBounds(10, 36, 113, 14);
		lblBookID.setForeground(Color.WHITE);
		m_Panel.add(lblBookID);
		
		textFieldBookID = new JTextField();
		textFieldBookID.setBounds(171, 33, 180, 20);
		textFieldBookID.setColumns(10);
		m_Panel.add(textFieldBookID);
		
		JButton btnLendBook = new CustomLibraryButton("Lend Book");
		btnLendBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				String studentID;
				String bookID;
				studentID = textFieldStudentID.getText();
				bookID = textFieldBookID.getText();
				
				try 
				{
					controller.BookController.ControllerLendBook(studentID, bookID);
				} 
				catch (NumberFormatException e1)
				{
					e1.printStackTrace();
					Error newError = new Error(e1.getMessage());
					newError.NewError();
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
					Error newError = new Error(e1.getMessage());
					newError.NewError();
				}
			}
		});
		btnLendBook.setBounds(117, 227, 180, 23);
		m_Panel.add(btnLendBook);
	}

}

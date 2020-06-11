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


public class RemoveABookFrame extends CustomLibraryFrame
{

	private JTextField textFieldBookIdentifier;

	/**
	 * Launch the application.
	 */
	public static void RemoveABookScreen()
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					RemoveABookFrame frame = new RemoveABookFrame();
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
	public RemoveABookFrame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		JLabel lblUserInput = new JLabel("Enter Book ID:");
		lblUserInput.setBounds(10, 11, 106, 14);
		lblUserInput.setForeground(Color.WHITE);
		m_Panel.add(lblUserInput);
		
		textFieldBookIdentifier = new JTextField();
		textFieldBookIdentifier.setBounds(100, 8, 180, 20);
		textFieldBookIdentifier.setColumns(10);
		m_Panel.add(textFieldBookIdentifier);

		JButton btnRemoveBook = new CustomLibraryButton("Remove Book");
		btnRemoveBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				String bookID;
				bookID = textFieldBookIdentifier.getText();
				
				try
				{
					controller.LibraryController.ControllerRemoveBook(bookID);
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Error newError = new Error(e1.getMessage());
					newError.NewError();
				}
			}
		});
		btnRemoveBook.setBounds(128, 227, 180, 23);
		m_Panel.add(btnRemoveBook);
	}
}

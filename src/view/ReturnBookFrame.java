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

public class ReturnBookFrame extends CustomLibraryFrame 
{

	private JTextField textFieldBookID;

	/**
	 * Launch the application.
	 */
	public static void ReturnBookScreen() 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				try
				{
					ReturnBookFrame frame = new ReturnBookFrame();
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
	public ReturnBookFrame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblReturnBook = new JLabel("Enter Book ID:");
		lblReturnBook.setBounds(10, 11, 120, 14);
		lblReturnBook.setForeground(Color.WHITE);
		m_Panel.add(lblReturnBook);
		
		textFieldBookID = new JTextField();
		textFieldBookID.setBounds(100, 8, 180, 20);
		textFieldBookID.setColumns(10);
		m_Panel.add(textFieldBookID);
		
		JButton btnReturnBook = new CustomLibraryButton("Return a Book");
		btnReturnBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				String bookID;
				bookID = textFieldBookID.getText();
				
				try
				{
					controller.BookController.ControllerReturnBook(bookID);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
					Error newError = new Error(e1.getMessage());
					newError.NewError();
				}	
			}
		});
		btnReturnBook.setBounds(121, 227, 180, 23);
		m_Panel.add(btnReturnBook);
	}

}

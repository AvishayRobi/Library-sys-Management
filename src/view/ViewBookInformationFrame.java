package view;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import model.Error;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewBookInformationFrame extends CustomLibraryFrame 
{
	
	private JTextField textFieldBookID;

	/**
	 * Launch the application.
	 */
	public static void ViewBookInformationScreen() 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ViewBookInformationFrame frame = new ViewBookInformationFrame();
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
	public ViewBookInformationFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		JLabel lblEnterBookID = new JLabel("Enter Book ID:");
		lblEnterBookID.setForeground(Color.WHITE);
		lblEnterBookID.setBounds(10, 11, 100, 14);
		m_Panel.add(lblEnterBookID);
		
		textFieldBookID = new JTextField();
		textFieldBookID.setBounds(98, 8, 180, 20);
		textFieldBookID.setColumns(10);
		m_Panel.add(textFieldBookID);
		
		JButton btnShowBookInfo = new CustomLibraryButton("View Book Information");
		btnShowBookInfo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				String bookID;
				bookID = textFieldBookID.getText();
				model.Book book = model.Library.SearchBookByID(bookID);
				
				if (book != null)
				{
					book.ShowInformation();
				}
			}
		});
		btnShowBookInfo.setBounds(137, 227, 180, 23);
		m_Panel.add(btnShowBookInfo);
	}
}

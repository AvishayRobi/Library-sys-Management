package view;


import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controller.LibraryController;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.naming.AuthenticationException;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AddABookFrame extends CustomLibraryFrame 
{
	private JTextField textFieldBookTitle;
	private JTextField textFieldCatagory;
	private JTextField textFieldPublisherName;
	private JTextField textFieldAuthorName;
	private JTextField textFieldPublisherPhoneNumber;

	/**
	 * Launch the application.
	 */
	public static void AddABookScreen()
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try 
				{
					AddABookFrame frame = new AddABookFrame();
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
	public AddABookFrame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblBookTitle = new JLabel("Book Title:");
		lblBookTitle.setBounds(10, 11, 80, 14);
		lblBookTitle.setForeground(Color.WHITE);
		m_Panel.add(lblBookTitle);
		
		JLabel lblAddCatagory = new JLabel("Catagory:");
		lblAddCatagory.setBounds(10, 39, 80, 14);
		lblAddCatagory.setForeground(Color.WHITE);
		m_Panel.add(lblAddCatagory);
		
		JLabel lblAddPublisher = new JLabel("Publisher's Name:");
		lblAddPublisher.setBounds(10, 102, 190, 14);
		lblAddPublisher.setForeground(Color.WHITE);
		m_Panel.add(lblAddPublisher);
		
		textFieldBookTitle = new JTextField();
		textFieldBookTitle.setBounds(180, 8, 160, 20);
		textFieldBookTitle.setColumns(10);
		m_Panel.add(textFieldBookTitle);

		textFieldCatagory = new JTextField();
		textFieldCatagory.setBounds(180, 36, 160, 20);
		textFieldCatagory.setColumns(10);
		m_Panel.add(textFieldCatagory);
		
		textFieldPublisherName = new JTextField();
		textFieldPublisherName.setBounds(180, 99, 162, 20);
		textFieldPublisherName.setColumns(10);
		m_Panel.add(textFieldPublisherName);

		
		JButton btnAddBook = new CustomLibraryButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean isInformationValid = true;
				String[] authorNameSplitted; 
				String[] splittedInformation;
				String bookTitle = textFieldBookTitle.getText();
				String category = textFieldCatagory.getText();
				String publisherName = textFieldPublisherName.getText();
				String authorName = textFieldAuthorName.getText();
				String publisherPhoneNumber = textFieldPublisherPhoneNumber.getText();
				
				// [0] - title, [1] - author name, [2] - publisher name, [3] - category, [4] - publisher phone number
				splittedInformation = controller.LibraryController.ControllerNewBookInformation(bookTitle, category, publisherName, authorName, publisherPhoneNumber);
				
				// Split to first and last name of author
				authorNameSplitted = splittedInformation[1].split(" ");
				
				try
				{
					LibraryController.ControllerAddNewBook(authorNameSplitted, splittedInformation);
				}
				catch (IOException | IllegalArgumentException e1)
				{
					view.PopUpMessage.ShowPopUpMessage(e1.getMessage());
					isInformationValid = false;
				}
				
				if (isInformationValid != false)
				{
					JOptionPane.showMessageDialog(null, "Book Added!");
				}	
				
				clearTextFields();
			}
		});
		
		btnAddBook.setBounds(167, 227, 89, 23);
		m_Panel.add(btnAddBook);
		
		JLabel lblAuthorName = new JLabel("Author's Name:");
		lblAuthorName.setBounds(10, 71, 100, 14);
		lblAuthorName.setForeground(Color.WHITE);
		m_Panel.add(lblAuthorName);
		
		textFieldAuthorName = new JTextField();
		textFieldAuthorName.setBounds(180, 68, 160, 20);
		textFieldAuthorName.setColumns(10);
		m_Panel.add(textFieldAuthorName);
		
		JLabel lblPublisherPhone = new JLabel("Publisher's Phone Number:");
		lblPublisherPhone.setBounds(10, 133, 170, 14);
		lblPublisherPhone.setForeground(Color.WHITE);
		m_Panel.add(lblPublisherPhone);
		
		textFieldPublisherPhoneNumber = new JTextField();
		textFieldPublisherPhoneNumber.setBounds(180, 130, 162, 20);
		textFieldPublisherPhoneNumber.setColumns(10);
		m_Panel.add(textFieldPublisherPhoneNumber);
	}
	
	private void clearTextFields()
	{
		textFieldAuthorName.setText("");
		textFieldBookTitle.setText("");
		textFieldCatagory.setText("");
		textFieldPublisherName.setText("");
		textFieldPublisherPhoneNumber.setText("");
	}
}

package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import model.Error;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class UserActionFrame extends CustomLibraryFrame {

	/**
	 * Launch the application.
	 */
	public static void UserActionScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserActionFrame frame = new UserActionFrame();
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
	public UserActionFrame()
	{
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		JButton btnRegisterStudent = new CustomLibraryButton("Register Student");
		btnRegisterStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				RegisterStudentFrame.RegisterStudentScreen();
			}
		});
		btnRegisterStudent.setBounds(10, 21, 180, 23);

		m_Panel.add(btnRegisterStudent);
		
		JButton btnSearchStudent = new CustomLibraryButton("Search Student");
		btnSearchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				SearchStudentFrame.SearchStudentScreen();	
			}
		});
		btnSearchStudent.setBounds(10, 55, 180, 23);

		
		m_Panel.add(btnSearchStudent);
		
		JButton btnRemoveStudent = new CustomLibraryButton("Unregister Student");
		btnRemoveStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				UnregisterStudentFrame.UnregisterStudentScreen();
			}
		});
		btnRemoveStudent.setBounds(10, 89, 180, 23);

		m_Panel.add(btnRemoveStudent);
		
		JButton btnLateStudentOptions = new CustomLibraryButton("Late Student Options");
		btnLateStudentOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LateStudentOptionsFrame.LateStudentOptionsScreen();
			}
		});
		btnLateStudentOptions.setBounds(10, 123, 180, 23);
	
		m_Panel.add(btnLateStudentOptions);
		
		JButton btnStudentInformation = new CustomLibraryButton("View Student Information");
		btnStudentInformation.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ViewStudentInformationFrame.ViewStudentInformationScreen(); 
			}
		});
		btnStudentInformation.setBounds(10, 157, 180, 23);

		m_Panel.add(btnStudentInformation);
		
		JButton btnAddNewBook = new CustomLibraryButton("Add a Book");
		btnAddNewBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				AddABookFrame.AddABookScreen();
			}
		});
		btnAddNewBook.setBounds(244, 21, 180, 23);

		m_Panel.add(btnAddNewBook);
		
		JButton btnRemoveBook = new CustomLibraryButton("Remove a Book");
		btnRemoveBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				RemoveABookFrame.RemoveABookScreen();
			}
		});
		btnRemoveBook.setBounds(244, 55, 180, 23);

		m_Panel.add(btnRemoveBook);
		
		JButton btnLendBook = new CustomLibraryButton("Lend a Book");
		btnLendBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LendBookFrame.LendBookScreen();
			}
		});
		btnLendBook.setBounds(244, 89, 180, 23);

		m_Panel.add(btnLendBook);
		
		JButton btnReturnBook = new CustomLibraryButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnBookFrame.ReturnBookScreen();
			}
		});
		btnReturnBook.setBounds(244, 123, 180, 23);

		m_Panel.add(btnReturnBook);
		
		JButton btnBookInformation = new CustomLibraryButton("View Book Information");
		btnBookInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewBookInformationFrame.ViewBookInformationScreen();
			}
		});
		btnBookInformation.setBounds(244, 157, 180, 23);

		m_Panel.add(btnBookInformation);
		
		JButton btnViewAllBooks = new CustomLibraryButton("View All Books");
		btnViewAllBooks.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{	
				String infoToShow = "";
				
				try
				{
					infoToShow = controller.LibraryController.ControllerViewAllBooks();
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				} 
				
				if (infoToShow.intern() != "".intern())
				{
					view.PopUpMessage.ShowPopUpMessage(infoToShow);
				}
			}
		});
		btnViewAllBooks.setBounds(244, 191, 180, 23);
		m_Panel.add(btnViewAllBooks);
	}

}

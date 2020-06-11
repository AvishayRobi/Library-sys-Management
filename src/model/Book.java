package model;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Book class, Implements the "Information" interface.
 */
public class Book implements Information
{
	private static final String k_LendingHistoryLog = "Lending_log.txt";
	private static final String k_BooksDBFile = "BooksDB.txt";
	private static final int k_NumberOfAllowedBooksInLibrary = 500;


	// Fields
	private static Book m_BookInstance;
	private static int m_ObjCount = 0;
	private int m_ID;
	private String m_Title;
	private Author m_Author;
	private Publisher m_Publisher;
	private boolean m_IsLended;
	private String m_Category;

	// Constructor
	public Book()
	{
		m_ObjCount++;
	}
	
	private Book(String i_Title, Author i_Author, Publisher i_Publisher, String i_Category) throws IOException
	{
		m_ID = FileHandler.GetLastIDGenerated(k_BooksDBFile) + 1;
		m_Title = i_Title;
		m_Author = i_Author;
		m_Publisher = i_Publisher;
		m_IsLended = false;
		m_Category = i_Category;
		
		m_ObjCount++;
	}

	
	/**
	 * This method makes sure that the amount of books the library
	 * can contains is no more than specified amount.
	 * @param i_Title - String
	 * @param i_Author - Author type object
	 * @param i_Publisher - Publisher type object
	 * @param i_Category - String
	 * @return Book type object
	 * @throws IOException
	 */
	public static synchronized Book GetBookInstance(String i_Title, Author i_Author, Publisher i_Publisher, String i_Category) throws IOException
	{
		if(m_ObjCount < k_NumberOfAllowedBooksInLibrary)
		{
			m_BookInstance = new Book(i_Title, i_Author, i_Publisher, i_Category);
		}
		
		return m_BookInstance;
	}
	
	/**
	 * This method makes sure that the amount of books the library
	 * can contains is no more than specified amount.
	 * @return Book type Object
	 */
	public static synchronized Book GetBookInstance()
	{
		if(m_ObjCount < k_NumberOfAllowedBooksInLibrary)
		{
			m_BookInstance = new Book();
		}
		
		return m_BookInstance;
	}
	
	// Methods
	
	/**
	 * This method writes to the "Lending Log" database
	 * the information about the book that is being landed,
	 * the student that is lending, and the date of lending.
	 * @param i_StudentID - Student type object
	 * @throws IOException
	 */
	public void Lend(String i_StudentID) throws IOException
	{
		String fileName = "Lending_log.txt";
		
		// This string to append to file
		StringBuilder strToAppendFile = new StringBuilder();

		// Set book status to lent
		SetIsLended(true);
		
		// Change lending status in BooksDB
		FileHandler.UpdateLendingStatusInFile(k_BooksDBFile, String.valueOf(this.GetID()));

		/* File looks like: BookID, Lending date, Return date, StudentID */

		// Set Book ID
		strToAppendFile.append(m_ID);
		addCommaAndSpaceToStringBuilder(strToAppendFile);

		// Set lending date
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		strToAppendFile.append(currentDate);
		addCommaAndSpaceToStringBuilder(strToAppendFile);

		// Set return date to NULL
		strToAppendFile.append("NULL");
		addCommaAndSpaceToStringBuilder(strToAppendFile);

		// Set Student ID
		strToAppendFile.append(i_StudentID);

		// Add string to log file
		FileHandler.WriteToFile(fileName, strToAppendFile);
	}

	/**
	 * A generic method to add a comma and a space (", ") to an existing StringBuilder object. 
	 * @param i_String - StringBuilder type object
	 */
	private void addCommaAndSpaceToStringBuilder(StringBuilder i_String)
	{
		String commaAndSpace = ", ";

		i_String.append(commaAndSpace);
	}

	/**
	 * This method sets the lending logical state of a book to false.
	 */
	public void ReturnFromLend() {
		SetIsLended(false);
	}

	/**
	 * This method counts the amount of people that have lended a book.
	 * @return appearanceNumber - int type
	 * @throws IOException
	 */
	public int NumberOfPeopleLended() throws IOException
	{
		// Go through lended history, and count number of "lending date"
		String idInString = Integer.toString(m_ID);
		int appearanceNumber = 0;

		appearanceNumber = FileHandler.FindAppearanceCountByID(k_LendingHistoryLog, idInString);

		return appearanceNumber;
	}

	/**
	 * This method returns the last date of lending of a specific book.
	 * @return lastLendedDate - Date type object
	 * @throws IOException
	 * @throws ParseException
	 */
	public Date LastLendedDate() throws IOException, ParseException
	{
		// Book ID in string format
		String stringID = Integer.toString(m_ID);

		// This will store the last line appearance
		String[] lastAppearanceLine = FileHandler.FindLastAppearanceLineByID(k_LendingHistoryLog, stringID);

		// This will store the last lended date, the second value in the array
		String lastLendedDateInString = lastAppearanceLine[1];

		// Convert string to date
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date lastLendedDate = df.parse(lastLendedDateInString);

		// Return result
		return lastLendedDate;
	}

	/**
	 * This method returns the most recent student that have lended a book.
	 * @return studentToReturn - Student type object
	 * @throws IOException
	 */
	public Student LastStudentLended() throws IOException
	{
		// Book ID in string format
		String stringID = Integer.toString(m_ID);

		// This will store the last line appearance
		String[] lastAppearanceLine = FileHandler.FindLastAppearanceLineByID(k_LendingHistoryLog, stringID);

		// This will store the last student id, the fourth value in the array
		String lastLendedStudentID = lastAppearanceLine[3];

		// Convert ID to student object
		Student studentToReturn;
		studentToReturn = Library.SearchStudentByID(lastLendedStudentID);

		// Return the result
		return studentToReturn;
	}

	// Getters
	public int GetID()
	{
		return m_ID;
	}

	public String GetTitle()
	{
		return m_Title;
	}

	public Author GetAuthor()
	{
		return m_Author;
	}

	public Publisher GetPublisher()
	{
		return m_Publisher;
	}

	public String GetCategory()
	{
		return m_Category;
	}

	public boolean GetIsLended() 
	{
		return m_IsLended;
	}

	// Setters
	public void SetID(int i_ID) 
	{
		m_ID = i_ID;
	}

	public void SetTitle(String i_Title)
	{
		m_Title = i_Title;
	}

	public void SetAuthor(Author i_Author)
	{
		m_Author = i_Author;
	}

	public void SetPublisher(Publisher i_Publisher)
	{
		m_Publisher = i_Publisher;
	}

	public void SetIsLended(boolean i_IsLended)
	{
		m_IsLended = i_IsLended;
	}
	
	public void SetCategory(String i_Category)
	{
		m_Category = i_Category;
	}

	/**
	 * This method shows the specific book information to the user by a pop up message.
	 */
	@Override
	public void ShowInformation()
	{	
		StringBuilder message = new StringBuilder();
		
		message.append("Title: " + this.GetTitle());
		message.append(System.lineSeparator());
		message.append("Author: " + this.GetAuthor().GetFirstName()+" "+ this.GetAuthor().GetLastName());
		message.append(System.lineSeparator());
		message.append("Category: " + this.GetCategory());
		message.append(System.lineSeparator());
		message.append("Publisher: " + this.GetPublisher().GetName());
		message.append(System.lineSeparator());
		
		if (this.m_IsLended)
		{
			message.append("The Book is Lended");
		}
		else
		{
			message.append("The Book is not Lended");

		}
				
		view.PopUpMessage.ShowPopUpMessage(message.toString());
	}
}
package model;
import java.io.IOException;
import java.util.ArrayList;

public class Student extends Person implements Information
{
	// Fields
	protected int m_StudentID;
	protected int m_PhoneNumber;
	protected ArrayList<Course> m_CoursesTaken = new ArrayList<Course>();
	//protected ArrayList<LendingHistory> m_LendingHistory;
	protected ArrayList<Book> m_CurrentBooksLended = new ArrayList<Book>();;
	protected int m_NumOfCurrentLendedBooks = 0;

	// Constructor
	public Student(int i_StudentID, String i_FirstName, String i_LastName, int i_PhoneNumber, int i_NumOfCurrentLendedBooks, ArrayList<Course> i_Courses) {
		super(i_FirstName, i_LastName);
		SetStudentID(i_StudentID);
		SetPhoneNumber(i_PhoneNumber);
		SetNumberOfCurrentLendedBooks(i_NumOfCurrentLendedBooks);
		m_CoursesTaken = i_Courses;		
	}
	
	public Student()
	{
		super("","");
	}

	// Methods	
	public int GetStudentID()
	{
		return m_StudentID;
	}

	public ArrayList<Course> GetCoursesTaken()
	{
		return m_CoursesTaken;
	}	

	public ArrayList<Book> GetCurrentBooksLanded()
	{
		return m_CurrentBooksLended;
	}

	public int GetNumberOfCurrentLendedBooks() 
	{
		return m_NumOfCurrentLendedBooks;
	}

	public int GetPhoneNumber()
	{
		return m_PhoneNumber;
	}

	public void SetStudentID(int i_StudentID)
	{
		m_StudentID = i_StudentID;
	}

	public void SetCoursesTaken(ArrayList<Course> i_Courses)
	{
		int howManyCourses;
		int i; // For loop

		howManyCourses = i_Courses.size();
		for (i = 0; i < howManyCourses; i++) {
			addCourseToCourseList(i_Courses.get(i));
		}
	}

	private void addCourseToCourseList(Course i_Course)
	{
		m_CoursesTaken.add(i_Course);
	}

	public void SetCurrentBooksLended(ArrayList<Book> i_BookList)
	{
		m_CurrentBooksLended = i_BookList;
	}

	public void SetNumberOfCurrentLendedBooks(int i_AmountOfLendedBooks)
	{
		m_NumOfCurrentLendedBooks = i_AmountOfLendedBooks;
	}

	public void SetPhoneNumber(int i_PhoneNumber) 
	{
		m_PhoneNumber = i_PhoneNumber;
	}

	public StringBuilder SuggestBooks() throws NumberFormatException, IOException 
	{
		StringBuilder suggestedBooks = new StringBuilder();
		String newLine = System.lineSeparator();
		int numOfBooks;

		numOfBooks = Library.GetNumberOfBooksInLibrary();
		ArrayList<Book> bookArrayList = Library.GetBooks();
		
		for (int i = 0; i < numOfBooks; i++) {
			Book book;
			String bookCategory;
			int numOfStudentCourses;

			book = bookArrayList.get(i);
			bookCategory = book.GetCategory();
			numOfStudentCourses = getNumOfCourses();

			for (int j = 0; j < numOfStudentCourses; j++) 
			{
				String courseOfStudent;
				boolean isCourseSameAsCategory;

				courseOfStudent = m_CoursesTaken.get(j).GetCourseName();
				isCourseSameAsCategory = courseOfStudent.intern() == bookCategory.intern();

				if (isCourseSameAsCategory)
				{
					suggestedBooks.append(book.GetTitle());
					suggestedBooks.append(newLine);
				}
			}
		}

		return suggestedBooks;
	}

	private int getNumOfCourses()
	{
		return m_CoursesTaken.size();
	}

	@Override
	public void ShowInformation() 
	{		
		StringBuilder message = new StringBuilder();
		
		message.append("ID: " + this.GetStudentID());
		message.append(System.lineSeparator());
		message.append("First Name: " + this.GetFirstName());
		message.append(System.lineSeparator());
		message.append("Last Name: " + this.GetLastName());
		message.append(System.lineSeparator());			
		message.append("Phone Number: " + this.GetPhoneNumber());
		message.append(System.lineSeparator());
		message.append("Courses: " + coursesToString());
				
		view.PopUpMessage.ShowPopUpMessage(message.toString());
	}
	
	private String coursesToString()
	{
		StringBuilder strToReturn = new StringBuilder();
		String finalStringToReturn;
		
		for (Course course : m_CoursesTaken)
		{
			strToReturn.append(course.GetCourseName());
			strToReturn.append(", ");
		}		
		
		finalStringToReturn = strToReturn.substring(0, strToReturn.length() - 2);
		
		return finalStringToReturn;
	}		
}

	


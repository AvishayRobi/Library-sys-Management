package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Library
{
	private static final String k_StudentDBFile = "StudentsDB.txt";
	private static final String k_BooksDBFile = "BooksDB.txt";
	private static final String k_AuthorDB = "AuthorDB.txt";
	private static final String k_PublisherDB = "PublisherDB.txt";
	private static final String k_BookNotLended = "0";
	private static final String k_BookIsLended = "1";
	private static final String k_LendingLogDBFile = "Lending_Log.txt";
	private static final int k_AllowedLateDays = 14;

	// Fields
	private static ArrayList<Book> m_Books = new ArrayList<Book>();
	private static ArrayList<Student> m_Students = new ArrayList<Student>();

	// Constructor ??? static

	// Methods
	public static void UpdateStudentList() throws NumberFormatException, IOException 
	{
		/* Update list of students from DB to ArrayList */
		String sCurrentLine;

		BufferedReader br = new BufferedReader(new FileReader(k_StudentDBFile));

		while ((sCurrentLine = br.readLine()) != null)
		{
			String[] lineValues = sCurrentLine.split(","); // Split the string on spaces into array
			Student studentToAdd = new Student(); //TODO id

			studentToAdd.SetStudentID(Integer.parseInt(lineValues[0]));
			studentToAdd.SetPhoneNumber(Integer.parseInt(lineValues[1]));
			studentToAdd.SetFirstName(lineValues[2]);
			studentToAdd.SetLastName(lineValues[3]);
			studentToAdd.SetCoursesTaken(stringCoursesTakenToArrayList(lineValues[5]));
		

			m_Students.add(studentToAdd);
		}
		
		br.close();
	}	
	
	private static ArrayList<Course> stringCoursesTakenToArrayList(String i_Courses)
	{
		/* String looks like: CourseTaken^Cat|CourseTaken^Cat|CourseTaken^Cat */
		
		// Split string to each course
		String[] strSplitted = i_Courses.split("\\|", -1);
		
		// Count how many courses
		int numOfCourses = strSplitted.length;
		
		// Array list to return
		ArrayList<Course> courseTakenList = new ArrayList<Course>();
		
		for (int i = 0; i < numOfCourses; i++)
		{
			// Course to add to list
			Course currentCourse = new Course();
			
			currentCourse.SetCourseName(strSplitted[i]);
			currentCourse.SetCategory(generateCategory());
			// [0] = CourseName, [1] = Category
			//String[] splittedCourseNameAndCategory = splitCategoryString(strSplitted[i]);
			
			// Add to current course that will be added to array list
			//currentCourse.SetCourseName(splittedCourseNameAndCategory[0]);
			//currentCourse.SetCategory(splittedCourseNameAndCategory[1]);
			
			
			// Add to array list
			courseTakenList.add(currentCourse);
		}
		
		return courseTakenList;		
	}
	
	public static String generateCategory()
	{
		String[] categories = {"Computer Science", "Design", "Engineering", "Physics", "Math", "Electronics"};
		int categoryNumber = categories.length;
		Random rnd = new Random();	
		
		return categories[rnd.nextInt(categoryNumber)];
	}

	private static void addBookToBookList(Book i_Book) 
	{
		m_Books.add(i_Book);
	}

	private static void addStudentToStudentList(Student i_Student) 
	{
		m_Students.add(i_Student);
	}

	public static boolean RegisterStudent(Student i_Student) 
	{
		/*
		 * File looks like: ID, PhoneNumber, CourseTaken^Cat|CourseTaken^Cat|CourseTaken^Cat, 
		 * CurrentBookLended|CurrentBookLended|CurrentBookLended,
		 * NumberOfCurrentBooksLended, FirstName, LastName
		 */
		StringBuilder stringToAppendFile = new StringBuilder();
		String coursesTaken;

		// Add ID
		stringToAppendFile.append(i_Student.GetStudentID());
		addCommaToStringBuilder(stringToAppendFile);

		// Add phone number
		stringToAppendFile.append(i_Student.GetPhoneNumber());
		addCommaToStringBuilder(stringToAppendFile);
		
		// Add first name
		stringToAppendFile.append(i_Student.GetFirstName());
		addCommaToStringBuilder(stringToAppendFile);
		
		// Add last name
		stringToAppendFile.append(i_Student.GetLastName());
		addCommaToStringBuilder(stringToAppendFile);
		//addCommaAndSpaceToStringBuilder(stringToAppendFile);	
		
		// Add number of current books lended
		//MAYBE NOT NEEDED IN THE BEGINING
		stringToAppendFile.append(i_Student.GetNumberOfCurrentLendedBooks());
		addCommaToStringBuilder(stringToAppendFile);

		// Add courses taken
		coursesTaken = setCoursesTakenForFile(i_Student.GetCoursesTaken());
		stringToAppendFile.append(coursesTaken);
		addCommaToStringBuilder(stringToAppendFile);

		// Append to file
		return FileHandler.WriteToFile(k_StudentDBFile, stringToAppendFile);
	}
	
	private static String setCoursesTakenForFile(ArrayList<Course> i_Courses)
	{
		StringBuilder courses = new StringBuilder();
		int i;
		int numOfCoursesWithoutLastOne = i_Courses.size() - 1;
		
		for(i = 0; i < numOfCoursesWithoutLastOne; i++)
		{
			courses.append(i_Courses.get(i).GetCourseName());
			//courses.append("|");
		}
		
		// Add last courses, dont put | after
		courses.append(i_Courses.get(i).GetCourseName());
		
		return courses.toString();
	}

	public static void RemoveStudent(Student i_Student) throws IOException 
	{
		// ID of student we want to remove
		String idToRemove = Integer.toString(i_Student.GetStudentID());

		// Remove line
		FileHandler.RemoveLineFromFile(k_StudentDBFile, idToRemove);

		// Update student list with new changes
		UpdateStudentList();
	}

	public static Student SearchStudentByID(String i_ID) 
	{
		Student studentToReturn = SearchStudentByIDWithoutPopUp(i_ID);
		boolean isStudentFound = studentToReturn.GetFirstName().intern() != "".intern();
		
		if (!isStudentFound) 
		{
			view.PopUpMessage.ShowPopUpMessage("Student Not found!");
		}

		return studentToReturn;
	}
	
	public static Student SearchStudentByIDWithoutPopUp(String i_ID)
	{
		// Calculate number of students in library
		int numberOfStudentsInLibrary = GetNumberOfStudentsInLibrary();

		// Assuming student will not be found. If he is, it will be updated to true
		Student studentToReturn = new Student();
		boolean isStudentFound = false;

		for (int i = 0; i < numberOfStudentsInLibrary; i++) 
		{
			Student currentStudent = m_Students.get(i);
			String currentStudentID = Integer.toString(currentStudent.GetStudentID());
			boolean isIDMatch = i_ID.intern() == currentStudentID.intern();

			if (isIDMatch) 
			{
				studentToReturn = currentStudent;
				isStudentFound = true;
				break;
			}
		}		

		return studentToReturn;
	}

	public static ArrayList<Student> SearchStudentByFirstAndLastName(String i_FirstName, String i_LastName) 
	{
		// Calculate number of students in library
		int numberOfStudentsInLibrary = GetNumberOfStudentsInLibrary();

		// There can be a few students with the same first and last name!
		ArrayList<Student> studentsToReturn = new ArrayList<Student>();

		// Assuming student will not be found. If he is, it will be updated to true
		boolean isAtLeastOneStudentFound = false;

		for (int i = 0; i < numberOfStudentsInLibrary; i++) 
		{
			Student currentStudent = m_Students.get(i);
			boolean isFirstNameMatch = i_FirstName.intern() == currentStudent.GetFirstName().intern();
			boolean isLastNameMatch = i_LastName.intern() == currentStudent.GetLastName().intern();

			if (isFirstNameMatch && isLastNameMatch) 
			{
				studentsToReturn.add(currentStudent);
				isAtLeastOneStudentFound = true;
			}
		}

		if (!isAtLeastOneStudentFound) 
		{
			view.PopUpMessage.ShowPopUpMessage("Not found!");
		}

		return studentsToReturn;
	}

	public static void UpdateBookList() throws NumberFormatException, IOException
	{
		/* Update list of books from DB to ArrayList */
		String sCurrentLine;
		m_Books = new ArrayList<Book>();
		
		BufferedReader br = new BufferedReader(new FileReader(k_BooksDBFile));		

		while ((sCurrentLine = br.readLine()) != null) 
		{
			/* File looks like: BookID, Harry Potter, AuthorID, value.... */
			String[] lineValues = sCurrentLine.split(","); // Split the string on spaces into array
			Book bookToAdd = Book.GetBookInstance();
			String[] authorName = lineValues[2].split("\\|");
			String[] publisher = lineValues[3].split("\\|");
			Author newAuthor = new Author(authorName[0],authorName[1]);
			Publisher newPublisher = new Publisher(publisher[0],Integer.parseInt(publisher[1]));
			bookToAdd.SetID(Integer.parseInt(lineValues[0].substring(0, lineValues[0].length())));
			bookToAdd.SetTitle(lineValues[1].substring(0, lineValues[1].length()));
			bookToAdd.SetAuthor(newAuthor);
			bookToAdd.SetPublisher(newPublisher);
			bookToAdd.SetCategory(lineValues[4].substring(0, lineValues[4].length() - 1));
			bookToAdd.SetIsLended(lineValues[5].intern() == k_BookIsLended.intern());
			
			m_Books.add(bookToAdd);			
		}
		
		br.close();
	}
	
	public static Publisher findPublisherByID(int i_ID) throws IOException
	{
		/*File looks like: PublisherID, Name, Phone*/
		String lineInFile = FileHandler.FindRowByID(k_PublisherDB, Integer.toString(i_ID));
		
		// [0] = PublisherID, [1] = Name, [2] = Phone
		String[] splittedValues = lineInFile.split(",");
		
		// Publisher to return
		Publisher pubToReturn = new Publisher();
		
		// Calc and set ID
		int pubID = Integer.parseInt(splittedValues[0]);
		pubToReturn.SetID(pubID);
		
		// Calc and set Name
		String pubName = splittedValues[1];
		pubToReturn.SetName(pubName);
		
		// Calc and set PhoneNumber
		int pubPhoneNumber = Integer.parseInt(splittedValues[2]);
		pubToReturn.SetPhoneNumber(pubPhoneNumber);
		
		return pubToReturn;
	}
	
	
	public static Author findAuthorByID(int i_ID) throws IOException
	{
		// File looks like: AuthorID, FirstName, LastName*/
		String lineInFile = FileHandler.FindRowByID(k_AuthorDB, Integer.toString(i_ID));
		
		// [0] = AuthorID, [1] = FirstName, [2] = LastName
		String[] splittedValues = lineInFile.split(",");
		
		// Author to return
		Author authorToReturn = new Author();
		
		// Calc and set FirstName
		String authorFirstName = splittedValues[1];
		authorToReturn.SetFirstName(authorFirstName);
		
		// Calc and set LastName
		String authorLastName = splittedValues[2];
		authorToReturn.SetLastName(authorLastName);
		
		return authorToReturn;		
	}	

	

	public static void InsertBook(Book i_Book) throws NumberFormatException, IOException
    {
        /* File looks like: Title, Author, Publisher, Category */
        StringBuilder stringToAppendFile = new StringBuilder();
        
        //Add comma and space after every value, except the last one (??)
        // Add ID
        stringToAppendFile.append(i_Book.GetID());
        addCommaToStringBuilder(stringToAppendFile);
        
        // Add title
        stringToAppendFile.append(i_Book.GetTitle());
        addCommaToStringBuilder(stringToAppendFile);
        
        // Add author
        // AuthorID|FirstName|LastName
        Author bookAuthor = i_Book.GetAuthor();

        stringToAppendFile.append(bookAuthor.GetFirstName());
        stringToAppendFile.append("|");
        stringToAppendFile.append(bookAuthor.GetLastName());
        addCommaToStringBuilder(stringToAppendFile);
        
        // Add publisher
        // PublisherID|Name|PhoneNumber
        Publisher bookPublisher = i_Book.GetPublisher();

        stringToAppendFile.append(bookPublisher.GetName());
        stringToAppendFile.append("|");
        stringToAppendFile.append(bookPublisher.GetPhoneNumber());
        addCommaToStringBuilder(stringToAppendFile);
        
        // Add category
        stringToAppendFile.append(i_Book.GetCategory());
        addCommaToStringBuilder(stringToAppendFile);     
        
        // Set lended or not: 1 if lended, 0 if not lended
        stringToAppendFile.append(k_BookNotLended);
        
        // Append to file
        FileHandler.WriteToFile(k_BooksDBFile, stringToAppendFile);
        UpdateBookList();
    }

	public static void addCommaToStringBuilder(StringBuilder i_String) 
	{
		String commaAndSpace = ",";

		i_String.append(commaAndSpace);
	}

	public static void RemoveBookFromDB(String i_BookID) throws IOException
	{
		// Remove book by ID
		FileHandler.RemoveLineFromFile(k_BooksDBFile, i_BookID);

		// Update book list with new changes
		UpdateBookList();
	}

	public static Book SearchBookByID(String i_ID) 
	{
		// Calculate number of books in library
		int numberOfBooksInLibrary = GetNumberOfBooksInLibrary();

		// Assuming book will not be found. If is is, it will be updated to true
		Book bookToReturn = null;
		boolean isBookFound = false;

		for (int i = 0; i < numberOfBooksInLibrary; i++) 
		{
			Book currentBook = m_Books.get(i);
			String bookID = Integer.toString(currentBook.GetID());
			
			boolean isIDMatch = i_ID.intern() == bookID.intern();

			if (isIDMatch) {
				bookToReturn = currentBook;
				isBookFound = true;
				break;
			}
		}

		if (!isBookFound) 
		{
			view.PopUpMessage.ShowPopUpMessage("Not found!");
		}

		return bookToReturn;
	}

	public static ArrayList<Book> SearchBookByTitle(String i_Title) 
	{
		// Calculate number of books in library
		int numberOfBooksInLibrary = GetNumberOfBooksInLibrary();

		// Assuming no book will be found. If at least one is, it will be updated to
		// true
		ArrayList<Book> booksToReturn = new ArrayList<Book>();
		boolean isAtLeastOneBookFound = false;

		for (int i = 0; i < numberOfBooksInLibrary; i++) 
		{
			Book currentBook = m_Books.get(i);

			/*
			 * Match part of the title. For example, if book title is:
			 * "Harry Potter and the Chamber of Secrets", And the desired title is
			 * "Harry Potter", It is a match.
			 */
			boolean isTitleMatch = currentBook.GetTitle().toLowerCase().contains(i_Title.toLowerCase());

			if (isTitleMatch) {
				booksToReturn.add(currentBook);
				isAtLeastOneBookFound = true;
			}
		}

		if (!isAtLeastOneBookFound) 
		{
			view.PopUpMessage.ShowPopUpMessage("Not found!");
		}

		return booksToReturn;
	}

	// Getters
	public static ArrayList<Book> GetBooks() throws NumberFormatException, IOException
	{
		UpdateBookList();

		return m_Books;
	}

	public static ArrayList<Student> GetStudents() throws NumberFormatException, IOException
	{
		UpdateStudentList();

		return m_Students;
	}

	public static int GetNumberOfBooksInLibrary() 
	{
		return m_Books.size();
	}

	public static int GetNumberOfStudentsInLibrary()
	{
		return m_Students.size();
	}

	// Setters
	public static void SetBooks(ArrayList<Book> i_Books) 
	{
		int numberOfBooks = i_Books.size();

		for (int i = 0; i < numberOfBooks; i++) {
			addBookToBookList(i_Books.get(i));
		}
	}

	public static void SetStudents(ArrayList<Student> i_Students)
	{
		int numberOfStudents = i_Students.size();

		for (int i = 0; i < numberOfStudents; i++) {
			addStudentToStudentList(i_Students.get(i));
		}
	}
	
	public static boolean IsStudentLate(String i_StudentID) throws NumberFormatException, IOException, ParseException
	{
		boolean isDateDiffMoreThanTwoWeeks = false;
		java.util.Date dateToday = Calendar.getInstance().getTime();
		java.util.Date dateLended;		
		String sCurrentLine;
		boolean isIDMatch;
		
		BufferedReader br = new BufferedReader(new FileReader(k_LendingLogDBFile));		

		while ((sCurrentLine = br.readLine()) != null) 
		{
			/* File looks like: BookID, LendingDate, ReturnDate, StudentID */
			String[] lineValues = sCurrentLine.split(","); // Split the string on spaces into array
			isIDMatch = lineValues[3].intern() == i_StudentID.intern();
			
			if (isIDMatch)
			{
			    dateLended = new SimpleDateFormat("dd/MM/yyyy").parse(lineValues[1]);  
			    if (checkDateDiff(dateToday, dateLended, k_AllowedLateDays))
			    {
			    	isDateDiffMoreThanTwoWeeks = true;
			    	break;
			    }
			}			
		}
		
		br.close();
		
		return isDateDiffMoreThanTwoWeeks;
	}
	
	private static boolean checkDateDiff(java.util.Date i_Date1, java.util.Date i_Date2, int i_Days)
	{
		return Math.abs(i_Date1.getTime() - i_Date2.getTime()) > i_Days;
	}
}
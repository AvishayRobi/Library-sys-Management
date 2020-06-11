package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.AuthenticationException;
import model.Course;
import model.Error;
import model.Library;
import model.Student;

/**
 * This class contains methods that validate given information about the Student,
 * and invokes methods in the "Model" package.
 * This class acts as the controller between the "View" package and the "Model" package.
 */
public class LibraryController
{
	// Regex for checking input
	private static final String k_LEttersOnlyRegex = "^[A-Za-z]+$";
	private static final String k_DigitsOnlyRegex = "^[0-9]+$";
	
	// No books lent on register
	private static final int k_NoBooksIssued= 0;
	
	/**
	 * This method creates a list of all the books in the library in the form of "ID : Title".
	 * @return String representation of all the books in the library
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static String ControllerViewAllBooks() throws NumberFormatException, IOException
	{
		StringBuilder booksInformation = new StringBuilder();
		ArrayList<model.Book> books = new ArrayList<model.Book>();
		String newLine = System.lineSeparator();
		
		books = Library.GetBooks();
		booksInformation.append(String.format("ID : Title%s", newLine));
		for (model.Book book : books)
		{
			booksInformation.append(String.format("%s : %s%s", book.GetID(), book.GetTitle(), newLine));
		}
		
		return booksInformation.toString();
	}
	
	/**
	 * Invokes "RemoveBook" method in the "Model" package
	 * @param  i_BookID
	 * @throws IOException
	 */
	public static void ControllerRemoveBook(String i_BookID) throws IOException
	{
		model.Library.RemoveBookFromDB(i_BookID);
	}
	
	/**
	 * Invokes InsertBook method in Library package.
	 * If an error occurs, it will be saved in the error database.
	 * @param i_authorNameSplitted - String
	 * @param i_splittedInformation - String
	 * @throws IOException
	 * @throws AuthenticationException 
	 */
	public static void ControllerAddNewBook(String[] i_authorNameSplitted, String[] i_splittedInformation) throws IOException, IllegalArgumentException
	{
		model.Book newBook = new model.Book();
		
		model.Author newAuthor;
		try
		{
			newAuthor = new model.Author(i_authorNameSplitted[0], i_authorNameSplitted[1]);
		} 
		catch (Exception e)
		{
			Error newError = new Error("book add; invalid book info");
			newError.NewError();
			throw new IllegalArgumentException("Author Must Have First And Last Name");
		}
			model.Publisher newPublisher = new model.Publisher(i_splittedInformation[2], Integer.parseInt(i_splittedInformation[4]));
			newBook = model.Book.GetBookInstance(i_splittedInformation[0], newAuthor ,newPublisher , i_splittedInformation[3]);
			
			model.Library.InsertBook(newBook);
	}
	
	/**
	 * This method splits the given information so it can be processed
	 * and passed to the book database. 
	 * @param i_bookTitle - String
	 * @param i_catagory - String
	 * @param i_publisherName - String
	 * @param i_authorName - String
	 * @param i_PublisherPhone - String
	 * @return splittedInformation - array of strings.
	 */
	public static String[] ControllerNewBookInformation(String i_bookTitle, String i_catagory, String i_publisherName, String i_authorName, String i_PublisherPhone)
	{	
		String emptyString = "";
		StringBuilder bookInformationString = new StringBuilder();
		String[] splittedInformation;

		i_bookTitle = i_bookTitle.trim();
		i_catagory = i_catagory.trim();
		i_publisherName = i_publisherName.trim();
		i_authorName = i_authorName.trim();
		
		if(i_bookTitle.intern() == emptyString.intern() || i_authorName.intern() == emptyString.intern() || i_PublisherPhone.intern() == emptyString.intern() || !CheckIfPhoneValid(i_PublisherPhone)) 
		{
			view.PopUpMessage.ShowPopUpMessage("Invalid Input!");
		}
		else
		{
			bookInformationString.append(i_bookTitle);
			Library.addCommaToStringBuilder(bookInformationString);
			bookInformationString.append(i_authorName);
			Library.addCommaToStringBuilder(bookInformationString);
		}
		
		if(i_publisherName.intern() == emptyString.intern())
		{
			bookInformationString.append("Miscellaneous"); // No publisher
			Library.addCommaToStringBuilder(bookInformationString);
		}
		else
		{
			bookInformationString.append(i_publisherName);
			Library.addCommaToStringBuilder(bookInformationString);
		}
		
		if(i_catagory.intern() == emptyString.intern())
		{
			bookInformationString.append(Library.generateCategory());
			Library.addCommaToStringBuilder(bookInformationString);
		}
		else
		{
			bookInformationString.append(i_catagory);
			Library.addCommaToStringBuilder(bookInformationString);
		}
		
		bookInformationString.append(i_PublisherPhone);
		splittedInformation = bookInformationString.toString().split(",");
		
		return splittedInformation;
	}
	
	/**
	 * This method searches for a student by his/her name.
	 * Input must consists of a first name and a last name.
	 * Input example: "John Doe".
	 * If input is not valid, a pop message is shown to the user.
	 * @param i_TextFromTextField - String
	 * @return
	 */
	public static String ControllerSearchStudentByName(String i_TextFromTextField)
	{	
		model.Student student;
		String stringOfInformation;
		StringBuilder stringOfStudents = new StringBuilder();
		ArrayList<model.Student> studentArrayList = new ArrayList<model.Student>();
		String[] fullName;
		String firstName, lastName="";
		int numOfStudentFound;
		
		fullName = i_TextFromTextField.split(" ");
		firstName = fullName[0];
		
		try
		{
			lastName = fullName[1];
		}
		catch (Exception e) 
		{
			view.PopUpMessage.ShowPopUpMessage("Last name Cannot Be Empty!");
			Error newError = new Error("search; last name was null");
			newError.NewError();
		}
		
		if (lastName != "".intern())
		{	
			
			studentArrayList = Library.SearchStudentByFirstAndLastName(firstName, lastName);
			numOfStudentFound = studentArrayList.size();

			if(numOfStudentFound > 0)
			{
				
				for(int i = 0; i < numOfStudentFound; i++)
				{
					student = studentArrayList.get(i);
					stringOfInformation = studentInformationToAppend(student);
					stringOfStudents.append(stringOfInformation);
				}
			}
		}
		
		return stringOfStudents.toString();
	}
	
	/**
	 * This method processes the student information
	 * to be passed on by the calling method
	 * @param i_Student - Student type object
	 * @return student - Student type object
	 */
	public static String studentInformationToAppend(Student i_Student)
	{
		StringBuilder stringToPrint = new StringBuilder();
		String newLine = System.lineSeparator();
			
		stringToPrint.append("Student ID: " + i_Student.GetStudentID() + newLine
				+ "Firstname: " + i_Student.GetFirstName() + newLine
				+ "Lastname:" + i_Student.GetLastName() + newLine
				+ "Phone: " + i_Student.GetPhoneNumber() + newLine
				+ newLine);
		
		return stringToPrint.toString();
	}	
		
	/**
	 * This method invokes the "SearchStudentByID" method,
	 * and passes the student found to the calling method.
	 * @param i_TextFromTextField - String
	 * @return student - Student type object
	 */
	public static Student ControllerSearchStudentByID(String i_TextFromTextField)
	{
		Student student;

		student = Library.SearchStudentByID(i_TextFromTextField);
		
		return student;
	}
	 
	/**
	 * This method processes the given student information,
	 * validates it, and invokes the "RegisterStudent" method in the "Model" package.
	 * If the student already exists, a pop message is shown to the user. 
	 * @param i_StudentInfo - StringBuilder type object
	 * @return registeredSuccessfully - boolean type
	 */
	public static boolean ControllerRegisterStudent(StringBuilder i_StudentInfo)
	{
		try
		{
			// [0] = StudentID, [1] = PhoneNumber, [2] = StudentFirstNAme, [3] = StudentLastName,  [4]:[n]= course list
			String[] studentInfo = i_StudentInfo.toString().split(",");

			// Split string into variables
			int studentID = Integer.parseInt(studentInfo[0]);
			String firstName = studentInfo[2];
			String lastName = studentInfo[3];
			int phoneNumber = Integer.parseInt(studentInfo[1]);
			String[] courseArray = studentInfo[4].split("|");			
		
			// Check if register successful
			boolean registeredSuccessfully = false;
			boolean isValidInput;
			boolean isStudentAlreadyExist = false;
			Student studentFromID = Library.SearchStudentByIDWithoutPopUp(Integer.toString(studentID));			
			
			if(studentFromID.GetFirstName().intern() != "".intern())
			{
				isStudentAlreadyExist = true;
				view.PopUpMessage.ShowPopUpMessage("Student already exists!");
			}
			
			// Create new ArrayList<E>ray list
			ArrayList<Course> courseArrayList = new ArrayList<Course>();
			int i;
			for(i = 0; i < courseArray.length; i++)
			{
				Course courseToAdd = new Course();
				courseToAdd.SetCourseName(courseArray[i]);
				courseArrayList.add(courseToAdd);
			}			
			
			// Check for valid input
			isValidInput = checkIfNameAndPhoneNumberAreValid(firstName, lastName, phoneNumber) && !isStudentAlreadyExist;
		
			// Information is valid, registering student to database
			if(isValidInput)
			{
				model.Student newStudent = new model.Student(studentID, firstName, lastName, phoneNumber, k_NoBooksIssued, courseArrayList); //problem
				
				// Returns true or false
				registeredSuccessfully = model.Library.RegisterStudent(newStudent);
			}
			
			// Update student list to refresh data
			model.Library.UpdateStudentList();
			
			// Insert student to DB		
			return registeredSuccessfully;
		}		
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * This method checks if the given name and phone number are valid by invoking
	 * the "checkInformationByRegex" method.
	 * @param i_FirstName - String
	 * @param i_LastName - String
	 * @param i_PhoneNumber - int
	 * @return true - if name and phone number are valid
	 */
	private static boolean checkIfNameAndPhoneNumberAreValid(String i_FirstName, String i_LastName, int i_PhoneNumber)
	{	
		boolean isFirstNameValid = checkInformationByRegex(i_FirstName, k_LEttersOnlyRegex);
		boolean isLastNameValid = checkInformationByRegex(i_LastName, k_LEttersOnlyRegex);
		boolean isPhoneNumberValid = checkInformationByRegex(Integer.toString(i_PhoneNumber), k_DigitsOnlyRegex);
		
		// True if all information is valid
		return (isFirstNameValid && isLastNameValid && isPhoneNumberValid);
	}

	/**
	 * This method validates the given information by using regex.
	 * @param i_String - String
	 * @param i_Regex - String
	 * @return isInformationValid - boolean type
	 */
	private static boolean checkInformationByRegex(String i_String, String i_Regex)
	{		
		Pattern patternAllowed;
		Matcher matcher;
		boolean isInformationValid;
		
		patternAllowed = Pattern.compile(i_Regex);
		matcher = patternAllowed.matcher(i_String);
		isInformationValid = matcher.matches();
		
		return isInformationValid;
	}

	/**
	 * This method checks if the given phone number can be parsed to an integer.
	 * @param i_StrFromPhoneTB - String
	 * @return isTextValidTrue - boolean type
	 */
	public static boolean CheckIfPhoneValid(String i_StrFromPhoneTB)
	{
		boolean isTextValidTrue = true;
		i_StrFromPhoneTB.trim();
		char[] stringArray = i_StrFromPhoneTB.toCharArray();
		
		// Check if given text is digits only
		for(int i = 0 ; i < stringArray.length ; i++)
		{
			if(!Character.isDigit(stringArray[i]))
			{
				isTextValidTrue = false;
			}
		}
		
		boolean isNameEmpty = i_StrFromPhoneTB.isEmpty();
		boolean isAllSpaces = AuthorController.isStringAllSpaces(i_StrFromPhoneTB);
		
		if(isNameEmpty || isAllSpaces)
		{
			isTextValidTrue = false;
		}
		
		return isTextValidTrue;
	}

	/**
	 * This method checks if the student exists in the database
	 * @param i_StudentID
	 * @return true - if the student exists.
	 */
	public static boolean isStudentExists(String i_StudentID)
	{
		model.Student student = model.Library.SearchStudentByID(i_StudentID);
		
		return (student.GetFirstName().intern() != "".intern());
	}
}
	
	

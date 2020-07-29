package testPackage;

import controller.LibraryController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestLibraryController
{

	private static final String k_TestFile =  "TestFile.txt";
	
	/**
	 * This method clears the TestFile.txt
	 */
	@Before
	public void ClearTestFileBeforeTest()
	{
		try
		{
			FileWriter writer = new FileWriter(k_TestFile);
			writer.write("");
			writer.close();
		}
		catch (IOException e)
		{
			fail();
		}
	}
	
	@Test
	public void TestisStudentExists()
	{
		boolean isSuccess = false;
		String wrongStudentID = "-1"; // Does not exists
		StringBuilder studentInfo = new StringBuilder();
		studentInfo.append("123456789,987654321,Some,Student,c1|c2|c3");
		
		try
		{
			model.Library.UpdateStudentList(k_TestFile);
			LibraryController.ControllerRegisterStudent(studentInfo, k_TestFile);
			isSuccess = LibraryController.isStudentExists("123456789");
		} 
		catch (NumberFormatException | IOException e)
		{
			fail();
		}	
		
		if (isSuccess)
		{
			isSuccess = true;
		}
		
		assertTrue(isSuccess);
		
		isSuccess = LibraryController.isStudentExists(wrongStudentID);
		assertFalse(isSuccess);
		
		
		
	}
	
	
	/**
	 * This method tests the "ControllerRegisterStudent" method.
	 * This method also covers:
	 * "RegisterStudent",
	 * "fetchStudentInfoFromFrame" - private,
	 * "checkIfNameAndPhoneNumberAreValid" - private.
	 * "checkInformationByRegex" - private.
	 * @see LibraryController#ControllerRegisterStudent
	 * @see model.Library#RegisterStudent
	 */
	public void TestControllerRegisterStudent()
	{
		boolean isSuccess = false;
		StringBuilder studentInfo = new StringBuilder();
		studentInfo.append("123456789,987654321,Some,Student,c1|c2|c3");
		
		
		try
		{
			model.Library.UpdateStudentList(k_TestFile);
			isSuccess = LibraryController.ControllerRegisterStudent(studentInfo, k_TestFile);
		} 
		catch (NumberFormatException | IOException e)
		{
			fail();
		}
		
		assertTrue(isSuccess);
		
		try
		{
			isSuccess = LibraryController.ControllerRegisterStudent(studentInfo, k_TestFile);
		}
		catch  (IllegalArgumentException e1)
		{
			isSuccess = false;
		}
		
		assertFalse(isSuccess);
		
		
	}
	
	
	/**
	 * This method tests the "ControllerSearchStudentByID" method.
	 * This method also covers:
	 * "SearchStudentByID",
	 * "SearchStudentByIDWithoutPopUp"
	 * @see LibraryController#ControllerSearchStudentByID
	 * @see model.Library#SearchStudentByID
	 * @see model.Library#SearchStudentByIDWithoutPopUp
	 */
	@Test
	public void TestControllerSearchStudentByID()
	{
		String IDNumber = "123456789";
		model.Student testStudent = new model.Student();
		FileWriter writer= null;
		String textToWriteToFile = "123456789,87654321,Some,Student,0,c1|c2|c3,";
		boolean isSuccess = false;
		
		try
		{
			writer = new FileWriter(k_TestFile);
			writer.write(textToWriteToFile);
			writer.close();
			
			model.Library.UpdateStudentList(k_TestFile);	
			testStudent = LibraryController.ControllerSearchStudentByID(IDNumber);
		}
		catch (Exception e)
		{
			fail();
		}
		
		if (testStudent.GetFirstName().intern() != "".intern())
		{
			isSuccess = true;
		}
		
		assertTrue(isSuccess);
	}
	
	/**
	 * This method tests the "ControllerSearchStudentByName" method.
	 * This method also covers:
	 * "SearchStudentByFirstAndLastName",
	 * "studentInformationToAppend",
	 * @see LibraryController#ControllerSearchStudentByName
	 * @see LibraryController#studentInformationToAppend
	 */
	@Test
	public void TestControllerSearchStudentByName()
	{
		FileWriter writer= null;
		String textToWriteToFile = "123456789,87654321,Some,Student,0,c1|c2|c3,";
		String outputString = "";
		boolean isSuccess = false;
		
		try
		{
			writer = new FileWriter(k_TestFile);
			writer.write(textToWriteToFile);
			writer.close();
			
			model.Library.UpdateStudentList(k_TestFile);		
			outputString = LibraryController.ControllerSearchStudentByName("Some Student");
		}
		catch (Exception e)
		{
			fail();
		}
		
		if (outputString.length() > 0)
		{
			isSuccess = true;
		}
		
		assertTrue(isSuccess);
	}
	
	
	/**
	 * This method tests the "ControllerAddNewBook" method.
	 * This method also covers:
	 * "ControllerNewBookInformation",
	 * "InsertBook",
	 * "UpdateBookList".
	 * @see controller.LibraryController#ControllerNewBookInformation
	 * @see model.Library#InsertBook
	 * @see model.Library#UpdateBookList
	 *  
	 */
	@Test
	public void TestControllerAddNewBook()
	{
		File file = new File(k_TestFile);
		boolean isFileEmpty;
		
		String[] nameTestSplitted = { "Some", "Author" };
		String[] informationTestSplitted = { "Test Title",
										     "Some Author",
										     "Some Publisher",
										     "Category",
										     "12345678" };
		try
		{
			LibraryController.ControllerAddNewBook(nameTestSplitted, informationTestSplitted, k_TestFile);
		}
		catch (IllegalArgumentException | IOException e) 
		{
			fail();
		}

		isFileEmpty = (file.length() < 0);
		assertFalse(isFileEmpty);
	}

	/**
	 * This method tests the "ControllerRemoveBook" method.
	 * This method also covers "RemoveBookFromDB".
	 * @see LibraryController#ControllerRemoveBook
	 * @see model.Library#RemoveBookFromDB
	 */
	@Test
	public void TestControllerRemoveBook()
	{
		FileWriter writer= null;
		boolean isSuccess = false;
		String bookID = "100001";
		
		try
		{
			writer = new FileWriter(k_TestFile);
			writer.write("100001,testtitle,author|auther,publisher|1234567,category,0");
			writer.close();
			isSuccess =	LibraryController.ControllerRemoveBook(bookID, k_TestFile);
		}
		catch (IOException e)
		{
			fail();
		}
		
		assertTrue(isSuccess);
	}
	
	/**
	 * This method tests the "ControllerGetAllBooks" method.
	 * This method also covers:
	 * "GetBooks"
	 * "UpdateBookList".
	 * @see model.Library#GetBooks
	 * @see model.Library#UpdateBookList
	 */
	@Test
	public void TestControlerViewAllBooks()
	{
		FileWriter writer = null;
		String newLine = System.lineSeparator();
		String outPutStreamFromMethod;
		StringBuilder invalidString = new StringBuilder();
		String validString;
		
		StringBuilder textToWrite = new StringBuilder();
		textToWrite.append("100001,Title,Some|Author,Some Publisher|123456,Category,0");
		textToWrite.append(newLine);
		textToWrite.append("100002,Title2,Another|Author,Another Publisher|654321,Category2,0\r\n");
		
		try
		{
			writer = new FileWriter(k_TestFile);
			writer.write(textToWrite.toString());
			writer.close();
		} 
		catch (Exception e)
		{
			fail();
		}

		validString = "ID : Title\r\n100001 : Title\r\n100002 : Title2\r\n" ;
		invalidString.append("12341,Title,Some|Author,Some Publisher|123456,Category,0");
		invalidString.append(newLine);
		invalidString.append("112202,Title2,Wrong|Author,Wrong Publisher|654321,Category2,0");
		
		try
		{
			outPutStreamFromMethod = LibraryController.ControllerGetAllBooks(k_TestFile);
		
			assertTrue(validString.equals(outPutStreamFromMethod));
			assertFalse(validString.toString().equals(invalidString.toString()));
			
		} catch (NumberFormatException | IOException e)
		{
			// Fail if could not get book information from TestFile
			fail();
		}
	}
	
	/**
	 * This method tests the "CheckIfPhoneValid" method.
	 * @see LibraryController#CheckIfPhoneValid
	 */
	@Test
	public void TestCheckIfPhoneValid()
	{
		String validPhoneNumber = "0501111111";
		String invalidPhoneNumber = "o$0!!!1111";
		boolean isPhoneNumberValid;
		
		// call to method with valid string
		isPhoneNumberValid = LibraryController.CheckIfPhoneValid(validPhoneNumber);
		
		// isPhoneNumberValid should be true
		assertTrue(isPhoneNumberValid);

		// call to method with invalid string
		isPhoneNumberValid = LibraryController.CheckIfPhoneValid(invalidPhoneNumber);
		
		// isPhoneNumberValid should be false
		assertFalse(isPhoneNumberValid);
	}
}

package testPackage;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import model.FileHandler;

public class TestFileHandler
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
	
	/**
	 * This method tests the "RemoveLineFromFile" method.
	 * @see FileHandler#RemoveLineFromFile
	 */
	@Test
	public void TestFindAppearanceCountByID()
	{
		String textToWrite = "SomeText";		
		File file = new File(k_TestFile);
		boolean isSuccess = false;
		
		try
		{
			FileHandler.RemoveLineFromFile(k_TestFile, textToWrite);
		}
		catch (IOException e)
		{
			fail();
		}
		
		if (file.length() == 0)
		{
			isSuccess = true;
		}
		
		assertTrue(isSuccess);
	}
	
	
	/**
	 * This method tests the "UpdateLendingStatusInFile" method.
	 * @see model.FileHandler#UpdateLendingStatusInFile
	 */
	@Test
	public void TestUpdateLendingStatusInFile()
	{
		String testZero = "Test0";
		String testOne = "Test1";
		String textToCompare = "";
		char lastChar;
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(k_TestFile));
			writer.write(testZero);
			writer.close();
			
			FileHandler.UpdateLendingStatusInFile(k_TestFile, testZero);
			BufferedReader reader = new BufferedReader(new FileReader(k_TestFile));
			textToCompare = reader.readLine();
			reader.close();
			
		}
		catch (Exception e)
		{
			fail();
		}
			
		assertTrue(textToCompare.equals(testOne));
	}
	
	
	/**
	 * This method tests the "WriteToFile" method.
	 * @see model.FileHandler#WriteToFile
	 */
	@Test
	public void TestWriteToFile()
	{
		StringBuilder textToWrite = new StringBuilder();
		textToWrite.append("Some Text");
		boolean isNotEmpty = false;
		File file = new File(k_TestFile);
		
		FileHandler.WriteToFile(k_TestFile, textToWrite);
		
		if (file.length() > 0)
		{
			isNotEmpty = true;
		}
		
		assertTrue(isNotEmpty);
		
	}
}

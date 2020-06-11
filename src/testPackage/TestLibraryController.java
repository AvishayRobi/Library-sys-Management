package testPackage;

import controller.LibraryController;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestLibraryController {

	/**
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

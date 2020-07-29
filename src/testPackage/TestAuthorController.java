package testPackage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import org.junit.Test;

import controller.AuthorController;

public class TestAuthorController
{
	
	/**
	 * This test method also asserts the "isStringAllSpaces" method
	 * that is being called during the run.
	 */
	@Test
	public void TestCheckIfAuthorNameValid()
	{
		boolean isValid;
		String validString = "Some Author";
		String invalidSpaceString = "     ";
		String ivalidDigitString = "$om3 Auth3r";
		
		isValid = AuthorController.CheckIfAuthorNameValid(validString);
		assertTrue(isValid);
		
		isValid = AuthorController.CheckIfAuthorNameValid(invalidSpaceString);
		assertFalse(isValid);
		
		isValid = AuthorController.CheckIfAuthorNameValid(ivalidDigitString);
		assertFalse(isValid);
	}

	
	
}

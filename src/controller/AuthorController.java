package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods that validate the Author information.
 * This class acts as the controller between the "View" package and the "Model" package.
 */
public class AuthorController
{
	/**
	 * This method checks if the Author name is valid,
	 * and returns false if the input contains non - letters or space characters,
	 * @param i_StrFromAuthorNameTB -
	 * String representation of input from Author's name text box
	 * @return isTextValid -
	 * boolean type
	 */
	public static boolean CheckIfAuthorNameValid(String i_StrFromAuthorNameTB) 
	{
		boolean isTextValid = true;
		
		// Remove leading and trailing spaces
		i_StrFromAuthorNameTB.trim();
		
		// Check if name is empty
		boolean isNameEmpty = i_StrFromAuthorNameTB.isEmpty();
		
		// Check if name is all  spaces
		boolean isAllSpaces = isStringAllSpaces(i_StrFromAuthorNameTB);
		
		// Check if name contains anything but letters or spaces
		Pattern patternAllowed = Pattern.compile("^[ A-Za-z]+$");
		Matcher matcher = patternAllowed.matcher(i_StrFromAuthorNameTB);
		boolean isContainsAnythingButLetters = matcher.matches();
		
		// Check conditions
		if(isNameEmpty || !isContainsAnythingButLetters || isAllSpaces)
		{
			isTextValid = false;
		}
		
		return isTextValid;
	}
	
	/**
	 * This methods checks if given string contains only spaces.
	 * @param i_Str - string to be checked
	 * @return boolean - true if string consists of only space characters
	 */
	public static boolean isStringAllSpaces(String i_Str)
	{
		return !(i_Str.trim().length() > 0);
	}	
}

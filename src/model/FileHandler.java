package model;

import java.io.*;

/**
 * FileHandler Class. This class is in charge of handling the writing and reading from 
 * the different databases.
 */
public class FileHandler
{
	private static final String k_EmptyString = "";

	/**
	 * This method writes into the given database.
	 * @param i_FileName - String
	 * @param i_String - StringBuilder type object
	 * @return true - if writing was successful
	 */
	public static boolean WriteToFile(String i_FileName, StringBuilder i_String)
	{
		String newLine = System.lineSeparator();
		FileWriter writer;
		boolean isAppend = true;
		boolean isWriteSuccess = true;
		i_String.append(newLine);

		try
		{
			writer = new FileWriter(i_FileName, isAppend); // True to append file
			writer.write(i_String.toString());
			writer.close();
		}
		catch (IOException e)
		{
			isWriteSuccess = false; // TODO: Do normal exception
			Error newError = new Error(e.getMessage());
			newError.NewError();
		}
		
		return isWriteSuccess;
	}

	/**
	 * This method updates the lending status of a book in the database.
	 * @param i_FileName - String
	 * @param i_LineIdentifier - String
	 * @throws IOException
	 */
	public static void UpdateLendingStatusInFile(String i_FileName, String i_LineIdentifier) throws IOException
	{	
		File inputFile = new File(i_FileName);
		File tempFile = new File("TempFile.txt");
		String currentLine;
		StringBuilder lineToReplace = new StringBuilder();
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		while((currentLine = reader.readLine()) != null)
		{	
			if (currentLine.contains(i_LineIdentifier))
			{
				lineToReplace.append(currentLine);
				
				if(lineToReplace.charAt(lineToReplace.length()-1) == '0')
				{
					lineToReplace.setCharAt(lineToReplace.length()-1, '1');
				}
				else
				{
					lineToReplace.setCharAt(lineToReplace.length()-1, '0');
				}	
				
				writer.write(lineToReplace.toString());
				writer.write(System.lineSeparator());
				continue;	
			}
			
			writer.write(currentLine);	
			writer.write(System.lineSeparator());
		}
		
		reader.close();
		writer.close();
		
		inputFile.delete();
		tempFile.renameTo(inputFile);
	}

	/**
	 * This method returns the amount of appearances by a given ID number.
	 * @param i_FileName - String
	 * @param i_ID - String
	 * @return appearanceCounter - int
	 * @throws IOException
	 */
	public static int FindAppearanceCountByID(String i_FileName, String i_ID) throws IOException
	{
		String sCurrentLine;
		int appearanceCounter = 0;
		BufferedReader br = new BufferedReader(new FileReader(i_FileName));

		while ((sCurrentLine = br.readLine()) != null)
		{
			String[] lineValues = sCurrentLine.split(","); // Split the string on commas into array
			String currentID;
			boolean isMatchID;

			currentID = lineValues[0].substring(0, lineValues[0].length() - 1);
			isMatchID = (currentID == i_ID);

			if (isMatchID)
			{
				appearanceCounter++;
			}
		}
		
		br.close();
			
		return appearanceCounter;
	}

	/**
	 * This method returns the last line in the database
	 * that contains the given ID number, and returns an array of strings to be processed.
	 * @param i_FileName - String
	 * @param i_ID - String
	 * @return currentLine - array of strings.
	 * @throws IOException
	 */
	public static String[] FindLastAppearanceLineByID(String i_FileName, String i_ID) throws IOException
	{
		String sCurrentLine;
		BufferedReader br = new BufferedReader(new FileReader(i_FileName));

		// This will store the last appearance and will be returned
		String[] currentLine = { "" };

		while ((sCurrentLine = br.readLine()) != null) 
		{
			String[] lineValues = sCurrentLine.split(","); // Split the string on commas into array
			String currentID;
			boolean isMatchID;

			currentID = lineValues[0].substring(0, lineValues[0].length() - 1);
			isMatchID = (currentID == i_ID);

			if (isMatchID)
			{
				currentLine = lineValues;
			}
		}

		br.close();
		
		return currentLine;
	}

	/**
	 * This method removes a line that contains the given ID number from the given database.
	 * @param i_FileName - String
	 * @param i_ID - String
	 * @throws IOException
	 */
	public static void RemoveLineFromFile(String i_FileName, String i_ID) throws IOException
	{
		File inputFile = new File(i_FileName);
		File tempFile = new File("Temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		// Remove line starts with this ID
		String iDIdentifierLineToRemove = i_ID;
		boolean isIDMatch = false;
		boolean isAtleastOneIDExist = false;
		String currentLine;
		String textToPopUp;
		String[] splittedLine;
		String idFromLine;

		while ((currentLine = reader.readLine()) != null)
		{
			/* Add all lines to temporary files, except line we want to remove */
			// Trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			
			// Get ID from line
			splittedLine = trimmedLine.split(",");
			idFromLine = splittedLine[0];

			// Match between ID's
			isIDMatch = idFromLine.equals(iDIdentifierLineToRemove);

			// Check match between ID's
			if (isIDMatch)
			{
				isAtleastOneIDExist = true;
				continue;
			}				

			// Add correct line to temporary file
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		
		if (!isAtleastOneIDExist)
		{
			textToPopUp = "Failed to remove!";
		}
		else
		{
			textToPopUp = "Deleted Successfully";
		}

		// Inform user if success or fail
		view.PopUpMessage.ShowPopUpMessage(textToPopUp);

		writer.close();
		reader.close();
		
		// Delete original file
		File f = new File(i_FileName);
		f.delete();
		
		tempFile.renameTo(inputFile);
	}
	
	/**
	 * This method counts the number of appearances by ID number and a substring. 
	 * @param i_FileName - String
	 * @param i_ID - String
	 * @param i_StrToFind - String
	 * @return
	 * @throws IOException
	 */
	public static int counStrAppearenceForID(String i_FileName, String i_ID, String i_StrToFind) throws IOException
	{
		String sCurrentLine;
		int appearanceCounter = 0;
		BufferedReader br = new BufferedReader(new FileReader(i_FileName));

		while ((sCurrentLine = br.readLine()) != null)
		{
			String[] lineValues = sCurrentLine.split(","); // Split the string on commas into array
			String currentID;
			boolean isMatchID;

			// Match ID
			currentID = lineValues[0].substring(0, lineValues[0].length() - 1);
			isMatchID = (currentID == i_ID);
			
			// Match NULL
			boolean isNullAppears = sCurrentLine.contains(i_StrToFind);

			// Check for match of ID and NULL
			if (isMatchID && isNullAppears)
			{
				appearanceCounter++;
			}
			
			br.close();
		}

		return appearanceCounter;
	}
	
	/**
	 * This method finds the first row in a database that contains the given ID number.
	 * @param i_FileName - String
	 * @param i_ID - String
	 * @return String
	 * @throws IOException
	 */
	public static String FindRowByID(String i_FileName, String i_ID) throws IOException
	{
		String sCurrentLine;
		BufferedReader br = new BufferedReader(new FileReader(i_FileName));
		
		while ((sCurrentLine = br.readLine()) != null)
		{
			String[] lineValues = sCurrentLine.split(",");
			String currentID;
			boolean isMatchID;
			
			// Match ID
			currentID = lineValues[0].substring(0, lineValues[0].length() - 1);
			isMatchID = (currentID == i_ID);
			
			if (isMatchID)
			{
				br.close();
				return sCurrentLine;
			}
		}
		
		view.PopUpMessage.ShowPopUpMessage("Not found!");
		
		br.close();
		return "";
	}
	
	/**
	 * This method returns the last ID number generated in the database.
	 * @param i_DataBase - String
	 * @return IDToReturn - int
	 * @throws IOException
	 */
	public static int GetLastIDGenerated(String i_DataBase) throws IOException
	{
		int IDToReturn;
		String lastLine = k_EmptyString;
		String line;
		String[] splittedLine;
		File file = new File(i_DataBase);
		BufferedReader br = new BufferedReader(new FileReader(i_DataBase));
		
		if (isFileEmpty(file))
		{
			IDToReturn = 100000;
		}
		else
		{
			while((line = br.readLine()) != null)
			{
				lastLine = line;
			}
			
			splittedLine = lastLine.split(",");
			IDToReturn = Integer.parseInt(splittedLine[0]);
		}

		br.close();
		
		return IDToReturn;
	}

	/**
	 * This method checks if a file is empty or not.
	 * @param i_FileName - String
	 * @return true- if file is empty
	 */
	private static boolean isFileEmpty(File i_FileName)
	{
		return (i_FileName.length() == 0);	
	}
	
}

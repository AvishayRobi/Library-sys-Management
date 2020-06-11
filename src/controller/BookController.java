package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.Book;
import model.Library;

/**
 * This class contains methods that validate book information,
 * and invoke methods in the "Model" package.
 * This class acts as the controller between the "View" package and the "Model" package.
 */
public class BookController 
{
	private static final String k_LendingHistoryDB = "Lending_log.txt";
	private static final String k_BooksDBFile = "BooksDB.txt";

	/**
	 * This method invokes the "lendbook" method,
	 *  only if the student that is lending the book exists.
	 * @param i_StudentID - String
	 * @param i_BookID - String
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void ControllerLendBook(String i_StudentID, String i_BookID) throws NumberFormatException, IOException
	{	
		if (LibraryController.isStudentExists(i_StudentID))
		{
			lendBook(i_StudentID, i_BookID);
			model.Library.UpdateBookList();
		}
	}	
	
	/**
	 * This method validates the following:
	 * The book is not already lended,
	 * The book ID is valid.
	 * If one of the two is not valid,
	 * a pop message with the relevant error is Shown to the user.
	 * If given information is valid,
	 * this method invokes the "Lend" method in the model package,
	 * and pops a message to the user that the procedure was successful.
	 * @param i_StudentID
	 * @param i_BookID
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private static void lendBook(String i_StudentID, String i_BookID) throws NumberFormatException, IOException
	{	
		model.Book newBook = null;
		
		for (Book book : Library.GetBooks())
		{
			
			if (i_BookID.intern() == Integer.toString(book.GetID()).intern())
			{
				newBook = book;
				break;
			}
		}
		
		if (newBook != null)
		{	
			if (newBook.GetIsLended())
			{
				view.PopUpMessage.ShowPopUpMessage("Book Is Already Lended!");
			}
			else
			{
				newBook.Lend(i_StudentID);
				model.Library.UpdateBookList();
				view.PopUpMessage.ShowPopUpMessage("Success!");
			}
		}
		else
		{
			view.PopUpMessage.ShowPopUpMessage("Invalid Book ID");
		}
	}
	
	/**
	 * This method validates the given information
	 * and handles the database by invoking methods 
	 * from the "FileHandler" class in the "Model" package.
	 * @param i_BookID - String
	 * @throws IOException
	 */
	public static void ControllerReturnBook(String i_BookID) throws IOException
	{	
		// Update the book list in memory from the Database.
		model.Library.UpdateBookList();

		int index = -1;
		int i = 0;		
		String currentLine;
		ArrayList<String> lines = new ArrayList<String>();
		
		File inputFile = new File(k_LendingHistoryDB);
		File tempFile = new File("TempLendingFile.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		model.Book bookToReturn = Library.SearchBookByID(i_BookID);
		
		if (bookToReturn != null)
		{
		
			while((currentLine = reader.readLine()) != null)
			{
				if(currentLine.contains(i_BookID) && bookToReturn.GetIsLended())
				{
					index = i;
				}
				
				lines.add(currentLine);
				i++;
			}
			
			// If index isn't -1, we have found a valid entry in the database.
			if(index != -1) 
			{
				String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
				String newLine = lines.get(index).replace("NULL", currentDate);
				
				lines.set(index, newLine);
				
				for(String str : lines)
				{
					writer.write(str);
					writer.write(System.lineSeparator());
				}
				
				model.FileHandler.UpdateLendingStatusInFile(k_BooksDBFile, i_BookID);
				bookToReturn.SetIsLended(false);
				
				reader.close();
				writer.close();
				inputFile.delete();
				tempFile.renameTo(inputFile);
				view.PopUpMessage.ShowPopUpMessage("Success!");
			}
			else
			{
				tempFile.delete();
				view.PopUpMessage.ShowPopUpMessage("Book Is Not Lended!");
			}
		}
		else
		{
			writer.close();
			reader.close();	
			tempFile.delete();
		}
	}
}

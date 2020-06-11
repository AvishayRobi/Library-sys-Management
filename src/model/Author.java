package model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author class, inherits from "Person" abstract class:
 * 	m_FirstName,
 *	m_LastName.
 */
public class Author extends Person
{
	private int m_AuthorID = 0;
	
	// Constructor	
	public Author()
	{
		super("", "");
	}
	
	public Author(String i_FirstName, String i_LastName)
	{
		super(i_FirstName, i_LastName);
	}	
	
	/**
	 * This mathod creates the author information.
	 * @param author - Author type object
	 * @return StringBuilder type object containing the author information.
	 */
	private static StringBuilder createAuthorDescription(Author author)
	{	
		String newLine = System.lineSeparator();
		StringBuilder description = new StringBuilder();
		description.append(author.GetFirstName());
		description.append(",");
		description.append(author.GetLastName());
		description.append(newLine);
		
		return description;
	}
	
	/**
	 * This method gets the books that the author have written.
	 * @return StringBuilder type object containing the author's books.
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public StringBuilder GetBooksWritten() throws NumberFormatException, IOException 
	{
		StringBuilder booksWrittenByAuthor = new StringBuilder();
		String newLine = System.lineSeparator();
		int numberOfBooksInLibrary = Library.GetNumberOfBooksInLibrary();
		
		ArrayList<Book> libraryBooks = Library.GetBooks();

		for (int i = 0; i < numberOfBooksInLibrary; i++) {
			Book libraryBook = libraryBooks.get(i);
			int libraryBookAuthorID = libraryBook.GetAuthor().GetID();

			if (libraryBookAuthorID == m_AuthorID) {
				booksWrittenByAuthor.append(libraryBook.GetTitle());
				booksWrittenByAuthor.append(newLine);
			}
		}

		return booksWrittenByAuthor;
	}
	
	/**
	 * This method gets the author's ID number.
	 * @return int - author's ID number
	 */
	public int GetID()
	{
		return m_AuthorID;
	}
	
	/**
	 * This method validates the Author by ID.
	 * @param i_Author - Author type object
	 * @return boolean type
	 */
	public boolean IsEqual(Author i_Author)
	{
		return m_AuthorID == i_Author.GetID();
	}
}

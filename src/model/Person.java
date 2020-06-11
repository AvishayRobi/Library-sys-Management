package model;

public abstract class Person 
{
	// Fields
	protected String m_FirstName;
	protected String m_LastName;

	// Constructor
	public Person(String i_FirstName, String i_LastName) 
	{
		m_FirstName = i_FirstName;
		m_LastName = i_LastName;
	}

	// Methods
	// Getters
	public String GetFirstName()
	{
		return m_FirstName;
	}

	public String GetLastName()
	{
		return m_LastName;
	}

	// Setters
	public void SetFirstName(String i_FirstName) 
	{
		m_FirstName = i_FirstName;
	}

	public void SetLastName(String i_LastName)
	{
		m_LastName = i_LastName;
	}
}

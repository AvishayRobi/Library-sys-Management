package model;

public class Publisher {
	// Fields
	private String m_Name;
	private int m_PhoneNumber;
	private int m_ID;

	// Constructor
	public Publisher()
	{
		
	}
	
	public Publisher(String i_Name, int i_PhoneNumber) 
	{
		SetName(i_Name);
		SetPhoneNumber(i_PhoneNumber);
	}

	// Methods
	public String GetName()
	{
		return m_Name;
	}

	public int GetPhoneNumber() 
	{
		return m_PhoneNumber;
	}
	
	public int GetID()
	{
		return m_ID;
	}

	public void SetPhoneNumber(int i_PhoneNumber)
	{
		m_PhoneNumber = i_PhoneNumber;
	}

	public void SetName(String i_PublisherName)
	{
		m_Name = i_PublisherName;
	}
	
	public void SetID(int i_ID)
	{
		m_ID = i_ID;
	}
}

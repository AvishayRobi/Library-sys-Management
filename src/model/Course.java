package model;

/**
 * Courses class. 
 */
public class Course {
	// Fields
	private String m_CourseName;
	private String m_CourseCatagory;

	// Constructor
	public Course()
	{
		
	}
	
	public Course(String i_CourseName, String i_CourseCatagory) 
	{
		this.m_CourseCatagory = i_CourseCatagory;
		this.m_CourseName = i_CourseName;
	}

	// Getters
	public String GetCourseName() {
		return m_CourseName;
	}

	public String GetCourseCatagory() {
		return m_CourseCatagory;
	}
	
	// Setters
	public void SetCourseName(String i_CourseName)
	{
		m_CourseName = i_CourseName;
	}
	
	public void SetCategory(String i_Category)
	{
		m_CourseCatagory = i_Category;
	}
}

package model;

import java.util.ArrayList;


public class LateStudent extends Student {
	// Fields
	private int m_TotalDaysPastReturn;

	// Constructor
	public LateStudent()
	{
		super();
	}
	
	public LateStudent(int i_StudentID, String i_FirstName, String i_LastName, int i_PhoneNumber, ArrayList<Course> i_Courses,
			ArrayList<model.Book> arrayList, int i_NumOfCurrentLendedBooks) {
		
		super(i_StudentID, i_FirstName, i_LastName, i_PhoneNumber, i_NumOfCurrentLendedBooks, i_Courses);
	}
	
	public LateStudent(Student i_Student)
	{
		super(i_Student.GetStudentID(),
			  i_Student.GetFirstName(), 
			  i_Student.GetLastName(),
			  i_Student.GetPhoneNumber(), 
			  i_Student.GetNumberOfCurrentLendedBooks(),
			  i_Student.GetCoursesTaken());
	}

	// Methods
	public int GetTotalDaysPastReturn() {
		return m_TotalDaysPastReturn;
	}

	public void SetTotalDaysPastReturn(int i_TotalDaysPastReturn) {
		m_TotalDaysPastReturn = i_TotalDaysPastReturn;
	}

	public void BlockPersonalPortal() {
		String messageToPopUp;

		messageToPopUp = String.format("Portal For Student %s %s Blocked!", m_FirstName, m_LastName);
		view.PopUpMessage.ShowPopUpMessage(messageToPopUp);
	}

	public void SendAlertSMS() {
		String messageToPopUp = String.format("Alert Sent To Phone Number %s", m_PhoneNumber);
		view.PopUpMessage.ShowPopUpMessage(messageToPopUp);
	}
}
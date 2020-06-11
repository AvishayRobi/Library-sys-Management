package model;
import java.util.Date;

public class LendingHistory {
	// Fields
	private static Date m_LendingDate;
	private static Date m_ReturnDate;

	// Constructors
	public LendingHistory(Date i_LendingDate, Date i_ReturnDate) {
		m_LendingDate = i_LendingDate;
		m_ReturnDate = i_ReturnDate;
	}

	public LendingHistory(Date i_LendingDate) {
		m_LendingDate = i_LendingDate;
	}

	// Methods
	// Getters
	public static Date GetLendingDate() {
		return m_LendingDate;
	}

	public static Date GetReturnDate() {
		return m_ReturnDate;
	}

	// Setters
	public static void SetLendingDate(Date i_LendingDate) {
		m_LendingDate = i_LendingDate;
	}

	public static void SetReturnDate(Date i_ReturnDate) {
		m_ReturnDate = i_ReturnDate;
	}
}

package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Error;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class WelcomeJframe extends CustomLibraryFrame {

	//private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeJframe frame = new WelcomeJframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					Error newError = new Error(e.getMessage());
					newError.NewError();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public WelcomeJframe() throws IOException {

		
		JLabel lblWelcome = new JLabel("Welcome!");
		lblWelcome.setForeground(Color.WHITE);
		lblWelcome.setBounds(156, 11, 109, 42);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 22));
		m_Panel.add(lblWelcome);
		
		JButton btnStartProgram = new CustomLibraryButton("Library System");
		btnStartProgram.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				UserActionFrame.UserActionScreen();

				try
				{
					model.Library.UpdateStudentList();
					model.Library.UpdateBookList();
				} catch (NumberFormatException | IOException e)
				{
					e.printStackTrace();
					model.Error newError = new model.Error(e.getMessage());
					newError.NewError();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
	
			}
		});
		btnStartProgram.setBounds(119, 129, 180, 23);		
		m_Panel.add(btnStartProgram);

	}
}

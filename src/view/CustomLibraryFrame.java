package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CustomLibraryFrame extends JFrame
{
	protected JPanel contentPane;
	ImagePanel m_Panel;
	
	public CustomLibraryFrame()
	{
		super();		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setResizable(false);
		m_Panel = new ImagePanel("ImageResources\\welcomeBackround.jpg");
		getContentPane().add(m_Panel);
		m_Panel.setLayout(null);
	}
}

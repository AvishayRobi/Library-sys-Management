package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomLibraryButton extends JButton
{

	public CustomLibraryButton(String i_Description)
	{		
		super(i_Description);
		this.setIcon(new ImageIcon("ImageResources\\silverBlue.png"));
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);		
	}
	
}

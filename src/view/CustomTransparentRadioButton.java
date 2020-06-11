package view;

import java.awt.Color;

import javax.swing.JRadioButton;

public class CustomTransparentRadioButton extends JRadioButton 
{
	public CustomTransparentRadioButton(String i_Description)
	{
		super(i_Description);
		this.setForeground(Color.WHITE);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setOpaque(false);
	}
}

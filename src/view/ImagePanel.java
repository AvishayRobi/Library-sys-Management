package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{

	private Image m_Image = null;

	public ImagePanel(String i_FileName) 
	{
		this.m_Image = new ImageIcon(i_FileName).getImage();
	}
	
		
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(m_Image, 0, 0, m_Image.getWidth(null), m_Image.getHeight(null), null);
    }	
}

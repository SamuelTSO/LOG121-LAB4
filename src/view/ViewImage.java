/******************************************************
Cours:   LOG121
Session: E2016
Groupe:  01
Projet: Laboratoire #4
�tudiant(e)s: 
              Philippe Torres-Brisebois
              Laurent Theroux-Bombardier
              Samuel Croteau
              Nelson Chao
Professeur : Francis Cardinal
Nom du fichier: ViewImage.java
Date cr��: 2016-07-27
Date dern. modif. 2016-07-27
*******************************************************
Historique des modifications
*******************************************************
2016-07-27 Version initiale
*******************************************************/


package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.ImageData;
import model.Perspective;

//Cette classe a ete inspirer du site suivant:
//MVP inspirer de Bnrdo - http://stackoverflow.com/questions/5217611/the-mvc-pattern-and-swing
public class ViewImage extends JFrame{
	//Constants
	//Attributes
	private JPanel panneauPrincipal;
	private viewImageImagePanel imagePanel;
	private JButton bCloseView;
	//Method
	public ViewImage() {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		panneauPrincipal = (JPanel)this.getContentPane();
		
		imagePanel = new viewImageImagePanel();
		bCloseView = new JButton();
		
		panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));
		
		imagePanel.setAlignmentX(CENTER_ALIGNMENT);
		bCloseView.setAlignmentX(CENTER_ALIGNMENT);
		
		panneauPrincipal.add(Box.createRigidArea(new Dimension(0,20)));
		panneauPrincipal.add(imagePanel);
		panneauPrincipal.add(Box.createRigidArea(new Dimension(0,10)));
		panneauPrincipal.add(bCloseView);
		panneauPrincipal.add(Box.createRigidArea(new Dimension(0,10)));
		
		this.setSize(new Dimension(400,600));
		this.setLocation(screenDimension.width/10-this.getSize().width/2, screenDimension.height/2-this.getSize().height/2);
        setResizable(false);
        setTitle("View Image");
        setVisible(true);
	}
	/**
	 * Set an image in a panel
	 * 
	 * @param image the desired image
	 */
	public void setImageInPanel(ImageData image) {
		imagePanel.setImage(image);
		this.setSize(new Dimension(image.getBufferedImage().getWidth(),image.getBufferedImage().getHeight()));
		if(this.getWidth() > 1000){
			this.setSize(1000, this.getHeight());
		}
		if(this.getHeight() > 1000){
			this.setSize(this.getWidth(),1000);
		}
	}
	//getter/setter
	public JButton getbCloseView() {
		return bCloseView;
	}
	
	//private class
	private class viewImageImagePanel extends JPanel{
		private ImageData image;
		
		protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image.getBufferedImage(), 0, 0, null);     
	        g.setColor(Color.GREEN);
	        drawPerspectiveRect(g,0);
	        g.setColor(Color.RED);
	        drawPerspectiveRect(g,1);
	    }
		
		public void drawPerspectiveRect(Graphics g, int index){
			Perspective perspective0 = image.getPerspective(index);
	        int x = perspective0.getVtState().getHorizontalTranslation();
	        int y = perspective0.getVtState().getVerticalTranslation();
	        double zoom = perspective0.getVtState().getZoomPercentage();
	        int width = (int) (394 / (zoom));
	        int height = (int) (475 / (zoom));
	        g.drawRect(0-x, 0-y, width, height);
		}
		
		public void setImage(ImageData image){
			this.image = image;
		}
	}
}

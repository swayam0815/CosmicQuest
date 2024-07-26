import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * APPLICATION TUTORIAL CLASS<br>
 * <br>
 * Application tutorial class that sets up the panel to show how to use the application
 * @author Jenish Paudel
 * @author Dikran Kahiaian(Javadoc)
 */
public class ApplicationTutorial extends JPanel {
	/** frame to be used */
	private JFrame frame;
	/** background image to be used */
	private Image background;
	/**
	 * APPLICATION TUTORIAL CONSTRUCTOR<br>
	 * <br>
	 * Application tutorial class that sets up the panel to show how to use the application
	 * @param inputFrame, Jframe for the content to be displayed on
	 * @param prevPanel, stores the instance of the previous panel 
	 */
	public ApplicationTutorial(JFrame inputFrame, TutorialMain prevPanel) {

		// setting up the frame
		this.frame = inputFrame;
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setLayout(new BorderLayout());

		// setting up the background
		ImageIcon backgroundImage = new ImageIcon("src/images/GameBckgrnd.jpg");
		this.background = backgroundImage.getImage();

		// --------------------------------------------------------------------------------
		// Main panel view
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setOpaque(false);

		// constraints
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(frame.getWidth() / 50, frame.getWidth() / 50, frame.getWidth() / 50,
				frame.getWidth() / 50);

		// --------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------
		// User Profile Tutorial

		JPanel userPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon applicationTut = new ImageIcon("src/images/applicationScreen.png");
				g.drawImage(applicationTut.getImage(), 0,0, this.getWidth(), this.getHeight(), null);

			}
		};
		userPanel.setPreferredSize(new Dimension(frame.getWidth()/2, frame.getHeight()/2));
		userPanel.setLayout(new BorderLayout());

		

		// constraints then adding
		gbc.gridx = 1;
		gbc.gridy = 1;

		mainPanel.add(userPanel, gbc);

		// *****************************************************************
		// BackButton Application

		// making the button
		JLabel backButApp = new JLabel();
		backButApp.setIcon(new ImageIcon("src/images/back.png"));


		// --------------------------------------------------------------------
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(backButApp, gbc);

		mainPanel.revalidate();
		mainPanel.repaint();

		this.add(mainPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();

		// ******************************************************
		// appBackButton
		backButApp.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) {
				//When button is clicked, do something -------------------------------------------------------
				removePanel();
				prevPanel.addPanel();
				
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				backButApp.setIcon(new ImageIcon("src/images/backH.png"));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButApp.setIcon(new ImageIcon("src/images/back.png"));

			}
		});


	}
	/**
	 * ADD PANEL METHOD<br>
	 * <br>
	 * 	Adds this panel to JFrame and the revalidates and repaints this and the frame
	 */	
	public void addPanel() {

		frame.add(this);
		this.revalidate();
		this.repaint();
		frame.revalidate();
		frame.repaint();
	}

	public void removePanel() { // removes this panel from the frame ------
		frame.remove(this);
		frame.repaint();
	}
	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Method that paints the background image
	 * 
	 * @param g The graphics used for the image to be painted
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0,0, this.getWidth(), this.getHeight(), null);

	}

}
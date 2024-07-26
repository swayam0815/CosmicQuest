import java.awt.BorderLayout;
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

public class UserProfileTutorial extends JPanel {
	// private stuff
	private JFrame frame;
	private Image background;

	public UserProfileTutorial(JFrame inputFrame, TutorialMain prevPanel) {

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

		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanel.setOpaque(false);

		JLabel userScreen = new JLabel();
		userScreen.setIcon(new ImageIcon("src/images/applicationScreen.png")); //UPDATE IMAGE HERE ----------------------------------

		userPanel.add(userScreen, BorderLayout.CENTER);

		// constraints then adding
		gbc.gridx = 1;
		gbc.gridy = 1;

		mainPanel.add(userPanel, gbc);

		// *****************************************************************
		// BackButton Userprofile

		// making the button
		// making the button
		JLabel backButUser = new JLabel();
		backButUser.setIcon(new ImageIcon("src/images/back.png"));


		// --------------------------------------------------------------------
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(backButUser, gbc);

		mainPanel.revalidate();
		mainPanel.repaint();

		this.add(mainPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();

		// ******************************************************
		// appBackButton
		backButUser.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) {
				//When button is clicked, do something -------------------------------------------------------
				removePanel();
				prevPanel.addPanel();
				
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				backButUser.setIcon(new ImageIcon("src/images/backH.png"));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButUser.setIcon(new ImageIcon("src/images/back.png"));

			}
		});


	}

	// Adds this panel to JFrame and the revalidates and repaints this and the frame
	// --
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

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background, 0,0, this.getWidth(), this.getHeight(), null);

	}

}
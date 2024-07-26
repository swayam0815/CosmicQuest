import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GAMEPLAYTUTORIAL CLASS <br>
 * <br>
 * Creates a panel to hold gameplay tutorial <br>
 * <br>
 * 
 * @author Thomas Ma
 * @author Jenish Paudel
 */
public class GameplayTutorial extends JPanel {

	// private stuff
	/** panels frame */
	private JFrame frame;
	/** the panel background */
	private Image background;

	/**
	 * GAMEPLAYTUTORIAL CONSTRUCTOR <br>
	 * <br>
	 * Creates the gameplay tutorial screen
	 * 
	 * @param inputFrame The program frame
	 * @param prevPanel  The previous game screen
	 */
	public GameplayTutorial(JFrame inputFrame, TutorialMain prevPanel) {

		// setting up the frame
		this.frame = inputFrame;

		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setLayout(new BorderLayout());

		// setting up the background
		ImageIcon backgroundImage = new ImageIcon("src/images/gameplayTut.png");
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
		// Gameplay Tutorial

		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setOpaque(false);

		// ***********************************************
		// Asteroid

		// *************************************************
		// Text input
		JTextField inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(frame.getWidth() / 5, frame.getHeight() / 10));
		inputField.setFont(new Font("Arial", Font.PLAIN, frame.getHeight() / 13));
		inputField.setHorizontalAlignment(JTextField.CENTER);

		gamePanel.add(inputField, BorderLayout.EAST);

		// *************************************************
		JLabel outputField = new JLabel();
		outputField.setPreferredSize(new Dimension(frame.getWidth() / 3, frame.getHeight() / 5));
		outputField.setHorizontalAlignment(JLabel.CENTER);
		outputField.setVisible(true);

		// color for visiability against background,use below later
		// outputField.setFont(gameStatsFont);
		outputField.setForeground(Color.green);

		gamePanel.add(outputField, BorderLayout.SOUTH);

		// constraints then adding
		gbc.gridx = 1;
		gbc.gridy = 1;

		mainPanel.add(gamePanel, gbc);
		// ---------------------------------------------

		// *****************************************************************
		// BackButton Gameplay

		// making the button
		// making back button
		JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		backButtonPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		backButtonPanel.setOpaque(false);

		JLabel backButApp = new JLabel();
		backButApp.setIcon(new ImageIcon("src/images/back.png"));

		backButtonPanel.add(backButApp);

		this.add(backButtonPanel);

		// ******************************************************
		// appBackButton
		backButApp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// When button is clicked, do something
				// -------------------------------------------------------
				GameplayTutorial.this.background = backgroundImage.getImage();
				GameplayTutorial.this.add(mainPanel);
				GameplayTutorial.this.repaint();
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

		gbc.gridx = 0;
		gbc.gridy = 0;

		mainPanel.revalidate();
		mainPanel.repaint();
		// -------------------------------------------------------------------------------------

		this.add(backButtonPanel);
		this.add(mainPanel, BorderLayout.CENTER);
		this.setComponentZOrder(backButtonPanel, 0);
		this.revalidate();
		this.repaint();

		// ********************************************************
		// gameTut text listener
		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String input = inputField.getText();

				if (input.equals("4")) {

					changeToPass(backButtonPanel);
				}

				else {
					// Show an error dialog that displays the message, 'alert':
					JOptionPane.showMessageDialog(null, "Incorrect Answer!", "Alert", JOptionPane.ERROR_MESSAGE);
				}

				inputField.setText("");
			}
		});

	}

	/**
	 * SCREEN CHANGER METHOD<br>
	 * <br>
	 * Changes the screen to the tutorial passed screen
	 * 
	 * @param exclusion the back button
	 */
	public void changeToPass(JPanel exclusion) {
		this.removeAll();
		this.background = new ImageIcon("src/images/tutPassed.png").getImage();
		this.add(exclusion);
		this.revalidate();
		this.repaint();

	}

	/**
	 * PANEL ADDER FUNCTION<br>
	 * <br>
	 * Adds this panel to the JFrame and then revalidates and repaints both this
	 * panel and the frame
	 */
	public void addPanel() {

		frame.add(this);
		this.revalidate();
		this.repaint();
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * PANEL REMOVER FUNCTION<br>
	 * <br>
	 * Removes this panel from the JFrame and then repaints the frame
	 */
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
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

	}

}
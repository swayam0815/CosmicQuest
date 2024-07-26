import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * TUTORIALMAIN CLASS <br>
 * <br>
 * Creates a panel to serve as a jumpoff point for the other tutorials <br>
 * <br>
 * 
 * @author Thomas Ma
 * @author Jenish Paudel
 */
public class TutorialMain extends JPanel {

	// private stuff
	/** panels frame */
	private JFrame frame;
	/** the panel background */
	private Image background;
	/** the previous panel */
	private MainMenuScreen prevPanel;

	/**
	 * TUTORIALMAIN CONSTRUCTOR <br>
	 * <br>
	 * Creates the main tutorial screen
	 * 
	 * @param inputFrame The program frame
	 * @param prevPanel  The previous game screen
	 */
	public TutorialMain(JFrame inputFrame, MainMenuScreen prevPanel) {

		// -----------------------------------------------------------------------------------------
		// Setup
		// this panels variables
		this.frame = inputFrame;
		this.prevPanel = prevPanel;

		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setLayout(new BorderLayout());

		// background image

		// images
		this.background = new ImageIcon("src/images/GameBckgrnd.jpg").getImage();

		// ---------------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------------

		// Main panel view
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setOpaque(false);

		// constraints
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(frame.getWidth() / 50, frame.getWidth() / 50, frame.getWidth() / 50,
				frame.getWidth() / 50);

		// ********************************************
		// Gameplay tutorial button
		// making the button
		JLabel gameTutBut = new JLabel();
		gameTutBut.setIcon(new ImageIcon("src/images/gameplay.png"));

		// constraints then adding
		gbc.gridx = 1;
		gbc.gridy = 1;
		mainPanel.add(gameTutBut, gbc);

		// ******************************************
		// User Profile tutorial button

		// making the button
		JLabel userTutBut = new JLabel();
		userTutBut.setIcon(new ImageIcon("src/images/settings.png")); // UPDATE IMAGE HERE
																		// ---------------------------------------

		// constraints then adding
		gbc.gridx = 1;
		gbc.gridy = 2;
		mainPanel.add(userTutBut, gbc);

		// *******************************************
		// How to use the application button

		// making the button
		JLabel appTutBut = new JLabel();
		appTutBut.setIcon(new ImageIcon("src/images/stats.png")); // UPDATE IMAGE HERE ------------------------------

		// constraints then adding
		gbc.gridx = 2;
		gbc.gridy = 1;

		mainPanel.add(appTutBut, gbc);

		// *******************************************
		// Basic math button

		// making the button
		JLabel mathTutBut = new JLabel();
		mathTutBut.setIcon(new ImageIcon("src/images/math.png"));

		// constraints then adding
		gbc.gridx = 2;
		gbc.gridy = 2;

		mainPanel.add(mathTutBut, gbc);

		// ************************************************

		// making the button
		JLabel backButMain = new JLabel();
		backButMain.setIcon(new ImageIcon("src/images/back.png"));

		// add the escape button
		mainPanel.add(backButMain);

		// revalidate at end --------
		mainPanel.revalidate();
		mainPanel.repaint();
		// -------------------------------------------------------------------------------------

		// --------------------------------------------------------------------------------------
		// Gameplay Tutorial

		GameplayTutorial gamePanel = new GameplayTutorial(inputFrame, this);
		// -------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------
		// Application Tutorial

		StatsTutorial appPanel = new StatsTutorial(inputFrame, this);

		// ----------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------
		// User Profile Tutorial

		SettingsTutorial userPanel = new SettingsTutorial(inputFrame, this);

		// ----------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------
		// Math Tutorial

		MathTutorial mathPanel = new MathTutorial(inputFrame, this);

		// ----------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------
		// initialize main panel to start
		this.add(mainPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();

		// -----------------------------------------------------------------------------------------
		// Listeners

		// ********************************************************
		// gameTutButton

		gameTutBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// When button is clicked, do something
				// -------------------------------------------------------
				removePanel();
				gamePanel.addPanel();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				gameTutBut.setIcon(new ImageIcon("src/images/gameplayH.png")); // UPDATE IMAGE HERE
																				// ---------------------------------

			}

			@Override
			public void mouseExited(MouseEvent e) {
				gameTutBut.setIcon(new ImageIcon("src/images/gameplay.png"));

			}
		});

		// ***********************************************************
		// userTutBut

		userTutBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// When button is clicked, do something
				// -------------------------------------------------------
				removePanel();
				userPanel.addPanel();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				userTutBut.setIcon(new ImageIcon("src/images/settingsH.png")); // UPDATE IMAGE HERE
																				// -------------------------------------------------

			}

			@Override
			public void mouseExited(MouseEvent e) {
				userTutBut.setIcon(new ImageIcon("src/images/settings.png")); // UPDATE IMAGE HERE
																				// ------------------------------

			}
		});

		// ************************************************************
		// applicationTutBut

		appTutBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// When button is clicked, do something
				// -------------------------------------------------------
				removePanel();
				appPanel.addPanel();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				appTutBut.setIcon(new ImageIcon("src/images/statsH.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				appTutBut.setIcon(new ImageIcon("src/images/stats.png"));

			}
		});

		// ***********************************************************
		// mathTutBut

		mathTutBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// When button is clicked, do something
				// -------------------------------------------------------
				removePanel();
				mathPanel.addPanel();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mathTutBut.setIcon(new ImageIcon("src/images/mathH.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				mathTutBut.setIcon(new ImageIcon("src/images/math.png"));

			}
		});

		// ***************************************************
		// mainBackButton
		backButMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// When button is clicked, do something
				// -------------------------------------------------------
				removePanel();
				prevPanel.addPanel();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				backButMain.setIcon(new ImageIcon("src/images/backH.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				backButMain.setIcon(new ImageIcon("src/images/back.png"));

			}
		});

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
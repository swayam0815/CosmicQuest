import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * MAINMENUSCREEN CLASS <br>
 * <br>
 * Represents the main menu screen of the game before the user is logged in <br>
 * <br>
 * This class is a panel that gets added and removed from a frame. The frame is
 * made during the main function of this program, which is contained within this
 * class <br>
 * 
 * @author Jenish Paudel
 * @author Swayam Sachdeva
 * @author Thomas Ma (Javadoc)
 */
public class MainMenuScreen extends JPanel {

	// Declaring Variables -------------------------------------------
	/** the panel background */
	private Image background;
	/** the program frame */
	private JFrame frame;
	/** the width of the frame */
	private int w;
	/** the height of the frame */
	private int h;
	/** the title image */
	private JLabel title;
	/** the new account button */
	private JLabel newAccount;
	/** the tutorial button */
	private JLabel learn;
	/** the leaderboard button */
	private JLabel lbb; // leaderboard button
	/** the quit button */
	private JLabel quit;
	/** the settings button */
	private JLabel settings;
	/** the load previous save button */
	private JLabel load;
	/** the copyright text image */
	private JLabel copyrightText;
	/** the hover-over-buton sound file */
	private File hover;
	/** the background music clip */
	public static Clip music = null;
	/** the static main menu panel */
	public static MainMenuScreen mainMenu;
	/** the tutorial screen */
	private TutorialMain tutorialScreen;

	/**
	 * MAINMENUSCREEN CONSTRUCTOR <br>
	 * <br>
	 * Creates the main menu screen
	 * 
	 * Music code taken by Max O'Didily(https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator)
	 * 
	 * @param frame The program frame
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public MainMenuScreen(JFrame frame) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		mainMenu = this;
		AudioInputStream musicIn = AudioSystem.getAudioInputStream(new File("src/sounds/music.wav"));
		music = AudioSystem.getClip();
		
		music.open(musicIn);
		music.setFramePosition(0);
		if (Settings.music)
			music.loop(Clip.LOOP_CONTINUOUSLY);

		// Initializing Variables -------------------------------------------------
		this.frame = frame;
		this.tutorialScreen = new TutorialMain(frame, this);

		w = frame.getWidth();
		h = frame.getHeight();
		hover = new File("src/sounds/hover.wav");
		title = new JLabel();
		newAccount = new JLabel();
		learn = new JLabel();
		lbb = new JLabel();
		quit = new JLabel();
		settings = new JLabel();
		load = new JLabel();
		copyrightText = new JLabel();
		ImageIcon icon = new ImageIcon("src/images/GameBckgrnd.jpg"); // CHANGE BACKGROUND HERE -----------------------
		background = icon.getImage();

		setupPanel();// Sets up this JPanel.

		try {
			addMouseListeners();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	/**
	 * SETUP FUNCTION <br>
	 * <br>
	 * Sets up the panel settings
	 */
	private void setupPanel() {
		// JPanel settings -----------------
		this.setBounds(0, 0, w, h);

		this.setLayout(new BorderLayout());

		// Buttons, other components and their settings ------------------------

		title.setIcon(new ImageIcon("src/images/title.png"));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		newAccount.setIcon(new ImageIcon("src/images/new.png"));
		newAccount.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Top, Left, Bottom, Right padding

		load.setIcon(new ImageIcon("src/images/load.png"));
		load.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		learn.setIcon(new ImageIcon("src/images/learn.png"));
		learn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

		lbb.setIcon(new ImageIcon("src/images/lbb.png"));
		lbb.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding
		//
		quit.setIcon(new ImageIcon("src/images/quit.png"));
		quit.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding
		//
		//
		settings.setIcon(new ImageIcon("src/images/settings.png"));
		settings.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding
		//
		copyrightText.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		copyrightText.setForeground(Color.white);
		copyrightText.setText(
				"<html>Created by Swayam Sachdeva, Matthew McDonald, Jenish Paudel, Dikran Kahiaian, and <br/> Thomas Ma (Group 5) at Western University during the winter 2024 term for the CS2212 course.</html>");
		settings.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		// Making a panel for the JLabel buttons ----
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(250, 0, 0, 0)); // Add top padding

		buttonPanel.setOpaque(false); // making it transparent -----

		JPanel fPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));// 1st row of Button Panel
		fPanel.setOpaque(false);
		fPanel.add(newAccount);
		fPanel.add(load);
		fPanel.add(learn);

		JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // 2nd row of Button Panel
		sPanel.setOpaque(false);
		sPanel.add(lbb);

		JPanel tPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // 2nd row of Button Panel
		tPanel.setOpaque(false);
		tPanel.add(quit);

		// Making a new panel for the copyright text
		JPanel copyrightPanel = new JPanel(new BorderLayout());
		copyrightPanel.setOpaque(false); // Making it transparent

		// Adding the copyright text to the copyright panel
		copyrightPanel.add(BorderLayout.EAST, copyrightText);

		// adding flow panels to the button Panel ---------------------------------
		buttonPanel.add(fPanel);
		buttonPanel.add(sPanel);
		buttonPanel.add(tPanel);

		// adding components to this panel.
		this.add(title, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.CENTER);
		this.add(copyrightPanel, BorderLayout.SOUTH);
	}

	/**
	 * MOUSE LISTENER ADDER FUNCTION<br>
	 * <br>
	 * Sets up the mouse listeners for the panel
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void addMouseListeners() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		Clip hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);

		newAccount.addMouseListener(new MouseAdapter() { // PLAY BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				CosmicQuestLoginScreen loginScreen;
				try {
					loginScreen = new CosmicQuestLoginScreen(frame, 0, MainMenuScreen.this);
					MainMenuScreen.this.removePanel();
					loginScreen.addPanel();

				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // initialize
					// loginScreen
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				newAccount.setIcon(new ImageIcon("src/images/newH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					if (Settings.music)
						hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				newAccount.setIcon(new ImageIcon("src/images/new.png"));

			}
		});

		load.addMouseListener(new MouseAdapter() { // PLAY BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				CosmicQuestLoginScreen loginScreen;
				try {
					loginScreen = new CosmicQuestLoginScreen(frame, 1, MainMenuScreen.this);
					MainMenuScreen.this.removePanel();
					loginScreen.addPanel();

				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // initialize
					// loginScreen
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				load.setIcon(new ImageIcon("src/images/loadH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					if (Settings.music)
						hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				load.setIcon(new ImageIcon("src/images/load.png"));

			}
		});

		learn.addMouseListener(new MouseAdapter() { // LEARN BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				removePanel();
				tutorialScreen.addPanel();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				learn.setIcon(new ImageIcon("src/images/learnH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					if (Settings.music)
						hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				learn.setIcon(new ImageIcon("src/images/learn.png"));

			}
		});
		lbb.addMouseListener(new MouseAdapter() { // LEADERBOARD BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				removePanel();
				try {
					LeaderboardPanel board = new LeaderboardPanel(frame);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lbb.setIcon(new ImageIcon("src/images/lbbh.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					if (Settings.music)
						hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lbb.setIcon(new ImageIcon("src/images/lbb.png"));

			}

		});
		quit.addMouseListener(new MouseAdapter() { // quit BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("CLicked on quit");
				Object[] options = { "QUIT", "CANCEL" };
				int ans = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				if (ans == 0) {
					frame.dispose();
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				quit.setIcon(new ImageIcon("src/images/quitH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					if (Settings.music)
						hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				quit.setIcon(new ImageIcon("src/images/quit.png"));

			}
		});
		settings.addMouseListener(new MouseAdapter() { // SETTINGS BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("CLicked on settings");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				settings.setIcon(new ImageIcon("src/images/settingsH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					if (Settings.music)
						hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				settings.setIcon(new ImageIcon("src/images/settings.png"));

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
		g.drawImage(background, 0, 0, w, h, null);
	}

	/**
	 * MAIN FUNCTION <br>
	 * <br>
	 * The main function that initializes the program frame and opens the main menu
	 * 
	 * @param args The command line arguments
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		JFrame frame = new JFrame();
		frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);

		MainMenuScreen printer = new MainMenuScreen(frame);
		printer.addPanel();

	}

}
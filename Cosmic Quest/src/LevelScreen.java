import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
/**
 * LEVEL SCREEN CLASS <br>
 * <br>
 * This class represents the level screen where the levels are shown. 
 * It contains all the levels that the user can play with their fuel count and avatar image.
 * @author Dikran Kahiaian
 * @author Jenish Paudel
 */
public class LevelScreen extends JPanel{
	/** background image of the screen */
	private Image background;
	/** frame of the game */
	private JFrame frame;
	/** instance of the previous screen */
	private LoggedInMenuScreen prevScreen;
	/** difficulty chosen by the player */
	private int difficultyChosen;
	/** level chosen by the player */
	private int levelChosen;
	/** fuel count of the player*/
	private int fuelCount=0;
	/** level one button of the game */
	private JLabel levelOneButton;
	/** level two button of the game */
	private JLabel levelTwoButton;
	/** level three button of the game */
	private JLabel levelThreeButton;
	/** level four button of the game */
	private JLabel levelFourButton;
	/** level five button of the game */
	private JLabel levelFiveButton;
	/** level one locked button */
	private JLabel levelOneButtonLocked;
	/** level two locked button */
	private JLabel levelTwoButtonLocked;
	/** level three locked button */
	private JLabel levelThreeButtonLocked;
	/** level four locked button */
	private JLabel levelFourButtonLocked;
	/** level five locked button */
	private JLabel levelFiveButtonLocked;
	/** hover sound file */
	private File hover;
	/** hover sound clip */
	private Clip hoverSound;
	/** instance of game panel */
	private GamePanel game;
	/**
	 * LEVEL SCREEN CONSTRUCTOR <br>
	 * <br>
	 * @param frame, the JFrame of the game
	 * @param prevScreen, the previous screen of the game
	 */
	public LevelScreen(JFrame frame, LoggedInMenuScreen prevScreen) throws UnsupportedAudioFileException, IOException, LineUnavailableException { 
		// initializing variables and sounds
		this.frame = frame;
		this.prevScreen = prevScreen;
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);

		ImageIcon icon = new ImageIcon("src/images/Gamebckgrnd.jpg"); //CHANGE BACKGROUND HERE  -----------------------
		background = icon.getImage();

		//JPanel settings -----------------------------------
		this.setBounds(0,0,frame.getWidth(), frame.getHeight());
		this.setLayout(new BorderLayout(10, 5));


		//JPanel components --------------
		JLabel title = new JLabel();
		title.setIcon(new ImageIcon("src/images/title.png")); //SET TITLE IMAGE HERE ----------
		title.setHorizontalAlignment(JLabel.CENTER);

		//getting user data ------------------------------
		String username = "";
		String avatar = "1.png";

		if (CosmicQuestLoginScreen.role == "student") {
			username = CosmicQuestLoginScreen.studentUser.getUsername();
			avatar = CosmicQuestLoginScreen.studentUser.getAvatar();
			if (avatar.equals("none")) {
				avatar = "1.png";
			}
			if (CosmicQuestLoginScreen.studentUser.getFuel().equals("none")) {
				fuelCount =0;
			}
			else fuelCount = Integer.parseInt(CosmicQuestLoginScreen.studentUser.getFuel());
		
		} else {
			username = CosmicQuestLoginScreen.developerUser.getUsername();
			avatar = CosmicQuestLoginScreen.developerUser.getAvatar();
			if (avatar.equals("none")) {
				avatar = "1.png";
			}
			if (CosmicQuestLoginScreen.developerUser.getFuel().equals("none")) {
				fuelCount =0;
			}
			else fuelCount = Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel());
		}

		JPanel userInfoPanel;
		//User info panel --------
		if (CosmicQuestLoginScreen.role.equals("student")) {
			userInfoPanel = userInfo(username,"src/avatars/"+CosmicQuestLoginScreen.studentUser.getAvatar() , fuelCount); //PARAMETER: FILE PATH FOR USER ICON AND FUEL COUNT OF USER ---
		}else {
			userInfoPanel = userInfo(username,"src/avatars/"+CosmicQuestLoginScreen.developerUser.getAvatar() , fuelCount); //PARAMETER: FILE PATH FOR USER ICON AND FUEL COUNT OF USER ---
		}
		userInfoPanel.revalidate();
		userInfoPanel.repaint();

		//Label panel ---------------------
		JPanel levelPanel = levelMap(3);



		//adding to this JPanel -------------------------
		this.add(title, BorderLayout.NORTH);
		this.add(userInfoPanel, BorderLayout.WEST);
		this.add(levelPanel, BorderLayout.CENTER);
		levelPanel.setOpaque(false);
		userInfoPanel.setOpaque(false);
		this.revalidate();
		this.repaint();
		frame.add(this);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * USER INFO METHOD <br>
	 * <br>
	 * Method that sets up the panel that shows the user's avatar, username, and fuel count
	 * @param username, stores the username of the player
	 * @param iconPath, stores the path of the avatar
	 * @param fuelCount, stores the fuel count of the player
	 */
	public JPanel userInfo(String username, String iconPath, int fuelCount) {
		//setting up Panel ----------
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(layout);

		//adding Components -------------------------------------
		JLabel welcome = new JLabel("Welcome, " + username+"!");
		welcome.setForeground(Color.white);
		welcome.setFont(new Font("Arial", Font.BOLD, frame.getHeight()/25));
		welcome.setHorizontalAlignment(JLabel.CENTER);

		JLabel playerIcon = new JLabel();
		playerIcon.setIcon(new ImageIcon(iconPath));


		JLabel fuelCounter = new JLabel("Fuel Count: " + String.valueOf(fuelCount));
		fuelCounter.setForeground(Color.red);
		fuelCounter.setFont(new Font("Arial", Font.BOLD, frame.getHeight()/20));
		fuelCounter.setHorizontalAlignment(JLabel.CENTER);

		JLabel backButton = new JLabel();
		backButton.setIcon(new ImageIcon("src/images/back.png")); //SET BACK IMAGE HERE -----------------
		backButton.setHorizontalAlignment(JLabel.CENTER);

		backButton.addMouseListener(new MouseAdapter () { //BACK BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				removePanel();
				prevScreen.addPanel();

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				backButton.setIcon(new ImageIcon("src/images/backH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;


			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(new ImageIcon("src/images/back.png"));

			}
		});

		//positioning the buttons on the screen
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(welcome, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(playerIcon, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(fuelCounter, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		panel.add(backButton, gbc);


		return panel;		
	}
	/**
	 * LEVEL MAP METHOD <br>
	 * <br>
	 * JPanel that sets up the buttons for the levels to be displayed
	 * @param levelsUnlocked, stores the levels unlocked by the user
	 */
	public JPanel levelMap(int levelsUnlocked) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		ImageIcon icon = new ImageIcon("src/images/bckgrnd.png"); //CHANGE BACKGROUND HERE (Note: picture may need to be made a bit larger) -----------------------


		//creating panel and painting background ------------
		JPanel panel = new JPanel() { 
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(), 0,0,this.getWidth(), this.getHeight(), null);
			}
		};

		//Panel settings ------------------------
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();


		//creating level 1 button -------------------------------------------
		levelOneButton = new JLabel();
		levelOneButtonLocked = new JLabel();
		levelOneButton.setIcon(new ImageIcon("src/images/level1.png"));
		levelOneButtonLocked.setIcon(new ImageIcon("src/images/level1L.png"));


		levelOneButton.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) {
				levelChosen = 1;
				difficultyPanel();

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				levelOneButton.setIcon(new ImageIcon("src/images/level1H.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;


			}
			@Override
			public void mouseExited(MouseEvent e) {
				levelOneButton.setIcon(new ImageIcon("src/images/level1.png"));

			}

		});

		//creating level 2 button -------------------------------------------
		levelTwoButton = new JLabel();
		levelTwoButtonLocked = new JLabel();
		levelTwoButton.setIcon(new ImageIcon("src/images/level2.png"));
		levelTwoButtonLocked.setIcon(new ImageIcon("src/images/level2L.png"));

		levelTwoButton.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) {
				levelChosen = 2;
				difficultyPanel();
				//Add code to show level panel here -------------------------------------------------------------------------------------

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				levelTwoButton.setIcon(new ImageIcon("src/images/level2H.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;


			}
			@Override
			public void mouseExited(MouseEvent e) {
				levelTwoButton.setIcon(new ImageIcon("src/images/level2.png"));

			}

		});

		//creating level 3 button -------------------------------------------
		levelThreeButton = new JLabel();
		levelThreeButtonLocked=new JLabel();
		levelThreeButton.setIcon(new ImageIcon("src/images/level3.png"));
		levelThreeButtonLocked.setIcon(new ImageIcon("src/images/level3L.png"));

		levelThreeButton.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) {
				levelChosen = 3;
				difficultyPanel();
				//Add code to show level panel here -------------------------------------------------------------------------------------

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				levelThreeButton.setIcon(new ImageIcon("src/images/level3H.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;


			}
			@Override
			public void mouseExited(MouseEvent e) {
				levelThreeButton.setIcon(new ImageIcon("src/images/level3.png"));

			}

		});

		//creating level 4 button -------------------------------------------
		levelFourButton = new JLabel();
		levelFourButtonLocked=new JLabel();
		levelFourButton.setIcon(new ImageIcon("src/images/level4.png"));
		levelFourButtonLocked.setIcon(new ImageIcon("src/images/level4L.png"));
		levelFourButton.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent e) {
				levelChosen = 4;
				difficultyPanel();
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				levelFourButton.setIcon(new ImageIcon("src/images/level4H.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;


			}
			@Override
			public void mouseExited(MouseEvent e) {
				levelFourButton.setIcon(new ImageIcon("src/images/level4.png"));

			}

		});

		//creating level 5 button -------------------------------------------
		levelFiveButton = new JLabel();
		levelFiveButtonLocked=new JLabel();
		levelFiveButton.setIcon(new ImageIcon("src/images/level5.png"));
		levelFiveButtonLocked.setIcon(new ImageIcon("src/images/level5L.png"));
		levelFiveButton.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent e) {
				levelChosen = 5;
				difficultyPanel();
				//Add code to show level panel here -------------------------------------------------------------------------------------

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				levelFiveButton.setIcon(new ImageIcon("src/images/level5H.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;


			}
			@Override
			public void mouseExited(MouseEvent e) {
				levelFiveButton.setIcon(new ImageIcon("src/images/level5.png"));

			}
		});


		//adding buttons to panel -----------
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;

		//ADJUST THESE NUMBERS TO POSITION THE PLANETS APPROPRIATELY!!
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(levelOneButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		if (fuelCount>=5000) {
			panel.add(levelTwoButton, gbc);
		}else {
			panel.add(levelTwoButtonLocked, gbc);
		}

		gbc.gridx = 2;
		gbc.gridy = 7;
		if (fuelCount>=10000) {
			panel.add(levelThreeButton, gbc);
		}else {
			panel.add(levelThreeButtonLocked, gbc);
		}
		gbc.gridx = 3;
		gbc.gridy = 2;
		if (fuelCount>=15000) {
			panel.add(levelFourButton, gbc);

		}else {
			panel.add(levelFourButtonLocked, gbc);
		}
		gbc.gridx = 4;
		gbc.gridy = 0;
		if (fuelCount>=20000) {
			
			panel.add(levelFiveButton, gbc);
		}else {
			panel.add(levelFiveButtonLocked, gbc);

		}
		
		

		return panel;
	}

	/**
	 * DIFFICULTYPANEL METHOD <br>
	 * <br>
	 * Method that displays the difficulty panel to choose from and switches to the GamePanel
	 */
	public void difficultyPanel() {
		difficultyChosen = 0; //default value ---------

		JPanel blankPanel = new JPanel();
		blankPanel.setLayout(null);
		blankPanel.setOpaque(false);
		blankPanel.setBounds(0,0, frame.getWidth(),frame.getHeight());
		blankPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
	       // Create a rounded border

		
		// set up the panel
		JPanel diffPanel = new JPanel();
		diffPanel.setBackground(new Color(0x4B0082)); // Set background color of the panel-
		diffPanel.setLayout(new GridLayout(5, 0)); // Set grid layout
		diffPanel.setBounds(blankPanel.getWidth()/3,blankPanel.getHeight()/3, blankPanel.getWidth()/3,blankPanel.getHeight()*3/5);
		
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK, 7, true);
        diffPanel.setBorder(roundedBorder);
		

		JLabel title = new JLabel("Difficulty");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, diffPanel.getWidth()/10));
		title.setForeground(Color.white);

		JLabel easyButton = new JLabel();
		easyButton.setIcon(new ImageIcon("src/images/easy.png"));
		easyButton.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel mediumButton = new JLabel();
		mediumButton.setIcon(new ImageIcon("src/images/medium.png"));
		mediumButton.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel hardButton = new JLabel();
		hardButton.setIcon(new ImageIcon("src/images/hard.png"));
		hardButton.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel closeButton = new JLabel();
		closeButton.setIcon(new ImageIcon("src/images/close.png"));
		closeButton.setHorizontalAlignment(JLabel.CENTER);

		// Add buttons -----
		diffPanel.add(title);
		diffPanel.add(easyButton);
		diffPanel.add(mediumButton);
		diffPanel.add(hardButton);
		diffPanel.add(closeButton);
		
        blankPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Prevent clicks from reaching components behind the panel
                e.consume();
            }
        });

		// Add the panel to the frame
        blankPanel.add(diffPanel);
		frame.setGlassPane(blankPanel);
		frame.getGlassPane().setVisible(true);
		
		


		//USER CLICKS ON EASY BUTTON -------------------------------------
		easyButton.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) { //EASY BUTTON CODE HERE -------------------
				// TODO Auto-generated method stub
				difficultyChosen = 1;
				frame.getGlassPane().setVisible(false);
				removePanel();
				game = new GamePanel(frame, levelChosen, difficultyChosen);
			
				frame.revalidate();
				frame.repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				easyButton.setIcon(new ImageIcon("src/images/easyH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;

			}
			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(new ImageIcon("src/images/easy.png"));
				
			}
		});
		
		
		
		
		//USER CLICKS ON MEDIUM BUTTON -------------------------------------
		mediumButton.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) { //MEDIUM BUTTON CODE HERE -------------------
				// TODO Auto-generated method stub
				difficultyChosen = 2;
				frame.getGlassPane().setVisible(false);
				removePanel();
				game = new GamePanel(frame, levelChosen, difficultyChosen);
				frame.revalidate();
				frame.repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				mediumButton.setIcon(new ImageIcon("src/images/mediumH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;

			}
			@Override
			public void mouseExited(MouseEvent e) {
				mediumButton.setIcon(new ImageIcon("src/images/medium.png"));
				
			}
		});

		//USER CLICKS ON HARD BUTTON -------------------------------------
		hardButton.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				difficultyChosen = 3;
				frame.getGlassPane().setVisible(false);
				removePanel();
				game = new GamePanel(frame, levelChosen, difficultyChosen);
				frame.revalidate();
				frame.repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //HARD BUTTON CODE HERE -----------------------------
				hardButton.setIcon(new ImageIcon("src/images/hardH.png")); 
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;

			}
			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(new ImageIcon("src/images/hard.png"));
				
			}
		});

		//USER CLICKS ON CLOSE BUTTON -------------------------------------
		closeButton.addMouseListener(new MouseAdapter () { 
			@Override
			public void mouseClicked(MouseEvent e) { //CLOSE BUTTON CODE HERE ----------------------------------------------------------
				// TODO Auto-generated method stub
				difficultyChosen = 0;
				frame.getGlassPane().setVisible(false);
				frame.revalidate();
				frame.repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				closeButton.setIcon(new ImageIcon("src/images/closeH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;

			}
			@Override
			public void mouseExited(MouseEvent e) {
				closeButton.setIcon(new ImageIcon("src/images/close.png"));
				
			}
		});

	}

	/**
	 * REMOVEPANEL METHOD <br>
	 * <br>
	 * Method to remove the current panel being displayed
	 */
	private void removePanel() { //removes this panel from Frame.
		frame.remove(this);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * PAINTCOMPONENT METHOD<br>
	 * <br>
	 * Method to paint the background image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0,0,null);
	}



}
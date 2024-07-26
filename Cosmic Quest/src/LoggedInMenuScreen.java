import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
 * LOGGED IN MENU SCREEN CLASS <br>
 * <br>
 * Represents the menu screen after the user is logged in including play, settings, logout, stats
 * @author Jenish Paudel
 */
public class LoggedInMenuScreen extends JPanel implements KeyListener {
	/** frame of the game*/
	private JFrame frame;
	/** role of the user currently logged in */
	private String role;
	/** title of the page */
	private JLabel title;
	/** play button */
	private JLabel play;
	/** stats button */
	private JLabel stats;
	/** logout button */
	private JLabel logout;
	/** setting button */
	private JLabel settings;
	/** class list button */
	private JLabel classList;
	/** question set button */
	private JLabel questionSet;
	/** south panel of the screen */
	private JPanel sPanel;
	/** button panel with all the buttons */
	private JPanel buttonPanel;
	/** top panel */
	private JPanel tPanel;
	/** background image */
	private Image background;
	/** sound file */
	private File hover;
	/** sound clip */
	private Clip hoverSound;

	/**
	 * LOGGEDINMENUSCREEN CONSTRUCTOR <br>
	 * <br>
	 * Constructor that sets up the panel with all the variables
	 * @param frame JFrame that this panel needs to be added to
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public LoggedInMenuScreen(JFrame frame) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		frame.addKeyListener(this); // Add an instance of Main as KeyListener
		frame.setFocusable(true); // Ensure the frame is focusable
		ImageIcon icon = new ImageIcon("src/images/GameBckgrnd.jpg"); //CHANGE BACKGROUND HERE  -----------------------
		background = icon.getImage();
		// add hover sound effects
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);
		this.role = CosmicQuestLoginScreen.role;
		
		if (role.equals("student") || role.equals("developer")) {
			//if student role or developer role, we need these components --------
			this.play = new JLabel();
			this.stats = new JLabel();
		} else if (role.equals("teacher")) {
			//if role is a teacher, we need these components  -----------------
			this.classList = new JLabel();
			this.questionSet = new JLabel();
		}
		else {
			//if role is any other string throw an error ------
			throw new IllegalArgumentException("Error with LoggedInMenuScreen(): The \"role\"  must be either \"student\", \"teacher\" or \"developer\" ");

		}

		this.frame = frame;
		this.title = new JLabel();
		this.logout = new JLabel();
		this.settings = new JLabel();

		setupPanel();
		addMouseListeners();
	}
	/**
	 * SET UP PANEL FUNCTION <br>
	 * <br>
	 * Sets up the panel for the buttons to be displayed depending on the role of the user
	 */
	private void setupPanel() {
		//JPanel settings -----------------
		this.setBounds(0,0, frame.getWidth(),frame.getHeight());
		this.setLayout(new BorderLayout());
		//Buttons, other components and their settings ------------------------

		title.setIcon(new ImageIcon("src/images/title.png"));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		
		JPanel fPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0 ));//1st row of Button Panel
		fPanel.setOpaque(false);

		if(role == "student" ||role == "developer") {
			play.setIcon(new ImageIcon("src/images/play.png"));
			play.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
			
			stats.setIcon(new ImageIcon("src/images/stats.png")); //ICON NEEDS TO BE CHANGED TO STATS -----------------------------
			stats.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
			fPanel.add(play);
			fPanel.add(stats);
			
		} else {
			classList.setIcon(new ImageIcon("src/images/classlist.png")); //ICON NEEDS TO BE CHANGED TO CLASS LIST ------------------------------------
			classList.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			questionSet.setIcon(new ImageIcon("src/images/qset.png")); //ICON NEEDS TO BE CHANGED TO QUESTIONSET --------------------------------------------
			questionSet.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
			
			fPanel.add(classList);
			fPanel.add(questionSet);
		}


		logout.setIcon(new ImageIcon("src/images/logout.png"));
		logout.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding
		//
		//
		settings.setIcon(new ImageIcon("src/images/settings.png"));
		settings.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); //  padding
		
		sPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0 )); //2nd row of Button Panel
		sPanel.setOpaque(false);
		sPanel.add(settings);

		tPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0 )); //2nd row of Button Panel
		tPanel.setOpaque(false);
		tPanel.add(logout);


		//Making a panel for the JLabel buttons ----
		buttonPanel = new JPanel(new GridLayout(4,1));
		buttonPanel.setOpaque(false); //making it transparent -----


		//adding flow panels to the button Panel ---------------------------------
		buttonPanel.add(fPanel);
		//adding components to this panel.
		this.add(title, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);

	}
	/**
	 * ADD MOUSE LISTENERS FUNCTION <br>
	 * <br>
	 * Performs actions according to user clicks
	 * 
	 */
	private void addMouseListeners() {
		
		if (role.equals("student") || role.equals("developer") ) {
			buttonPanel.add(sPanel);
			buttonPanel.add(tPanel);

			play.addMouseListener(new MouseAdapter () { 
				@Override
				public void mouseClicked(MouseEvent e) {
					//When button is clicked, do something -------------------------------------------------------
					try {
						LoggedInMenuScreen.this.removePanel();
						LevelScreen screen = new LevelScreen(frame, LoggedInMenuScreen.this);
					} catch (UnsupportedAudioFileException e1) {

						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					play.setIcon(new ImageIcon("src/images/playH.png"));
					hoverSound.setFramePosition(0); // Rewind to the beginning
					if (Settings.music) hoverSound.start();

				}
				@Override
				public void mouseExited(MouseEvent e) {
					play.setIcon(new ImageIcon("src/images/play.png"));

				}
			});
			
			stats.addMouseListener(new MouseAdapter () {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (CosmicQuestLoginScreen.role.equals("student")) {
						try {
							removePanel();
							new IndividualStatsPanel(frame, CosmicQuestLoginScreen.studentUser);
						} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						}
					} else if (CosmicQuestLoginScreen.role.equals("developer")) {
						try {
							removePanel();
							new IndividualStatsPanel(frame, CosmicQuestLoginScreen.developerUser);
						} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						}					}
					
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					stats.setIcon(new ImageIcon("src/images/statsH.png"));
					hoverSound.setFramePosition(0); // Rewind to the beginning
					if (Settings.music) hoverSound.start();;

				}
				@Override
				public void mouseExited(MouseEvent e) {
					stats.setIcon(new ImageIcon("src/images/stats.png"));

				}
			});
			
		}
		else {
			buttonPanel.add(tPanel);
			classList.addMouseListener(new MouseAdapter () {
				@Override
				public void mouseClicked(MouseEvent e) {
					removePanel();
					try {
						AddStudentScreen screen = new AddStudentScreen(frame, LoggedInMenuScreen.this);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					classList.setIcon(new ImageIcon("src/images/classlistH.png"));
					hoverSound.setFramePosition(0); // Rewind to the beginning
					if (Settings.music) hoverSound.start();;
				}
				@Override
				public void mouseExited(MouseEvent e) {
					classList.setIcon(new ImageIcon("src/images/classlist.png")); //UPDATE IMAGE HERE -------------------------------------------------------------
				}
			});
			
			questionSet.addMouseListener(new MouseAdapter () { 
				@Override
				public void mouseClicked(MouseEvent e) {
					//When button is clicked, do something -------------------------------------------------------
					removePanel();
					try {
						QuestionSetPanel screen = new QuestionSetPanel(frame, LoggedInMenuScreen.this);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					}
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					questionSet.setIcon(new ImageIcon("src/images/qsetH.png")); //UPDATE IMAGE HERE -------------------------------------------------
					hoverSound.setFramePosition(0); // Rewind to the beginning
					if (Settings.music) hoverSound.start();;
				}
				@Override
				public void mouseExited(MouseEvent e) {
					questionSet.setIcon(new ImageIcon("src/images/qset.png"));
				}
			});
			
		}

		logout.addMouseListener(new MouseAdapter () { //quit BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				Object [] options = {"QUIT", "CANCEL"};
				int ans = JOptionPane.showOptionDialog (null, "Are you sure you want to quit?", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options [0]);
				
				if (ans == 0) {
					  // Remove the key listener from the frame
		            frame.removeKeyListener(LoggedInMenuScreen.this);
					LoggedInMenuScreen.this.removePanel();
					frame.add(MainMenuScreen.mainMenu);
					frame.revalidate();
					frame.repaint();
				}

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				logout.setIcon(new ImageIcon("src/images/logoutH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;

			}
			@Override
			public void mouseExited(MouseEvent e) {
				logout.setIcon(new ImageIcon("src/images/logout.png"));
				
			}
		});
		settings.addMouseListener(new MouseAdapter () { //SETTINGS BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				removePanel();
				try {
					Settings Settings=new Settings(frame, LoggedInMenuScreen.this);
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				settings.setIcon(new ImageIcon("src/images/settingsH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;

			}
			@Override
			public void mouseExited(MouseEvent e) {
				settings.setIcon(new ImageIcon("src/images/settings.png"));

			}
		});

	}
	
	/**
	 * ADD PANEL FUNCTION <br>
	 * <br>
	 * method that adds the current panel into the frame
	 */
	public void addPanel() {

		frame.add(this);
		this.revalidate();
		this.repaint();
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * REMOVE PANEL FUNCTION <br>
	 * <br>
	 * method that removes the current panel from the screen
	 */
	public void removePanel() { //removes this panel from the frame ------
		frame.remove(this);
		frame.repaint();
	}
	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * method that paints the background image
	 * @param Graphics g, the graphics used for the image to be painted
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0,0, this.getWidth(), this.getHeight(), null);
	}
	/**1
	 * KEY PRESSED FUNCTION <br>
	 * <br>
	 * Method that takes user's key input and performs actions accordingly
	 */
	@Override
	public void keyPressed(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	        // Stop the game panel if it's currently running
	        if (frame.getContentPane().getComponentCount() > 0 && frame.getContentPane().getComponent(0) instanceof GamePanel) {
	            GamePanel gamePanel = (GamePanel) frame.getContentPane().getComponent(0);
	            gamePanel.timer.stop();
	            gamePanel.asteroidSpawnTimer.stop();
	            frame.getContentPane().removeAll();
	            frame.remove(gamePanel);
	            frame.revalidate();
	            frame.repaint();
	        }

	        // Remove the key listener from the main menu
	        frame.removeKeyListener(this);

	        // Add the main menu screen
	        frame.getContentPane().removeAll();
	        try {
	        	frame.getGlassPane().setVisible(false);
				frame.add(new LoggedInMenuScreen(frame));
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        frame.revalidate();
	        frame.repaint();
	    }
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}


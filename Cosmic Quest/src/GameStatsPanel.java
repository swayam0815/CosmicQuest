import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * GAMES STATS PANEL CLASS <br>
 * <br>
 * This class represents the panel that displays game statistics and outcome
 * after completing a level. It includes information such as the player's
 * remaining lives, fuel count, number of correct answers, total questions, and
 * whether the player passed or failed the level.
 * 
 * @author Dikran Kahiaian
 * @author Matthew McDonald (Javadoc)
 */
public class GameStatsPanel<T> extends JPanel {
	/** Lives remaining at end of game */
	private int lives;
	/** Fuel obtained at end of game */
	private int gameFuel;
	/** Correct answers obtained by end of game */
	private int correctAnswers;
	/** Number of questions obtained by end of game */
	private int totalQuestions;
	/** The game's level that was chosen */
	private int levelChosen;
	/** The game's difficulty that was chosen */
	private int difficultyChosen;
	/** The boolean of whether or not the user passed */
	private boolean passed;
	/** The stat panel's frame */
	private JFrame frame;
	/** back ton the menu button */
	private JLabel menuButton;
	/** replay level button */
	private JLabel replayButton;
	/** savedGameText for saved game*/
	private JLabel savedGameText;
	/** hover sound file */
	private File hover;
	/** hover clip to play the hover sound file */
	private Clip hoverSound;

	/**
	 * GAME STATS PANEL CONSTRUCTOR
	 *
	 * @param lives            is the remaining lives of the player.
	 * @param gameFuel         is the fuel count of the player for that level.
	 * @param correctAnswers   is the number of correct answers given by the player.
	 * @param totalQuestions   is the total number of questions in the level.
	 * @param frame            is the JFrame to which this panel belongs.
	 * @param passed           is a boolean indicating whether the player passed the
	 *                         level (true) or failed (false)
	 * @param difficultyChosen is the difficulty level chosen by the player.
	 * @param levelChosen      is the level chosen by the player.
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 */
	public GameStatsPanel(int lives, int gameFuel, int correctAnswers, int totalQuestions, JFrame frame, boolean passed,int difficultyChosen, int levelChosen)throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		//initialize all variables
		this.lives = lives;
		this.gameFuel = gameFuel;
		this.correctAnswers = correctAnswers;
		this.totalQuestions = totalQuestions;
		this.passed = passed;
		this.levelChosen = levelChosen;
		this.difficultyChosen = difficultyChosen;
		this.frame = frame;
		menuButton = new JLabel();
		replayButton = new JLabel();
		savedGameText = new JLabel();
		menuButton.setIcon(new ImageIcon("src/images/check.png"));
		replayButton.setIcon(new ImageIcon("src/images/back.png"));
		savedGameText.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		savedGameText.setForeground(Color.white);
		savedGameText.setText("Saved Game!");
		//set the layouts and positions of buttons and text
		setLayout(null);
		Dimension menuButtonSize = menuButton.getPreferredSize();
		menuButton.setBounds(1400, 500, menuButtonSize.width, menuButtonSize.height);
		replayButton.setBounds(1400, 650, menuButtonSize.width, menuButtonSize.height);
		savedGameText.setBounds(1500, 800, 300, 300);
		add(menuButton);
		add(replayButton);

		AudioInputStream sound;
		// play the level passed audio if the user passed and failed audio if failed
		if (passed) {
			sound = AudioSystem.getAudioInputStream(new File("src/sounds/passed.wav"));
			Clip pass = AudioSystem.getClip();
			pass.open(sound);
			pass.start();
		} else {
			sound = AudioSystem.getAudioInputStream(new File("src/sounds/failed.wav"));
			Clip fail = AudioSystem.getClip();
			fail.open(sound);
			fail.start();
		}

		// add hover sound effects
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);
		addMouseListeners();

	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Overrides the paintComponent method to draw the game statistics and outcome
	 * on the panel
	 *
	 * @param g The Graphics object used for painting.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Clear the panel
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		g2D.setColor(Color.red);
		g2D.drawImage(new ImageIcon("src/images/GameBckgrnd.jpg").getImage(), 0, 0, this);
		//display level stats
		g2D.drawImage(new ImageIcon("src/images/levelStats.png").getImage(), 500, 200, this);
		g2D.drawString("Lives Remaining: ", 600, 400);
		g2D.drawString(String.valueOf(lives), 920, 400);
		g2D.drawString("Fuel Achieved: ", 600, 500);
		g2D.drawString(String.valueOf(gameFuel), 900, 500);
		g2D.drawString("Total Fuel: ", 600, 600);
		if(CosmicQuestLoginScreen.role.equals("student")&&CosmicQuestLoginScreen.studentUser.getFuel().equals("none")) {
            g2D.drawString(String.valueOf(gameFuel), 824, 600);
        }else if (CosmicQuestLoginScreen.role.equals("student")) {
         g2D.drawString(String.valueOf(Integer.parseInt(CosmicQuestLoginScreen.studentUser.getFuel())+gameFuel), 824, 600);
        }else{
            g2D.drawString(String.valueOf(gameFuel),824, 600);
        }		
		g2D.drawString("Correct Answers: ", 600, 700);
		g2D.drawString(String.valueOf(correctAnswers), 940, 700);
		g2D.drawString("Total Questions: ", 600, 800);
		g2D.drawString(String.valueOf(totalQuestions), 925, 800);
		if (passed) {
			g2D.drawImage(new ImageIcon("src/images/levelpassed.png").getImage(), 500, 0, this);
		} else {
			g2D.drawImage(new ImageIcon("src/images/levelfailed.png").getImage(), 500, 0, this);
		}
	}
	/**
	 * SAVE STATS FUNCTION <br>
	 * <br>
	 * Save stats method that saves all the user's stats for the level played
	 *
	 */
	private void saveStats() {
		String stats = "";
		//check if the user is a student and update their fuel level
		if (CosmicQuestLoginScreen.role.equals("student")&& CosmicQuestLoginScreen.studentUser.getFuel().equals("none")) {
			CosmicQuestLoginScreen.studentUser.setFuel(String.valueOf(gameFuel));
		}else if (CosmicQuestLoginScreen.role.equals("student")) {
			CosmicQuestLoginScreen.studentUser.setFuel(String.valueOf(gameFuel + Integer.parseInt(CosmicQuestLoginScreen.studentUser.getFuel())));

		}
		// check the level chosen and update their stats for that specific level
		if (levelChosen == 1) {
			stats = CosmicQuestLoginScreen.studentUser.getAdditionStat();
			stats = calculateStats(stats);
			CosmicQuestLoginScreen.studentUser.setAdditionStat(stats);
			CosmicQuestLoginScreen.studentUser.saveInfo();
		}

		if (levelChosen == 2) {
			stats = CosmicQuestLoginScreen.studentUser.getSubtractionStat();
			stats = calculateStats(stats);
			CosmicQuestLoginScreen.studentUser.setSubtractionStat(stats);
			CosmicQuestLoginScreen.studentUser.saveInfo();

		}

		if (levelChosen == 3) {
			stats = CosmicQuestLoginScreen.studentUser.getMultiplicationStat();
			stats = calculateStats(stats);
			CosmicQuestLoginScreen.studentUser.setMultiplicationStat(stats);
			CosmicQuestLoginScreen.studentUser.saveInfo();
		}

		if (levelChosen == 4) {
			stats = CosmicQuestLoginScreen.studentUser.getDivisionStat();
			stats = calculateStats(stats);
			CosmicQuestLoginScreen.studentUser.setDivisionStat(stats);
			CosmicQuestLoginScreen.studentUser.saveInfo();

		}
		
	}
	/**
	 * calculateStats FUNCTION <br>
	 * <br>
	 * calculateStats method that updates user's level and their stats for this level
	 *
	 * @param stats string that stores their current stats before this level
	 */
	private String calculateStats(String stats) {
		int numerator = 0;
		int denominator = 0;
		//check the user's fuel and change their level accordingly
		if (Integer.parseInt(CosmicQuestLoginScreen.studentUser.getFuel())>=5000) {
			CosmicQuestLoginScreen.studentUser.setLevel("2");
		}

		if (Integer.parseInt(CosmicQuestLoginScreen.studentUser.getFuel())>=10000) {
			CosmicQuestLoginScreen.studentUser.setLevel("3");

		}
		if (Integer.parseInt(CosmicQuestLoginScreen.studentUser.getFuel())>=15000) {
			CosmicQuestLoginScreen.studentUser.setLevel("4");
		}
	
		if (Integer.parseInt(CosmicQuestLoginScreen.studentUser.getFuel())>=20000) {
			CosmicQuestLoginScreen.studentUser.setLevel("5");
		}
		if (stats.equals("none")) {
			stats = String.valueOf(correctAnswers) + "/" + String.valueOf(totalQuestions);
			return stats;
		} else {
			//split the string from / and add the correct and total questions 
			String[] lineParts = stats.split("/");
			numerator = Integer.parseInt(lineParts[0]) + correctAnswers;
			denominator = Integer.parseInt(lineParts[1]) + totalQuestions;
			stats = String.valueOf(numerator) + "/" + String.valueOf(denominator);
			return stats;
		}
		
	}
	private void addMouseListeners() {
		menuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (CosmicQuestLoginScreen.role.equals("student")) {
					saveStats();
				}
				frame.getContentPane().removeAll();
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();

				// Create a new LoggedInMenuScreen panel
				LoggedInMenuScreen loggedInMenuScreen;
				try {
					loggedInMenuScreen = new LoggedInMenuScreen(frame);
					// Add the "Saved Game!" savedGameText to the LoggedInMenuScreen panel
					loggedInMenuScreen.add(savedGameText);
					// Add the LoggedInMenuScreen panel to the frame
					frame.add(loggedInMenuScreen);
					// Use Timer to switch to the LoggedInMenuScreen panel after a delay
					Timer timer = new Timer(2000, new ActionListener() { // 3000 milliseconds = 3 seconds
						@Override
						public void actionPerformed(ActionEvent e) {
							loggedInMenuScreen.remove(savedGameText); // Remove the "Saved Game!" savedGameText from the
																// LoggedInMenuScreen panel
							frame.getContentPane().removeAll(); // Remove all components from the frame
							frame.getContentPane().revalidate();
							frame.getContentPane().repaint();
							frame.getContentPane().add(loggedInMenuScreen); // Add the LoggedInMenuScreen panel to the
																			// frame

						}
					});
					timer.setRepeats(false); // Make the timer run only once
					timer.start(); // Start the timer
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				menuButton.setIcon(new ImageIcon("src/images/checkH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				menuButton.setIcon(new ImageIcon("src/images/check.png"));

			}

		});
		replayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Clear the frame
				frame.getContentPane().removeAll();
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				// Add the new panel
				frame.add(new GamePanel(frame, levelChosen, difficultyChosen));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				replayButton.setIcon(new ImageIcon("src/images/backH.png"));
				// Rewind to the beginning
				hoverSound.setFramePosition(0);
				if (Settings.music)
					hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				replayButton.setIcon(new ImageIcon("src/images/back.png"));
			}
		});
	}
}
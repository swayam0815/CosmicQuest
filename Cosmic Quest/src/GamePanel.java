import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * GAME PANEL CLASS <br>
 * <br>
 * This class represents the main panel where the game is played. It contains
 * asteroids, questions, player stats, and handles game logic.
 * 
 * @author Dikran Kahiaian
 * @author Matthew McDonald (Javadoc)
 */
public class GamePanel extends JPanel implements ActionListener {
	/** The game's list of asteroids */
	private List<Asteroid> asteroids;
	/** The game's list of questions */
	private ArrayList<String[]> questionsGenerated;
	/** questions object to generate questions */
	private Question questions;
	/** The game's animation timer */
	protected Timer timer;
	/** The game's spawn timer */
	protected Timer asteroidSpawnTimer;
	/** The game's current asteroid counter */
	private int counter = 0;
	/** The game's number */
	private int fuelCount = 0;
	/** The game's number of remaining lives */
	private int lives = 3;
	/** The game's number of correct answers */
	private int correctAnswers = 0;
	/** The game's current level */
	private int levelChosen = 1;
	/** The game's difficulty */
	private int difficultyChosen = 1;
	/** The game's question font */
	private Font questionFont;
	/** The game's statistics font */
	private Font gameStatsFont;
	/** The game's field for answer input */
	private JTextField answerField;
	/** The game's fuel count */
	private JLabel fuel;
	/** The game's first life */
	private JLabel live1;
	/** The game's second life */
	private JLabel live2;
	/** The game's third life */
	private JLabel live3;
	/** The game's panel to hold current fuel and lives */
	private JPanel topLeftPanel;
	/** The main game frame */
	private JFrame frame;

	/**
	 * GAME PANEL CONSTRUCTOR
	 *
	 * @param frame            is the JFrame to which this panel belongs
	 * @param difficultyChosen is the chosen difficulty level
	 * @param levelChosen      is the chosen level
	 */
	GamePanel(JFrame frame, int levelChosen, int difficultyChosen) {
		// initialize variables
		this.frame = frame;
		this.setBounds(0,0,frame.getWidth(), frame.getHeight());
		this.difficultyChosen = difficultyChosen;
		this.levelChosen = levelChosen;
		live1 = new JLabel(new ImageIcon("src/images/lives.png"));
		live2 = new JLabel(new ImageIcon("src/images/lives.png"));
		live3 = new JLabel(new ImageIcon("src/images/lives.png"));
		questionFont = new Font("Comic Sans MS", Font.BOLD, 60);
		gameStatsFont = new Font("Comic Sans MS", Font.BOLD, 40);
		asteroids = new ArrayList<>();
		questionsGenerated = new ArrayList<String[]>();
		generateQuestions();
		// timer for the screen to refresh every 5ms
		timer = new Timer(5, this);
		timer.start();
		// timer for asteroid spawns every 1 seconds
		asteroidSpawnTimer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAsteroid();
			}
		});
		asteroidSpawnTimer.start();

		// setting the layout of the panel, adjusting its alignment and adding
		// components to it
		setLayout(new FlowLayout());
		topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topLeftPanel.setOpaque(false);
		topLeftPanel.add(live1);
		topLeftPanel.add(live2);
		topLeftPanel.add(live3);

		JLabel fuelLabel = new JLabel("Fuel:");
		fuelLabel.setFont(gameStatsFont);
		fuelLabel.setForeground(Color.RED);
		topLeftPanel.add(fuelLabel);

		fuel = new JLabel(String.valueOf(fuelCount));
		fuel.setFont(gameStatsFont);
		fuel.setForeground(Color.RED);
		topLeftPanel.add(fuel);

		answerField = new JTextField();
		answerField.setPreferredSize(new Dimension(200, 100));
		answerField.setFont(gameStatsFont);
		answerField.setHorizontalAlignment(JTextField.CENTER);

		// adding the panels to the this JPanel
		add(topLeftPanel, BorderLayout.NORTH);
		add(answerField, BorderLayout.SOUTH);
		frame.add(this);
	}

	/**
	 * CREATE ASTEROID FUNCTION <br>
	 * <br>
	 * This function will create an asteroid
	 */
	private void createAsteroid() {
		// get a random Y coordinate
		if (counter < questionsGenerated.size()) {
			int randomY = (int) (Math.random() * getHeight());
			// check if we're out of bounds
			if (randomY > 890) {
				// create a Asteroid object that stores the question with its coordinate
				asteroids.add(new Asteroid(0, randomY - 250, questionsGenerated.get(counter)));
			} else {
				asteroids.add(new Asteroid(0, randomY, questionsGenerated.get(counter)));
			}
			counter++;
		}
	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Custom painting method to draw asteroids and questions
	 * 
	 * @param g The Graphics object to paint
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		// draw the images needed to be displayed
		g2D.drawImage(new ImageIcon("src/images/GameBckgrnd.jpg").getImage(), 0, 0, this);
		g2D.drawImage(new ImageIcon("src/images/rocket.png").getImage(), 1000, 50, this);
		g2D.setFont(questionFont);
		g2D.setColor(Color.RED);
		// loop through the asteroids list and display them
		for (Asteroid asteroid : asteroids) {
			g2D.drawImage(new ImageIcon("src/images/asteroid.png").getImage(), asteroid.getX(), asteroid.getY(), this);
			g2D.drawString(asteroid.getQuestion(), asteroid.getX() + 80, asteroid.getY() + 140);
		}
	}
	
	/**
	 * ACTION PERFORMED FUNCTION <br>
	 * <br>
	 * Handles timer events and game logic
	 * 
	 * @param e The ActionEvent representing the timer event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// move the asteroids across the screen
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid asteroid = asteroids.get(i);
			asteroid.move();
			// remove the asteroids that are out of bounds
			if (asteroid.getX() > getWidth()) {
				asteroids.remove(i);
				// if they missed an asteroid, take a health away
				try {
					updateLives(lives - 1);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		}

		try {
			// get input for the answers
			answerField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String userInput = answerField.getText();

					try {
						int userNumber = Integer.parseInt(userInput);
						boolean asteroidHit = false;
						// loop through each asteroid and see if any of the asteroids have matching
						// answer
						for (int i = 0; i < asteroids.size(); i++) {
							Asteroid asteroid = asteroids.get(i);
							if (userNumber == asteroid.getAnswer()) {
								// remove the asteroid off of the screen if the answer matches
								asteroids.remove(i);
								
								// reset the answer field and reward the player with points
								answerField.setText("");
								fuelCount += 100;
								correctAnswers += 1;
								fuel.setText(String.valueOf(fuelCount));
								asteroidHit = true;
								if (counter == questionsGenerated.size()) {
									endGame();
								}
								break;
							}
						}
						// if the user entered the wrong answer, take away a health
						if (!asteroidHit) {
							try {
								updateLives(lives - 1);
							} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
								System.out.println("Game panel error code 1");
							}
							answerField.setText("");
						}
					} catch (NumberFormatException | LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
						answerField.setText("");
					}
				}
			});
			
			
			//ESCAPE KEY CODE HERE ----------------------------------------------------------------------------------------------------------
			
			KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
			String keyname = "escape";
			answerField.getInputMap().put(escapeKey, keyname);
			answerField.getActionMap().put(keyname, new AbstractAction () {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					timer.stop();
					asteroidSpawnTimer.stop();
					frame.remove(GamePanel.this);
					try {
						LoggedInMenuScreen newScreen = new LoggedInMenuScreen(frame);
						newScreen.addPanel();
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});

		} catch (Exception NullPointerException) {
			System.out.println("Game panel error code 3");
		}
		repaint();
	}

	/**
	 * UPDATE LIVES FUNCTION <br>
	 * <br>
	 * Updates the player's lives and handles end-game conditions.
	 *
	 * @param newLives The updated number of lives.
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void updateLives(int newLives) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		// check the amount of lives
		this.lives = newLives;
		if (lives == 0) {
			removeAll();
			revalidate();
			endGame();
		} else if (lives == 2) {
			topLeftPanel.remove(live1);
			if (counter == questionsGenerated.size()) {
				endGame();
			}
		} else if (lives == 1) {
			topLeftPanel.remove(live2);
			if (counter == questionsGenerated.size()) {
				endGame();
			}
		}
	}

	/**
	 * GENERATE QUESTIONS METHOD<br>
	 * <br>
	 * method to generate questions depending on the level and difficulty chosen
	 */
	private void generateQuestions() {
		// generate questions depending on level and difficulty*/
		if (difficultyChosen == 1 && levelChosen == 1) {
			questions = new Question("add", "easy");
			for (int i = 0; i < 20; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 2 && levelChosen == 1) {
			questions = new Question("add", "medium");
			for (int i = 0; i < 25; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 3 && levelChosen == 1) {
			questions = new Question("add", "hard");
			for (int i = 0; i < 30; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 1 && levelChosen == 2) {
			questions = new Question("subtract", "easy");
			for (int i = 0; i < 20; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 2 && levelChosen == 2) {
			questions = new Question("subtract", "medium");
			for (int i = 0; i < 25; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 3 && levelChosen == 2) {
			questions = new Question("subtract", "hard");
			for (int i = 0; i < 30; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 1 && levelChosen == 3) {
			questions = new Question("multiply", "easy");
			for (int i = 0; i < 20; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 2 && levelChosen == 3) {
			questions = new Question("multiply", "medium");
			for (int i = 0; i < 25; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 3 && levelChosen == 3) {
			questions = new Question("multiply", "hard");
			for (int i = 0; i < 30; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 1 && levelChosen == 4) {
			questions = new Question("divide", "easy");
			for (int i = 0; i < 20; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 2 && levelChosen == 4) {
			questions = new Question("divide", "medium");
			for (int i = 0; i < 25; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 3 && levelChosen == 4) {
			questions = new Question("divide", "hard");
			for (int i = 0; i < 30; i++) {
				questionsGenerated.add(i, questions.generateQuestion());
			}
		}
		if (difficultyChosen == 1 && levelChosen == 5) {
				if (CosmicQuestLoginScreen.role.equals("student")){
				questionsGenerated=Question.getCustomQuestions("easy", CosmicQuestLoginScreen.studentUser.getClassCode());
				}else {
					questionsGenerated=Question.getCustomQuestions("easy", "none");

				}
		}
		if (difficultyChosen == 2 && levelChosen == 5) {
				if (CosmicQuestLoginScreen.role.equals("student")){
					questionsGenerated=Question.getCustomQuestions("medium", CosmicQuestLoginScreen.studentUser.getClassCode());
				}else {
					questionsGenerated=Question.getCustomQuestions("medium", "none");

				}
		}
		if (difficultyChosen == 3 && levelChosen == 5) {
				if (CosmicQuestLoginScreen.role.equals("student")){
				questionsGenerated=Question.getCustomQuestions("hard", CosmicQuestLoginScreen.studentUser.getClassCode());
				}else {
					questionsGenerated=Question.getCustomQuestions("hard", "none");

				}
		}
	}

	/**
	 * END GAME FUNCTION <br>
	 * <br>
	 * Ends the game by stopping timers, removing the panel from the frame, and
	 * displaying game statistics in a new panel.
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void endGame() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		// stop the timers to stop refreshing the screen and spawning asteroids
		timer.stop();
		asteroidSpawnTimer.stop();
		this.removeAll();
		frame.remove(this);
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		// check if they failed or passed the level and call the GameStatsPanel
		if (lives > 0) {
			frame.add(new GameStatsPanel<>(lives, fuelCount, correctAnswers, questionsGenerated.size(), frame, true,difficultyChosen, levelChosen));
		} else {
			frame.add(new GameStatsPanel<>(lives, fuelCount, correctAnswers, questionsGenerated.size(), frame, false,difficultyChosen, levelChosen));
		}
		
	}
	
}

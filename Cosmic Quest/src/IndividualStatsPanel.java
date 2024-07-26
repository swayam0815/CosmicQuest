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
 * INDIVIDUAL STATS PANEL CLASS <br>
 * <br>
 * Represents a panel displaying individual statistics including username, fuel
 * count, best and worst performed level, and current level.
 * 
 * @author Dikran Kahiaian
 * @author Matthew McDonald (Javadoc)
 */
public class IndividualStatsPanel extends JPanel {
	/** panels frame */
	private JFrame frame;
	/** increase fuel field for developer */
	private JLabel upFuel;
	/** decrease fuel field for developer */
	private JLabel downFuel;
	/** increase level field for developer */
	private JLabel upLevel;
	/** decrease level field for developer */
	private JLabel downLevel;
	/** back button field */
	private JLabel backButton;
	/** student object to grab statistics from */
	private Student student;
	/** sound file */
	private File hover;
	/** sound clip */
	private Clip hoverSound;

	/**
	 * INDIVIDUAL STATS PANEL CONSTRUCTOR <br>
	 * <br>
	 * 
	 * @param frame The JFrame associated with this panel.
	 * @param Student object of the game
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public IndividualStatsPanel(JFrame frame, Student student)throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		//initialize variables
		this.frame = frame;
		this.setBounds(0,0,frame.getWidth(), frame.getHeight());
		this.student = student;
		upFuel = new JLabel();
		downFuel = new JLabel();
		upLevel = new JLabel();
		downLevel = new JLabel();
		backButton = new JLabel();

		// set the image icons
		upFuel.setIcon(new ImageIcon("src/images/up.png"));
		downFuel.setIcon(new ImageIcon("src/images/down.png"));
		upLevel.setIcon(new ImageIcon("src/images/up.png"));
		downLevel.setIcon(new ImageIcon("src/images/down.png"));
		backButton.setIcon(new ImageIcon("src/images/back.png"));
		setLayout(null);
		// set their location on the screen
		Dimension upSize = upFuel.getPreferredSize();
		upFuel.setBounds(1400, 650, upSize.width, upSize.height);
		downFuel.setBounds(1400, 850, upSize.width, upSize.height);
		upLevel.setBounds(1680, 650, upSize.width, upSize.height);
		downLevel.setBounds(1680, 850, upSize.width, upSize.height);
		backButton.setBounds(100, 850, upSize.width, upSize.height);
		// add hover sound effects
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);
		// check if user is developer and add the buttons accordingly
		if (CosmicQuestLoginScreen.role.equals("developer")) {
			add(downFuel);
			add(upFuel);
			add(upLevel);
			add(downLevel);
		}
		add(backButton);
		frame.add(this);
		addMouseListeners();
	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Overrides the paintComponent method to draw individual statistics on the
	 * panel
	 *
	 * @param g The Graphics object used for painting.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		// Draw background images and avatar-related images
		g2D.drawImage(new ImageIcon("src/images/GameBckgrnd.jpg").getImage(), 0, 0, null);
		if (CosmicQuestLoginScreen.role.equals("developer")) {
			g2D.drawImage(new ImageIcon("src/images/fuel.png").getImage(), 1360, 525, null);
			g2D.drawImage(new ImageIcon("src/images/level.png").getImage(), 1650, 525, null);
		}
		g2D.drawImage(new ImageIcon("src/images/studentstats.png").getImage(), 500, 0, null);
		g2D.drawImage(new ImageIcon("src/images/avatarframe.png").getImage(), 0, 400, null);
		g2D.drawImage(new ImageIcon("src/images/nameText.png").getImage(), 500, 200, null);
		g2D.drawImage(new ImageIcon("src/images/fuelText.png").getImage(), 500, 350, null);
		g2D.drawImage(new ImageIcon("src/images/levelsText.png").getImage(), 700, 450, null);
		g2D.drawImage(new ImageIcon("src/images/bestText.png").getImage(), 450, 550, null);
		g2D.drawImage(new ImageIcon("src/images/worstText.png").getImage(), 900, 550, null);
		g2D.drawImage(new ImageIcon("src/images/currentText.png").getImage(), 650, 700, null);

		// Set font and color for drawing text
		g2D.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		g2D.setColor(Color.RED);
		// Draw text indicating statistics
		// Check the role and retrieve user-specific statistics
		if (CosmicQuestLoginScreen.role.equals("student")) {
			g2D.drawString(CosmicQuestLoginScreen.studentUser.getUsername(), 675, 260);
			g2D.drawString(CosmicQuestLoginScreen.studentUser.getFuel(), 675, 410);
			g2D.drawString(bestLevel(), 660, 610);
			g2D.drawString(worstLevel(), 1125, 615);
			if(CosmicQuestLoginScreen.studentUser.getLevel().equals("none")) {
				g2D.drawString("1", 880, 740);
			}else {
				g2D.drawString(CosmicQuestLoginScreen.studentUser.getLevel(), 880, 740);
			}
			g2D.drawImage(new ImageIcon("src/avatars/"+CosmicQuestLoginScreen.studentUser.getAvatar()).getImage(), 25, 420, this);
		} else if (CosmicQuestLoginScreen.role.equals("developer")) {
			g2D.drawString(CosmicQuestLoginScreen.developerUser.getUsername(), 675, 260);
			g2D.drawString(CosmicQuestLoginScreen.developerUser.getFuel(), 675, 410);
			g2D.drawString(bestLevel(), 660, 610);
			g2D.drawString(worstLevel(), 1125, 615);
			g2D.drawString(CosmicQuestLoginScreen.developerUser.getLevel(), 880, 740);
			g2D.drawImage(new ImageIcon("src/avatars/"+CosmicQuestLoginScreen.developerUser.getAvatar()).getImage(), 25, 420, this);
		} else if (CosmicQuestLoginScreen.role.equals("teacher")) {
			g2D.drawString(student.getUsername(), 675, 260);
			g2D.drawString(student.getFuel(), 675, 410);
			g2D.drawString(bestLevel(), 660, 610);
			g2D.drawString(worstLevel(), 1125, 615);
			if(student.getLevel().equals("none")) {
				g2D.drawString("1", 880, 740);
			}else {
				g2D.drawString(student.getLevel(), 880, 740);
			}
			g2D.drawImage(new ImageIcon("src/avatars/"+student.getAvatar()).getImage(), 25, 420, this);
		}
	
	}

	/**
	 * BEST LEVEL FUNCTION <br>
	 * <br>
	 * Retrieves the best performed level by the user
	 *
	 * @return A String indicating the best performed level
	 */
	private String bestLevel() {
		// variables needed
		double max = 0;
		String numerator = "";
		String denominator = "";
		double addition = 0;
		double subtraction = 0;
		double division = 0;
		double multiplication = 0;
		boolean secOperand = false;
		// check the role
		if (CosmicQuestLoginScreen.role.equals("student")) {
			// check if they have played that level
			if (CosmicQuestLoginScreen.studentUser.getAdditionStat().equals("none")) {
				addition = 0;
			} else {
				// loop through each character of the String number
				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getAdditionStat().length(); i++) {
					// check if we're at the second operand yet
					if (CosmicQuestLoginScreen.studentUser.getAdditionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						// if we're not at the second operand, then concatenate the number into
						// numerator String, otherwise concatenate to denominator
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getAdditionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getAdditionStat().charAt(i);
						}
					}
				}
				// save the result in the variable by converting from String to integer
				addition = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}

			// remaining if statements do the same as above, except for other stats
			if (CosmicQuestLoginScreen.studentUser.getSubtractionStat().equals("none")) {
				subtraction = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getSubtractionStat().length(); i++) {
					if (CosmicQuestLoginScreen.studentUser.getSubtractionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getSubtractionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getSubtractionStat().charAt(i);
						}
					}
				}
				subtraction = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.studentUser.getMultiplicationStat().equals("none")) {
				multiplication = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getMultiplicationStat().length(); i++) {
					if (CosmicQuestLoginScreen.studentUser.getMultiplicationStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getMultiplicationStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getMultiplicationStat().charAt(i);
						}
					}
				}
				multiplication = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.studentUser.getDivisionStat().equals("none")) {
				division = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";

				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getDivisionStat().length(); i++) {
					if (CosmicQuestLoginScreen.studentUser.getDivisionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getDivisionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getDivisionStat().charAt(i);
						}
					}
				}
				division = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
		} else if (CosmicQuestLoginScreen.role.equals("developer")) {
			if (CosmicQuestLoginScreen.developerUser.getAdditionStat().equals("none")) {
				addition = 0;
			} else {
				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getAdditionStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getAdditionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getAdditionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getAdditionStat().charAt(i);
						}
					}
				}
				addition = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}

			if (CosmicQuestLoginScreen.developerUser.getSubtractionStat().equals("none")) {
				subtraction = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getSubtractionStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getSubtractionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getSubtractionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getSubtractionStat().charAt(i);
						}
					}
				}
				subtraction = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.developerUser.getMultiplicationStat().equals("none")) {
				multiplication = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getMultiplicationStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getMultiplicationStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getMultiplicationStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getMultiplicationStat().charAt(i);
						}
					}
				}
				multiplication = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.developerUser.getDivisionStat().equals("none")) {
				division = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";

				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getDivisionStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getDivisionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getDivisionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getDivisionStat().charAt(i);
						}
					}
				}
				division = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
		} else {
			if (student.getAdditionStat().equals("none")) {
				addition = 0;
			} else {
				for (int i = 0; i < student.getAdditionStat().length(); i++) {
					if (student.getAdditionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getAdditionStat().charAt(i);
						} else {
							denominator += student.getAdditionStat().charAt(i);
						}
					}
				}
				addition = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}

			if (student.getSubtractionStat().equals("none")) {
				subtraction = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < student.getSubtractionStat().length(); i++) {
					if (student.getSubtractionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getSubtractionStat().charAt(i);
						} else {
							denominator += student.getSubtractionStat().charAt(i);
						}
					}
				}
				subtraction = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (student.getMultiplicationStat().equals("none")) {
				multiplication = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < student.getMultiplicationStat().length(); i++) {
					if (student.getMultiplicationStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getMultiplicationStat().charAt(i);
						} else {
							denominator += student.getMultiplicationStat().charAt(i);
						}
					}
				}
				multiplication = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (student.getDivisionStat().equals("none")) {
				division = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";

				for (int i = 0; i < student.getDivisionStat().length(); i++) {
					if (student.getDivisionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getDivisionStat().charAt(i);
						} else {
							denominator += student.getDivisionStat().charAt(i);
						}
					}
				}
				division = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
		}
		// find the max among the 4 levels
		max = addition;
		if (subtraction > max)
			max = subtraction;
		if (multiplication > max)
			max = multiplication;
		if (division > max)
			max = division;
		// return depending on the max level
		if (max == addition) {
			return "Addition";
		} else if (max == subtraction) {
			return "Subtraction";
		} else if (max == multiplication) {
			return "Multiplication";
		} else if (max == division) {
			return "Division";
		}
		return "N/A";
	}

	/**
	 * WORST LEVEL FUNCTION <br>
	 * <br>
	 * Retrieves the worst performed level by the user
	 *
	 * @return A String indicating the worst performed level
	 */
	private String worstLevel() {
		double min = 0;
		String numerator = "";
		String denominator = "";
		double addition = 0;
		double subtraction = 0;
		double division = 0;
		double multiplication = 0;
		boolean secOperand = false;
		if (CosmicQuestLoginScreen.role.equals("student")) {
			if (CosmicQuestLoginScreen.studentUser.getAdditionStat().equals("none")) {
				addition = 0;
			} else {
				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getAdditionStat().length(); i++) {
					if (CosmicQuestLoginScreen.studentUser.getAdditionStat().charAt(i) == ('/')) {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getAdditionStat().charAt(i);
							System.out.println(numerator);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getAdditionStat().charAt(i);
							System.out.println(denominator);

						}
					}
				}
				addition = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}

			if (CosmicQuestLoginScreen.studentUser.getSubtractionStat().equals("none")) {
				subtraction = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getSubtractionStat().length(); i++) {
					if (CosmicQuestLoginScreen.studentUser.getSubtractionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getSubtractionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getSubtractionStat().charAt(i);
						}
					}
				}
				subtraction = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.studentUser.getMultiplicationStat().equals("none")) {
				multiplication = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getMultiplicationStat().length(); i++) {
					if (CosmicQuestLoginScreen.studentUser.getMultiplicationStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getMultiplicationStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getMultiplicationStat().charAt(i);
						}
					}
				}
				multiplication = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.studentUser.getDivisionStat().equals("none")) {
				division = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";

				for (int i = 0; i < CosmicQuestLoginScreen.studentUser.getDivisionStat().length(); i++) {
					if (CosmicQuestLoginScreen.studentUser.getDivisionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.studentUser.getDivisionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.studentUser.getDivisionStat().charAt(i);
						}
					}
				}
				division = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
		} else if (CosmicQuestLoginScreen.role.equals("developer")) {
			if (CosmicQuestLoginScreen.developerUser.getAdditionStat().equals("none")) {
				addition = 0;
			} else {
				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getAdditionStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getAdditionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getAdditionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getAdditionStat().charAt(i);
						}
					}
				}
				addition = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}

			if (CosmicQuestLoginScreen.developerUser.getSubtractionStat().equals("none")) {
				subtraction = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getSubtractionStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getSubtractionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getSubtractionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getSubtractionStat().charAt(i);
						}
					}
				}
				subtraction = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.developerUser.getMultiplicationStat().equals("none")) {
				multiplication = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getMultiplicationStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getMultiplicationStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getMultiplicationStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getMultiplicationStat().charAt(i);
						}
					}
				}
				multiplication = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (CosmicQuestLoginScreen.developerUser.getDivisionStat().equals("none")) {
				division = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";

				for (int i = 0; i < CosmicQuestLoginScreen.developerUser.getDivisionStat().length(); i++) {
					if (CosmicQuestLoginScreen.developerUser.getDivisionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += CosmicQuestLoginScreen.developerUser.getDivisionStat().charAt(i);
						} else {
							denominator += CosmicQuestLoginScreen.developerUser.getDivisionStat().charAt(i);
						}
					}
				}
				division = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
		} else {
			if (student.getAdditionStat().equals("none")) {
				addition = 0;
			} else {
				for (int i = 0; i < student.getAdditionStat().length(); i++) {
					if (student.getAdditionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getAdditionStat().charAt(i);
						} else {
							denominator += student.getAdditionStat().charAt(i);
						}
					}
				}
				addition = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}

			if (student.getSubtractionStat().equals("none")) {
				subtraction = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < student.getSubtractionStat().length(); i++) {
					if (student.getSubtractionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getSubtractionStat().charAt(i);
						} else {
							denominator += student.getSubtractionStat().charAt(i);
						}
					}
				}
				subtraction = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (student.getMultiplicationStat().equals("none")) {
				multiplication = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";
				for (int i = 0; i < student.getMultiplicationStat().length(); i++) {
					if (student.getMultiplicationStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getMultiplicationStat().charAt(i);
						} else {
							denominator += student.getMultiplicationStat().charAt(i);
						}
					}
				}
				multiplication = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
			if (student.getDivisionStat().equals("none")) {
				division = 0;
			} else {
				secOperand = false;
				numerator = "";
				denominator = "";

				for (int i = 0; i < student.getDivisionStat().length(); i++) {
					if (student.getDivisionStat().charAt(i) == '/') {
						secOperand = true;
					} else {
						if (!secOperand) {
							numerator += student.getDivisionStat().charAt(i);
						} else {
							denominator += student.getDivisionStat().charAt(i);
						}
					}
				}
				division = Double.parseDouble(numerator) / Double.parseDouble(denominator);
			}
		}
		System.out.println("addition:"+addition);

		System.out.println("subtraction:"+subtraction);
		System.out.println("multiplication:"+multiplication);
		System.out.println("division:"+division);

		min = addition;
		if (subtraction < min)
			min = subtraction;
		if (multiplication < min)
			min = multiplication;
		if (division < min)
			min = division;

		if (min == addition) {
			return "Addition";
		} else if (min == subtraction) {
			return "Subtraction";
		} else if (min == multiplication) {
			return "Multiplication";
		} else if (min == division) {
			return "Division";
		}
		return "N/A";
	}
	/**
	 * ADDMOUSELISTENERS FUNCTION <br>
	 * <br>
	 *performs actions depending on user clicks
	 * 
	 */
	private void addMouseListeners() {
		// mouse event for all buttons
				upFuel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// check if user's fuel is already at max
						if (CosmicQuestLoginScreen.developerUser.getFuel().equals("none")) {
							CosmicQuestLoginScreen.developerUser.setFuel("1000");
							frame.revalidate();
							frame.repaint();
									
						}
						else if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel()) > 20000) {
							return;
						} else {
							// add fuel otherwise
							CosmicQuestLoginScreen.developerUser.setFuel(
									String.valueOf(Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel()) + 1000));
							if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<5000){
								CosmicQuestLoginScreen.developerUser.setLevel("1");
							}else if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<10000) {
								CosmicQuestLoginScreen.developerUser.setLevel("2");

							}else if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<15000) {
								CosmicQuestLoginScreen.developerUser.setLevel("3");

							}else if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<20000) {
								CosmicQuestLoginScreen.developerUser.setLevel("4");

							}else {
								CosmicQuestLoginScreen.developerUser.setLevel("5");

							}
							frame.revalidate();
							frame.repaint();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// hover effect on button
						upFuel.setIcon(new ImageIcon("src/images/upH.png"));
						hoverSound.setFramePosition(0); // Rewind to the beginning
						if (Settings.music) hoverSound.start();;

					}

					@Override
					public void mouseExited(MouseEvent e) {
						upFuel.setIcon(new ImageIcon("src/images/up.png"));

					}

				});
				downFuel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (CosmicQuestLoginScreen.developerUser.getFuel().equals("none")||(Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel()) < 1000)) {
							return;
						} else {
							CosmicQuestLoginScreen.developerUser.setFuel(String.valueOf(Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel()) - 1000));
							if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<5000){
								CosmicQuestLoginScreen.developerUser.setLevel("1");
							}else if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<10000) {
								CosmicQuestLoginScreen.developerUser.setLevel("2");

							}else if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<15000) {
								CosmicQuestLoginScreen.developerUser.setLevel("3");

							}else if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getFuel())<20000) {
								CosmicQuestLoginScreen.developerUser.setLevel("4");

							}else {
								CosmicQuestLoginScreen.developerUser.setLevel("5");

							}
							frame.revalidate();
							frame.repaint();
						}
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						downFuel.setIcon(new ImageIcon("src/images/downH.png"));
						hoverSound.setFramePosition(0); // Rewind to the beginning
						if (Settings.music) hoverSound.start();;

					}

					@Override
					public void mouseExited(MouseEvent e) {
						downFuel.setIcon(new ImageIcon("src/images/down.png"));

					}

				});
				upLevel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (CosmicQuestLoginScreen.developerUser.getLevel().equals("none")) {
							CosmicQuestLoginScreen.developerUser.setLevel("1");
							frame.revalidate();
							frame.repaint();
						}
						if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getLevel()) == 5) {
							return;
						} else {
							CosmicQuestLoginScreen.developerUser.setLevel(String.valueOf(Integer.parseInt(CosmicQuestLoginScreen.developerUser.getLevel()) + 1));
							if (CosmicQuestLoginScreen.developerUser.getLevel().equals("1")) {
								CosmicQuestLoginScreen.developerUser.setFuel("0");
							}
							else if (CosmicQuestLoginScreen.developerUser.getLevel().equals("2")) {
								CosmicQuestLoginScreen.developerUser.setFuel("5000");
							}else if(CosmicQuestLoginScreen.developerUser.getLevel().equals("3")) {
								CosmicQuestLoginScreen.developerUser.setFuel("10000");

							}else if (CosmicQuestLoginScreen.developerUser.getLevel().equals("4")) {
								CosmicQuestLoginScreen.developerUser.setFuel("15000");

							}else if(CosmicQuestLoginScreen.developerUser.getLevel().equals("5")) {
								CosmicQuestLoginScreen.developerUser.setFuel("20000");
							}
							frame.revalidate();
							frame.repaint();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						upLevel.setIcon(new ImageIcon("src/images/upH.png"));
						hoverSound.setFramePosition(0); // Rewind to the beginning
						if (Settings.music) hoverSound.start();;

					}

					@Override
					public void mouseExited(MouseEvent e) {
						upLevel.setIcon(new ImageIcon("src/images/up.png"));
						
					}

				});
				downLevel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (Integer.parseInt(CosmicQuestLoginScreen.developerUser.getLevel()) == 1) {
							return;
						} else {
							CosmicQuestLoginScreen.developerUser.setLevel(
									String.valueOf(Integer.parseInt(CosmicQuestLoginScreen.developerUser.getLevel()) - 1));
							if (CosmicQuestLoginScreen.developerUser.getLevel().equals("1")) {
								CosmicQuestLoginScreen.developerUser.setFuel("0");
							}
							else if (CosmicQuestLoginScreen.developerUser.getLevel().equals("2")) {
								CosmicQuestLoginScreen.developerUser.setFuel("5000");
							}else if(CosmicQuestLoginScreen.developerUser.getLevel().equals("3")) {
								CosmicQuestLoginScreen.developerUser.setFuel("10000");

							}else if (CosmicQuestLoginScreen.developerUser.getLevel().equals("4")) {
								CosmicQuestLoginScreen.developerUser.setFuel("15000");

							}else if(CosmicQuestLoginScreen.developerUser.getLevel().equals("5")) {
								CosmicQuestLoginScreen.developerUser.setFuel("20000");
							}
							frame.revalidate();
							frame.repaint();
						
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						downLevel.setIcon(new ImageIcon("src/images/downH.png"));
						hoverSound.setFramePosition(0); // Rewind to the beginning
						if (Settings.music) hoverSound.start();;

					}

					@Override
					public void mouseExited(MouseEvent e) {
						downLevel.setIcon(new ImageIcon("src/images/down.png"));

					}

				});
				backButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						frame.getContentPane().removeAll();
						frame.getContentPane().revalidate();
						frame.getContentPane().repaint();
						try {
							frame.add(new LoggedInMenuScreen(frame));
						} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// hover effect on button
						backButton.setIcon(new ImageIcon("src/images/backH.png"));
						hoverSound.setFramePosition(0); // Rewind to the beginning
						if (Settings.music) hoverSound.start();;

					}

					@Override
					public void mouseExited(MouseEvent e) {
						backButton.setIcon(new ImageIcon("src/images/back.png"));

					}

				});
		
	}
}
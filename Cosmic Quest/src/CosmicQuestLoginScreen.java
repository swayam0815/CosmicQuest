import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * COSMIC QUESTION LOGIN SCREEN CLASS <br>
 * <br>
 * This class represents the login screen view
 * 
 * @author Matthew McDonald
 * @author Jenish Paudel
 */
public class CosmicQuestLoginScreen extends JPanel {
	/** Screen's background image */
	private Image background;
	/** Screen's frame */
	private JFrame frame;
	/** Screen's current screen */
	private JPanel current;
	/** Screen's constraints */
	private GridBagConstraints gbc;
	/** Screen's panel number */
	private int panelID;
	/** Screen's menu screen object */
	private MainMenuScreen menuScreen;

	/** Screen's username field */
	private JTextField usernameLoginField;
	/** Screen's password field */
	private JTextField passwordLoginField;
	/** Screen's login back button */
	private JLabel backFromLogin;
	/** Screen's login next button */
	private JLabel nextFromLogin;

	/** Screen's create account username field */
	private JTextField usernameCreateField;
	/** Screen's create account password field */
	private JTextField passwordCreateField;
	/** Screen's create account confirm password field */
	private JTextField confirmPasswordField;
	/** Screen's create account next button */
	private JLabel nextFromCreate;
	/** Screen's create account back button */
	private JLabel backFromCreate;
	/** Screen's create account role button */
	private JLabel chooseRole;
	/** Screen's create account student button */
	private ImageIcon studentIcon;
	/** Screen's create account teacher button */
	private ImageIcon teacherIcon;

	/** Username input */
	private String username = "";
	/** Password input */
	private String password = "";
	/** Confirm password input */
	private String confirmPassword = "";
	/** Class code input */
	private String classCode = "";

	/** User role */
	public static String role = "";

	/** Teacher user object */
	public static Teacher teacherUser = new Teacher();
	/** Student user object */
	public static Student studentUser = new Student();
	/** Developer user object */
	public static Developer developerUser = new Developer();

	/**
	 * COSMIC QUEST LOGIN SCREEN CONSTRUCTOR
	 * 
	 * @param frame      the JFrame which this JPanel needs to be added to
	 * @param panelID    Represents whether the user wants to login or start fresh.
	 *                   (0 means to start new and 1 means to load previous).
	 * @param menuScreen screen that needs the user needs to be taken to if they
	 *                   choose to back
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public CosmicQuestLoginScreen(JFrame frame, int panelID, MainMenuScreen menuScreen) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// initializing variables --
		if (panelID != 0 && panelID != 1)
			throw new IllegalArgumentException("PanelID can only be either 0 or 1");

		this.frame = frame;
		this.panelID = panelID;
		this.menuScreen = menuScreen;

		this.gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);

		// initializing variables for login Screen (NOTE: These fields are needed even if panelID is 0.
		this.usernameLoginField = new JTextField("Username");
		this.passwordLoginField = new JTextField("Password");
		this.backFromLogin = new JLabel();
		this.nextFromLogin = new JLabel();
		// CHANGE BUTTON IMAGE HERE TO "BACK"
		backFromLogin.setIcon(new ImageIcon("src/images/back.png"));
		// CHANGE BUTTON IMAGE HERE TO "LOG IN"
		nextFromLogin.setIcon(new ImageIcon("src/images/check.png"));

		if (panelID == 0) {
			this.usernameCreateField = new JTextField("Username");
			this.passwordCreateField = new JTextField("Password");
			this.confirmPasswordField = new JTextField("Confirm Password");
			this.nextFromCreate = new JLabel();
			this.backFromCreate = new JLabel();
			this.chooseRole = new JLabel();
			// CHANGE BUTTON IMAGE HERE TO "BACK"
			backFromCreate.setIcon(new ImageIcon("src/images/back.png"));
			// CHANGE BUTTON IMAGE HERE TO "LOG IN"
			nextFromCreate.setIcon(new ImageIcon("src/images/check.png"));

			// CHANGE BUTTON IMAGE HERE to STUDENT ICON
			studentIcon = new ImageIcon("src/images/question.png");
			// CHANGE BUTTON IMAGE HERE TO TEACHER ICON
			teacherIcon = new ImageIcon("src/images/rocket.png");

		}

		setupPanel();
		addMouseListeners();

		this.revalidate();
		this.repaint();

		// adding all panels to the frame and setting up the first one
		frame.add(this);
		frame.validate();
		frame.repaint();
	}

	/**
	 * LOGIN PANEL SETUP FUNCTION <br>
	 * <br>
	 * Sets up login panel UI
	 */
	private void loginPanelSetUp() {
		// setting up the Login Panel
		this.current = new JPanel(new GridBagLayout());
		this.current.setOpaque(false);

		// adding components and their settings

		usernameLoginField.setPreferredSize(new Dimension(frame.getWidth() / 4, frame.getHeight() / 12));
		usernameLoginField.setFont(new Font("Arial", Font.PLAIN, frame.getHeight() / 13));
		usernameLoginField.setHorizontalAlignment(JTextField.CENTER);

		passwordLoginField.setPreferredSize(new Dimension(frame.getWidth() / 4, frame.getHeight() / 12));
		passwordLoginField.setFont(new Font("Arial", Font.PLAIN, frame.getHeight() / 13));
		passwordLoginField.setHorizontalAlignment(JTextField.CENTER);
		
		// GirdBagConstraints setup ------------------------
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);

		// adding components to login panel to their appropriate spots
		gbc.gridx = 1;
		gbc.gridy = 0;
		current.add(usernameLoginField, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		current.add(passwordLoginField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		current.add(backFromLogin, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		current.add(nextFromLogin, gbc);

		current.revalidate();
		current.repaint();

		this.add(current, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();

	}

	/**
	 * CREATE PANEL SETUP FUNCTION <br>
	 * <br>
	 * Sets up create account UI
	 */
	private void createPanelSetUp() {
		// Setting up Create Account Panel
		this.current = new JPanel(new GridBagLayout());
		this.current.setOpaque(false);

		// adding components and their settings

		usernameCreateField.setPreferredSize(new Dimension(frame.getWidth() / 4, frame.getHeight() / 15));
		usernameCreateField.setFont(new Font("Arial", Font.BOLD, frame.getHeight() / 16));
		usernameCreateField.setHorizontalAlignment(JTextField.CENTER);

		passwordCreateField.setPreferredSize(new Dimension(frame.getWidth() / 4, frame.getHeight() / 15));
		passwordCreateField.setFont(new Font("Arial", Font.BOLD, frame.getHeight() / 16));
		passwordCreateField.setHorizontalAlignment(JTextField.CENTER);

		confirmPasswordField.setPreferredSize(new Dimension(frame.getWidth() / 4, frame.getHeight() / 15));
		confirmPasswordField.setFont(new Font("Arial", Font.BOLD, frame.getHeight() / 16));
		confirmPasswordField.setHorizontalAlignment(JTextField.CENTER);

		// CHANGE BUTTON IMAGE HERE TO "SIGN UP"
		nextFromCreate.setIcon(new ImageIcon("src/images/check.png"));
		// CHANGE BUTTON IMAGE HERE "BACK"
		backFromCreate.setIcon(new ImageIcon("src/images/back.png"));

		// default is student.
		chooseRole.setIcon(new ImageIcon("src/images/teacherbtn.png"));
		// default role value.
		role = "student";

		// adding components to login panel to their appropriate spots
		gbc.gridx = 1;
		gbc.gridy = 0;
		current.add(usernameCreateField, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		current.add(passwordCreateField, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		current.add(confirmPasswordField, gbc);
		gbc.gridx = 2;
		gbc.gridy = 3;
		current.add(nextFromCreate, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		current.add(backFromCreate, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		current.add(chooseRole, gbc);

		current.revalidate();
		current.repaint();

		this.add(current, BorderLayout.CENTER);

		this.revalidate();

	}

	/**
	 * SETUP PANEL FUNCTION <br>
	 * <br>
	 * Sets up basic panel UI
	 */
	private void setupPanel() {
		// JPanel settings -----------------
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setLayout(new BorderLayout(5, this.getHeight() / 7));

		ImageIcon icon = new ImageIcon("src/images/GameBckgrnd.jpg"); // CHANGE BACKGROUND HERE -----------------------
		this.background = icon.getImage();

		JLabel title = new JLabel();
		title.setIcon(new ImageIcon("src/images/title.png")); // CHANGE TITLE IMAGE HERE --
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		this.add(title, BorderLayout.NORTH);

		if (this.panelID == 0)
			createPanelSetUp();
		else
			loginPanelSetUp();

	}

	/**
	 * MOUSE LISTENER FUNCTION <br>
	 * <br>
	 * Adds mouse listeners to screen
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	private void addMouseListeners() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/sounds/hover.wav"));
		Clip hover = AudioSystem.getClip();
		hover.open(audioIn);
		
		
		//Clear JTextFields -----------------------------------------------
        usernameLoginField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (usernameLoginField.getText().equals("Username")) {
            		usernameLoginField.setText("");
            	}
            }
        });
        
        passwordLoginField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (passwordLoginField.getText().equals("Password")) {
            		passwordLoginField.setText("");
            	}
            }
        });
        

		// LoginPanel next button
		nextFromLogin.addMouseListener(new MouseAdapter() { // lOGIN BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				// set variables to the text field info
				username = usernameLoginField.getText();
				password = passwordLoginField.getText();

				if (signInUser(username, password) == true) {
					LoggedInMenuScreen nextScreen;
					try {
						nextScreen = new LoggedInMenuScreen(frame);
						CosmicQuestLoginScreen.this.removePanel();
						nextScreen.addPanel();
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					usernameLoginField.setText("Username");
					passwordLoginField.setText("Password");
					errorPopUp("Error: Password or username you entered is incorrect.");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nextFromLogin.setIcon(new ImageIcon("src/images/checkH.png"));
				hover.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					hover.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextFromLogin.setIcon(new ImageIcon("src/images/check.png"));

			}
		});

		// LoginPanel back button --------------------------------------------
		backFromLogin.addMouseListener(new MouseAdapter() { // LEARN BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				usernameLoginField.setText("Username");
				passwordLoginField.setText("Password");
				removePanel();
				menuScreen.addPanel();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				backFromLogin.setIcon(new ImageIcon("src/images/backH.png"));
				hover.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					hover.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				backFromLogin.setIcon(new ImageIcon("src/images/back.png"));

			}

		});

		if (panelID == 0) {

			//Clear JTextFields ----------------------------------
	        usernameCreateField.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	if (usernameCreateField.getText().equals("Username")) {
	            		usernameCreateField.setText("");
	            	}
	            }
	        });
	        
	        passwordCreateField.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	if (passwordCreateField.getText().equals("Password")) {
	            		passwordCreateField.setText("");
	            	}
	            }
	        });
	        confirmPasswordField.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	if (confirmPasswordField.getText().equals("Confirm Password")) {
	            		confirmPasswordField.setText("");
	            	}
	            }
	        });
	        
			// Choose role clicked -------------
			chooseRole.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (role.equals("student")) {
						chooseRole.setIcon(new ImageIcon("src/images/teacherbtnH.png"));
						role = "teacher";
					} else {
						chooseRole.setIcon(new ImageIcon("src/images/teacherbtn.png"));
						role = "student";
					}
				}
			});

			nextFromCreate.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// set variables to the text field info
					username = usernameCreateField.getText();
					password = passwordCreateField.getText();
					confirmPassword = confirmPasswordField.getText();
					
					//Username and password length conditions ----------------------------------------------
					if(username.length() < 3) {
						errorPopUp("Error: Username too short!");
						return;
					}
					else if (username.length() > 12) {
						errorPopUp("Error: Username too long!");
						return;
					}
					if(password.length() < 6) {
						errorPopUp("Error: Password too short!");
						return;
					}
					
					// check if the username entered already exists
					if (userExists(username) == false) {

						// if the credentials are valid, check the selected account role ----------
						if (password.equals(confirmPassword)) {

							// create teacher account
							if (role.equals("teacher")) {

								// generate a new random class code for the new teacher account
								classCode = generateClassCode();
								createTeacherAccount(role, username, password, classCode);

								// return user to login Screen
								panelID = 1;
								CosmicQuestLoginScreen.this.remove(current);
								setupPanel();
								frame.repaint();

								// create student account
							} else if (role.equals("student")) {

								createStudentAccount(role, username, password);

								// return user to login screen
								panelID = 1;
								CosmicQuestLoginScreen.this.remove(current);
								setupPanel();
								frame.repaint();
							}
						} else {
							errorPopUp("Error: Password and confirm password must match.");
						}
					} else {
						usernameCreateField.setText("Username");
						passwordCreateField.setText("Password");
						confirmPasswordField.setText("Confirm Password");
						errorPopUp("Error: Username already exists.");
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					nextFromCreate.setIcon(new ImageIcon("src/images/checkH.png"));
					hover.setFramePosition(0); // Rewind to the beginning
					if (Settings.music)
						hover.start();

				}

				@Override
				public void mouseExited(MouseEvent e) {
					nextFromCreate.setIcon(new ImageIcon("src/images/check.png"));

				}
			});

			backFromCreate.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					removePanel();
					menuScreen.addPanel();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					backFromCreate.setIcon(new ImageIcon("src/images/backH.png"));
					hover.setFramePosition(0); // Rewind to the beginning
					if (Settings.music)
						hover.start();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					backFromCreate.setIcon(new ImageIcon("src/images/back.png"));

				}
			});
		}
	}

	/**
	 * ADD PANEL FUNCTION <br>
	 * <br>
	 * Adds this panel to JFrame and the revalidates and repaints this and the frame
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
	 * Removes this panel from the frame
	 */
	public void removePanel() {
		frame.remove(this);
		frame.repaint();
	}

	/**
	 * ERROR POP UP FUNCTION <br>
	 * <br>
	 * Pops an alert with the given message
	 * 
	 * @param error: the error message
	 */
	private void errorPopUp(String error) {
		JOptionPane.showMessageDialog(null, error, "alert", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * GENERATE CLASS CODE FUNCTION <br>
	 * <br>
	 * Generate a new random class code
	 * 
	 * @return a new generated class code for a new teacher account
	 */
	public String generateClassCode() {

		String generatedCode = "";

		// declare random operator
		Random random = new Random();

		// generate random 8 digit code
		while (generatedCode.length() < 8) {
			int randomNumber = random.nextInt(10);
			generatedCode += String.valueOf(randomNumber);
		}

		// check if the class code already exists
		if (classCodeExists(generatedCode) == true) {
			return generateClassCode();
		} else {
			return generatedCode;
		}
	}

	/**
	 * CLASS CODE EXISTS <br>
	 * <br>
	 * Check if a class code already exists
	 * 
	 * @param code: the class code to look for
	 * @return true if the class code exists, and false otherwise
	 */
	public boolean classCodeExists(String code) {

		String generatedCode = code;

		// declare name of accounts file to access
		String accountFile = "src/accounts.txt";
		File file = new File(accountFile);

		try {
			// create reader to read the file
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String currentLine = fileReader.readLine();

			// loop through file to check if the class code exists
			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (generatedCode.equals(lineParts[3])) {
					fileReader.close();
					return true;
				} else {
					currentLine = fileReader.readLine();
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Generating class code error.");
		}
		return false;
	}

	/**
	 * SIGN IN USER FUNCTION <br>
	 * <br>
	 * Signs user into game
	 * 
	 * @param username: the users username
	 * @param password: the users password
	 */
	public boolean signInUser(String username, String password) {

		// declare name of accounts file to access
		String accountFile = "src/accounts.txt";
		File file = new File(accountFile);

		if (username.equals("cs2212") && password.equals("ducks2212")) {
			role = "developer";
			developerUser.setUsername(username);
			developerUser.setPassword(password);
			developerUser.setClassCode("none");
			developerUser.setLevel("1");
			developerUser.setFuel("none");
			developerUser.setAdditionStat("none");
			developerUser.setSubtractionStat("none");
			developerUser.setMultiplicationStat("none");
			developerUser.setDivisionStat("none");
			developerUser.setAvatar("1.png");
			return true;
		}

		try {
			// create reader to read the file
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String currentLine = fileReader.readLine();

			// loop through file to check user credentials
			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (username.equals(lineParts[1])) {
					if (password.equals(lineParts[2])) {
						fileReader.close();
						// sign teacher in
						if (lineParts[0].equals("teacher")) {
							role = "teacher";
							teacherUser.setUsername(lineParts[1]);
							teacherUser.setPassword(lineParts[2]);
							teacherUser.setClassCode(lineParts[3]);
							// sign student in
						} else if (lineParts[0].equals("student")) {
							role = "student";
							studentUser.setUsername(lineParts[1]);
							studentUser.setPassword(lineParts[2]);
							studentUser.setClassCode(lineParts[3]);
							studentUser.setLevel(lineParts[4]);
							studentUser.setFuel(lineParts[5]);
							studentUser.setAdditionStat(lineParts[6]);
							studentUser.setSubtractionStat(lineParts[7]);
							studentUser.setMultiplicationStat(lineParts[8]);
							studentUser.setDivisionStat(lineParts[9]);
							studentUser.setAvatar(lineParts[10]);
						}
						return true;
					} else {
						fileReader.close();
						return false;
					}
				} else {
					currentLine = fileReader.readLine();
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("User sign in error.");
		}
		return false;
	}

	/**
	 * USER EXISTS FUNCTION <br>
	 * <br>
	 * Check if a username already exists
	 * 
	 * @param username: the username to check for
	 * @return true if the username already exists, and false otherwise
	 */
	public boolean userExists(String username) {

		// declare name of accounts file to access
		String accountFile = "src/accounts.txt";
		File file = new File(accountFile);

		try {
			// create reader to read the file
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String currentLine = fileReader.readLine();

			// loop through file to check if the username already exists
			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (username.equals(lineParts[1])) {
					fileReader.close();
					return true;
				} else {
					currentLine = fileReader.readLine();
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Checking username exists error.");
		}
		return false;
	}

	/**
	 * CREATE TEACHER ACCOUNT FUNCTION <br>
	 * <br>
	 * Adds new teacher account to accounts file
	 * 
	 * @param role:      the teacher's account type (in this case: teacher)
	 * @param username:  the teacher's account username
	 * @param password:  the teacher's account password
	 * @param classCode: the class code of the class the teacher may be enrolled in
	 */
	public void createTeacherAccount(String role, String username, String password, String classCode) {

		// declare name of accounts file to access
		String accountFile = "src/accounts.txt";
		File file = new File(accountFile);

		try {
			// declare buffered writer to write to file
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));

			// write the proper format of a teacher to the account file
			fileWriter.write(role + "," + username + "," + password + "," + classCode);
			fileWriter.newLine();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Teacher sign up error.");
		}

	}

	/**
	 * CREATE STUDENT ACCOUNT FUNCTION <br>
	 * <br>
	 * Adds new student account to accounts file
	 * 
	 * @param role:     the student's account type (in this case: student)
	 * @param username: the student's account username
	 * @param password: the student's account password
	 */
	public void createStudentAccount(String role, String username, String password) {

		// declare name of accounts file to access
		String accountFile = "src/accounts.txt";
		File file = new File(accountFile);

		try {
			// declare buffered writer to write to file
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));

			// write the proper format of a student to the account file
			fileWriter.write(role + "," + username + "," + password + "," + "none" + "," + "none" + "," + "none" + ","
					+ "none" + "," + "none" + "," + "none" + "," + "none" + "," + "1.png");

			fileWriter.newLine();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Student sign up error.");
		}
	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Draws background image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}

}

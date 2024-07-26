import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * ADD STUDENT SCREEN CLASS
 * <br><br>
 * Creates an instance of the student add and remove panel
 * @author Matthew McDonald
 * @author Dikran Kahiaian
 * @author Jenish Paudel
 */
public class AddStudentScreen extends JPanel {
	/** the panels frame */
	private JFrame frame;
	/** the panels background */
	private Image background;
	/** the list of students for student list */
	private ArrayList<Student> studentsList;
	/** the student username to add or remove */
	private String studentUsername;
	/** teacher's class code */
	private String teacherClassCode = CosmicQuestLoginScreen.teacherUser.getClassCode();
	/**Previous screen to go to when pressed back */
	private LoggedInMenuScreen prevScreen;
	/** add button to add a student*/
	private JLabel addButton;
	/** remove button to remove a student*/
	private JLabel removeButton;
	/** back button to return to previous panel*/
	private JLabel backButton;
	/** message line to inform the user about their actions*/
	private JLabel messageLine;
	/** title for the screen*/
	private JLabel title;
	/** center panel for student list */
	private JPanel list;
	/** bottom panel to show the buttons */
	private JPanel bottomPanel;
	/** scroll list panel to hold the names of the students */
	private JScrollPane scrollList;
	/** sound file */
	private File hover;
	/** sound clip */
	private Clip hoverSound;
	/**
	 * ADD STUDENT SCREEN CONSTRUCTOR
	 * <br><br>
	 * @param frame is the screens frame
	 * @param prevScreen is the previous screen to be used for back button
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public AddStudentScreen(JFrame frame, LoggedInMenuScreen prevScreen) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// initialize all variables with images and locations on screen
		this.frame = frame;
		this.prevScreen = prevScreen;

		ImageIcon icon = new ImageIcon("src/images/GameBckgrnd.jpg"); //CHANGE BACKGROUND HERE  -----------------------
		background = icon.getImage();
		// add hover sound effects
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);


		title = new JLabel();
		title.setIcon(new ImageIcon("src/images/title.png")); //CHANGE TITLE HERE ------------------
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		addButton = new JLabel();
		addButton.setIcon(new ImageIcon("src/images/addstudent.png"));
		removeButton = new JLabel();
		removeButton.setIcon(new ImageIcon("src/images/removestudent.png"));
		backButton = new JLabel();
		backButton.setIcon(new ImageIcon("src/images/back.png"));

		setupPanel(); //sets up the panel ------------
		addMouseListeners();
	}

	/**
	 * SET UP CLASS LIST FUNCTION
	 * <br><br>
	 * Sets up the class list
	 * 
	 * @throws IOException
	 */
	private void setupClassList() throws IOException {
		int studentCount = 0;
		File accountFile = new File("src/accounts.txt");

		// create reader to read the file
		BufferedReader fileReader = new BufferedReader(new FileReader(accountFile));
		String currentLine = fileReader.readLine();
		// loop through file to check user credentials
		while (currentLine != null) {
			String[] lineParts = currentLine.split(",");
			if (lineParts[0].equals("student") && CosmicQuestLoginScreen.role.equals("teacher")
					&& lineParts[3].equals(CosmicQuestLoginScreen.teacherUser.getClassCode())) {
				studentsList.add(studentCount, new Student());
				studentsList.get(studentCount).setUsername(lineParts[1]);
				studentsList.get(studentCount).setPassword(lineParts[2]);
				studentsList.get(studentCount).setClassCode(lineParts[3]);
				studentsList.get(studentCount).setLevel(lineParts[4]);
				studentsList.get(studentCount).setFuel(lineParts[5]);
				studentsList.get(studentCount).setAdditionStat(lineParts[6]);
				studentsList.get(studentCount).setSubtractionStat(lineParts[7]);
				studentsList.get(studentCount).setMultiplicationStat(lineParts[8]);
				studentsList.get(studentCount).setDivisionStat(lineParts[9]);
				studentsList.get(studentCount).setAvatar(lineParts[10]);
				studentCount++;
			}
			currentLine = fileReader.readLine();
		}

		// Define a custom comparator that compares students based on their fuel attribute
		Comparator<Student> fuelComparator = new Comparator<Student>() {
			@Override
			public int compare(Student s1, Student s2) {
				System.out.println(s1.getUsername());
				return s1.getUsername().compareTo(s2.getUsername());
			}
		};

		// Sort the studentsList ArrayList using the custom comparator
		Collections.sort(studentsList, fuelComparator);
	}

	/**
	 * DISPLAY CLASS LIST FUNCTION
	 * <br><br>
	 * Displays the class list of students
	 * 
	 * @param list is the given list to display
	 */
	private void displayClassList(JPanel list) {

		for (Student student: studentsList) {

			//Set up JLabel
			JLabel studentInfo = new JLabel(student.getUsername());
			studentInfo.setFont(new Font("Bookman Old Style", Font.BOLD, 80)); // Adjust font size if needed
			studentInfo.setForeground(Color.red);
			studentInfo.setHorizontalAlignment(JLabel.CENTER);
			studentInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
			studentInfo.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

			//Set onClick for the student's name
			studentInfo.addMouseListener(new MouseAdapter() {
				@Override


				public void mouseClicked(MouseEvent e) {
					// Clear the frame
					frame.getContentPane().removeAll(); 
					frame.getContentPane().revalidate();
					frame.getContentPane().repaint();
					try {
						frame.add(new IndividualStatsPanel(frame, student));
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						System.out.println("Display class list error code 1");
					} /** Add the new panel */
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					studentInfo.setForeground(Color.blue);
					studentInfo.setForeground(Color.blue);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					studentInfo.setForeground(Color.red);
					studentInfo.setForeground(Color.red);
				}
			});
			list.add(studentInfo);
		}
		list.revalidate();
		list.repaint();
		revalidate();
		repaint();
	}

	/**
	 * SET UP PANEL FUNCTION
	 * <br><br>
	 * Sets up panel
	 */
	private void setupPanel() {
		this.setBounds(0,0, frame.getWidth(),frame.getHeight());
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(frame.getHeight()/8, frame.getWidth()/6, frame.getHeight()/8, frame.getWidth()/6));
		
		//declaring array --
		studentsList = new ArrayList<Student>();
		
		//creating panels ---- 
				list = new JPanel();
				list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));


				try {
					setupClassList();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				displayClassList(list);

				scrollList = new JScrollPane(list);
				

				// CREATING BOTTOM PANEL
				bottomPanel = new JPanel(new GridBagLayout());
				bottomPanel.setOpaque(false);
				GridBagConstraints gbc = new GridBagConstraints();



				messageLine = new JLabel("  "); //REMOVE THIS MESSAGE TO MAKE IT BLANK -------
				messageLine.setFont(new Font("Serif", Font.BOLD, 30));
				messageLine.setForeground(Color.red);

				// setting the locations of buttons
				gbc.gridx = 0;
				gbc.gridy = 1;  
				gbc.weightx = 0.3;
				gbc.weighty = 0.3;
				gbc.insets = new Insets(0,0,0,0);
				bottomPanel.add(backButton, gbc);
				gbc.gridx = 1;
				gbc.gridy = 1;  

				bottomPanel.add(removeButton, gbc);
				gbc.gridx = 2;
				gbc.gridy = 1;
				bottomPanel.add(addButton, gbc);

				gbc.gridx = 1;
				gbc.gridy = 4;
				bottomPanel.add(messageLine, gbc);
				bottomPanel.revalidate();
				bottomPanel.repaint();

				// add everything to the main JPanel
				this.add(title, BorderLayout.NORTH);
				this.add(scrollList, BorderLayout.CENTER);
				this.add(bottomPanel, BorderLayout.SOUTH);
				
				this.revalidate();
				this.repaint();
				frame.add(this);
				frame.revalidate();
				frame.repaint();

	}

	/**
	 * PAINT COMPONENT FUNCTION
	 * <br><br>
	 * Displays background image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0,0, frame.getWidth(),frame.getHeight() , null);
	}

	/**
	 * ADD STUDENT TO CLASS FUNCTION
	 * <br><br>
	 * Adds student to the teachers class
	 * @param username is the student's username to add to the class
	 * @return 1 if the user is already in the class, 2 if the student was added successfully, or 3 if the student does not exist
	 */
	public int addStudentToClass(String username) {

		String accountFile = "src/accounts.txt";
		// create temporary student object
		Student tempStudent = new Student();

		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(accountFile));
			String currentLine = fileReader.readLine();

			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (username.equals(lineParts[1])) {
					// check if student is already in class
					if (lineParts[3].equals(teacherClassCode)) {
						fileReader.close();
						return 1;
					} else {
	
						
						// set up student object holders properties
						tempStudent.setUsername(lineParts[1]);
						tempStudent.setPassword(lineParts[2]);
						tempStudent.setClassCode(lineParts[3]);
						tempStudent.setLevel(lineParts[4]);
						tempStudent.setFuel(lineParts[5]);
						tempStudent.setAdditionStat(lineParts[6]);
						tempStudent.setSubtractionStat(lineParts[7]);
						tempStudent.setMultiplicationStat(lineParts[8]);
						tempStudent.setDivisionStat(lineParts[9]);
						tempStudent.setAvatar(lineParts[10]);

						// change the users class code and update the student info
						tempStudent.setClassCode(teacherClassCode);
						tempStudent.saveInfo();
						fileReader.close();
						
						this.list.removeAll();
						this.removeAll();
						this.setupPanel();
						
						return 2;
					}
				}
				currentLine = fileReader.readLine();
			}
			fileReader.close();

		} catch (IOException e) {
			System.out.println("Add student error code 1");
		}
		return 3;
	}

	/**
	 * REMOVE STUDENT FROM CLASS FUNCTION
	 * <br><br>
	 * Removes student from the teachers class
	 * 
	 * @param username is the student's username to remove from the class
	 * @return 1 if the student is not apart of the class, 2 if the student was removed successfully, or 3 if the student does not exist
	 */
	public int removeStudentFromClass(String username) {

		String accountFile = "src/accounts.txt";
		// create temporary student object
		Student tempStudent = new Student();

		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(accountFile));
			String currentLine = fileReader.readLine();

			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (username.equals(lineParts[1])) {
					// check if student is in class or not
					if (lineParts[3].equals("none")) {
						fileReader.close();
						return 1;
					} else {
						
						// set up student object holders properties
						tempStudent.setUsername(lineParts[1]);
						tempStudent.setPassword(lineParts[2]);
						tempStudent.setClassCode(lineParts[3]);
						tempStudent.setLevel(lineParts[4]);
						tempStudent.setFuel(lineParts[5]);
						tempStudent.setAdditionStat(lineParts[6]);
						tempStudent.setSubtractionStat(lineParts[7]);
						tempStudent.setMultiplicationStat(lineParts[8]);
						tempStudent.setDivisionStat(lineParts[9]);
						tempStudent.setAvatar(lineParts[10]);

						// change the users class code and update the student info
						tempStudent.setClassCode("none");
						tempStudent.saveInfo();
						fileReader.close();
						
						this.list.removeAll();
						this.removeAll();
						this.setupPanel();
						
						return 2;
					}
				}
				currentLine = fileReader.readLine();
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("Remove student error code 1");
		}
		return 3;
	}
	/**
	 * SET UP MOUSE LISTENERS
	 * <br><br>
	 * Sets up mouse listeners for all the buttons on the screen to perform actions
	 */
	private void addMouseListeners() {
		// MouseListeners
		addButton.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent e) {
				String input = JOptionPane.showInputDialog ("Please type the student's username to add: ");
				if (input == null) {
					// User cancelled. Do nothing
				} else {

					int studentAddResult = addStudentToClass(input);
					if (studentAddResult == 1) {
						messageLine.setText(input + " is already in the class.");
					} else if (studentAddResult == 2) {
						messageLine.setText(input + " has been added.");
					} else if (studentAddResult == 3) {
						messageLine.setText(input + " does not exist.");
					}
				}
			}
		});

		removeButton.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent e) {
				String input = JOptionPane.showInputDialog ("Please type the student's username to remove: ");
				if (input == null) {
					// User cancelled. Do nothing
				} else {

					int studentRemoveResult = removeStudentFromClass(input);
					if (studentRemoveResult == 1) {
						messageLine.setText(input + " is not in the class.");
					} else if (studentRemoveResult == 2) {
						messageLine.setText(input + " has been removed.");
					} else if (studentRemoveResult == 3) {
						messageLine.setText(input + " does not exist.");
					}
				}
				messageLine.setForeground(Color.red);
			}
		});

		backButton.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent e) {
				prevScreen.addPanel();
				frame.remove(AddStudentScreen.this);
				frame.revalidate();
				frame.repaint();
			}public void mouseEntered(MouseEvent e) {
				backButton.setIcon(new ImageIcon("src/images/backH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music) hoverSound.start();;

			}
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(new ImageIcon("src/images/back.png"));
			}
		});

	}


}

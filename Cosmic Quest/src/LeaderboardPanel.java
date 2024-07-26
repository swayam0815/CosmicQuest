import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * LEADERBOARD PANEL CLASS <br>
 * <br>
 * Creates a panel to hold the leaderboard students
 * 
 * @author Dikran Kahiaian
 * @author Matthew McDonald (Javadoc)
 */
public class LeaderboardPanel extends JPanel {
	/** sound file */
	private File hover;
	/** sound clip */
	private Clip hoverSound;
	/** panels frame */
	private JFrame frame;
	/** next leaderboard page button */
	private JLabel nextPageButton;
	/** previous leaderboard page button */
	private JLabel previousPageButton;
	/** back button field */
	private JLabel backButton;
	/** leaderboard page list */
	private JPanel list;
	/** list of students on leaderboard */
	private ArrayList<Student> studentsList;
	/** current leaderboard page number */
	private int currentPage = 0;
	/** number of students for each leaderboard page */
	private int studentsPerPage = 8;

	/**
	 * LEADER BOARD PANEL CONSTRUCTOR <br>
	 * <br>
	 * 
	 * @param frame is the main panel frame
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public LeaderboardPanel(JFrame frame) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		// initializing variables
		this.frame = frame;
		this.setBounds(0,0,frame.getWidth(), frame.getHeight());
		studentsList = new ArrayList<Student>();
		backButton = new JLabel();
		nextPageButton = new JLabel();
		previousPageButton = new JLabel();
		backButton.setIcon(new ImageIcon("src/images/back.png"));
		nextPageButton.setIcon(new ImageIcon("src/images/next.png"));
		previousPageButton.setIcon(new ImageIcon("src/images/previous.png"));
		setLayout(null);
		//set their positions on the screen
		Dimension backButtonSize = backButton.getPreferredSize();
		backButton.setBounds(50, 850, backButtonSize.width, backButtonSize.height);
		nextPageButton.setBounds(1600, 850, nextPageButton.getPreferredSize().width, nextPageButton.getPreferredSize().height);
		previousPageButton.setBounds(1450, 850, nextPageButton.getPreferredSize().width, nextPageButton.getPreferredSize().height);
		add(backButton);
		add(nextPageButton);
		add(previousPageButton);
		// add hover sound effects
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);
		//set up the list of students
		list = new JPanel();
		list.setLayout(null);
		list.setOpaque(false);
		list.setBounds(500, 175, 800, 800);
		setupClassList();
		displayClassList();
		addMouseListeners();
		frame.add(this);
	}

	/**
	 * SET UP CLASS LIST FUNCTION <br>
	 * <br>
	 * Sets up class list of students for leaderboard
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
			if (lineParts[0].equals("student")&& (lineParts[5].equals("none"))) {
				studentsList.add(studentCount, new Student());
				studentsList.get(studentCount).setUsername(lineParts[1]);
				studentsList.get(studentCount).setPassword(lineParts[2]);
				studentsList.get(studentCount).setClassCode(lineParts[3]);
				studentsList.get(studentCount).setLevel(lineParts[4]);
				studentsList.get(studentCount).setFuel("0");
				studentsList.get(studentCount).setAdditionStat(lineParts[6]);
				studentsList.get(studentCount).setSubtractionStat(lineParts[7]);
				studentsList.get(studentCount).setMultiplicationStat(lineParts[8]);
				studentsList.get(studentCount).setDivisionStat(lineParts[9]);
				studentsList.get(studentCount).setAvatar(lineParts[10]);
				studentCount++;
			} else if (lineParts[0].equals("student") ) {
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
			System.out.println();
			currentLine = fileReader.readLine();
		}

		// Define a custom comparator that compares students based on their fuel
		// attribute
		Comparator<Student> fuelComparator = new Comparator<Student>() {
			@Override
			public int compare(Student s1, Student s2) {
				return Integer.compare(Integer.parseInt(s2.getFuel()), Integer.parseInt(s1.getFuel()));
				}
			
	
		};

		// Sort the studentsList ArrayList using the custom comparator
		Collections.sort(studentsList, fuelComparator);
	}

	/**
	 * DISPLAY CLASS LIST FUNCTION <br>
	 * <br>
	 * Displays the class list of all students or select students depending on if
	 * the user signed in user is a teacher
	 */
	private void displayClassList() {
		list.removeAll();
		remove(list);
		int labelHeight = 60; // Increase label height
		int verticalGap = 30; // Increase vertical gap
		int initialY = 25; // Initial y position
		int startIndex = currentPage * studentsPerPage; // Calculate the starting index for the current page

		for (int i = startIndex; i < Math.min(startIndex + studentsPerPage, studentsList.size()); i++) {
			Student student = studentsList.get(i);
			JLabel studentNameLabel = new JLabel((i + 1) + "." + student.getUsername());
			JLabel studentFuelLabel = new JLabel(student.getFuel());
			studentNameLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 40)); // Adjust font size if needed
			studentNameLabel.setForeground(Color.red);
			studentNameLabel.setBounds(50, initialY + (labelHeight + verticalGap) * (i - startIndex), 300, labelHeight); // Adjust position and size as needed
			studentFuelLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 40)); // Adjust font size if needed
			studentFuelLabel.setForeground(Color.red);
			studentFuelLabel.setBounds(600, initialY + (labelHeight + verticalGap) * (i - startIndex), 300,labelHeight); // Adjust position and size as needed
				list.add(studentNameLabel);
				list.add(studentFuelLabel);
		}
		
		this.add(list);
		// Repaint the panel to reflect changes
		revalidate();
		repaint();
	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Displays the background image
	 * @param Graphics g, graphics used for the image to be painted
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon("src/images/GameBckgrnd.jpg").getImage(), 0, 0, null);
		g.drawImage(new ImageIcon("src/images/leaderboardTitle.png").getImage(), 450, 25, null);
		g.drawImage(new ImageIcon("src/images/lbframe.png").getImage(), 420, 180, null);

	}
	/**
	 * ADDMOUSELISTENERS FUNCTION <br>
	 * <br>
	 * performs actions depending on user clicks
	 */
	private void addMouseListeners() {
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Clear the frame
				frame.getContentPane().removeAll();
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				frame.add(MainMenuScreen.mainMenu);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				backButton.setIcon(new ImageIcon("src/images/backH.png"));
				// Rewind to the beginning
				hoverSound.setFramePosition(0);
				if (Settings.music) hoverSound.start();;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(new ImageIcon("src/images/back.png"));

			}

		});
		nextPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check if there are more students
				if ((currentPage + 1) * studentsPerPage < studentsList.size()) {
					// Move to the next page
					currentPage++;
					// Redraw the panel with the next set of students
					displayClassList();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nextPageButton.setIcon(new ImageIcon("src/images/nextH.png"));
				// Rewind to the beginning
				hoverSound.setFramePosition(0);
				if (Settings.music) hoverSound.start();;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextPageButton.setIcon(new ImageIcon("src/images/next.png"));
			}
		});
		previousPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check if not on the first page
				if (currentPage > 0) {
					// Move to the previous page
					currentPage--;
					// Redraw the panel with the previous set of students
					displayClassList();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				previousPageButton.setIcon(new ImageIcon("src/images/previousH.png"));
				// Rewind to the beginning
				hoverSound.setFramePosition(0);
				if (Settings.music) hoverSound.start();;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				previousPageButton.setIcon(new ImageIcon("src/images/previous.png"));

			}
		});
	}
}
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * QUESTION SET PANEL CLASS <br>
 * <br>
 * Creates an instance of the question set panel
 * 
 * @author Matthew McDonald
 * @author Dikran Kahiaian
 */
public class QuestionSetPanel extends JPanel {
	/** sound file */
	private File hover;
	/** sound clip */
	private Clip hoverSound;
	/** panel to hold questions */
	private JPanel questionsPanel;
	/** button for adding question */
	private JLabel addQuestionButton;
	/** button for removing question */
	private JLabel removeQuestionButton;
	/** button to navigate to next page of question set page */
	private JLabel nextPageButton;
	/** button to navigate to previous page of question set page */
	private JLabel previousPageButton;
	/** button to navigate out of question panel */
	private JLabel backButton;
	/** frame for question panel */
	private JLabel questionFrame;
	/** Text to inform the user of the question format*/
	private JLabel textInfo;
	/** error pop up text */
	private JLabel errorPopup;
	/** field to enter question to add or remove */
	private JTextField questionField;
	/** main frame for page */
	private JFrame frame;
	/** array list for questions in question set */
	private ArrayList<String> questionSet;
	/** current question set page displayed */
	private int currentPage = 0;
	/** number of questions on each page */
	private int questionsPerPage = 8;
	/** question entered by user */
	private String question;
	/** class code of teacher that is signed in */
	private String teacherClassCode = CosmicQuestLoginScreen.teacherUser.getClassCode();
	/** instance of the previous screen */
	private LoggedInMenuScreen prevScreen;

	/**
	 * QUESTION SET PANEL <br>
	 * <br>
	 * 
	 * @param frame for the question panel
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public QuestionSetPanel(JFrame frame, LoggedInMenuScreen prevScreen) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		this.frame = frame;
		this.prevScreen=prevScreen;
		setLayout(null);
		//initialize JLabels
		addQuestionButton = new JLabel();
		removeQuestionButton = new JLabel();
		backButton = new JLabel();
		questionFrame = new JLabel();
		nextPageButton = new JLabel();
		previousPageButton = new JLabel();
		textInfo=new JLabel();
		questionField = new JTextField();
		questionsPanel = new JPanel();
		errorPopup=new JLabel();
		errorPopup.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		errorPopup.setForeground(Color.white);
		questionsPanel.setLayout(null);
		questionsPanel.setOpaque(false);
		questionsPanel.setBounds(350, 200, 600, 800);
		//set the images for JLabels
		addQuestionButton.setIcon(new ImageIcon("src/images/add.png"));
		removeQuestionButton.setIcon(new ImageIcon("src/images/remove.png"));
		backButton.setIcon(new ImageIcon("src/images/back.png"));
		questionFrame.setIcon(new ImageIcon("src/images/questionFrame.png"));
		nextPageButton.setIcon(new ImageIcon("src/images/next.png"));
		previousPageButton.setIcon(new ImageIcon("src/images/previous.png"));
		Dimension buttonSize = addQuestionButton.getPreferredSize();
		questionField.setFont(new Font("Bookman Old Style", Font.BOLD, 40));
		textInfo.setFont((new Font("Bookman Old Style", Font.BOLD, 30)));
		textInfo.setForeground(Color.white);
		questionField.setBounds(1350, 300, 500, 80);
		//set the locations of JLabels
		addQuestionButton.setBounds(1350, 450, buttonSize.width, buttonSize.height);
		removeQuestionButton.setBounds(1680, 450, buttonSize.width, buttonSize.height);
		backButton.setBounds(50, 850, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
		questionFrame.setBounds(300, 180, questionFrame.getPreferredSize().width,questionFrame.getPreferredSize().height);
		nextPageButton.setBounds(850, 520, nextPageButton.getPreferredSize().width,nextPageButton.getPreferredSize().height);
		previousPageButton.setBounds(250, 520, nextPageButton.getPreferredSize().width,nextPageButton.getPreferredSize().height);
		errorPopup.setBounds(1350, 100, 500, 300);
		textInfo.setBounds(1180, 600,1000, 500);
		textInfo.setText("<html>The questions must be in <br/>Operand Operator Operand=answer <br/> format with no spaces or IT WILL NOT WORK.<br/> Ex:123+456=579</html>");
		add(addQuestionButton);
		add(removeQuestionButton);
		add(backButton);
		add(questionField);
		add(nextPageButton);
		add(previousPageButton);
		add(questionFrame);
		add(textInfo);
		add(questionsPanel);
		frame.add(this);
		frame.revalidate();
		frame.repaint();
		// add hover sound effects
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);
		if (teacherClassExists()) {
			getQuestionSet();
			displayQuestionsList();
		}
		addMouseListeners();
	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Displays background images
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		/** draw the images needed to be displayed */
		g2D.drawImage(new ImageIcon("src/images/GameBckgrnd.jpg").getImage(), 0, 0, this);
		g2D.drawImage(new ImageIcon("src/images/questionSetTitle.png").getImage(), 450, 0, this);

	}
	/**
	 * DISPLAY QUESTION LIST <br>
	 * <br>
	 * Displays the question list
	 */
	private void displayQuestionsList() {
		questionsPanel.removeAll();
		remove(questionsPanel);
		questionsPanel.revalidate();
		questionsPanel.repaint();
		// Increase label height
		int labelHeight = 60;
		// Increase vertical gap
		int verticalGap = 30;
		// Initial y position
		int initialY = 25;
		// Calculate the starting index for the current page
		int startIndex = currentPage * questionsPerPage;

		for (int i = startIndex; i < Math.min(startIndex + questionsPerPage, questionSet.size()); i++) {
			JLabel questionText = new JLabel(questionSet.get(i));
			// Adjust font size if needed
			questionText.setFont(new Font("Bookman Old Style", Font.BOLD, 40));
			questionText.setForeground(Color.red);
			questionText.setBounds(50, initialY + (labelHeight + verticalGap) * (i - startIndex), 300, labelHeight);
			// Adjust position and size as needed
			questionsPanel.add(questionText);
		}

		this.add(questionsPanel);
		// Repaint the panel to reflect changes
		revalidate();
		repaint();
	}

	/**
	 * ADD QUESTION FUNCTION <br>
	 * <br>
	 * Adds question to teacher's question set
	 */
	public void addQuestion() {

		File questionSetFile = new File("src/questionSets.txt");
		ArrayList<String> questionSet = new ArrayList<String>();

		// check if the teacher has an existing question set
		if (teacherClassExists() == false) {

			try {
				// write the teachers class code to the file first
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(questionSetFile, true));

				fileWriter.write(teacherClassCode);
				fileWriter.newLine();
				fileWriter.close();

			} catch (IOException e) {
				System.out.println("Adding question error code 2");
			}
		}

		// get the teacher's question set
		questionSet = getQuestionSet();

		// add the new question to the question set
		questionSet.add(question);
		String updatedQuestionSet = teacherClassCode;

		// loop through question set and add commas between each element for file
		// formatting
		for (int i = 0; i < questionSet.size(); i++) {
			updatedQuestionSet += "," + questionSet.get(i);
		}

		// update the question set
		updateQuestionSet(updatedQuestionSet);

	}

	/**
	 * TEACHER CLASS EXISTS FUNCTION <br>
	 * <br>
	 * Checks if a teacher has a question set
	 * 
	 * @return true if the teacher has a question class and false otherwise
	 */
	private boolean teacherClassExists() {

		File questionSetFile = new File("src/questionSets.txt");

		try {

			BufferedReader fileReader = new BufferedReader(new FileReader(questionSetFile));
			String currentLine = fileReader.readLine();

			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (lineParts[0].equals(teacherClassCode)) {
					fileReader.close();
					return true;
				} else {
					currentLine = fileReader.readLine();
				}
			}

			fileReader.close();

		} catch (IOException e) {
			System.out.println("Adding question error code 1");
		}

		return false;
	}

	/**
	 * GET QUESTION SET FUNCTION <br>
	 * <br>
	 * Gets the teacher's question set
	 * 
	 * @return array list of the teacher's question set
	 */
	private ArrayList<String> getQuestionSet() {
		//set the path of the file
		String questionSetFile = "src/questionSets.txt";
		questionSet = new ArrayList<String>();

		try {
			// initialize the buffered reader
			BufferedReader fileReader = new BufferedReader(new FileReader(questionSetFile));
			String currentLine = fileReader.readLine();
			// loop through each line and check the class code
			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (lineParts[0].equals(teacherClassCode)) {
					//add the questions into an arraylist if question sets were found
					for (int i = 1; i < lineParts.length; i++) {
						questionSet.add(lineParts[i]);
					}
					break;
				} else {
					currentLine = fileReader.readLine();
				}
			}

			fileReader.close();
		} catch (Exception e) {
			System.out.println("Adding question error code 3");
		}

		return questionSet;

	}

	/**
	 * UPDATE QUESTION SET FUNCTION <br>
	 * <br>
	 * Updates the teacher's question set
	 * 
	 * @param updatedQuestionSet is the question set to add to the question set file
	 */
	private void updateQuestionSet(String updatedQuestionSet) {

		String questionSetFile = "src/questionSets.txt";

		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader fileReader = new BufferedReader(new FileReader(questionSetFile));
			String currentLine = fileReader.readLine();

			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				// create string builder of the question set file
				if (lineParts[0].equals(teacherClassCode)) {
					// add the updated line to the string builder
					sb.append(updatedQuestionSet);
				} else {
					sb.append(currentLine);
				}
				sb.append(System.lineSeparator());
				currentLine = fileReader.readLine();
			}

			fileReader.close();
			// write the string builder to the file
			FileWriter writer = new FileWriter(questionSetFile);
			writer.write(sb.toString());
			writer.close();

		} catch (IOException e) {
			System.out.println("Student save error code 4");
		}
	}

	/**
	 * REMOVE QUESTION FUNCTION <br>
	 * <br>
	 * Removes question set from the teacher's question set
	 */
	public void removeQuestion() {

		File questionSetFile = new File("src/questionSets.txt");
		ArrayList<String> questionSet = new ArrayList<String>();

		if (teacherClassExists() == false) {
			errorPopup.setText("add a question first before removing.");
			add(errorPopup);
			frame.revalidate();
			frame.repaint();
			//display the text for 3 seconds
			Timer timer = new Timer(3000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					remove(errorPopup);
					errorPopup.setVisible(false);
					errorPopup.revalidate();
					errorPopup.repaint();
					frame.revalidate();
					frame.repaint();

				}
			});			return;
		}

		questionSet = getQuestionSet();

		// get the index of where the question to remove is located in the array list
		int indexToRemove = getRemoveIndex(questionSet);

		if (indexToRemove == -1) {
			// ERROR POP UP THAT QUESTION IS NOT IN THE QUESTION SET
			errorPopup.setText("Question is not in the set.");
			add(errorPopup);
			frame.revalidate();
			frame.repaint();
			//display the text for 3 seconds
			Timer timer = new Timer(3000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					remove(errorPopup);
					errorPopup.setVisible(false);
					errorPopup.revalidate();
					errorPopup.repaint();
					frame.revalidate();
					frame.repaint();

				}
			});
			return;
		}

		// remove the question from the array list
		questionSet.remove(indexToRemove);
		String updatedQuestionSet = teacherClassCode;

		// loop through question set and add commas between each element for file
		// formating
		for (int i = 0; i < questionSet.size(); i++) {
			updatedQuestionSet += "," + questionSet.get(i);
		}

		// update the teacher's question set
		updateQuestionSet(updatedQuestionSet);

	}

	/**
	 * GET REMOVE INDEX FUNCTION <br>
	 * <br>
	 * Gets the index of where the element to remove is located
	 * 
	 * @param questionSet is the question set to search through
	 * @return the index of the element in the array list
	 */
	private int getRemoveIndex(ArrayList<String> questionSet) {
		for (int i = 0; i < questionSet.size(); i++) {
			if (questionSet.get(i).equals(question)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * ADD MOUSE LISTENERS FUNCTION <br>
	 * <br>
	 * performs actions according to user clicks
	 * 
	 */
	private void addMouseListeners() {
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Clear the frame
				frame.getContentPane().removeAll();
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				frame.add(prevScreen);
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
		addQuestionButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//check if the input is valid and call methods
				if (questionField.getText() != "") {
					question = questionField.getText();
						addQuestion();
					getQuestionSet();
					displayQuestionsList();
					
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				addQuestionButton.setIcon(new ImageIcon("src/images/addH.png"));
				// Rewind to the beginning
				hoverSound.setFramePosition(0);
				if (Settings.music) hoverSound.start();;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				addQuestionButton.setIcon(new ImageIcon("src/images/add.png"));

			}

		});
		removeQuestionButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (questionField.getText() != "") {
					question = questionField.getText();
					removeQuestion();
					getQuestionSet();
					displayQuestionsList();
					
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				removeQuestionButton.setIcon(new ImageIcon("src/images/removeH.png"));
				// Rewind to the beginning
				hoverSound.setFramePosition(0);
				if (Settings.music) hoverSound.start();;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				removeQuestionButton.setIcon(new ImageIcon("src/images/remove.png"));

			}

		});
		nextPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check if there are more students
				if ((currentPage + 1) * questionsPerPage < questionSet.size()) {
					// Move to the next page
					currentPage++;
					// Redraw the panel with the next set of students
					displayQuestionsList();
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
					displayQuestionsList();
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
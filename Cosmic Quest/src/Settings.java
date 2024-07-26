import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * SETTINGS CLASS <br>
 * <br>
 * Creates a panel to hold the settings of the game <br>
 * <br>
 * 
 * @author Thomas Ma
 * @author Dikran Kahiaian
 */
public class Settings extends JPanel {
	/** music boolean, on by default */
	public static boolean music = true;
	/** panel frame */
	private JFrame frame;
	/** back button */
	private JLabel backButton;
	/** sound toggle button */
	private JLabel soundToggle;
	/** avatar image holder */
	private JLabel avatarImage;
	/** next avatar button */
	private JLabel nextButton;
	/** previous avatar button */
	private JLabel previousButton;
	/** save avatar button */
	private JLabel saveButton;
	/** the title image */
	private JLabel title;
	/** text popup field */
	private JLabel text;
	/** list of avatars */
	private String[] avatars;
	/** index for list of avatars */
	private int avatarListIndex = 0;
	/** the hover-over-buton sound file */
	private File hover;
	/** the hover-over-buton sound clip */
	private Clip hoverSound;
	/** instance of previous screen */
	private LoggedInMenuScreen prevScreen;

	/**
	 * SETTINGS CONSTRUCTOR <br>
	 * <br>
	 * Creates the settings screen
	 * 
	 * @param frame      The program frame
	 * @param prevScreen The previous game screen
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public Settings(JFrame frame, LoggedInMenuScreen prevScreen)
			throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		avatars = new String[] { "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png",
				"10.png" };
		//initialize variables
		this.frame = frame;
		this.prevScreen=prevScreen;
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		backButton = new JLabel();
		soundToggle = new JLabel();
		text = new JLabel();
		avatarImage = new JLabel();
		nextButton = new JLabel();
		previousButton = new JLabel();
		saveButton = new JLabel();
		title = new JLabel();
		hover = new File("src/sounds/hover.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(hover);
		hoverSound = AudioSystem.getClip();
		hoverSound.open(audioIn);
		text.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		text.setForeground(Color.white);
		text.setText("Saved Avatar!");
		backButton.setIcon(new ImageIcon("src/images/back.png"));
		nextButton.setIcon(new ImageIcon("src/images/next.png"));
		previousButton.setIcon(new ImageIcon("src/images/previous.png"));
		avatarImage.setIcon(new ImageIcon("src/avatars/" + avatars[avatarListIndex]));
		saveButton.setIcon(new ImageIcon("src/images/saveavatar.png"));
		soundToggle.setIcon(new ImageIcon("src/images/onButton.png"));
		//set the locations of components
		setLayout(null);
		Dimension backButtonSize = backButton.getPreferredSize();
		Dimension avatarImageSize = avatarImage.getPreferredSize();
		Dimension saveButtonSize = saveButton.getPreferredSize();
		Dimension toggleButtonsSize = soundToggle.getPreferredSize();
		backButton.setBounds(50, 800, backButtonSize.width, backButtonSize.height);
		nextButton.setBounds(950, 650, nextButton.getPreferredSize().width, nextButton.getPreferredSize().height);
		previousButton.setBounds(750, 650, nextButton.getPreferredSize().width, nextButton.getPreferredSize().height);
		avatarImage.setBounds(765, 275, avatarImageSize.width, avatarImageSize.height);
		saveButton.setBounds(700, 800, saveButtonSize.width, saveButtonSize.height);
		soundToggle.setBounds(50, 350, toggleButtonsSize.width, toggleButtonsSize.height);
		text.setBounds(1500, 600, 300, 300);
		
		JLabel shortcutInfo = new JLabel("<html>Press 'Escape' key anytime to return back to main menu.</html>");
		shortcutInfo.setBounds(1300, 275, frame.getWidth()-1300, 80);
		shortcutInfo.setFont(new Font("Bookman Old Style", Font.BOLD, 35)); // Adjust font size if needed
		shortcutInfo.setForeground(Color.red);
		
		add(backButton);
		add(nextButton);
		add(previousButton);
		add(avatarImage);
		add(saveButton);
		add(shortcutInfo);
		frame.add(this);
		//check if sound is on and off
		if (music) {
			soundToggle.setIcon(new ImageIcon("src/images/onButton.png"));
			add(soundToggle);
		} else {
			soundToggle.setIcon(new ImageIcon("src/images/offButton.png"));
			add(soundToggle);
		}
		addMouseListeners();
	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Method that adds images to the panel
	 * 
	 * @param g The graphics used for the image to be painted
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setFont(new Font("Bookman Old Style", Font.BOLD, 80));
		g2D.setColor(Color.red);
		/** draw the images needed to be displayed */
		g2D.drawImage(new ImageIcon("src/images/GameBckgrnd.jpg").getImage(), 0, 0, this);
		g2D.drawImage(new ImageIcon("src/images/avatarframe.png").getImage(), 750, 250, this);
		g2D.drawImage(new ImageIcon("src/images/settingsTitle.png").getImage(), 600, 0, this);
		g2D.drawImage(new ImageIcon("src/images/soundTitle.png").getImage(), -50, 200, this);
	}
	/**
	 * ADD MOUSE LISTENER FUNCTION <br>
	 * <br>
	 * Method that performs actions depending on mouse clicks
	 * 
	 */
	private void addMouseListeners() {
		soundToggle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (music) {
					soundToggle.setIcon(new ImageIcon("src/images/offButton.png"));
					music = false;
					MainMenuScreen.music.stop();
				} else {
					soundToggle.setIcon(new ImageIcon("src/images/onButton.png"));
					music = true;
					MainMenuScreen.music.setFramePosition(0);
					MainMenuScreen.music.loop(Clip.LOOP_CONTINUOUSLY);

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.getContentPane().removeAll(); // Clear the frame
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				frame.add(prevScreen);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				backButton.setIcon(new ImageIcon("src/images/backH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(new ImageIcon("src/images/back.png"));

			}

		});
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (avatarListIndex < avatars.length - 1) {

					avatarListIndex++;
					avatarImage.setIcon(new ImageIcon("src/avatars/" + avatars[avatarListIndex]));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				nextButton.setIcon(new ImageIcon("src/images/nextH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextButton.setIcon(new ImageIcon("src/images/next.png"));

			}

		});
		previousButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (avatarListIndex > 0) {

					avatarListIndex--;
					avatarImage.setIcon(new ImageIcon("src/avatars/" + avatars[avatarListIndex]));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// hover effect on button
				previousButton.setIcon(new ImageIcon("src/images/previousH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				previousButton.setIcon(new ImageIcon("src/images/previous.png"));

			}

		});
		saveButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (CosmicQuestLoginScreen.role.equals("developer")) {
					CosmicQuestLoginScreen.developerUser.setAvatar(avatars[avatarListIndex]);
					CosmicQuestLoginScreen.developerUser.saveInfo();
				} else if (CosmicQuestLoginScreen.role.equals("student")) {
					CosmicQuestLoginScreen.studentUser.setAvatar(avatars[avatarListIndex]);
					CosmicQuestLoginScreen.studentUser.saveInfo();
				}

				add(text);
				text.setVisible(true);
				text.revalidate();
				text.repaint();
				//display the text for 3 seconds
				Timer timer = new Timer(3000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						remove(text);
						text.setVisible(false);
						text.revalidate();
						text.repaint();
						frame.revalidate();
						frame.repaint();

					}
				});
				timer.setRepeats(false); // Make the timer only fire once
				timer.start(); // Start the
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				saveButton.setIcon(new ImageIcon("src/images/saveavatarH.png"));
				hoverSound.setFramePosition(0); // Rewind to the beginning
				if (Settings.music)
					hoverSound.start();
				;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				saveButton.setIcon(new ImageIcon("src/images/saveavatar.png"));

			}

		});
	}
}


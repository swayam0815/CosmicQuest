import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * STATSTUTORIAL CLASS <br>
 * <br>
 * Creates a panel to hold stats tutorial <br>
 * <br>
 * 
 * @author Thomas Ma
 * @author Jenish Paudel
 */
public class StatsTutorial extends JPanel {
	// private stuff
	/** panels frame */
	private JFrame frame;
	/** the panel background */
	private Image background;

	/**
	 * STATSTUTORIAL CONSTRUCTOR <br>
	 * <br>
	 * Creates the stats tutorial screen
	 * 
	 * @param inputFrame The program frame
	 * @param prevPanel  The previous game screen
	 */
	public StatsTutorial(JFrame inputFrame, TutorialMain prevPanel) {

		// setting up the frame
		this.frame = inputFrame;
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setLayout(new BorderLayout());

		// setting up the background
		ImageIcon backgroundImage = new ImageIcon("src/images/statsTut.png");
		this.background = backgroundImage.getImage();

		// making back button
		JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		backButtonPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		backButtonPanel.setOpaque(false);

		JLabel backButApp = new JLabel();
		backButApp.setIcon(new ImageIcon("src/images/back.png"));

		backButtonPanel.add(backButApp);

		this.add(backButtonPanel);

		// ******************************************************
		// appBackButton
		backButApp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// When button is clicked, do something
				// -------------------------------------------------------
				removePanel();
				prevPanel.addPanel();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				backButApp.setIcon(new ImageIcon("src/images/backH.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				backButApp.setIcon(new ImageIcon("src/images/back.png"));

			}
		});

		this.revalidate();
		this.repaint();

	}

	/**
	 * PANEL ADDER FUNCTION<br>
	 * <br>
	 * Adds this panel to the JFrame and then revalidates and repaints both this
	 * panel and the frame
	 */
	public void addPanel() {

		frame.add(this);
		this.revalidate();
		this.repaint();
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * PANEL REMOVER FUNCTION<br>
	 * <br>
	 * Removes this panel from the JFrame and then repaints the frame
	 */
	public void removePanel() { // removes this panel from the frame ------
		frame.remove(this);
		frame.repaint();
	}

	/**
	 * PAINT COMPONENT FUNCTION <br>
	 * <br>
	 * Method that paints the background image
	 * 
	 * @param g The graphics used for the image to be painted
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

	}

}
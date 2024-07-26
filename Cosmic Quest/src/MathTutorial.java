import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * MATHTUTORIAL CLASS <br>
 * <br>
 * Creates a panel to hold math tutorial <br>
 * <br>
 * 
 * @author Thomas Ma
 * @author Jenish Paudel
 */
public class MathTutorial extends JPanel {
	// private stuff
	/** panels frame */
	private JFrame frame;

	/** math tutorial page 1 */
	private JPanel page1;
	/** math tutorial page 2 */
	private JPanel page2;
	/** math tutorial page 3 */
	private JPanel page3;
	/** math tutorial page 4 */
	private JPanel page4;
	/** math tutorial page tracker */
	private int currentPage;

	/**
	 * MATHTUTORIAL CONSTRUCTOR <br>
	 * <br>
	 * Creates the math tutorial screen
	 * 
	 * @param inputFrame The program frame
	 * @param prevPanel  The previous game screen
	 */
	public MathTutorial(JFrame inputFrame, TutorialMain prevPanel) {

		// setting up the frame
		this.frame = inputFrame;
		this.currentPage = 1;
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setLayout(null);

		// Main panel view
		// ----------------------------------------------------------------------------------------------
		JPanel mainPanel = new JPanel();
		CardLayout cL = new CardLayout();
		mainPanel.setLayout(cL);

		this.page1 = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon backgroundImage = new ImageIcon("src/images/mathpage1.png");
				g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			}
		};
		this.page1.setBounds(0, 0, frame.getWidth(), frame.getHeight());

		this.page2 = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon backgroundImage = new ImageIcon("src/images/mathpage2.png");
				g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			}
		};
		this.page3 = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon backgroundImage = new ImageIcon("src/images/mathpage3.png");
				g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			}
		};

		this.page4 = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon backgroundImage = new ImageIcon("src/images/mathpage4.png");
				g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			}
		};

		mainPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		mainPanel.add(page1, "1");
		mainPanel.add(page2, "2");
		mainPanel.add(page3, "3");
		mainPanel.add(page4, "4");
		cL.show(mainPanel, "1");

		// return button
		// ---------------------------------------------------------------------------------------------------
		// making back button
		JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		backButtonPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		backButtonPanel.setOpaque(false);

		JLabel backButApp = new JLabel();
		backButApp.setIcon(new ImageIcon("src/images/back.png"));

		backButtonPanel.add(backButApp);

		this.add(backButtonPanel);

		// ******************************************************

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

		// Button Panel -------------------------------------------------------
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setOpaque(false);
		int panelHeight = frame.getHeight() / 6;
		buttonPanel.setBounds(0, frame.getHeight() - panelHeight, this.getWidth(), panelHeight);

		GridBagConstraints gbc = new GridBagConstraints();
		Insets buttonInsets = new Insets(10, 10, 10, 10);

		JLabel prevButton = new JLabel();
		prevButton.setIcon(new ImageIcon("src/images/leftarrow.png"));

		prevButton.addMouseListener(new MouseAdapter() { // LEADERBOARD BUTTON CODE HERE -------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentPage == 1) {
				} else {
					currentPage--;
					cL.show(mainPanel, "" + currentPage);
					MathTutorial.this.setComponentZOrder(buttonPanel, 0);
					MathTutorial.this.revalidate();
					MathTutorial.this.repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				prevButton.setIcon(new ImageIcon("src/images/leftarrowH.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				prevButton.setIcon(new ImageIcon("src/images/leftarrow.png"));

			}

		});

		JLabel nextButton = new JLabel();
		nextButton.setIcon(new ImageIcon("src/images/rightarrow.png"));

		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentPage == 4) {
				} else {
					currentPage++;
					cL.show(mainPanel, "" + currentPage);
					MathTutorial.this.setComponentZOrder(buttonPanel, 0);
					MathTutorial.this.setComponentZOrder(backButtonPanel, 0);
					MathTutorial.this.revalidate();
					MathTutorial.this.repaint();

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nextButton.setIcon(new ImageIcon("src/images/rightarrowH.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextButton.setIcon(new ImageIcon("src/images/rightarrow.png"));

			}

		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = buttonInsets;
		gbc.anchor = GridBagConstraints.PAGE_END;
		buttonPanel.add(prevButton, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		buttonPanel.add(nextButton, gbc);

		buttonPanel.revalidate();
		buttonPanel.repaint();

		this.add(mainPanel);
		this.add(buttonPanel);
		this.add(backButtonPanel);
		this.setComponentZOrder(buttonPanel, 0);
		this.setComponentZOrder(backButtonPanel, 0);

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

}
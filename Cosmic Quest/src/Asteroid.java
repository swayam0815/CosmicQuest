/**
 * ASTEROID CLASS <br>
 * <br>
 * This class represents an asteroid object in the math game <br>
 * 
 * @author Dikran Kahiaian
 */
public class Asteroid {
	/** The asteroid's x coordinate */
	private int x;
	/** The asteroid's y coordinate */
	private int y;
	/** The asteroid's associated math question */
	private String question;
	/** The asteroid's associated math answer */
	private String answer;

	/**
	 * ASTEROID CONSTRUCTOR
	 * 
	 * @param x        is the x coordinate
	 * @param y        is the y coordinate
	 * @param question is the associated math question
	 */
	public Asteroid(int x, int y, String[] question) {
		this.x = x;
		this.y = y;
		this.question = question[0];
		this.answer=question[1];
	}

	/**
	 * GET X COORDINATE FUNCTION <br>
	 * <br>
	 * This function will get the asteroid's x coordinate
	 * 
	 * @return the asteroid's x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * GET Y COORDINATE FUNCTION <br>
	 * <br>
	 * This function will get the asteroid's y coordinate
	 * 
	 * @return the asteroid's y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
     * GET QUESTION FUNCTION
     * <br><br>
     * This function will get the asteroid's associated math question
     * 
     * @return the asteroid's math question
     */
    public String getQuestion() {
        return this.question;
    }
    /**
     * GET ANSWER FUNCTION<br>
     * <br>
     * This function will get the asteroid's associated math answer
     * 
     * @return the asteroid's math answer
     */
    public int getAnswer() {
        return Integer.parseInt(answer);
    }
	/**
	 * MOVE FUNCTION <br>
	 * <br>
	 * This function will increment the asteroids x coordinate
	 */
	public void move() {
		x += 5;
	}
}
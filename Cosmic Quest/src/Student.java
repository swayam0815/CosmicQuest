import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * STUDENT CLASS <br>
 * <br>
 * Creates an instance of a student user
 * 
 * @author Matthew McDonald
 */
public class Student extends Teacher {

	/** user's level */
	private String level;
	/** user's fuel */
	private String fuel;
	/** user's addition stat */
	private String additionStat;
	/** user's subtraction stat */
	private String subtractionStat;
	/** user's multiplication stat */
	private String multiplicationStat;
	/** user's division stat */
	private String divisionStat;
	/** user's avatar */
	private String avatar;

	/**
	 * STUDENT CONSTRUCTOR <br>
	 * <br>
	 * Creates student user object
	 */
	public Student() {
		super();
		level = null;
		fuel = null;
		additionStat = null;
		subtractionStat = null;
		multiplicationStat = null;
		divisionStat = null;
		avatar = null;

	}
	
	/**
	 * GETTER FUNCTION
	 * 
	 * @return the student's role
	 */
	public String getRole() {
		return "student";
	}

	/**
	 * GETTER FUNCTION
	 * 
	 * @return user's level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * GETTER FUNCTION
	 * 
	 * @return user's fuel
	 */
	public String getFuel() {
		return fuel;
	}

	/**
	 * GETTER FUNCTION
	 * 
	 * @return user's addition statistics
	 */
	public String getAdditionStat() {
		return additionStat;
	}

	/**
	 * GETTER FUNCTION
	 * 
	 * @return user's subtraction statistics
	 */
	public String getSubtractionStat() {
		return subtractionStat;
	}

	/**
	 * GETTER FUNCTION
	 * 
	 * @return user's multiplication statistics
	 */
	public String getMultiplicationStat() {
		return multiplicationStat;
	}

	/**
	 * GETTER FUNCTION
	 * 
	 * @return user's division statistics
	 */
	public String getDivisionStat() {
		return divisionStat;
	}

	/**
	 * GETTER FUNCTION
	 * 
	 * @return user's avatar
	 */
	public String getAvatar() {
		return this.avatar;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Updates user's level
	 * 
	 * @param newLevel: new level to change to
	 */
	public void setLevel(String newLevel) {
		level = newLevel;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Updates user's fuel
	 * 
	 * @param newFuel: new fuel to change to
	 */
	public void setFuel(String newFuel) {
		fuel = newFuel;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Updates user's addition statistics
	 * 
	 * @param newAddStat: new addition statistics to change to
	 */
	public void setAdditionStat(String newAddStat) {
		additionStat = newAddStat;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Updates user's subtraction statistics
	 * 
	 * @param newSubStat: new subtraction statistics to change to
	 */
	public void setSubtractionStat(String newSubStat) {
		subtractionStat = newSubStat;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Updates user's multiplication statistics
	 * 
	 * @param newMultStat: new multiplication statistics to change to
	 */
	public void setMultiplicationStat(String newMultStat) {
		multiplicationStat = newMultStat;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Updates user's division statistics
	 * 
	 * @param newDivStat: new division statistics to change to
	 */
	public void setDivisionStat(String newDivStat) {
		divisionStat = newDivStat;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Set's the users avatar
	 * 
	 * @param avatarString is the new avatar
	 */
	public void setAvatar(String avatarString) {
		this.avatar = avatarString;
	}

	/**
	 * SETTER FUNCTION <br>
	 * <br>
	 * Saves all user info to accounts file
	 */
	public void saveInfo() {

		// prepare updated user info
		String fileName = "src/accounts.txt";
		String updatedInfo = this.getRole() + "," + this.getUsername() + "," + this.getPassword() + ","
				+ this.getClassCode() + "," + this.getLevel() + "," + this.getFuel() + "," + this.getAdditionStat()
				+ "," + this.getSubtractionStat() + "," + this.getMultiplicationStat() + "," + this.getDivisionStat()
				+ "," + this.getAvatar();

		// write user updated info to file
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
			String currentLine = fileReader.readLine();

			while (currentLine != null) {
				String[] lineParts = currentLine.split(",");
				if (this.getUsername().equals(lineParts[1])) {
					sb.append(updatedInfo);
				} else {
					sb.append(currentLine);
				}
				sb.append(System.lineSeparator());
				currentLine = fileReader.readLine();
			}

			fileReader.close();
			FileWriter writer = new FileWriter(fileName);
			writer.write(sb.toString());
			writer.close();

		} catch (IOException e) {
			System.out.println("Student save error code 2");
		}
	}
}
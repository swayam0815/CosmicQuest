
/**
 * STUDENT CLASS
 * @author Matthew McDonald
 */
public class Teacher {

	// declare instance variables
	private String username;
	private String password;
	private String classCode;
	
	/**
	 * CONSTRUCTOR: creates teacher user object
	 */
	public Teacher() {
		
		// set variables to null
		username = null;
		password = null;
		classCode = null;
	}
	
	/**
	 * GETTER METHOD
	 * @return the user's username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * GETTER METHOD
	 * @return the user's password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * GETTER METHOD
	 * @return the user's class code
	 */
	public String getClassCode() {
		return this.classCode;
	}
	
	/**
	 * SETTER METHOD
	 * @param newUsername is the new username to update to
	 */
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	/**
	 * SETTER METHOD
	 * @param newPassword is the new password to update to
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	/**
	 * SETTER METHOD
	 * @param newClassCode is the new class code to update to
	 */
	public void setClassCode(String newClassCode) {
		this.classCode = newClassCode;
	}
	
}
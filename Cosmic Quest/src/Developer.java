/**
 * DEVELOPER CLASS
 * <br><br>
 * Creates an instance of a developer user
 * 
 * @author Matthew McDonald
 */
public class Developer extends Student{

	/**
	 * DEVELOPER CONSTRUCTOR 
	 * <br><br>
	 * Creates developer user
	 */
	public Developer(){
		super();
	}
	
	/**
	 * GETTER FUNCTION
	 * 
	 * @return the developer's role
	 */
	@Override
	public String getRole() {
		return "developer";
	}
	
	/**
	 * INCREASE LEVEL FUNCTION
	 * <br><br>
	 * Increases level
	 * 
	 * @param addedLevel: new level to add to current level
	 */
	public void increaseLevel(int addedLevel) {
		// convert the user's current level to an integer
		int currentLevel = Integer.parseInt(this.getLevel());
		// add the new level to the current level
		int newLevel = currentLevel + addedLevel;
		// convert the new level to a string and update the user's level
		String newLevelString = Integer.toString(newLevel);
		this.setLevel(newLevelString);
	}
	
	/**
	 * DECREASE LEVEL FUNCTION
	 * <br><br>
	 * Decreases level
	 * 
	 * @param subtractedLevel: new level to subtract from current level
	 */
	public void decreaseLevel(int subtractedLevel) {
		// convert the user's current level to an integer
		int currentLevel = Integer.parseInt(this.getLevel());
		// subtract the new level from the current level
		int newLevel = currentLevel - subtractedLevel;
		// convert the new level to a string and update the user's level
		String newLevelString = Integer.toString(newLevel);
		this.setLevel(newLevelString);
	}
	
	/**
	 * INCREASE FUEL FUNCTION
	 * <br><br>
	 * Increases fuel
	 * 
	 * @param addedFuel: new fuel to add to current fuel
	 */
	public void increaseFuel(int addedFuel) {
		// convert the user's current fuel to an integer
		int currentFuel = Integer.parseInt(this.getFuel());
		// add the new fuel to the current fuel
		int newFuel = currentFuel + addedFuel;
		// convert the new fuel to a string and update the user's fuel
		String newFuelString = Integer.toString(newFuel);
		this.setFuel(newFuelString);
	}
	
	/**
	 * DECREASE FUEL FUNCTION
	 * <br><br>
	 * Decreases level
	 * 
	 * @param subtractedFuel: new fuel to subtract from current fuel
	 */
	public void decreaseFuel(int subtractedFuel) {
		// convert the user's current fuel to an integer
		int currentFuel = Integer.parseInt(this.getFuel());
		// subtract the new fuel from the current fuel
		int newFuel = currentFuel - subtractedFuel;
		// convert the new fuel to a string and update the user's fuel
		String newFuelString = Integer.toString(newFuel);
		this.setFuel(newFuelString);
	}
}
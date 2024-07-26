import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeveloperTest {

	@Test
	void testGetRole() {
		// setting up dummy variables
		Developer dummy = new Developer();

		// getting output and checking against expected
		String exp = "developer";
		String actual = dummy.getRole();

		assertEquals(exp, actual);
	}

	@Test
	void testIncreaseLevel() {
		// setting up dummy variables
		Developer dummy = new Developer();
		dummy.setLevel("0");

		int preLevel = Integer.parseInt(dummy.getLevel());

		// getting output and checking against expected
		dummy.increaseLevel(1);

		int exp = preLevel + 1;
		int actual = Integer.parseInt(dummy.getLevel());

		assertEquals(exp, actual);
	}

	@Test
	void testDecreaseLevel() {
		// setting up dummy variables
		Developer dummy = new Developer();
		dummy.setLevel("4");

		int preLevel = Integer.parseInt(dummy.getLevel());

		// getting output and checking against expected
		dummy.decreaseLevel(1);

		int exp = preLevel - 1;
		int actual = Integer.parseInt(dummy.getLevel());

		assertEquals(exp, actual);
	}

	@Test
	void testIncreaseFuel() {
		// setting up dummy variables
		Developer dummy = new Developer();
		dummy.setFuel("1000");

		int preFuel = Integer.parseInt(dummy.getFuel());

		// getting output and checking against expected
		dummy.increaseFuel(1000);

		int exp = preFuel + 1000;
		int actual = Integer.parseInt(dummy.getFuel());

		assertEquals(exp, actual);
	}

	@Test
	void testDecreaseFuel() {
		// setting up dummy variables
		Developer dummy = new Developer();

		dummy.setFuel("5000");

		int preFuel = Integer.parseInt(dummy.getFuel());

		// getting output and checking against expected
		dummy.decreaseFuel(1000);

		int exp = preFuel - 1000;
		int actual = Integer.parseInt(dummy.getFuel());

		assertEquals(exp, actual);
	}

}

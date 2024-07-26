import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AsteroidTest {

	@Test
	void testGetX() {
		// creating dummy variables
		String[] tester = { "a", "5" };
		Asteroid testAsteroid = new Asteroid(1, 2, tester);

		// getting output and checking against expected
		int exp = 1;
		int result = testAsteroid.getX();

		assertEquals(exp, result);

	}

	@Test
	void testGetY() {
		// creating dummy variables
		String[] tester = { "a", "5" };
		Asteroid testAsteroid = new Asteroid(1, 2, tester);

		// getting output and checking against expected
		int exp = 2;
		int result = testAsteroid.getY();

		assertEquals(exp, result);

	}

	@Test
	void testGetQuestion() {
		// creating dummy variables
		String[] tester = { "a", "5" };
		Asteroid testAsteroid = new Asteroid(1, 1, tester);

		// getting output and checking against expected
		String exp = "a";
		String result = testAsteroid.getQuestion();

		assertEquals(exp, result);

	}

	@Test
	void testGetAnswer() {
		// creating dummy variables
		String[] tester = { "a", "5" };
		Asteroid testAsteroid = new Asteroid(1, 1, tester);

		// getting output and checking against expected
		int exp = 5;
		int result = testAsteroid.getAnswer();

		assertEquals(exp, result);

	}

	@Test
	void testMove() {
		// creating dummy variables
		String[] tester = { "a", "5" };
		Asteroid testAsteroid = new Asteroid(1, 1, tester);

		// getting output and checking against expected
		int exp = 6;
		testAsteroid.move();
		int result = testAsteroid.getX();

		assertEquals(exp, result);
	}

}

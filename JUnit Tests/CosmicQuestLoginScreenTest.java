import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class CosmicQuestLoginScreenTest {

	@Test
	void testAddPanel() {
		// GUI function, cannot be automatically tested
		// Manually tested by running the program and seeing if the panel was added
	}

	@Test
	void testRemovePanel() {
		// GUI function, cannot be automatically tested
		// Manually tested by running the program and seeing if the panel was removed
	}

	@Test
	void testGenerateClassCode() {
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);
			String generated = test.generateClassCode();

			// getting output and checking against expected
			boolean valid = false;

			if (generated.length() > 0 || generated.length() < 8) {
				valid = true;
			}

			assertTrue(valid);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testClassCodeExistsTrue() {

		// this test requires knowledge of what codes already exits in the account file
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			boolean exists = test.classCodeExists("06382275");
			assertTrue(exists);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testClassCodeExistsFalse() {

		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			boolean exists = test.classCodeExists("1");
			assertFalse(exists);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testSignInUserTrue() {

		// this test requires knowledge of what codes already exits in the account file
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			boolean signedIn = test.signInUser("apple", "p");

			assertTrue(signedIn);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testSignInUserFalse() {

		// this test requires knowledge of what codes already exits in the account file
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			boolean signedIn = test.signInUser("DNE", "DNE");

			assertFalse(signedIn);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testSignInUserDev() {
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			boolean signedIn = test.signInUser("cs2212", "ducks2212");

			assertTrue(signedIn);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testUserExistsTrue() {

		// this test requires knowledge of what codes already exits in the account file
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			boolean exists = test.userExists("apple");

			assertTrue(exists);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testUserExistsFalse() {

		// this test requires knowledge of what codes already exits in the account file
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			boolean exists = test.userExists("DNE");

			assertFalse(exists);

		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testCreateTeacherAccount() {

		// repeated test should be run with different names to avoid false positives, or
		// should have the accounts file edited
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			// getting output and checking against expected
			test.createTeacherAccount("teacher", "testerT", "testerT", "testerT");

			boolean exists = test.userExists("testerT");

			assertTrue(exists);
		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testCreateStudentAccount() {

		// repeated test should be run with different names to avoid false positives, or
		// should have the accounts file edited
		try {

			// creating dummy variables
			JFrame dummyF = new JFrame();
			CosmicQuestLoginScreen test = new CosmicQuestLoginScreen(dummyF, 1, null);

			test.createStudentAccount("student", "testerS", "testerS");

			// getting output and checking against expected
			boolean exists = test.userExists("testerS");

			assertTrue(exists);
		}

		catch (Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	void testPaintComponentGraphics() {
		// GUI function, cannot be automatically tested
		// Manually tested by running the program and seeing if the background is loaded
		// properly
	}

}

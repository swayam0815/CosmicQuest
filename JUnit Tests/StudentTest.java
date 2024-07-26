import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class StudentTest {

	@Test
	void testGetRole() {
		// setting up dummy variables
		Student dummy = new Student();

		// getting output and checking against expected
		String exp = "student";

		String actual = dummy.getRole();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetLevel() {

		// combined the setter and getter tests since they are intrinsically linked
		Student dummy = new Student();
		dummy.setLevel("5");

		String exp = "5";
		String actual = dummy.getLevel();
		assertEquals(exp, actual);

	}

	@Test
	void testSetGetFuel() {
		// combined the setter and getter tests since they are intrinsically linked
		Student dummy = new Student();
		dummy.setFuel("500");

		String exp = "500";
		String actual = dummy.getFuel();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetAdditionStat() {
		// combined the setter and getter tests since they are intrinsically linked
		Student dummy = new Student();
		dummy.setAdditionStat("1000");

		String exp = "1000";
		String actual = dummy.getAdditionStat();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetSubtractionStat() {
		// combined the setter and getter tests since they are intrinsically linked
		Student dummy = new Student();
		dummy.setSubtractionStat("1100");

		String exp = "1100";
		String actual = dummy.getSubtractionStat();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetMultiplicationStat() {
		// combined the setter and getter tests since they are intrinsically linked
		Student dummy = new Student();
		dummy.setMultiplicationStat("1200");

		String exp = "1200";
		String actual = dummy.getMultiplicationStat();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetDivisionStat() {
		// combined the setter and getter tests since they are intrinsically linked
		Student dummy = new Student();
		dummy.setDivisionStat("1300");

		String exp = "1300";
		String actual = dummy.getDivisionStat();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetAvatar() {
		// combined the setter and getter tests since they are intrinsically linked
		Student dummy = new Student();
		dummy.setAvatar("1.png");
		;

		String exp = "1.png";
		String actual = dummy.getAvatar();
		assertEquals(exp, actual);
	}

	@Test
	void testSaveInfo() {
		Student dummy = new Student();

		// creating dummy variables
		dummy.setUsername("a");
		dummy.setPassword("dummyTest");
		dummy.setClassCode("dummyTest");
		dummy.setLevel("dummyTest");
		dummy.setFuel("dummyTest");
		dummy.setAdditionStat("dummyTest");
		dummy.setSubtractionStat("dummyTest");
		dummy.setMultiplicationStat("dummyTest");
		dummy.setDivisionStat("dummyTest");
		dummy.setAvatar("dummyTest");

		// attempting to save the info
		dummy.saveInfo();
		boolean exists = false;

		// checking if the info was saved
		String accountFile = "src/accounts.txt";
		File file = new File(accountFile);

		try {
			// create reader to read the file
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String currentLine = fileReader.readLine();

			// loop through file to check if the username already exists
			while (currentLine != null) {
				System.out.println("occ");
				String[] lineParts = currentLine.split(",");
				if ((lineParts[3]).equals("dummyTest")) {
					currentLine = null;
					fileReader.close();
					exists = true;

				} else {
					currentLine = fileReader.readLine();
				}
			}
			fileReader.close();
		} catch (IOException e) {
			fail("error occured");
		}

		// editing the info so the test can be rerun
		dummy.setClassCode("PH");
		dummy.saveInfo();

		assertTrue(exists);
	}

}

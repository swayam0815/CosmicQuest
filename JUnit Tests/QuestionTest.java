import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class QuestionTest {

	@Test
	void testGenerateQuestionAddEasy() {

		// making question
		Question dummy = new Question("add", "easy");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 10;
		String shouldContain = "+";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);
		}

	}

	@Test
	void testGenerateQuestionAddMedium() {
		// making question
		Question dummy = new Question("add", "medium");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 20;
		String shouldContain = "+";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);

		}
	}

	@Test
	void testGenerateQuestionAddHard() {
		// making question
		Question dummy = new Question("add", "hard");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 30;
		String shouldContain = "+";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);

		}
	}

	@Test
	void testGenerateQuestionSubEasy() {
		// making question
		Question dummy = new Question("subtract", "easy");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 10;
		String shouldContain = "-";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);

		}
	}

	@Test
	void testGenerateQuestionSubMedium() {
		// making question
		Question dummy = new Question("subtract", "medium");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 20;
		String shouldContain = "-";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);

		}
	}

	@Test
	void testGenerateQuestionSubHard() {
		// making question
		Question dummy = new Question("subtract", "hard");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 30;
		String shouldContain = "-";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);

		}
	}

	@Test
	void testGenerateQuestionMulEasy() {
		// making question
		Question dummy = new Question("multiply", "easy");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 5;
		String shouldContain = "x";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);

		}
	}

	@Test
	void testGenerateQuestionMulMedium() {
		// making question
		Question dummy = new Question("multiply", "medium");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 10;
		String shouldContain = "x";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);
		}
	}

	@Test
	void testGenerateQuestionMulHard() {
		// making question
		Question dummy = new Question("multiply", "hard");
		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 12;
		String shouldContain = "x";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);
		}
	}

	@Test
	void testGenerateQuestionDivEasy() {
		// making question
		Question dummy = new Question("divide", "easy");

		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 5;
		String shouldContain = "/";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange * maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);
		}
	}

	@Test
	void testGenerateQuestionDivMedium() {
		// making question
		Question dummy = new Question("divide", "medium");

		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 10;
		String shouldContain = "/";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange * maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);
		}
	}

	@Test
	void testGenerateQuestionDivHard() {
		// making question
		Question dummy = new Question("divide", "hard");

		String[] questionArray = dummy.generateQuestion();
		String q = questionArray[0];

		int maxRange = 12;
		String shouldContain = "/";

		// testing if output contains the correct character, and if the number range is
		// correct
		if (!q.contains(shouldContain)) {

			fail("does not contain " + shouldContain);
		}

		else {
			String[] parts = q.split(" ");

			int o1 = Integer.parseInt(parts[0]);
			int o2 = Integer.parseInt(parts[2]);

			boolean withinRange1 = (o1 <= maxRange * maxRange & o1 > 0);
			boolean withinRange2 = (o2 <= maxRange & o2 > 0);

			boolean withinRange = (withinRange1 & withinRange2);

			assertTrue(withinRange);
		}
	}

}

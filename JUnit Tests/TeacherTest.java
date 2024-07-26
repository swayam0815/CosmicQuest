import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TeacherTest {

	@Test
	void testSetGetUsername() {
		// combined the setter and getter tests since they are intrinsically linked
		Teacher dummy = new Teacher();
		dummy.setUsername("U");

		String exp = "U";
		String actual = dummy.getUsername();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetPassword() {
		// combined the setter and getter tests since they are intrinsically linked
		Teacher dummy = new Teacher();
		dummy.setPassword("P");

		String exp = "P";
		String actual = dummy.getPassword();
		assertEquals(exp, actual);
	}

	@Test
	void testSetGetClassCode() {
		// combined the setter and getter tests since they are intrinsically linked
		Teacher dummy = new Teacher();
		dummy.setClassCode("C");

		String exp = "C";
		String actual = dummy.getClassCode();
		assertEquals(exp, actual);
	}

}

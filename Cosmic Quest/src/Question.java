import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * QUESTION CLASS <br>
 * <br>
 * This class represents a question object that is used to generate a random
 * math question based on the given type (operator) and difficulty of the
 * question. <br>
 * 
 * @author Matthew McDonald
 */
public class Question {
	/** The question's operator type */
	private String type;
	/** The question's difficulty */
	private String difficulty;
	/** The question's format */
	private String question;
	/** The question's solution */
	private String answer;

	/**
	 * QUESTION CONSTRUCTOR
	 * 
	 * @param type       is the operator
	 * @param difficulty is the difficulty
	 */
	public Question(String type, String difficulty) {
		this.type = type;
		this.difficulty = difficulty;
		this.question = null;
		this.answer = null;
	}

	/**
	 * GENERATE QUESTION FUNCTION <br>
	 * <br>
	 * This function will generate a random math question
	 * 
	 * @return an array with the question and its solution
	 */
	public String[] generateQuestion() {
		// check the question type and call the corresponding generate question function
		if (this.type.equals("add")) {
			getAdditionQuestion();
		} else if (this.type.equals("subtract")) {
			getSubtractionQuestion();
		} else if (this.type.equals("multiply")) {
			getMultiplicationQuestion();
		} else if (this.type.equals("divide")) {
			getDivisionQuestion();
		}

		String[] questionArray = { this.question, this.answer };
		return questionArray;
	}

	/**
	 * GENERATE ADDITION QUESTION FUNCTION <br>
	 * <br>
	 * This function will generate a random addition question and its solution
	 */
	private void getAdditionQuestion() {
		// declare local variables
		int maxRange = 0;
		int operand1;
		int operand2;
		int answer;

		Random random = new Random();

		if (this.difficulty.equals("easy")) {
			maxRange = 10;
		} else if (this.difficulty.equals("medium")) {
			maxRange = 20;
		} else if (this.difficulty.equals("hard")) {
			maxRange = 30;
		}

		// generate operands with ranges depending on difficulty */
		operand1 = random.nextInt(maxRange) + 1;
		operand2 = random.nextInt(maxRange) + 1;

		// generate the question and the answer
		this.question = Integer.toString(operand1) + " + " + Integer.toString(operand2);
		answer = operand1 + operand2;
		this.answer = Integer.toString(answer);
	}

	/**
	 * GENERATE SUBTRACTION QUESTION FUNCTION <br>
	 * <br>
	 * This function will generate a random subtraction question and its solution
	 */
	private void getSubtractionQuestion() {
		// declare local variables
		int maxRange = 0;
		int operand1;
		int operand2;
		int answer;

		Random random = new Random();

		if (this.difficulty.equals("easy")) {
			maxRange = 10;
		} else if (this.difficulty.equals("medium")) {
			maxRange = 20;
		} else if (this.difficulty.equals("hard")) {
			maxRange = 30;
		}

		// generate operands with ranges depending on difficulty
		operand1 = random.nextInt(maxRange) + 1;
		operand2 = random.nextInt(operand1) + 1;

		// generate the question and the answer
		this.question = Integer.toString(operand1) + " - " + Integer.toString(operand2);
		answer = operand1 - operand2;
		this.answer = Integer.toString(answer);
	}

	/**
	 * GENERATE MULTIPLICATION QUESTION CLASS <br>
	 * <br>
	 * This function will generate a random multiplication question and its solution
	 */
	private void getMultiplicationQuestion() {
		// declare local variables
		int maxRange = 0;
		int operand1;
		int operand2;
		int answer;

		Random random = new Random();

		if (this.difficulty.equals("easy")) {
			maxRange = 5;
		} else if (this.difficulty.equals("medium")) {
			maxRange = 10;
		} else if (this.difficulty.equals("hard")) {
			maxRange = 12;
		}

		// generate operands with ranges depending on difficulty
		operand1 = random.nextInt(maxRange) + 1;
		operand2 = random.nextInt(maxRange) + 1;

		// generate the question and the answer
		this.question = Integer.toString(operand1) + " x " + Integer.toString(operand2);
		answer = operand1 * operand2;
		this.answer = Integer.toString(answer);
	}

	/**
	 * GENERATE DIVISION QUESTION CLASS <br>
	 * <br>
	 * This function will generate a random division question and its solution
	 */
	private void getDivisionQuestion() {
		// declare local variables
		int maxRange = 0;
		int operand1;
		int operand2;

		Random random = new Random();

		if (this.difficulty.equals("easy")) {
			maxRange = 5;
		} else if (this.difficulty.equals("medium")) {
			maxRange = 10;
		} else if (this.difficulty.equals("hard")) {
			maxRange = 12;
		}

		// generate operands with ranges depending on difficulty
		operand1 = random.nextInt(maxRange) + 1;
		operand2 = random.nextInt(maxRange) + 1;

		// generate the question and the answer
		int result = operand1 * operand2;
		this.question = Integer.toString(result) + " / " + Integer.toString(operand1);
		this.answer = Integer.toString(operand2);
	}

	/**
	 * GET CUSTOM QUESTIONS FUNCTION <br>
	 * <br>
	 * This function will generate an array of questions that include the custom
	 * question sets uploaded by the student's teacher along with randomly generated
	 * questions to fill in the required question amount for the level
	 * 
	 * @param difficulty is the difficulty of the questions
	 * @param classCode  is the class code of the student
	 * @return array of questions
	 */
	public static ArrayList<String[]> getCustomQuestions(String difficulty, String classCode) {
		System.out.println("question class");
		String questionSetFile = "src/questionSets.txt";
		int numberOfQuestions = 0;
		int numberOfRequiredQuestions = 0;
		ArrayList<String[]> questionArray = new ArrayList<String[]>();

		// check the required amount of questions needed
		if (difficulty.equals("easy")) {
			numberOfRequiredQuestions = 20;
		} else if (difficulty.equals("medium")) {
			numberOfRequiredQuestions = 25;
		} else if (difficulty.equals("hard")) {
			numberOfRequiredQuestions = 30;
		}

		// randomly generate questions for a student that is not apart of the class
		if (classCode.equals("none")) {
			for (int i = 0; i < numberOfRequiredQuestions; i++) {
				questionArray.add(getRandomFillQuestion(difficulty, getRandomQuestionType()));
			}
			return questionArray;

		} else {

			// get a question list that consists of the questions from the student's class
			// question set
			// as well as random questions if needed to fill up the array list to the proper
			// number of questions
			ArrayList<String> questionSet = new ArrayList<String>();

			try {

				BufferedReader fileReader = new BufferedReader(new FileReader(questionSetFile));
				String currentLine = fileReader.readLine();

				while (currentLine != null) {
					String[] lineParts = currentLine.split(",");
					if (lineParts[0].equals(classCode)) {
						for (int i = 1; i < lineParts.length; i++) {
							questionSet.add(lineParts[i]);
						}
						break;
					} else {
						currentLine = fileReader.readLine();
					}
				}

				fileReader.close();

			} catch (IOException e) {
				System.out.println("Get custom question error code 1");
			}

			// read in question from question set and attempt to add it to the array of
			// questions if it is valid
			for (int i = 0; i < questionSet.size(); i++) {

				try {
					String[] questionParts = questionSet.get(i).split("=");
					String question = questionParts[0];
					String answer = questionParts[1];
					String[] questionToAdd = { question, answer };
					questionArray.add(questionToAdd);
					numberOfQuestions += 1;
				} catch (IndexOutOfBoundsException e) {
					continue;
				}

			}

			// add in remaining random questions to fill array to the proper required length
			for (int i = numberOfQuestions; i < numberOfRequiredQuestions; i++) {
				questionArray.add(getRandomFillQuestion(difficulty, getRandomQuestionType()));
			}

			return questionArray;

		}
	}

	/**
	 * GET RANDOM FILL QUESTION <br>
	 * <br>
	 * Generates a random math question
	 * 
	 * @param difficulty is the difficulty of the question
	 * @param type       is the type of question
	 * @return a random generated question with the question and answer
	 */
	private static String[] getRandomFillQuestion(String difficulty, String type) {

		Question question = new Question(type, difficulty);
		return question.generateQuestion();

	}

	/**
	 * GET RANDOM QUESTION TYPE <br>
	 * <br>
	 * Generates a random type of question to generate
	 * 
	 * @return a random question type
	 */
	private static String getRandomQuestionType() {

		Random random = new Random();
		int randomType = random.nextInt(3);
		String questionType;

		if (randomType == 0) {
			questionType = "add";
			System.out.println("add");

		} else if (randomType == 1) {
			questionType = "subtract";
			System.out.println("sub");

		} else if (randomType == 2) {
			questionType = "multiply";
			System.out.println("mul");

		} else {
			questionType = "divide";
			System.out.println("div");
		}
		return questionType;
	}
}
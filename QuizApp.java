import java.util.*;

public class QuizApp {
    private static List<QuizQuestion> quizQuestions;
    private static int score;
    private static Scanner scanner;
    private static Timer timer;

    public static void main(String[] args) {
        initializeQuiz();

        System.out.println("Welcome to the Quiz!\n");

        for (QuizQuestion question : quizQuestions) {
            displayQuestion(question);
            startTimer(10); 
            int userAnswer = getUserAnswer(question.getOptions().size());
            stopTimer();
            checkAnswer(question, userAnswer);
            System.out.println(); 
        }

        displayResult();
        scanner.close();
    }

    private static void initializeQuiz() {
        quizQuestions = new ArrayList<>();
        quizQuestions.add(new QuizQuestion("What is the capital of France?", Arrays.asList("Berlin", "Paris", "London"), 2));
        quizQuestions.add(new QuizQuestion("Which planet is known as the Red Planet?", Arrays.asList("Mars", "Venus", "Jupiter"), 1));
        quizQuestions.add(new QuizQuestion("What is the latest mammal?", Arrays.asList("Elephant", "Blue Whale", "Giraffe"), 2));

        score = 0;
        scanner = new Scanner(System.in);
        timer = new Timer();
    }

    private static void displayQuestion(QuizQuestion question) {
        System.out.println("Question: " + question.getQuestion());
        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.print("Your choice (1-" + options.size() + "): ");
    }

    private static int getUserAnswer(int numOptions) {
        int userAnswer = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                userAnswer = scanner.nextInt();
                if (userAnswer >= 1 && userAnswer <= numOptions) {
                    validInput = true;
                } else {
                    System.out.print("Invalid choice. Please enter a number between 1 and " + numOptions + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
            } finally {
                scanner.nextLine(); 
            }
        }

        return userAnswer;
    }

    private static void checkAnswer(QuizQuestion question, int userAnswer) {
        if (userAnswer == question.getCorrectOption()) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer is option " + question.getCorrectOption() + ".");
        }
    }

    private static void displayResult() {
        System.out.println("\nQuiz completed!");
        System.out.println("Your final score is: " + score + " out of " + quizQuestions.size());

        int correctAnswers = score;
        int incorrectAnswers = quizQuestions.size() - score;

        System.out.println("Correct Answers: " + correctAnswers);
        System.out.println("Incorrect Answers: " + incorrectAnswers);
    }

    private static void startTimer(int seconds) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Moving to the next question.");
                stopTimer();
            }
        }, seconds * 1000);
    }

    private static void stopTimer() {
        timer.cancel();
        timer = new Timer();
    }
}

class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctOption;

    public QuizQuestion(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private static ArrayList<QuestionAnswer> questions;
    private static Scanner input;

    public UserInterface(Scanner in) {
        input = in;
    }

    public static void setUpUI() {
        questions
                .add(new QuestionAnswer("Learn more about the optimism/pessimism level in a certain region and time."));
        questions.add(new QuestionAnswer(
                "Read articles with some specific filter (contains a word, from a region, date, etc.)"));
    }

    public static void promptUser() {
        setUpUI();
        System.out.print("Hello! This is our project on parsing Google News related to corona. "
                + "Please input the question number which you would like an answer for:\n");

        for (int i = 1; i <= questions.size(); i++) {
            System.out.println(i + questions.get(i - 1).getQuestion());
        }

        while (true) {
            int number = input.nextInt();
            System.out.println();
            if (number == 1) {
                int region = promptRegions();
                promptDates();
            }

            if (number == 2) {
                int region = promptRegions();
                promptDates();
            }
        }
    }

    public static int promptRegions() {
        System.out.print("Please input the region number which you would like an answer for:\n" + "1. Africa\n"
                + "2. Americas\n" + "3. Eastern Mediterranean\n" + "4. Europe\n" + "5. South-East Asia\n"
                + "6. Western Pacific\n");
        int region = input.nextInt();
        if (region < 1 || region > 6) {
            System.out.println("That is not a valid region number. Try again.");
            region = promptRegions();
        }
        return region;
    }

    public static void promptDates() {
        System.out.print("Please input the date you would like an answer for. First enter the month:\n");
        int month = input.nextInt();
        System.out.print("Please enter the day:\n");
        int day = input.nextInt();
        System.out.print("Please enter the year:\n");
        int year = input.nextInt();
    }
}

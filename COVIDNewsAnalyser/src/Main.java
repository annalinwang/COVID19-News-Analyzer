import java.util.Scanner;

/**
 * Our main method that sets up our UserInterface and prompts users with
 * questions
 */
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        UserInterface ui = new UserInterface(input);
        ui.setUpUI();
        ui.promptUser();
    }
}

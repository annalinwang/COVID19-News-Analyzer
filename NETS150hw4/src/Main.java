import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        UserInterface ui = new UserInterface(input);
        ui.setUpUI();
        ui.promptUser();
    }
}

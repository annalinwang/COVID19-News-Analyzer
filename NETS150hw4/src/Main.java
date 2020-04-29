import java.time.DayOfWeek;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        ArticleSorter articleSorter = new ArticleSorter();
        Set<Article> articles = articleSorter.getArticlesInRegion("americas");
//        Set<Article> articles = articleSorter.getAllArticles();
//        Set<Article> articles = articleSorter.getArticlesInRegionOnDayOfWeek("south-east asia", DayOfWeek.TUESDAY);
//        Set<Article> articles = articleSorter.getArticlesWithTitleContaining("trump");
        
        System.out.println();
        System.out.println(articles.size() + " articles found.");
        for (Article a : articles) {
            System.out.println(a);
        }
        
        
        Scanner input = new Scanner(System.in);
        System.out.print("Hello! This is our project on parsing Google News related to corona. "
                + "Please input the question number which you would like an answer for:\n"
                + "1. What is the optimism/pessimism level in a certain region?\n"
                + "2. Read articles containing a certain word.\n"
                + "3. Read articles from a certain region."
                + "4. Rank regions by optimism/pessimism.\n");
        while (true) {
            int number = input.nextInt();
            System.out.println();
            if (number == 1) {
                System.out.print("You want the optimism/pessimism level in a certain region. "
                        + "Please input the region number which you would like an answer for:\n"
                        + "1. Africa\n"
                        + "2. Americas\n"
                        + "3. Eastern Mediterranean\n"
                        + "4. Europe\n"
                        + "5. South-East Asia\n"
                        + "6. Western Pacific\n");
                int region = input.nextInt();
                if (region == 1) {
                    //ADD METHOD HERE
                }
            } else if (number == 2) {
                System.out.print("You want to read articles containing a certain word. "
                        + "What word would you like?\n");
                String word = input.next();
            } else if (number == 3) {
                System.out.print("You want to read articles in a certain region. "
                        + "Please input the region number which you would like an answer for:\n"
                        + "1. Africa\n"
                        + "2. Americas\n"
                        + "3. Eastern Mediterranean\n"
                        + "4. Europe\n"
                        + "5. South-East Asia\n"
                        + "6. Western Pacific\n");
                int region = input.nextInt();
                if (region == 1) {
                    articles = articleSorter.getArticlesInRegion("africa");
                }
                //ADD ALL REGIONS Here
                System.out.println(articles.size() + " articles found.");
                for (Article a : articles) {
                    System.out.println(a);
                }
            } else if (number == 4) {
                System.out.print("You wanted to rank the articles by pessimism/optimism.\n"
                        + "Here is a list:\n");
                //ADD METHOD HERE for ranking articles
            } else {
                System.out.println("You did not enter a question number.");
                input.close();
            }
            System.out.println("\n You can input another number: \n");
        }
    }
}

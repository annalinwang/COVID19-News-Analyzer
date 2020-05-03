import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;


public class Main {

    public static void main(String[] args) {

        ArticleSorter articleSorter = new ArticleSorter();
        Set<Article> allArticles = articleSorter.getAllArticles();
        
        System.out.println("\n" + allArticles.size() + " total articles.\n");
        
        articleSorter.saveAllArticles();
        
        
        
        
        Set<Article> articlesToCompare = articleSorter.getArticlesByPublication("CNN");
        
        System.out.println("\nComparing " + articlesToCompare.size() + " articles.");
        
        OptimismPessimismCalculator.calculate(articlesToCompare);
        
        
        
        Scanner input = new Scanner(System.in);
        UserInterface ui = new UserInterface(input);
        ui.promptUser(input);
    }
}

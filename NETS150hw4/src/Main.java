
import java.time.DayOfWeek;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        
        ArticleSorter articleSorter = new ArticleSorter();
//        Set<Article> articles = articleSorter.getArticlesInRegion("americas");
//        Set<Article> articles = articleSorter.getAllArticles();
//        Set<Article> articles = articleSorter.getArticlesInRegion("americas");
//        Set<Article> articles = articleSorter.getAllArticles();
//        Set<Article> articles = articleSorter.getArticlesInRegionOnDayOfWeek("south-east asia", DayOfWeek.TUESDAY);
        Set<Article> articles = articleSorter.getArticlesWithTitleContaining("trump");
        
        System.out.println();
        System.out.println(articles.size() + " articles found.");
        for (Article a : articles) {
            System.out.println(a);
        }
    }
    
}

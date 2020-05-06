import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class ArticleSorter {

    static Map<String, Set<Article>> regionArticles;
    
    public ArticleSorter() {
        regionArticles = new ArticleFinder().getArticles();
    }
    
    /**
     * Gets all articles, both in database and online on Google News
     * @return A set containing all articles
     */
    public static Set<Article> getAllArticles() {
        Set<Article> answer = new TreeSet<Article>();
        for (String region : regionArticles.keySet()) {
            answer.addAll(regionArticles.get(region));
        }
        return answer;
    }
    
    public void saveAllArticles() {
        int counter = 0;
        for (Article article : getAllArticles()) {
            if (article.saveArticle()) {
                counter++;
            }
        }
        System.out.println("Saved " + counter + " new articles to the dataset.");
    }
    
    public Article getRandomArticleFrom(Set<Article> articles) {
        Object[] arr = articles.toArray();
        Random rand = new Random(System.currentTimeMillis());
        Article article = (Article) arr[rand.nextInt(arr.length)];
        return article;
    }
    
    // Regions: Africa, Americas, Eastern Mediterranean, Europe, South-East Asia, Western Pacific
    public Set<Article> getArticlesInRegion(Set<Article> articles, String region) {
        region = region.toLowerCase();
        Set<Article> answer = new TreeSet<Article>();
        for (Article a : articles) {
            if (a.getRegion().toLowerCase().contains(region)) {
                answer.add(a);
            }
        }
        return answer;
    }
    
    // Should also read from text file containing previous articles
    public Set<Article> getArticlesByPublication(Set<Article> articles, String publicationQuery) {
        Set<Article> answer = new TreeSet<Article>();
        for (Article article : articles) {
            String publisher = article.getPublisher().toLowerCase();
            if (publisher.contains(publicationQuery.toLowerCase())) {
                answer.add(article);
            }
        }
        return answer;
    }
    
    
    public Set<Article> getArticlesOnDay(Set<Article> articles, int month, int day, int year) {
        Set<Article> answer = new TreeSet<Article>();
        for (Article article : articles) {
            LocalDateTime articleDate = article.getDate();
            if (articleDate.getYear() == year 
                    && articleDate.getMonthValue() == month
                    && articleDate.getDayOfMonth() == day) {
                answer.add(article);
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesFromPastNumberOfDays(Set<Article> articles, int numDays) {
        Set<Article> answer = new TreeSet<Article>();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime past = today.minusDays(numDays);
        for (Article article : articles) {
            LocalDateTime articleDate = article.getDate();
            if (articleDate.isAfter(past)) {
                answer.add(article);
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesOnDayOfWeek(Set<Article> articles, DayOfWeek day) {
        Set<Article> answer = new TreeSet<Article>();
        for (Article article : articles) {
            LocalDateTime articleDate = article.getDate();
            if (articleDate.getDayOfWeek() == day) {
                answer.add(article);
            }
        }
        return answer;
    }
    
    
    public Set<Article> getArticlesWithTitleContaining(Set<Article> articles, String titleQuery) {
        Set<Article> answer = new TreeSet<Article>();
        for (Article article : articles) {
            String title = article.getTitle().toLowerCase();
            if (title.contains(titleQuery.toLowerCase())) {
                answer.add(article);
            }
        }
        return answer;
    }    
    
}

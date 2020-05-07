import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class ArticleSorter {

    static Map<String, Set<Article>> regionArticles;
    
    /**
     * Initializes an articlesorter class which can sort through
     * articles to return a filtered set of articles
     */
    public ArticleSorter() {
        regionArticles = new ArticleFinder().getArticles();
    }
    
    /**
     * Gets all articles, both in database and online on Google News
     * @return A set containing all articles
     */
    public Set<Article> getAllArticles() {
        Set<Article> answer = new TreeSet<Article>();
        for (String region : regionArticles.keySet()) {
            answer.addAll(regionArticles.get(region));
        }
        return answer;
    }
    
    /**
     * Saves all new articles off of google news
     */
    public void saveAllArticles() {
        int counter = 0;
        for (Article article : getAllArticles()) {
            if (article.saveArticle()) {
                counter++;
            }
        }
        System.out.println("Saved " + counter + " new articles to the dataset.\n");
    }
    
    /**
     * @param articles the set of articles
     * @return a random article from our set
     */
    public Article getRandomArticleFrom(Set<Article> articles) {
        Object[] arr = articles.toArray();
        Random rand = new Random(System.currentTimeMillis());
        Article article = (Article) arr[rand.nextInt(arr.length)];
        return article;
    }
    
    /**
     * Gets articles in a certain region
     * @param articles the articles in our set
     * @param region Africa, Americas, Eastern Mediterranean, Europe, 
     *          South-East Asia, or Western Pacific
     * @return Gets articles in the region given
     */
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
    
    /**
     * Should also read from text file containing previous articles
     * @param articles  the articles we are filtering
     * @param publicationQuery  the publisher we want our articles to be by
     * @return  the set of articles published by a certain publisher
     */
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
    
    /**
     * @param articles set of articles we are filtering
     * @param month of date
     * @param day of date
     * @param year of date
     * @return set of articles on the given date from a user
     */
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
    
    /**
     * @param articles  given set of articles
     * @param numDays   past days you want articles from
     * @return  articles filtered from the given past days
     */
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
    
    /**
     * @param articles given set of articles
     * @param day   of the week you want articles from
     * @return  articles filtered from the specific day of week
     */
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
    
    /**
     * @param articles  given set of articles
     * @param titleQuery    keyword you want articles to contain
     * @return  articles filtered with keyword containing in title
     */
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

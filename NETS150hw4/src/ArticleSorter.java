import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ArticleSorter {

    Map<String, Set<Article>> regionArticles;
    
    public ArticleSorter() {
        regionArticles = new ArticleFinder().getArticles();
    }
    
    public Set<Article> getAllArticles() {
        Set<Article> answer = new TreeSet<Article>();
        for (String region : regionArticles.keySet()) {
            answer.addAll(regionArticles.get(region));
        }
        return answer;
    }
    
    // Regions: Africa, Americas, Eastern Mediterranean, Europe, South-East Asia, Western Pacific
    public Set<Article> getArticlesInRegion(String region) {
        region = region.toLowerCase();
        String selected = null;
        if ("africa".contains(region)) {
            selected = "Africa";
        } else if ("americas".contains(region)) {
            selected = "Americas";
        } else if ("eastern mediterranean".contains(region)) {
            selected = "Eastern Mediterranean";
        } else if ("europe".contains(region)) {
            selected = "Europe";
        } else if ("south-east asia".contains(region)) {
            selected = "South-East Asia";
        } else if ("western pacific".contains(region)) {
            selected = "Western Pacific";
        }
        
        if (selected == null) {
            throw new IllegalArgumentException("Invalid region");
        }
        
        return regionArticles.get(selected);
    }
    
    // Should also read from text file containing previous articles
    public Set<Article> getArticlesByPublication(String publicationQuery) {
        Set<Article> answer = new TreeSet<Article>();
        for (String region : regionArticles.keySet()) {
            Set<Article> articles = regionArticles.get(region);
            for (Article article : articles) {
                String publisher = article.getPublisher().toLowerCase();
                if (publisher.contains(publicationQuery.toLowerCase())) {
                    answer.add(article);
                }
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesInRegionByPublication(String region, String publicationQuery) {
        Set<Article> answer = new TreeSet<Article>();
        Set<Article> articles = getArticlesInRegion(region);
        for (Article article : articles) {
            String publisher = article.getPublisher().toLowerCase();
            if (publisher.contains(publicationQuery.toLowerCase())) {
                answer.add(article);
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesOnDay(int month, int day, int year) {
        Set<Article> answer = new TreeSet<Article>();
        for (String region : regionArticles.keySet()) {
            Set<Article> articles = regionArticles.get(region);
            for (Article article : articles) {
                LocalDateTime articleDate = article.getDate();
                if (articleDate.getYear() == year 
                        && articleDate.getMonthValue() == month
                        && articleDate.getDayOfMonth() == day) {
                    answer.add(article);
                }
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesInRegionOnDay(String region, int month, int day, int year) {
        Set<Article> answer = new TreeSet<Article>();
        Set<Article> articles = getArticlesInRegion(region);
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
    
    public Set<Article> getArticlesWithTitleContaining(String titleQuery) {
        Set<Article> answer = new TreeSet<Article>();
        for (String region : regionArticles.keySet()) {
            Set<Article> articles = regionArticles.get(region);
            for (Article article : articles) {
                String title = article.getTitle().toLowerCase();
                if (title.contains(titleQuery.toLowerCase())) {
                    answer.add(article);
                }
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesInRegionWithTitleContaining(String region, String titleQuery) {
        Set<Article> answer = new TreeSet<Article>();
        Set<Article> articles = getArticlesInRegion(region);
        for (Article article : articles) {
            String title = article.getTitle().toLowerCase();
            if (title.contains(titleQuery.toLowerCase())) {
                answer.add(article);
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesOnDayOfWeek(DayOfWeek day) {
        Set<Article> answer = new TreeSet<Article>();
        for (String region : regionArticles.keySet()) {
            Set<Article> articles = regionArticles.get(region);
            for (Article article : articles) {
                LocalDateTime articleDate = article.getDate();
                if (articleDate.getDayOfWeek() == day) {
                    answer.add(article);
                }
            }
        }
        return answer;
    }
    
    public Set<Article> getArticlesInRegionOnDayOfWeek(String region, DayOfWeek day) {
        Set<Article> answer = new TreeSet<Article>();
        Set<Article> articles = getArticlesInRegion(region);
        for (Article article : articles) {
            LocalDateTime articleDate = article.getDate();
            if (articleDate.getDayOfWeek() == day) {
                answer.add(article);
            }
        }
        return answer;
    }
}

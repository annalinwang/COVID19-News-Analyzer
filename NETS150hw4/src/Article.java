import java.io.IOException;
import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Article implements Comparable<Article> {

    private String googleUrl;
    private String trueUrl;
    private String title;
    private LocalDateTime date;
    private String publisher;
    
    public Article(String url, String title, LocalDateTime date, String publisher) {
        this.googleUrl = url;
        this.title = title;
        this.date = date;
        this.publisher = publisher;
    }
    
    public String getGoogleUrl() {
        return googleUrl;
    }
    
    public String getTrueUrl() {
        Document doc;
        try {
            doc = Jsoup.connect(googleUrl).get();
            String wholeText = doc.text();
            String[] words = wholeText.split(" ");
            for (int i = 0; i < words.length; i++) {
                if (words[i].contains("Opening")) {
                    trueUrl = words[i + 1];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (trueUrl == null) {
            throw new IllegalArgumentException("article's true url cannot be found!");
        }
        return trueUrl;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public ArticleDocument getDocument() {
        return new ArticleDocument(getTrueUrl());
    }
    
    public void saveArticle() {
        // TODO
    }
    
    @Override
    public boolean equals(Object obj) { 
        if (obj == this) { 
            return true; 
        }
        if (!(obj instanceof Article)) { 
            return false; 
        } 
        Article art = (Article) obj; 
        return (this.googleUrl).equals(art.googleUrl);
    }
    
    @Override
    public String toString() {
        return this.title + ", " + this.date + ", " + this.publisher + ", " + this.googleUrl;
    }

    @Override
    public int compareTo(Article o) {
        int compared = o.date.compareTo(this.date);
        if (compared == 0) {
            if (o.googleUrl.equals(this.googleUrl)) {
                return 0;
            }
            return 1;
        }
        return compared;
    }
    
}

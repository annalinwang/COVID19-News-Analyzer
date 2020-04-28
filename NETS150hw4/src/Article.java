import java.time.LocalDateTime;
//helloooooo
public class Article implements Comparable<Article> {

    private String url;
    private String title;
    private LocalDateTime date;
    private String publisher;
    
    public Article(String url, String title, LocalDateTime date, String publisher) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.publisher = publisher;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
        return (this.url).equals(art.url);
    }
    
    @Override
    public String toString() {
        return this.title + ", " + this.date + ", " + this.publisher + ", " + this.url;
    }

    @Override
    public int compareTo(Article o) {
        int compared = o.date.compareTo(this.date);
        if (compared == 0) {
            if (o.url.equals(this.url)) {
                return 0;
            }
            return 1;
        }
        return compared;
    }
    
}

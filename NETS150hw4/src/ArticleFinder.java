

import java.io.IOException;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArticleFinder {

    
    private Document doc;
    private Map<String, String> regionLinks;
    
    private Map<String, Set<Article>> regionArticles;
    
    public ArticleFinder() {
        regionArticles = new HashMap<>();
        regionLinks = new RegionFinder().getRegionLinks();
        initializeArticles();
    }  
    
    private void initializeArticles() {
        for (String region : regionLinks.keySet()) {
            String regionLink = regionLinks.get(region);
            try {
                doc = Jsoup.connect(regionLink).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<Article> articles = new TreeSet<>();
            Elements elems = doc.select("article");
            System.out.println(region + ": " + elems.size() + " articles");
            if (elems.size() == 0) {
                System.out.println("No articles for " + region + " found "
                        + "(site likely being updated). RUN AGAIN.");
                return;
            }
            for (Element elem : elems) {
                try {
                    String url = null;
                    String title = null;
                    LocalDateTime date;
                    String publisher = "unknown";
                    // URL, TITLE, & PUBLISHER
                    Elements linkElems = elem.getElementsByAttribute("href");
                    for (Element linkElem : linkElems) {
                        if (linkElem.attr("href").contains("articles") 
                                && linkElem.hasText()) {
                            url = linkElem.absUrl("href").trim();
                            title = linkElem.text().trim();
                        } else if (linkElem.attr("href").contains("publications") 
                                && linkElem.hasText()) {
                            publisher = linkElem.text().trim();
                        }
                    }
                    
                    // Not every article has a "publications" link to mark publisher
                    if (publisher.equals("unknown")) {
                        Elements aElems = elem.select("a");
                        for (Element e : aElems) {
                            if (!e.hasAttr("href") && e.hasText()) {
                                publisher = e.text().trim();
                            }
                        }
                    }
                    
                    // DATE
                    Element dateElem = elem.getElementsByAttribute("datetime").first();
                    String rawDate = dateElem.attr("datetime");
                    DateTimeFormatter inputFormatter = 
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
                    date = LocalDateTime.parse(rawDate, inputFormatter);
                    
                    if (url != null && title != null && date != null) {
                        articles.add(new Article(url, title, date, publisher));
                    }
                } catch (Exception e) { }
            }
            regionArticles.put(region, articles);
        }
    }
    
    public Map<String, Set<Article>> getArticles() {
        return Map.copyOf(regionArticles);
    }
    
}

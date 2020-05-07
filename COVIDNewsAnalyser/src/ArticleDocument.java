import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This class represents one document. It will keep track of the term
 * frequencies.
 * 
 * @author swapneel, maxdu, annawang
 *
 */
public class ArticleDocument implements VSMDocument {

    /**
     * A hashmap for term frequencies. Maps a term to the number of times this terms
     * appears in this document.
     */
    private HashMap<String, Integer> termFrequency;
    
    private String url;
    private String region;
    private String title;
    private String publisher;
    private LocalDateTime date;

    /**
     * The constructor. It takes in the name of a file to read. It will read the
     * file and pre-process it.
     * 
     * @param url the name of the file
     */
    public ArticleDocument(String url, String region, String title, 
            String publisher, LocalDateTime date) {
        this.url = url;
        this.region = region;
        this.title = title;
        this.publisher = publisher;
        this.date = date;
        termFrequency = new HashMap<String, Integer>();

        readURLAndPreProcess();
    }

    /**
     * This method will read in the file and do some pre-processing. The following
     * things are done in pre-processing: Every word is converted to lower case.
     * Every character that is not a letter or a digit is removed. We don't do any
     * stemming. Once the pre-processing is done, we create and update the
     */
    private void readURLAndPreProcess() {
        try {
            Document doc = Jsoup.parse(new URL(url), 5000);
            String wholeText = doc.text();
            String[] words = wholeText.split(" ");
            for (String word : words) {
                String filteredWord = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
                if (!(filteredWord.equalsIgnoreCase(""))) {
                    if (termFrequency.containsKey(filteredWord)) {
                        int oldCount = termFrequency.get(filteredWord);
                        termFrequency.put(filteredWord, ++oldCount);
                    } else {
                        termFrequency.put(filteredWord, 1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error reading " + url + 
                    ". This is normal, certain webpages cannot be read.");
        }
    }

    /**
     * @return the hashmap mapping terms to frequency
     */
    public HashMap<String, Integer> getTermFrequency() {
        return termFrequency;
    }

    /**
     * @return url for the name of the file
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return region of the article
     */
    public String getRegion() {
        return region;
    }
    
    /**
     * @return title of the article
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return publisher of the article
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @return date of the article
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * This method will return the term frequency for a given word. If this document
     * doesn't contain the word, it will return 0
     * 
     * @param word The word to look for
     * @return the term frequency for this word in this document
     */
    public double getTermFrequency(String word) {
        if (termFrequency.containsKey(word)) {
            return termFrequency.get(word);
        } else {
            return 0;
        }
    }

    /**
     * This method will return a set of all the terms which occur in this document.
     * 
     * @return a set of all terms in this document
     */
    public Set<String> getTermList() {
        return termFrequency.keySet();
    }

    @Override
    public String toString() {
        return url;
    }

}
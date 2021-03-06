import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Creates a map that links urls to regions from Google News
 * @author Anna, maxdu
 *
 */
public class RegionFinder {

    private String url = 
            "https://news.google.com/topics/CAAqIggK"
            + "IhxDQkFTRHdvSkwyMHZNREZqY0hsNUVnSmxiaWdBUAE/"
            + "sections/CAQqEAgAKgcICjCcuZcLMI_irgMww"
            + "LvMBg?hl=en-US&gl=US&ceid=US%3Aen";
    private Document doc;
    private Map<String, String> regionLinks;
    
    /**
     * Instantiates a regionfinder class
     */
    public RegionFinder() {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        regionLinks = new HashMap<>();
        initializeRegionLinks();
    }  
    
    /**
     * Initializes all the links by region: Africa, Americas,
     * Eastern Mediterranean, Europe, South-East Asia, and Western Pacific
     */
    private void initializeRegionLinks() {
        Elements elems = doc.getElementsByAttribute("aria-label");
        for (Element elem : elems) {
            String label = elem.attr("aria-label");
            switch (label) {
                case "Africa":
                    regionLinks.put("Africa", elem.absUrl("href"));
                    break;
                case "Americas":
                    regionLinks.put("Americas", elem.absUrl("href"));
                    break;
                case "Eastern Mediterranean":
                    regionLinks.put("Eastern Mediterranean", elem.absUrl("href"));
                    break;
                case "Europe":
                    regionLinks.put("Europe", elem.absUrl("href"));
                    break;
                case "South-East Asia":
                    regionLinks.put("South-East Asia", elem.absUrl("href"));
                    break;
                case "Western Pacific":
                    regionLinks.put("Western Pacific", elem.absUrl("href"));
                    break;
                default:
                    continue;
            }
        }
    }
    
    /**
     * @return  the map linking region to url
     */
    public Map<String, String> getRegionLinks() {
        return regionLinks;
    }
    
}

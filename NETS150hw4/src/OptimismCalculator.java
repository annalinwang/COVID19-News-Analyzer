import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class OptimismCalculator {
    
    /**
     * An arraylist of all documents that you're reading.
     */
    private ArrayList<Article> documents;
    private String text;
    
    public OptimismCalculator(ArrayList<Article> docs) {
        documents = docs;
        for (Article x : docs) {
            text = text + x;
        }
    }
    
    
    
}

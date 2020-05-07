import java.util.Set;

/**
 * Interface of a document that will return termfrequency and termlist
 * @author Anna, maxdu
 *
 */
public interface VSMDocument {

    /**
     * @param word
     * @return the frequency of that word in the document
     */
    public double getTermFrequency(String word);

    /**
     * @return the list of terms in a document
     */
    public Set<String> getTermList();

}

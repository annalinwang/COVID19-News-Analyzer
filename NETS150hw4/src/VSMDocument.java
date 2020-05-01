import java.util.Set;


public interface VSMDocument {
    
    public double getTermFrequency(String word);
    
    public Set<String> getTermList();
    
}

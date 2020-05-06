import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OptimismPessimismCalculator {

    private static int size;
    private static double avgPositive;
    private static double avgNegative;
    private static double mostPositive;
    private static VSMDocument mostPositiveDoc;
    private static double mostNegative;
    private static VSMDocument mostNegativeDoc;
    private static double biggestDifference;
    private static VSMDocument biggestDifferenceDoc;
    
    public OptimismPessimismCalculator() {
        mostPositive = 0;
        mostPositiveDoc = null;
        mostNegative = 0;
        mostNegativeDoc = null;
        biggestDifference = 0;
        biggestDifferenceDoc = null;
    }
    
    public static void calculate(Set<Article> articles) {
        
        Set<ArticleDocument> articleDocuments = new HashSet<>();
        for (Article article : articles) {
            articleDocuments.add(article.getDocument());
        }
        
        VSMDocument positiveWords = new TextDocument("positive_words.txt");
        VSMDocument negativeWords = new TextDocument("negative_words.txt");
        
        ArrayList<VSMDocument> documents = new ArrayList<VSMDocument>();
        documents.add(positiveWords);
        documents.add(negativeWords);
        documents.addAll(articleDocuments);
        
        Corpus corpus = new Corpus(documents);
        
        VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
        
        double totalPositive = 0;
        double totalNegative = 0;
        
        for(int i = 2; i < documents.size(); i++) {
            VSMDocument doc = documents.get(i);
            System.out.println("\nComparing to " + doc);
            double positive = vectorSpace.cosineSimilarity(positiveWords, doc);
            double negative = vectorSpace.cosineSimilarity(negativeWords, doc);
            System.out.println("Positive: " + positive);
            System.out.println("Negative: " + negative);
            if (!Double.isNaN(positive)) {
                totalPositive += positive;
            }
            if (!Double.isNaN(negative)) {
                totalNegative += negative;
            }
            double difference = positive - negative;
            if (difference >= 0) {
                System.out.println("More positive by " + difference);
            } else {
                System.out.println("More negative by " + Math.abs(difference));
            }
            
            if (positive >= mostPositive) {
                mostPositive = positive;
                mostPositiveDoc = doc;
            }
            if (negative >= mostNegative) {
                mostNegative = negative;
                mostNegativeDoc = doc;
            }
            if (Math.abs(difference) >= Math.abs(biggestDifference)) {
                biggestDifference = difference;
                biggestDifferenceDoc = doc;
            }
            
        }
        size = documents.size();
        avgPositive = totalPositive / size;
        avgNegative = totalNegative / size;
    }
    
    public double getAvgPositive() {
        return avgPositive;
    }
    
    public double getAvgNegative() {
        return avgNegative;
    }
    
    public double getMostPositive() {
        return mostPositive;
    }
    
    public VSMDocument getMostPositiveDoc() {
        return mostPositiveDoc;
    }
    
    public double getMostNegative() {
        return mostNegative;
    }
    
    public VSMDocument getMostNegativeDoc() {
        return mostNegativeDoc;
    }
    
    public double getBiggestDifference() {
        return biggestDifference;
    }
    
    public VSMDocument getBiggestDifferenceDoc() {
        return biggestDifferenceDoc;
    }
    
    public static void printInfo() {
        System.out.println("\n---------------------------------------------\n");
        System.out.println("Most positive article: " + mostPositiveDoc + "\nwith positivity " + mostPositive + "\n");
        System.out.println("Most negative article: " + mostNegativeDoc + "\nwith negativity " + mostNegative + "\n");
        if (biggestDifference >= 0) {
            System.out.println("Biggest difference: " + biggestDifferenceDoc + "\nwith more positivity by " + biggestDifference);
        } else {
            System.out.println("Biggest difference: " + biggestDifferenceDoc + "\nwith more negativity by " + Math.abs(biggestDifference));
        }
        
        System.out.println("\nAverage positivity: " + avgPositive);
        System.out.println("Average negativity: " + avgNegative);
        System.out.println("\n---------------------------------------------");
    }
    
}

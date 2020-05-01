import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OptimismPessimismCalculator {

    
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
        
        double mostPositive = 0;
        VSMDocument mostPositiveDoc = null;
        double mostNegative = 0;
        VSMDocument mostNegativeDoc = null;
        double biggestDifference = 0;
        VSMDocument biggestDifferenceDoc = null;
        
        for(int i = 2; i < documents.size(); i++) {
            VSMDocument doc = documents.get(i);
            System.out.println("\nComparing to " + doc);
            double positive = vectorSpace.cosineSimilarity(positiveWords, doc);
            double negative = vectorSpace.cosineSimilarity(negativeWords, doc);
            System.out.println("Positive: " + vectorSpace.cosineSimilarity(positiveWords, doc));
            System.out.println("Negative: " + vectorSpace.cosineSimilarity(negativeWords, doc));
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
        
        System.out.println("\n---------------------------------------------\n");
        System.out.println("Most positive article: " + mostPositiveDoc + "\nwith positivity " + mostPositive + "\n");
        System.out.println("Most negative article: " + mostNegativeDoc + "\nwith negativity " + mostNegative + "\n");
        if (biggestDifference >= 0) {
            System.out.println("Biggest Difference: " + biggestDifferenceDoc + "\nwith more positivity by " + biggestDifference);
        } else {
            System.out.println("Biggest Difference: " + biggestDifferenceDoc + "\nwith more negativity by " + Math.abs(biggestDifference));
        }

    }
    
}

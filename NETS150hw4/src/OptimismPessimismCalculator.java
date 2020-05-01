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
        
        for(int i = 2; i < documents.size(); i++) {
            VSMDocument doc = documents.get(i);
            System.out.println("\nComparing to " + doc);
            double positive = vectorSpace.cosineSimilarity(positiveWords, doc);
            double negative = vectorSpace.cosineSimilarity(negativeWords, doc);
            System.out.println("Positive: " + vectorSpace.cosineSimilarity(positiveWords, doc));
            System.out.println("Negative: " + vectorSpace.cosineSimilarity(negativeWords, doc));
            double difference = Math.abs(positive - negative);
            if (positive >= negative) {
                System.out.println("More positive by " + difference);
            } else {
                System.out.println("More negative by " + difference);
            }
        }
    }
    
}

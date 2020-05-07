import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * This class is our optimismpessimism calculator which calculates interesting
 * statistics from a given set of articles
 * @author Anna, maxdu
 */
public class OptimismPessimismCalculator {

    private int size;
    private double avgPositive;
    private double avgNegative;
    private double mostPositive;
    private VSMDocument mostPositiveDoc;
    private double mostNegative;
    private VSMDocument mostNegativeDoc;
    private double biggestDifference;
    private VSMDocument biggestDifferenceDoc;
    
    private Map<String, LinkedList<Double>> publisherOptimism;
    private Map<String, LinkedList<Double>> publisherPessimism;
    private Map<String, LinkedList<Double>> regionOptimism;
    private Map<String, LinkedList<Double>> regionPessimism;
    private Map<LocalDate, LinkedList<Double>> dayOptimism;
    private Map<LocalDate, LinkedList<Double>> dayPessimism;
    private Map<DayOfWeek, LinkedList<Double>> weekdayOptimism;
    private Map<DayOfWeek, LinkedList<Double>> weekdayPessimism;
    
    /**
     * Sets up our optimism pessimism calculator class
     * Sets numbers to 0 and documents to null.
     * Instantiates hashmaps for specific optimism/pessimism maps
     */
    public OptimismPessimismCalculator() {
        mostPositive = 0;
        mostPositiveDoc = null;
        mostNegative = 0;
        mostNegativeDoc = null;
        biggestDifference = 0;
        biggestDifferenceDoc = null;
        
        publisherOptimism = new HashMap<>();
        publisherPessimism = new HashMap<>();
        regionOptimism = new HashMap<>();
        regionPessimism = new HashMap<>();
        dayOptimism = new HashMap<>();
        dayPessimism = new HashMap<>();
        weekdayOptimism = new HashMap<>();
        weekdayPessimism = new HashMap<>();
    }
    
    /**
     * Given a set of articles, does the calculations for optimism/pessimism
     * @param articles  given set of articles
     */
    public void calculate(Set<Article> articles) {
        
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
        
        
        for(ArticleDocument articleDoc : articleDocuments) {
            VSMDocument doc = (VSMDocument) articleDoc;
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
            
            String publisher = articleDoc.getPublisher();
            String region = articleDoc.getRegion();
            LocalDate day = articleDoc.getDate().toLocalDate();
            DayOfWeek weekday = day.getDayOfWeek();
            if (!Double.isNaN(positive)) {
                if (publisherOptimism.containsKey(publisher)) {
                    publisherOptimism.get(publisher).add(positive);
                } else {
                    publisherOptimism.put(publisher, new LinkedList<Double>());
                    publisherOptimism.get(publisher).add(positive);
                }
                
                if (publisherPessimism.containsKey(publisher)) {
                    publisherPessimism.get(publisher).add(negative);
                } else {
                    publisherPessimism.put(publisher, new LinkedList<Double>());
                    publisherPessimism.get(publisher).add(negative);
                }
                
                if (regionOptimism.containsKey(region)) {
                    regionOptimism.get(region).add(positive);
                } else {
                    regionOptimism.put(region, new LinkedList<Double>());
                    regionOptimism.get(region).add(positive);
                }
                
                if (regionPessimism.containsKey(region)) {
                    regionPessimism.get(region).add(negative);
                } else {
                    regionPessimism.put(region, new LinkedList<Double>());
                    regionPessimism.get(region).add(negative);
                }
                
                if (dayOptimism.containsKey(day)) {
                    dayOptimism.get(day).add(positive);
                } else {
                    dayOptimism.put(day, new LinkedList<Double>());
                    dayOptimism.get(day).add(positive);
                }
                
                if (dayPessimism.containsKey(day)) {
                    dayPessimism.get(day).add(negative);
                } else {
                    dayPessimism.put(day, new LinkedList<Double>());
                    dayPessimism.get(day).add(negative);
                }
                
                if (weekdayOptimism.containsKey(weekday)) {
                    weekdayOptimism.get(weekday).add(positive);
                } else {
                    weekdayOptimism.put(weekday, new LinkedList<Double>());
                    weekdayOptimism.get(weekday).add(positive);
                }
                
                if (weekdayPessimism.containsKey(weekday)) {
                    weekdayPessimism.get(weekday).add(negative);
                } else {
                    weekdayPessimism.put(weekday, new LinkedList<Double>());
                    weekdayPessimism.get(weekday).add(negative);
                }
            }
        }
        
        size = articleDocuments.size();
        avgPositive = totalPositive / size;
        avgNegative = totalNegative / size;
        printInfo();
    }
    
    /**
     * @return average positivity from given set of articles
     */
    public double getAvgPositive() {
        return avgPositive;
    }
    
    /**
     * @return average negativity from given set of articles
     */
    public double getAvgNegative() {
        return avgNegative;
    }
    
    /**
     * @return  most positivity from given set of articles
     */
    public double getMostPositive() {
        return mostPositive;
    }
    
    /**
     * @return most positivie doc from given set of articles
     */
    public VSMDocument getMostPositiveDoc() {
        return mostPositiveDoc;
    }
    
    /**
     * @return most negativity from given set of articles
     */
    public double getMostNegative() {
        return mostNegative;
    }
    
    /**
     * @return most negative doc from given set of articles
     */
    public VSMDocument getMostNegativeDoc() {
        return mostNegativeDoc;
    }
    
    /**
     * @return  the biggest difference between positivity and negativity
     */
    public double getBiggestDifference() {
        return biggestDifference;
    }
    
    /**
     * @return  doc with biggest difference between positivity and negativity
     */
    public VSMDocument getBiggestDifferenceDoc() {
        return biggestDifferenceDoc;
    }
    
    /**
     * Prints out all the basic and advanced results that we found with our 
     * optimism/pessimism calculator from the given set of articles
     */
    public void printInfo() {
        System.out.println("\n\n---------------------BASIC RESULTS-----------------------\n");
        System.out.println("Most positive article: " + mostPositiveDoc + "\nwith positivity " + mostPositive + "\n");
        System.out.println("Most negative article: " + mostNegativeDoc + "\nwith negativity " + mostNegative + "\n");
        if (biggestDifference >= 0) {
            System.out.println("Biggest difference: " + biggestDifferenceDoc + "\nwith more positivity by " + biggestDifference);
        } else {
            System.out.println("Biggest difference: " + biggestDifferenceDoc + "\nwith more negativity by " + Math.abs(biggestDifference));
        }
        
        System.out.println("\nAverage positivity: " + avgPositive);
        System.out.println("Average negativity: " + avgNegative);
        System.out.println("\n-----------------------------------------------------------\n");
        System.out.println("\n---------------------ADVANCED RESULTS----------------------");
        
        System.out.println("\nAverage positivity for PUBLISHERS:");
        for (String s : publisherOptimism.keySet()) {
            LinkedList<Double> positives = publisherOptimism.get(s);
            int size = positives.size();
            double total = 0;
            for (double d : positives) {
                total += d;
            }
            System.out.println(s + ", positivity = " + total / size);
        }
        
        System.out.println("\nAverage negativity for PUBLISHERS:");
        for (String s : publisherPessimism.keySet()) {
            LinkedList<Double> negatives = publisherPessimism.get(s);
            int size = negatives.size();
            double total = 0;
            for (double d : negatives) {
                total += d;
            }
            System.out.println(s + ", negativity = " + total / size);
        }
        
        System.out.println("\nAverage positivity for REGIONS:");
        for (String s : regionOptimism.keySet()) {
            LinkedList<Double> positives = regionOptimism.get(s);
            int size = positives.size();
            double total = 0;
            for (double d : positives) {
                total += d;
            }
            System.out.println(s + ", positivity = " + total / size);
        }
        
        System.out.println("\nAverage negativity for REGIONS:");
        for (String s : regionPessimism.keySet()) {
            LinkedList<Double> negatives = regionPessimism.get(s);
            int size = negatives.size();
            double total = 0;
            for (double d : negatives) {
                total += d;
            }
            System.out.println(s + ", negativity = " + total / size);
        }
        
        System.out.println("\nAverage positivity for DATES:");
        for (LocalDate s : dayOptimism.keySet()) {
            LinkedList<Double> positives = dayOptimism.get(s);
            int size = positives.size();
            double total = 0;
            for (double d : positives) {
                total += d;
            }
            System.out.println(s + ", positivity = " + total / size);
        }
        
        System.out.println("\nAverage negativity for DATES:");
        for (LocalDate s : dayPessimism.keySet()) {
            LinkedList<Double> negatives = dayPessimism.get(s);
            int size = negatives.size();
            double total = 0;
            for (double d : negatives) {
                total += d;
            }
            System.out.println(s + ", negativity = " + total / size);
        }
        
        System.out.println("\nAverage positivity for WEEKDAYS:");
        for (DayOfWeek s : weekdayOptimism.keySet()) {
            LinkedList<Double> positives = weekdayOptimism.get(s);
            int size = positives.size();
            double total = 0;
            for (double d : positives) {
                total += d;
            }
            System.out.println(s + ", positivity = " + total / size);
        }
        
        System.out.println("\nAverage negativity for WEEKDAYS:");
        for (DayOfWeek s : weekdayPessimism.keySet()) {
            LinkedList<Double> negatives = weekdayPessimism.get(s);
            int size = negatives.size();
            double total = 0;
            for (double d : negatives) {
                total += d;
            }
            System.out.println(s + ", negativity = " + total / size);
        }
        
        System.out.println("\n-----------------------------------------------------------\n");
        
    }
    
}

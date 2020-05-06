
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserInterface {
    private static ArrayList<QuestionAnswer> questions;
    private static Scanner input;
    private static ArticleSorter articleSorter;
    static Set<Article> allArticles;

    public UserInterface(Scanner in) {
        input = in;
        articleSorter = new ArticleSorter();
        questions = new ArrayList<QuestionAnswer>();
        allArticles = articleSorter.getAllArticles();
        System.out.println("\n" + allArticles.size() + " total articles (saved + current).\n");
        articleSorter.saveAllArticles();        
    }

    public static void setUpUI() {
        questions.add(new QuestionAnswer(
                "Find a random article in a specific filter."));
        questions.add(new QuestionAnswer(
                "Find all articles in a specific filter."));
        questions
                .add(new QuestionAnswer("Learn about the optimism/pessimism level of a set of articles under a filter."));
        questions
                .add(new QuestionAnswer("Compare the optimism/pessimism level between two sets of articles with different filters."));
    }

    public static void promptUser() {
        setUpUI();
        System.out.print("Hello! This is our project on parsing Google News related to corona.\n"
                + "Each question comes with user-specified article filters: region, publication, "
                + "date, weekday, and if it contains a certain word in the title.\n"
                + "Please input the question number which you would like an answer for:\n\n");

        for (int i = 1; i <= questions.size(); i++) {
            System.out.println(i + ". " + questions.get(i - 1).getQuestion());
        }

        boolean wantsToContinue = true;
        while (wantsToContinue) {
            myQuestions();
            System.out.println("Would you like to continue? (yes/no)\n");
            String ans = input.next();
            if (ans.equals("no") || ans.equals("No")) {
                wantsToContinue = false;
            }
            if (ans.equals("yes") || ans.equals("Yes")) {
                myQuestions();
            }
        }
    }
    
    public static void myQuestions() {
        int number = input.nextInt();
        System.out.println();
        if (number == 1) {
            System.out.println("We will first ask you a set of questions to filter your random article.");
            Set<Article> mySet = promptArticleFilters(allArticles);
            Article randomArticle = articleSorter.getRandomArticleFrom(mySet);
            System.out.println("A random article from your filter is: " + randomArticle.toString());
        } else if (number == 2) {
            System.out.println("We will first ask you a set of questions to filter your set of articles.");
            Set<Article> mySet = promptArticleFilters(allArticles);
            System.out.println("We have found " + mySet.size() + " articles under your filter: \n");
            for (Article x : mySet) {
                System.out.println(x.toString());
            }
        } else if (number == 3) {
            System.out.println("We will first ask you a set of questions to filter the articles you would like to"
                    + " know more about.");
            Set<Article> mySet = promptArticleFilters(allArticles);
            System.out.println("\nComparing " + mySet.size() + " articles.");
            OptimismPessimismCalculator.calculate(mySet);
            OptimismPessimismCalculator.printInfo();
        } else if (number == 4) {
            System.out.println("We will be comparing two sets of articles for optimism/pessimism."
                    + "\nWe will first ask you a set of questions to filter the FIRST set of articles to compare.");
            Set<Article> firstSet = promptArticleFilters(allArticles);
            OptimismPessimismCalculator calc1 = new OptimismPessimismCalculator();
            calc1.calculate(firstSet);
            System.out.println("The next set of questions are to filter the SECOND set of articles to compare.");
            Set<Article> secondSet = promptArticleFilters(allArticles);
            OptimismPessimismCalculator calc2 = new OptimismPessimismCalculator();
            calc2.calculate(secondSet);
            calculateDifferencesOptimismPessimism(calc1, calc2);
        }
    }

    public static void calculateDifferencesOptimismPessimism(OptimismPessimismCalculator calc1,
            OptimismPessimismCalculator calc2) {
        double avgPositive1 = calc1.getAvgPositive();
        double avgNegative1 = calc1.getAvgNegative();
        double mostPositive1 = calc1.getMostPositive();
        VSMDocument mostPositiveDoc1 = calc1.getMostPositiveDoc();
        double mostNegative1 = calc1.getMostNegative();;
        VSMDocument mostNegativeDoc1 = calc1.getMostNegativeDoc();
        
        double avgPositive2 = calc2.getAvgPositive();
        double avgNegative2 = calc2.getAvgNegative();
        double mostPositive2 = calc2.getMostPositive();
        VSMDocument mostPositiveDoc2 = calc2.getMostPositiveDoc();
        double mostNegative2 = calc2.getMostNegative();;
        VSMDocument mostNegativeDoc2 = calc2.getMostNegativeDoc();
        
        System.out.println("The differences between your first set and second set:\n");
        System.out.println("Your first set and second set had an average positivity/negativity  difference of: "
                + Math.abs(avgPositive1 - avgPositive2) + "\nYour first set had an average positivity of "
                + avgPositive1 + " while your second set had an average positivity of " + avgPositive2);
        System.out.println("Your first set and second set had an average positivity/negativity  difference of: "
                + Math.abs(avgNegative1 - avgNegative2) + "\nYour first set had an average negativity of "
                + avgNegative1 + " while your second set had an average negativity of " + avgNegative2);
        System.out.println(
                "Your first set and second set's most positive article had positivity/negativity difference of: "
                        + Math.abs(mostPositive1 - mostPositive2) + "Your first set's most positive article was "
                        + mostPositiveDoc1 + "with a positivity/negativity of: " + mostPositive1
                        + " while your second set's most positive article was: " + mostPositiveDoc2
                        + "with a positivity/negativity of: " + mostPositive2);
        System.out.println(
                "Your first set and second set's most negative article had positivity/negativity difference of: "
                        + Math.abs(mostNegative1 - mostNegative2) + "Your first set's most negative article was "
                        + mostNegativeDoc1 + "with a positivity/negativity of: " + mostNegative1
                        + " while your second set's most negative article was: " + mostNegativeDoc2
                        + "with a positivity/negativity of: " + mostNegative2);
    }
    
    public static Set<Article> promptArticleFilters(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        newSet = promptRegions(myArticles);
        newSet = promptPublication(newSet);
        newSet = promptTime(newSet);
        newSet = promptTitle(newSet);
        return newSet;
    }

    public static Set<Article> promptRegions(Set<Article> myArticles) {
        System.out.print("The first filter is region. Please choose one of the following:\n"
                + "1. Africa\n" + "2. Americas\n" + "3. Eastern Mediterranean\n" 
                + "4. Europe\n" + "5. South-East Asia\n" + "6. Western Pacific\n" + "7. All of the above\n");
        int currentRegion = input.nextInt();
        Set<Article> addSet = new HashSet<Article>();
        if (currentRegion == 1) {
            addSet = articleSorter.getArticlesInRegion(myArticles, "Africa");
        } else if (currentRegion == 2) {
            addSet = articleSorter.getArticlesInRegion(myArticles, "Americas");
        } else if (currentRegion == 3) {
            addSet = articleSorter.getArticlesInRegion(myArticles, "Eastern Mediterranean");
        } else if (currentRegion == 4) {
            addSet = articleSorter.getArticlesInRegion(myArticles, "Europe");
        } else if (currentRegion == 5) {
            addSet = articleSorter.getArticlesInRegion(myArticles, "South-East Asia");
        } else if (currentRegion == 6) {
            addSet = articleSorter.getArticlesInRegion(myArticles, "Western Pacific");
        } else if (currentRegion == 7) {
            return myArticles;
        }
        if (addSet.size() == 0) {
            System.out.println("Just to let you know, there are no articles under your filter.\n");
        }
        return addSet;
    }
    
    public static Set<Article> promptPublication(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is publication. Please choose one of the following."
                + "1. Specific publication (cnn, cnbc, nbc, etc.)\n"
                + "2. All publications\n");
        int answer = input.nextInt();
        if (answer == 2) {
            return myArticles;
        }
        else if (answer == 1) {
            System.out.println("Please type in the publication you would like to filter");
            String publication = input.next();
            newSet = articleSorter.getArticlesByPublication(myArticles, publication);
        }
        if (newSet.size() == 0) {
            System.out.println("Just to let you know, there are no articles under your filter.\n");
        }
        return newSet;
    }
    
    
    // not gonna use day of week...
    public static Set<Article> promptTime(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is time of publication. Please choose one of the following."
                + "1. Articles written on a specific date\n"
                + "2. Articles from the past number of days\n"
                + "3. Articles released any time\n");
        int answer = input.nextInt();
        if (answer == 3) {
            return myArticles;
        }
        else if (answer == 1) {
            System.out.print("Please input the date you would like an answer for. First enter the month:\n");
            int month = input.nextInt();
            System.out.print("Please enter the day:\n");
            int day = input.nextInt();
            System.out.print("Please enter the year:\n");
            int year = input.nextInt();
            newSet = articleSorter.getArticlesOnDay(myArticles, month, day, year);
        }
        else if (answer == 2) {
            System.out.print("Please input the past number of days you would like articles from:\n");
            int days = input.nextInt();
            newSet = articleSorter.getArticlesFromPastNumberOfDays(myArticles, days);
        }        
        if (newSet.size() == 0) {
            System.out.println("Just to let you know, there are no articles under your filter.\n");
        }
        return newSet;
    }
    
    public static Set<Article> promptTitle(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is articles with a title containing a certain word/order of words. "
                + "Please choose one of the following."
                + "1. Filter articles with title containing words\n"
                + "2. Get articles with all titles\n");
        int answer = input.nextInt();
        if (answer == 2) {
            return myArticles;
        }
        else if (answer == 1) {
            System.out.print("Please input the query you would like your articles' title to contain "
                    + "(e.g. donald trump, corona, happy: \n");
            String words = input.next();
            newSet = articleSorter.getArticlesWithTitleContaining(myArticles, words);
        }
        if (newSet.size() == 0) {
            System.out.println("Just to let you know, there are no articles under your filter.\n");
        }
        return newSet;
    }
}

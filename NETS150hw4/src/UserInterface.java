
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
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

    public void setUpUI() {
        questions.add(new QuestionAnswer(
                "Find a random article in a specific filter."));
        questions.add(new QuestionAnswer(
                "Find all articles in a specific filter."));
        questions
                .add(new QuestionAnswer("Learn about the optimism/pessimism level of a set of articles under a filter."));
    }

    public void promptUser() {
        setUpUI();
        System.out.print("Hello! This is our project on parsing Google News related to corona.\n"
                + "Each question comes with user-specified article filters: region, publication, "
                + "date, weekday, and if it contains a certain word in the title.\n"
                + "Please input the question number which you would like an answer for:\n");

        for (int i = 1; i <= questions.size(); i++) {
            System.out.println(i + ". " + questions.get(i - 1).getQuestion());
        }

        boolean wantsToContinue = true;
        while (wantsToContinue) {
            System.out.println();
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
    
    public void myQuestions() {
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
        }
    }

    public Set<Article> promptArticleFilters(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        newSet = promptRegions(myArticles);
        findSize(newSet);
        newSet = promptPublication(newSet);
        findSize(newSet);
        newSet = promptTime(newSet);
        findSize(newSet);
        newSet = promptTitle(newSet);
        findSize(newSet);
        return newSet;
    }
    
    public void findSize(Set<Article> myArticles) {
        int size = myArticles.size();
        if (size == 0) {
            System.out.println("Just to let you know, there are no articles under your filter.\n");
        } else {
            System.out.println("The number of articles under your current filters is: " + myArticles.size() + "\n");
        }
    }

    public Set<Article> promptRegions(Set<Article> myArticles) {
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
    
    public Set<Article> promptPublication(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is publication. Please choose one of the following:\n"
                + "1. Specific publication (cnn, cnbc, nbc, etc.)\n"
                + "2. All publications\n");
        int answer = input.nextInt();
        if (answer == 2) {
            return myArticles;
        }
        else if (answer == 1) {
            System.out.println("Please type in the publication you would like to filter. \n"
                    + "The current top 10 publications are: \n ");
            topTenPublications(myArticles);
            String publication = input.next();
            newSet = articleSorter.getArticlesByPublication(myArticles, publication);
        }
        if (newSet.size() == 0) {
            System.out.println("Just to let you know, there are no articles under your filter.\n");
        }
        return newSet;
    }

    public void topTenPublications(Set<Article> myArticles) {
        Map<String, Integer> publicationsToNumber = new HashMap<String, Integer>();
        for (Article x : myArticles) {
            String publisher = x.getPublisher();
            if (publicationsToNumber.containsKey(publisher)) {
                int num = (int) publicationsToNumber.get(publisher);
                publicationsToNumber.replace(publisher, num++);
            } else {
                publicationsToNumber.put(publisher, 1);
            }
        }
        /**
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(Comparator.comparing(e -> e.getValue()));
        for (Map.Entry<String, Integer> entry : publicationsToNumber.entrySet()) {
            queue.offer(entry);
            if (queue.size() > 10) {
                queue.poll();
            }
        }
        ArrayList<String> results = new ArrayList<String>();
        while (queue.size() > 0) {
            results.add(queue.poll().getKey());
        }
        Collections.reverse(results);

        for (int i = 0; i < 10; i++) {
            if (!queue.isEmpty()) {
                System.out.println(i + ". " + results.get(i));
            }
        }
        FIX TOMORROW
        */
        
    }
    
    
    // not gonna use day of week...
    public Set<Article> promptTime(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is time of publication. Please choose one of the following:\n"
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
    
    public Set<Article> promptTitle(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is articles with a title containing a certain word/order of words. "
                + "Please choose one of the following:\n"
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

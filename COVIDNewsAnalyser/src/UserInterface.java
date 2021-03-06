import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Creates the User Interface that users can interact with
 * to filter down and learn more about sets of articles from
 * Google News.
 * @author Anna, maxdu
 *
 */
public class UserInterface {
    private static ArrayList<QuestionAnswer> questions;
    private static Scanner input;
    private static ArticleSorter articleSorter;
    static Set<Article> allArticles;

    /**
     * Instantiates the user interface and updates/saves articles
     * @param in    scanner to read user's inputs
     */
    public UserInterface(Scanner in) {
        input = in;
        articleSorter = new ArticleSorter();
        questions = new ArrayList<QuestionAnswer>();
        allArticles = articleSorter.getAllArticles();
        System.out.println("\n" + allArticles.size() + " total articles (saved + current).\n");
        articleSorter.saveAllArticles();
    }

    /**
     * Adds the questions we will allow users to ask
     */
    public void setUpUI() {
        questions.add(new QuestionAnswer("Find a random article in a specific filter."));
        questions.add(new QuestionAnswer("Find all articles in a specific filter."));
        questions.add(new QuestionAnswer(
                "Learn about the optimism/pessimism level of a set " + "of articles under a filter."));
    }

    /**
     * Prompts users with the list of questions and runs until user wants to stop.
     */
    public void promptUser() {
        System.out.print("\nHello! This is our project on parsing Google News " + "related to corona.\n"
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
            System.out.println("-----------------------------------------------------------");
            System.out.println("Would you like to continue? (yes/no)\n");
            String ans = input.next();
            if (ans.equals("no") || ans.equals("No")) {
                wantsToContinue = false;
            }
            if (ans.equals("yes") || ans.equals("Yes")) {
                promptUser();
                wantsToContinue = false;
            }
        }
    }

    /**
     * Prompts the user with filters and then prints out the information
     * according to which question they chose to learn more about.
     */
    public void myQuestions() {
        int number = input.nextInt();
        System.out.println();
        if (number == 1) {
            System.out.println("We will first ask you a set of questions "
                    + "to filter your random article.");
            Set<Article> mySet = promptArticleFilters(allArticles);
            Article randomArticle = articleSorter.getRandomArticleFrom(mySet);
            System.out.println("A random article from your filter is: " 
            + randomArticle.toString());
        } else if (number == 2) {
            System.out.println("We will first ask you a set of questions"
                    + " to filter your set of articles.");
            Set<Article> mySet = promptArticleFilters(allArticles);
            for (Article x : mySet) {
                System.out.println(x.toString());
            }
        } else if (number == 3) {
            System.out.println("We will first ask you a set of questions"
                    + " to filter the articles you would like to"
                    + " know more about.");
            Set<Article> mySet = promptArticleFilters(allArticles);
            System.out.println("\nCalculating optimism/pessimism for " 
            + mySet.size() + " articles...");
            OptimismPessimismCalculator calc = new OptimismPessimismCalculator();
            if (mySet.size() == 0) {
                System.out.println(
                        "There are no articles under your given filters "
                        + "to analyze for optimism/pessimism levels.");
            } else {
                calc.calculate(mySet);
            }
        }
    }

    /**
     * Prompts the user with questions to filter down their article set.
     * If the user has filtered too much, so the size of the article set becomes
     * 0, it will terminate.
     * @param myArticles    all articles
     * @return  a newly filtered set of articles
     */
    public Set<Article> promptArticleFilters(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        newSet = promptRegions(myArticles);
        findSize(newSet);
        if (newSet.size() == 0) {
            return newSet;
        }
        newSet = promptPublication(newSet);
        findSize(newSet);
        if (newSet.size() == 0) {
            return newSet;
        }
        newSet = promptTime(newSet);
        findSize(newSet);
        if (newSet.size() == 0) {
            return newSet;
        }
        newSet = promptTitle(newSet);
        findSize(newSet);
        return newSet;
    }

    /**
     * Finds the size of your current set of articles and lets the user know
     * how many articles are under their current filter.
     * @param myArticles
     */
    public void findSize(Set<Article> myArticles) {
        int size = myArticles.size();
        if (size == 0) {
            System.out.println("Just to let you know, there are "
                    + "no articles under your filter.\n");
        } else {
            System.out.println("The number of articles under your "
                    + "current filters is: " + myArticles.size() + "\n");
        }
    }

    /**
     * Asks users questions for filtering by region
     * @param myArticles    current set of articles
     * @return  a set of articles under the filtered region
     */
    public Set<Article> promptRegions(Set<Article> myArticles) {
        System.out.print("The first filter is region. Please choose one of the following:\n" + "1. Africa\n"
                + "2. Americas\n" + "3. Eastern Mediterranean\n" + "4. Europe\n" + "5. South-East Asia\n"
                + "6. Western Pacific\n" + "7. All of the above\n");
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
        return addSet;
    }

    /**
     * Asks users questions for filtering by publication.
     * Will tell the user the top 10 current publications in their set.
     * @param myArticles    current set of articles
     * @return  a set of articles under the filtered publication
     */
    public Set<Article> promptPublication(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is publication. "
                + "Please choose one of the following:\n"
                + "1. Specific publication (cnn, cnbc, nbc, etc.)\n" + "2. All publications\n");
        int answer = input.nextInt();
        if (answer == 2) {
            return myArticles;
        } else if (answer == 1) {
            System.out.println("Please type in the publication "
                    + "you would like to filter. \n"
                    + "The current top 10 publications are: \n ");
            topTenPublications(myArticles);
            input.nextLine();
            String publication = input.nextLine();
            newSet = articleSorter.getArticlesByPublication(myArticles, publication);
        }
        return newSet;
    }

    /**
     * Tells the user the top 10 publishers in set of articles
     * @param myArticles    set of articles
     */
    public void topTenPublications(Set<Article> myArticles) {
        Map<String, Integer> publicationsToNumber = new HashMap<String, Integer>();
        for (Article x : myArticles) {
            String publisher = x.getPublisher();
            if (publicationsToNumber.containsKey(publisher)) {
                int num = publicationsToNumber.get(publisher);
                num += 1;
                publicationsToNumber.replace(publisher, num);
            } else {
                publicationsToNumber.put(publisher, 1);
            }
        }

        List<Map.Entry<String, Integer>> entryList = new LinkedList<Map.Entry<String, Integer>>(
                publicationsToNumber.entrySet());

        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        LinkedHashMap<String, Integer> solution = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : entryList) {
            solution.put(entry.getKey(), entry.getValue());
        }

        int i = 0;
        for (String pub : solution.keySet()) {
            i++;
            System.out.println(pub + ", " + publicationsToNumber.get(pub) + " articles");
            if (i == 10) {
                break;
            }
        }

    }

    /**
     * Asks users questions for filtering by time of publication.
     * Will tell user earliest time of publication if they choose specific date
     * @param myArticles    current set of articles
     * @return  a set of articles under the filtered time
     */
    public Set<Article> promptTime(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is time of publication. "
                + "Please choose one of the following:\n"
                + "1. Articles written on a specific date\n" 
                + "2. Articles from the past number of days\n"
                + "3. Articles released any time\n");
        int answer = input.nextInt();
        if (answer == 3) {
            return myArticles;
        } else if (answer == 1) {
            int earliestMonth = earliestMonth(myArticles);
            System.out.print(
                    "Please input the month and day in 2020 you would like articles for. "
                            + "This date should be pretty recent."
                            + "\nTo give you an idea of the earliest saved document, the earliest month is "
                            + earliestMonth + " and the earliest day in that month is "
                            + earliestDay(myArticles, earliestMonth) + "\nFirst enter the month (number):\n");
            int month = input.nextInt();
            System.out.print("Please enter the day (number):\n");
            int day = input.nextInt();
            newSet = articleSorter.getArticlesOnDay(myArticles, month, day, 2020);
        } else if (answer == 2) {
            System.out.print("Please input the past number of days you would like articles from:\n");
            int days = input.nextInt();
            newSet = articleSorter.getArticlesFromPastNumberOfDays(myArticles, days);
        }
        return newSet;
    }

    /**
     * @param myArticles    set of articles
     * @return  earliest month of publication from set of articles
     */
    public int earliestMonth(Set<Article> myArticles) {
        int min = 13;
        for (Article x : myArticles) {
            int month = x.getDate().getMonthValue();
            if (month < min) {
                min = month;
            }
        }
        return min;
    }

    /**
     * @param myArticles    set of articles
     * @param month         earliest month in set of articles
     * @return  earliest day of publication from set of articles in month
     */
    public int earliestDay(Set<Article> myArticles, int month) {
        int min = 100;
        for (Article x : myArticles) {
            int myMonth = x.getDate().getMonthValue();
            if (myMonth == month) {
                int day = x.getDate().getDayOfMonth();
                if (day < min) {
                    min = day;
                }
            }
        }
        return min;
    }

    /**
     * Asks users questions for filtering by title keyword.
     * 
     * @param myArticles current set of articles
     * @return a set of articles under the filtered title keyword
     */
    public Set<Article> promptTitle(Set<Article> myArticles) {
        Set<Article> newSet = new HashSet<Article>();
        System.out.print("The next filter is articles with a title "
                + "containing a certain word/order of words. "
                + "Please choose one of the following:\n" 
                + "1. Filter articles with title containing words\n"
                + "2. Get articles with all titles\n");
        int answer = input.nextInt();
        if (answer == 2) {
            return myArticles;
        } else if (answer == 1) {
            System.out.print("Please input the query you would like "
                    + "your articles' title to contain "
                    + "(e.g. donald trump, corona, happy): \n");
            input.nextLine();
            String words = input.nextLine();
            newSet = articleSorter.getArticlesWithTitleContaining(myArticles, words);
        }
        return newSet;
    }
}

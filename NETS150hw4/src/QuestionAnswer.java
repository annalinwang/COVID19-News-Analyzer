/**
 * A question class which the UI uses to add questions
 * @author Anna
 */
public class QuestionAnswer {
    private String question;
    
    /**
     * Creates a questionanswer object
     */
    public QuestionAnswer() {
        question = null;
    }
    
    /**
     * Creates a questionanswer object
     * @param q the question
     */
    public QuestionAnswer(String q) {
        question = q;
    }
    
    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

}

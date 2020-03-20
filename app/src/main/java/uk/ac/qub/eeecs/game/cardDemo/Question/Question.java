package uk.ac.qub.eeecs.game.cardDemo.Question;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class Question {

    // /////////////////////////////////////////////////////////////////////////
    // Properties:
    // /////////////////////////////////////////////////////////////////////////

    //Define the Card Name and Type
    private String questionID;
    private String question;
    private String answer;

    private static GameScreen gameScreen;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    public Question(String mQuestionID, String mQuestion, String mAnswer) {

        questionID = mQuestionID;
        question = mQuestion;
        answer = mAnswer;

    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    public String getQuestionID() {
        return questionID;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public static GameScreen getGameScreen() {
        return gameScreen;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////


    public static void setGameScreen(GameScreen gameScreen) {
        Question.gameScreen = gameScreen;
    }
}
package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.game.cardDemo.Enums.Colour;

/**
 * Defines a PopUp that displays a question to
 * the user. Includes an image and two buttons
 * (true and false). The User can answer the
 * question by selecting one of these buttons.
 * This action creates an InfoPopUp object to
 * inform the User of the outcome of their
 * decision
 *
 * Created By Niamh McCartney
 */
public class TrueFalseQuestionPopUp extends PopUp{

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the answer to the question
    private String answer;

    //Defines the message displayed if the User selects the incorrect answer
    private String incorrectMessage;

    //Defines the message displayed if the User selects the correct answer
    private String correctMessage;

    //Define the ID of the Pop-Up's image
    private int imageID;

    //Define the true button displayed on the Pop-Up
    private Button trueButton;

    //Define the false button displayed on the Pop-Up
    private Button falseButton;

    //Returns true if the User answers correctly
    private Boolean answerCorrect;

    //Returns true if the User has answered the question
    private Boolean questionAnswered;

    /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the TrueFalseQuestionPopUp object
     *
     * @param activity activity the pop up was called in
     * @param questionText question to be displayed
     * @param answer the answer to the question
     * @param imageBackgroundColour Background colour of the PopUp
     * @param imageID ID of the image in the drawable folder
     *
     * Created by Niamh McCartney
     */
    public TrueFalseQuestionPopUp(Activity activity, String questionText, String answer,
                                  Colour imageBackgroundColour, int imageID){
        super(activity, questionText, R.layout.question_window, imageBackgroundColour);

        //Define the parameters
        this.imageID = imageID;
        this.answer = answer;

        //Initialise the classes' properties
        trueButton = getDialog().findViewById(R.id.btn_dialog);
        falseButton = getDialog().findViewById(R.id.btn_dialog2);
        incorrectMessage = "Oops, that's Incorrect! Health points have been deducted!";
        correctMessage = "That's Correct! As a reward we've increased your health points!";
        answerCorrect = false;
        questionAnswered = false;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Displays a pop-up box with a question and a true and false button.
     * Depending on which button is pressed and whether it corresponds
     * to the answer parameter given, another pop-up box will display
     * informing the user whether they are correct or incorrect
     *
     * Created By Niamh McCartney
     */
    @Override
    public void showDialog() {
        //Sets the PopUp's properties
        setImageProperties(imageID);
        setTextProperties(R.id.text_dialog);
        setButtonProperties(R.id.btn_dialog, "true", R.drawable.green_btn);
        setButtonProperties(R.id.btn_dialog2, "false", R.drawable.green_btn);

        //Dictates what should happen when the Pop-Ups buttons are pressed
        onButtonClick();

        //Display the PopUp Dialog Box
        displayDialog();
    }

    /**
     * Displays a Pop-Up informing the
     * user they have answered the
     * question inCorrectly
     *
     * Created By Niamh McCartney
     */
    private void displayIncorrectDialog(){
        getDialog().dismiss();
        InfoPopUp IncorrectDialog = new InfoPopUp(getActivity(), incorrectMessage, Colour.WHITE,
                R.drawable.incorrect_symbol, "OK", R.drawable.green_btn);
        IncorrectDialog.showDialog();
    }

    /**
     * Displays a Pop-Up informing the
     * user they have answered the
     * question correctly
     *
     * Created By Niamh McCartney
     */
    private void displayCorrectDialog(){
        getDialog().dismiss();
        InfoPopUp correctDialog = new InfoPopUp(getActivity(), correctMessage, Colour.WHITE,
                R.drawable.correct_symbol, "OK", R.drawable.green_btn);
        correctDialog.showDialog();
    }

    /**
     * Dictates that when the User chooses
     * an answer a message will display
     * informing the User of whether they
     * have answered correctly or incorrectly
     *
     * Created By Niamh McCartney
     */
    @Override
    protected void onButtonClick() {
        if (answer == "true") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswerCorrect(true);
                    setQuestionAnswered(true);
                    displayCorrectDialog();
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswerCorrect(false);
                    setQuestionAnswered(true);
                    displayIncorrectDialog();
                }
            });
        }

        if (answer == "false") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswerCorrect(false);
                    setQuestionAnswered(true);
                    displayIncorrectDialog();
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswerCorrect(true);
                    setQuestionAnswered(true);
                    displayCorrectDialog();
                }
            });
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Returns true if User answers correctly
     * Created By Niamh McCartney
     */
    public Boolean getAnswerCorrect() {
        return answerCorrect;
    }

    /**
     * Returns true when User answers the question
     * Created By Niamh McCartney
     */
    public Boolean getQuestionAnswered() {
        return questionAnswered;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Setter to set answerCorrect Boolean
     * Created By Niamh McCartney
     */
    private void setAnswerCorrect(Boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    /**
     * Setter to set questionAnswered Boolean
     * Created By Niamh McCartney
     */
    public void setQuestionAnswered(Boolean questionAnswered) {
        this.questionAnswered = questionAnswered;
    }
}
package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.game.cardDemo.Colour.ColourEnum;

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

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public TrueFalseQuestionPopUp(Activity activity, String questionText, String answer, ColourEnum imageBackgroundColour, int imageID){
        super(activity, questionText, R.layout.question_window, imageBackgroundColour);

        //Define the parameters
        this.imageID = imageID;
        this.answer = answer;

        //Initialise the classes' properties
        trueButton = getDialog().findViewById(R.id.btn_dialog);
        falseButton = getDialog().findViewById(R.id.btn_dialog2);
        incorrectMessage = "That's Incorrect! Better luck next time!";
        correctMessage = "That's Correct! Well done!";
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Displays a pop-up box with a question
     * and a true and false button.
     * Depending on which button is pressed
     * and whether is corresponds to the
     * answer parameter given. Another pop-up
     * box will display informing the user whether
     * they are correct or incorrect
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
     */
    private void displayIncorrectDialog(){
        getDialog().dismiss();
        InfoPopUp IncorrectDialog = new InfoPopUp(getActivity(), incorrectMessage, ColourEnum.WHITE ,R.drawable.incorrect_symbol, "OK", R.drawable.green_btn);
        IncorrectDialog.showDialog();
    }

    /**
     * Displays a Pop-Up informing the
     * user they have answered the
     * question correctly
     */
    private void displayCorrectDialog(){
        getDialog().dismiss();
        InfoPopUp correctDialog = new InfoPopUp(getActivity(), correctMessage, ColourEnum.WHITE ,R.drawable.correct_symbol, "OK", R.drawable.green_btn);
        correctDialog.showDialog();
    }

    /**
     * Dictates that when the User chooses
     * an answer a message will display
     * informing the User of whether they
     * have answered correctly or incorrectly
     */
    @Override
    protected void onButtonClick() {
        if (answer == "true") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayCorrectDialog();
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayIncorrectDialog();
                }
            });
        }

        if (answer == "false") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayIncorrectDialog();
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayCorrectDialog();
                }
            });
        }
    }
}

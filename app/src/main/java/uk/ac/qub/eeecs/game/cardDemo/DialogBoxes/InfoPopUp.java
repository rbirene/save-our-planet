package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.game.cardDemo.Colour.ColourEnum;

/**
 * Defines a PopUp that displays an informative
 * message to the user. Includes an image and a
 * button which the User must press to dismiss
 * the PopUp
 *
 * Created By Niamh McCartney
 */
public class InfoPopUp extends PopUp{

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the text contained on the button image
    private String buttonText;

    //Define the ID of the Pop-Up's image
    private int imageID;

    //Define the ID of the button's image
    private int buttonImage;

    //Define the button displayed on the Pop-Up
    private Button dialogButton;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the InfoPopUp object
     *
     * @param activity activity the pop up was called in
     * @param message the message to be displayed by the PopUp
     * @param imageBackgroundColour Background colour of the PopUp
     * @param imageID ID of the PopUp image in the drawable folder
     * @param buttonText text to be displayed on the PopUp's button
     * @param buttonImageID ID of the button background image in the drawable folder
     *
     * Created by Niamh McCartney
     */
    public InfoPopUp(Activity activity, String message, ColourEnum imageBackgroundColour, int imageID, String buttonText, int buttonImageID){
        super(activity, message, R.layout.info_window, imageBackgroundColour);

        //Define the parameters
        this.imageID = imageID;
        this.buttonText = buttonText;
        this.buttonImage = buttonImageID;

        //Initialise the classes' properties
        dialogButton = getDialog().findViewById(R.id.btn_dialog);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Displays a pop-up box
     * containing an informative message
     *
     * Created By Niamh McCartney
     */
    @Override
    public void showDialog(){
        //sets the PopUp's properties
        setImageProperties(imageID);
        setTextProperties(R.id.text_dialog);
        setButtonProperties(R.id.btn_dialog, buttonText, buttonImage);

        //Dictates what should happen when the Pop-Ups button is pressed
        onButtonClick();

        //Display the PopUp Dialog Box
        displayDialog();
    }

    /**
     * Dictates that when the User chooses
     * the Pop-Up's Button the dialog should
     * be dismissed
     */
    @Override
    protected void onButtonClick() {
        //When button is pressed cancel the dialog box
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }
}
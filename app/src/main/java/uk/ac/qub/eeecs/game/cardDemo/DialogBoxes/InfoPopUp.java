package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.game.cardDemo.ColourEnum;

public class InfoPopUp extends PopUp{

    private ColourEnum backgroundColour;

    //Define the ID of the Pop-Up's image
    private int imageID;

    //Define the button displayed on the Pop-Up
    private Button dialogButton;

    //Define the text contained on the button image
    private String buttonText;

    //Define the ID of the button's image
    private int buttonImage;

    public InfoPopUp(Activity activity, String message, ColourEnum imageBackgroundColour, int imageID, String buttonText, int buttonImageID){
        super(activity, message, R.layout.info_window);

        //Define the parameters
        this.backgroundColour = imageBackgroundColour;
        this.imageID = imageID;
        this.buttonText = buttonText;
        this.buttonImage = buttonImageID;

        //Initialise the classes' properties
        dialogButton = getDialog().findViewById(R.id.btn_dialog);
    }

    /**
     * Displays a pop-up box
     * containing an informative message
     *
     * Created By Niamh McCartney
     */
    public void showDialog(){
        //sets the PopUp's properties
        setImageProperties(imageID, backgroundColour);
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
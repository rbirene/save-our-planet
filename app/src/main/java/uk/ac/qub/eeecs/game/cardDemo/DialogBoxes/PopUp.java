package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.game.cardDemo.Enums.Colour;

/**
 * Defines a PopUp that can be used to display information
 * to the User and allows User interaction/input. All PopUps
 * include a message, a background and a button for User
 * input
 *
 * Created By Niamh McCartney
 */
public abstract class PopUp {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Background colour of the PopUp object
    private Colour backgroundColour;

    //Activity the pop up was called in
    private Activity activity;

    //Message displayed by the PopUp object
    private String message;

    //Defines dialog object used to display the PopUp on screen
    private Dialog dialog;

    private int ui_flags;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the PopUp object
     *
     * @param activity activity the pop up was called in
     * @param msg message to be displayed by the PopUp
     * @param msg message to be displayed by the PopUp
     * @param imageBackgroundColour Background colour of the PopUp
     *
     * Created by Niamh McCartney
     */
    public PopUp(Activity activity, String msg, int popUpLayout, Colour imageBackgroundColour){
        //Define the parameters
        this.activity = activity;
        this.message = msg;
        this.backgroundColour = imageBackgroundColour;

        //Initialise the object's properties
        this.dialog = new Dialog(activity);

        //Sets up the Dialog's properties
        setDialogProperties();

        dialog.setContentView(popUpLayout);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Display the Pop-Up to the User
     *
     * Created By Niamh McCartney
     */
    protected void displayDialog(){
        // Set Dialog "not focusable" so nav bar still hiding
        dialog.getWindow().
                setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        // Set full-screen mode
        dialog.getWindow().getDecorView().setSystemUiVisibility(ui_flags);

        dialog.show();

        // Set dialog focusable to avoid touching outside
        dialog.getWindow().
                clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    /**
     * Set the Pop-Up's properties
     *
     * Created By Niamh McCartney
     */
    protected void setDialogProperties(){
        //Gets rid of title on Dialog box
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Prevents dialog box from being closed by pressing the back button
        dialog.setCancelable(false);

        // Flags for full-screen mode:
        ui_flags =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    }

    /**
     * Set the properties of the
     * Pop-Up's Image
     * @param imageID ID of the Image in the drawable folder
     *
     * Created By Niamh McCartney
     */
    protected void setImageProperties(int imageID){
        ImageView image = dialog.findViewById(R.id.a);
        //Get colour inputted by user and its code
        String colourCode = backgroundColour.getColourCode();
        //Use colour code to set background
        image.setBackgroundColor(Color.parseColor(colourCode));
        //Set dialog image
        image.setImageResource(imageID);
    }

    /**
     * Set the properties of the
     * Pop-Up's Text View
     * @param textID ID of the text in the xml file
     *
     *
     * Created By Niamh McCartney
     */
    protected void setTextProperties(int textID){
        TextView text = dialog.findViewById(R.id.text_dialog);
        //Set PopUp message
        text.setText(message);
    }

    /**
     * Set the properties of a
     * Pop-Up's Button
     * @param buttonID ID of the Pop-Up's button in the xml file
     * @param buttonText text on the button
     * @param buttonImageID ID of the button image in the drawable folder
     *
     * Created By Niamh McCartney
     */
    protected void setButtonProperties(int buttonID,  String buttonText, int buttonImageID){
        Button dialogButton = dialog.findViewById(buttonID);
        dialogButton.setText(buttonText);
        dialogButton.setBackgroundResource(buttonImageID);
    }

    /**
     * Dictates what the system do when the
     * User presses one of the Pop-Up's buttons
     */
    protected abstract void onButtonClick();

    /**
     * Method which contains the code to set
     * the properties of the Pop-Up box and
     * display it to the User
     */
    protected abstract void showDialog();

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Returns the Pop-Up's Activity
     *
     * Created By Niamh McCartney
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Returns the Pop-Up's message
     *
     * Created By Niamh McCartney
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the Pop-Up's dialog
     *
     * Created By Niamh McCartney
     */
    public Dialog getDialog() {
        return dialog;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Setter to set the Pop-Up's message
     *
     * Created By Niamh McCartney
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
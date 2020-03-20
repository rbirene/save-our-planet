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
import uk.ac.qub.eeecs.game.cardDemo.Colour;
import uk.ac.qub.eeecs.game.cardDemo.ColourEnum;

public abstract class PopUp {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    private Activity activity;

    private String message;

    private final Dialog dialog;

    private int ui_flags;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public PopUp(Activity activity, String msg, int popUpLayout){
        this.activity = activity;
        this.message = msg;

        this.dialog = new Dialog(activity);

        setDialogProperties();

        dialog.setContentView(popUpLayout);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

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

    protected void setImageProperties(int imageID, ColourEnum backgroundColour){
        ImageView image = dialog.findViewById(R.id.a);
        //Get colour inputted by user and get its code
        Colour colour01 = new Colour(backgroundColour);
        String colourCode = colour01.getColourCode();
        //Use colour code to set background
        image.setBackgroundColor(Color.parseColor(colourCode));
        //Set dialog image
        image.setImageResource(imageID);
    }

    protected void setTextProperties(int textID){
        //Set Dialog message
        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(message);
    }

    protected void setButtonProperties(int buttonID,  String buttonText, int buttonImageID){
        Button dialogButton = dialog.findViewById(buttonID);
        dialogButton.setText(buttonText);
        dialogButton.setBackgroundResource(buttonImageID);
    }

    protected abstract void onButtonClick();


    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    public Activity getActivity() {
        return activity;
    }

    public String getMessage() {
        return message;
    }

    public Dialog getDialog() {
        return dialog;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

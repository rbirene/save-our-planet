package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;
import uk.ac.qub.eeecs.gage.R;

public class CoinFlipDialog extends PopUp{

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Defines the name of the player who will go first
    private String firstPlayerName;

    //Define the button displayed on the Pop-Up
    private Button dialogButton;

    //Define the text displayed on the Pop-Up
    private TextView text;

    //Handler to access the UI thread
    private Handler handler;

    private Runnable mUpdateDialog;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public CoinFlipDialog(Activity activity, String message, final String firstPlayerName){
        super(activity, message, R.layout.coinflip_window);

        //Define the parameters
        this.firstPlayerName = firstPlayerName;

        //Initialise the classes' properties
        dialogButton = getDialog().findViewById(R.id.btn_dialog);
        text = getDialog().findViewById(R.id.text_dialog);
        handler = new Handler(Looper.getMainLooper());

        mUpdateDialog = new Runnable() {
            public void run() {
                //change text displayed in button
                dialogButton.setText("Let's Play!");

                //Set Dialog message displaying who takes the first turn
                text.setText(firstPlayerName + " gets to go first!");

            }

        };
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Displays a pop-up box that
     * allows the user to flip a coin.
     * When the coin is flipped an
     * informative message is displayed
     *
     * Created By Niamh McCartney
     */
    @Override
    public void showDialog(){
        //sets the PopUp's properties
        setTextProperties(R.id.text_dialog);
        setButtonProperties(R.id.btn_dialog, "Flip", R.drawable.green_btn);

        //Dictates what should happen when the Pop-Ups button is pressed
        onButtonClick();

        //Display the PopUp Dialog Box
        displayDialog();
    }

    /**
     * Dictates what occurs  when the User
     * clicks the button to flip the coin
     */
    @Override
    protected void onButtonClick() {
        //when the button is pressed perform the following actions
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If first turn has not been decided and the button is pressed then execute the following changes
                if(text.getText().equals("Flip the coin to decide who goes first")) {

                    //change the png image to a gif
                    GifImageView gifView = getDialog().findViewById(R.id.a);
                    gifView.setImageResource(R.drawable.coinflipgif);

                    /*
                     *After 2 seconds have elapsed then call the mUpdateDialog runnable
                     *This time delay allows time for the coin flip gif to update
                     */
                    int time = 2000;
                    handler.postDelayed(mUpdateDialog, time);

                }//When the first turn has been decided and the button is pressed again then cancel the dialog box
                else{
                    getDialog().dismiss();
                }

            }
        });
    }
}
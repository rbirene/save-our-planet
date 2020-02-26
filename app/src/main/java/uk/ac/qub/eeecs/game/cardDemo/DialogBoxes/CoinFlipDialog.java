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

public class CoinFlipDialog {

    /**
     * Displays a pop-up box
     * containing an informative message
     *
     * @param activity
     *
     * Created By Niamh McCartney
     */

    //Handler to access the UI thread
    private final Handler handler = new Handler(Looper.getMainLooper());

    public void showDialog(Activity activity, final String firstPlayerName){
        // Flags for full-screen mode:
        int ui_flags =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.coinflip_window);

        //define id of the Dialog's text
        final TextView text = dialog.findViewById(R.id.text_dialog);

        //define id of the Dialog's Button
        final Button dialogButton = dialog.findViewById(R.id.btn_dialog);


        //class Task extends TimerTask {
        final Runnable mUpdateDialog = new Runnable() {
            public void run() {
                //change text displayed in button
                dialogButton.setText("Let's Play!");

                //Set Dialog message displaying who takes the first turn
                text.setText(firstPlayerName + " gets to go first!");

            }

        };


        //when the button is pressed perform the following actions
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If first turn has not been decided and the button is pressed then execute the following changes
                if(text.getText().equals("Flip the coin to decide who goes first")) {

                    //change the png image to a gif
                    GifImageView gifView = dialog.findViewById(R.id.a);
                    gifView.setImageResource(R.drawable.coinflipgif);


                    /*
                    *After 2 seconds have elapsed then call the mUpdateDialog runnable
                    *This time delay allows time for the coin flip gif to update
                    */
                    int time = 2000;
                    handler.postDelayed(mUpdateDialog, time);

                }//When the first turn has been decided and the button is pressed again then cancel the dialog box
                else{ dialog.dismiss();}

            }
        });

        // Set alertDialog "not focusable" so nav bar still hiding
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

}
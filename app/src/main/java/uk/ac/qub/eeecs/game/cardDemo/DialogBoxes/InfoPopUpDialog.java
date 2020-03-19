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

public class InfoPopUpDialog{

    /**
     * Displays a pop-up box
     * containing an informative message
     *
     * @param activity
     * @param msg Text for Dialog Box
     *
     * Created By Niamh McCartney
     */

    public void showDialog(Activity activity, String msg, ColourEnum backgroundColour, int imageID, String buttonText, int buttonImage){
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
        dialog.setContentView(R.layout.info_window);

        ImageView image = dialog.findViewById(R.id.a);
        //Get colour inputted by user and get its code
        Colour colour01 = new Colour(backgroundColour);
        String colourCode = colour01.getColourCode();
        //Use colour code to set background
        image.setBackgroundColor(Color.parseColor(colourCode));
        //Set dialog image
        image.setImageResource(imageID);

        //Set Dialog message
        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        dialogButton.setText(buttonText);
        dialogButton.setBackgroundResource(buttonImage);

        //When button is pressed cancel the dialog box
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
package uk.ac.qub.eeecs.game.cardDemo;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.qub.eeecs.gage.R;

public class IncorrectAnswerDialog {

    /**
     * Displays a pop-up box
     * with a unsuccessful message
     *
     * @param activity
     * @param msg Text for pop-up Dialog Box
     *
     * Created By Niamh McCartney
     */

    public void showDialog(Activity activity, String msg){
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
        dialog.setContentView(R.layout.incorrect_answer_window);

        //Set Dialog message
        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        //When button is pressed cancel the dialog box
        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
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

package uk.ac.qub.eeecs.game.cardDemo;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.qub.eeecs.gage.R;

public class QuestionPopUpDialog {

    /**
     * Return a string that holds the corresponding label for the specified
     * type of touch event.
     *
     * @param activity
     * @param question Text for Dialog Box
     * @param answer   true of false
     *                 <p>
     *                 Created By Niamh McCartney
     */

    public void showDialog(Activity activity, String question, String answer) {
        final Activity mActivity = activity;
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
        dialog.setContentView(R.layout.question_window);

        //Set Dialog message
        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(question);

        //When button is pressed cancel the dialog box
        Button trueButton = dialog.findViewById(R.id.btn_dialog);

        //When button is pressed cancel the dialog box
        Button falseButton = dialog.findViewById(R.id.btn_dialog2);


        if (answer == "true") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    CorrectAnswerDialog correctDialog = new CorrectAnswerDialog();
                    correctDialog.showDialog(mActivity, "That's Correct! Well done!");
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    IncorrectAnswerDialog incorrectDialog = new IncorrectAnswerDialog();
                    incorrectDialog.showDialog(mActivity, "That's Incorrect! Better luck next time!");
                }
            });
        }

        if (answer == "false") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    IncorrectAnswerDialog incorrectDialog = new IncorrectAnswerDialog();
                    incorrectDialog.showDialog(mActivity, "That's Incorrect! Better luck next time!");
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    CorrectAnswerDialog correctDialog = new CorrectAnswerDialog();
                    correctDialog.showDialog(mActivity, "That's Correct! Well done!");
                }
            });
        }


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

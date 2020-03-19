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

public class TrueFalseQuestionPopUpDialog {

    /**
     * Displays a pop-up box with a question
     * and a true and false button.
     * Depending on which button is pressed
     * and whether is corresponds to the
     * answer parameter given. Another pop-up
     * box will display informing the user whether
     * they are correct or incorrect
     *
     * @param activity
     * @param question Text for Dialog Box
     * @param answer   answer to question (true of false)
     *
     * Created By Niamh McCartney
     */

    public void showDialog(Activity activity, String question, String answer, ColourEnum backgroundColour, int imageID) {
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

        ImageView image = dialog.findViewById(R.id.a);
        //Get colour inputted by user and get its code
        Colour colour01 = new Colour(backgroundColour);
        String colourCode = colour01.getColourCode();
        //Use colour code to set background
        image.setBackgroundColor(Color.parseColor(colourCode));
        //Set dialog image
        image.setImageResource(imageID);

        TextView text = dialog.findViewById(R.id.text_dialog);
        //Set Dialog message
        text.setText(question);

        //Define id of the Dialog's true Button
        Button trueButton = dialog.findViewById(R.id.btn_dialog);

        //Define id of the Dialog's false Button
        Button falseButton = dialog.findViewById(R.id.btn_dialog2);

        if (answer == "true") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    InfoPopUpDialog correctDialog = new InfoPopUpDialog();
                    correctDialog.showDialog(mActivity, "That's Correct! Well done!", ColourEnum.WHITE ,R.drawable.correct_symbol, "OK", R.drawable.green_btn);
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    InfoPopUpDialog IncorrectDialog = new InfoPopUpDialog();
                    IncorrectDialog.showDialog(mActivity, "That's Incorrect! Better luck next time!", ColourEnum.WHITE ,R.drawable.incorrect_symbol, "OK", R.drawable.green_btn);
                }
            });
        }

        if (answer == "false") {
            trueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    InfoPopUpDialog IncorrectDialog = new InfoPopUpDialog();
                    IncorrectDialog.showDialog(mActivity, "That's Incorrect! Better luck next time!", ColourEnum.WHITE ,R.drawable.incorrect_symbol, "OK", R.drawable.green_btn);
                }
            });
            falseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    InfoPopUpDialog correctDialog = new InfoPopUpDialog();
                    correctDialog.showDialog(mActivity, "That's Correct! Well done!", ColourEnum.WHITE ,R.drawable.correct_symbol, "OK", R.drawable.green_btn);
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

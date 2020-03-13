package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.game.cardDemo.Colour;
import uk.ac.qub.eeecs.game.cardDemo.ColourEnum;

public class formDialog {

    //List of all the previous User names
    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    //Name of the User that will be inputted in this form
    private String userName;

    /**
     * Displays a pop-up box
     * containing an informative message
     *
     * @param activity
     * @param msg Text for Dialog Box
     * @param imageBackgroundColour Colour of Image background
     * @param imageID id of image in the drawable file
     * @param buttonImage id of button image in the drawable file
     *
     * Created By Niamh McCartney
     */

    public void showDialog(Activity activity, String msg, ColourEnum imageBackgroundColour, int imageID, int buttonImage){
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
        dialog.setContentView(R.layout.form_window);

        ImageView image = dialog.findViewById(R.id.a);
        //Get colour inputted by user and get its code
        Colour colour01 = new Colour(imageBackgroundColour);
        String colourCode = colour01.getColourCode();
        //Use colour code to set background
        image.setBackgroundColor(Color.parseColor(colourCode));
        //Set dialog image
        image.setImageResource(imageID);

        list.add("Lauren");
        list.add("Dave");
        list.add("Tom");
        list.add("Sarah");

        //define id of dialog's ListView
        final ListView listView = dialog.findViewById(R.id.list);

        //define id of dialog's EditText
        final EditText textInput = dialog.findViewById(R.id.text_input);

        // Create an ArrayAdapter from List
        arrayAdapter = new ArrayAdapter<>
                (activity, android.R.layout.simple_list_item_1, list);

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);

        //prevents the user from selecting multiple options
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //highlights selected option
        listView.setSelector(android.R.color.darker_gray);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<? > arg0, View view, int position, long id) {
                userName = listView.getItemAtPosition(position).toString();
                Log.d("textInputbun", "" + userName);

            }

        });

        //define id of dialog's text
        TextView text = dialog.findViewById(R.id.text_dialog);
        //set the text contained in the dialog
        text.setText(msg);

        //define id of the first dialog button
        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        //set the text of the dialog button
        dialogButton.setText("Add");
        //set the background image of the dialog button
        dialogButton.setBackgroundResource(buttonImage);

        //when dialog button is clicked take in the name inputted by the user
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = textInput.getText().toString();
                Log.d("textInputbun", "" + textInput.getText());
                //dismiss the dialog
                dialog.dismiss();
            }
        });


        //define id of the second dialog button
        Button selectButton = dialog.findViewById(R.id.selectBtn);
        //set the text of the dialog button
        selectButton.setText("Select");
        //set the background image of the dialog button
        selectButton.setBackgroundResource(buttonImage);

        //when dialog button is clicked take in the name selected by the user
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("textInputbun", "" + textInput.getText());
                //dismiss the dialog
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

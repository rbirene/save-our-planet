package uk.ac.qub.eeecs.game.cardDemo.DialogBoxes;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.game.cardDemo.Colour.ColourEnum;
import uk.ac.qub.eeecs.game.cardDemo.User.User;

public class FormPopUp extends PopUp{

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the game that calls the Pop-Up
    private Game aGame;

    //List of all saved Users
    private ArrayList<User> users;

    //List of all saved Users' names
    private ArrayList<String> list = new ArrayList<>();

    //Create an ArrayAdapter List
    private ArrayAdapter<String> arrayAdapter;

    //Name of the User that will be inputted in this form
    private String userNameInputted;

    //Name of the User that is selected from the list
    private String userNameSelected;

    //Defines the position of the selected User in the UserStore
    private int userPos;

    //Define the ID of the Pop-Up's image
    private int imageID;

    //Define the ID of the button's image
    private int buttonImage;

    //Define Pop-Up's buttons
    private Button addButton;
    private Button selectButton;

    //Define the dialog's ListView
    private ListView listView;

    //Define the dialog's EditText
    private EditText textInput;

    //Define the dialog's TextView
    private TextView text;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public FormPopUp(Activity activity, final Game game, final String msg, ColourEnum imageBackgroundColour, int imageID, int buttonImageID){
        super(activity, msg, R.layout.form_window, imageBackgroundColour);

        //Define the parameters
        this.aGame = game;
        this.imageID = imageID;
        this.buttonImage = buttonImageID;

        //Initialise the classes' properties
        users = aGame.getUserStore().getUserList();
        text = getDialog().findViewById(R.id.text_dialog);
        addButton = getDialog().findViewById(R.id.btn_dialog);
        selectButton = getDialog().findViewById(R.id.selectBtn);
        listView = getDialog().findViewById(R.id.list);
        textInput = getDialog().findViewById(R.id.text_input);
        arrayAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_list_item_1, list);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Displays a pop-up box
     * containing an informative message
     *
     * Created By Niamh McCartney
     */
    @Override
    public void showDialog(){
        //sets the PopUp's properties
        setImageProperties(imageID);
        setTextProperties(R.id.text_dialog);
        setButtonProperties(R.id.btn_dialog, "Add", buttonImage);
        setButtonProperties(R.id.selectBtn, "Select", buttonImage);
        setListProperties();

        for(int i = 0; i<users.size(); i++){
            list.add(users.get(i).getName());
        }

        onButtonClick();

        //Display the PopUp Dialog Box
        displayDialog();
    }

    /**
     * Set the properties of the
     * Pop-Up's List View
     *
     * Created By Niamh McCartney
     */
    private void setListProperties(){
        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);
        //prevents the user from selecting multiple options
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //highlights selected option
        listView.setSelector(android.R.color.darker_gray);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<? > arg0, View view, int position, long id) {
                userNameSelected = listView.getItemAtPosition(position).toString();
            }

        });
    }

    /**
     * Dictates what the system do when the
     * User presses one of the Pop-Up's buttons
     */
    @Override
    protected void onButtonClick() {
        //when dialog button is clicked take in the name inputted by the user
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameInputted = textInput.getText().toString();
                if (userNameInputted != null && !userNameInputted.equals("")) {
                    User user = new User(userNameInputted, 0, 0);
                    if (aGame.getUserStore().checkUserStore(userNameInputted) == -1) {
                        users.add(user);
                        aGame.getCurrentUser().setUser(user);
                        aGame.getUserStore().saveUsers();

                        //dismiss the dialog
                        getDialog().dismiss();

                    }else{text.setText(getMessage() + ". That Name is already taken");

                    }
                }
            }
        });


        //when dialog button is clicked take in the name selected by the user
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameSelected != null) {
                    userPos = aGame.getUserStore().checkUserStore(userNameSelected);
                    User user = aGame.getUserStore().getUserList().get(userPos);
                    aGame.getCurrentUser().setUser(user);

                    //dismiss the dialog
                    getDialog().dismiss();
                }
            }
        });
    }
}
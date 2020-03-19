package uk.ac.qub.eeecs.game.cardDemo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;

public class UserStore {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    private FileIO mFileIO;

    //Define the Game that the object is created in
    private Game mGame;

    //Define the list of Users
    private ArrayList<User> userList;

    // Define the shared preferences and its editor
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // Define the context
    private Context context;

    // Define the GSON object
    private Gson gson;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public UserStore(Game game, Context cxt){
        // Define the parameters
        this.context = cxt;
        this.mGame = game;

        // Initialise the UserStore properties
        this.userList = new ArrayList<>();
        this.mFileIO = mGame.getFileIO();
        this.sp = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        this.editor = sp.edit();
        this.gson = new Gson();

        // Load saved User objects
        loadUserObjects();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Load in the list of saved User
     * objects using shared preferences
     *
     * Created By Niamh McCartney
     */
    private void loadUserObjects() {
        // Load in the list of User objects
        Type type = new TypeToken<List<User>>(){}.getType();
        String loadedUsersList = sp.getString("UserData", "");
        ArrayList<User> users = gson.fromJson(loadedUsersList, type);

        // Add each User to the userList
        if(users != null) {
            for (int idx = 0; idx < users.size(); idx++) {
                User user = users.get(idx);
                addUser(user);
            }
        }
    }

    /**
     * Add a User to the list of Users
     * @param user user to add to list
     *
     * Created By Niamh McCartney
     */
    public void addUser(User user){
        userList.add(user);
    }

    /**
     * Save the list of Users
     * using shared preferences
     *
     * Created By Niamh McCartney
     */
    public void saveUsers(){
        String accountsJson = gson.toJson(userList);
        editor.putString("UserData", accountsJson);
        editor.apply();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Return the List of User objects
     *
     * Created By Niamh McCartney
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Setter to set the List
     * of User objects
     *
     * Created By Niamh McCartney
     */
    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
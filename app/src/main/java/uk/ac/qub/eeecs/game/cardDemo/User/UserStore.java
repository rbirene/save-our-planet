package uk.ac.qub.eeecs.game.cardDemo.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;

/**
 * Contains list of the Game's Users
 * Provides methods to access User
 * objects and makes use of Shared
 * Preferences to load and save Users
 * and their information
 *
 * Created By Niamh McCartney
 */
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

    //Name used to identify data when it's saved and loaded
    private String dataStorageName;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the UserStore object and load
     * the Users from Shared Preferences
     *
     * @param game Game the UserStore object belongs to
     * @param cxt Context of the Game the UserStore is in
     * @param uniqueStorageName name to store the data under
     *
     * Created by Niamh McCartney
     */
    public UserStore(Game game, Context cxt, String uniqueStorageName){
        // Define the parameters
        this.context = cxt;
        this.mGame = game;
        this.dataStorageName = uniqueStorageName;

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
        // Load in the list of User objects as a JSON object using Shared Preferences
        String loadedUsersList = sp.getString(dataStorageName, "");

        //Get type of object to covert JSON string to
        Type type = new TypeToken<List<User>>(){}.getType();

        //Convert list of JSON objects to list of User object using GSON
        ArrayList<User> users = gson.fromJson(loadedUsersList, type);

        // Add each User to the userList
        if(users != null) {
            for (int idx = 0; idx < users.size(); idx++) {
                User user = users.get(idx);
                // addUser(user);
            }
        }
    }

    /**
     * Add a User to the list of Users if User with
     * the same name does not already exist
     * @param user user to add to list
     *
     * Created By Niamh McCartney
     */
    public void addUser(User user){
        if(checkUserStore(user.getName()) == -1){
            userList.add(user);
        }else{
            throw new RuntimeException(
                    "UserStore.addUser: duplicate User error [" +
                            "Tried to input User with a name that's already taken" + "]");
        }
    }

    /**
     * Save the list of Users
     * using shared preferences
     *
     * Created By Niamh McCartney
     */
    public void saveUsers(){
        String userJsonString = gson.toJson(userList);
        editor.putString(dataStorageName, userJsonString);
        editor.apply();
    }

    /**
     * Check the UserStore to see if any Users have a
     * given name. If true return the position of the
     * User in the UserStore. If false return -1.
     *
     * @param userName Name to check UserStore for
     *
     * Created By Niamh McCartney
     */
    public int checkUserStore(String userName){
        for(int i = 0; i<getNumOfUsers(); i++){
            String name = userList.get(i).getName();
            if(name.equals(userName)){
                return i;
            }
        }
        return -1;
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

    /**
     * Returns the number of Users
     * in the UserStore
     *
     * Created By Niamh McCartney
     */
    public int getNumOfUsers() {
        return userList.size();
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
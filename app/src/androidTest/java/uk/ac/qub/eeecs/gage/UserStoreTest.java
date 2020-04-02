package uk.ac.qub.eeecs.gage;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import uk.ac.qub.eeecs.game.cardDemo.User.User;
import uk.ac.qub.eeecs.game.cardDemo.User.UserStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserStoreTest {

    private TestGame aGame;
    private ArrayList<User> aUserList;
    private UserStore aUserStore;
    private User user01;
    private User user02;
    private User user03;

    @Before
    public void SetUp() {
        aGame = new TestGame(420, 360);

        user01 = new User("Nathan", 2, 1);
        user02 = new User("Becky",3, 0);
        user03 = new User("Kasia", 0,0);

        aUserList = new ArrayList<>();

        aUserList.add(user01);
        aUserList.add(user02);
        aUserList.add(user03);

        aUserStore = aGame.getUserStore();
        ArrayList<User> users = new ArrayList<>();
        aUserStore.setUserList(users);
        //aUserStore.setUserList(aUserList);
    }

    @Test
    public void getNumOfUsers_zeroUsers(){
        int size = aUserStore.getNumOfUsers();
        assertEquals(0, size);
    }

    @Test
    public void getNumOfUsers_moreThanZeroUsers(){
        aUserStore.addUser(user01);
        int size = aUserStore.getNumOfUsers();
        assertEquals(1, size);
    }

    @Test
    public void getUserList_ContainsCorrectUsers(){
        aUserStore.addUser(user01);
        aUserStore.addUser(user02);
        aUserStore.addUser(user03);
        int size = aUserStore.getNumOfUsers();

        User user1 = aUserStore.getUserList().get(size-3);
        User user2 = aUserStore.getUserList().get(size-2);
        User user3 = aUserStore.getUserList().get(size-1);

        Boolean containsCorrectUsers = user1==user01 && user2==user02 && user3==user03;
        assertTrue(containsCorrectUsers);
    }

    @Test
    public void setUserList_ContainsCorrectUsers(){
        aUserStore.setUserList(aUserList);

        Boolean containsCorrectUsers = true;

        for(int i = 0; i<aUserStore.getNumOfUsers(); i++){
            if(aUserStore.getUserList().get(i) != aUserList.get(i)){
                containsCorrectUsers = false;
            }
        }
        assertTrue(containsCorrectUsers);
    }

    @Test
    public void addUser_UserNameNotTaken_UserStoreSizeIncreases(){
        int userStoreSize = aUserStore.getNumOfUsers();

        User newUser = new User("Anthony", 4, 6);
        aUserStore.addUser(newUser);

        int newUserStoreSize = aUserStore.getNumOfUsers();
        assertEquals(userStoreSize+1, newUserStoreSize);
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void addUser_UserNameTaken_ExceptionThrown(){
        //Create new User and add them to store
        User newUser = new User("Anthony", 4, 6);
        aUserStore.addUser(newUser);

        //Attempt to add user again
        aUserStore.addUser(newUser);
    }

    @Test
    public void checkUserStore_NameNotInStore(){
        String newUserName = "randomName123";
        int position = aUserStore.checkUserStore(newUserName);
        assertEquals(-1, position);
    }

    @Test
    public void checkUserStore_NameInStore(){
        String newUserName = "Anthony";
        User newUser = new User(newUserName, 4, 6);
        aUserStore.addUser(newUser);
        int expectedPosition = aUserStore.getNumOfUsers()-1;
        int position =  aUserStore.checkUserStore(newUserName);
        assertEquals(expectedPosition, position);
    }

    @Test
    public void saveUsersLoadUsers_NewStoreContainsCorrectUsers(){
        //Set and save contents of UserStore
        aUserStore.setUserList(aUserList);
        aUserStore.saveUsers();
        //Create new UserStore with same data storage as previous store
        UserStore newUserStore = new UserStore(aGame, aGame.context, aGame.uniqueUserStorageName);

        Boolean containsCorrectUsers = true;

        //Check previous store saved the data correctly and new store loaded the data correctly
        for(int i = 0; i<newUserStore.getNumOfUsers(); i++){
            String userStoreUser = newUserStore.getUserList().get(i).getName();
            String userListUser = aUserStore.getUserList().get(i).getName();
            if(!userStoreUser.equals(userListUser)){
                containsCorrectUsers = false;
            }
        }
        //Returns true if loaded data matches saved data
        assertTrue(containsCorrectUsers);
    }

    @Test
    public void loadUsers_NoUsersToLoad_StoreSizeIsZero(){
        UserStore newUserStore = new UserStore(aGame, aGame.context, "newDataStorage");
        assertTrue(newUserStore.getNumOfUsers() == 0);
    }
}
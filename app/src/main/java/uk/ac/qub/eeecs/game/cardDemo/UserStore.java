package uk.ac.qub.eeecs.game.cardDemo;

import java.util.ArrayList;

public class UserStore {

    private ArrayList<User> userList;

    public UserStore(){
        userList = new ArrayList<>();
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user){
        userList.add(user);
    }
}

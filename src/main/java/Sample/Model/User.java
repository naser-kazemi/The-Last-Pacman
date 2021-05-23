package Sample.Model;

import java.util.ArrayList;

public class User {

    protected static ArrayList<User> users = new ArrayList<>();
    protected static ArrayList<String> usernames = new ArrayList<>();


    protected String username;
    protected String password;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        users.add(this);
        usernames.add(username);
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username))
                return user;
        }

        return null;
    }

    public static ArrayList<String> getUsernames() {
        return usernames;
    }
}

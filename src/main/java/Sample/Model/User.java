package Sample.Model;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
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
        try {
            FileWriter jsonWriter = new FileWriter("src/main/resources/Sample/Data/Users.json");
            jsonWriter.write(new Gson().toJson(users));
            jsonWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void setUsers(ArrayList<User> users, ArrayList<String> usernames) {
        User.users.addAll(users);
        User.usernames.addAll(usernames);
    }
}

package sample.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sample.view.LoginPage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class User {

    protected static ArrayList<User> users = new ArrayList<>();
    protected static ArrayList<String> usernames = new ArrayList<>();


    protected ArrayList<Map> maps = new ArrayList<>();
    protected String username;
    protected String password;
    protected Pacman pacman;
    protected boolean hasSaved;


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

    public static void updateUsers() {
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

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public Pacman getPacman() {
        return this.pacman;
    }

    public void readPacman() throws IOException {
        if (hasSaved) {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Sample/Data/savedGames/" +
                    LoginPage.getCurrentUser().getUsername() + ".json")));
            this.pacman = new Gson().fromJson(data, new TypeToken<Pacman>() {
            }.getType());
        }
    }

    public void setHasSaved(boolean hasSaved) {
        this.hasSaved = hasSaved;
    }

    public boolean getHasSaved() {
        return hasSaved;
    }
}

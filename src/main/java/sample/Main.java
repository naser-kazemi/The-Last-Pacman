package sample;

import sample.controller.AccountSettingPageController;
import sample.model.Score;
import sample.model.User;
import sample.view.WelcomePage;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static sample.view.MainPage.isPlayAsGuest;


public class Main {


    public static void initiateProgram() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/sample/data/Users.json")));
            ArrayList<User> users = new Gson().fromJson(data, new TypeToken<ArrayList<User>>(){}.getType());
            ArrayList<String> usernames = new ArrayList<>();
            if (users != null) {
                for (User user : users) {
                    usernames.add(user.getUsername());
                    System.out.println(user.getUsername());
                }
                User.setUsers(users, usernames);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/sample/data/Scores.json")));
            ArrayList<Score> scores = new Gson().fromJson(data, new TypeToken<ArrayList<Score>>(){}.getType());
            Score.setScores(scores);
        }catch(IOException e) {
            e.printStackTrace();
        }
        while(User.getUsernames().contains("******Guest******"))
            User.deleteUser(User.getUserByUsername("******Guest******"));
    }



    public static void main(String[] args) {
        initiateProgram();
        new WelcomePage().run(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (isPlayAsGuest) {
                    while (User.getUsernames().contains("******Guest******"))
                        AccountSettingPageController.getInstance().deleteAccount();
                }
                System.exit(0);
            }
        }));
    }

}

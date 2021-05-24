package Sample;

import Sample.Model.User;
import Sample.View.WelcomePage;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {


    public static void initiateProgram() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Sample/Data/Users.json")));
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
    }



    public static void main(String[] args) {
        initiateProgram();
        new WelcomePage().run(args);
    }

}

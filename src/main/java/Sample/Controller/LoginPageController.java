package Sample.Controller;

import Sample.Model.User;
import Sample.View.LoginPage;
import Sample.View.MainPage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPageController {

    private static LoginPageController instance;

    private LoginPageController() {}

    public static LoginPageController getInstance() {
        if (instance == null)
            instance = new LoginPageController();
        return instance;
    }


    public abstract static class Handler {

        protected Handler nextHandler = null;

        public void setNextHandler(Handler nextHandler) {
            this.nextHandler = nextHandler;
        }
        public String handle(String username, String password) {
            return null;
        }

        public static Matcher getMatcher(String input, String regex) {
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(input);
        }
    }



    public static class IsUsernameValidHandler extends Handler {
        @Override
        public String handle(String username, String password) {
            Matcher matcher = getMatcher(username, "\\S+");
            if (matcher.matches()) {
                if (nextHandler != null)
                    return nextHandler.handle(username, password);
                else
                    return "Login Successful!";
            }

            else
                return "Invalid Username!";
        }
    }


    public static class DoesUsernameExistHandler extends Handler {
        @Override
        public String handle(String username, String password) {
            ArrayList<String> usernames = User.getUsernames();
            for (String str : usernames) {
                if (str.equals(username)) {
                    if (nextHandler != null)
                        return nextHandler.handle(username, password);
                    else
                        return "Login Successful!";
                }
            }
            return "Username Does Not Exist!";
        }
    }


    public static class IsPasswordCorrectHandler extends Handler {
        @Override
        public String handle(String username, String password) {
            User user = User.getUserByUsername(username);
            if (user.getPassword().equals(password)) {
                if (nextHandler != null)
                    return nextHandler.handle(username, password);
                else
                    return "Login Successful!";
            }
            else
                return "Incorrect Password";
        }
    }




    public void login(String username, String password) throws InterruptedException {
        Handler isUsernameValidHandler = new IsUsernameValidHandler();
        Handler doesUsernameExistHandler = new DoesUsernameExistHandler();
        Handler isPasswordCorrectHandler = new IsPasswordCorrectHandler();
        isUsernameValidHandler.setNextHandler(doesUsernameExistHandler);
        doesUsernameExistHandler.setNextHandler(isPasswordCorrectHandler);
        String result = isUsernameValidHandler.handle(username, password);
        if (result.equals("Login Successful!")) {
            MainPage.setCurrentUser(User.getUserByUsername(username));
            LoginPage.setCurrentUser(User.getUserByUsername(username));
            System.out.println(User.getUserByUsername(username).getUsername());
        }

        LoginPage.setMessage(result);
    }



}

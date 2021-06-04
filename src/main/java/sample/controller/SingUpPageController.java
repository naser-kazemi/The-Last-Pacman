package sample.controller;

import sample.model.User;
import sample.view.SignUpPage;
import sample.view.SignUpSuccessfulPage;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingUpPageController {

    private static SingUpPageController instance;



    private SingUpPageController() {}


    public static SingUpPageController getInstance() {
        if (instance == null)
            instance = new SingUpPageController();
        return instance;
    }

    public abstract static class Handler {

        protected Handler nextHandler = null;

        public void setNextHandler(Handler nextHandler) {
            this.nextHandler = nextHandler;
        }

        public String handle(String username, String password, String confirmPassword) {
            return null;
        }

        public static Matcher getMatcher(String input, String regex) {
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(input);
        }
    }

    public static class IsUsernameValidHandler extends Handler {
        @Override
        public String handle(String username, String password, String confirmPassword) {
            Matcher matcher = getMatcher(username, "\\S+");
            if (matcher.matches()) {
                if (nextHandler != null)
                    return nextHandler.handle(username, password, confirmPassword);
                else
                    return "Sign Up Successful!";
            }

            else
                return "Invalid Username!";
        }
    }


    public static class IsPasswordValidHandler extends Handler {
        @Override
        public String handle(String username, String password, String confirmPassword) {
            Matcher matcher = getMatcher(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$^#~!%*?&])[A-Za-z\\d@$^#~!%*?&]{8,32}$");
            if (matcher.matches()) {
                if (nextHandler != null)
                    return nextHandler.handle(username, password, confirmPassword);
                else
                    return "Sign Up Successful!";
            }

            else
                return """
                        Invalid Password!
                        Password must contain from 8 to 32 characters; at least
                        one uppercase letter, one lowercase letter,
                        one number and one special character""";
        }
    }


    public static class ConfirmPasswordHandler extends Handler {

        @Override
        public String handle(String username, String password, String confirmPassword) {
            if (password.equals(confirmPassword))
                if (nextHandler != null)
                    return nextHandler.handle(username, password, confirmPassword);
                else
                    return "Sign Up Successful!";
            else
                return "Passwords do not match!";
        }
    }



    public static class RepeatedUsernameHandler extends Handler {
        @Override
        public String handle(String username, String password, String confirmPassword) {
            ArrayList<String> usernames = User.getUsernames();
            int counter = 0;
            for (String str : usernames)
                if (str.equals(username)) counter++;
            if (counter == 0)
                if (nextHandler != null)
                    return nextHandler.handle(username, password, confirmPassword);
                else
                    return "Sign Up Successful!";
            else
                return "Username already exists!";
        }
    }



        public void signUp(String username, String password, String confirmPassword) throws Exception {
        Handler isUsernameValidHandler = new IsUsernameValidHandler();
        Handler repeatedUsernameHandler = new RepeatedUsernameHandler();
        Handler isPasswordValidHandler = new IsPasswordValidHandler();
        Handler confirmPasswordHandler = new ConfirmPasswordHandler();
        isUsernameValidHandler.setNextHandler(repeatedUsernameHandler);
        repeatedUsernameHandler.setNextHandler(isPasswordValidHandler);
        isPasswordValidHandler.setNextHandler(confirmPasswordHandler);
        String result = isUsernameValidHandler.handle(username, password, confirmPassword);
        if (result.equals("Sign Up Successful!")) {
            new User(username, password);
            new SignUpSuccessfulPage().start(SignUpPage.getMainStage());
        }

        SignUpPage.setMessage(result);
    }




}

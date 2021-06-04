package sample.controller;

import sample.model.User;
import sample.view.AccountSettingPage;
import sample.view.LoginPage;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountSettingPageController {

    private static AccountSettingPageController instance;
    protected static User currentUser;


    private AccountSettingPageController() {}

    public static AccountSettingPageController getInstance() {
        if (instance == null)
            instance = new AccountSettingPageController();
        return instance;
    }




    public abstract static class Handler {

        protected Handler nextHandler = null;

        public void setNextHandler(Handler nextHandler) {
            this.nextHandler = nextHandler;
        }

        public String handle(String oldPassword, String newPassword, String confirmNewPassword) {
            return null;
        }

        public static Matcher getMatcher(String input, String regex) {
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(input);
        }
    }


    public static class IsPasswordCorrectHandler extends Handler {
        @Override
        public String handle(String oldPassword, String newPassword,String confirmNewPassword) {
            if (currentUser.getPassword().equals(oldPassword)) {
                if (nextHandler != null)
                    return nextHandler.handle(oldPassword, newPassword, confirmNewPassword);
                else
                    return "Password Changed!";
            }
            else
                return "Incorrect Password";
        }
    }


    public static class IsPasswordValidHandler extends Handler {
        @Override
        public String handle(String oldPassword, String newPassword,String confirmNewPassword) {
            Matcher matcher = getMatcher(newPassword, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$^#~!%*?&])[A-Za-z\\d@$^#~!%*?&]{8,32}$");
            if (matcher.matches()) {
                if (nextHandler != null)
                    return nextHandler.handle(oldPassword, newPassword, confirmNewPassword);
                else
                    return "Password Changed!";
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
        public String handle(String oldPassword, String newPassword,String confirmNewPassword) {
            if (newPassword.equals(confirmNewPassword))
                if (nextHandler != null)
                    return nextHandler.handle(oldPassword, newPassword, confirmNewPassword);
                else
                    return "Password Changed!";
            else
                return "Passwords do not match!";
        }
    }





    public void changePassword(String oldPassword, String newPassword, String confirmNewPassword) {
        Handler isPasswordCorrectHandler = new IsPasswordCorrectHandler();
        Handler isPasswordValidHandler = new IsPasswordValidHandler();
        Handler confirmPasswordHandler = new ConfirmPasswordHandler();
        isPasswordCorrectHandler.setNextHandler(isPasswordValidHandler);
        isPasswordValidHandler.setNextHandler(confirmPasswordHandler);
        String result = isPasswordCorrectHandler.handle(oldPassword, newPassword, confirmNewPassword);
        if (result.equals("Password Changed!"))
            currentUser.changePassword(newPassword);
        AccountSettingPage.setMessage(result);
    }



    private void deleteFile(File file, boolean isDirectory) {
        if (isDirectory) {
            File[] files = file.listFiles();
            assert files != null;
            for (File thisFile : files) {
                if (thisFile.exists()) {
                    if (thisFile.delete())
                        System.out.println("file was deleted!");
                    else
                        System.out.println("file was not deleted!");
                }
                else
                    System.out.println("file doesn't exist!");
            }
        }
        else {
            if (file.exists()) {
                if (file.delete())
                    System.out.println("file was deleted!");
                else
                    System.out.println("file was not deleted!");
            }
            else
                System.out.println("file doesn't exist!");
        }
    }


    public void deleteAccount() {
        File file = new File("src/main/resources/sample/data/personalMaps/" + currentUser.getUsername());
        deleteFile(file, true);
        deleteFile(file, false);
        file = new File("src/main/resources/sample/data/savedGames/" + currentUser.getUsername() + ".json");
        deleteFile(file, false);
        User.deleteUser(currentUser);
        LoginPage.setCurrentUser(null);
        System.out.println("User was deleted!");
    }



    public static void setCurrentUser(User user) {
        currentUser = user;
    }



}

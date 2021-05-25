package Sample.Controller;

public class MainPageController {

    private static MainPageController instance;


    private MainPageController() {}


    public static MainPageController getInstance() {
        if (instance == null)
            instance = new MainPageController();
        return instance;
    }
}

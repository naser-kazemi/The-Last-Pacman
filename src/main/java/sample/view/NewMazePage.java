package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Maze;
import sample.model.Pacman;
import sample.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class NewMazePage extends Application{

    public static Stage mainStage;
    public static User currentUser = MainPage.getCurrentUser();
    public static Maze maze;
    protected static boolean lastFileHasBeanRead;
    protected static File currentFile;


    protected boolean mapIsSaved = false;
    protected String lastFileContent;
    protected boolean isLastFileOverWritten = false;
    protected Alert saveAlert = new Alert(Alert.AlertType.NONE, "Map has been saved!", ButtonType.OK);



    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL pageAddress = getClass().getResource("/Sample/fxml/new_maze_page.fxml");
        System.out.println(pageAddress);
        Parent signUpPagePane = FXMLLoader.load(Objects.requireNonNull(pageAddress));
        Scene scene = new Scene(signUpPagePane);
        stage.setScene(scene);
        stage.show();
    }

    public void createNewMap() throws Exception {
        maze = new Maze();
        if (currentUser.mapCounter == 5 && !lastFileHasBeanRead) {
            lastFileHasBeanRead = true;
            isLastFileOverWritten = true;
            File file = new File("src/main/resources/sample/data/personalMaps/" + currentUser.getUsername() + "/map" + currentUser.mapCounter + ".txt");
            lastFileContent = new String(Files.readAllBytes(Paths.get(file.getPath())));
        }
        if (currentUser.mapCounter < 5) {
            currentUser.mapCounter++;
        }
        String filePath = "src/main/resources/sample/data/personalMaps/" + currentUser.getUsername() + "/map" + currentUser.mapCounter + ".txt";
        currentFile = new File(filePath);
        try {
            FileWriter writer = new FileWriter(filePath);
            String[][] mazeString = maze.printMaze();
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 18; j++) {
                    writer.write(mazeString[i][j] + " ");
                }
                writer.write(mazeString[i][18] + "\n");
            }
            for (int i = 0; i < 18; i++)
                writer.write(mazeString[20][i] + " ");
            writer.write(mazeString[20][18]);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Pacman.mapFileName = filePath;
        Pacman.isMapDefault = false;
        new MazePage().start(mainStage);


    }

    public void saveMap(MouseEvent mouseEvent) {
        mapIsSaved = true;
        URL css = getClass().getResource("/sample/css/alert_style.css");
        DialogPane dialogPane = saveAlert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        ButtonBar buttonBar = (ButtonBar) saveAlert.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-background-color: #403e45;" +
                "-fx-pref-height: 40px;" + "-fx-text-fill: black" + "");
        buttonBar.getButtons().forEach(button -> button.setStyle("-fx-background-color: #e2b44d;" +
                "-fx-pref-height: 30px;" + "-fx-text-fill: black"));
        saveAlert.showAndWait();
        if (saveAlert.getResult() == ButtonType.OK)
            saveAlert.close();
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        if (!mapIsSaved && currentFile != null) {
            if (currentFile.delete())
                System.out.println("file deleted!");
            else
                System.out.println("file wasn't deleted!");
            currentUser.mapCounter++;
        }
        currentUser.mapCounter--;
        Pacman.isMapDefault = true;
        if (isLastFileOverWritten) {
            try {
                FileWriter writer = new FileWriter("src/main/resources/sample/data/personalMaps/" + currentUser.getUsername() + "/map5.txt");
                writer.write(lastFileContent);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lastFileHasBeanRead = false;
        new SelectMapPage().start(mainStage);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public void createAnotherMap() throws Exception {
        if (currentFile.delete())
            System.out.println("file deleted!");
        else
            System.out.println("file wasn't deleted!");
        currentUser.mapCounter--;
        createNewMap();
    }
}

module Pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;

    opens Sample.View to javafx.fxml;
    opens Sample.Model;
    exports Sample.View;
    exports Sample.Model;


}
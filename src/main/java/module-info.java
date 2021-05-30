module Pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;
    requires javafx.media;



    opens sample.view to javafx.fxml;
    opens sample.controller to javafx.fxml;
    opens sample.model;
    exports sample.view;
    exports sample.model;
    exports sample.controller;


}
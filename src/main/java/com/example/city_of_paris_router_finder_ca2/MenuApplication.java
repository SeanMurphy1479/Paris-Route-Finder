package com.example.city_of_paris_router_finder_ca2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuApplication extends Application {
    public static Scene menu;

    public static Stage primaryStage;



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuApplication.class.getResource("hello-view.fxml"));
        menu = new Scene(fxmlLoader.load(),1400, 800);
        stage.setTitle("Paris Route Finder");
        stage.setScene(menu);
        stage.show();
        primaryStage= stage;


    }

    public static void main(String[] args) {
        launch();
    }
}
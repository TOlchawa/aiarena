package com.thm.aiarena.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        double maxWidth = 0;
        double maxHeight = 0;

        for (Screen screen : Screen.getScreens()) {
            double width = screen.getBounds().getWidth();
            double height = screen.getBounds().getHeight();
            if (width > maxWidth) {
                maxWidth = width;
            }
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        System.out.println("Maximum screen resolution: " + maxWidth + " x " + maxHeight);


        StackPane root = new StackPane();
        Scene scene = new Scene(root, maxWidth, maxHeight);

        stage.setScene(scene);
        stage.setTitle("Full Screen App");

        // Make the window go full screen
        stage.setFullScreen(true);

        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
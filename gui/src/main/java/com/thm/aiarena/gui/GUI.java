package com.thm.aiarena.gui;

import com.thm.aiarena.communication.DataObject;
import com.thm.aiarena.gui.client.DataProvider;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    DataProvider dataProvider;

    double maxWidth = 0;
    double maxHeight = 0;
    StackPane root;
    Canvas canvas;
    GraphicsContext gc;

    public GUI() throws IOException {
        dataProvider = new DataProvider(this);
    }

    @Override
    public void start(Stage stage) {

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


        root = new StackPane();
        Scene scene = new Scene(root, maxWidth, maxHeight);
        canvas = new Canvas(maxWidth, maxHeight);
        gc = canvas.getGraphicsContext2D();

        stage.setScene(scene);
        stage.setTitle("Full Screen App");

        // Make the window go full screen
        stage.setFullScreen(true);

        root.getChildren().add(canvas);

        stage.show();

    }

    public void add(DataObject event) {
        gc.setFill(Color.GREEN);
        gc.fillRect(event.x, event.y, event.x+1, event.y+1);
        gc.fill();
    }

}
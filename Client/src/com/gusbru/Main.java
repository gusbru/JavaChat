package com.gusbru;

import com.gusbru.Connection.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./Connection/Connection.fxml"));
            AnchorPane root = loader.load();

            Connection connection = loader.getController();
            connection.setStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        }
        catch (Exception e)
        {
            throw new Exception("Error staring client");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

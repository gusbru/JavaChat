package com.gusbru.Connection;

import com.gusbru.Message.Message;
import com.gusbru.Message.MessageLogin;
import com.gusbru.Message.MessageRooms;
import com.gusbru.SelectRoom.SelectRoom;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Connection implements Initializable
{
    @FXML
    private TextField txtPort, txtServer, txtUsername;

    @FXML
    private Button btnConnect;

    private int port;
    private String server, username;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Stage currentStage;
    private Message messageRooms;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        btnConnect.setOnAction(event -> {
            connect();
            changeWindow();
        });
    }

    public void setStage(Stage stage)
    {
        this.currentStage = stage;
    }

    private void connect()
    {
        try
        {
            port   = Integer.valueOf(txtPort.getText());
            server = txtServer.getText();
            socket = new Socket(server, port);
        }
        catch (Exception e)
        {
            System.err.println("Error connecting to server");
            e.printStackTrace();
        }

        try
        {
            output = new ObjectOutputStream(socket.getOutputStream());
            input  = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            System.err.println("Error creating IO ");
            e.printStackTrace();
        }

        try
        {
            username = txtUsername.getText();
            Message message = new MessageLogin(username);
            output.writeObject(message);
        }
        catch (Exception e)
        {
            System.err.println("Error sending MessageLogin to server");
            e.printStackTrace();
        }
    }

    private void changeWindow()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gusbru/SelectRoom/SelectRoom.fxml"));
            AnchorPane root   = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            SelectRoom selectRoom = loader.getController();
            selectRoom.setStage(stage);
            selectRoom.setUserName(username);
            selectRoom.setConnections(output, input);
            selectRoom.receiveRooms();

            stage.show();
            currentStage.close();
        }
        catch (Exception e)
        {
            System.err.println("Error opening ChatRoom");
            e.printStackTrace();
        }
    }
}

package com.gusbru.ChatRoom;

import com.gusbru.Listeners.ListenerNewMessages;
import com.gusbru.Message.MessageText;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatRoom implements Initializable
{
    @FXML
    private Button btnSend;

    @FXML
    private TableView<MessageText> tableViewMessages;

    @FXML
    private TableColumn columnUser, columnMessage;

    @FXML
    private ListView<String> listViewUsers;

    @FXML
    private CheckBox chkPrivateMessage;

    @FXML
    private TextField txtMessage;

    @FXML
    private Label lblRoomName;

    private Stage currentStage;
    private String roomName, userName;
    private ObjectInputStream input;
    private ObjectOutputStream output;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        btnSend.setOnAction(event -> sendMessage());
    }

    public void setStage(Stage stage) throws Exception
    {
        if (stage == null)
            throw new Exception("Invalid stage");

        this.currentStage = stage;
    }

    public void setRoomName(String roomName) throws Exception
    {
        if (roomName.isEmpty())
            throw new Exception("Room name is empty");

        this.roomName = roomName;
        lblRoomName.setText(roomName);
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setConnections(ObjectOutputStream output, ObjectInputStream input)
    {
        this.output = output;
        this.input  = input;
    }

    public void updateUsersList(ArrayList<String> newUserList)
    {
        listViewUsers.getItems().clear();
        listViewUsers.getItems().addAll(newUserList);
    }

    public void startListener()
    {
        // listening for new messages
        try
        {
            new ListenerNewMessages(output, input, this);
        }
        catch (Exception e)
        {
            System.err.println("Error starting the listener...");
            e.printStackTrace();
        }
    }

    private void sendMessage()
    {
        String messageString = txtMessage.getText();
        String sender = listViewUsers.getSelectionModel().getSelectedItem();
        MessageText messageText = new MessageText(userName, sender, messageString);
        txtMessage.clear();

        if (chkPrivateMessage.isSelected())
        {
            System.out.println();
        }
        else
        {

        }

    }
}

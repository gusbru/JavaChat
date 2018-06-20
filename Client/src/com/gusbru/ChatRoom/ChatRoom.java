package com.gusbru.ChatRoom;

import com.gusbru.Listener.ListenerNewMessages;
import com.gusbru.Message.Message;
import com.gusbru.Message.MessageText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Label lblRoomName, lblCurrentUser;

    private Stage currentStage;
    private String roomName, userName;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ObservableList<MessageText> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        columnUser.setCellValueFactory(new PropertyValueFactory<>("sender"));
        columnMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        tableViewMessages.setItems(data);

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
        lblCurrentUser.setText(userName);
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
        try
        {
            String messageString = txtMessage.getText();
            String recipient = listViewUsers.getSelectionModel().getSelectedItem();
            boolean isPrivate = chkPrivateMessage.isSelected();
            MessageText messageText = new MessageText(recipient, userName, messageString, isPrivate);

            txtMessage.clear();
            output.writeObject(messageText);
        }
        catch (Exception e)
        {
            System.err.println("Error sending message");
            e.printStackTrace();
        }
    }

    public void newMessageReceived(MessageText messageText)
    {
        data.add(messageText);
        tableViewMessages.setItems(data);
    }
}

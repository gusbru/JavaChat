package com.gusbru.SelectRoom;

import com.gusbru.ChatRoom.ChatRoom;
import com.gusbru.Message.MessageEnterRoom;
import com.gusbru.Message.MessageRooms;
import com.gusbru.Rooms.RoomInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectRoom implements Initializable
{
    @FXML
    private Label lblUser;

    @FXML
    private TableView<RoomInfo> tableViewRoomsInfo;
    private ObservableList<RoomInfo> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn columnRoomName;

    @FXML
    private TableColumn columnOnlineUsers, columnCapacity;

    @FXML
    private Button btnEnter;

    private Stage currentStage;
    private String userName;
    private String selectedRoomName;
    private MessageRooms messageRooms;
    private ObjectInputStream input;
    private ObjectOutputStream output;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        columnRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        columnOnlineUsers.setCellValueFactory(new PropertyValueFactory<>("currentCapacity"));
        columnCapacity.setCellValueFactory(new PropertyValueFactory<>("maximumCapacity"));
        tableViewRoomsInfo.setItems(data);

        btnEnter.setOnAction(event -> {
            enterRoom();
            changeWindow();
        });
    }

    public void setStage(Stage stage)
    {
        this.currentStage = stage;
    }

    public void setUserName(String userName)
    {
        lblUser.setText(userName);
        this.userName = userName;
    }

    public void setRoomsInfo(MessageRooms messageRooms)
    {
        this.messageRooms = messageRooms;

    }

    public void setConnections(ObjectOutputStream output, ObjectInputStream input)
    {
        this.output = output;
        this.input  = input;
    }

    public void receiveRooms()
    {
        try
        {
            messageRooms = (MessageRooms) input.readObject();
            ArrayList<String> roomsName = messageRooms.getRoomsNames();
            ArrayList<Integer> maximumCapacities = messageRooms.getMaximumCapacities();
            ArrayList<Integer> currentCapacities = messageRooms.getCurrentCapacities();
            for (int i = 0; i < roomsName.size(); i++)
            {
                RoomInfo roomInfo = new RoomInfo(roomsName.get(i), maximumCapacities.get(i), currentCapacities.get(i));
                data.add(roomInfo);
            }
            tableViewRoomsInfo.setItems(data);

        }
        catch (Exception e)
        {
            System.err.println("Error receiving the rooms");
            e.printStackTrace();
        }
    }

    private void enterRoom()
    {
        RoomInfo selectedRoom = tableViewRoomsInfo.getSelectionModel().getSelectedItem();
        if (selectedRoom == null)
        {
            showAlert();
        }
        else
        {
            placeUser(selectedRoom.getRoomName());
        }
    }

    private void showAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chat");
        alert.setHeaderText("Select a Room");
        alert.setContentText("To continue, select a room, please :)");

        alert.showAndWait();
    }

    private void placeUser(String roomName)
    {
        try
        {
            MessageEnterRoom messageEnterRoom = new MessageEnterRoom(roomName);
            selectedRoomName = messageEnterRoom.getRoomName();
            output.writeObject(messageEnterRoom);
        }
        catch (Exception e)
        {
            System.err.println("Error entering in the room");
            e.printStackTrace();
        }
    }

    private void changeWindow()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gusbru/ChatRoom/ChatRoom.fxml"));
            AnchorPane root   = loader.load();
            ChatRoom chatRoom = loader.getController();


            chatRoom.setRoomName(selectedRoomName);
            chatRoom.setUserName(userName);
            chatRoom.setConnections(output, input);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            chatRoom.setStage(stage);
            chatRoom.startListener();

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

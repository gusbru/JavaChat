package com.gusbru.ClientHandler;

import com.gusbru.DB.DBO.Room;
import com.gusbru.Listener.ListenerNewMessages;
import com.gusbru.Message.*;
import com.gusbru.Rooms.RoomInfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String userName;
    private int roomID;
    private ArrayList<Room> rooms;

    public ClientHandler(Socket socket, ArrayList<Room> rooms) throws Exception
    {
        if (socket == null)
            throw new Exception ("Error handling client. Socket null");

        this.rooms = rooms;
        this.socket = socket;
        Thread thread = new Thread(this);
        thread.start();
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void sendMessageToCurrentUser(Message message) throws Exception
    {
        if (message == null)
            throw new Exception("Message cannot be null");

        try
        {
            output.writeObject(message);
        }
        catch (Exception e)
        {
            System.err.println("Error sending message to client");
            e.printStackTrace();
        }
    }

    public void sendMessageTextToCurrentUser(MessageText messageText) {
        try
        {
            output.writeObject(messageText);
        }
        catch (Exception e)
        {
            System.err.println("Error sending text message to client");
            e.printStackTrace();
        }
    }

    // TODO: sendMessageToAllUsers
    public void sendMessageTextToAllUsers(MessageText messageText)
    {
        ArrayList<ClientHandler> clientHandlers = rooms.get(roomID).getClientsHandlers();
        for (ClientHandler clientHandler : clientHandlers)
        {
            clientHandler.sendMessageTextToCurrentUser(messageText);
        }
    }

    // TODO: sendMessageToSpecificUser
    public void sendMessageTextToSpecificUser(MessageText messageText) throws Exception
    {
        String recipient = messageText.getRecipient();

        // find recipient handler
        ClientHandler recipientHandler = rooms.get(roomID).getClientHandler(recipient);

        if (recipient == null)
            throw new Exception("Username not found");

        recipientHandler.sendMessageToCurrentUser(messageText);
    }

    public void run()
    {
        // create the connection with the client
        try
        {
            output = new ObjectOutputStream(socket.getOutputStream());
            input  = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e)
        {
            System.err.println("[SERVER] Error getting input/output stream for client");
            e.printStackTrace();
        }

        // receives the client username
        try
        {
            MessageLogin message = (MessageLogin) input.readObject();
            userName = message.getUsername();
            System.out.println(message.getUsername());
        }
        catch (Exception e)
        {
            System.err.println("Error receiving message");
            e.printStackTrace();
        }

        // send rooms to connected user
        try
        {
            System.out.print("Sending rooms info to client...");
            ArrayList<String> roomsName = new ArrayList<>(rooms.size());
            ArrayList<Integer> currentCapacities = new ArrayList<>(rooms.size());
            ArrayList<Integer> maximumCapacities = new ArrayList<>(rooms.size());
            for (Room room : rooms)
            {
                roomsName.add(room.getRoomName());
                currentCapacities.add(room.getCurrentCapacity());
                maximumCapacities.add(room.getMaxCapacity());
            }
            MessageRooms messageRooms = new MessageRooms(roomsName, maximumCapacities, currentCapacities);
            output.writeObject(messageRooms);
            System.out.println("OK");
        }
        catch (Exception e)
        {
            System.err.println("Error sending rooms to client");
            e.printStackTrace();
        }

        // receive the desired room and try to place the client in the selected room
        try
        {
            MessageEnterRoom messageEnterRoom = (MessageEnterRoom) input.readObject();
            String roomName = messageEnterRoom.getRoomName();
            for (int i = 0; i < rooms.size(); i++)
            {
                if (rooms.get(i).getRoomName().equals(roomName))
                {
                    roomID = i;
                    rooms.get(i).addClient(this);
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error receiving the selected room");
            e.printStackTrace();
        }

        // for all users update the entire usersList
        try
        {
            rooms.get(roomID).updateUsersList();
        }
        catch (Exception e)
        {
            System.err.println("Error updating the usersList");
            e.printStackTrace();
        }



        //#######################################################
        System.out.println("Number of users in each room");
        for (Room room : rooms)
        {
            System.out.println(room.getRoomName() + " " + room.getCurrentCapacity());
        }
        //#######################################################

        // TODO: finish the incoming messages listener
        // listening for incoming messages
        new ListenerNewMessages(output, input,this);

        // TODO: outgoing messages listener
        // listening for outgoing messages

        // TODO: exit user listener (?)
        // listening for user exit
    }
}

package com.gusbru.DB.DBO;

import com.gusbru.ClientHandler.ClientHandler;
import com.gusbru.Message.MessageNewClient;

import java.util.ArrayList;

public class Room implements Cloneable
{
    private String name;
    private int maxCapacity, currentCapacity = 0;
    private ArrayList<ClientHandler> clients;

    public Room(String name, int maxCapacity) throws Exception
    {
        if (name.isEmpty())
            throw new Exception ("Invalid Room name");

        if (maxCapacity < 0)
            throw new Exception ("Invalid Room Maximum Capacity");

        this.name = name;
        this.maxCapacity = maxCapacity;

        clients = new ArrayList<>(maxCapacity);
    }

    public Room(Room model) throws Exception
    {
        if (model == null)
            throw new Exception ("Room cannot be null");

        this.name = model.name;
        this.maxCapacity = model.maxCapacity;
        this.currentCapacity = model.currentCapacity;

        clients = new ArrayList<>(maxCapacity);
        clients.addAll(model.clients);
    }

    public Object clone()
    {
        Room retRoom = null;
        try
        {
            retRoom = new Room(this);
        }
        catch (Exception e)
        {
            System.err.println("Not possible to clone");
        }

        return retRoom;
    }

    public String getRoomName()
    {
        return name;
    }

    public int getMaxCapacity()
    {
        return maxCapacity;
    }

    public int getCurrentCapacity()
    {
        return currentCapacity;
    }

    synchronized public void addClient(ClientHandler clientHandler) throws Exception
    {
        if (clientHandler == null)
            throw new Exception ("Cannot add a null clientHandler");

        if (currentCapacity == maxCapacity)
            throw new Exception ("Room full");

        // TODO: check if clientHandler is cloneable
        clients.add(clientHandler);
        currentCapacity++;
    }

    public ArrayList<String> getUserList()
    {
        ArrayList<String> users = new ArrayList<>(clients.size());
        for (ClientHandler client : clients)
        {
            users.add(client.getUserName());
        }

        return users;
    }

    public void updateUsersList(String newUserName) throws Exception
    {
        if (newUserName.isEmpty())
            throw new Exception("Username cannot be empty");

        try
        {
            MessageNewClient messageNewClient = new MessageNewClient(newUserName);
            for (ClientHandler client : clients)
            {
                client.sendMessage(messageNewClient);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error notifying the clients about the new user");
            e.printStackTrace();
        }

    }

    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (obj.getClass() != this.getClass())
            return false;

        Room test = (Room) obj;

        if (!test.name.equals(this.name))
            return false;

        if (test.maxCapacity != this.maxCapacity)
            return false;

        if (test.currentCapacity != this.currentCapacity)
            return false;

        for (int i = 0; i < clients.size(); i++)
        {
            if (!clients.get(i).equals(test.clients.get(i)))
                return false;
        }

        return true;
    }



}

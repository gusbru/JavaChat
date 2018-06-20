package com.gusbru.DB.DAO;

import com.gusbru.DB.DB;
import com.gusbru.DB.DBO.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Rooms
{
    public ArrayList<Room> getRooms()
    {
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Salas";

        try
        {
            PreparedStatement preparedStatement = DB.CONNECTION.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                String name = resultSet.getString("Nome");
                int capacity = resultSet.getInt("Capacidade");
                Room room = new Room(name, capacity);
                rooms.add(room);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error retrieving rooms from DB server");
            e.printStackTrace();
        }

        return rooms;
    }
}

package com.gusbru.DB;

import com.gusbru.DB.DAO.Rooms;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB
{
    public static final Connection CONNECTION;

    // DAO
    public static final Rooms ROOMS;

    static
    {
        Connection connection = null;

        // DAO
        Rooms rooms = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlserver://poo.database.windows.net:1433;database=salas;user=gusbru@poo;password=d5uv6DoT.;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

            // DAO
            rooms = new Rooms();
        }
        catch (Exception e)
        {

        }

        CONNECTION = connection;

        // DAO
        ROOMS = rooms;
    }
}

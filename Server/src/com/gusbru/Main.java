package com.gusbru;

import com.gusbru.ClientHandler.ClientHandler;
import com.gusbru.DB.DB;
import com.gusbru.DB.DBO.Room;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        int port = 12322;

        // retrieve rooms from DB server
        System.out.print("[SERVER]Receiving rooms from DB...");
        ArrayList<Room> rooms = DB.ROOMS.getRooms();
        System.out.println("OK");

        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            for (;;)
            {
                System.out.print("Waiting for new clients...");
                Socket socket = serverSocket.accept();
                System.out.println("OK");
                new ClientHandler(socket, rooms);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error starting the server");
            e.printStackTrace();
        }
    }
}

package com.gusbru.Listeners;

import com.gusbru.ChatRoom.ChatRoom;
import com.gusbru.Message.Message;
import com.gusbru.Message.MessageNewClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ListenerNewMessages implements Runnable
{
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ChatRoom chatRoom;


    public ListenerNewMessages(ObjectOutputStream output, ObjectInputStream input, ChatRoom chatRoom) throws Exception
    {
        if (output == null || input == null)
            throw new Exception("Input and/or output cannot be null");

        this.output   = output;
        this.input    = input;
        this.chatRoom = chatRoom;

        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run()
    {
        while (input != null && output != null)
        {
            try
            {
                Message message = (Message) input.readObject();
                if (message instanceof MessageNewClient)
                {
                    MessageNewClient messageNewClient = (MessageNewClient) message;
                    chatRoom.updateUsersList(messageNewClient.getNewClientName());
                }

            }
            catch (Exception e)
            {
                System.err.println("Error listening for messages");
                e.printStackTrace();
            }
        }
        System.out.println("Exiting the listener :(");
    }
}

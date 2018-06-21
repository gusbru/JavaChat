package com.gusbru.Listener;

import com.gusbru.ClientHandler.ClientHandler;
import com.gusbru.Message.Message;
import com.gusbru.Message.MessageText;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ListenerNewMessages implements Runnable
{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ClientHandler clientHandler;

    public ListenerNewMessages(ObjectOutputStream output, ObjectInputStream input, ClientHandler clientHandler)
    {
        try
        {
            this.output = output;
            this.input = input;
            this.clientHandler = clientHandler;
            Thread thread = new Thread(this);
            thread.start();
        }
        catch (Exception e)
        {
            System.err.println("Error starting the New Messages listener");
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        Message message;
        while (input != null && output != null)
        {
            try
            {
                message = (Message) input.readObject();

                if (message instanceof MessageText)
                {
                    MessageText messageText = (MessageText) message;
                    String sender = messageText.getSender();
                    String recipient = messageText.getRecipient();
                    String msg = messageText.getMessage();
                    boolean isPrivate = messageText.isPrivate();

                    System.out.println("Message received!");
                    System.out.println("Sender:" + sender);
                    System.out.println("Recipient:" + recipient);
                    System.out.println("MSG:" + msg);


                    if (isPrivate)
                    {
                        clientHandler.sendMessageTextToSpecificUser(messageText);
                    }
                    else
                    {
                        clientHandler.sendMessageTextToAllUsers(messageText);
                    }
                }
            }
            catch (Exception e)
            {
                System.err.println("Error listening messages");
                System.err.println("====> " + clientHandler.getUserName());
                e.printStackTrace();
            }
        }
    }
}

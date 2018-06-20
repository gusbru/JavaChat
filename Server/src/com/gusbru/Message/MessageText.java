package com.gusbru.Message;

public class MessageText implements Message
{
    private String recipient, sender, message;

    public MessageText(String recipient, String sender, String message)
    {
        this.recipient = recipient;
        this.sender = sender;
        this.message = message;
    }

    public String getRecipient()
    {
        return this.recipient;
    }

    public String getSender()
    {
        return sender;
    }

    public String getMessage()
    {
        return message;
    }
}


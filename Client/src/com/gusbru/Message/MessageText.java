package com.gusbru.Message;

public class MessageText implements Message
{
    private String recipient, sender, message;
    private boolean isPrivate;

    public MessageText(String recipient, String sender, String message, boolean isPrivate)
    {
        this.recipient = recipient;
        this.sender = sender;
        this.message = message;
        this.isPrivate = isPrivate;
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

    public boolean isPrivate()
    {
        return isPrivate;
    }
}

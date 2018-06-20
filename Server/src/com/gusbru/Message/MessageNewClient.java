package com.gusbru.Message;

public class MessageNewClient implements Message
{
    private String newClientName;

    public MessageNewClient(String newClientName)
    {
        this.newClientName = newClientName;
    }

    public String getNewClientName()
    {
        return this.newClientName;
    }
}

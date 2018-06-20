package com.gusbru.Message;

public class MessageLogin implements Message
{
    private String username;

    public MessageLogin(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}

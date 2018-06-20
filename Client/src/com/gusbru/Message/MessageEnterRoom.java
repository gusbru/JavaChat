package com.gusbru.Message;

public class MessageEnterRoom implements Message
{
    private String roomName;

    public MessageEnterRoom(String roomName) throws Exception
    {
        if (roomName.isEmpty())
            throw new Exception("Invalid room");

        this.roomName = roomName;
    }

    public String getRoomName()
    {
        return this.roomName;
    }

}

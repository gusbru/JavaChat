package com.gusbru.Rooms;

public class RoomInfo
{
    private String roomName;
    private int currentCapacity;
    private int maximumCapacity;

    public RoomInfo(String roomName, int maximumCapacity, int currentCapacity)
    {
        this.roomName = roomName;
        this.maximumCapacity = maximumCapacity;
        this.currentCapacity = currentCapacity;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public int getCurrentCapacity()
    {
        return currentCapacity;
    }

    public int getMaximumCapacity()
    {
        return maximumCapacity;
    }

    public String toString()
    {
        String ret = "";
        ret += "Room Name:" + roomName;
        ret += "|maxCapac:" + maximumCapacity;
        ret += "|curCapac:" + currentCapacity;

        return ret;
    }

}

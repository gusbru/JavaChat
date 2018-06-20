package com.gusbru.Message;

import java.util.ArrayList;

public class MessageRooms implements Message
{
    private ArrayList<String> roomsNames;
    private ArrayList<Integer> maximumCapacities;
    private ArrayList<Integer> currentCapacities;

    public MessageRooms(ArrayList<String> roomsNames, ArrayList<Integer> maximumCapacities, ArrayList<Integer> currentCapacities)
    {
        this.roomsNames = new ArrayList<>(roomsNames.size());
        this.currentCapacities = new ArrayList<>(currentCapacities.size());
        this.maximumCapacities = new ArrayList<>(maximumCapacities.size());
        for (int i = 0; i < roomsNames.size(); i++)
        {
           this.roomsNames.add(roomsNames.get(i));
           this.currentCapacities.add(currentCapacities.get(i));
           this.maximumCapacities.add(maximumCapacities.get(i));
        }
    }

    public ArrayList<String> getRoomsNames()
    {
        return roomsNames;
    }

    public ArrayList<Integer> getCurrentCapacities()
    {
        return currentCapacities;
    }

    public ArrayList<Integer> getMaximumCapacities()
    {
        return maximumCapacities;
    }
}

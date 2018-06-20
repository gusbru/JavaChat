package com.gusbru.Message;

import java.util.ArrayList;

public class MessageNewClientList implements Message
{
    private ArrayList<String> newClientList;

    public MessageNewClientList(ArrayList<String> newClientList)
    {
        this.newClientList = new ArrayList<>(newClientList.size());
        for (int i = 0; i < newClientList.size(); i++)
        {
            this.newClientList.add(newClientList.get(i));
        }
    }

    public ArrayList<String> getClientList()
    {
        return this.newClientList;
    }
}
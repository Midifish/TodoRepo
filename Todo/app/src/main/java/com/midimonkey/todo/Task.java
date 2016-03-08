package com.midimonkey.todo;

import java.io.Serializable;

/**
 * Created by MJK on 3/5/2016.
 */
public class Task implements Serializable{
    private int id;
    private String description;

    public Task(String desc)
    {
        description = desc;
    }

    public Task(int id, String desc)
    {
        this.id = id;
        description = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

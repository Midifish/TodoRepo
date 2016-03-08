package com.midimonkey.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private DataIO database;
    private ArrayList<String> taskDescriptions;
    private ListView taskListView;
    private ArrayList<Task> tasks;
    private SimpleCursorAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DataIO(this);
        taskDescriptions = new ArrayList<>();
        taskListView = (ListView) findViewById(R.id.taskList);
        tasks = new ArrayList<>();
        taskListAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                database.getCursor(),
                new String[] { "description" },
                new int[] { android.R.id.text1 },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        checkForAddedTask();
        checkForEditedTask();
        checkForDeletedTask();
        taskListView.setAdapter(taskListAdapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                taskListAdapter.getCursor().moveToPosition(position);
                openEditTaskActivity(database.getTask(taskListAdapter.getCursor().getInt(0)));
            }
        });
    }

    public void checkForAddedTask()
    {
        Task addedTask = (Task) getIntent().getSerializableExtra("AddedTask");
        if(addedTask != null)
        {
            database.addTask(addedTask);
            taskListAdapter.notifyDataSetChanged();
        }
    }

    public void checkForEditedTask()
    {
        Task editedTask = (Task) getIntent().getSerializableExtra("EditedTask");
        if(editedTask != null)
        {
            database.editTask(editedTask);
        }
    }

    public void checkForDeletedTask()
    {
        Task deletedTask = (Task) getIntent().getSerializableExtra("DeletedTask");
        if(deletedTask != null)
        {
            database.deleteTask(deletedTask);
        }
    }

    public void openAddTaskActivity(View v)
    {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void openEditTaskActivity(Task task)
    {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("taskToEdit", task);
        startActivity(intent);
    }
}

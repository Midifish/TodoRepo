package com.midimonkey.todo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity
{
    private DataIO database;
    private ListView taskListView;
    private SimpleCursorAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DataIO(this);
        taskListView = (ListView) findViewById(R.id.taskList);
        taskListAdapter = new SimpleCursorAdapter
                (
                this,
                android.R.layout.simple_list_item_1,
                database.getCursor(),
                new String[] { "description" },
                new int[] { android.R.id.text1 },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                );
        taskListView.setAdapter(taskListAdapter);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                taskListAdapter.getCursor().moveToPosition(position);
                openEditTaskActivity(database.getTask(taskListAdapter.getCursor().getInt(0)));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Task updatedTask;
        if(requestCode == 0)
        {
            updatedTask = (Task) data.getSerializableExtra("AddedTask");
            database.addTask(updatedTask);
        }
        else if(requestCode == 1)
        {
            if((updatedTask = (Task) data.getSerializableExtra("DeletedTask")) != null)
                database.deleteTask(updatedTask);
            else if((updatedTask = (Task) data.getSerializableExtra("EditedTask")) != null)
                database.editTask(updatedTask);
        }
        taskListAdapter.swapCursor(database.getCursor());
        taskListAdapter.notifyDataSetChanged();
    }

    public void openAddTaskActivity(View v)
    {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent,0);
    }

    public void openEditTaskActivity(Task task)
    {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("taskToEdit", task);
        startActivityForResult(intent,1);
    }
}

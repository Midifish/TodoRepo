package com.midimonkey.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class AddActivity extends AppCompatActivity
{
    private EditText taskDescriptionTextBox;
    private Task newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        taskDescriptionTextBox = (EditText) findViewById(R.id.taskDescriptionTextBox);
    }

    public void addTask(View v)
    {
        newTask = new Task(taskDescriptionTextBox.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("AddedTask", newTask);
        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }


}

package com.midimonkey.todo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity
{
    private EditText taskDescriptionTextBox;
    private Task taskToEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        taskDescriptionTextBox = (EditText) findViewById(R.id.taskDescriptionTextBox);
        taskToEdit = (Task) getIntent().getSerializableExtra("taskToEdit");
        if(taskToEdit != null)
        {
            taskDescriptionTextBox.setText(taskToEdit.getDescription());
        }
    }

    public void editTask(View v)
    {
        taskToEdit.setDescription(taskDescriptionTextBox.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EditedTask", taskToEdit);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void deleteTask(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("DeletedTask", taskToEdit);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}

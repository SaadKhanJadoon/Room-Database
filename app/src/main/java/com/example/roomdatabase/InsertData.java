package com.example.roomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdatabase.Database.DatabaseStudent;
import com.example.roomdatabase.Entity.EntityStudent;

public class InsertData extends AppCompatActivity {

    EditText name, studentClass, age ;
    Button saveData;
    StudentDAO studentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);

        name = findViewById(R.id.name);
        studentClass = findViewById(R.id.studentClass);
        age = findViewById(R.id.age);
        saveData = findViewById(R.id.saveData);

        studentDAO = DatabaseStudent.getDBInstance( this ).studentDAO();

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //name
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Name is required.", Toast.LENGTH_SHORT).show();
                }
                //Class
                else if (studentClass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Class is required.", Toast.LENGTH_SHORT).show();
                }
                //Age
                else if (age.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Age is required.", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveStudentData();
                    Intent openMainActivity = new Intent(InsertData.this, MainActivity.class);
                    openMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(openMainActivity);
                    finish();
                }
            }
        });
    }

    private void saveStudentData() {

        String name1 = name.getText().toString();
        String class1 = studentClass.getText().toString();
        String age1 = age.getText().toString();

        //creating a task
        EntityStudent task = new EntityStudent();
        task.setName(name1);
        task.setStudentClass(class1);
        task.setAge(age1);
        studentDAO.insert( task );
        Intent openMain = new Intent(getApplicationContext(), MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(openMain);
        finish();
        Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
    }
}

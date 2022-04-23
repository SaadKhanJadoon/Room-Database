package com.example.roomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.Adapter.StudentAdapter;
import com.example.roomdatabase.Database.DatabaseStudent;
import com.example.roomdatabase.Entity.EntityStudent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements StudentAdapter.onUpdateClickListener, StudentAdapter.onDeleteClickListener  {

    boolean flag = true;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    StudentDAO studentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.studentData);
        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        studentDAO = DatabaseStudent.getDBInstance(this).studentDAO();
        getStudentData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    fab1.show();
                    fab2.show();
                    fab.setImageResource(R.drawable.ic_close_icon);

                    fab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent openInsertActivity = new Intent(MainActivity.this, InsertData.class);
                            openInsertActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(openInsertActivity);
                        }
                    });

                    flag = false;
                }else {
                    fab1.hide();
                    fab2.hide();
                    fab1.animate().translationY(0);
                    fab2.animate().translationY(0);

                    fab.setImageResource(R.drawable.ic_add_icon);
                    flag = true;

                }
//                Intent openInsertActivity = new Intent(MainActivity.this, InsertData.class);
//                openInsertActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(openInsertActivity);
            }
        });
    }

    private void getStudentData() {
        try {
            StudentAdapter adapter1 = new StudentAdapter(MainActivity.this, studentDAO.getAll(), this::onDeleteClickListener, this::onUpdateClickListener);
            recyclerView.setAdapter(adapter1);
            layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(decoration);

        }catch (Exception e){
            Toast.makeText(MainActivity.this, "No Data Exists", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteClickListener(EntityStudent entityStudent) {
        studentDAO.delete(entityStudent);
        Toast.makeText(getApplicationContext(), " Student Data Deleted ", Toast.LENGTH_LONG).show();
        StudentAdapter adapter1 = new StudentAdapter(MainActivity.this, studentDAO.getAll(), this::onUpdateClickListener, this::onUpdateClickListener);
        recyclerView.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onUpdateClickListener(EntityStudent entityStudent) {
        studentDAO.update(entityStudent);
        StudentAdapter updatedData = new StudentAdapter(MainActivity.this, studentDAO.getAll(), this::onDeleteClickListener, this::onUpdateClickListener);
        recyclerView.setAdapter(updatedData);
        updatedData.notifyDataSetChanged();
    }
}
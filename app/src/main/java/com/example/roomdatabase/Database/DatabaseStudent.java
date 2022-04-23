package com.example.roomdatabase.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabase.Entity.EntityStudent;
import com.example.roomdatabase.StudentDAO;

@Database(
        entities = EntityStudent.class,
        version=1,
        exportSchema = false )

public abstract class DatabaseStudent extends RoomDatabase {
    private static DatabaseStudent userDB = null;

    //public abstract UserDAO userDAO;
    public static synchronized DatabaseStudent getDBInstance(Context context){
        if (userDB==null){
            userDB= Room.databaseBuilder( context.getApplicationContext(), DatabaseStudent.class, "Student").allowMainThreadQueries().build();
        }
        return userDB;
    }
    public abstract StudentDAO studentDAO();
}

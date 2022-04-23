package com.example.roomdatabase.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdatabase.Entity.EntityStudent;
import com.example.roomdatabase.StudentDAO;

@Database(entities = {EntityStudent.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

}

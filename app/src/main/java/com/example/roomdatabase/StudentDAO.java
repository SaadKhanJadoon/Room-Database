package com.example.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdatabase.Entity.EntityStudent;

import java.util.List;

@Dao
public interface StudentDAO {

    @Query("SELECT * FROM Student")
    List<EntityStudent> getAll();

    @Insert
    void insert(EntityStudent entityStudent);

    @Delete
    void delete(EntityStudent entityStudent);

    @Update
    void update(EntityStudent entityStudent);

}

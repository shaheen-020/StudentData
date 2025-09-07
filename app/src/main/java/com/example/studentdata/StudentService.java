package com.example.studentdata;
import android.database.Cursor;

import com.example.studentdata.DbHelper;

import java.util.ArrayList;
import java.util.List;
public class StudentService {
    private final DbHelper db;
    public StudentService(DbHelper dbHelper) {
        this.db = dbHelper;
    }
    public boolean idExists(String roll) {
        return db.idExists(roll);
    }
    public boolean insertStudent(String roll, String name) {
        return db.insertStudent(roll, name);
    }
    public List<String[]> searchStudents(String query) {
        List<String[]> students = new ArrayList<>();
        students = db.searchStudents(query);
        return students;
    }

}
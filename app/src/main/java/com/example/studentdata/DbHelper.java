package com.example.studentdata;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "studentinfo.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "students";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " TEXT PRIMARY KEY, " +
                COL_NAME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertStudent(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        cv.put(COL_NAME, name);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    public boolean idExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?", new String[]{id});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateStudent(String id, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, newName);
        int rowsAffected = db.update(TABLE_NAME, cv, COL_ID + " = ?", new String[]{id});
        return rowsAffected > 0;
    }
    public boolean deleteStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{id});
        return rows > 0;
    }
    public List<String[]>  searchStudents(String query) {
        List<String[]> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_ID + " = ? OR " + COL_NAME + " LIKE ?", new String[]{query, "%" + query + "%"});
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String[] student = new String[2];
                    student[0] = cursor.getString(0); // ID
                    student[1] = cursor.getString(1); // Name
                    students.add(student);
                }
            } finally {
                cursor.close();
            }
        }
        return  students;
    }
}

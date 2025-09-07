package com.example.studentdata;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {
    EditText etRoll, etName;
    Button btnSave;
    DbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        etRoll = findViewById(R.id.etAddRoll);
        etName = findViewById(R.id.etAddName);
        btnSave = findViewById(R.id.btnSaveStudent);
        db = new DbHelper(this);
        com.example.studentdata.StudentService sv = new com.example.studentdata.StudentService(db);

        btnSave.setOnClickListener(v -> {
            String roll = etRoll.getText().toString().trim();
            String name = etName.getText().toString().trim();
            if (roll.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Enter both ID and name", Toast.LENGTH_SHORT).show();
            } else if (sv.idExists(roll)) {
                Toast.makeText(this, "ID already exists", Toast.LENGTH_SHORT).show();
            } else {
                boolean ok = sv.insertStudent(roll, name);
                Toast.makeText(this, ok ? "Added successfully" : "Insert failed", Toast.LENGTH_SHORT).show();
                if (ok) {
                    etRoll.setText("");
                    etName.setText("");
                }
            }
        });
    }
}

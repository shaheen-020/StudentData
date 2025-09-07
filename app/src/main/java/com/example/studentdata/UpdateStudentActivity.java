package com.example.studentdata;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {
    EditText etRoll, etNewName;
    Button btnUpdate;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        etRoll = findViewById(R.id.etUpdRoll);
        etNewName = findViewById(R.id.etUpdName);
        btnUpdate = findViewById(R.id.btnUpdateStudent);
        db = new DbHelper(this);

        btnUpdate.setOnClickListener(v -> {
            String roll = etRoll.getText().toString().trim();
            String newName = etNewName.getText().toString().trim();
            if (roll.isEmpty() || newName.isEmpty()) {
                Toast.makeText(this, "Enter ID and new name", Toast.LENGTH_SHORT).show();
            } else {
                boolean ok = db.updateStudent(roll, newName);
                Toast.makeText(this, ok ? "Updated" : "Student not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

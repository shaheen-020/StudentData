package com.example.studentdata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnView, btnUpdate, btnStdRecords, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Check login status
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // ✅ Find buttons
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnStdRecords = findViewById(R.id.btnStdRecords);
        btnLogout = findViewById(R.id.btnLogout);

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddStudentActivity.class)));
        btnView.setOnClickListener(v -> startActivity(new Intent(this, ViewStudentsActivity.class)));
        btnUpdate.setOnClickListener(v -> startActivity(new Intent(this, UpdateStudentActivity.class)));
        btnStdRecords.setOnClickListener(v -> startActivity(new Intent(this, StudentRecordsActivity.class)));

        // ✅ Logout
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this, "Logout successfully.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}

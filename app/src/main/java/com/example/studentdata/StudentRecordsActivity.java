package com.example.studentdata;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class StudentRecordsActivity extends AppCompatActivity {

    EditText etQuery;
    Button btnSearch;
    TableLayout tableLayout;
    DbHelper db;
    com.example.studentdata.StudentService studentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_records);

        etQuery = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        tableLayout = findViewById(R.id.tableLayoutRecords);
        db = new DbHelper(this);
        studentService = new StudentService(db);

        btnSearch.setOnClickListener(v -> {
            String q = etQuery.getText().toString().trim();
            if (q.isEmpty()) {
                Toast.makeText(this, "Enter ID or name", Toast.LENGTH_SHORT).show();
                return;
            }

            tableLayout.removeAllViews();
            addHeader();

            List<String[]> students = studentService.searchStudents(q);
            if (students.isEmpty()) {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
            } else {
                for (String[] student : students) {
                    TableRow row = new TableRow(this);
                    row.addView(makeText(student[0])); // ID
                    row.addView(makeText(student[1])); // Name
                    tableLayout.addView(row);
                }
            }
        });
    }

    void addHeader() {
        TableRow header = new TableRow(this);
        header.addView(makeText("ID", true));
        header.addView(makeText("Name", true));
        tableLayout.addView(header);
    }

    TextView makeText(String text) {
        return makeText(text, false);
    }

    TextView makeText(String text, boolean isHeader) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(16, 10, 16, 10);
        if (isHeader) tv.setTextSize(18);
        return tv;
    }
}

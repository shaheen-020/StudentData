package com.example.studentdata;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewStudentsActivity extends AppCompatActivity {
    TableLayout tableLayout;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stusents);

        tableLayout = findViewById(R.id.tableLayout);
        db = new DbHelper(this);
        tableLayout.removeAllViews();
        addHeader();

        Cursor c = db.getAllStudents();
        if (c.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                TableRow row = new TableRow(this);
                String roll = c.getString(0);
                row.addView(makeText(roll));
                row.addView(makeText(c.getString(1)));

                Button btnDel = new Button(this);
                btnDel.setText("Delete");
                btnDel.setOnClickListener(v -> {
                    if (db.deleteStudent(roll)) {
                        Toast.makeText(this, "Deleted " + roll, Toast.LENGTH_SHORT).show();
                        recreate();
                    }
                });

                row.addView(btnDel);
                tableLayout.addView(row);
            }
        }
        c.close();
    }

    void addHeader() {
        TableRow h = new TableRow(this);
        h.addView(makeText("ID", true));
        h.addView(makeText("Name", true));
        h.addView(makeText("Action", true));
        tableLayout.addView(h);
    }
    TextView makeText(String s) { return makeText(s, false); }

    TextView makeText(String s, boolean hdr) {
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(16, 10, 16, 10);
        if (hdr) tv.setTextSize(18);
        return tv;
    }
}

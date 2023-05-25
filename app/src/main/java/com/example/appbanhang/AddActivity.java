package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.appbanhang.dal.SQLiteHelper;
import com.example.appbanhang.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    public Spinner spDesFrom, spDesTo;
    private EditText eName, ePrice, eDate;
    private Button btUpdate, btRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        spDesFrom = findViewById(R.id.spDesFrom);
        spDesTo = findViewById(R.id.spDesTo);
        eName = findViewById(R.id.edName);
        ePrice = findViewById(R.id.edPrice);
        eDate = findViewById(R.id.edDate);
        btUpdate = findViewById(R.id.btUpdate);
        btRemove = findViewById(R.id.btRemove);
        spDesFrom.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
        spDesTo.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        if (view == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dp = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if (m > 8) {
                        if (d > 9)
                            date += d + "/" + (m + 1) + "/" + y;
                        else
                            date += "0" + d + "/" + (m + 1) + "/" + y;
                    } else {
                        if (d > 9)
                            date += d + "/0" + (m + 1) + "/" + y;
                        else
                            date += "0" + d + "/0" + (m + 1) + "/" + y;
                    }
                    eDate.setText(date);
                }
            }, year, month, day);
            dp.show();
        } else if (view == btRemove) {
            finish();
        } else if (view == btUpdate) {
            String name = eName.getText().toString();
            String price = ePrice.getText().toString();
            String desFrom = spDesFrom.getSelectedItem().toString();
            String desTo = spDesTo.getSelectedItem().toString();
            String date = eDate.getText().toString();

            if (!name.isEmpty() && price.matches("\\d+")) {
                Item i = new Item(name, desFrom, desTo, price, date);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(i);
                finish();
            }
        }
    }
}
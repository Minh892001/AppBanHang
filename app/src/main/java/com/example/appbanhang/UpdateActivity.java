package com.example.appbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UpdateActivity extends AppCompatActivity {
    private Spinner spDesFrom, spDesTo;
    private EditText eName, ePrice, eDate;
    private Button btUpdate, btDelete, btBack;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        spDesFrom = findViewById(R.id.spDesFrom);
        spDesTo = findViewById(R.id.spDesTo);
        eName = findViewById(R.id.edName);
        ePrice = findViewById(R.id.edPrice);
        eDate = findViewById(R.id.edDate);

        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btRemove);
        btBack = findViewById(R.id.btBack);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");

        eName.setText(item.getName());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());

        int p1 = 0;
        int p2 = 0;
        for (int i = 0; i < spDesFrom.getCount(); i++) {
            if(spDesFrom.getItemAtPosition(i).toString().equalsIgnoreCase(item.getDestinationFrom())) {
                p1 = i;
                break;
            }
        }
        for (int i = 0; i < spDesTo.getCount(); i++) {
            if(spDesTo.getItemAtPosition(i).toString().equalsIgnoreCase(item.getDestinationTo())) {
                p2 = i;
                break;
            }
        }
        spDesFrom.setSelection(p1);
        spDesTo.setSelection(p2);

        spDesFrom.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));
        spDesTo.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));

        SQLiteHelper db = new SQLiteHelper(this);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = eName.getText().toString();
                String dFrom = spDesFrom.getSelectedItem().toString();
                String dTo = spDesTo.getSelectedItem().toString();
                String p = ePrice.getText().toString();
                String d = eDate.getText().toString();

                if(!t.isEmpty() && !d.isEmpty() && p.matches("\\d+")) {
                    item.setId(item.getId());
                    item.setName(t);
                    item.setDestinationFrom(dFrom);
                    item.setDestinationTo(dTo);
                    item.setPrice(p);
                    item.setDate(d);

                    db.updateItem(item);
                    finish();
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac muon xoa " + item.getId() + "khong?");
                builder.setIcon(R.drawable.remove);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println(item.getId());
                        db.deleteItem(item.getId());
                        finish();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date = "";
                        if(m > 8) {
                            if(d > 9)
                                date += d+"/"+(m+1)+"/"+y;
                            else
                                date += "0"+d+"/"+(m+1)+"/"+y;
                        }
                        else {
                            if(d > 9)
                                date += d+"/0"+(m+1)+"/"+y;
                            else
                                date += "0"+d+"/0"+(m+1)+"/"+y;
                        }

                        eDate.setText(date);
                    }
                }, year, month, day);

                dialog.show();
            }
        });
    }
}
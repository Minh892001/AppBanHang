package com.example.appbanhang.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.RecycleViewAdapter;
import com.example.appbanhang.dal.SQLiteHelper;
import com.example.appbanhang.model.Item;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener{
    private Button btnSearch;
    private RecyclerView recyclerView;
    private TextView tvTong;
    private SearchView searchView;
    private EditText dFrom, dTo;
    private Spinner sp;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        searchView = view.findViewById(R.id.search);
        dFrom = view.findViewById(R.id.dateFrom);
        dTo = view.findViewById(R.id.dateTo);
        sp = view.findViewById(R.id.spCategory);
        String[] arr = getResources().getStringArray(R.array.category);
        String[] arr1 = new String[arr.length + 1];
        arr1[0] = "All";
        for (int i = 0; i < arr.length; i++) {
            arr1[i + 1] = arr[i];
        }
        sp.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, arr1));
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Item> list = db.getAllItem();
        adapter.setList(list);
        tvTong.setText("Total: " + tong(list) + "K");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list = db.searchByName(s);
                tvTong.setText("Total: " + tong(list) + "K");
                adapter.setList(list);
                return true;
            }
        });
        dFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

                        dFrom.setText(date);
                    }
                }, year, month, day);

                dialog.show();
            }
        });
        dTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

                        dTo.setText(date);
                    }
                }, year, month, day);

                dialog.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = dFrom.getText().toString();
                String to = dTo.getText().toString();

                if(!from.isEmpty() && !to.isEmpty()) {
                    List<Item> l = db.searchByDateFromTo(from, to);

                    tvTong.setText("Tong tien: " + tong(l) + "K");
                    adapter.setList(l);
                }
                else
                    Toast.makeText(getContext(), "From and To Date is not allow empty", Toast.LENGTH_SHORT).show();
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate = sp.getItemAtPosition(position).toString();

                List<Item> li;

                if(cate.equalsIgnoreCase("All"))
                    li = db.getAllItem();
                else {
                    System.out.println(cate);
                    li = db.searchByDestination(cate);
                }
                tvTong.setText("Tong tien: " + tong(li) + "K");

                adapter.setList(li);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    private int tong(List<Item> list) {
        int sum = 0;
        for(Item i:list) {
            sum += Integer.parseInt(i.getPrice());
        }
        return sum;
    }
}

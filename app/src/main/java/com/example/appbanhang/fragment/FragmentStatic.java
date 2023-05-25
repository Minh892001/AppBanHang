package com.example.appbanhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.RecycleViewAdapter;
import com.example.appbanhang.adapter.StaticRecycleViewAdapter;
import com.example.appbanhang.dal.SQLiteHelper;
import com.example.appbanhang.model.Item;

import java.util.List;

public class FragmentStatic extends Fragment implements StaticRecycleViewAdapter.ItemListener, View.OnClickListener {
    private TextView tvTong;
    private RecyclerView recyclerView;
    private StaticRecycleViewAdapter adapter;
    private Button btDay, btYear, btMonth;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_static, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTong = view.findViewById(R.id.tvTong);
        recyclerView = view.findViewById(R.id.recycleView);
        btDay = view.findViewById(R.id.btnDay);
        btMonth = view.findViewById(R.id.btnMonth);
        btYear = view.findViewById(R.id.btnYear);
        adapter = new StaticRecycleViewAdapter();
        db = new SQLiteHelper(getContext());

        btDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> list = db.staticItemByDate();

                tvTong.setText("Total: " + tong(list) + "K");
                adapter.setList(list);
            }
        });

        btMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> list = db.staticItemByMonth();

                tvTong.setText("Total: " + tong(list) + "K");
                adapter.setList(list);
            }
        });

        btYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> list = db.staticItemByYear();

                tvTong.setText("Total: " + tong(list) + "K");
                adapter.setList(list);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();

        List<Item> list = db.staticItemByDate();
        adapter.setList(list);
    }

    private int tong(List<Item> list) {
        int sum = 0;
        for(Item i:list) {
            sum += i.getTotal();
        }
        return sum;
    }

    @Override
    public void onClick(View view) {

    }
}

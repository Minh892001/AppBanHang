package com.example.appbanhang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.UpdateActivity;
import com.example.appbanhang.adapter.RecycleViewAdapter;
import com.example.appbanhang.dal.SQLiteHelper;
import com.example.appbanhang.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private RecyclerView recycleView;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView = view.findViewById(R.id.recycleView);
        db = new SQLiteHelper(getContext());
        adapter = new RecycleViewAdapter();
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(sdf.format(today));
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(sdf.format(today));
        adapter.setList(list);
    }
}

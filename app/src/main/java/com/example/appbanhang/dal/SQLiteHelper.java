package com.example.appbanhang.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appbanhang.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "datve3.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, desFrom TEXT, desTo TEXT, price TEXT, date TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Item> getAllItem() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("items", null, null, null, null, null, order);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desFrom = rs.getString(2);
            String desTo = rs.getString(3);
            String price = rs.getString(4);
            String date = rs.getString(5);
            list.add(new Item(id, name, desFrom, desTo, price, date));
        }
        return list;
    }

    public List<Item> staticItemByMonth() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        String groupBy = "substr(date, 4)";
        Cursor rs = st.query("items", new String[]{ "*", "SUM(price)" }, null, null, groupBy, null, order);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desFrom = rs.getString(2);
            String desTo = rs.getString(3);
            String price = rs.getString(4);
            String date = rs.getString(5);
            String total = rs.getString(6);
            list.add(new Item(id, name, desFrom, desTo, price, date.substring(3), Integer.parseInt(total)));
        }
        return list;
    }

    public List<Item> staticItemByYear() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        String groupBy = "substr(date, 7)";
        Cursor rs = st.query("items", new String[]{ "*", "SUM(price)" }, null, null, groupBy, null, order);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desFrom = rs.getString(2);
            String desTo = rs.getString(3);
            String price = rs.getString(4);
            String date = rs.getString(5);
            String total = rs.getString(6);
            list.add(new Item(id, name, desFrom, desTo, price, date.substring(6), Integer.parseInt(total)));
        }
        return list;
    }

    public List<Item> staticItemByDate() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        String groupBy = "date";
        Cursor rs = st.query("items", new String[]{ "*", "SUM(price)" }, null, null, groupBy, null, order);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desFrom = rs.getString(2);
            String desTo = rs.getString(3);
            String price = rs.getString(4);
            String date = rs.getString(5);
            String total = rs.getString(6);
            list.add(new Item(id, name, desFrom, desTo, price, date, Integer.parseInt(total)));
        }
        return list;
    }

    public long addItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("desFrom", item.getDestinationFrom());
        values.put("desTo", item.getDestinationTo());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("items", null,values);
    }

    public List<Item> getByDate(String dateQuery) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date LIKE ?";
        String[] whereArgs = {dateQuery};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desFrom = rs.getString(2);
            String desTo = rs.getString(3);
            String price = rs.getString(4);
            String date = rs.getString(5);
            list.add(new Item(id, name, desFrom, desTo, price, date));
        }
        return list;
    }

    public int updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("desFrom", item.getDestinationFrom());
        values.put("desTo", item.getDestinationTo());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {item.getId() + ""};
        return db.update("items",values, whereClause, whereArgs);
    }

    public int deleteItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {id + ""};
        return db.delete("items", whereClause, whereArgs);
    }

    public List<Item> searchByName(String name) {
        List<Item> list = new ArrayList<>();

        String where = "name like ?";
        String[] args = {"%" + name + "%"};

        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("items", null, where, args, null, null, null);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString((5))));
        }

        return list;
    }

    public List<Item> searchByDestination(String destination) {
        List<Item> list = new ArrayList<>();

        String where = "desFrom like ? OR desTo like ?";
        String[] args = {"%" + destination + "%", destination};

        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("items", null, where, args, null, null, null);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }

        return list;
    }

    public List<Item> searchByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();

        String where = "date BETWEEN ? AND ?";
        String[] args = {from.trim(), to.trim()};

        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("items", null, where, args, null, null, null);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }

        return list;
    }
}

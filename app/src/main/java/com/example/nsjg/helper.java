package com.example.nsjg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class helper extends SQLiteOpenHelper {
    private static String db_name = "chocolateCo";
    private static SQLiteDatabase.CursorFactory factory =null;
    private static Integer version = 1;
    private String tbl_name = "tbl_produk";

    public helper(@Nullable Context context) {
        super(context, db_name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + this.tbl_name +  " (kode TEXT, nama TEXT, jenis TEXT, warna INTEGER, harga INTEGER)");
        db.execSQL("INSERT INTO " + this.tbl_name + " VALUES ('01', 'Kelompok2', 'Crispy Ice Cream', '1',18000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +this.tbl_name);
        onCreate(db);
    }
}

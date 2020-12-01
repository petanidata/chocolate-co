package com.example.nsjg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText pencarian,kode,nama,jenis,warna,harga;
    ListView listmk;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    FloatingActionButton tambah;
    helper helperank;
    SQLiteDatabase db;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    LayoutInflater inflater;
    View viewInflater;
    CardView cardCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<String>();

        helperank = new helper(getApplicationContext());

        db = helperank.getReadableDatabase();

        //Cursor cursor = db.rawQuery("SELECT * FROM tbl_mata_kuliah", null);
        Cursor cursor = db.query(
                "tbl_produk",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        while(! cursor.isAfterLast()){
            data.add(cursor.getString(0));

            cursor.moveToNext();
        }

        cursor.close();

        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                data
        );

        tambah = (FloatingActionButton) findViewById(R.id.tambah);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(MainActivity.this);

                inflater = getLayoutInflater();

                viewInflater = inflater.inflate(R.layout.custom_dialog, null);

                builder.setTitle("Form Pemesanan")
                        .setIcon(R.drawable.plus)
                        .setView(viewInflater)
                        .setCancelable(true)
                        .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                kode = viewInflater.findViewById(R.id.kode);
                                nama = viewInflater.findViewById(R.id.nama);
                                jenis = viewInflater.findViewById(R.id.jenis);
                                warna = viewInflater.findViewById(R.id.warna);
                                harga = viewInflater.findViewById(R.id.harga);

                                db = helperank.getWritableDatabase();

                                db.execSQL("INSERT INTO tbl_produk VALUES('"+ kode.getText().toString()+"','"+nama.getText().toString()+"','" + jenis.getText().toString()+ "','"+warna.getText().toString()+ "','"+ harga.getText().toString() + "')");

                                adapter.add(kode.getText().toString());
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                dialog = builder.create();

                dialog.show();
            }
        });

        cardCart = (CardView) findViewById(R.id.cardCart);
        cardCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
    }
}

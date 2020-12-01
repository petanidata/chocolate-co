package com.example.nsjg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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
                CartActivity.this,
                android.R.layout.simple_list_item_1,
                data
        );

        listmk = (ListView) findViewById(R.id.listmk);

        listmk.setAdapter(adapter);

        listmk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                /**/
                Cursor cursor = db.rawQuery(
                        "SELECT * FROM tbl_produk WHERE kode='" + adapter.getItem(position) + "'",
                        null
                );
                cursor.moveToFirst();

                Context context = getApplicationContext();
                CharSequence text = cursor.getString(0)+' '+cursor.getString(1)+' '+cursor.getString(2)+' '+cursor.getString(3)+' '+cursor.getString(4) ;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                /**/
                builder = new AlertDialog.Builder(CartActivity.this);

                inflater = getLayoutInflater();

                viewInflater = inflater.inflate(R.layout.custom_dialog, null);

                kode = viewInflater.findViewById(R.id.kode);
                nama = viewInflater.findViewById(R.id.nama);
                jenis = viewInflater.findViewById(R.id.jenis);
                warna = viewInflater.findViewById(R.id.warna);
                harga = viewInflater.findViewById(R.id.harga);


                cursor = db.rawQuery(
                        "SELECT * FROM tbl_produk WHERE kode='" + adapter.getItem(position) + "'",
                        null
                );

                cursor.moveToFirst();

                kode.setText(cursor.getString(0));
                nama.setText(cursor.getString(1));
                jenis.setText(cursor.getString(2));
                warna.setText(cursor.getString(3));
                harga.setText(cursor.getString(4));

                builder.setTitle("Form Pemesanan")
                        .setIcon(R.drawable.ic_edit)
                        .setView(viewInflater)
                        .setCancelable(true)
                        .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db = helperank.getWritableDatabase();

                                String id = kode.getText().toString();

                                db.execSQL("UPDATE tbl_produk SET kode='" +
                                        id + "', nama='" +
                                        nama.getText().toString() + "', jenis='" +
                                        jenis.getText().toString() + "', warna='" +
                                        warna.getText().toString() +  "', harga='" +
                                        harga.getText().toString() + "' WHERE kode='" +
                                        adapter.getItem(position) + "'");

                                adapter.remove(adapter.getItem(position));
                                adapter.add(id);
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

        listmk.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder = new AlertDialog.Builder(CartActivity.this);

                builder.setTitle("Konfirmasi Penghapusan")
                        .setIcon(R.drawable.ic_ask)
                        .setMessage("Apakah Anda yakin?")
                        .setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db = helperank.getWritableDatabase();

                                String id = adapter.getItem(position);

                                db.execSQL("DELETE FROM tbl_produk WHERE kode='" + id + "'");

                                adapter.remove(id);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                dialog = builder.create();

                dialog.show();

                return false;
            }
        });

        pencarian = (EditText) findViewById(R.id.cari);

        pencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tambah = (FloatingActionButton) findViewById(R.id.tambah);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(CartActivity.this);

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
    }
}

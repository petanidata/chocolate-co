package com.example.nsjg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button registerR, signinR;
    EditText nama, usernameR, email, passwordR, alamat, noHP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerR = (Button) findViewById(R.id.registerR);
        signinR = (Button) findViewById(R.id.signInR);
        nama =  (EditText) findViewById(R.id.nama);
        usernameR = (EditText) findViewById(R.id.usernameR);
        email = (EditText) findViewById(R.id.email);
        passwordR = (EditText) findViewById(R.id.passwordR);
        alamat = (EditText) findViewById(R.id.alamat);
        noHP = (EditText) findViewById(R.id.noHP);

        registerR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });


        //nyoba validasi data
        /*
        registerR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = nama.getText().toString();
                String username = usernameR.getText().toString();
                String password = passwordR.getText().toString();

                if (n.equals("") || username.equals("")) {
                    if (username.equals("")) {
                        username.setError("Username harus diisi");
                        username.requestFocus();
                    }

                    if (password.equals("")) {
                        password.setError("Password harus diisi");
                        password.requestFocus();
                    }
                } else {
                    if (username.toUpperCase().equals("KELOMPOK2") && password.equals("123")) {
                        SharedPreferences sp = getSharedPreferences("statusLogin", MODE_PRIVATE);

                        SharedPreferences.Editor e = sp.edit();

                        e.putString("username", username);

                        e.commit();

                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Pengguna tidak terdaftar", Toast.LENGTH_LONG).show();

                        usernameR.setText("");
                        passwordR.setText("");
                        usernameR.requestFocus();
                    }
                }
            }
        });
        */

        signinR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}

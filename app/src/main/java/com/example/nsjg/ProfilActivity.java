package com.example.nsjg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfilActivity extends AppCompatActivity {

    Button signOutP;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        sp = getSharedPreferences("statusLogin",MODE_PRIVATE);

        signOutP = (Button) findViewById(R.id.signOutP);

        signOutP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor e = sp.edit();

                e.clear();
                e.commit();

                Intent i = new Intent(ProfilActivity.this, LoginActivity.class);

                startActivity(i);
            }
        });
    }
}

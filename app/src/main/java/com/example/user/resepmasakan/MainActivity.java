package com.example.user.resepmasakan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnresep=(Button) findViewById(R.id.btnresep);
        Button btnrekomendasi = (Button) findViewById(R.id.btnrekomendasi);
        btnresep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View resep) {
                Intent i=new Intent(getApplicationContext(),ResepActivity.class);
                startActivity(i);
            }
        });
        btnrekomendasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LokasiActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.example.user.resepmasakan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import android.widget.LinearLayout;

public class ResepActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);
        CardView cardview_makanan=(CardView) findViewById(R.id.resepmakanan);
        cardview_makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View kategori) {
                Intent i=new Intent(getApplicationContext(),kategori_makanan.class);
                startActivity(i);
            }
        });
        CardView cardview_minuman=(CardView) findViewById(R.id.resepminuman);
        cardview_minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),kategori_minuman.class);
                startActivity(i);

            }


    });
        CardView cardview_youtube=(CardView) findViewById(R.id.resepvideo);
        cardview_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),YoutubeActivity.class);
                startActivity(i);

            }


        });
    }
}

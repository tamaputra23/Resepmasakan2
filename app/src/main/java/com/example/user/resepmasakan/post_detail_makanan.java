package com.example.user.resepmasakan;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;


public class post_detail_makanan extends AppCompatActivity {
    TextView mTitleTv, mDetailTv, mDeskripsiTv, mCaraTv, mSumberTv;
    ImageView mImageIv;
    Bitmap bitmap;
    Button mSaveBtn, mShareBtn, mWallBtn;
    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;
    String toko_kopi_seduh = "-6.3548118,106.8405373"; // koordinat Masjid Agung Demak
    /*Deklarasi variable*/
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_makanan);
        mTitleTv = (TextView) findViewById(R.id.title_detail_makanan);
        mImageIv = (ImageView) findViewById(R.id.img_detail_makanan);
        mDetailTv = (TextView) findViewById(R.id.deskripsi_bahan_detail_makanan);
        mDeskripsiTv = (TextView) findViewById(R.id.deskripsi_detail_makanan);
        mCaraTv = (TextView) findViewById(R.id.deskripsi_cara_detail_makanan);
        mSumberTv = (TextView) findViewById(R.id.sumber_detail_makanan);

        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        String title =getIntent().getStringExtra("title");
        String bahan = getIntent().getStringExtra("bahan");
        String deksripsi = getIntent().getStringExtra("deskripsi");
        String cara = getIntent().getStringExtra("cara");
        String sumber = getIntent().getStringExtra("sumber");
        mSumberTv.setText(sumber);
        mCaraTv.setText(cara);
        mDeskripsiTv.setText(deksripsi);
        mTitleTv.setText(title);
        mDetailTv.setText(bahan);
        mImageIv.setImageBitmap(bmp);
        bitmap = ((BitmapDrawable)mImageIv.getDrawable()).getBitmap();


    }
}
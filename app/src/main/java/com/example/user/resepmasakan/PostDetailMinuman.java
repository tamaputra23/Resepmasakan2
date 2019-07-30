package com.example.user.resepmasakan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PostDetailMinuman extends AppCompatActivity {
    TextView mTitleTv, mDetailTv, mDeskripsiTv, mCaraTv, mSumberTv;
    ImageView mImageIv;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_minuman);
        mTitleTv = (TextView) findViewById(R.id.title_detail_minuman);
        mImageIv = (ImageView) findViewById(R.id.img_detail_minuman);
        mDetailTv = (TextView) findViewById(R.id.deskripsi_bahan_detail_minuman);
        mDeskripsiTv = (TextView) findViewById(R.id.deskripsi_detail_minuman);
        mCaraTv = (TextView) findViewById(R.id.deskripsi_cara_detail_minuman);
        mSumberTv = (TextView) findViewById(R.id.sumber_detail_minuman);

        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        String title =getIntent().getStringExtra("title");
        String bahan = getIntent().getStringExtra("bahan");
        String deksripsi = getIntent().getStringExtra("deskripsi");
        String cara = getIntent().getStringExtra("cara");
        String sumber = getIntent().getStringExtra("sumber");
        mSumberTv.setText(sumber);
        mCaraTv.setText(cara);
        mDetailTv.setText(bahan);
        mDeskripsiTv.setText(deksripsi);
        mTitleTv.setText(title);
        mImageIv.setImageBitmap(bmp);
        bitmap = ((BitmapDrawable)mImageIv.getDrawable()).getBitmap();
    }
}

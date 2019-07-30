package com.example.user.resepmasakan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class LokasiActivity extends AppCompatActivity {
    private RecyclerView lokasilist;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("lokasi");

        lokasilist = (RecyclerView) findViewById(R.id.recyclerview_id_lokasi);
        lokasilist.setHasFixedSize(true);
        lokasilist.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<lokasi, LokasiActivity.ViewHolder> Adapter = new FirebaseRecyclerAdapter<lokasi, LokasiActivity.ViewHolder>
                (lokasi.class, R.layout.cardview_item_lokasi, LokasiActivity.ViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(LokasiActivity.ViewHolder viewHolder, lokasi model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getAlamat(), model.getHarga(), model.getJambuka(), model.getImage(), model.getKeyloc(), model.getKoorlat(), model.getKoorlong() );
                final String mLokasi = model.getKeyloc();
                Double mKoorlat = model.getKoorlat();
                final String sKoorlat = Double.toString(mKoorlat);
                Double mKoorlong = model.getKoorlong();
                final String sKoorlong = Double.toString(mKoorlong);
                viewHolder.mLokasiBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Intent mapIntent = new Intent(view.getContext(),MapsActivity.class);
                    mapIntent.putExtra("keyloc", mLokasi);
                    mapIntent.putExtra("koorlat",sKoorlat);
                    mapIntent.putExtra("koorlong", sKoorlong);
                    startActivity(mapIntent);
                    }
                });
            }
        };
        lokasilist.setAdapter(Adapter);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button mLokasiBtn;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mLokasiBtn = itemView.findViewById(R.id.btn_lokasi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            });
            //item long click
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mClickListener.onItemLongClick(view, getAdapterPosition());
                    return true;
                }
            });


        }
        public void setDetails(Context ctx, String title, String alamat, String harga, String jambuka, String image, String keyloc, Double koorlat, Double koorlong){
            TextView mTitleTv = mView.findViewById(R.id.judul_lokasi);
            ImageView mImageIv = mView.findViewById(R.id.gambar_lokasi);
            TextView mAlamatTv = mView.findViewById(R.id.alamat_lokasi);
            TextView mHargaTv = mView.findViewById(R.id.harga_lokasi);
            TextView mJambukaTv = mView.findViewById(R.id.jambuka_lokasi);
            mTitleTv.setText(title);
            mAlamatTv.setText(alamat);
            mHargaTv.setText(harga);
            mJambukaTv.setText(jambuka);
            Picasso.with(ctx).load(image).into(mImageIv);
        }

        private LokasiActivity.ViewHolder.ClickListener mClickListener;
        //interface to send callbacks
        public interface ClickListener{
            void onItemClick(View view, int position);
            void onItemLongClick(View  view, int position);
        }
        public void setOnClickListener(LokasiActivity.ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }
    }

}

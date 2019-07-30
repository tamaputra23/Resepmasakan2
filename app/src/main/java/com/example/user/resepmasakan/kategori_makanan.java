package com.example.user.resepmasakan;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;


public class kategori_makanan extends AppCompatActivity {
    private RecyclerView makananlist;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_makanan);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Makanan");

        makananlist = (RecyclerView) findViewById(R.id.recyclerview_id_makanan);
        makananlist.setHasFixedSize(true);
        makananlist.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<makanan, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<makanan, ViewHolder>
                (makanan.class,
                        R.layout.cardview_item_makanan,
                        ViewHolder.class,
                        mRef) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, makanan model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getDeskripsi(), model.getCara(), model.getBahan(), model.getSumber(), model.getImage());
                String mCara = model.getCara();
            }
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener(){

                    @Override
                    public void onItemClick(View view, int position) {
                        TextView mTitleTv = view.findViewById(R.id.judul_makanan);
                        ImageView mImage = view.findViewById(R.id.gambar_makanan);
                        TextView mBahanTv = view.findViewById(R.id.bahan_makanan);
                        TextView mDeskripsiTv = view.findViewById(R.id.deskripsi_makanan);
                        TextView mCaraTv = view.findViewById(R.id.cara_makanan);
                        TextView mSumberTv = view.findViewById(R.id.sumber_makanan);

                        String mSumber = mSumberTv.getText().toString();
                        String mCara = mCaraTv.getText().toString();
                        String mDesk = mDeskripsiTv.getText().toString();
                        String mTitle = mTitleTv.getText().toString();
                        String mBahan = mBahanTv.getText().toString();
                        Drawable mDrawable = mImage.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();



                        Intent intent = new Intent(view.getContext(), post_detail_makanan.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra("deskripsi", mDesk);
                        intent.putExtra("image",bytes);
                        intent.putExtra("title", mTitle);
                        intent.putExtra("cara", mCara);
                        intent.putExtra("bahan", mBahan);
                        intent.putExtra("sumber", mSumber);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }
                );
                return viewHolder;
            }


        };
        makananlist.setAdapter(firebaseRecyclerAdapter);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
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
        public void setDetails(Context ctx,String title, String deskripsi, String cara, String bahan, String sumber, String image){
            TextView mTitleTv = mView.findViewById(R.id.judul_makanan);
            ImageView mImageIv = mView.findViewById(R.id.gambar_makanan);
            TextView  mBahan = mView.findViewById(R.id.bahan_makanan);
            TextView mDskripsi = mView.findViewById(R.id.deskripsi_makanan);
            TextView mCara = mView.findViewById(R.id.cara_makanan);
            TextView mSumber = mView.findViewById(R.id.sumber_makanan);
            mSumber.setText(sumber);
            mCara.setText(cara);
            mDskripsi.setText(deskripsi);
            mBahan.setText(bahan);
            mTitleTv.setText(title);
            Picasso.with(ctx).load(image).into(mImageIv);
        }


        private ViewHolder.ClickListener mClickListener;
        //interface to send callbacks
        public interface ClickListener{
            void onItemClick(View view, int position);
            void onItemLongClick(View  view, int position);
        }
        public void setOnClickListener(ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }
    }
}
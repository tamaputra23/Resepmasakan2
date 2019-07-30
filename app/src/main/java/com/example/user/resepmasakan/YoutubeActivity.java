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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Vector;

public class YoutubeActivity extends AppCompatActivity {

    private RecyclerView youtubelist;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    RecyclerView recyclerView;
    public static final String API_KEY = "AIzaSyCzmM_Fh3CxZMdXyFkauBrPz0gdjYGsPuA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Resep");

        youtubelist = (RecyclerView) findViewById(R.id.recyclerView);
        youtubelist.setHasFixedSize(true);
        youtubelist.setLayoutManager( new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<VideoAdapter, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<VideoAdapter, ViewHolder>
                (VideoAdapter.class,
                        R.layout.cardview_item_youtube,
                        ViewHolder.class,
                        mRef) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, final VideoAdapter model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getTitle(),  model.getVideoUrl(), model.getImage(), model.getSumber());
                /*  initialize the thumbnail image view , we need to pass Developer Key */
                viewHolder.videoThumbnailImageView.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        //when initialization is sucess, set the video id to thumbnail to load
                        youTubeThumbnailLoader.setVideo(model.getVideoUrl());

                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                                youTubeThumbnailLoader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                                //print or show error when thumbnail load failed
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        //print or show error when initialization failed
                    }
                });
            }
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener(){

                                                  @Override
                                                  public void onItemClick(View view, int position) {

                                                      TextView mVideoid   = view.findViewById(R.id.videoId_Tv);
                                                      String mVideo = mVideoid.getText().toString();
                                                      Intent intent = new Intent(view.getContext(), YoutubePlayer.class);
                                                      intent.putExtra("videoid", mVideo);
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
        youtubelist.setAdapter(firebaseRecyclerAdapter);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public YouTubeThumbnailView videoThumbnailImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            videoThumbnailImageView = itemView.findViewById(R.id.video_thumbnail_image_view);
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
        public void setDetails(Context ctx,String title, String videoUrl, String image, String sumber){
            TextView mTitleTv = mView.findViewById(R.id.judul_youtube);
            //ImageView mImageIv = mView.findViewById(R.id.gambar_youtube);
            TextView mVideoid   = mView.findViewById(R.id.videoId_Tv);
            TextView mSumberTv   = mView.findViewById(R.id.video_sumber);
            mSumberTv.setText(sumber);
            mVideoid.setText(videoUrl);
            mTitleTv.setText(title);
            //Picasso.with(ctx).load(image).into(mImageIv);
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

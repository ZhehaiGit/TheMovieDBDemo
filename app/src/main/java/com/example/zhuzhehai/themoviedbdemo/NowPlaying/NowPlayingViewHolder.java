package com.example.zhuzhehai.themoviedbdemo.NowPlaying;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhuzhehai.themoviedbdemo.Models.BaseViewHolder;
import com.example.zhuzhehai.themoviedbdemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;


public class NowPlayingViewHolder extends BaseViewHolder {
    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.movie_image)
    SimpleDraweeView movieImage;
    @BindView(R.id.movie_popularity)
    TextView moviePopularity;
    @BindView(R.id.movie_genre)
    TextView movieGenre;
    @BindView(R.id.progress)
    ProgressBar process;

    public NowPlayingViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}

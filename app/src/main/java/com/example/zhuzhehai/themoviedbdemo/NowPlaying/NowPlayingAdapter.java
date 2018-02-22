package com.example.zhuzhehai.themoviedbdemo.NowPlaying;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.zhuzhehai.themoviedbdemo.Models.BaseViewHolder;
import com.example.zhuzhehai.themoviedbdemo.Models.InfinitAdapter;
import com.example.zhuzhehai.themoviedbdemo.Models.movie;
import com.example.zhuzhehai.themoviedbdemo.R;
import com.example.zhuzhehai.themoviedbdemo.utils.ImageUtils;

import java.util.List;


public class NowPlayingAdapter extends InfinitAdapter<movie> {

    private List<movie> items;
    private final Fragment fragment;


    public NowPlayingAdapter(@NonNull Fragment content, @NonNull List<movie> items, @NonNull InfinitAdapter.LoadMoreListener loadMoreListener) {
        super(content.getContext(), items, loadMoreListener);
        this.items = items;
        this.fragment = content;

    }

    @Override
    protected BaseViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.recycle_shot, parent, false);
        return new NowPlayingViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(BaseViewHolder holder, int position) {
        final movie item = items.get(position);
        NowPlayingViewHolder mholder = (NowPlayingViewHolder) holder;
        final ProgressBar pbar = mholder.process;

        mholder.movieName.setText( item.title);
        mholder.moviePopularity.setText( item.popularity);
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < item.genre_ids.length; i++) {
            b.append(item.genre_ids[i]);
            b.append(" ");
        }
        mholder.movieGenre.setText(b.toString());
        ImageUtils.loadShotImage(item, mholder.movieImage);

    }



}

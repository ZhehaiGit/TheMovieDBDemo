package com.example.zhuzhehai.themoviedbdemo.Upcoming;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhuzhehai.themoviedbdemo.Load.Load;
import com.example.zhuzhehai.themoviedbdemo.Load.LoadException;
import com.example.zhuzhehai.themoviedbdemo.Models.InfinitAdapter;
import com.example.zhuzhehai.themoviedbdemo.Models.ModelUtils;
import com.example.zhuzhehai.themoviedbdemo.Models.genre;
import com.example.zhuzhehai.themoviedbdemo.Models.movie;
import com.example.zhuzhehai.themoviedbdemo.NowPlaying.NowPlayingAdapter;
import com.example.zhuzhehai.themoviedbdemo.R;
import com.example.zhuzhehai.themoviedbdemo.utils.SpaceItemDecoration;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuzhehai on 2/21/18.
 */

public class UpcomingFragment extends Fragment {
    private NowPlayingAdapter adapter;
    public static final int LIST_TYPE_SHOT = 11;
    public static final String KEY_LIST_TYPE = "listType";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;


    public static UpcomingFragment newInstance() {
        Bundle args = new Bundle();
        UpcomingFragment fragment = new UpcomingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_recycler_view, container, false);
        ButterKnife.bind(this, view); //bind(target, source)
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //set refresh at first false;

        swipeRefreshLayout.setEnabled(false);
        //when loadShotsTask is true, gte more data using AsyncTask.
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataTask(true).execute();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

        adapter = new NowPlayingAdapter(this, new ArrayList<movie>(), new InfinitAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                new LoadDataTask(false).execute();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private class LoadDataTask extends AsyncTask<Void, Void, List<movie>> {

        private boolean refresh;
        private IOException exception;
        private List<genre> genres;
        private List<movie> movies;



        private LoadDataTask(boolean refresh) {
            this.refresh = refresh;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<movie> doInBackground(Void... voids) {

            int page = refresh ?  1  : adapter.getData().size() / 10 + 1;

            try {
                movies = Load.getMovie("upcoming", page).results;
//                genres = Load.getGenre();
                genres  = ModelUtils.read(getContext(), "genre", new TypeToken<List<genre>>(){});
                Log.v("xxxx", genres.size() + "");
                return movies;
            } catch (LoadException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<movie> result) {
            if (exception != null) {
                onFailed(exception);
            } else {
                onSuccess(result);
            }
        }

        protected void onSuccess(List<movie> result) {
            Log.v("sizesize", result.size() + "");
            HashMap<String, String > map = new HashMap<>();
            for (int i = 0; i < genres.size(); i++) {
                genre g = genres.get(i);
                map.put(g.id, g.name);
            }
            for (int i = 0; i < movies.size(); i++) {
                String[] g_id  = movies.get(i).genre_ids;
                for (int j = 0; j < g_id.length; j++) {
                    String id = g_id[j];
                    if (map.containsKey(id)) {
                        g_id[j] = map.get(id);
                    }
                }
            }
            adapter.setShowLoading(result.size() >= 10);
            if (refresh) {
                swipeRefreshLayout.setRefreshing(false);
                adapter.setData(result);
            } else {
                swipeRefreshLayout.setEnabled(true);
                adapter.append(result);
            }
        }

        protected void onFailed(IOException e) {
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
            //snackbar will show a brief message at the bottom of the screen
        }


    }


}

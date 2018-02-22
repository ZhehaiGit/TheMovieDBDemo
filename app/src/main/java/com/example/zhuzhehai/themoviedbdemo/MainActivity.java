package com.example.zhuzhehai.themoviedbdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.zhuzhehai.themoviedbdemo.Load.Load;
import com.example.zhuzhehai.themoviedbdemo.Load.LoadException;
import com.example.zhuzhehai.themoviedbdemo.Models.ModelUtils;
import com.example.zhuzhehai.themoviedbdemo.Models.genre;
import com.example.zhuzhehai.themoviedbdemo.Models.movie;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    FragmentManager fm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        new LoadGenreData().execute();



    }

    private class LoadGenreData extends AsyncTask<Void, Void, List<genre>> {

        private boolean refresh;
        private IOException exception;

        private List<genre> genres;


        private LoadGenreData() {
        }
        @Override
        protected void onPreExecute() {
            progress= new ProgressDialog(MainActivity.this);
            progress.setCancelable(false);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected List<genre> doInBackground(Void... voids) {


            try {
                genres = Load.getGenre().genres;
                return genres;
            } catch (LoadException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(List<genre> result) {
            if (exception != null) {
                onFailed(exception);
            } else {
                onSuccess(result);
                progress.dismiss();
            }
        }

        protected void onSuccess(List<genre> result) {
            Log.v("xxxx", result.size() + "");
            ModelUtils.save(MainActivity.this, "genre", result);
            ViewPager pager =   findViewById(R.id.pager);
            pager.setAdapter(new MainActivityPagerAdapter(MainActivity.this, fm));
            pager.setOffscreenPageLimit(2);

            TabLayout tabLayout =  findViewById(R.id.tabs);
            tabLayout.bringToFront();
            tabLayout.setupWithViewPager(pager);

        }

        protected void onFailed(IOException e) {
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    public void exit() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        finish();

        super.onBackPressed();
    }

}

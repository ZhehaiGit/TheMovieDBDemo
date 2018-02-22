package com.example.zhuzhehai.themoviedbdemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.example.zhuzhehai.themoviedbdemo.NowPlaying.NowPlayingFragment;
import com.example.zhuzhehai.themoviedbdemo.Upcoming.UpcomingFragment;

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    public static final int ID_DEFAULT = 0;
    public static final int ID_STYLED = 1;


    private Context context;


    FragmentManager fragmentManager;
    Fragment[] fragments;
    


    public MainActivityPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
        fragments = new Fragment[2];
        this.context = context;
    }



    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case ID_DEFAULT:
                    fragment = NowPlayingFragment.newInstance();
                break;
            case ID_STYLED:
                    fragment = UpcomingFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {


        switch (position) {
            case 0:
                return "Now Playing";
            case 1:
                return "Upcoming";
        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Fragment fragment = getItem(position);
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.add(container.getId(),fragment,"fragment:"+position);
        trans.commit();

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        assert(0 <= position && position < fragments.length);
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.remove(fragments[position]);
        trans.commit();
        fragments[position] = null;
    }
}
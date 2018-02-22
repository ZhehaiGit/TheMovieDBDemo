package com.example.zhuzhehai.themoviedbdemo.Load;

import android.util.Log;

import com.example.zhuzhehai.themoviedbdemo.Models.ModelUtils;
import com.example.zhuzhehai.themoviedbdemo.Models.genre;
import com.example.zhuzhehai.themoviedbdemo.Models.genres;
import com.example.zhuzhehai.themoviedbdemo.Models.movie;
import com.example.zhuzhehai.themoviedbdemo.Models.mvbd;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Load {

    private static final String TAG = "BA API";
    public static final int COUNT_PER_LOAD = 12;



    private static final String API_URL = "https://api.themoviedb.org/3/movie/";

    private static final String API_End = "api_key=c095f781a55b6c0b7c3b28fce5a6a234&language=en-US&";

    private static final String API_genre = "https://api.themoviedb.org/3/genre/movie/list?api_key=c095f781a55b6c0b7c3b28fce5a6a234&language=en-US";



    private static final TypeToken<mvbd> movie_infor = new TypeToken<mvbd>(){};
    private static final TypeToken<List<genre>> genre_infor = new TypeToken<List<genre>>(){};
    private static final TypeToken<genres> genre_infor2 = new TypeToken<genres>(){};


    private final static OkHttpClient client = new OkHttpClient();

    private static Response makeRequest(Request request) throws LoadException {
        try {
            // 向网站发送请求(OkHttpClient)
            OkHttpClient eagerClient = client.newBuilder()
                    .readTimeout(500, TimeUnit.MILLISECONDS)
                    .build();
            return eagerClient.newCall(request).execute();
        } catch (IOException e) {
            throw new LoadException(e.getMessage());
        }
    }

    private static Response makeGetRequest(String url) throws LoadException {

        Request request = new Request.Builder().url(url).build(); //通过authRequest build request;
        return makeRequest(request);// 通过request 向网站发送请求(makeRequest) 得到response;
    }

    //解析网站传回的数据 得到需要的data. 传入 response 和 type
    private static <T> T parseResponse(Response response,
                                       TypeToken<T> typeToken) throws LoadException {
        String responseString;
        try {
            responseString = response.body().string();//get string
        } catch (IOException e) {
            throw new LoadException(e.getMessage());
        }

        try {

            return ModelUtils.toObject(responseString, typeToken);//string to object
        } catch (JsonSyntaxException e) {
            throw new LoadException(responseString);
        }
    }

    public static mvbd getMovie(String type , int page) throws LoadException {

        String url = API_URL + type + "?" + API_End + "page=" + page;
        Log.v("uuuuuurl", url);
        return parseResponse(makeGetRequest(url), movie_infor);
    }

    public static genres getGenre() throws LoadException {
        String url = API_genre;
        Log.v("uuuuuurl", url);
        return parseResponse(makeGetRequest(url), genre_infor2);
    }
}

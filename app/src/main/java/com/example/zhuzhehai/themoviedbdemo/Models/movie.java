package com.example.zhuzhehai.themoviedbdemo.Models;

import android.support.annotation.Nullable;

/**
 * Created by zhuzhehai on 2/21/18.
 */

public class movie {
    public String title;
    public String popularity;
    public String backdrop_path;
    public String[] genre_ids;


    @Nullable
    public String getImageUrlBackPath() {
        return backdrop_path;
    }



//     "vote_count": 419,
//             "id": 337167,
//             "video": false,
//             "vote_average": 6.8,
//             "title": "Fifty Shades Freed",
//             "popularity": 443.09654,
//             "poster_path": "/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg",
//             "original_language": "en",
//             "original_title": "Fifty Shades Freed",
//             "genre_ids": [
//             18,
//             10749
//             ],
//             "backdrop_path": "/9ywA15OAiwjSTvg3cBs9B7kOCBF.jpg",
//             "adult": false,
//             "overview": "Believing they have left behind shadowy figures from their past, newlyweds Christian and Ana fully embrace an inextricable connection and shared life of luxury. But just as she steps into her role as Mrs. Grey and he relaxes into an unfamiliar stability, new threats could jeopardize their happy ending before it even begins.",
//             "release_date": "2018-02-07"
}

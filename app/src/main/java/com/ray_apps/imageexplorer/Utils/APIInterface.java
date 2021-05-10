package com.ray_apps.imageexplorer.Utils;

import com.ray_apps.imageexplorer.Models.SearchImage;
import com.ray_apps.imageexplorer.Models.UnsplashImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import io.reactivex.Observable;

//An Interface Handling queries using get api
public interface APIInterface {
    //you can get your own access key from here: https://unsplash.com/developers
    String header = "Authorization: Client-ID LtcOlNtG0I8Pd21Qhg9-M-VmmJ6ckAwhyVFyUUu3oh0";

    @Headers(header)
    @GET("photos")
    Observable<List<UnsplashImage>> getPhotos();

    @Headers(header)
    @GET("photos")
    Observable<List<UnsplashImage>> getNextPhotos(@Query("page") int page);

    @Headers(header)
    @GET("search/photos")
    Observable<SearchImage> searchPhotos(@Query("query") String query);


    @Headers(header)
    @GET("search/photos")
    Observable<SearchImage> getNextSearchPhotos(@Query("query") String query, @Query("page") int page);

    @Headers(header)
    @POST("photos/:id/like")
    Call<Boolean> likeImage(@Body Boolean like, @Query("id") String ID);
}


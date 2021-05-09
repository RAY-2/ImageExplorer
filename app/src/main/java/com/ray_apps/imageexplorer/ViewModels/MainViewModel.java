package com.ray_apps.imageexplorer.ViewModels;

import android.app.Application;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ray_apps.imageexplorer.Models.SearchImage;
import com.ray_apps.imageexplorer.Models.UnsplashImage;
import com.ray_apps.imageexplorer.Utils.APIClient;
import com.ray_apps.imageexplorer.Utils.APIInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private APIInterface api;
    private List<UnsplashImage> imageList = new ArrayList<>();
    private MutableLiveData<List<UnsplashImage>> liveImages;
    private int BASIC_PAGE;
    private int SEARCH_PAGE;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void initViewModel(){
        if(liveImages == null){
            liveImages = new MutableLiveData<>();
        }
        api = APIClient.getClient().create(APIInterface.class);

        getBasicImages();
        getBasicPagination();
        getBasicPagination();
        getBasicPagination();
    }


    private void getBasicImages(){
        BASIC_PAGE = 1;
        Observable<List<UnsplashImage>> observable = api.getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        CompositeDisposable cp = new CompositeDisposable();
        cp.add(observable.subscribe(images -> {
            imageList.addAll(images);
            liveImages.postValue(imageList);
        }));
        BASIC_PAGE ++;
    }

    public void getBasicPagination(){
        Observable<List<UnsplashImage>> observable = api.getNextPhotos(BASIC_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        CompositeDisposable cp = new CompositeDisposable();
        cp.add(observable.subscribe(images -> {
            imageList.addAll(images);
            liveImages.postValue(imageList);
        }));
        BASIC_PAGE ++;
    }


    public void getSearchImages(String query){
        SEARCH_PAGE = 1;
        Observable<SearchImage> observable = api.searchPhotos(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        CompositeDisposable cp = new CompositeDisposable();
        cp.add(observable.subscribe(images -> {
            imageList.clear();
            imageList.addAll(images.getImageList());
            liveImages.postValue(imageList);

            for(UnsplashImage image : imageList){
                Log.d("ViewModel:", image.getUrls().getThumb());
            }
        }));
        SEARCH_PAGE ++;

        getSearchPagination(query);
        getSearchPagination(query);
        getSearchPagination(query);

    }

    public void getSearchPagination(String query)
    {
        Observable<SearchImage> observable = api.getNextSearchPhotos(query, SEARCH_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        CompositeDisposable cp = new CompositeDisposable();
        cp.add(observable.subscribe(images -> {
            imageList.addAll(images.getImageList());
            liveImages.postValue(imageList);
        }));
        SEARCH_PAGE ++;
    }

    public LiveData<List<UnsplashImage>> getLiveImages(){
        return liveImages;
    }
}

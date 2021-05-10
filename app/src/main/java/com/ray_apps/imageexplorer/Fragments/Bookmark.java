package com.ray_apps.imageexplorer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ray_apps.imageexplorer.Adaptors.MyAdaptor;
import com.ray_apps.imageexplorer.R;
import com.ray_apps.imageexplorer.ViewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends Fragment{

    View view;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MyAdaptor adapter;
    private MainViewModel viewModel;
    private NestedScrollView nestedScrollView;

    private static final int SPAN_COUNT = 3;
    private List<String> myBookmarkList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        createBookmarkList();
    }

//    private void initAdapter(){
//
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.setAdapter(adapter);
//    }


//    private void initView(){
//        progressBar = view.findViewById(R.id.progress_bar_bookmark);
//        recyclerView = view.findViewById(R.id.recycler_view_bookmark);
//        progressBar.setVisibility(View.VISIBLE);
//
//        nestedScrollView = view.findViewById(R.id.scroll_view_bookmark);
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
//
//    }

//    private void createBookmarkList() {
//        myBookmarkList = new ArrayList<String>();
//
//        SharedPreferences sharedPreferences;
//        sharedPreferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//
//        Gson gson = new Gson();
//        String response=sharedPreferences.getString(KEY , "");
//        Log.d("RAY: ", "response = " + response);
//
//        myBookmarkList = gson.fromJson(response,
//                new TypeToken<List<String>>(){}.getType());
//
//        if(myBookmarkList != null)
//            for(int i=0;i<myBookmarkList.size();i++)
//            {
//                Log.d("RAY: ", myBookmarkList.get(i));
//            }
//    }

}
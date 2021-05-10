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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ray_apps.imageexplorer.Interfaces.BookmarkManager;
import com.ray_apps.imageexplorer.ViewModels.MainViewModel;
import com.ray_apps.imageexplorer.Adaptors.MyAdaptor;
import com.ray_apps.imageexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class Discover extends Fragment implements BookmarkManager {
    View view;


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MyAdaptor adapter;
    private MainViewModel viewModel;
    private NestedScrollView nestedScrollView;

    private Button bookmarkButton;

    List<String> myBookmarkList;
    private static final String KEY = "KEY";

    private static final int SPAN_COUNT = 3;
    //to handle loading images if search is ongoing or stopped
    private Boolean SEARCHING = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_discover, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize viewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.initViewModel();

        //initialize view and observe Livedata
        initView();
        observeLiveData();
        createBookmarkList();
    }



    private void observeLiveData(){
        viewModel.getLiveImages().observe(getViewLifecycleOwner(), images -> {
            progressBar.setVisibility(View.GONE);

            if(adapter == null){
                initAdapter();
            } else {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initAdapter(){
        adapter = new MyAdaptor(getContext(), viewModel.getLiveImages().getValue(),this);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void initView(){
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar.setVisibility(View.VISIBLE);

        SearchView search = view.findViewById(R.id.searchView);
        nestedScrollView = view.findViewById(R.id.scroll_view);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                viewModel.getSearchImages(query);
                SEARCHING = true;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SEARCHING = false;
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        //A function to handle Infinite Pagination
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    if(SEARCHING)
                    {
                        //Add Images to existing search result
                        viewModel.getSearchPagination(search.getQuery().toString());
                    }
                    else
                    {
                        //add images to existing basic Image page
                        viewModel.getBasicPagination();
                    }
                }
            }
        });

    }

    private void createBookmarkList() {
        myBookmarkList = new ArrayList<String>();

        SharedPreferences sharedPreferences;
        sharedPreferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String response=sharedPreferences.getString(KEY , "");
        Log.d("RAY: ", "response = " + response);

        myBookmarkList = gson.fromJson(response,
                new TypeToken<List<String>>(){}.getType());

        if(myBookmarkList != null)
            for(int i=0;i<myBookmarkList.size();i++)
            {
                Log.d("RAY: ", myBookmarkList.get(i));
            }
    }

    @Override
    public void addToBookmarks(String string) {

        //find if already in bookmarks
        if(myBookmarkList == null)
            myBookmarkList = new ArrayList<>();


        for(int i=0;i<myBookmarkList.size();i++)
        {
            if(string.equals(myBookmarkList.get(i)))
            {
                Toast.makeText(getContext(),"Image already added to bookmarks",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        myBookmarkList.add(string);
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = gson.toJson(myBookmarkList);

        editor = sharedPreferences.edit();
        editor.remove(KEY).commit();
        editor.putString(KEY, json);
        editor.commit();

        Toast.makeText(getContext(),"Image added to bookmarks",Toast.LENGTH_SHORT).show();
    }
}
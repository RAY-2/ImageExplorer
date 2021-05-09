package com.ray_apps.imageexplorer.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ray_apps.imageexplorer.ViewModels.MainViewModel;
import com.ray_apps.imageexplorer.Adaptors.MyAdaptor;
import com.ray_apps.imageexplorer.R;

public class Discover extends Fragment {
    View view;


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MyAdaptor adapter;
    private MainViewModel viewModel;
    private NestedScrollView nestedScrollView;


    private static final int SPAN_COUNT = 4;
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
        adapter = new MyAdaptor(getContext(), viewModel.getLiveImages().getValue());

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void initView(){
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar.setVisibility(View.VISIBLE);

        EditText et_search = view.findViewById(R.id.et_search);
        Button btn_search = view.findViewById(R.id.btn_search);
        nestedScrollView = view.findViewById(R.id.scroll_view);

        btn_search.setOnClickListener(v -> {
            if(!SEARCHING)
            {
                progressBar.setVisibility(View.VISIBLE);
                viewModel.getSearchImages(et_search.getText().toString());
                SEARCHING = true;
                btn_search.setText("STOP SEARCH");
            }
            else
            {
                SEARCHING = false;
                btn_search.setText("SEARCH");
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
                        viewModel.getSearchPagination(et_search.getText().toString());
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

}
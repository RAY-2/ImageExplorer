package com.ray_apps.imageexplorer.Adaptors;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ray_apps.imageexplorer.Activities.ViewFullScaleImage;
import com.ray_apps.imageexplorer.Interfaces.BookmarkManager;
import com.ray_apps.imageexplorer.MainActivity;
import com.ray_apps.imageexplorer.R;
import com.ray_apps.imageexplorer.Models.UnsplashImage;

import java.util.List;

//Adaptor class for Handling Recycler view in Discover Tab that shows Images
public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.ViewHolder> {

    private List<UnsplashImage> listOfImages;
    private Context context;
    private BookmarkManager bookmarkManager;


    //constructor
    public MyAdaptor(Context context, List<UnsplashImage> list, BookmarkManager bookmarkManager) {
        this.context = context;
        this.listOfImages = list;
        this.bookmarkManager = bookmarkManager;
    }
    //constructor
    public MyAdaptor(Context context, List<UnsplashImage> list) {
        this.context = context;
        this.listOfImages = list;
    }

    @NonNull
    @Override
    public MyAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdaptor.ViewHolder holder, int position) {

        String imageURl = listOfImages.get(position).getUrls().getThumb();
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

        //Use glide api
        Glide.with(context)
                .load(imageURl)
                .apply(requestOptions)
                .into(holder.iv_photo);

        holder.tv_author.setText(listOfImages.get(position).getUser().getUsername());
        holder.iv_photo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent mainIntent = new Intent(context, ViewFullScaleImage.class);

                mainIntent.putExtra(MainActivity.IMAGE_KEY, listOfImages.get(position).getUrls().getRegular());
                mainIntent.putExtra(MainActivity.AUTHOR_KEY,listOfImages.get(position).getUser().getUsername());

                context.startActivity(mainIntent);

                return false;
            }
        });

        holder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarkManager.addToBookmarks(listOfImages.get(position).getUrls().getThumb());
            }
        });

        Log.d("POSITION DEBUG", String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return listOfImages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_photo;
        TextView tv_author;

        public ViewHolder(View v) {
            super(v);
            iv_photo = v.findViewById(R.id.image_of_card);
            tv_author = v.findViewById(R.id.author_of_image);
        }
    }
}

package com.ray_apps.imageexplorer.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ray_apps.imageexplorer.MainActivity;
import com.ray_apps.imageexplorer.R;


public class ViewFullScaleImage extends AppCompatActivity {

    private String imageURL;
    private String authorName;

    private ImageView image;
    private TextView author;
    private TextView linkText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_scale_image);

        //getting the data bundle from other activity incoming
        Bundle extras = getIntent().getExtras();
        imageURL = extras.getString(MainActivity.IMAGE_KEY);
        authorName = extras.getString(MainActivity.AUTHOR_KEY);

        initializeView();
        initializeData();

    }

    private void initializeView() {
        image = findViewById(R.id.iv_full_scale_image);
        author = findViewById(R.id.tv_author_of_full_image);
        linkText = findViewById(R.id.tv_link_to_actual_image);
    }


    private void initializeData() {

        //Use glide api to set image
        Glide.with(getApplicationContext())
                .load(imageURL)
                .into(image);

        author.setText("Author : " + authorName);
        linkText.setText("Original Photo is here\n" + imageURL);
    }

}
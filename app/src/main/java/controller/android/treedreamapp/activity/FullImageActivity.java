package controller.android.treedreamapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import controller.android.treedreamapp.R;


public class FullImageActivity extends Activity {
    private String path = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        path = i.getExtras().getString("path");


        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        Picasso.get().load(path).into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);

    }
}
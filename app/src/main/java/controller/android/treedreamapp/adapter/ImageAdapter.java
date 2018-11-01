package controller.android.treedreamapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.interfaces.OnRecyclerViewItemClickListener;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> imagesList;
    private OnRecyclerViewItemClickListener listener;


    // Constructor
    public ImageAdapter(Context c, ArrayList<String> images){
        mContext = c;
        this.imagesList = images;
    }

    public void setListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        Picasso.get().load(imagesList.get(position)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(330, 280));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(position, -1);
            }
        });

        return imageView;
    }

}
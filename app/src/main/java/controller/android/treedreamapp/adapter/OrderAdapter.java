package controller.android.treedreamapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.interfaces.OnRecyclerViewItemClickListener;
import controller.android.treedreamapp.model.Order;
import controller.android.treedreamapp.model.User;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<Order> albumList;
    private OnRecyclerViewItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, price;
        private ImageView thumbnail;
        private RelativeLayout layout;
        private MyViewHolder(View view) {
            super(view);
            layout = (RelativeLayout) view.findViewById(R.id.layout);
            title = (TextView) view.findViewById(R.id.categoryName);
            price = (TextView) view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.categoryIcon);

        }
    }

    public void setListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }


    public OrderAdapter(Context mContext, List<Order> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.price.setText("Rs. "+album.getStatus());
       // Picasso.get().load(albumList.get(position).getIcon()).into(holder.thumbnail);

        // loading album cover using Glide library


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listener.onRecyclerViewItemClicked(position, -1);
            }
        });
    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

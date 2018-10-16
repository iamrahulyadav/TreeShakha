package controller.android.treedreamapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.adapter.CategoryAdapter;
import controller.android.treedreamapp.common.Config;
import controller.android.treedreamapp.interfaces.OnRecyclerViewItemClickListener;
import controller.android.treedreamapp.model.GiftCategory;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GiftCategoryFragment extends Fragment {
    private View rootView;
    private List<GiftCategory> categoryList ;
    private CategoryAdapter adapter;
    private RecyclerView categoryRecyclerview;
    private OnRecyclerViewItemClickListener listener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addData(){
        categoryList = new ArrayList<>();
        GiftCategory category1 = new GiftCategory();
        category1.setIcon("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
        category1.setId(1);
        category1.setName("Gift tree on Anniversary");
        category1.setPrice(120);

        GiftCategory category2 = new GiftCategory();
        category2.setIcon("http://i.imgur.com/DvpvklR.png");
        category2.setId(2);
        category2.setName("Gift tree on Birthday");
        category2.setPrice(170);

        GiftCategory category3 = new GiftCategory();
        category3.setIcon("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
        category3.setId(3);
        category3.setName("Gift tree on Festival");
        category3.setPrice(1020);

        GiftCategory category4 = new GiftCategory();
        category4.setIcon("http://i.imgur.com/DvpvklR.png");
        category4.setId(4);
        category4.setName("Gift tree on any Occassion");
        category4.setPrice(1200);
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
        categoryList.add(category4);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_gift_category,null, false);
        categoryRecyclerview = (RecyclerView) rootView.findViewById(R.id.giftCategory);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        categoryRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        categoryRecyclerview.setLayoutManager(mLayoutManager);

        addData();
        Config.SHOWHOME = true;
        listener = new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                  try{

                  }catch (NullPointerException e){
                      Log.e("NullPointerExcep: ",""+e.getLocalizedMessage());
                  }
            }
        };
        adapter = new CategoryAdapter(getActivity(),categoryList);
        categoryRecyclerview.setAdapter(adapter);
        adapter.setListener(listener);
        return rootView;
    }
}

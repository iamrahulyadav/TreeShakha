package controller.android.treedreamapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.activity.MainActivity;
import controller.android.treedreamapp.adapter.CategoryAdapter;
import controller.android.treedreamapp.adapter.OrderAdapter;
import controller.android.treedreamapp.common.Api_Url;
import controller.android.treedreamapp.common.CallBackInterface;
import controller.android.treedreamapp.common.CallWebService;
import controller.android.treedreamapp.common.Config;
import controller.android.treedreamapp.interfaces.OnRecyclerViewItemClickListener;
import controller.android.treedreamapp.model.GiftCategory;
import controller.android.treedreamapp.model.Order;

import static com.facebook.FacebookSdk.getApplicationContext;

public class OrderHistory extends Fragment {
    private View rootView;

    private List<Order> orderList ;
    private OrderAdapter adapter;
    private RecyclerView orderRecyclerView;
    private OnRecyclerViewItemClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order, null,false);
        Config.SHOWHOME = true;
        Config.SHOWCATEGORY = false;

        orderRecyclerView = (RecyclerView) rootView.findViewById(R.id.orderList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        orderRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        orderRecyclerView.setLayoutManager(mLayoutManager);
        ((MainActivity)getActivity()).updateTitle("Orders");
        addData();
        listener = new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                try{
                    Order category = orderList.get(position);
                    Fragment fragment = new OrderDetailsFragment();
                    Bundle extras = new Bundle();
                    extras.putParcelable("order", category);
                    if (fragment != null) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        fragment.setArguments(extras);
                        ft.replace(R.id.frame, fragment);
                        ft.commit();
                    }
                }catch (NullPointerException e){
                    Log.e("NullPointerExcep: ",""+e.getLocalizedMessage());
                }catch (ArrayIndexOutOfBoundsException e){
                    Log.e("ArrayIndex Excep: ",""+e.getLocalizedMessage());
                }
            }
        };

        adapter = new OrderAdapter(getActivity(),orderList);
        orderRecyclerView.setAdapter(adapter);
        adapter.setListener(listener);




        return rootView;
    }
private void addData(){
        orderList = new ArrayList<>();
        Order order1 =  new Order();
        order1.setTitle("Birthday Gift");
        order1.setAmount(1200);
        order1.setStatus("Successful");
        order1.setOrderDate("08:30 PM  29/10/2018");
        order1.setOrderId(1);
        order1.setThumbnail("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");

        Order order2 =  new Order();
        order2.setTitle("Anniversary Gift");
        order2.setAmount(1300);
        order2.setStatus("Pending");
        order2.setOrderDate("07:30 AM  29/10/2018");
        order2.setOrderId(2);
        order2.setThumbnail("http://i.imgur.com/DvpvklR.png");

        Order order3 =  new Order();
        order3.setTitle("Birthday Gift");
        order3.setAmount(1700);
        order3.setStatus("Successful");
        order3.setOrderDate("09:30 PM  30/10/2018");
        order3.setOrderId(3);
        order3.setThumbnail("http://i.imgur.com/DvpvklR.png");

        Order order4 =  new Order();
        order4.setTitle("Anniversary Gift");
        order4.setAmount(1500);
        order4.setStatus("Successful");
        order4.setOrderDate("10:30 AM  30/10/2018");
        order4.setOrderId(4);
        order4.setThumbnail("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");

        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderList.add(order4);

}


    private JSONObject addJsonObjects() {
        try {


            JSONObject user = new JSONObject();


            return user;
        } catch (Exception e) {
            Log.e("Exception: ",""+e.getLocalizedMessage());
            return null;
        }
    }

    void getCategories(){
        CallWebService.getInstance(getActivity(),true).hitJSONObjectVolleyWebServiceforPost(Request.Method.POST, Api_Url.registationUrl, addJsonObjects(), true, new CallBackInterface() {
            @Override
            public void onJsonObjectSuccess(JSONObject object) {
                Log.d("Quiz List: ",""+object.toString());
                try {
                    ParseData(object.toString());

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onJsonArrarSuccess(JSONArray array) {
                Log.d("Contacts List: ",""+array.toString());
            }

            @Override
            public void onFailure(String str) {
                Log.e("failure: ",""+str);
                Toast.makeText(getActivity(),"login Faild email or password incorrect",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ParseData(String data1) {

        try {
            // JSON Parsing of data
            JSONObject obj=new JSONObject(data1);
            if(obj.getBoolean("success")) {

                String ss = obj.getString("user");
                JSONObject objdata = new JSONObject(ss);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

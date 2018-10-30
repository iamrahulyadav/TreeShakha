package controller.android.treedreamapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.activity.MainActivity;
import controller.android.treedreamapp.common.Api_Url;
import controller.android.treedreamapp.common.CallBackInterface;
import controller.android.treedreamapp.common.CallWebService;
import controller.android.treedreamapp.common.Config;
import controller.android.treedreamapp.model.GiftCategory;
import controller.android.treedreamapp.model.Order;

public class OrderDetailsFragment extends Fragment {
    private View rootView;
    private ImageView profile;
    private TextView title,amount,status,date;
    private LinearLayout success,failure;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.order_details, null, false);
        Config.SHOWHOME = false;
        Config.SHOWCATEGORY = false;
        Config.SHOWORDER = true;
        ((MainActivity)getActivity()).updateTitle("Order Details");
        profile = (ImageView) rootView.findViewById(R.id.profile);
        title = (TextView) rootView.findViewById(R.id.title);
        amount = (TextView) rootView.findViewById(R.id.amount);
        status = (TextView) rootView.findViewById(R.id.status);
        date = (TextView) rootView.findViewById(R.id.date);
        success = (LinearLayout) rootView.findViewById(R.id.success);
        failure = (LinearLayout) rootView.findViewById(R.id.failed);

        final Order order = getArguments().getParcelable("order");
        if(order!= null) {
            title.setText(order.getTitle());
            amount.setText(getActivity().getResources().getString(R.string.Rs)+""+order.getAmount());
            status.setText(order.getStatus());
            date.setText(order.getOrderDate());
            if (order.getStatus().equals(Config.STATUSMSG)) {
                success.setVisibility(View.VISIBLE);
                failure.setVisibility(View.GONE);
            } else {
                success.setVisibility(View.GONE);
                failure.setVisibility(View.VISIBLE);
            }
        }

        return rootView;
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

package controller.android.treedreamapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.activity.FullImageActivity;
import controller.android.treedreamapp.activity.MainActivity;
import controller.android.treedreamapp.adapter.ImageAdapter;
import controller.android.treedreamapp.common.Api_Url;
import controller.android.treedreamapp.common.CallBackInterface;
import controller.android.treedreamapp.common.CallWebService;
import controller.android.treedreamapp.common.Config;
import controller.android.treedreamapp.interfaces.OnRecyclerViewItemClickListener;
import controller.android.treedreamapp.model.Plant;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PlantDetailsFragment extends Fragment {

    private View rootView;
    private Plant plant;
    private OnRecyclerViewItemClickListener listener;
    private TextView title,subTitle,address,lattitude,longitude,date;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_plant_details, null, false);
        ((MainActivity)getActivity()).updateTitle("Plant Details ");
        Config.SHOWHOME = true;
        //Config.SHOWCATEGORY = true;
        plant = getArguments().getParcelable("plant");
        final ArrayList<String> images = plant.getImages();
        title = (TextView) rootView.findViewById(R.id.title);
        title.setText(plant.getTitle());
        subTitle = (TextView) rootView.findViewById(R.id.subTitle);
        subTitle.setText(plant.getSubTitle());
        address = (TextView) rootView.findViewById(R.id.address);
        address.setText(plant.getAddress());
        lattitude = (TextView) rootView.findViewById(R.id.lat);
        lattitude.setText(""+plant.getLattitude());
        longitude = (TextView) rootView.findViewById(R.id.longi);
        longitude.setText(""+plant.getLongitude());
        date = (TextView) rootView.findViewById(R.id.date);
        date.setText(plant.getPlantationDate());

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class


        listener = new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                /*Bundle extras =new Bundle();
                extras.putString("path", images.get(position));
                Fragment fragment = new FullImageFragment();
                fragment.setArguments(extras);
                if (fragment != null) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                }

*/
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                // passing array index
                i.putExtra("path", images.get(position));
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        };
        ImageAdapter adapter = new ImageAdapter(getActivity(), images);
        adapter.setListener(listener);
        gridView.setAdapter(adapter);


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

            }
        });

    }

    private void ParseData(String data1) {
        try {
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

package controller.android.treedreamapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.common.Api_Url;
import controller.android.treedreamapp.common.CallBackInterface;
import controller.android.treedreamapp.common.CallWebService;

public class MyProfile extends AppCompatActivity   {
    private ImageView goBack;
    private EditText name,email,mobile,address;
    private Button update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
private void initView(){
        goBack = (ImageView) findViewById(R.id.goBack);
        update = (Button) findViewById(R.id.btnUpdate);
        name = (EditText) findViewById(R.id.eteName);
        email = (EditText) findViewById(R.id.etEmail);
        mobile = (EditText) findViewById(R.id.etMobile);
        address = (EditText) findViewById(R.id.etAddress);
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
        CallWebService.getInstance(MyProfile.this,true).hitJSONObjectVolleyWebServiceforPost(Request.Method.POST, Api_Url.registationUrl, addJsonObjects(), true, new CallBackInterface() {
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
                Toast.makeText(MyProfile.this,"login Faild email or password incorrect",Toast.LENGTH_SHORT).show();
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

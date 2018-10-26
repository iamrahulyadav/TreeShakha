package controller.android.treedreamapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.common.Api_Url;
import controller.android.treedreamapp.common.CallBackInterface;
import controller.android.treedreamapp.common.CallWebService;
import controller.android.treedreamapp.common.UserSessionManager;


public class RegisterActivity extends AppCompatActivity {


    private EditText _emailText,name,mobileNo,etpassword;
    private Button signin,signup;
    private String email,nameText,mobile, password;
   // private Userdata userdata;
    private UserSessionManager userSessionManager;

    //private LoginButton loginButton;
    //private CallbackManager callbackManager;

    //private FirebaseAuth firebaseAuth;
    //private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        // listener = (UpdateListener) LoginActivity.this;


        etpassword=(EditText) findViewById(R.id.password);
        _emailText=(EditText) findViewById(R.id.etemaillogin);
        name=(EditText) findViewById(R.id.etename);
        mobileNo=(EditText) findViewById(R.id.etphone);
        signin=(Button) findViewById(R.id.btnsigninlogin);
        signup=(Button) findViewById(R.id.btnsignUp);

        userSessionManager = new UserSessionManager(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate())
                {
                    registerRequest(nameText,email,mobile,password);

                }

            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginScreen();
            }
        });


/*
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

       // loginButton = (LoginButton) findViewById(R.id.loginButton);

        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d("Firebase Token: ",""+user.getIdToken(true).getResult().getToken());
                    userSessionManager.updateUserLoggedIN(true);
                    goMainScreen();
                }
            }
        };*/
    }

    /*private void handleFacebookAccessToken(AccessToken accessToken) {

        loginButton.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                }

                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }*/



    private void ParseData(String data1) {

        // Log.d("data", getuserdata.toString());
        // dismiss the progress dialog after receiving data from API
        try {
            // JSON Parsing of data
            JSONObject obj=new JSONObject(data1);
            if(obj.getBoolean("success"))
            {

                String ss=obj.getString("user");
                JSONObject objdata=new JSONObject(ss);
                int userId = objdata.getInt("id");
                String mobile = objdata.getString("mobile");
                String useremail = objdata.getString("email");
                String name = objdata.getString("name");
                String auth_token = objdata.getString("authentication_token");

               /* String ss=obj.getString("data");
                JSONObject objdata=new JSONObject(ss);
                userSessionManager.createUserLoginSession(email,objdata.getString("user_id"));



                Intent mainintent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(mainintent);
                finish();*/


                new UserSessionManager(RegisterActivity.this).updateUserLoggedIN(true);
                Intent verifyMobile = new Intent(RegisterActivity.this, MainActivity.class);
                verifyMobile.putExtra("name", name);
                verifyMobile.putExtra("id", userId);
                verifyMobile.putExtra("email", useremail);
                verifyMobile.putExtra("mobile", mobile);
                verifyMobile.putExtra("auth_token",auth_token);
                verifyMobile.putExtra("via", "normal");
                startActivity(verifyMobile);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



            }else {
                Toast.makeText(getApplicationContext(),"login Faild email or password incorrect",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, Login_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
       //firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }
    public boolean validate() {
        boolean valid = true;
        nameText = name.getText().toString();
        email = _emailText.getText().toString();
        mobile = mobileNo.getText().toString();
        password = etpassword.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 10) {
            mobileNo.setError("10 digit mobile number is required!");
            valid = false;
        } else {
            mobileNo.setError(null);
        }
        if (password.isEmpty()) {
            etpassword.setError("Password is required!");
            valid = false;
        } else {
            etpassword.setError(null);
        }

        return valid;
    }


    private JSONObject addJsonObjects(String name,String email, String mobile, String password) {
        try {


            JSONObject user = new JSONObject();
            JSONObject packet = new JSONObject();
            packet.put("email", email);
            packet.put("password",password);
            packet.put("name", name);
          //  packet.put("token","1/fFAGRNJru1FTz70BzhT3Zg");
            packet.put("mobile", mobile);

            packet.put("token", userSessionManager.getUserDeviceToken());

            user.put("user", packet);
            return user;
        } catch (Exception e) {
            Log.e("Exception: ",""+e.getLocalizedMessage());
            return null;
        }
    }

    void registerRequest(String name, String useremail, String mobile, String password){
        CallWebService.getInstance(this,true).hitJSONObjectVolleyWebServiceforPost(Request.Method.POST, Api_Url.registationUrl, addJsonObjects(name,useremail, mobile, password), true, new CallBackInterface() {
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
                Toast.makeText(RegisterActivity.this,"login Faild email or password incorrect",Toast.LENGTH_SHORT).show();

            }
        });



    }
}

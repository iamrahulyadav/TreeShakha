package controller.android.treedreamapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


import controller.android.treedreamapp.R;
import controller.android.treedreamapp.activity.MainActivity;
import controller.android.treedreamapp.common.Api_Url;
import controller.android.treedreamapp.common.CallBackInterface;
import controller.android.treedreamapp.common.CallWebService;
import controller.android.treedreamapp.common.Config;
import controller.android.treedreamapp.common.Constants;
import controller.android.treedreamapp.model.GiftCategory;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PaymentScreen extends Fragment {
    private View rootView;
    private TextView title,price;
    private EditText quantity;
    private Button payNow;
    private int totalCost = 0;

    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    private View mGooglePayButton;

    /** A constant integer you define to track a request for payment data activity */
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment,null,false);
        Config.SHOWHOME = false;
        Config.SHOWCATEGORY = true;
        ((MainActivity)getActivity()).updateTitle("Payment Details ");
        final GiftCategory category = getArguments().getParcelable("category");
        title = (TextView) rootView.findViewById(R.id.title);
        quantity = (EditText) rootView.findViewById(R.id.quantity);
        price = (TextView) rootView.findViewById(R.id.price);
        title.setText(category.getName());
        quantity.setText("1");
        totalCost = category.getPrice();
        price.setText("Rs. "+category.getPrice());

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int qua = Integer.parseInt(s.toString());
                    totalCost = (category.getPrice() * qua);
                    price.setText("Rs. "+totalCost);

                }catch (NullPointerException e){
                    Log.e("NullPointerExcep: ",""+e.getLocalizedMessage());
                }catch (NumberFormatException e){
                    Log.e("NumberFormatExcep: 2",""+e.getLocalizedMessage());
                }
            }
        });

        payNow = (Button) rootView.findViewById(R.id.payNow);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializePaymentGateway(price.getText().toString());
            }
        });

        return rootView;
    }

    private void initGoglePay(){
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", "test@axisbank")
                        .appendQueryParameter("pn", "Test Merchant")
                        .appendQueryParameter("mc", "1234")
                        .appendQueryParameter("tr", "123456789")
                        .appendQueryParameter("tn", "test transaction note")
                        .appendQueryParameter("am", "10.01")
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", "https://test.merchant.website")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
        startActivityForResult(intent, TEZ_REQUEST_CODE);


    }

    private void initializePaymentGateway(String amount){
        PaytmPGService Service = PaytmPGService.getStagingService();

        HashMap<String, String> paramMap = new HashMap<String,String>();
        paramMap.put( "MID" , Constants.merchantMid);
        // Key in your staging and production MID available in your dashboard
        paramMap.put( "ORDER_ID" , Constants.orderId);
        paramMap.put( "CUST_ID" , Constants.custId);
        paramMap.put( "MOBILE_NO" , Constants.mobileNo);
        paramMap.put( "EMAIL" , Constants.email);
        paramMap.put( "CHANNEL_ID" , Constants.channelId);
        paramMap.put( "TXN_AMOUNT" , Constants.txnAmount);
        paramMap.put( "WEBSITE" , Constants.website);
        // This is the staging value. Production value is available in your dashboard
        paramMap.put( "INDUSTRY_TYPE_ID" , Constants.industryTypeId);
        // This is the staging value. Production value is available in your dashboard
        paramMap.put( "CALLBACK_URL", Constants.callbackUrl);
        paramMap.put( "CHECKSUMHASH" , Constants.CheckSum);
        PaytmOrder Order = new PaytmOrder(paramMap);


        //PaytmClientCertificate Certificate = new PaytmClientCertificate(String inPassword, String inFileName);
        Service.initialize(Order, null);


        Service.startPaymentTransaction(getActivity(), true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.e("Payment Some Issue: ",""+inErrorMessage);
            }
            public void onTransactionResponse(Bundle inResponse) {
                Log.d("LOG", "Payment Transaction is successful " + inResponse);
                Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
            }
            public void networkNotAvailable() {

            }
            public void clientAuthenticationFailed(String inErrorMessage) {
                 Log.e("Payment failed: ",""+inErrorMessage);
            }
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {

            }
            public void onBackPressedCancelTransaction() {
                Toast.makeText(getActivity(),"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
                Config.SHOWCATEGORY = true;

            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                Toast.makeText(getApplicationContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                Log.e("Payment Canceled: ",""+inErrorMessage);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            Log.d("result", data.getStringExtra("Status"));
        }
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

package controller.android.treedreamapp.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import controller.android.treedreamapp.R;

public class GooglePay {




    public static Optional<JSONObject> getPaymentDataRequest() {
        try {
            JSONObject paymentDataRequest = GooglePay.getBaseRequest();
            paymentDataRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(GooglePay.getCardPaymentMethod()));
            paymentDataRequest.put("transactionInfo", GooglePay.getTransactionInfo());
            paymentDataRequest.put("merchantInfo", GooglePay.getMerchantInfo());
            return Optional.of(paymentDataRequest);
        } catch (JSONException e) {
            return Optional.empty();
        }
    }


    private static JSONObject getMerchantInfo() {
        JSONObject object=null;
        try {
            new JSONObject()
                    .put("merchantName", "Example Merchant");
        }catch (JSONException e){
            Log.e("JSONExcep: ",""+e.getLocalizedMessage());
        }
        return object;
    }

    private static JSONObject getTransactionInfo() {
        JSONObject transactionInfo = new JSONObject();
        try {
            transactionInfo.put("totalPrice", "12.34");
            transactionInfo.put("totalPriceStatus", "FINAL");
            transactionInfo.put("currencyCode", "USD");
        }catch (JSONException e){
            Log.e("JSONExcep: ",""+e.getLocalizedMessage());
        }

        return transactionInfo;
    }

    public static Optional<JSONObject> getIsReadyToPayRequest() {
        JSONObject isReadyToPayRequest = getBaseRequest();
        try {
            isReadyToPayRequest.put(
                    "allowedPaymentMethods",
                    new JSONArray()
                            .put(getBaseCardPaymentMethod()));
        }catch (JSONException e){
            Log.e("JSONExcep: ",""+e.getLocalizedMessage());
        }

        return Optional.of(isReadyToPayRequest);
    }


    private static JSONObject getBaseRequest() {
        JSONObject obj = null;
        try {
             obj =  new JSONObject()
                    .put("apiVersion", 2)
                    .put("apiVersionMinor", 0);
        }catch (JSONException e){
            Log.e("JSONExcep: ",""+e.getLocalizedMessage());
        }
        return obj;
    }

    private static JSONObject getTokenizationSpecification() {
        JSONObject tokenizationSpecification = new JSONObject();
        try {
            tokenizationSpecification.put("type", "PAYMENT_GATEWAY");
            tokenizationSpecification.put(
                    "parameters",
                    new JSONObject()
                            .put("gateway", "example")
                            .put("gatewayMerchantId", "exampleMerchantId"));
        }catch (JSONException e){
            Log.e("JSONExcep: ",""+e.getLocalizedMessage());
        }

        return tokenizationSpecification;
    }

    private static JSONArray getAllowedCardNetworks() {
        return new JSONArray()
                .put("AMEX")
                .put("DISCOVER")
                .put("JCB")
                .put("MASTERCARD")
                .put("VISA");
    }

    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");
    }

    private static JSONObject getBaseCardPaymentMethod() {
        JSONObject cardPaymentMethod = new JSONObject();
        try {
            cardPaymentMethod.put("type", "CARD");
            cardPaymentMethod.put(
                    "parameters",
                    new JSONObject()
                            .put("allowedAuthMethods", getAllowedCardAuthMethods())
                            .put("allowedCardNetworks", getAllowedCardNetworks()));
        }catch (JSONException e){
            Log.e("JSONExcep: ",""+e.getLocalizedMessage());
        }

        return cardPaymentMethod;
    }

    private static JSONObject getCardPaymentMethod() {
        JSONObject cardPaymentMethod = getBaseCardPaymentMethod();
        try {
            cardPaymentMethod.put("tokenizationSpecification", getTokenizationSpecification());
        }catch (JSONException e){
            Log.e("JSONExcep: ",""+e.getLocalizedMessage());
        }

        return cardPaymentMethod;
    }

}

package com.mangobits.fitco.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import mangobits.startupkit.core.service.JsonRequest;

/**
 * Created by angela on 19/09/17.
 */

public class AddressRequest extends AsyncTask<Void, Void, AddressViaCep> {


    protected String messageProgress;

    protected Context context;

    private ProgressDialog progress;

    protected boolean showProgress;

    private Handler searchDelay;

    private WeakReference<AddressRequestInterface> activity;


    public AddressRequest(AddressRequestInterface activity) {
        this.activity = new WeakReference<>(activity);
    }

//    public AddressRequest( PaymentActivity activity ){
//        this.paymentActivity = new WeakReference<>( activity );
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        activity.get().lockFields(true);

//        messageProgress = this.context.getText(R.string.loading).toString();
//
//        progress.setMessage(messageProgress);
//
//        progress.show();


    }

    @Override
    protected AddressViaCep doInBackground(Void... voids) {

        try {
            String jsonString = JsonRequest.request(activity.get().getUriRequest());
            Gson gson = new Gson();

            return gson.fromJson(jsonString, AddressViaCep.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(AddressViaCep address) {
        super.onPostExecute(address);

        if (activity.get() != null) {
            activity.get().lockFields(false);

            if (address != null) {
                activity.get().setAddressFields(address);
            }
        }
    }

    public interface AddressRequestInterface {


        void lockFields(boolean boo);

        void setAddressFields(AddressViaCep address);

        String getUriRequest();
    }
}

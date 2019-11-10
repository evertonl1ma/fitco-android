package com.mangobits.fitco.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import mangobits.startupkit.core.service.JsonRequest;

/**
 * Created by angela on 19/09/17.
 */

public class CurrencyRequest2 extends AsyncTask<Void, Void, Currency> {


    protected String messageProgress;

    protected Context context;

    private ProgressDialog progress;

    protected boolean showProgress;

    private Handler searchDelay;

    private WeakReference<CurrencyRequest2Interface> activity;


    public CurrencyRequest2(CurrencyRequest2Interface activity) {
        this.activity = new WeakReference<>(activity);
    }

//    public CurrencyRequest2( PaymentActivity activity ){
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
    protected Currency doInBackground(Void... voids) {

        try {
            String jsonString = JsonRequest.request(activity.get().getUriRequestDollar());
            String jsonString2 = JsonRequest.request(activity.get().getUriRequestEuro());
            Gson gson = new Gson();

            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(
//                    DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JSONObject json = new JSONObject(jsonString);
            JSONObject usd = json.getJSONObject("USD_BRL");
            Double value = usd.getDouble("val");

            JSONObject json2 = new JSONObject(jsonString2);
            JSONObject eur = json2.getJSONObject("EUR_BRL");
            Double value2 = eur.getDouble("val");

            Currency currency = new Currency();
            currency.setValueDollar(value);
            currency.setValueEuro(value2);

            return currency;

//            return mapper.readValue(jsonString, Currency.class);

//            return gson.fromJson(jsonString, Currency.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Currency address) {
        super.onPostExecute(address);

        if (activity.get() != null) {
            activity.get().lockFields(false);

            if (address != null) {
                activity.get().setAddressFields(address);
            }
        }
    }

    public interface CurrencyRequest2Interface {

        void lockFields(boolean boo);

        void setAddressFields(Currency address);

        String getUriRequestDollar();

        String getUriRequestEuro();
    }
}

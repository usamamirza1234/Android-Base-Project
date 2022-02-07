package com.ast.MyBills.IntroAuxilaries.WebServices;


import android.content.Context;
import android.util.Log;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;


import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;



public class Intro_WebHit_Post_OTP {


    //object bnaya ha api k respnse ko website a dalny k bad
    // root class ka nam hatm kr dia ha
    //andr wali czain rhny di han


    //http request ka object ha
    private final AsyncHttpClient mClient = new AsyncHttpClient();

    //context
    public Context mContext;


    //nam kuch b ho
    public void postOTP(Context context, final IWebCallback iWebCallback,
                           final String _signInEntity) {
        mContext = context;


//        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.signIn;
        String myUrl = "http://cbs.zong.com.pk/reachrestapi/home/SendQuickSMS";



        ///////////
        Log.d("LOG_AS", "postOTP: url is " + myUrl + " Params are"+ _signInEntity);

        StringEntity entity = null;

        entity = new StringEntity(_signInEntity, "UTF-8");

        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);

        Log.d("currentLang", AppConfig.getInstance().loadDefLanguage());

        mClient.post(mContext, myUrl, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;


                        try {
                            Gson gson = new Gson();

                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "OTP: strResponse" + strResponse);



                            iWebCallback.onWebResult(true, "");

//                            switch (statusCode) {
//
//                                case AppConstt.ServerStatus.OK:
//                                case AppConstt.ServerStatus.CREATED:
//                                    iWebCallback.onWebResult(true, "");
//                                    break;
//
//                                default:
//                                    AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
//                                    break;
//                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            iWebCallback.onWebException(ex);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        switch (statusCode) {
                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                iWebCallback.onWebResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.FORBIDDEN:
                            case AppConstt.ServerStatus.UNAUTHORIZED:
                            default:
                                AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                break;
                        }
                    }
                }

        );
    }



}




package com.ast.MyBills.MainAuxilaries.WebServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.ast.MyBills.Utils.IWebCallback;
import com.google.gson.Gson;

import java.util.List;

public class More_WebHit_Get_Bills {

    public static ResponseModel responseObject = null;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    public void getBills(Context mContext, IWebCallback iWebCallback) {
        String myUrl = "http://115.186.179.110:2222/09141241306100";
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(mContext);
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, myUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(mContext, "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    Gson gson = new Gson();
                    Log.d("LOG_AS", "getTermsConditions onSuccess: strResponse " + response);
                    responseObject = gson.fromJson(response, ResponseModel.class);
                    iWebCallback.onWebResult(true, "Success");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    iWebCallback.onWebException(ex);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iWebCallback.onWebResult(false,  error.toString());
                   }
        });


        mStringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        mRequestQueue.add(mStringRequest);
    }

    public class ResponseModel {

        public class IescoBill
        {
            private String ADDRESS;

            private String BILLMONTH;

            private String CONSUMERID;

            private String DUEDATE;

            private String ISSUEDATE;

            private String NAME;

            private String PAYABLEAFTERDUEDATE;

            private String PAYABLEWITHINDUEDATE;

            private String READINGDATE;

            private List<List<String>> lastYearBills;

            private String referenceNumber;

            public void setADDRESS(String ADDRESS){
                this.ADDRESS = ADDRESS;
            }
            public String getADDRESS(){
                return this.ADDRESS;
            }
            public void setBILLMONTH(String BILLMONTH){
                this.BILLMONTH = BILLMONTH;
            }
            public String getBILLMONTH(){
                return this.BILLMONTH;
            }
            public void setCONSUMERID(String CONSUMERID){
                this.CONSUMERID = CONSUMERID;
            }
            public String getCONSUMERID(){
                return this.CONSUMERID;
            }
            public void setDUEDATE(String DUEDATE){
                this.DUEDATE = DUEDATE;
            }
            public String getDUEDATE(){
                return this.DUEDATE;
            }
            public void setISSUEDATE(String ISSUEDATE){
                this.ISSUEDATE = ISSUEDATE;
            }
            public String getISSUEDATE(){
                return this.ISSUEDATE;
            }
            public void setNAME(String NAME){
                this.NAME = NAME;
            }
            public String getNAME(){
                return this.NAME;
            }
            public void setPAYABLEAFTERDUEDATE(String PAYABLEAFTERDUEDATE){
                this.PAYABLEAFTERDUEDATE = PAYABLEAFTERDUEDATE;
            }
            public String getPAYABLEAFTERDUEDATE(){
                return this.PAYABLEAFTERDUEDATE;
            }
            public void setPAYABLEWITHINDUEDATE(String PAYABLEWITHINDUEDATE){
                this.PAYABLEWITHINDUEDATE = PAYABLEWITHINDUEDATE;
            }
            public String getPAYABLEWITHINDUEDATE(){
                return this.PAYABLEWITHINDUEDATE;
            }
            public void setREADINGDATE(String READINGDATE){
                this.READINGDATE = READINGDATE;
            }
            public String getREADINGDATE(){
                return this.READINGDATE;
            }
            public void setLastYearBills(List<List<String>> lastYearBills){
                this.lastYearBills = lastYearBills;
            }
            public List<List<String>> getLastYearBills(){
                return this.lastYearBills;
            }
            public void setReferenceNumber(String referenceNumber){
                this.referenceNumber = referenceNumber;
            }
            public String getReferenceNumber(){
                return this.referenceNumber;
            }
        }



            private IescoBill iescoBill;

            public void setIescoBill(IescoBill iescoBill){
                this.iescoBill = iescoBill;
            }
            public IescoBill getIescoBill(){
                return this.iescoBill;
            }
        }

    }


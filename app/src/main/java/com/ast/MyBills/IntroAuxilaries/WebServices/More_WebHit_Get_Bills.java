package com.ast.MyBills.IntroAuxilaries.WebServices;

import android.content.Context;
import android.util.Log;

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

    public void getBills(Context mContext, IWebCallback iWebCallback, String ref, String type) {
//        String myUrl = "http://115.186.179.110:2222/scrapersapi/"+type+"/" + ref + "";
        String myUrl = "http://115.186.179.110:2222/api/Bill?type="+type+"&billNumber="+ref+"";


        Log.d("LOG_AS", "getTermsConditions myUrl:" + myUrl);

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(mContext);
        //String Request initialized

        mStringRequest = new StringRequest(Request.Method.GET, myUrl, response -> {
            try {
                Gson gson = new Gson();
                Log.d("LOG_AS", "getTermsConditions onSuccess: strResponse" + response);


                responseObject = gson.fromJson(response, ResponseModel.class);


                iWebCallback.onWebResult(true, "Success");
            } catch (Exception ex) {
                ex.printStackTrace();
                iWebCallback.onWebException(ex);
            }

        }, error -> iWebCallback.onWebResult(false, error.toString()));


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




            public class Data
            {
                private String BILLMONTH;

                private String READINGDATE;

                private String ISSUEDATE;

                private String DUEDATE;

                private String NAME;

                private String ADDRESS;

                private String CONSUMERID;

                private String PAYABLEWITHINDUEDATE;

                private String PAYABLEAFTERDUEDATE;

                private List<List<String>> LASTYEARBILLS;

                private String BILLTYPE;

                private String CITY;

                private String REFERENCENUMBER;

                private String METERNO;

                private String PREVIOUSREADING;

                private String PRESENTREADING;

                private String MF;

                private String UNITS;

                private String STATUS;

                private String TARIFF;

                private String TOTALUNITS;

                public void setBILLMONTH(String BILLMONTH){
                    this.BILLMONTH = BILLMONTH;
                }
                public String getBILLMONTH(){
                    return this.BILLMONTH;
                }
                public void setREADINGDATE(String READINGDATE){
                    this.READINGDATE = READINGDATE;
                }
                public String getREADINGDATE(){
                    return this.READINGDATE;
                }
                public void setISSUEDATE(String ISSUEDATE){
                    this.ISSUEDATE = ISSUEDATE;
                }
                public String getISSUEDATE(){
                    return this.ISSUEDATE;
                }
                public void setDUEDATE(String DUEDATE){
                    this.DUEDATE = DUEDATE;
                }
                public String getDUEDATE(){
                    return this.DUEDATE;
                }
                public void setNAME(String NAME){
                    this.NAME = NAME;
                }
                public String getNAME(){
                    return this.NAME;
                }
                public void setADDRESS(String ADDRESS){
                    this.ADDRESS = ADDRESS;
                }
                public String getADDRESS(){
                    return this.ADDRESS;
                }
                public void setCONSUMERID(String CONSUMERID){
                    this.CONSUMERID = CONSUMERID;
                }
                public String getCONSUMERID(){
                    return this.CONSUMERID;
                }
                public void setPAYABLEWITHINDUEDATE(String PAYABLEWITHINDUEDATE){
                    this.PAYABLEWITHINDUEDATE = PAYABLEWITHINDUEDATE;
                }
                public String getPAYABLEWITHINDUEDATE(){
                    return this.PAYABLEWITHINDUEDATE;
                }
                public void setPAYABLEAFTERDUEDATE(String PAYABLEAFTERDUEDATE){
                    this.PAYABLEAFTERDUEDATE = PAYABLEAFTERDUEDATE;
                }
                public String getPAYABLEAFTERDUEDATE(){
                    return this.PAYABLEAFTERDUEDATE;
                }
                public void setLASTYEARBILLS(List<List<String>> LASTYEARBILLS){
                    this.LASTYEARBILLS = LASTYEARBILLS;
                }
                public List<List<String>> getLASTYEARBILLS(){
                    return this.LASTYEARBILLS;
                }
                public void setBILLTYPE(String BILLTYPE){
                    this.BILLTYPE = BILLTYPE;
                }
                public String getBILLTYPE(){
                    return this.BILLTYPE;
                }
                public void setCITY(String CITY){
                    this.CITY = CITY;
                }
                public String getCITY(){
                    return this.CITY;
                }
                public void setREFERENCENUMBER(String REFERENCENUMBER){
                    this.REFERENCENUMBER = REFERENCENUMBER;
                }
                public String getREFERENCENUMBER(){
                    return this.REFERENCENUMBER;
                }
                public void setMETERNO(String METERNO){
                    this.METERNO = METERNO;
                }
                public String getMETERNO(){
                    return this.METERNO;
                }
                public void setPREVIOUSREADING(String PREVIOUSREADING){
                    this.PREVIOUSREADING = PREVIOUSREADING;
                }
                public String getPREVIOUSREADING(){
                    return this.PREVIOUSREADING;
                }
                public void setPRESENTREADING(String PRESENTREADING){
                    this.PRESENTREADING = PRESENTREADING;
                }
                public String getPRESENTREADING(){
                    return this.PRESENTREADING;
                }
                public void setMF(String MF){
                    this.MF = MF;
                }
                public String getMF(){
                    return this.MF;
                }
                public void setUNITS(String UNITS){
                    this.UNITS = UNITS;
                }
                public String getUNITS(){
                    return this.UNITS;
                }
                public void setSTATUS(String STATUS){
                    this.STATUS = STATUS;
                }
                public String getSTATUS(){
                    return this.STATUS;
                }
                public void setTARIFF(String TARIFF){
                    this.TARIFF = TARIFF;
                }
                public String getTARIFF(){
                    return this.TARIFF;
                }
                public void setTOTALUNITS(String TOTALUNITS){
                    this.TOTALUNITS = TOTALUNITS;
                }
                public String getTOTALUNITS(){
                    return this.TOTALUNITS;
                }
            }

            private Data data;

            public void setData(Data data){
                this.data = data;
            }
            public Data getData(){
                return this.data;
            }






    }
}


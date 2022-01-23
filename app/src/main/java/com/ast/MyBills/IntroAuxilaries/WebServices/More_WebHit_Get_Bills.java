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

    public void getBills(Context mContext, IWebCallback iWebCallback, String ref) {
        String myUrl = "http://115.186.179.110:2222/scrapersapi/iesco/" + ref + "";

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


        private IescoBill iescoBill;

        public IescoBill getIescoBill() {
            return this.iescoBill;
        }

        public void setIescoBill(IescoBill iescoBill) {
            this.iescoBill = iescoBill;
        }

        public class IescoBill {
            private String BILLMONTH;

            private String READINGDATE;

            private String ISSUEDATE;

            private String DUEDATE;

            private String NAME;

            private String ADDRESS;

            private String CONSUMERID;

            private String PAYABLEWITHINDUEDATE;

            private String PAYABLEAFTERDUEDATE;

            private List<List<String>> lastYearBills;

            private String billType;

            private String city;

            private String referenceNumber;

            private String METERNO;

            private String PREVIOUSREADING;

            private String PRESENTREADING;

            private String MF;

            private String UNITS;

            private String STATUS;

            private String TARIFF;

            public String getBILLMONTH() {
                return this.BILLMONTH;
            }

            public void setBILLMONTH(String BILLMONTH) {
                this.BILLMONTH = BILLMONTH;
            }

            public String getREADINGDATE() {
                return this.READINGDATE;
            }

            public void setREADINGDATE(String READINGDATE) {
                this.READINGDATE = READINGDATE;
            }

            public String getISSUEDATE() {
                return this.ISSUEDATE;
            }

            public void setISSUEDATE(String ISSUEDATE) {
                this.ISSUEDATE = ISSUEDATE;
            }

            public String getDUEDATE() {
                return this.DUEDATE;
            }

            public void setDUEDATE(String DUEDATE) {
                this.DUEDATE = DUEDATE;
            }

            public String getNAME() {
                return this.NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getADDRESS() {
                return this.ADDRESS;
            }

            public void setADDRESS(String ADDRESS) {
                this.ADDRESS = ADDRESS;
            }

            public String getCONSUMERID() {
                return this.CONSUMERID;
            }

            public void setCONSUMERID(String CONSUMERID) {
                this.CONSUMERID = CONSUMERID;
            }

            public String getPAYABLEWITHINDUEDATE() {
                return this.PAYABLEWITHINDUEDATE;
            }

            public void setPAYABLEWITHINDUEDATE(String PAYABLEWITHINDUEDATE) {
                this.PAYABLEWITHINDUEDATE = PAYABLEWITHINDUEDATE;
            }

            public String getPAYABLEAFTERDUEDATE() {
                return this.PAYABLEAFTERDUEDATE;
            }

            public void setPAYABLEAFTERDUEDATE(String PAYABLEAFTERDUEDATE) {
                this.PAYABLEAFTERDUEDATE = PAYABLEAFTERDUEDATE;
            }

            public List<List<String>> getLastYearBills() {
                return this.lastYearBills;
            }

            public void setLastYearBills(List<List<String>> lastYearBills) {
                this.lastYearBills = lastYearBills;
            }

            public String getBillType() {
                return this.billType;
            }

            public void setBillType(String billType) {
                this.billType = billType;
            }

            public String getCity() {
                return this.city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getReferenceNumber() {
                return this.referenceNumber;
            }

            public void setReferenceNumber(String referenceNumber) {
                this.referenceNumber = referenceNumber;
            }

            public String getMETERNO() {
                return this.METERNO;
            }

            public void setMETERNO(String METERNO) {
                this.METERNO = METERNO;
            }

            public String getPREVIOUSREADING() {
                return this.PREVIOUSREADING;
            }

            public void setPREVIOUSREADING(String PREVIOUSREADING) {
                this.PREVIOUSREADING = PREVIOUSREADING;
            }

            public String getPRESENTREADING() {
                return this.PRESENTREADING;
            }

            public void setPRESENTREADING(String PRESENTREADING) {
                this.PRESENTREADING = PRESENTREADING;
            }

            public String getMF() {
                return this.MF;
            }

            public void setMF(String MF) {
                this.MF = MF;
            }

            public String getUNITS() {
                return this.UNITS;
            }

            public void setUNITS(String UNITS) {
                this.UNITS = UNITS;
            }

            public String getSTATUS() {
                return this.STATUS;
            }

            public void setSTATUS(String STATUS) {
                this.STATUS = STATUS;
            }

            public String getTARIFF() {
                return this.TARIFF;
            }

            public void setTARIFF(String TARIFF) {
                this.TARIFF = TARIFF;
            }
        }


//        public class IescoBill
//        {
//            private String ADDRESS;
//
//            private String BILLMONTH;
//
//            private String CONSUMERID;
//
//            private String DUEDATE;
//
//            private String ISSUEDATE;
//
//            private String METERNO;
//
//            private String MF;
//
//            private String NAME;
//
//            private String PAYABLEAFTERDUEDATE;
//
//            private String PAYABLEWITHINDUEDATE;
//
//            private String PRESENTREADING;
//
//            private String PREVIOUSREADING;
//
//            private String READINGDATE;
//
//            private String STATUS;
//
//            private String TARIFF;
//
//            private String UNITS;
//
//            private String billType;
//
//            private String city;
//
//            private List<List<String>> lastYearBills;
//
//            private String referenceNumber;
//
//            public void setADDRESS(String ADDRESS){
//                this.ADDRESS = ADDRESS;
//            }
//            public String getADDRESS(){
//                return this.ADDRESS;
//            }
//            public void setBILLMONTH(String BILLMONTH){
//                this.BILLMONTH = BILLMONTH;
//            }
//            public String getBILLMONTH(){
//                return this.BILLMONTH;
//            }
//            public void setCONSUMERID(String CONSUMERID){
//                this.CONSUMERID = CONSUMERID;
//            }
//            public String getCONSUMERID(){
//                return this.CONSUMERID;
//            }
//            public void setDUEDATE(String DUEDATE){
//                this.DUEDATE = DUEDATE;
//            }
//            public String getDUEDATE(){
//                return this.DUEDATE;
//            }
//            public void setISSUEDATE(String ISSUEDATE){
//                this.ISSUEDATE = ISSUEDATE;
//            }
//            public String getISSUEDATE(){
//                return this.ISSUEDATE;
//            }
//            public void setMETERNO(String METERNO){
//                this.METERNO = METERNO;
//            }
//            public String getMETERNO(){
//                return this.METERNO;
//            }
//            public void setMF(String MF){
//                this.MF = MF;
//            }
//            public String getMF(){
//                return this.MF;
//            }
//            public void setNAME(String NAME){
//                this.NAME = NAME;
//            }
//            public String getNAME(){
//                return this.NAME;
//            }
//            public void setPAYABLEAFTERDUEDATE(String PAYABLEAFTERDUEDATE){
//                this.PAYABLEAFTERDUEDATE = PAYABLEAFTERDUEDATE;
//            }
//            public String getPAYABLEAFTERDUEDATE(){
//                return this.PAYABLEAFTERDUEDATE;
//            }
//            public void setPAYABLEWITHINDUEDATE(String PAYABLEWITHINDUEDATE){
//                this.PAYABLEWITHINDUEDATE = PAYABLEWITHINDUEDATE;
//            }
//            public String getPAYABLEWITHINDUEDATE(){
//                return this.PAYABLEWITHINDUEDATE;
//            }
//            public void setPRESENTREADING(String PRESENTREADING){
//                this.PRESENTREADING = PRESENTREADING;
//            }
//            public String getPRESENTREADING(){
//                return this.PRESENTREADING;
//            }
//            public void setPREVIOUSREADING(String PREVIOUSREADING){
//                this.PREVIOUSREADING = PREVIOUSREADING;
//            }
//            public String getPREVIOUSREADING(){
//                return this.PREVIOUSREADING;
//            }
//            public void setREADINGDATE(String READINGDATE){
//                this.READINGDATE = READINGDATE;
//            }
//            public String getREADINGDATE(){
//                return this.READINGDATE;
//            }
//            public void setSTATUS(String STATUS){
//                this.STATUS = STATUS;
//            }
//            public String getSTATUS(){
//                return this.STATUS;
//            }
//            public void setTARIFF(String TARIFF){
//                this.TARIFF = TARIFF;
//            }
//            public String getTARIFF(){
//                return this.TARIFF;
//            }
//            public void setUNITS(String UNITS){
//                this.UNITS = UNITS;
//            }
//            public String getUNITS(){
//                return this.UNITS;
//            }
//            public void setBillType(String billType){
//                this.billType = billType;
//            }
//            public String getBillType(){
//                return this.billType;
//            }
//            public void setCity(String city){
//                this.city = city;
//            }
//            public String getCity(){
//                return this.city;
//            }
//            public void setLastYearBills(List<List<String>> lastYearBills){
//                this.lastYearBills = lastYearBills;
//            }
//            public List<List<String>> getLastYearBills(){
//                return this.lastYearBills;
//            }
//            public void setReferenceNumber(String referenceNumber){
//                this.referenceNumber = referenceNumber;
//            }
//            public String getReferenceNumber(){
//                return this.referenceNumber;
//            }
//        }
//
//
//        private IescoBill iescoBill;
//
//        public void setIescoBill(IescoBill iescoBill){
//            this.iescoBill = iescoBill;
//        }
//        public IescoBill getIescoBill(){
//            return this.iescoBill;
//        }


    }

}


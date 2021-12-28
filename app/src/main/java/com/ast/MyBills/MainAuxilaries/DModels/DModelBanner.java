package com.ast.MyBills.MainAuxilaries.DModels;

import android.graphics.drawable.Drawable;

public class DModelBanner {


    String id;
    String featureBanner;
  public   Drawable uploadPresp;
    boolean isFromApi;
   public DModelBanner(String  id , String icon, Drawable uploadPrsc, boolean isApi){

       this.id= id;
       this.featureBanner= icon;
       this.uploadPresp= uploadPrsc;
       this.isFromApi = isApi;

    }

    public String getFeatureBanner() {
        return featureBanner;
    }

    public void setFeatureBanner(String featureBanner) {
        this.featureBanner = featureBanner;
    }

    public Drawable getUploadPresp() {
        return uploadPresp;
    }

    public void setUploadPresp(Drawable uploadPresp) {
        this.uploadPresp = uploadPresp;
    }

    public boolean isFromApi() {
        return isFromApi;
    }

    public void setFromApi(boolean fromApi) {
        isFromApi = fromApi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

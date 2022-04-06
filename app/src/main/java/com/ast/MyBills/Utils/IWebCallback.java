package com.ast.MyBills.Utils;


/**
 * Created by usamak.mirza@gmail.com
 */


public interface IWebCallback {

    void onWebResult(boolean isSuccess, String strMsg);

    void onWebException(Exception ex);
}

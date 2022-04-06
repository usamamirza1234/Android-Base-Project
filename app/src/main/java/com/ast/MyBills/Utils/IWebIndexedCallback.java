package com.ast.MyBills.Utils;


/**
 * Created by usamak.mirza@gmail.com
 */


public interface IWebIndexedCallback {

    void onWebResult(boolean isSuccess, String strMsg, int index);

    void onWebException(Exception ex, int index);
}

package com.ast.MyBills.Utils;

/**
 * Created by usamak.mirza@gmail.com
 */


public interface IWebPaginationCallback {

    void onWebInitialResult(boolean isSuccess, String strMsg);

    void onWebSuccessiveResult(boolean isSuccess, boolean isCompleted, String strMsg);

    void onWebInitialException(Exception ex);

    void onWebSuccessiveException(Exception ex);
}

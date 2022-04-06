package com.ast.MyBills.Utils;


/**
 * Created by usamak.mirza@gmail.com
 */


public interface IBadgeUpdateListener {

    public void setBottomTabVisiblity(int mVisibility);

    public void setToolbarVisiblity(int mVisibility);

    public void setToolbarState(byte mState);

    public void switchStatusBarColor(boolean isDark);

    public boolean setHeaderTitle(String strAppTitle);



}

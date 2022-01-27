package com.ast.MyBills.MainAuxilaries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.IntroAuxilaries.MyBillsFragment;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IBadgeUpdateListener;


public class EditProfileFragment extends Fragment implements View.OnClickListener {

    IBadgeUpdateListener mBadgeUpdateListener;
RelativeLayout rlNext;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        init();
        bindviews(frg);


        return frg;
    }

    private void init() {
        setBottomBar();
    }

    private void bindviews(View view) {
        rlNext= view.findViewById(R.id.frg_editprofile_rlnext);
        rlNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.frg_editprofile_rlnext:
                    navToBillerSettingFragment();
                    break;



}
    }


    void setBottomBar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.ToolbarState.TOOLBAR_VISIBLE);
            mBadgeUpdateListener.setHeaderTitle(getString(R.string.frg_edit_profile));

        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setBottomBar();
        }
    }

    private void navToBillerSettingFragment() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new MyBillsFragment();
        ft = fm.beginTransaction();
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_BillerSettingFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_BillerSettingFragment);
        ft.hide(this);
        ft.commit();
    }



}

package com.ast.MyBills.MainAuxilaries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.IntroAuxilaries.MyBillsFragment;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_CreateAccount;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_EditProfile;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.google.gson.JsonObject;

import java.util.ArrayList;


public class EditProfileFragment extends Fragment implements View.OnClickListener {

    IBadgeUpdateListener mBadgeUpdateListener;
RelativeLayout rlNext;
EditText edtName,edtPhone,edtEmail;

    ArrayList<DModel_EditProfile> lstEditProfile;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        init();
        bindviews(frg);


        return frg;
    }

    private void init() {
        setBottomBar();

        lstEditProfile = AppConfig.getInstance().getEditProfile();
    }

    private void bindviews(View view) {
        rlNext= view.findViewById(R.id.frg_editprofile_rlnext);

        edtName= view.findViewById(R.id.frg_edit_profile_edt_Name);
        edtEmail= view.findViewById(R.id.frg_edit_profile_edt_email);
        edtPhone= view.findViewById(R.id.frg_edit_profile_edt_phone);
        rlNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.frg_editprofile_rlnext:
                    checkErrorConditions();
                    closeKeyboard();

                    break;



}
    }

    private void checkErrorConditions() {
        if (checkPasswordError()) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Name", edtName.getText().toString());
            jsonObject.addProperty("Email", edtEmail.getText().toString());
            jsonObject.addProperty("Number", edtPhone.getText().toString());

            lstEditProfile.add(
                    new DModel_EditProfile(edtName.getText().toString() + "",
                            edtEmail.getText().toString() + "",
                            edtPhone.getText().toString() + ""));

            AppConfig.getInstance().saveEditProfile(lstEditProfile);

            navToBillerSettingFragment();

        }
    }

    private boolean checkPasswordError() {
        if (!edtName.getText().toString().equalsIgnoreCase("") && !edtEmail.getText().toString().isEmpty() && !edtPhone.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_LONG).show();
            return false;
        }

    }


    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());
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

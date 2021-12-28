package com.ast.MyBills.IntroAuxilaries;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.IntroActivity;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlSignin, rlSignUp, rlReset;
    RelativeLayout rlBack;
    EditText etEmail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_forgot_password, container, false);



        rlBack = frg.findViewById(R.id.frg_forgot_password_rlToolbar);
        rlSignin = frg.findViewById(R.id.frg_forgot_password_rlLogin);
        rlReset = frg.findViewById(R.id.frg_forgot_password_reset);
        rlSignUp = frg.findViewById(R.id.frg_forgot_password_rlCreate);

        etEmail = frg.findViewById(R.id.frg_forgot_password_edt_username);

        rlBack.setOnClickListener(this);
        rlSignin.setOnClickListener(this);
        rlSignUp.setOnClickListener(this);
        rlReset.setOnClickListener(this);


        return frg;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_forgot_password_rlLogin:
                navToSignInFragment();
                break;

            case R.id.frg_forgot_password_reset:
                validateInput();
                //navToSignInFragment();
                break;

            case R.id.frg_forgot_password_rlCreate:
                navToSignUpFragment();
                break;
            case R.id.frg_forgot_password_rlToolbar:
                ((IntroActivity) getActivity()).onBackPressed();
                break;
        }
    }

    private void navToSignUpFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUpFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignUpFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignUpFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navToSignInFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignInFragment();
        ft.replace(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
        ft.hide(this);
        ft.commit();
    }



    boolean validateInput() {

        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }


        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}

package com.ast.MyBills.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;


public class PreSignInFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlSignin, rlSignUp, rlForgot;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_pre_sign_in, container, false);


        rlSignin = frg.findViewById(R.id.frg_presigin_rllogin);
        rlSignUp = frg.findViewById(R.id.frg_presigin_rlregister);
        rlForgot = frg.findViewById(R.id.frg_presigin_rlforgot);


        rlSignin.setOnClickListener(this);
        rlSignUp.setOnClickListener(this);
        rlForgot.setOnClickListener(this);

        return frg;
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_presigin_rllogin:
                navToSignInFragment();
                break;

            case R.id.frg_presigin_rlregister:
                navToSignUpFragment();
                break;

            case R.id.frg_presigin_rlforgot:
                navToForgotPasswordFragment();
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
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToForgotPasswordFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ForgotPasswordFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
        ft.hide(this);
        ft.commit();
    }
}

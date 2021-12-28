package com.ast.MyBills.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.IntroActivity;
import com.ast.MyBills.MainAuxilaries.EditProfileFragment;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;


public class SignUpFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlBack, rlNext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_up, container, false);


        rlBack = frg.findViewById(R.id.frg_signup_rlToolbar);
        rlNext = frg.findViewById(R.id.frg_presigin_rlnext);
        rlBack.setOnClickListener(this);
        rlNext.setOnClickListener(this);

        return frg;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.frg_signup_rlToolbar:
                ((IntroActivity) getActivity()).onBackPressed();
                break;



            case R.id.frg_presigin_rlnext:
              navToMyBillsFragment();

                break;
        }


    }




    private void navToMyBillsFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new MyBillsFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyBillsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyBillsFragment);
        ft.hide(this);
        ft.commit();
    }


}

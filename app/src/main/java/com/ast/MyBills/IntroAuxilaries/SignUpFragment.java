package com.ast.MyBills.IntroAuxilaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.IntroActivity;
import com.ast.MyBills.IntroAuxilaries.WebServices.Intro_WebHit_Post_OTP;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_CreateAccount;
import com.ast.MyBills.MainAuxilaries.VerificationFragment_OTP;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.DatabaseHelper;
import com.ast.MyBills.Utils.IWebCallback;
import com.ast.MyBills.Utils.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;


public class SignUpFragment extends Fragment implements View.OnClickListener {


    EditText etOTP,etUsername,etPassword,etEmail;
    TextView verifyNumber;
    RelativeLayout rlBack, rlNext;
    private Dialog progressDialog;
    ArrayList<DModel_CreateAccount> lstCreateAccount;
    int randomNumber;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_up, container, false);

        init();
        verifyNumber = frg.findViewById(R.id.verifyno);
        etOTP = frg.findViewById(R.id.etOTP);
        etEmail = frg.findViewById(R.id.frg_signup_edt_email);
        etPassword = frg.findViewById(R.id.frg_signup_edt_password);
        etUsername = frg.findViewById(R.id.frg_signup_edt_username);



        Random random = new Random();
        randomNumber = random.nextInt(9999);

        rlBack = frg.findViewById(R.id.frg_signup_rlToolbar);
        rlNext = frg.findViewById(R.id.frg_presigin_rlnext);
        rlBack.setOnClickListener(this);
        rlNext.setOnClickListener(this);
        verifyNumber.setOnClickListener(this);




        ?? check this method if work then it is perefered if not
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        User user = new User();
        user.setEmail("usama@gmail.com");
        user.setName("usama");
        user.setPassword("pass");
        databaseHelper.addUser(user);  ?? ADD USER


        ?? Method 2
        ?? onclick save/register
                ?? save this password against this email this would register user
        AppConfig.getInstance().setPassword(etEmail.getText().toString(), etPassword.getText().toString());

        return frg;
    }
    private void init() {

        lstCreateAccount = AppConfig.getInstance().getCreateAccount();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.frg_signup_rlToolbar:
                ((IntroActivity) getActivity()).onBackPressed();

                break;



            case R.id.frg_presigin_rlnext:

                checkErrorConditions();
                closeKeyboard();
                break;

        }


    }
    String data ="";

    private void RequestOTP(String data) {
        showProgDialog();
        Intro_WebHit_Post_OTP intro_webHit_post_otp = new Intro_WebHit_Post_OTP();

        intro_webHit_post_otp.postOTP(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                dismissProgDialog();
                if (isSuccess) {
//                    if (Intro_WebHit_Post_OTP.responseObject != null &&
//                            Intro_WebHit_Post_OTP.responseObject.getResult() != null) {
//
//
//                   //     navToCompleteFAProfileFragment();
                   navOTPFragment();
//
//                    }

                } else {
                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
//                CustomToast.showToastMessage(IntroActivity.this, AppConfig.getInstance().getNetworkExceptionMessage(ex.getMessage()), Toast.LENGTH_SHORT);
                AppConfig.getInstance().showErrorMessage(getContext(), ex.getLocalizedMessage());
            }
        }, data);

    }

    //region  functions for Dialog
    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        progressDialog.setCancelable(false);
        progressDialog.show();
    }



    private void checkErrorConditions() {
        if (checkPasswordError()) {

            String strPhoneNumber = etOTP.getText().toString();
            strPhoneNumber = "" + strPhoneNumber.substring(0);

            AppConfig.getInstance().mUser.setPhone(strPhoneNumber);

            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("loginId", "923168159860");
            jsonObject1.addProperty("loginPassword",   "Roberts1234@");
            jsonObject1.addProperty("Destination", etOTP.getText().toString());
            jsonObject1.addProperty("Mask","smsapp");
            jsonObject1.addProperty("Message",randomNumber);
            jsonObject1.addProperty("Unicode", "0");
            jsonObject1.addProperty("ShortCodePrefered", "n");
            Log.d("LOG_AS", "postSignUp: " + jsonObject1.toString());

            RequestOTP(jsonObject1.toString());


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Name", etUsername.getText().toString());
            jsonObject.addProperty("Email", etEmail.getText().toString());
            jsonObject.addProperty("Password", etPassword.getText().toString());
            jsonObject.addProperty("Number", etOTP.getText().toString());

            lstCreateAccount.add(
                    new DModel_CreateAccount(etUsername.getText().toString() + "",
                            etEmail.getText().toString() + "",
                            etPassword.getText().toString() + "",
                            etOTP.getText().toString() + ""));

            AppConfig.getInstance().saveCreateAccount(lstCreateAccount);




        }
    }

    private boolean checkPasswordError() {
        if (!etUsername.getText().toString().equalsIgnoreCase("") && !etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty() && !etOTP.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_LONG).show();
            return false;
        }

    }


    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());
    }
//SMS





    private void navToMyBillsFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new MyBillsFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyBillsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyBillsFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navOTPFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new VerificationFragment_OTP();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyBillsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyBillsFragment);
        ft.hide(this);
        ft.commit();
    }



}

package com.ast.MyBills.IntroAuxilaries;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.IntroActivity;
import com.ast.MyBills.IntroAuxilaries.WebServices.Intro_WebHit_Post_OTP;
import com.ast.MyBills.IntroAuxilaries.WebServices.More_WebHit_Get_Bills;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bills;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_CreateAccount;
import com.ast.MyBills.MainAuxilaries.SmsBroadcastReceiver;
import com.ast.MyBills.MainAuxilaries.VerificationFragment_OTP;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IWebCallback;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    EditText etOTP,etUsername,etPassword,etEmail;
    TextView verifyNumber;
    RelativeLayout rlBack, rlNext;
    private Dialog progressDialog;
    ArrayList<DModel_CreateAccount> lstCreateAccount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_up, container, false);

        init();
        verifyNumber = frg.findViewById(R.id.verifyno);
        etOTP = frg.findViewById(R.id.etOTP);
        etEmail = frg.findViewById(R.id.frg_signup_edt_email);
        etPassword = frg.findViewById(R.id.frg_signup_edt_password);
        etUsername = frg.findViewById(R.id.frg_signup_edt_username);




        rlBack = frg.findViewById(R.id.frg_signup_rlToolbar);
        rlNext = frg.findViewById(R.id.frg_presigin_rlnext);
        rlBack.setOnClickListener(this);
        rlNext.setOnClickListener(this);
        verifyNumber.setOnClickListener(this);
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

            case R.id.verifyno:

                String strPhoneNumber = etOTP.getText().toString();
                strPhoneNumber = "92" + strPhoneNumber.substring(1);

                AppConfig.getInstance().mUser.setPhone(strPhoneNumber);
                RequestOTP(data);

                //startSmartUserConsent();
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

            navToMyBillsFragment();

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


    private void startSmartUserConsent() {
        registerBroadcastReceiver();
        Toast. makeText(getActivity(), "0!", Toast. LENGTH_SHORT).show();
        SmsRetrieverClient client = SmsRetriever.getClient(getContext());
        client.startSmsUserConsent(null);
        //

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast. makeText(getActivity(), "2!", Toast. LENGTH_SHORT).show();

        if (requestCode == REQ_USER_CONSENT){


            if ((resultCode == RESULT_OK) && (data != null)){

                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);


            }


        }

    }

    private void getOtpFromMessage(String message) {

        Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()){

            etOTP.setText(matcher.group(0));
            Toast. makeText(getActivity(), "1!", Toast. LENGTH_SHORT).show();
        }


    }

    private void registerBroadcastReceiver(){
        Toast. makeText(getActivity(), "3!", Toast. LENGTH_SHORT).show();
        smsBroadcastReceiver = new SmsBroadcastReceiver();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {

                startActivityForResult(intent,REQ_USER_CONSENT);

            }

            @Override
            public void onFailure() {

            }
        };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerForContextMenu(getView());

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

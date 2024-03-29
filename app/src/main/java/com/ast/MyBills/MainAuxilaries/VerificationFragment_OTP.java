package com.ast.MyBills.MainAuxilaries;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.IntroActivity;
import com.ast.MyBills.IntroAuxilaries.MyBillsFragment;
import com.ast.MyBills.IntroAuxilaries.SignInFragment;
import com.ast.MyBills.IntroAuxilaries.SignUpFragment;
import com.ast.MyBills.IntroAuxilaries.WebServices.Intro_WebHit_Post_OTP;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_EditProfile;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.CustomToast;
import com.ast.MyBills.Utils.IWebCallback;
import com.ast.MyBills.Utils.PinEntry;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class VerificationFragment_OTP extends Fragment implements View.OnClickListener, OTPListener {


    final static int resendTries = 2;
    private static final long TIME_COUNTDOWN = 5 * 1000;
    private static final int PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
    RelativeLayout rlSubmit;
    int pinCodeTries = 1;
    private String strEnteredPIN, strPIN, strPhone;
    private EditText edtCode;
    private Button btnConfirm;
    private TextView txvResend, txvCountDown;
    private PinEntry mPIN;
    private TextView txvNtReceived;
    private TextView txvNtReceivedShown;
    private TextView txvOTP;
    private CountDownTimer mTimer;
    private Dialog progressDialog;


    int randomNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_verification_fa, container, false);

        initData();
        bindViews(frg);
        showSoftKeyboardForced();
        startCountDownTimer(TIME_COUNTDOWN);

        Random random = new Random();
        randomNumber = random.nextInt(9999);
        String strPhoneNumber = String.valueOf(AppConfig.getInstance().mUser.getPhone());
        strPhoneNumber = "" + strPhoneNumber.substring(1);
        strPhoneNumber = "0" + strPhoneNumber.substring(1);

        String str_changeTextColot = getColoredSpanned("", "#A0A0A0");
        str_changeTextColot = getColoredSpanned(strPhoneNumber, "#7DD92958");


        String str_firstText = getResources().getString(R.string.verification_code_send);
        String str_lastText = getResources().getString(R.string.verification_code_send_2);

        txvOTP.setText(Html.fromHtml(str_firstText + " " + str_changeTextColot + ". " + str_lastText + ". "));


        Log.d("LOG_AS", "postSignUp: getPhone " + AppConfig.getInstance().mUser.getPhone());


        return frg;
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

    //endregion


    private void requestVerifyPin(String data) {
        showProgDialog();
        Intro_WebHit_Post_OTP intro_webHit_post_otp = new Intro_WebHit_Post_OTP();

        intro_webHit_post_otp.postOTP(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                dismissProgDialog();
                if (isSuccess) {
//                    if (Intro_WebHit_Post_OTP.responseObject != null &&
//                            Intro_WebHit_Post_OTP.responseObject.getResult() != null) {


                            navToSignUpFragment();

//
//                    }

                } else {
                  //  AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
//                CustomToast.showToastMessage(IntroActivity.this, AppConfig.getInstance().getNetworkExceptionMessage(ex.getMessage()), Toast.LENGTH_SHORT);
                //AppConfig.getInstance().showErrorMessage(getContext(), ex.getLocalizedMessage());
            }
        }, data);

    }

    private void navToSignUpFragment() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUpFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isComingFromIntro",true);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignUpFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignUpFragment);
        frag.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }
    private void navLogin() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignInFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
        ft.hide(this);
        ft.commit();
    }

    private void initData() {
        // Request the permission immediately here for the first time run
        requestPermissions(Manifest.permission.RECEIVE_SMS, PERMISSIONS_REQUEST_RECEIVE_SMS);

        strEnteredPIN = "";
        strPIN = "";
        strPhone = "";
        OtpReader.bind(this, "8700");
    }

    private void requestPermissions(String permission, int requestCode) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(getContext(), "Granting permission is necessary!", Toast.LENGTH_LONG).show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{permission},
                        requestCode);

                // requestCode is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_RECEIVE_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

//                    NotificationUtil.getInstance().show(this, NotificationUtil.CONTENT_TYPE.INFO,
//                            getResources().getString(R.string.app_name),
//                            "Permission granted!");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

//                    NotificationUtil.getInstance().show(this, NotificationUtil.CONTENT_TYPE.ERROR,
//                            getResources().getString(R.string.app_name),
//                            "Permission denied! App will not function correctly");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void otpReceived(String smsText) {
        //Do whatever you want to do with the text

        String number = smsText.replaceAll("\\D+", "");
        try {
            mPIN.setText(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Toast.makeText(getContext(),"Got "+number,Toast.LENGTH_LONG).show();
        Log.d("Otp", number);
    }

    private void startCountDownTimer(long mCountDownTime) {
        txvNtReceived.setOnClickListener(null);

        txvNtReceived.setTextColor(getResources().getColor(R.color.gray));

        txvCountDown.setVisibility(View.VISIBLE);
        if (mTimer != null)
            mTimer.cancel();

        mTimer = new CountDownTimer(mCountDownTime, 1000) {

            public void onTick(long millisUntilFinished) {
//                txvCountdown.setText(millisUntilFinished / 1000 + " ");
                if (txvCountDown != null)
                    if ((millisUntilFinished % 60000 / 1000) < 10) {
                        txvCountDown.setText("0" + (millisUntilFinished / 60000) + ":" + "0" + (millisUntilFinished % 60000 / 1000));
                    } else {
                        txvCountDown.setText("0" + (millisUntilFinished / 60000) + ":" + (millisUntilFinished % 60000 / 1000));
                    }

//                remMillis = millisUntilFinished;
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                txvNtReceived.setVisibility(View.VISIBLE);
                hideCountDownTimer();
            }

        }.start();
    }

    private void hideCountDownTimer() {
        try {
            if (mTimer != null)
                mTimer.cancel();

            txvNtReceived.setOnClickListener(this);

            txvNtReceived.setTextColor(getResources().getColor(R.color.black));

            txvCountDown.setVisibility(View.INVISIBLE);

            txvCountDown.setText("00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews(View frg) {


        mPIN = frg.findViewById(R.id.frg_sign_up_verifictn_pin_entry);
        txvNtReceived = frg.findViewById(R.id.frg_sign_up_verifictn_txv_nt_received);
        txvNtReceivedShown = frg.findViewById(R.id.frg_sign_up_verifictn_txv_nt_received_showen);
        txvOTP = frg.findViewById(R.id.frg_varification_va_txv_txv_otp);
        txvCountDown = frg.findViewById(R.id.frg_sign_up_verifictn_txv_countdown);
        mPIN.setOnPinEnteredListener(new PinEntry.OnPinEnteredListener() {
            @Override
            public void onPinEntered(String pin) {
                if (pin.length() == 4) {
                    strEnteredPIN = pin;
                    AppConfig.getInstance().closeKeyboard(getActivity());
                } else {
                    strEnteredPIN = "";
                }
            }
        });


        rlSubmit = frg.findViewById(R.id.frg_verifiacction_rl_login);


        txvNtReceived.setOnClickListener(this);
        rlSubmit.setOnClickListener(this);
//        if (strEnteredPIN.length() == 6) {
//            if (AppConfig.getInstance().isComingFromHome) {
//                progressDilogue.startiOSLoader(getActivity(), R.drawable.image_for_rotation, getString(R.string.please_wait), false);
//                requestValidateCode(AppConfig.getInstance().mUser.getmPhoneNumber(), strEnteredPIN);
//            } else {
//                progressDilogue.startiOSLoader(getActivity(), R.drawable.image_for_rotation, getString(R.string.please_wait), false);
//                requestValidateCode(strPhone, strEnteredPIN);
//            }
////                    hideCountDownTimer();
//        } else {
//           showAlertDialog(getString(R.string.sign_up_enter_account_setup_heading), getString(R.string.enter_valid_pin_message), null, null, false, true, null);
//        }

//        btnConfirm = frg.findViewById(R.id.frg_vrfctn_btn_confirm);
//        txvResend = frg.findViewById(R.id.frg_vrfctn_txv_resend);
//        btnConfirm.setTransformationMethod(null);
//
//        btnConfirm.setOnClickListener(this);
//        txvResend.setOnClickListener(this);
//
//
//        edtCode = frg.findViewById(R.id.frg_vrfctn_edt_code);
//
//        //Language based special cases
//        if (AppConfig.getInstance().isEnglishMode) {
//            edtCode.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//        } else {
//            edtCode.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//        }
    }


    private void hideMyKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtCode.getWindowToken(), 0);
    }

    private void showSoftKeyboardForced() {
        try {
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            edtCode.postDelayed(new Runnable() {
                @Override
                public void run() {
                    edtCode.requestFocus();
                    imm.showSoftInput(edtCode, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_sign_up_verifictn_txv_nt_received:




                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("loginId", "923168159860");
                jsonObject.addProperty("loginPassword",   "Roberts1234@");
                jsonObject.addProperty("Destination", AppConfig.getInstance().mUser.getPhone());
                jsonObject.addProperty("Mask","smsapp");
                jsonObject.addProperty("Message",randomNumber);
                jsonObject.addProperty("Unicode", "0");
                jsonObject.addProperty("ShortCodePrefered", "n");
                Log.d("LOG_AS", "postSignUp: " + jsonObject.toString());
                //requestCode();


                Log.d("LOG_AS", "Resend " + resendTries);
                Log.d("LOG_AS", "PinCodeTries " + pinCodeTries);
                if (pinCodeTries < resendTries) {
                    requestResendOTP(jsonObject.toString());
                    CustomToast.showToastMessage(getActivity(),"OTP Sent", Toast.LENGTH_SHORT);
                } else {

                    showOTP();

                }




                break;
            case R.id.frg_verifiacction_rl_login:
               // navToMyBillsFragment();

                navLogin();

            //    checkErrorConditions();
                //  navToSignUpFragment();

                Log.d("LOG_AS", "postOTP: getPinCode " + AppConfig.getInstance().mUser.getPinCode());
                Log.d("LOG_AS", "postOTP: strEnteredPIN " + strEnteredPIN);

                if (mPIN.getText().toString().equalsIgnoreCase(String.valueOf(AppConfig.getInstance().mUser.getPinCode()))) {

                    Log.d("LOG_AS", "postOTP: getPhone " + AppConfig.getInstance().mUser.getPhone());


//                    JsonObject jsonObject1 = new JsonObject();
//                    jsonObject1.addProperty("loginId", "923168159860");
//                    jsonObject1.addProperty("loginPassword",   "Roberts1234@");
//                    jsonObject1.addProperty("Destination", AppConfig.getInstance().mUser.getPhone());
//                    jsonObject1.addProperty("Mask","smsapp");
//                    jsonObject1.addProperty("Message",randomNumber);
//                    jsonObject1.addProperty("Unicode", "0");
//                    jsonObject1.addProperty("ShortCodePrefered", "n");
//                    Log.d("LOG_AS", "postSignUp: " + jsonObject1.toString());


                 //  requestVerifyPin( jsonObject1.toString());

                }

                break;
            default:
                break;
        }
    }


    private void checkErrorConditions() {
        if (checkPasswordError()) {
            if (!mPIN.getText().toString().equalsIgnoreCase("") && !mPIN.getText().toString().isEmpty()) {

                Toast.makeText(getContext(), "Enter Pin", Toast.LENGTH_LONG).show();


            }
        }
    }

    private boolean checkPasswordError() {
        if (!mPIN.getText().toString().equalsIgnoreCase("") && !mPIN.getText().toString().isEmpty() ) {
            return true;
        } else {
            Toast.makeText(getContext(), "Enter Pin", Toast.LENGTH_LONG).show();
            return false;
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


    private void showOTP() {
        String str_changeTextColot = getColoredSpanned("", "#A0A0A0");

        str_changeTextColot = getColoredSpanned(String.valueOf(AppConfig.getInstance().mUser.getPinCode()), "#E80F1F");

        txvNtReceived.setVisibility(View.GONE);

        String str_firstText = getResources().getString(R.string.technical_fault_otp);

        txvNtReceivedShown.setText(Html.fromHtml(str_firstText + " " + str_changeTextColot));


        mPIN.setText(Html.fromHtml(str_changeTextColot));

        txvNtReceivedShown.setVisibility(View.VISIBLE);
    }


    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    String data ="";
    private void requestResendOTP(String data) {



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
                       // navToSignUpFragment();
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
    }



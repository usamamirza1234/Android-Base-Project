package com.ast.MyBills.MainAuxilaries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ast.MyBills.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class OTPFragment extends Fragment implements View.OnClickListener  {
    Button _btnLogin, _btnVerOTP;
    EditText _txtName, _txtPhone, _txtVerOTP;
    int randomNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_o_t_p, container, false);

        initData();
        bindViews(frg);
        return frg;

    }

    private void initData() {

    }

    private void bindViews(View frg) {
        _txtName=(EditText)frg.findViewById(R.id.txtName);
        _txtPhone=(EditText)frg.findViewById(R.id.txtPhone);
        _txtVerOTP=(EditText)frg.findViewById(R.id.txtVerOTP);
        _btnLogin=(Button)frg.findViewById(R.id.btnLogin);
        _btnVerOTP=(Button)frg.findViewById(R.id.btnVerOTP);
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        _btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btnLogin:

                try {
// Construct data
                    String apiKey = "apikey=" + "Ek6sgY0CBTg-f67dbnK1IZFyWho2GHAu6WMViPAqld";
                    Random random = new Random();
                    randomNumber = random.nextInt(999999);
                    String message = "&amp;message=" + "Hiiiii " + _txtName.getText().toString() + "Your OTP IS " + randomNumber;
                    String sender = "&amp;sender=" + "03066913702";
                    String numbers = "&amp;numbers=" + _txtPhone.getText().toString();

// Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://cbs.zong.com.pk/reachrestapi/home/SendQuickSMS").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    rd.close();
                    Toast.makeText(getContext(), "OTP SEND SUCCESSFULLY", Toast.LENGTH_LONG).show();

//return stringBuffer.toString();
                } catch (Exception e) {
//System.out.println("Error SMS "+e);
///return "Error "+e;
                    Toast.makeText(getContext(), "ERROR SMS " + e, Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "ERROR " + e, Toast.LENGTH_LONG).show();
                }



//                if (randomNumber == Integer.valueOf(_txtVerOTP.getText().toString())) {
//                    Toast.makeText(getContext(), "You are logined successfully", Toast.LENGTH_LONG).show();
//
//                } else {
//                    Toast.makeText(getContext(), "WRONG OTP", Toast.LENGTH_LONG).show();
//                }


                break;



        }
    }









}
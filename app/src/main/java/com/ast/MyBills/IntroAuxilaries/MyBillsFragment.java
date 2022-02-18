package com.ast.MyBills.IntroAuxilaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.IntroActivity;
import com.ast.MyBills.IntroAuxilaries.WebServices.More_WebHit_Get_Bills;
import com.ast.MyBills.MainAuxilaries.Adapters.BillListingRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.BillTypeSpinnerAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.BillerSpinnerAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bills;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_ImportantDates;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.ast.MyBills.Utils.IWebCallback;
import com.google.gson.JsonObject;

import java.util.ArrayList;


public class MyBillsFragment extends Fragment implements View.OnClickListener {

    IBadgeUpdateListener mBadgeUpdateListener;

    ArrayList<DModel_Bills> lstMyBills;
    ArrayList<String> lstImpDates;
    BillListingRcvAdapter billListingRcvAdapter;
    BillTypeSpinnerAdapter billTypeSpinnerAdapter;
    BillerSpinnerAdapter billerSpinnerAdapter;

    RelativeLayout rlAdd;

    RecyclerView rcv_myBills;
    Spinner spinnerBillType;
    Spinner spinnerBiller;
    RelativeLayout biller;
    String str_billType = "";
    RelativeLayout rlBack;
    ArrayList<DModel_Bill> lastBill;
    String strbiller;
    private TextView txvBillType;
    private TextView edt_Reffrence;
    private TextView edt_Account;
    private Dialog progressDialog;
    private ArrayList<DModelBillInfo> lstBillInfo;
    private ArrayList<DModel_Bills> lstImp;
    private final String TAG = "MYBILL";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_bills, container, false);

        init();
        bindviews(frg);
        populateMyBills(lstMyBills);

        populateSpinnerBillType();
        populateSpinnerBiller();


        return frg;
    }

    private void populateSpinnerBillType() {
        ArrayList<String> lstGender = new ArrayList<>();


        lstGender.add("Electricity");
        lstGender.add("Gas");
        lstGender.add("Water");
        lstGender.add("Telephone");
        lstGender.add("Mobile Phone");

        lstGender.add("Education Fee");
        lstGender.add("TV Cable");
        lstGender.add("Internet");
        lstGender.add("Society Bill");
        lstGender.add("Loan");
        lstGender.add("Insurance");
        lstGender.add("Credit Line");
        lstGender.add(getResources().getString(R.string.select_bill_type));


        billTypeSpinnerAdapter = new BillTypeSpinnerAdapter(getContext(), lstGender);
        spinnerBillType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                int Pos = Integer.parseInt(selectedItem);
                // txvBillType.setText(lstGender.get(position));
                str_billType = (lstGender.get(position));


                ArrayList<String> lstBiller = new ArrayList<>();
                switch (str_billType) {
                    case "Electricity":
                        // CustomToast.showToastMessage(getActivity(), str_billType + " selected", Toast.LENGTH_LONG);


                        lstBiller.add("IESCO");
                        lstBiller.add("LESCO");
                        lstBiller.add("PESCO");
                        lstBiller.add("GEPCO");

                        lstBiller.add("FESCO");
                        lstBiller.add("MEPCO");
                        lstBiller.add("HESCO");
                        lstBiller.add("SEPCO");
                        lstBiller.add("QESCO");
                        lstBiller.add("TESCO");

                        lstBiller.add(getResources().getString(R.string.select_biller_type));
                        billerSpinnerAdapter = new BillerSpinnerAdapter(getContext(), lstBiller);
                        spinnerBiller.setAdapter(billerSpinnerAdapter);
                        spinnerBiller.setSelection(billerSpinnerAdapter.getCount());
                        break;

                    case "Gas":
                        //  CustomToast.showToastMessage(getActivity(), str_billType + " selected", Toast.LENGTH_LONG);

                        lstBiller.add("SNGPL");


                        lstBiller.add(getResources().getString(R.string.select_biller_type));
                        billerSpinnerAdapter = new BillerSpinnerAdapter(getContext(), lstBiller);
                        spinnerBiller.setAdapter(billerSpinnerAdapter);
                        spinnerBiller.setSelection(billerSpinnerAdapter.getCount());
                        break;

                    case "Water":
                        //  CustomToast.showToastMessage(getActivity(), str_billType + " selected", Toast.LENGTH_LONG);


                        lstBiller.add("WASA");


                        lstBiller.add(getResources().getString(R.string.select_biller_type));
                        billerSpinnerAdapter = new BillerSpinnerAdapter(getContext(), lstBiller);
                        spinnerBiller.setAdapter(billerSpinnerAdapter);
                        spinnerBiller.setSelection(billerSpinnerAdapter.getCount());
                        break;

                    case "Telephone":
                        // CustomToast.showToastMessage(getActivity(), str_billType + " selected", Toast.LENGTH_LONG);


                        lstBiller.add("PTCL");


                        lstBiller.add(getResources().getString(R.string.select_biller_type));
                        billerSpinnerAdapter = new BillerSpinnerAdapter(getContext(), lstBiller);
                        spinnerBiller.setAdapter(billerSpinnerAdapter);
                        spinnerBiller.setSelection(billerSpinnerAdapter.getCount());
                        break;
                }

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBillType.setAdapter(billTypeSpinnerAdapter);
        spinnerBillType.setSelection(billTypeSpinnerAdapter.getCount());


    }

    private void populateSpinnerBiller() {
        ArrayList<String> lstBiller = new ArrayList<>();

//        lstBiller.add("IESCO");
//        lstBiller.add("WAPDA");
//        lstBiller.add("GEPCO");
//        lstBiller.add("PESCO");

        lstBiller.add(getResources().getString(R.string.select_biller_type));

        billerSpinnerAdapter = new BillerSpinnerAdapter(getContext(), lstBiller);
        spinnerBiller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                int Pos = Integer.parseInt(selectedItem);
                // txvBillType.setText(lstGender.get(position));

//                strbiller = lstBiller.get(position);
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBiller.setAdapter(billerSpinnerAdapter);
        spinnerBiller.setSelection(billerSpinnerAdapter.getCount());


    }

    private void populateMyBills(ArrayList<DModel_Bills> lstMyBills) {
//        int size = (2);
//        for (int i = size; i >= 0; i--) {
//            lstMyBills.add(new DModel_Bills("Bill", "100"+i, (i + 1) + ""));
//
//        }

        if (billListingRcvAdapter == null) {

            billListingRcvAdapter = new BillListingRcvAdapter(getActivity(), lstMyBills, (eventId, position) -> {

            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcv_myBills.setLayoutManager(linearLayoutManager);
            rcv_myBills.setAdapter(billListingRcvAdapter);

        } else {
            billListingRcvAdapter.notifyDataSetChanged();
        }

    }

    private void init() {
        setBottomBar();
        lstImp = new ArrayList<>();
        lstImpDates = new ArrayList<>();
        // lstMyBills = new ArrayList<>();
        lstBillInfo = new ArrayList<>();
        lastBill = new ArrayList<>();
        lstMyBills = AppConfig.getInstance().getBillerSetting();
    }

    private void bindviews(View view) {
        rcv_myBills = view.findViewById(R.id.frg_my_bills_rcvBills);


        edt_Reffrence = view.findViewById(R.id.frg_my_bills_edt_ref);
        edt_Account = view.findViewById(R.id.frg_my_bills_edt_acc_num);


        rlAdd = view.findViewById(R.id.frg_my_bills_rlAdd);
        spinnerBillType = view.findViewById(R.id.frg_my_bills_spinnerBilltpe);
        spinnerBiller = view.findViewById(R.id.frg_my_bills_spinnerBiller);
        txvBillType = view.findViewById(R.id.frg_my_bills_txvBilltype);


        rlBack = view.findViewById(R.id.frg_signin_rlToolbar);
        rlBack.setOnClickListener(this);

        rlAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_my_bills_rlAdd:

                checkErrorConditions();
                closeKeyboard();

                break;

            case R.id.frg_signin_rlToolbar:
                getActivity().onBackPressed();
                break;

        }
    }


    private void checkErrorConditions() {
        if (checkPasswordError()) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("ref", edt_Reffrence.getText().toString());
            //  jsonObject.addProperty("account", edt_Account.getText().toString());
//            jsonObject.addProperty("billtype", txvBillType.getText().toString());
//            jsonObject.addProperty("biller", txvBiller.getText().toString());

            if (!edt_Reffrence.getText().toString().equalsIgnoreCase("")) {

                RequestBillyear(edt_Reffrence.getText().toString());


            }
        }
    }

    private boolean checkPasswordError() {
        if (!edt_Reffrence.getText().toString().equalsIgnoreCase("")) {
            return true;
        } else {
            Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_LONG).show();
            return false;
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
            mBadgeUpdateListener.setHeaderTitle(getString(R.string.frg_my_bills));

        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setBottomBar();
        }


    }

    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());
    }

    private void RequestBillyear(String StrRef) {
        showProgInstallingDataDialog();
        More_WebHit_Get_Bills more_webHit_get_bills = new More_WebHit_Get_Bills();
        more_webHit_get_bills.getBills(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    if (More_WebHit_Get_Bills.responseObject != null &&
                            More_WebHit_Get_Bills.responseObject.getIescoBill() != null) {


                        DModelBillInfo dModelBillInfo = new DModelBillInfo();
                        dModelBillInfo.setName(More_WebHit_Get_Bills.responseObject.getIescoBill().getNAME());
                        dModelBillInfo.setBillType(More_WebHit_Get_Bills.responseObject.getIescoBill().getBillType());
                        dModelBillInfo.setAddress(More_WebHit_Get_Bills.responseObject.getIescoBill().getADDRESS());
                        dModelBillInfo.setCity(More_WebHit_Get_Bills.responseObject.getIescoBill().getCity());
                        dModelBillInfo.setReference(More_WebHit_Get_Bills.responseObject.getIescoBill().getReferenceNumber());
                        dModelBillInfo.setAfterDueDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getPAYABLEAFTERDUEDATE());
                        dModelBillInfo.setWithinDueDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getPAYABLEWITHINDUEDATE());
                        dModelBillInfo.setIusseDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getISSUEDATE());
                        dModelBillInfo.setBillMonth(More_WebHit_Get_Bills.responseObject.getIescoBill().getBILLMONTH());
                        dModelBillInfo.setDueDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getDUEDATE());
                        dModelBillInfo.setConsumerID(More_WebHit_Get_Bills.responseObject.getIescoBill().getCONSUMERID());
                        dModelBillInfo.setCurrentAmount(More_WebHit_Get_Bills.responseObject.getIescoBill().getPAYABLEWITHINDUEDATE());
                        dModelBillInfo.setMeterStatus(More_WebHit_Get_Bills.responseObject.getIescoBill().getSTATUS());
                        dModelBillInfo.setTariff(More_WebHit_Get_Bills.responseObject.getIescoBill().getTARIFF());
                        dModelBillInfo.setUnits(More_WebHit_Get_Bills.responseObject.getIescoBill().getTOTALUNITS());
                        dModelBillInfo.setPresentReading(More_WebHit_Get_Bills.responseObject.getIescoBill().getPRESENTREADING());
                        dModelBillInfo.setPrevReading(More_WebHit_Get_Bills.responseObject.getIescoBill().getPREVIOUSREADING());
                        dModelBillInfo.setReadingDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getREADINGDATE());
                        dModelBillInfo.setIusseDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getISSUEDATE());
                        dModelBillInfo.setDueDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getDUEDATE());

                        lstBillInfo.add(dModelBillInfo);
                       lstImpDates.add(More_WebHit_Get_Bills.responseObject.getIescoBill().getDUEDATE());
                      // lstImpDates.add(More_WebHit_Get_Bills.responseObject.getIescoBill().getISSUEDATE());
                      // lstImpDates.add(More_WebHit_Get_Bills.responseObject.getIescoBill().getREADINGDATE());
//                        DModel_Bills dModel_bills = new DModel_Bills();
//                        dModel_bills.setBiller(More_WebHit_Get_Bills.responseObject.getIescoBill().getBillType());
//                        dModel_bills.setAmount(More_WebHit_Get_Bills.responseObject.getIescoBill().getPAYABLEWITHINDUEDATE());
//                        dModel_bills.setDueDate(More_WebHit_Get_Bills.responseObject.getIescoBill().getDUEDATE());
//
//                        lstImp.add(dModel_bills);

                        lstMyBills.add(new DModel_Bills(str_billType + "",
                                (More_WebHit_Get_Bills.responseObject.getIescoBill().getReferenceNumber()) + "",
                                edt_Account.getText().toString() + "",
                                More_WebHit_Get_Bills.responseObject.getIescoBill().getPAYABLEWITHINDUEDATE(),
                                More_WebHit_Get_Bills.responseObject.getIescoBill().getDUEDATE(),
                                More_WebHit_Get_Bills.responseObject.getIescoBill().getBillType()));
                        billListingRcvAdapter.notifyDataSetChanged();



//                     ??   String impkey =  "impkey" + (More_WebHit_Get_Bills.responseObject.getIescoBill().getReferenceNumber()) ;
//                    ??    Log.d(TAG, "onWebResult: impKey " + impkey);
//
//                      ??  AppConfig.getInstance().saveimp(lstImp);
//                      ??  AppConfig.getInstance().saveimpString(lstImpDates,impkey);


                        String key = "key"+ (More_WebHit_Get_Bills.responseObject.getIescoBill().getReferenceNumber());
                        Log.d(TAG, "onWebResult: Key " + key);
                        AppConfig.getInstance().saveIESCO(key, lstBillInfo);


//                      ??  String impkey =  key + "impkey";



                        String impkey =   "impkey";
                        Log.d(TAG, "onWebResult: impKey " + impkey);

                        AppConfig.getInstance().saveimp(lstImp);
                        AppConfig.getInstance().saveimpString(lstImpDates,impkey);
                        Log.d(TAG, "onWebResult: impKey " + impkey + " " + AppConfig.getInstance().getimpString(impkey).size() );




                        if (More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().size() > 0) {
                            for (int i = 0; i < More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().size(); i++) {
                                if (i > 0) {
                                    AppConfig.getInstance().lastYear.add(More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i));
                                    DModel_Bill dModel_bill = new DModel_Bill
                                            (More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(0),
                                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(1),
                                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(2),
                                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(3));
                                    lastBill.add(dModel_bill);
                                }
                            }
                            String key1 = key+"years";
                            AppConfig.getInstance().saveBillsList(key1,lastBill);
                            AppConfig.getInstance().getBillsList(key1);
                            Log.d(TAG,"SIZE WITHOUT KEY: " +lastBill.size());
                            Log.d(TAG,"SIZE WITH KEY: " + AppConfig.getInstance().getBillsList(key1).size());
                            Log.d(TAG,"KEY: " + key1);
                        }
                    }
                    if (AppConfig.getInstance().getBillerSetting().size() > 0) {
                        AppConfig.getInstance().getBillerSetting().clear();

                    }
                    AppConfig.getInstance().saveBillerSetting(lstMyBills);
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();

                } else {
                    dismissProgDialog();
                    //  Toast.makeText(getContext(), "Reference Number Does Not Exist", Toast.LENGTH_LONG).show();
                    AppConfig.getInstance().showErrorMessage(getContext(), "Reference Number Does Not Exist");
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                //Toast.makeText(getContext(), "Exception :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                AppConfig.getInstance().showErrorMessage(getContext(), "Exception :" + ex.getMessage());
            }
        }, StrRef);
    }
    private void showProgInstallingDataDialog() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_installing_progress);

        progressDialog.setCancelable(false);
        progressDialog.show();
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
}

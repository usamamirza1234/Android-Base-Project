package com.ast.MyBills.MainAuxilaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
import com.ast.MyBills.MainAuxilaries.WebServices.More_WebHit_Get_Bills;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.ast.MyBills.Utils.IWebCallback;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;


public class ElectricityHomeFragment extends Fragment implements View.OnClickListener {

    private ArrayList<DModelBillInfo> lstBillInfo=AppConfig.getInstance().getBillsIESCO();
    private ArrayList lstData;
    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;
    LinearLayout llBillDetails,llImportantdates;
    ElectricityInfoRcvAdapter electricityInfoRcvAdapter;
    int position_ = 0;
    Integer selection = null;
    private Dialog progressDialog;

    TextView txv_billDetails_company,txv_billDetails_name;
    TextView txvpdf,txvhistory;
    TextView txv_billDetails_address;
    TextView txv_billDetails_tariff;
    TextView txv_billDetails_billing_month;
    TextView txv_billDetails_prev_reading;
    TextView txv_billDetails_pres_reading;
    TextView txv_billDetails_unit;
    TextView txv_billDetails_due_date;
    TextView txv_billDetails_current_amount;
    TextView txv_billDetails_reference;
    TextView txv_billDetails_MeterStatus;
EditText ref;
RelativeLayout add;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home_electricity, container, false);

        init();
        bindviews(frg);


      // populateBillInfo();

        if (selection == null) {
            selection=0;
           // setBillDetails();
//            llBillDetails.setVisibility(View.GONE);
        }
      //  RequestBills();

        if (AppConfig.getInstance().getBillsIESCO().size() > 0) {
            AppConfig.getInstance().getBillsIESCO().clear();
            RequestBillssavedIESCO();

        }else {


        }

        return frg;
    }


    private void init() {
        setBottomBar();
        lstBillInfo = new ArrayList<>();

        lstData = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle = getArguments();

        if (bundle!=null)
        {
            sref = bundle.getString("key_iesco");
        }
    }





    private void bindviews(View view) {
        ref = view.findViewById(R.id.frg_my_bills_edt_ref);
        add = view.findViewById(R.id.frg_my_bills_rlAdd);
        add.setOnClickListener(this);

        rcvElectInfo = view.findViewById(R.id.frg_home_electricity_rcvElectricityInfo);
        llBillDetails = view.findViewById(R.id.frg_home_electricity_llBill_Details);
        llImportantdates = view.findViewById(R.id.frg_home_electricity_llImportantdates);


        txv_billDetails_company = view.findViewById(R.id.frg_home_electricity_txv_bill_company);
        txv_billDetails_name = view.findViewById(R.id.frg_home_electricity_txv_bill_name);
        txv_billDetails_address = view.findViewById(R.id.frg_home_electricity_txv_bill_adderess);
        txv_billDetails_tariff = view.findViewById(R.id.frg_home_electricity_txv_bill_teriff);
        txv_billDetails_billing_month = view.findViewById(R.id.frg_home_electricity_txv_bill_billing_month);
        txv_billDetails_prev_reading = view.findViewById(R.id.frg_home_electricity_txv_bill_prev_reading);
        txv_billDetails_pres_reading = view.findViewById(R.id.frg_home_electricity_txv_bill_pres_reading);
        txv_billDetails_unit = view.findViewById(R.id.frg_home_electricity_txv_bill_units);
        txv_billDetails_due_date = view.findViewById(R.id.frg_home_electricity_txv_bill_due_date);
        txv_billDetails_current_amount = view.findViewById(R.id.frg_home_electricity_txv_bill_current_amount);
        txv_billDetails_reference= view.findViewById(R.id.frg_home_electricity_txv_bill_reference);
        txv_billDetails_MeterStatus= view.findViewById(R.id.frg_home_electricity_txv_bill_meter_status);
        txvpdf = view.findViewById(R.id.electricityhomepdf);
        txvhistory = view.findViewById(R.id.electricityhomehistory);

        txvpdf.setOnClickListener(this);
        txvhistory.setOnClickListener(this);
        llImportantdates.setOnClickListener(this);



    }


    String sref ="";
    //request year
    List<DModel_Bill> lastBill = new ArrayList<>();

    private void RequestBillyear(String _Entity,String StrRef) {


        showProgDialog();
        More_WebHit_Get_Bills more_webHit_get_bills = new More_WebHit_Get_Bills();


        more_webHit_get_bills.getBills(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    if (More_WebHit_Get_Bills.responseObject != null &&
                            More_WebHit_Get_Bills.responseObject.getIescoBill() != null)
                    {
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
                        dModelBillInfo.setUnits(More_WebHit_Get_Bills.responseObject.getIescoBill().getUNITS());
                        dModelBillInfo.setPresentReading(More_WebHit_Get_Bills.responseObject.getIescoBill().getPRESENTREADING());
                        dModelBillInfo.setPrevReading(More_WebHit_Get_Bills.responseObject.getIescoBill().getPREVIOUSREADING());


                        lstBillInfo.add(dModelBillInfo);
                        populateBillInfo(lstBillInfo);


                        AppConfig.getInstance().saveIESCO(lstBillInfo);



                        if ( More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().size()>0)
                        {
                            for (int i=0;i< More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().size();i++)
                            {
                                if (i>0)
                                {
                                    Log.d("LOG_AS","Elements of lastYearBills "+  More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i));
                                    AppConfig.getInstance().lastYear.add(More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i));

                                    DModel_Bill dModel_bill = new DModel_Bill(
                                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(0),
                                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(1),
                                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(2),
                                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(3)
                                    );
//                                    dModel_bill.setPAYMENT(More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(0).get);

                                    lastBill.add(dModel_bill);


                                }

                            }


                            for (int i=0;i<lastBill.size();i++)
                            {
                                Log.d("LOG_AS","Elements of lastBill "+  lastBill.get(i).getBILL());
                                Log.d("LOG_AS","Elements of lastBill "+  lastBill.get(i).getUNITS());
                                Log.d("LOG_AS","Elements of lastBill "+  lastBill.get(i).getMONTH());
                                Log.d("LOG_AS","Elements of lastBill "+  lastBill.get(i).getPAYMENT());

                            }
                        }

                    }
                    else {
                        dismissProgDialog();
                        Toast.makeText(getContext(), "Error :" + strMsg, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                Toast.makeText(getContext(), "Exception :" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        },_Entity,StrRef);
    }


    private void RequestBillssavedIESCO() {

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
          dModelBillInfo.setUnits(More_WebHit_Get_Bills.responseObject.getIescoBill().getUNITS());
          dModelBillInfo.setPresentReading(More_WebHit_Get_Bills.responseObject.getIescoBill().getPRESENTREADING());
          dModelBillInfo.setPrevReading(More_WebHit_Get_Bills.responseObject.getIescoBill().getPREVIOUSREADING());


          lstBillInfo.add(dModelBillInfo);
          populateBillInfo(lstBillInfo);
      }}
//end

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





    private void populateBillInfo( ArrayList<DModelBillInfo> lstBillInfo) {
//        lstBillInfo.clear();
//
//        lstBillInfo.add(new DModelBillInfo("ISECo", "PLOT 34 A FLAT 4,I-8/M IBD", "F9"));
//        lstBillInfo.add(new DModelBillInfo("WAPDA", "2,31" + 1, "F10"));
//        lstBillInfo.add(new DModelBillInfo("WASA", "2,31" + 2, "F11"));
//        lstBillInfo.add(new DModelBillInfo("LESCO", "2,31" + 2, "I18"));

        electricityInfoRcvAdapter = null;
        if (electricityInfoRcvAdapter == null) {

            electricityInfoRcvAdapter = new ElectricityInfoRcvAdapter(getActivity(), lstBillInfo, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:

                        position_ = position;
                        selection = position;
//                        txvSelected_Disease.setVisibility(View.VISIBLE);
//                        txvSelected_Disease.setText(AppConfig.getInstance().lst_DiseasesDef.get(position).getDiseaseName());

                        setBillDetails();

                        break;

                    case EVENT_B:
                        navToBillAnaylsisFragment(position);
                        break;
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvElectInfo.setLayoutManager(linearLayoutManager);
            rcvElectInfo.setAdapter(electricityInfoRcvAdapter);

        } else {
            electricityInfoRcvAdapter.notifyDataSetChanged();
        }

    }

    private void setBillDetails() {
        if (selection == null)
            llBillDetails.setVisibility(View.GONE);
        else llBillDetails.setVisibility(View.VISIBLE);


        txv_billDetails_company.setText(lstBillInfo.get(selection).getBillType());
        txv_billDetails_name.setText(lstBillInfo.get(selection).getName());
        txv_billDetails_address.setText(lstBillInfo.get(selection).getAddress());
      txv_billDetails_reference.setText(lstBillInfo.get(selection).getReference());


        txv_billDetails_billing_month.setText(lstBillInfo.get(selection).getBillMonth());
        txv_billDetails_due_date.setText(lstBillInfo.get(selection).getDueDate());
        txv_billDetails_tariff.setText(lstBillInfo.get(selection).getTariff());
        txv_billDetails_unit.setText(lstBillInfo.get(selection).getUnits());
        txv_billDetails_current_amount.setText(lstBillInfo.get(selection).getCurrentAmount());
        txv_billDetails_MeterStatus.setText(lstBillInfo.get(selection).getMeterStatus());
        txv_billDetails_pres_reading.setText(lstBillInfo.get(selection).getPresentReading());
        txv_billDetails_prev_reading.setText(lstBillInfo.get(selection).getPrevReading());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.electricityhomepdf:
            navToPDFFragment(selection);
            break;

            case R.id.electricityhomehistory:
             navToBillAnaylsisFragment(selection);
                break;

            case R.id.frg_home_electricity_llImportantdates:
                navToImportantDatesFragment();
                break;

            case R.id.frg_my_bills_rlAdd:
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("reference", ref.getText().toString());
                RequestBillyear(jsonObject.toString(),sref);
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
            mBadgeUpdateListener.setHeaderTitle("");

        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setBottomBar();
        }
    }

//    private void navToHistoryFragment() {
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft;
//        Fragment frg = new HistoryFragment();
//        ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_HistoryFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_HistoryFragment);
//        ft.hide(this);
//        ft.commit();
//    }
//
private void navToImportantDatesFragment() {

    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft;
    Fragment frg = new ImportantDatesFragment();
    ft = fm.beginTransaction();
    ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_HistoryFragment);
    ft.addToBackStack(AppConstt.FragTag.FN_HistoryFragment);
    ft.hide(this);
    ft.commit();
}

    private void navToBillAnaylsisFragment(int selection) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new BillAnaylsisFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection" , selection);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_BillAnaylsisFragment);
        Log.d("selection", "selectedPosition navToBillAnaylsisFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_BillAnaylsisFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }

    private void navToPDFFragment(int selection) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new PdfFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection" , selection);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_PdfFragment);
        Log.d("selection", "selectedPosition navToPDFFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_PdfFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }
}

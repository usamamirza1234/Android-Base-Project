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
import com.ast.MyBills.IntroAuxilaries.WebServices.More_WebHit_Get_Bills;
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
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
    private final String TAG = "MYBILL";

    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;
    LinearLayout llBillDetails, llImportantdates;
    ElectricityInfoRcvAdapter electricityInfoRcvAdapter;
    int position_ = 0;
    String arrayKey = "000";
    String arrayKey1 = "";
    Integer selection = 0;
    TextView txv_billDetails_company, txv_billDetails_name;
    TextView txvpdf, txvhistory;
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
    String sref = "";
    //request year
    List<DModel_Bill> lastBill = new ArrayList<>();
    private ArrayList<DModelBillInfo> lstBillInfo;
    private ArrayList lstData;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home_electricity, container, false);

        init();
        bindviews(frg);
        populateBillInfo(lstBillInfo);
      //  setBillDetails();
        return frg;
    }

    private void init() {
        setBottomBar();
        Bundle bundle = new Bundle();
        bundle = getArguments();
        if (bundle != null) {
            sref = bundle.getString("key_iesco");
            arrayKey = bundle.getString("key_fordata");
            selection = bundle.getInt("key_selection");
       //     arrayKey1 = bundle.getString("key_fordata1");
        }
        lstBillInfo = new ArrayList<>();
        lstData = new ArrayList<>();
        lstBillInfo = AppConfig.getInstance().getBillsIESCO(arrayKey);
        Log.d("MYBILL", "onWebResult: Key " +arrayKey + " "  +  AppConfig.getInstance().getBillsIESCO(arrayKey).size());
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
        txv_billDetails_reference = view.findViewById(R.id.frg_home_electricity_txv_bill_reference);
        txv_billDetails_MeterStatus = view.findViewById(R.id.frg_home_electricity_txv_bill_meter_status);
        txvpdf = view.findViewById(R.id.electricityhomepdf);
        txvhistory = view.findViewById(R.id.electricityhomehistory);

        txvpdf.setOnClickListener(this);
        txvhistory.setOnClickListener(this);
        llImportantdates.setOnClickListener(this);


    }

    private void populateBillInfo(ArrayList<DModelBillInfo> lstBillInfo) {


        electricityInfoRcvAdapter = null;
        if (electricityInfoRcvAdapter == null) {

            electricityInfoRcvAdapter = new ElectricityInfoRcvAdapter(getActivity(), lstBillInfo, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:

                        position_ = position;
                        selection = position;

                        setBillDetails();
                        break;

                    case EVENT_B:
                        String key1= "key1  "+ (lstBillInfo.get(position).getReference());
                        navToBillAnaylsisFragment(position,arrayKey,key1);

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
        if (selection == null) {
            llBillDetails.setVisibility(View.GONE);
        }
        else {
                 llBillDetails.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.electricityhomepdf:
                navToPDFFragment(selection);
                break;

         case R.id.electricityhomehistory:
                navToBillAnaylsisFragment(selection,arrayKey,arrayKey1);
              break;

            case R.id.frg_home_electricity_llImportantdates:
                navToImportantDatesFragment();
                break;

            case R.id.frg_my_bills_rlAdd:

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

    private void navToBillAnaylsisFragment(int selection,String arrayKey,String arrayKey1) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new BillAnaylsisFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection", selection);
        bundle.putString("key_fordata", arrayKey);
        bundle.putString("key_fordata1", arrayKey1);
;

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
        bundle.putInt("key_selection", selection);
        bundle.putString("key_fordata", arrayKey);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_PdfFragment);
        Log.d("selection", "selectedPosition navToPDFFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_PdfFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }
}

package com.ast.MyBills.MainAuxilaries;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.ast.MyBills.MainActivity;
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bills;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_EditProfile;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.ast.MyBills.Utils.IWebCallback;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    String billType = "";
    int positionSelected = 0;
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
    TextView BilltypeHeading;
    EditText ref,amount,date;
    RelativeLayout add;
    String sref = "";
    RelativeLayout rlUpdatePayment;
    //request year
    List<DModel_Bill> lastBill = new ArrayList<>();
    private ArrayList<DModelBillInfo> lstBillInfo;
    private ArrayList lstData;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home_electricity, container, false);

        init();
        bindviews(frg);

        BilltypeHeading.setText(billType+"");

        populateBillInfo(lstBillInfo);
        // setBillDetails();
        return frg;
    }

    private void init() {
        setBottomBar();
        Bundle bundle = new Bundle();
        bundle = getArguments();
        if (bundle != null) {
            sref = bundle.getString("key_iesco");
            arrayKey = bundle.getString("key_fordata");
            billType = bundle.getString("key_billType");
            selection = bundle.getInt("key_selection");
            positionSelected = bundle.getInt("key_selection");
            arrayKey1 = bundle.getString("key_fordata1");
        }
        lstBillInfo = new ArrayList<>();
        lstData = new ArrayList<>();
        lstBillInfo = AppConfig.getInstance().getBillsIESCO(arrayKey);
        Log.d("MYBILL", "onWebResult: Key " +arrayKey + " "  +  AppConfig.getInstance().getBillsIESCO(arrayKey).size());
    }

    private void bindviews(View view) {
        BilltypeHeading = view.findViewById(R.id.BilltypeHeading);
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
        rlUpdatePayment = view.findViewById(R.id.frg_updatePayment);
        txvhistory = view.findViewById(R.id.electricityhomehistory);

        amount = view.findViewById(R.id.frg_update_payment_edt_Amount);
        date = view.findViewById(R.id.frg_update_payment_edt_Date);


        rlUpdatePayment.setOnClickListener(this);
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
                        navToBillAnaylsisFragment(position,arrayKey);

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
                navToBillAnaylsisFragment(selection,arrayKey);
              break;

            case R.id.frg_home_electricity_llImportantdates:
                navToImportantDatesFragment(arrayKey);
                break;

            case R.id.frg_updatePayment:
                showProgPopUpDialog(progressDialog);
           // Toast.makeText(getContext(), "Exception :" + "aa", Toast.LENGTH_LONG).show();
                break;

        }
    }

    private void showProgPopUpDialog(Dialog frg) {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_lst_ey_dpt);
        LinearLayout llParentBG;
        TextView titleDesc;
        llParentBG = progressDialog.findViewById(R.id.llParentBG);
      //  llParentBG.setBackground(getActivity().getResources().getDrawable(R.color.white));



        progressDialog.setCancelable(true);
        progressDialog.show();
        searchRelventDept( progressDialog);
  }

    private void searchRelventDept( Dialog frg) {




        RelativeLayout close, rlupdatePayment;

        close = frg.findViewById(R.id.close);
        rlupdatePayment = frg.findViewById(R.id.frg_update_payment_edt_UpdatePayment);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);




        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissProgDialog();
            }
        });

        rlupdatePayment.setOnClickListener(v -> {

            AppConfig.getInstance().lstBillDashboardElement.get(selection).setPayableafterduedate("100");

            String currentDate = new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(new Date());
            AppConfig.getInstance().lstBillDashboardElement.get(selection).duedate=(currentDate);
            AppConfig.getInstance().saveBillerSetting(AppConfig.getInstance().lstBillDashboardElement);
            closeKeyboard();
            Toast.makeText(getContext(), "Added :" + AppConfig.getInstance().lstBillDashboardElement.size(), Toast.LENGTH_LONG).show();
            dismissProgDialog();
            ((MainActivity)getActivity()).navToHomeFragment();
            //Uncomment solve this error by your own and comment line 261
//                checkErrorConditions();


        });
    }

    private void checkErrorConditions() {
        if (checkPasswordError()) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Name", amount.getText().toString());
            jsonObject.addProperty("Email", date.getText().toString());

            AppConfig.getInstance().lstBillDashboardElement.get(selection).setPaid(true);
            AppConfig.getInstance().lstBillDashboardElement.get(selection).setAmount("100");
            AppConfig.getInstance().lstBillDashboardElement.get(selection).setPayableafterduedate("100");

            String currentDate = new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(new Date());
//            SimpleDateFormat format = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
//            try {
//                Date dateFromCurrentDateString = format.parse(currentDate);
//                AppConfig.getInstance().lstBillDashboardElement.get(selection).setDueDate(dateFromCurrentDateString+"");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

//

            AppConfig.getInstance().saveBillerSetting(AppConfig.getInstance().lstBillDashboardElement);
            closeKeyboard();
            Toast.makeText(getContext(), "Added :" + AppConfig.getInstance().lstBillDashboardElement.size(), Toast.LENGTH_LONG).show();
            dismissProgDialog();
            ((MainActivity)getActivity()).navToHomeFragment();



        }
    }

    private boolean checkPasswordError() {
        if (!amount.getText().toString().equalsIgnoreCase("") && !date.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_LONG).show();
            return false;
        }

    }


    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
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

    private void navToImportantDatesFragment(String arrayKey) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new ImportantDatesFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();

        bundle.putString("key_fordata", arrayKey);
        frg.setArguments(bundle);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ImportantDatesFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ImportantDatesFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToBillAnaylsisFragment(int selection,String arrayKey) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new BillAnaylsisFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection", selection);
        bundle.putString("key_fordata", arrayKey);
        bundle.putString("key_billType", billType);
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
        bundle.putString("key_billType", billType);
        bundle.putString("key_fordata", arrayKey);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_PdfFragment);
        Log.d("selection", "selectedPosition navToPDFFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_PdfFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }
}

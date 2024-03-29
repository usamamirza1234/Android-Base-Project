package com.ast.MyBills.MainAuxilaries;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.MainAuxilaries.Adapters.BillAnaylsisRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.BillanalysisInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.ChartManagers.BarChartManager;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;
import static com.google.firebase.messaging.Constants.TAG;


public class BillAnaylsisFragment extends Fragment implements View.OnClickListener {
    BarChart mBarHistoryUnit;
    TextView txv_billinfo, txvPdf;
    ArrayList<DModel_Bill> lstBillAnaylsis;
    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;
    RecyclerView rcvBillAnaylsis;
    LinearLayout llBillDetails, llBillAnalysisImportantDates;
    BillanalysisInfoRcvAdapter billanalysisInfoRcvAdapter;
    BillAnaylsisRcvAdapter billAnaylsisRcvAdapter;
    int position_ = 0;
    Integer selection = null;
    TextView txv_billDetails_company;
    String sref = "";
    String billType = "";
    private final boolean isFirstTime = true;
    private ArrayList<DModelBillInfo> lstBillInfo;
    private ArrayList<String> xLabel;
    private Dialog progressDialog;
    private String arrayKey="000";
    TextView BilltypeHeading;
    String str;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_bill_anaylsis, container, false);

        init();
        bindviews(frg);
        BilltypeHeading.setText(billType+"");
        populateBillInfo(  lstBillInfo);
        showBarHistoryUnit(lstBillAnaylsis);
        populateBillAnaylsis(lstBillAnaylsis);
        setBillDetails();
        return frg;
    }

    private void init() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            billType = bundle.getString("key_billType");
            selection = bundle.getInt("key_selection");
            sref = bundle.getString("key_iesco");
            arrayKey = bundle.getString("key_fordata");

        }
        setBottomBar();
        lstBillInfo = new ArrayList<>();
        lstBillAnaylsis = new ArrayList<>();
        lstBillInfo = AppConfig.getInstance().getBillsIESCO(arrayKey);

        lstBillAnaylsis = AppConfig.getInstance().getBillsList(arrayKey+"years");
        Log.d("MYBILL", "onWebResult: KEYYEAR " +arrayKey+"years" + " "  +  lstBillAnaylsis.size());

        for (int i =0; i<lstBillAnaylsis.size(); i++)
        {
            Log.d("MYBILL",  " "  +  lstBillAnaylsis.get(i).getMONTH());

        }

    }

    private void bindviews(View view) {
        BilltypeHeading = view.findViewById(R.id.analysisHeading);
        rcvElectInfo = view.findViewById(R.id.frg_bill_anaylsis_rcvElectricityInfo);
        rcvBillAnaylsis = view.findViewById(R.id.frg_bill_anaylsis_rcvMonthsAnaylsis);
        llBillDetails = view.findViewById(R.id.frg_bill_anaylsis_llBill_Details);
        llBillAnalysisImportantDates = view.findViewById(R.id.frg_home_billanalysis_llimportantdates);
        txv_billDetails_company = view.findViewById(R.id.frg_bill_anaylsis_txv_bill_company);
        txv_billinfo = view.findViewById(R.id.frg_bill_anaylsis_llBill_billinfo);
        txv_billinfo.setOnClickListener(this);
        txvPdf = view.findViewById(R.id.billanalysispdf);
        txvPdf.setOnClickListener(this);
        llBillAnalysisImportantDates.setOnClickListener(this);
        mBarHistoryUnit = view.findViewById(R.id.BarHistoryUnit);
    }


    private void showBarHistoryUnit(ArrayList<DModel_Bill> lstUnitHistory) {
        List<String> xAxisValues = new ArrayList<>();
        ArrayList<BarEntry> yValueGroup1 = new ArrayList<>();
        for (int i=0;i< lstUnitHistory.size();i++)
        {
            xAxisValues.add(lstUnitHistory.get(i).getMONTH());
            str = (lstUnitHistory.get(i).getPAYMENT().replace(",", ""));
            yValueGroup1.add(new BarEntry((i+1), Float.parseFloat(str)));
        }
        BarChartManager barChartManager = new BarChartManager(mBarHistoryUnit, getContext());
        barChartManager.showBarChartVertical(yValueGroup1, xAxisValues);
    }

    private void populateBillInfo(ArrayList<DModelBillInfo> lstBillInfo) {
        if (billanalysisInfoRcvAdapter == null) {
            billanalysisInfoRcvAdapter = new BillanalysisInfoRcvAdapter(getActivity(), lstBillInfo, selection, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:
                        position_ = position;
                        selection = position;
                       // setBillDetails();


                        break;
                    case EVENT_B:
                        navToPDFFragment(selection,arrayKey);
                        break;
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvElectInfo.setLayoutManager(linearLayoutManager);
            rcvElectInfo.setAdapter(billanalysisInfoRcvAdapter);
        } else {
            billanalysisInfoRcvAdapter.notifyDataSetChanged();
        }
    }

    public void populateBillAnaylsis(ArrayList<DModel_Bill> lstBillAnaylsis) {
        if (billAnaylsisRcvAdapter == null) {
            billAnaylsisRcvAdapter = new BillAnaylsisRcvAdapter(getActivity(), lstBillAnaylsis, (eventId, position) -> { });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvBillAnaylsis.setLayoutManager(linearLayoutManager);
            rcvBillAnaylsis.setAdapter(billAnaylsisRcvAdapter);
        } else {
            billAnaylsisRcvAdapter.notifyDataSetChanged();
        }
    }
    private void setBillDetails() {
        if (selection == null)
            llBillDetails.setVisibility(View.GONE);
        else {
            llBillDetails.setVisibility(View.VISIBLE);
            txv_billDetails_company.setText(lstBillInfo.get(selection).getBillType());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_bill_anaylsis_llBill_billinfo:
                navToElectricityHomeFragment();
                break;
            case R.id.billanalysispdf:
                navToPDFFragment(selection,arrayKey);
                break;
            case R.id.frg_home_billanalysis_llimportantdates:
                navToImportantDatesFragment();
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
//            mBadgeUpdateListener.setHeaderTitle(getString(R.string.BillAnaylsisFragment));
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

    private void navToElectricityHomeFragment() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new ElectricityHomeFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection" , selection);
        bundle.putString("key_fordata", arrayKey);
        bundle.putString("key_billType", billType);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ElectricityHomeFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ElectricityHomeFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }

    private void navToPDFFragment(int selection,String arrayKey) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new PdfFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection", selection);
        bundle.putString("key_fordata", arrayKey);
        bundle.putString("key_billType", billType);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_PdfFragment);
        Log.d("selection", "selectedPosition navToPDFFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_PdfFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }

    private class barChartOnChartValueSelectedListener implements OnChartValueSelectedListener {

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            //trigger activity when the bar value is selected

        }

        @Override
        public void onNothingSelected() {

        }
    }
}

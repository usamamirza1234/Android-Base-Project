package com.ast.MyBills.MainAuxilaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
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
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.MainAuxilaries.WebServices.More_WebHit_Get_Bills;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.ChartManagers.BarChartManager;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.ast.MyBills.Utils.IWebCallback;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;


public class BillAnaylsisFragment extends Fragment implements View.OnClickListener {
    private boolean isFirstTime = true;

    BarChart mBarHistoryUnit;
   TextView txv_billinfo,txvPdf;
    private ArrayList<DModelBillInfo> lstBillInfo;
     ArrayList<DModel_Bill> lstBillAnaylsis= AppConfig.getInstance().getBillsList();
    private ArrayList<String> xLabel;
    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;
    RecyclerView rcvBillAnaylsis;
    LinearLayout llBillDetails,llBillAnalysisImportantDates;
    private Dialog progressDialog;
    BillanalysisInfoRcvAdapter billanalysisInfoRcvAdapter;
    BillAnaylsisRcvAdapter billAnaylsisRcvAdapter;

    int position_ = 0;
    Integer selection = null;


    TextView txv_billDetails_company;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_bill_anaylsis, container, false);

        init();
        bindviews(frg);

        populateBillInfo();


        if (selection == null) {
            selection = 0;
//            llBillDetails.setVisibility(View.GONE);
        } else
            //setBillDetails();

        if (AppConfig.getInstance().getBillsList().size() > 0) {
            AppConfig.getInstance().getBillsList().clear();
            //RequestBillyearSaved();

        }else {
          //  RequestBillyear(sref);
        }


        showBarHistoryUnit();
        return frg;
    }




    private void init() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selection = bundle.getInt("key_selection");
            Log.d("selection", "selectedPosition init " + selection);
        }
        setBottomBar();


        bundle = getArguments();

        if (bundle!=null)
        {
            sref = bundle.getString("key_iesco");
        }


        lstBillInfo = new ArrayList<>();
        lstBillAnaylsis = new ArrayList<>();

    }

    private void bindviews(View view) {


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

    private void  showBarHistoryUnit(){


        List<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("Jan");
        xAxisValues.add("Feb");
        xAxisValues.add("March");
        xAxisValues.add("Apr");
        xAxisValues.add("May");
        xAxisValues.add("Jun");
        xAxisValues.add("Jul");
        xAxisValues.add("Aug");
        xAxisValues.add("Sep");
        xAxisValues.add("Oct");
        xAxisValues.add("Nov");
        xAxisValues.add("Dec");


        ArrayList<BarEntry> yValueGroup1 = new ArrayList<>();

        yValueGroup1.add(new BarEntry(1f, 500f));
        yValueGroup1.add(new BarEntry(2f, 200f));
        yValueGroup1.add(new BarEntry(3f, 300f));
        yValueGroup1.add(new BarEntry(4f, 400f));
        yValueGroup1.add(new BarEntry(5f, 700f));
        yValueGroup1.add(new BarEntry(6f, 214f));
        yValueGroup1.add(new BarEntry(7f, 900f));
        yValueGroup1.add(new BarEntry(8f, 1000f));
        yValueGroup1.add(new BarEntry(9f, 1100f));
        yValueGroup1.add(new BarEntry(10f, 1400f));
        yValueGroup1.add(new BarEntry(11f, 1700f));
        yValueGroup1.add(new BarEntry(12f, 1900f));

        BarChartManager barChartManager = new BarChartManager(mBarHistoryUnit, getContext());
        barChartManager.showBarChartVertical(yValueGroup1, xAxisValues);

    }

    String sref ="";
    private void RequestBillyearSaved() {

        if (More_WebHit_Get_Bills.responseObject != null &&
                More_WebHit_Get_Bills.responseObject.getIescoBill() != null) {
            DModelBillInfo dModelBillInfo = new DModelBillInfo();
            dModelBillInfo.setBillType(More_WebHit_Get_Bills.responseObject.getIescoBill().getBillType());
            dModelBillInfo.setAddress(More_WebHit_Get_Bills.responseObject.getIescoBill().getADDRESS());
            dModelBillInfo.setCity(More_WebHit_Get_Bills.responseObject.getIescoBill().getCity());

            lstBillInfo.add(dModelBillInfo);
            populateBillInfo();
        }
        if (More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().size() > 0) {
            for (int i = 0; i < More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().size(); i++) {
                if (i > 0) {
                    Log.d("LOG_AS", "Elements of lastYearBills " + More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i));
                    AppConfig.getInstance().lastYear.add(More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i));

                    DModel_Bill dModel_bill = new DModel_Bill(
                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(0),
                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(1),
                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(2),
                            More_WebHit_Get_Bills.responseObject.getIescoBill().getLastYearBills().get(i).get(3)
                    );
//
                    lstBillAnaylsis.add(dModel_bill);
                    populateBillAnaylsis(lstBillAnaylsis);
                }
            }
        }
    }

    private void RequestBillyear(String _signUpEntity,String StrRef) {
        showProgDialog();
        More_WebHit_Get_Bills more_webHit_get_bills = new More_WebHit_Get_Bills();
        more_webHit_get_bills.getBills(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    if (More_WebHit_Get_Bills.responseObject != null &&
                            More_WebHit_Get_Bills.responseObject.getIescoBill() != null)
                    {DModelBillInfo dModelBillInfo = new DModelBillInfo();
                        dModelBillInfo.setBillType(More_WebHit_Get_Bills.responseObject.getIescoBill().getBillType());
                        dModelBillInfo.setAddress(More_WebHit_Get_Bills.responseObject.getIescoBill().getADDRESS());
                        dModelBillInfo.setCity(More_WebHit_Get_Bills.responseObject.getIescoBill().getCity());

                        lstBillInfo.add(dModelBillInfo);
                        populateBillInfo();
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
//
                                    lstBillAnaylsis.add(dModel_bill);
                                    populateBillAnaylsis(lstBillAnaylsis);



                                    AppConfig.getInstance().saveBillsList(lstBillAnaylsis);



                                }

                            }


                            for (int i=0;i<lstBillAnaylsis.size();i++)
                            {
                                Log.d("LOG_AS","Elements of lastBill "+  lstBillAnaylsis.get(i).getBILL());
                                Log.d("LOG_AS","Elements of lastBill "+  lstBillAnaylsis.get(i).getUNITS());
                                Log.d("LOG_AS","Elements of lastBill "+  lstBillAnaylsis.get(i).getMONTH());
                                Log.d("LOG_AS","Elements of lastBill "+  lstBillAnaylsis.get(i).getPAYMENT());

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
        },_signUpEntity,StrRef);
    }


    private void populateBillInfo() {
//        lstBillInfo.clear();
//
//        lstBillInfo.add(new DModelBillInfo("ISECo", "23" + 0, "F9"));
//        lstBillInfo.add(new DModelBillInfo("WAPDA", "28" + 1, "F10"));
//        lstBillInfo.add(new DModelBillInfo("WASA", "20" + 2, "F11"));
//        lstBillInfo.add(new DModelBillInfo("LESCO", "29" + 2, "I18"));


        if (billanalysisInfoRcvAdapter == null) {

            billanalysisInfoRcvAdapter = new BillanalysisInfoRcvAdapter(getActivity(), lstBillInfo, selection, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:

                        position_ = position;
                        selection = position;
//                        txvSelected_Disease.setVisibility(View.VISIBLE);
//                        txvSelected_Disease.setText(AppConfig.getInstance().lst_DiseasesDef.get(position).getDiseaseName());

                        setBillDetails();

                        break;

                    case EVENT_B:
                       navToPDFFragment(selection);
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


    public void populateBillAnaylsis(  ArrayList<DModel_Bill> lstBillAnaylsis) {
      // lstBillAnaylsis.clear();


       // lstBillAnaylsis.add(new DModel_Bill("500", "Jan", "220","1111"));
//        lstBillAnaylsis.add(new DModel_Bill("200", "Feb", "320"));
//        lstBillAnaylsis.add(new DModel_Bill("300", "Mar", "420"));
//        lstBillAnaylsis.add(new DModel_Bill("100", "Apr", "20"));
//        lstBillAnaylsis.add(new DModel_Bill("100", "May", "220"));


        if (billAnaylsisRcvAdapter == null) {

            billAnaylsisRcvAdapter = new BillAnaylsisRcvAdapter(getActivity(), lstBillAnaylsis, (eventId, position) -> {

            });

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
        else llBillDetails.setVisibility(View.VISIBLE);

        txv_billDetails_company.setText(lstBillInfo.get(selection).getBillType());

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_bill_anaylsis_llBill_billinfo:
                navToElectricityHomeFragment();
                break;

            case R.id.billanalysispdf:
                navToPDFFragment(selection);
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
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ElectricityHomeFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ElectricityHomeFragment);
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

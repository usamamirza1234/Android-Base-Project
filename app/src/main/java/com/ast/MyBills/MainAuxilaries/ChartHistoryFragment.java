package com.ast.MyBills.MainAuxilaries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.MainAuxilaries.Adapters.BillAnaylsisRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.ChartHistoryRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.PDFRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillAnaylsis;
import com.ast.MyBills.MainAuxilaries.DModels.DModelChartHistory;
import com.ast.MyBills.MainAuxilaries.DModels.DModelChartHistory;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.ChartManagers.BarChartManager;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.shockwave.pdfium.PdfDocument;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;

public class ChartHistoryFragment extends Fragment implements View.OnClickListener {


    private ArrayList<DModelChartHistory> lstChartHistory;
    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;

    TextView History,billinfo,pdf;
    ChartHistoryRcvAdapter chartHistoryRcvAdapter;
    int position_ = 0;
    Integer selection = null;
    TextView txv_billDetails_company;

    BarChart mBarHistoricalPayment,mBarHistoricalUnitsConsumed;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_chart_history, container, false);

        init();
        bindviews(frg);


        populateBillInfo();

        if (selection == null) {
            selection=0;

//            llBillDetails.setVisibility(View.GONE);
        }


        showBarHistoricalPayment();
        showBarHistoricalUnitsConsumed();

        return frg;
    }



    private void init() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selection = bundle.getInt("key_selection");
            Log.d("selection", "selectedPosition init " + selection);
        }
        setBottomBar();


        lstChartHistory = new ArrayList<>();
    }

    private void bindviews(View view) {



        rcvElectInfo = view.findViewById(R.id.frg_home_electricity_rcvElectricityInfo);

        pdf = view.findViewById(R.id.charthistorypdf);
        History = view.findViewById(R.id.electricityhomehistory);
        billinfo = view.findViewById(R.id.electricityhomebillinfo);
        txv_billDetails_company = view.findViewById(R.id.frg_home_electricity_txv_bill_company);

        mBarHistoricalPayment = view.findViewById(R.id.BarHistoricalPayment);
        mBarHistoricalUnitsConsumed = view.findViewById(R.id.BarHistoricalUnitsConsumed);
        pdf.setOnClickListener(this);
        History.setOnClickListener(this);
        billinfo.setOnClickListener(this);

    }

    private void  showBarHistoricalPayment(){


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

        yValueGroup1.add(new BarEntry(1f, 8540));
        yValueGroup1.add(new BarEntry(2f, 10569f));
        yValueGroup1.add(new BarEntry(3f, 4149f));
        yValueGroup1.add(new BarEntry(4f, 8073f));
        yValueGroup1.add(new BarEntry(5f, 10598f));
        yValueGroup1.add(new BarEntry(6f, 13552f));
        yValueGroup1.add(new BarEntry(7f, 39091f));
        yValueGroup1.add(new BarEntry(8f, 58358f));
        yValueGroup1.add(new BarEntry(9f, 25000f));
        yValueGroup1.add(new BarEntry(10f, 41362f));
        yValueGroup1.add(new BarEntry(11f, 65665f));
        yValueGroup1.add(new BarEntry(12f, 63000f));

        BarChartManager barChartManager = new BarChartManager(mBarHistoricalPayment, getContext());
        barChartManager.showBarChartVertical(yValueGroup1, xAxisValues);

    }

    private void  showBarHistoricalUnitsConsumed(){


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















        yValueGroup1.add(new BarEntry(1f, 1300));
        yValueGroup1.add(new BarEntry(2f, 1800));
        yValueGroup1.add(new BarEntry(3f, 1250));
        yValueGroup1.add(new BarEntry(4f, 2500));
        yValueGroup1.add(new BarEntry(5f, 3000));
        yValueGroup1.add(new BarEntry(6f, 4500));
        yValueGroup1.add(new BarEntry(7f, 4600));
        yValueGroup1.add(new BarEntry(8f, 3500));
        yValueGroup1.add(new BarEntry(9f, 2000));
        yValueGroup1.add(new BarEntry(10f, 1800));
        yValueGroup1.add(new BarEntry(11f, 1700));
        yValueGroup1.add(new BarEntry(12f, 1200));

        BarChartManager barChartManager = new BarChartManager(mBarHistoricalUnitsConsumed, getContext());
        barChartManager.showBarChartVertical(yValueGroup1, xAxisValues);

    }




    private void populateBillInfo() {
        lstChartHistory.clear();

        lstChartHistory.add(new DModelChartHistory("ISECo", "23100" +0, "F9"));
        lstChartHistory.add(new DModelChartHistory("WAPDA", "23100" + 1, "F10"));
        lstChartHistory.add(new DModelChartHistory("WASA", "23100" + 2, "F11"));
        lstChartHistory.add(new DModelChartHistory("LESCO", "23100" + 2, "I18"));


        if (chartHistoryRcvAdapter == null) {

            chartHistoryRcvAdapter = new ChartHistoryRcvAdapter(getActivity(), lstChartHistory, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:

                        position_ = position;
                        selection = position;
//                        txvSelected_Disease.setVisibility(View.VISIBLE);
//                        txvSelected_Disease.setText(AppConfig.getInstance().lst_DiseasesDef.get(position).getDiseaseName());



                        break;

                    case EVENT_B:

                        break;
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvElectInfo.setLayoutManager(linearLayoutManager);
            rcvElectInfo.setAdapter(chartHistoryRcvAdapter);

        } else {
            chartHistoryRcvAdapter.notifyDataSetChanged();
        }

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.electricityhomebillinfo:
                navToElectricityHomeFragment();
                break;

            case R.id.electricityhomehistory:
                navToBillAnaylsisFragment(selection);
                break;

            case R.id.charthistorypdf:
                navToPDFFragment(selection);
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
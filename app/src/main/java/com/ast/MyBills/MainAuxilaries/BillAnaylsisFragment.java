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
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillAnaylsis;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IBadgeUpdateListener;


import java.util.ArrayList;

import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;


public class BillAnaylsisFragment extends Fragment implements View.OnClickListener {
    private boolean isFirstTime = true;

   // BarChart chart;
   TextView txv_billinfo;
    private ArrayList<DModelBillInfo> lstBillInfo;
    private ArrayList<DModelBillAnaylsis> lstBillAnaylsis;
    private ArrayList<String> xLabel;
    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;
    RecyclerView rcvBillAnaylsis;
    LinearLayout llBillDetails;

    ElectricityInfoRcvAdapter electricityInfoRcvAdapter;
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
        populateBillAnaylsis();

//        if (selection == null) {
//            selection = 0;
////            llBillDetails.setVisibility(View.GONE);
//        } else setBillDetails();
////        showBarChart();
        return frg;
    }


    private void init() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selection = bundle.getInt("key_selection");
            Log.d("selection", "selectedPosition init " + selection);
        }
        setBottomBar();


        lstBillInfo = new ArrayList<>();
        lstBillAnaylsis = new ArrayList<>();

        xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        xLabel.add("Jul");
        xLabel.add("Aug");
        xLabel.add("Sep");
        xLabel.add("Oct");
        xLabel.add("Nov");
        xLabel.add("Dec");

    }

    private void bindviews(View view) {


        rcvElectInfo = view.findViewById(R.id.frg_bill_anaylsis_rcvElectricityInfo);
        rcvBillAnaylsis = view.findViewById(R.id.frg_bill_anaylsis_rcvMonthsAnaylsis);
        llBillDetails = view.findViewById(R.id.frg_bill_anaylsis_llBill_Details);


        txv_billDetails_company = view.findViewById(R.id.frg_bill_anaylsis_txv_bill_company);
        txv_billinfo = view.findViewById(R.id.frg_bill_anaylsis_llBill_billinfo);
        txv_billinfo.setOnClickListener(this);


        //chart = view.findViewById(R.id.piechart);
        //initBarDataSet();

    }
//
//    private void showBarChart() {
//        ArrayList<Double> valueList = new ArrayList<Double>();
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        String title = "Bill";
//
//        //input data
////        for (int i = 0; i < 6; i++) {
////            valueList.add((i+1) * 200.1);
////        }
//
//
//        valueList.add(220.0);
//        valueList.add(320.0);
//        valueList.add(420.0);
//        valueList.add(20.0);
//        valueList.add(220.0);
////        valueList.add(520.0);
////        valueList.add(620.0);
////        valueList.add(650.0);
////        valueList.add(620.0);
//
//
//        //fit the data into a bar
//        for (int i = 0; i < valueList.size(); i++) {
//            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
//            entries.add(barEntry);
//        }
//
//        BarDataSet barDataSet = new BarDataSet(entries, title);
//
//        BarData data = new BarData(barDataSet);
//        chart.setData(data);
//        chart.invalidate();
//    }
//
//    private void initBarDataSet() {
//        //hiding the grey background of the chart, default false if not set
//        chart.setDrawGridBackground(false);
//        //remove the bar shadow, default false if not set
//        chart.setDrawBarShadow(false);
//        //remove border of the chart, default false if not set
//        chart.setDrawBorders(false);
//
//        //remove the description label text located at the lower right corner
//        Description description = new Description();
//        description.setEnabled(false);
//        chart.setDescription(description);
//
//        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
//        chart.animateY(1000);
//        //setting animation for x-axis, the bar will pop up separately within the time we set
//        chart.animateX(1000);
//
////        XAxis xAxis = chart.getXAxis();
////        //change the position of x-axis to the bottom
////        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
////        //set the horizontal distance of the grid line
////        xAxis.setGranularity(1f);
////        //hiding the x-axis line, default true if not set
////        xAxis.setDrawAxisLine(false);
////        //hiding the vertical grid lines, default true if not set
////        xAxis.setDrawGridLines(false);
//
//
//
//
//
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return xLabel.get((int)value);
//            }
//        });
//
//
//
//
//
//        YAxis leftAxis = chart.getAxisLeft();
//        //hiding the left y-axis line, default true if not set
//        leftAxis.setDrawAxisLine(false);
//
//        YAxis rightAxis = chart.getAxisRight();
//        //hiding the right y-axis line, default true if not set
//        rightAxis.setDrawAxisLine(false);
//
//        Legend legend = chart.getLegend();
//        //setting the shape of the legend form to line, default square shape
//        legend.setForm(Legend.LegendForm.LINE);
//        //setting the text size of the legend
//        legend.setTextSize(11f);
//        //setting the alignment of legend toward the chart
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        //setting the stacking direction of legend
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        //setting the location of legend outside the chart, default false if not set
//        legend.setDrawInside(false);
//
//
//        chart.setOnChartValueSelectedListener(new barChartOnChartValueSelectedListener());
//    }


    private void populateBillInfo() {
        lstBillInfo.clear();

        lstBillInfo.add(new DModelBillInfo("ISECo", "23100" + 0, "F9"));
        lstBillInfo.add(new DModelBillInfo("WAPDA", "23100" + 1, "F10"));
        lstBillInfo.add(new DModelBillInfo("WASA", "23100" + 2, "F11"));
        lstBillInfo.add(new DModelBillInfo("LESCO", "23100" + 2, "I18"));


        if (electricityInfoRcvAdapter == null) {

            electricityInfoRcvAdapter = new ElectricityInfoRcvAdapter(getActivity(), lstBillInfo, selection, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:

                        position_ = position;
                        selection = position;
//                        txvSelected_Disease.setVisibility(View.VISIBLE);
//                        txvSelected_Disease.setText(AppConfig.getInstance().lst_DiseasesDef.get(position).getDiseaseName());

                        setBillDetails();

                        break;

                    case EVENT_B:

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


    private void populateBillAnaylsis() {
        lstBillAnaylsis.clear();


        lstBillAnaylsis.add(new DModelBillAnaylsis("500", "Jan", "220"));
        lstBillAnaylsis.add(new DModelBillAnaylsis("200", "Feb", "320"));
        lstBillAnaylsis.add(new DModelBillAnaylsis("300", "Mar", "420"));
        lstBillAnaylsis.add(new DModelBillAnaylsis("100", "Apr", "20"));
        lstBillAnaylsis.add(new DModelBillAnaylsis("100", "May", "220"));


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
               // navToElectricityHomeFragment();
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

//    private class barChartOnChartValueSelectedListener implements OnChartValueSelectedListener {
//
//        @Override
//        public void onValueSelected(Entry e, Highlight h) {
//            //trigger activity when the bar value is selected
//
//        }
//
//        @Override
//        public void onNothingSelected() {
//
//        }
//    }
}

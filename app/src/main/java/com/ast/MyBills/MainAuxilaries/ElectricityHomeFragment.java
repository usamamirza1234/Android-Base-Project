package com.ast.MyBills.MainAuxilaries;

import android.graphics.drawable.Drawable;
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

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.FeaturedAdsViewPagerAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBanner;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IAdapterCallback;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.duolingo.open.rtlviewpager.RtlViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import me.relex.circleindicator.CircleIndicator;

import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;


public class ElectricityHomeFragment extends Fragment implements View.OnClickListener {
    private boolean isFirstTime = true;
    Timer timer;
    int currentPage, mIndicatorPosition;
    private ArrayList<DModelBanner> lstElectricAds;
    private ArrayList<DModelBillInfo> lstBillInfo;
    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;
    LinearLayout llBillDetails,llHistory;
    private RtlViewPager viewPgrFeaturedBanner;
    private CircleIndicator circleIndicator;
    ElectricityInfoRcvAdapter electricityInfoRcvAdapter;
    private FeaturedAdsViewPagerAdapter featuredAdsViewPagerAdapter;
    int position_ = 0;
    Integer selection = null;


    TextView txv_billDetails_company;
    TextView txv_billDetails_name;
    TextView txv_billDetails_address;
    TextView txv_billDetails_tariff;
    TextView txv_billDetails_billing_month;
    TextView txv_billDetails_prev_reading;
    TextView txv_billDetails_pres_reading;
    TextView txv_billDetails_unit;
    TextView txv_billDetails_due_date;
    TextView txv_billDetails_current_month;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home_electricity, container, false);

        init();
        bindviews(frg);

        initializeAds();
        populateBillInfo();

        if (selection == null) {
            selection=0;
            setBillDetails();
//            llBillDetails.setVisibility(View.GONE);
        }

        return frg;
    }

    private void initializeAds() {


        Drawable uploadPrscp;
        if (AppConfig.getInstance().isEnglishMode) {
            uploadPrscp = getActivity().getResources().getDrawable(R.drawable.ic_baseline_wifi_protected_setup_24);
        } else {
            uploadPrscp = getActivity().getResources().getDrawable(R.drawable.ic_baseline_wifi_protected_setup_24);
        }


        lstElectricAds.add(new DModelBanner("4", "https://i.ytimg.com/vi/66u6-W5sUVI/maxresdefault.jpg", uploadPrscp, true));
        lstElectricAds.add(new DModelBanner("3", "https://d3nuqriibqh3vw.cloudfront.net/nescafe_original_resized.jpg?X5opHznbHMpf6P5EgUfoX_NafvL7eCyT", uploadPrscp, true));
        lstElectricAds.add(new DModelBanner("2", "https://i2.wp.com/campaignsoftheworld.com/wp-content/uploads/2019/06/Sarfaraz-Ahmed-Yawn-memes-COTW-2.jpg?w=960&ssl=1", uploadPrscp, true));
        lstElectricAds.add(new DModelBanner("1", "https://i.dawn.com/primary/2016/02/56b9905e791f9.jpg", uploadPrscp, true));
        lstElectricAds.add(new DModelBanner("0", "https://mir-s3-cdn-cf.behance.net/project_modules/1400/7b18be17592343.562bbf99ac2ca.jpg", uploadPrscp, true));
//        lstFeaturedOutlets.add(new DModelBanner("-1", "", uploadPrscp, false));


        featuredAdsViewPagerAdapter = new FeaturedAdsViewPagerAdapter(getActivity(), lstElectricAds, this, new IAdapterCallback() {
            @Override
            public void onAdapterEventFired(int eventId, int position) {
                switch (eventId) {
                    case EVENT_A:

                        break;
                    default:
                        break;
                }
            }
        });

        viewPgrFeaturedBanner.setAdapter(featuredAdsViewPagerAdapter);
        viewPgrFeaturedBanner.setCurrentItem(0);
        viewPgrFeaturedBanner.setOffscreenPageLimit(1);
        circleIndicator.setVisibility(View.VISIBLE);
        circleIndicator.setViewPager(viewPgrFeaturedBanner);


        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        currentPage = 0;
        mIndicatorPosition = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new timerTask(), 2000, 2500);

    }

    private void init() {
        setBottomBar();

        lstElectricAds = new ArrayList<>();
        lstBillInfo = new ArrayList<>();
    }

    private void bindviews(View view) {

        viewPgrFeaturedBanner = view.findViewById(R.id.frg_market_viewpgr_featured_banner);
        circleIndicator = view.findViewById(R.id.frg_market_viewpagr_indicator);

        rcvElectInfo = view.findViewById(R.id.frg_home_electricity_rcvElectricityInfo);
        llBillDetails = view.findViewById(R.id.frg_home_electricity_llBill_Details);
        llHistory = view.findViewById(R.id.frg_home_electricity_llHistory);


        txv_billDetails_company = view.findViewById(R.id.frg_home_electricity_txv_bill_company);
        txv_billDetails_name = view.findViewById(R.id.frg_home_electricity_txv_bill_name);
        txv_billDetails_address = view.findViewById(R.id.frg_home_electricity_txv_bill_adderess);
        txv_billDetails_tariff = view.findViewById(R.id.frg_home_electricity_txv_bill_teriff);
        txv_billDetails_billing_month = view.findViewById(R.id.frg_home_electricity_txv_bill_billing_month);
        txv_billDetails_prev_reading = view.findViewById(R.id.frg_home_electricity_txv_bill_prev_reading);
        txv_billDetails_pres_reading = view.findViewById(R.id.frg_home_electricity_txv_bill_pres_reading);
        txv_billDetails_unit = view.findViewById(R.id.frg_home_electricity_txv_bill_units);
        txv_billDetails_due_date = view.findViewById(R.id.frg_home_electricity_txv_bill_due_date);
        txv_billDetails_current_month = view.findViewById(R.id.frg_home_electricity_txv_bill_current_month);


        llHistory.setOnClickListener(this);

    }


    private void populateBillInfo() {
        lstBillInfo.clear();

        lstBillInfo.add(new DModelBillInfo("ISECo", "23100" +0, "F9"));
        lstBillInfo.add(new DModelBillInfo("WAPDA", "23100" + 1, "F10"));
        lstBillInfo.add(new DModelBillInfo("WASA", "23100" + 2, "F11"));
        lstBillInfo.add(new DModelBillInfo("LESCO", "23100" + 2, "I18"));


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
                       // navToBillAnaylsisFragment(position);
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
        txv_billDetails_name.setText(lstBillInfo.get(selection).getCity());
        txv_billDetails_address.setText(lstBillInfo.get(selection).getAddress());

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_home_electricity_llHistory:
            //navToHistoryFragment();
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


    public class timerTask extends TimerTask {

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (isFirstTime) {
                            isFirstTime = false;
                        } else {
                            try {
                                if (viewPgrFeaturedBanner.getCurrentItem() == lstElectricAds.size() - 1) {
                                    currentPage = 0;
                                    mIndicatorPosition = 0;
                                    //updateIndicatorViews(mIndicatorPosition);
                                }
                                if (currentPage == 0) {
                                    viewPgrFeaturedBanner.setCurrentItem(currentPage, true);
                                    currentPage = currentPage + 1;
                                } else {
                                    viewPgrFeaturedBanner.setCurrentItem(currentPage, true);
                                    currentPage = currentPage + 1;
                                }

                                //updateIndicatorViews(mIndicatorPosition++);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }
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
}

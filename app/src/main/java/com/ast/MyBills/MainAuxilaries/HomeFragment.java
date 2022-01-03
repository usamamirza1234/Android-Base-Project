package com.ast.MyBills.MainAuxilaries;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.MainAuxilaries.Adapters.Dashboardinforcvadapter;
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.FeaturedAdsViewPagerAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBanner;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillDashboardInfo;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.CircleImageView;
import com.ast.MyBills.Utils.IAdapterCallback;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.duolingo.open.rtlviewpager.RtlViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private IBadgeUpdateListener mBadgeUpdateListener;
    private boolean isFirstTime = true;
    Timer timer;
    int currentPage, mIndicatorPosition;
    private ArrayList<DModelBanner> lstElectricAds;
    private ArrayList<DModelBillDashboardInfo> lstBillDashboardInfo;

    RecyclerView rcvdashboardinfo;
    LinearLayout llBillDetails,llHistory;
    private RtlViewPager viewPgrFeaturedBanner;
    private CircleIndicator circleIndicator;
    Dashboardinforcvadapter dashboardinforcvadapter;
    private FeaturedAdsViewPagerAdapter featuredAdsViewPagerAdapter;
    int position_ = 0;
    Integer selection = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home, container, false);


        init();
        bindviews(frg);

        initializeAds();
        populateBillInfo();

        if (selection == null) {
            selection=0;
           // setBillDetails();
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
        timer.scheduleAtFixedRate(new HomeFragment.timerTask(), 2000, 2500);

    }

    private void populateBillInfo() {
        lstBillDashboardInfo.clear();

        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Electricity", "2,00" +0, "20-4-2021", "paid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Water", "2,00" +1, "20-4-2019", "Unpaid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Mobile", "2,00" +2, "10-4-2018", "paid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("TV", "2,00" +3, "24-4-2021", "paid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Internet", "2,00" +4, "09-4-2021", "paid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Electricity", "2,00" +0, "22-4-2021", "paid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Water", "2,00" +1, "14-4-2021", "Unpaid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Mobile", "2,00" +2, "17-4-2021", "paid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("TV", "2,00" +3, "23-4-2022", "paid", "F9"));
        lstBillDashboardInfo.add(new DModelBillDashboardInfo("Internet", "2,00" +4, "02-4-2022", "paid", "F9"));
        if (dashboardinforcvadapter == null) {

            dashboardinforcvadapter = new Dashboardinforcvadapter(getActivity(), lstBillDashboardInfo, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:

                        position_ = position;
                        selection = position;
//                        txvSelected_Disease.setVisibility(View.VISIBLE);
//                        txvSelected_Disease.setText(AppConfig.getInstance().lst_DiseasesDef.get(position).getDiseaseName());



                        break;

                    case EVENT_B:
                        //navToBillAnaylsisFragment(position);
                        navToElectricityHomeFragment(position);
                        break;
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvdashboardinfo.setLayoutManager(linearLayoutManager);
            rcvdashboardinfo.setAdapter(dashboardinforcvadapter);

        } else {
            dashboardinforcvadapter.notifyDataSetChanged();
        }

    }
    //region INIT
    private void init() {
        setToolbar();
        lstElectricAds = new ArrayList<>();
        lstBillDashboardInfo = new ArrayList<>();

    }
    private void bindviews(View view) {

        viewPgrFeaturedBanner = view.findViewById(R.id.frg_market_viewpgr_featured_banner);
        circleIndicator = view.findViewById(R.id.frg_market_viewpagr_indicator);

        rcvdashboardinfo = view.findViewById(R.id.frg_home_dashboard_rcvdashboardInfo);

    }





    void setToolbar() {

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
            setToolbar();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.profile:



                break;
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


        private void navToElectricityHomeFragment(int selection) {
            FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new ElectricityHomeFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection" , selection);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ElectricityHomeFragment);
        Log.d("selection", "selectedPosition navToBillAnaylsisFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_ElectricityHomeFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();

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
//    private void navToBillAnaylsisFragment(int selection) {
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft;
//        Fragment frg = new BillAnaylsisFragment();
//        ft = fm.beginTransaction();
//        Bundle bundle = new Bundle();
//        bundle.putInt("key_selection" , selection);
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_BillAnaylsisFragment);
//        Log.d("selection", "selectedPosition navToBillAnaylsisFragment " + selection);
//        ft.addToBackStack(AppConstt.FragTag.FN_BillAnaylsisFragment);
//        frg.setArguments(bundle);
//        ft.hide(this);
//        ft.commit();
//    }
}
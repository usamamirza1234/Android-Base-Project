package com.ast.MyBills.MainAuxilaries;

import android.graphics.Color;
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
import com.ast.MyBills.MainAuxilaries.Adapters.Dashboardinforcvadapter;
import com.ast.MyBills.MainAuxilaries.Adapters.FeaturedAdsViewPagerAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBanner;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillDashboardInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bills;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IAdapterCallback;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.duolingo.open.rtlviewpager.RtlViewPager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static com.ast.MyBills.Utils.AppConstt.bill.ALL;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;

public class HomeFragment extends Fragment implements View.OnClickListener {

    Timer timer;
    int currentPage, mIndicatorPosition;
    TextView txvoverdue, txvupcoming, txvpaid, txvall;
    LinearLayout llImportantDates;
    RecyclerView rcvdashboardinfo;
    LinearLayout llBillDetails, llHistory;
    Dashboardinforcvadapter dashboardinforcvadapter;
    Integer position_ = null;
    Integer selection = null;
    String key = "";
    private IBadgeUpdateListener mBadgeUpdateListener;
    private boolean isFirstTime = true;
    private ArrayList<DModelBanner> lstElectricAds;
    private ArrayList<DModelBillDashboardInfo> lstBillDashboardInfo;
    private ArrayList<DModel_Bills> lstBillDashboardElement;
    private ArrayList<DModel_Bills> lstBillDashboardElementFilter;
    private RtlViewPager viewPgrFeaturedBanner;
    private CircleIndicator circleIndicator;
    private FeaturedAdsViewPagerAdapter featuredAdsViewPagerAdapter;
TextView txvStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home, container, false);


        init();
        bindviews(frg);

        initializeAds();
        populateBillInfo(ALL);
        txvStatus.setText("All");

        txvStatus.setTextColor(Color.parseColor("#F8C120"));
        if (selection == null) {
            selection = 0;
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


    //under
    private void populateBillInfo(int act) {

        lstBillDashboardElementFilter.clear();
        lstBillDashboardElement.clear();
        lstBillDashboardElement = AppConfig.getInstance().getBillerSetting();


        ArrayList<DModel_Bills> lstBillIsPaid = new ArrayList<>();
        ArrayList<DModel_Bills> lstBillIsDue = new ArrayList<>();
        ArrayList<DModel_Bills> lstBillIsUpcoming = new ArrayList<>();

        String currentDate = new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(new Date());
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);

        for (int i = 0; i < lstBillDashboardElement.size(); i++) {
//            if (lstBillDashboardElement.get(i).isPaid) // if you found isPaid wala check from api or sir bta dain k kisye niklana ha ispaid. is paid hoga to due or upcoming ni daikhna else daikna ha
//            {
//                lstBillIsPaid.add(lstBillDashboardElement.get(i));
//            }
//            else
//                {
            try {
                String s = lstBillDashboardElement.get(i).duedate;
                Date dateFromApi = format.parse(lstBillDashboardElement.get(i).duedate);
                Date dateFromCurrentDateString = format.parse(currentDate);
                if (dateFromApi.after(dateFromCurrentDateString)) {
                    lstBillIsUpcoming.add(lstBillDashboardElement.get(i));
                } else {
                    lstBillIsDue.add(lstBillDashboardElement.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            }

        }
        
        if (act == AppConstt.bill.ALL) {
            populateBils(act, lstBillDashboardElement);
        } else if (act == AppConstt.bill.OVERDUE) {
            populateBils(act, lstBillIsDue);
        } else if (act == AppConstt.bill.UPCOMING) {
            populateBils(act, lstBillIsUpcoming);
        } else if (act == AppConstt.bill.PAID) {
            populateBils(act, lstBillIsPaid);
        }


    }

    private void populateBils(int act, ArrayList<DModel_Bills> lstvdsf) {

        int s = act;
        dashboardinforcvadapter = null;
        if (dashboardinforcvadapter == null) {


            dashboardinforcvadapter = new Dashboardinforcvadapter(getActivity(), lstvdsf, act, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:
                        position_ = position;
                        selection = position;
                        key = "key" + (lstBillDashboardElement.get(position).getRefference_number());
                        break;
                    case EVENT_B:
                        navToElectricityHomeFragment(position, key, lstBillDashboardElement.get(position).getBillType());
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
        lstBillDashboardElementFilter = new ArrayList<>();
        lstBillDashboardElement = new ArrayList<>();


    }

    private void bindviews(View view) {

        viewPgrFeaturedBanner = view.findViewById(R.id.frg_market_viewpgr_featured_banner);
        circleIndicator = view.findViewById(R.id.frg_market_viewpagr_indicator);

        rcvdashboardinfo = view.findViewById(R.id.frg_home_dashboard_rcvdashboardInfo);
        llImportantDates = view.findViewById(R.id.homeImportantdates);
        txvoverdue = view.findViewById(R.id.overdue);
        txvStatus = view.findViewById(R.id.txvstatus);
        txvupcoming = view.findViewById(R.id.upcoming);
        txvpaid = view.findViewById(R.id.paid);
        txvall = view.findViewById(R.id.alll);
        txvall.setOnClickListener(this);
        txvoverdue.setOnClickListener(this);
        txvupcoming.setOnClickListener(this);
        txvpaid.setOnClickListener(this);
        llImportantDates.setOnClickListener(this);
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


            case R.id.overdue:

                populateBillInfo(AppConstt.bill.OVERDUE);
                txvStatus.setText("Overdue");
                txvStatus.setTextColor(Color.parseColor("#ca2306"));
                break;
            case R.id.alll:
                populateBillInfo(ALL);
                txvStatus.setText("All");
                txvStatus.setTextColor(Color.parseColor("#F8C120"));
                break;

            case R.id.upcoming:
                populateBillInfo(AppConstt.bill.UPCOMING);
                txvStatus.setText("Upcoming");
                txvStatus.setTextColor(Color.parseColor("#017fd6"));

                break;

            case R.id.paid:
                populateBillInfo(AppConstt.bill.PAID);
                txvStatus.setText("Paid");
                txvStatus.setTextColor(Color.parseColor("#16ca6b"));

                break;


            case R.id.homeImportantdates:
                //   if (isListSelected())
                navToImportantDatesFragment(key);
//                else
//                    CustomToast.showToastMessage(getActivity(), "Select Bill first", Toast.LENGTH_SHORT);
//                break;
        }
    }

    private void navToElectricityHomeFragment(int selection, String key, String billType) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new ElectricityHomeFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection", selection);
        bundle.putString("key_fordata", key);
        bundle.putString("key_billType", billType);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ElectricityHomeFragment);
        Log.d("selection", "selectedPosition navToBillAnaylsisFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_ElectricityHomeFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();

    }

    private void navToImportantDatesFragment(String Key) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new ImportantDatesFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("key_fordata", Key);
        frg.setArguments(bundle);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_HistoryFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_HistoryFragment);
        ft.hide(this);
        ft.commit();
    }

    private boolean isListSelected() {
        return position_ != null;
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
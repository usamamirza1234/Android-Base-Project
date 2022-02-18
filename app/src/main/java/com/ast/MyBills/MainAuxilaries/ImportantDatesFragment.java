package com.ast.MyBills.MainAuxilaries;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.AppConfig;
import com.ast.MyBills.IntroAuxilaries.WebServices.More_WebHit_Get_Bills;
import com.ast.MyBills.MainAuxilaries.Adapters.Dashboardinforcvadapter;
import com.ast.MyBills.MainAuxilaries.Adapters.FeaturedAdsViewPagerAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.ImportantDatesRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBanner;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bills;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_ImportantDates;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.decorators.DisableSelectedDatesDecorator;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.decorators.EventDecorator;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.CalendarDay;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.MaterialCalendarView;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IAdapterCallback;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static android.content.ContentValues.TAG;


public class ImportantDatesFragment extends Fragment implements View.OnClickListener {
    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    private boolean isFirstTime = true;
    Timer timer;
    int currentPage, mIndicatorPosition;
    private ArrayList<DModelBanner> lstElectricAds;
    private RtlViewPager viewPgrFeaturedBanner;
    private CircleIndicator circleIndicator;
    private FeaturedAdsViewPagerAdapter featuredAdsViewPagerAdapter;


     MaterialCalendarView materialCalendarView;
    private MaterialCalendarView materialCalendarView_nextMonth;
    IBadgeUpdateListener mBadgeUpdateListener;
    private SimpleDateFormat mFormat, mFormatForApi;
    ImportantDatesRcvAdapter importantDatesRcvAdapter;
    ArrayList<DModel_Bills> lstImportantDates;
    RecyclerView rcvImportantDates;
    ArrayList<String> strlstImportantDates;
    private String arrayKey="";
    ArrayList<String> lstImpDates;

    View frg;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        frg = inflater.inflate(R.layout.fragment_calender_custom, container, false);

        init();
        bindviews(frg);
        initializeAds();
        populateCalenders();
        populateImportantDates();
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
        timer.scheduleAtFixedRate(new ImportantDatesFragment.timerTask(), 2000, 2500);

    }

    //region Material Calender
    private void populateCalenders() {
        if (getActivity() != null) {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            Date selectedDate = new Date();
            mFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            mFormatForApi = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            materialCalendarView.setSelectedDate(selectedDate);
            materialCalendarView.setPagingEnabled(false);

            materialCalendarView.state().edit()
                    .setFirstDayOfWeek(Calendar.MONDAY)

                    .setMinimumDate(CalendarDay.from(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1))
                    .commit();

//Day of Name in full form like,"Saturday", or if you need the first three characters you have to put "EEE" in the date format and your result will be "Sat".
//            SimpleDateFormat sdf_ = new SimpleDateFormat("E");
//            Date date1 = new Date();
//            String dayName = sdf_.format(date1);

//            materialCalendarView.addDecorator(new EventDecorator(getActivity()));
//            materialCalendarView.addDecorator(new DisableDatesDecorator());   /// before date highlights
//            materialCalendarView.addDecorator(new TodayDateDecorator(getActivity()));

            ArrayList<CalendarDay> listDates = new ArrayList<>();
            CalendarDay dateTimeSlots = new CalendarDay();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            for (int i = 0; i < strlstImportantDates.size(); i++) {

//                String date = "2021-08-28";
                String date = strlstImportantDates.get(i).toString();
                Date days = null;

                if (date != null) {

                    try {
                        days = dateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.d("LOG_AS", "formated date time: " + days.getTime() + "");


                    CalendarDay calDays = dateTimeSlots.from(days);

                    listDates.add(calDays);


                }
            }

            materialCalendarView.addDecorator(new DisableSelectedDatesDecorator(getActivity(), listDates));

        }

//Calendar 2
        if (getActivity() != null) {


            Calendar cal = Calendar.getInstance(Locale.ENGLISH);

//            cal.add(Calendar.MONTH, 2);
//            Date selectedDate = cal.getTime();

            Date selectedDate = new Date();


            mFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            mFormatForApi = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            materialCalendarView_nextMonth.setSelectedDate(selectedDate);


            materialCalendarView_nextMonth.setPagingEnabled(false);

            materialCalendarView_nextMonth.state().edit()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setMinimumDate(CalendarDay.from(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1))
                    .commit();


              materialCalendarView_nextMonth.addDecorator(new EventDecorator(getActivity()));
//            materialCalendarView_nextMonth.addDecorator(new DisableDatesDecorator());
//            materialCalendarView_nextMonth.addDecorator(new TodayDateDecorator(getActivity()));

            ArrayList<CalendarDay> listDates = new ArrayList<>();

            CalendarDay dateTimeSlots = new CalendarDay();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


            for (int i = 0; i < strlstImportantDates.size(); i++) {

//                String date = "2021-08-28";
                String date = strlstImportantDates.get(i).toString();
                Date days = null;

                if (date != null) {

                    try {
                        days = dateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.d("LOG_AS", "formated date time: " + days.getTime() + "");


                    CalendarDay calDays = dateTimeSlots.from(days);

                    listDates.add(calDays);


                }
            }

            materialCalendarView_nextMonth.addDecorator(new DisableSelectedDatesDecorator(getActivity(), listDates));
            materialCalendarView_nextMonth.setNextMonth();
        }
    }
    //endregion

    //region Important Dates
    private void populateImportantDates() {
        lstImportantDates.clear();
        lstImportantDates = AppConfig.getInstance().getBillerSetting();

        if (importantDatesRcvAdapter == null) {

            importantDatesRcvAdapter = new ImportantDatesRcvAdapter(getActivity(), lstImportantDates, (eventId, position) -> {

            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvImportantDates.setLayoutManager(linearLayoutManager);
            rcvImportantDates.setAdapter(importantDatesRcvAdapter);

        } else {
            importantDatesRcvAdapter.notifyDataSetChanged();
        }

    }
    //endregion

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


        }
    }


    //region Fragment Compulsory
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            arrayKey = bundle.getString("key_fordata");
        }

        setBottomBar();


        lstElectricAds = new ArrayList<>();
        lstImportantDates = new ArrayList<>();
        strlstImportantDates = new ArrayList();
        lstImportantDates = AppConfig.getInstance().getimp();



      ??  lstImpDates = AppConfig.getInstance().getimpString(arrayKey+"ImpKey");
        ??? AGR AIK JGA SY A RA HA TO DYNAMIC KEY KU CHIYE HA?????????????????????
        ?? isn't i ask k sb smj a gya ha
            ???? sara krwa k diya tha concept smjhny gya tha mera or tera time zaya ni krny gya tha
                ?? wasnt i told you how busy i am????????
        ?? upr sy isi liy khta tha k gando k triky sy kam kro taky yh gando smj jy asani sy kiya
                chutiyap chl rah ha
        why thi array key ?? arrayKey or ha b to khin say ani chiye na bundle ma likh dainy sy a jay gi ???????
    ??        String key = arrayKey+"impkey";




        String key = "impkey";
        lstImpDates = AppConfig.getInstance().getimpString(key);

        Log.d(TAG, "onWebResult: impKey " + key + " " + AppConfig.getInstance().getimpString(key).size() );

        ???  what is this ??
        saving key like "impkey12312312312312" and wnt to get like "key12312312312312ImpKey"
        Log.d("MYBILL", "onWebResult: KEYimp " +arrayKey+"imp");
            ???  what is this what do you want to log????  (arrayKey+"imp") is same with (arrayKey+"ImpKey") ??


        ?? I told you to dont do this like AppConfig.getInstance().lstImpDates ??
        ?? I have done only to trivers data in app



        for (int i=0; i<lstImpDates.size();i++)
        {
            String strdate = lstImpDates.get(i);
            Log.d("logApiData", "strdate:  " + strdate);
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
            try {
                Date date = format.parse(strdate);
                Log.d("logApiData", "date1:  " + date);
                SimpleDateFormat dayMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String strmonth = dayMonth.format(date);
                Log.d("logApiData","formattedDate:  " +strmonth);
                strlstImportantDates.add(strmonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }




    private void bindviews(View view) {
        viewPgrFeaturedBanner = view.findViewById(R.id.frg_market_viewpgr_featured_banner);
        circleIndicator = view.findViewById(R.id.frg_market_viewpagr_indicator);
        rcvImportantDates = view.findViewById(R.id.frg_my_bills_rcvImportantDate);
        materialCalendarView = view.findViewById(R.id.dlg_reorder_mcv_calndr);
        materialCalendarView_nextMonth = view.findViewById(R.id.dlg_reorder_mcv_calndr_nextmonth);
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

    void setBottomBar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.ToolbarState.TOOLBAR_VISIBLE);
            mBadgeUpdateListener.setHeaderTitle(getString(R.string.frg_important_dates));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setBottomBar();
        }
    }
}

package com.ast.MyBills;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ast.MyBills.IntroAuxilaries.MyBillsFragment;
import com.ast.MyBills.IntroAuxilaries.PreSignInFragment;
import com.ast.MyBills.MainAuxilaries.Adapters.BillerSpinnerAdapter;
import com.ast.MyBills.MainAuxilaries.EditProfileFragment;
import com.ast.MyBills.MainAuxilaries.HomeFragment;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.CircleImageView;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IBadgeUpdateListener{

    LinearLayout llPriority_Sector, llDashboard, llPerformancemonitoring, llRapid, llissues,llEditProfile, llSetting, llImmediateDiseasesReportsIDR, llIssuesFacedPrivate, llLogout;
    CircleImageView editprofile, Setting;
    FragmentTransaction ft;
    RelativeLayout rlBotmbar;
    public DrawerLayout drawer;
    NavigationView navigationView;
    private FragmentManager fm;

    TextView txvTitleBar,btnview;

    RelativeLayout rlToolbar, rlMenu ,idr;
    private Dialog progressDialog;
    RelativeLayout mMap,mcalendar;


    BillerSpinnerAdapter billerSpinnerAdapter;
    Spinner spinnerBiller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AppConfig.getInstance().performLangCheck(getWindow());
        if (savedInstanceState != null) {
            return;
        }
        init();

        bindviews();

        navToHomeFragment();

        populateSpinnerBiller();

    }



    private void populateSpinnerBiller() {
        ArrayList<String> lstBiller = new ArrayList<>();

        lstBiller.add("IESCO");
        lstBiller.add("WAPDA");
        lstBiller.add("GEPCO");
        lstBiller.add("PESCO");

        lstBiller.add(getResources().getString(R.string.select_biller_type));


        billerSpinnerAdapter = new BillerSpinnerAdapter(this, lstBiller);
        spinnerBiller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                int Pos = Integer.parseInt(selectedItem);
//                txvBillType.setText(lstGender.get(position));


            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBiller.setAdapter(billerSpinnerAdapter);
        spinnerBiller.setSelection(billerSpinnerAdapter.getCount());


    }


    private void bindviews() {
//        Spinner dropdown1 = findViewById(R.id.spinner1);
//        Spinner dropdown2 = findViewById(R.id.spinner2);
//        String[] items1 = new String[]{"Electricity", "Gas", "Water"};
//        String[] items2 = new String[]{"Electricity", "Gas", "Water"};
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
//        dropdown1.setAdapter(adapter1);
//        dropdown2.setAdapter(adapter2);


        editprofile = findViewById(R.id.profile);
        Setting = findViewById(R.id.tlSetting);

        spinnerBiller = findViewById(R.id.frg_my_bills_spinnerBiller);

        editprofile.setOnClickListener(this);
        Setting.setOnClickListener(this);



        drawer = findViewById(R.id.act_main_drawr);
        navigationView = drawer.findViewById(R.id.act_main_navview);
        rlBotmbar = findViewById(R.id.act_main_rl_bttabbar);
        btnview = findViewById(R.id.btnview);

        txvTitleBar = findViewById(R.id.act_intro_txv_title);
        rlMenu = findViewById(R.id.act_intro_rl_toolbar_menu);
        idr = findViewById(R.id.homebar);

        llPriority_Sector = findViewById(R.id.lay_navigationview_llPriority_Sector);
        llPerformancemonitoring = findViewById(R.id.lay_navigationview_llPerformancemonitoring);
        llDashboard = findViewById(R.id.lay_navigationview_llDashboard);
        llRapid = findViewById(R.id.lay_navigationview_llRapid);
        llEditProfile = findViewById(R.id.lay_navigationview_llEditProfile);
        llSetting = findViewById(R.id.lay_navigationview_llSetting);
        llImmediateDiseasesReportsIDR = findViewById(R.id.lay_navigationview_llimmediatediseasesreports_idr);
        llIssuesFacedPrivate = findViewById(R.id.lay_navigationview_llIssuesFacedPrivate);
        llLogout = findViewById(R.id.lay_navigationview_llLogout);
        mMap = findViewById(R.id.map);
//        mMap.setOnClickListener(this);

      //  mcalendar = findViewById(R.id.btncalendar);
//        mcalendar.setOnClickListener(this);

//        SupportMapFragment MapFragment = (SupportMapFragment).getSupportFragmentManager().findFragmentById(R.id.map);
//
//        MapFragment.getMapAsync(this);


        rlMenu.setOnClickListener(this);


        idr.setOnClickListener(this);
//
//        llPriority_Sector.setOnClickListener(this);
        llPerformancemonitoring.setOnClickListener(this);
        llDashboard.setOnClickListener(this);
//        llRapid.setOnClickListener(this);
//        llissues.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        llEditProfile.setOnClickListener(this);

        llImmediateDiseasesReportsIDR.setOnClickListener(this);

//        llIssuesFacedPrivate.setOnClickListener(this);
        llLogout.setOnClickListener(this);


        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // Whatever you want
                //   changeDrawerItemState();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // Whatever you want
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Whatever you want
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Whatever you want
            }
        });



    }




    //region  functions for Dialog
    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    private void showProgDialog() {
        progressDialog = new Dialog(this, R.style.AppTheme);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void navToHomeFragment() {
        clearMyBackStack();
        Fragment frg = new HomeFragment();
        // TODO: 09-Nov-21 change frg here
        // Fragment frg = new ExampleHRUseFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_HomeFragment);
        ft.commit();

    }



    public void navToEditProfileFragment() {
        Fragment frg = new EditProfileFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_EditProfileFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_EditProfileFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }
    private void navToBillerSettingFragment() {
        Fragment frg = new MyBillsFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_MyBillsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyBillsFragment);
        hideLastStackFragment(ft);
        ft.commit();

    }

    private void navToMyBillsFragment() {
        Fragment frg = new MyBillsFragment();
        ft = fm.beginTransaction();
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_MyBillsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyBillsFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }
//
//    public void navToCalendar() {
//        Fragment frg = new CalendarFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_CalendarFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_CalendarFragment);
//        hideLastStackFragment(ft);
//        ft.commit();
//    }
//
//
//    public void navToCircle() {
//        Fragment frg = new circleFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_CalendarFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_CalendarFragment);
//        hideLastStackFragment(ft);
//        ft.commit();
//    }






    private void init() {
        fm = getSupportFragmentManager();
    }


//    public void navToImmediateDiseasesReportsIDR() {
//        Fragment frg = new ImmediateDiseasesReportsIDRFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ImmediateDiseasesReportsIDR);
//        ft.addToBackStack(AppConstt.FragTag.FN_ImmediateDiseasesReportsIDR);
//        hideLastStackFragment(ft);
//        ft.commit();
//
//    }
//
//    public void navToPerformanceMonitoringFragment() {
//        Fragment frg = new PerformanceMonitoringFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_PerformanceMonitoringFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_PerformanceMonitoringFragment);
//        hideLastStackFragment(ft);
//        ft.commit();
//    }

    public void navToPrimeMinisterPrioritySectorFragment() {
//        Fragment frg = new PrimeMinisterPrioritySectorFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_PrimeMinisterFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_PrimeMinisterFragment);
//        hideLastStackFragment(ft);
//        ft.commit();
    }

    public void navToIssuesFaced() {
//        Fragment frg = new IssuesFacedByPrivateSectorFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_IssuesFacedFragment);
//
//        ft.addToBackStack(AppConstt.FragTag.FN_IssuesFacedFragment);
//        hideLastStackFragment(ft);
//        ft.commit();
    }

    public void navToRapidAssessment() {
//        Fragment frg = new RapidAssessmentFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_RapidAssessment);
//        ft.addToBackStack(AppConstt.FragTag.FN_RapidAssessment);
//        hideLastStackFragment(ft);
//        ft.commit();
    }

    public void navToissues() {
//        Fragment frg = new FragmentIssues();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_issues);
//        ft.addToBackStack(AppConstt.FragTag.FN_issues);
//        hideLastStackFragment(ft);
//        ft.commit();
    }


    public void navToFeedbackReport() {
//        Fragment frg = new FeedbackReportFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_FeedbackReport);
//        ft.addToBackStack(AppConstt.FragTag.FN_FeedbackReport);
//        hideLastStackFragment(ft);
//        ft.commit();
    }




    public void clearMyBackStack() {
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();

        }

    }


    public void hideLastStackFragment(FragmentTransaction ft) {
//        Fragment frg = null;
//        frg = getSupportFragmentManager().findFragmentById(R.id.act_main_content_frg);
//
//        if (frg != null) {
//            if (frg instanceof HomeFragment && frg.isVisible()) {
//                ft.hide(frg);
//            }
//            if (frg instanceof PunjabBusinessRegulatoryFragment && frg.isVisible()) {
//                ft.hide(frg);
//            } else if (frg instanceof RegulatoryMappingFragment && frg.isVisible()) {
//                ft.hide(frg);
//            } else if (frg instanceof PrimeMinisterPrioritySectorFragment && frg.isVisible()) {
//                ft.hide(frg);
//            } else if (frg instanceof IssuesFacedByPrivateSectorFragment && frg.isVisible()) {
//                ft.hide(frg);
//            } else if (frg instanceof RapidAssessmentFragment && frg.isVisible()) {
//                ft.hide(frg);
//            } else if (frg instanceof FeedbackReportFragment && frg.isVisible()) {
//                ft.hide(frg);
//            } else if (frg instanceof FragmentIssues && frg.isVisible()) {
//                ft.hide(frg);
//            }
//        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_intro_rl_toolbar_menu:
                openDrawar();
                break;
            case R.id.lay_navigationview_llLogout:


                    AppConfig.getInstance().navtoLogin();



                break;
             case R.id.lay_navigationview_llDashboard:
                closeDrawar();
                navToHomeFragment();

                break;
            case R.id.lay_navigationview_llSetting:
                closeDrawar();
                navToBillerSettingFragment();

                break;
            case R.id.lay_navigationview_llEditProfile:
                closeDrawar();

                navToEditProfileFragment();

                break;

            case R.id.profile:
                navToEditProfileFragment();


                break;
            case R.id.tlSetting:
                navToBillerSettingFragment();


                break;

        }


    }

    //region Ibadge
    @Override
    public void setBottomTabVisiblity(int mVisibility) {

    }
    @Override
    public void setToolbarVisiblity(int mVisibility) {
//        rlToolbar.setVisibility(mVisibility);
    }

    @Override
    public void setToolbarState(byte mState) {
        switch (mState) {
            case AppConstt.ToolbarState.TOOLBAR_BACK_HIDDEN:
                closeDrawar();

                rlMenu.setVisibility(View.VISIBLE);
                txvTitleBar.setVisibility(View.VISIBLE);
                idr.setVisibility(View.VISIBLE);
                break;

//            case AppConstt.ToolbarState.TOOLBAR_VISIBLE:
//                closeDrawar();
//
//                rlMenu.setVisibility(View.VISIBLE);
//                txvTitleBar.setVisibility(View.VISIBLE);
//                break;
        }
    }

    @Override
    public void switchStatusBarColor(boolean isDark) {

    }

    @Override
    public boolean setHeaderTitle(String strAppTitle) {
        try {
            txvTitleBar.setText(strAppTitle + "");
        }
        catch (Exception e)
        {

        }

        return false;
    }
    //endregion

    //region Drawer
    public void openDrawar() {

        drawer.openDrawer(GravityCompat.START);

    }

    public void closeDrawar() {

        drawer.closeDrawer(GravityCompat.START);
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void lockDrawar() {

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawar() {

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
    //endregion

    @Override
    public void onBackPressed() {

        AppConfig.getInstance().closeKeyboard(this);

        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();

            } else {

                super.onBackPressed();
            }
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}




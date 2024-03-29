package com.ast.MyBills.MainAuxilaries;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.ast.MyBills.MainActivity;
import com.ast.MyBills.MainAuxilaries.Adapters.ElectricityInfoRcvAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.FeaturedAdsViewPagerAdapter;
import com.ast.MyBills.MainAuxilaries.Adapters.PDFRcvAdapter;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBanner;
import com.ast.MyBills.MainAuxilaries.DModels.DModelBillInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModelPDF;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IAdapterCallback;
import com.ast.MyBills.Utils.IBadgeUpdateListener;
import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static android.content.ContentValues.TAG;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_A;
import static com.ast.MyBills.Utils.IAdapterCallback.EVENT_B;


public class PdfFragment extends Fragment implements View.OnClickListener, OnLoadCompleteListener {


  //  private ArrayList<DModelPDF> lstPDF;
    IBadgeUpdateListener mBadgeUpdateListener;
    RecyclerView rcvElectInfo;
    LinearLayout llBillDetails, llpdfImportantdates;
    TextView History,billinfo;
    PDFRcvAdapter pdfRcvAdapter;
    int position_ = 0;
    Integer selection = null;
    TextView txv_billDetails_company,BilltypeHeading;
    String sref = "";
    private ArrayList<DModelBillInfo> lstPDF;
    private String arrayKey="";
    String billType = "";
//pdf
PDFView pdfview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_pdf, container, false);

        init();
        bindviews(frg);
        BilltypeHeading.setText(billType+"");

        populateBillInfo(lstPDF);

        if (selection == null) {
            selection=0;

//            llBillDetails.setVisibility(View.GONE);
        }
        //else
         setBillDetails();


        return frg;
    }



    private void init() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selection = bundle.getInt("key_selection");
            Log.d("selection", "selectedPosition init " + selection);
            sref = bundle.getString("key_iesco");
            billType = bundle.getString("key_billType");
            arrayKey = bundle.getString("key_fordata");
        }
        setBottomBar();
        Log.d("MYBILL", "onWebResult: KEYChart " +arrayKey+"pdf");
       // lstPDF = AppConfig.getInstance().getBillsIESCO("
        // ");
        lstPDF = AppConfig.getInstance().getBillsIESCO(arrayKey);

        //lstPDF = new ArrayList<>();
    }

    private void bindviews(View view) {



        BilltypeHeading = view.findViewById(R.id.PDFHeading);

        rcvElectInfo = view.findViewById(R.id.frg_home_electricity_rcvElectricityInfo);
        llBillDetails = view.findViewById(R.id.frg_home_electricity_llBill_Details);
        History = view.findViewById(R.id.electricityhomehistory);
        billinfo = view.findViewById(R.id.electricityhomebillinfo);
        txv_billDetails_company = view.findViewById(R.id.frg_home_electricity_txv_bill_company);
        llpdfImportantdates = view.findViewById(R.id.frg_home_pdf_llImportantdates);
        pdfview = view.findViewById(R.id.pdfView);
        pdfview.fromAsset("Bill.pdf")
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .onLoad(this)
                .load();


        llpdfImportantdates.setOnClickListener(this);
        History.setOnClickListener(this);
        billinfo.setOnClickListener(this);

    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfview.getDocumentMeta();
        printBookmarksTree(pdfview.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }





    private void populateBillInfo(ArrayList<DModelBillInfo> lstPDF) {
//        lstPDF.clear();
//
//        lstPDF.add(new DModelPDF("ISECo", "11" +0, "F9"));
//        lstPDF.add(new DModelPDF("WAPDA", "10" + 1, "F10"));
//        lstPDF.add(new DModelPDF("WASA", "32" + 2, "F11"));
//        lstPDF.add(new DModelPDF("LESCO", "12" + 2, "I18"));


        if (pdfRcvAdapter == null) {

            pdfRcvAdapter = new PDFRcvAdapter(getActivity(), lstPDF, (eventId, position) -> {
                switch (eventId) {
                    case EVENT_A:

                        position_ = position;
                        selection = position;

                        //setBillDetails();

                        //to show URL Based PDF if "http://docs.google.com/viewer?url=" is removed it will download it directly
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=" + "https://www.ppsc.gop.pk/(S(2y5bvukt1lbgiaskirte4knb))/Misc_Uploading/Examiner%20Proforma_24-09-2021.pdf"), "text/html");
//                        startActivity(intent);

                        break;

                    case EVENT_B:
                        navToChartHistory(selection,arrayKey);
                        break;
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvElectInfo.setLayoutManager(linearLayoutManager);
            rcvElectInfo.setAdapter(pdfRcvAdapter);

        } else {
            pdfRcvAdapter.notifyDataSetChanged();
        }

    }

    private void setBillDetails() {
        if (selection == null)
            llBillDetails.setVisibility(View.GONE);
        else llBillDetails.setVisibility(View.VISIBLE);


        txv_billDetails_company.setText(lstPDF.get(selection).getBillType());


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.electricityhomebillinfo:
                navToElectricityHomeFragment();
                break;

            case R.id.electricityhomehistory:
                navToBillAnaylsisFragment(selection,arrayKey);
                break;
            case R.id.frg_home_pdf_llImportantdates:
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
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection" , selection);
        bundle.putString("key_billType", billType);
        bundle.putString("key_fordata", arrayKey);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ElectricityHomeFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ElectricityHomeFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }

    private void navToBillAnaylsisFragment(int selection,String arrayKey) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new BillAnaylsisFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection" , selection);
        bundle.putString("key_billType", billType);
        bundle.putString("key_fordata", arrayKey);
        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_BillAnaylsisFragment);
        Log.d("selection", "selectedPosition navToBillAnaylsisFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_BillAnaylsisFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
    }

    private void navToChartHistory(int selection,String arrayKey) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        Fragment frg = new ChartHistoryFragment();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("key_selection" , selection);
        bundle.putString("key_fordata", arrayKey);
        bundle.putString("key_billType", billType);

        ft.add(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_ChartHistoryFragment);
        Log.d("selection", "selectedPosition navToPDFFragment " + selection);
        ft.addToBackStack(AppConstt.FragTag.FN_ChartHistoryFragment);
        frg.setArguments(bundle);
        ft.hide(this);
        ft.commit();
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

}

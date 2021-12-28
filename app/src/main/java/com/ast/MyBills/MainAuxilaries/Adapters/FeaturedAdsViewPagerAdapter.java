package com.ast.MyBills.MainAuxilaries.Adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.ast.MyBills.MainAuxilaries.DModels.DModelBanner;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.IAdapterCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FeaturedAdsViewPagerAdapter extends PagerAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<DModelBanner> listData;
    View convertView;
    Fragment frg;
    DisplayMetrics metrics;
    private IAdapterCallback iAdapterCallback;

    public FeaturedAdsViewPagerAdapter(Context context, ArrayList<DModelBanner> listData, Fragment frg, final IAdapterCallback iAdapterCallback) {
        this.iAdapterCallback = iAdapterCallback;
        this.context = context;
        this.listData = listData;
        this.frg = frg;
        if (context != null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (frg.getActivity() != null) {
            metrics = new DisplayMetrics();
            frg.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        }


    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = inflater.inflate(R.layout.lay_item_market_feature_banner, container, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imvBanner = itemView.findViewById(R.id.lay_item_market_imv_banner);


        viewHolder.imvBanner.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels, (metrics.widthPixels * 2) / 3)); // for ratio 1200 x 800 banner


        if (listData.get(position).isFromApi() && listData.get(position).getFeatureBanner() != null && !listData.get(position).getFeatureBanner().equals("")) {
            Picasso.get().load(listData.get(position).getFeatureBanner()).placeholder(R.drawable.ic_baner_placeholder).into(viewHolder.imvBanner);
        } else {
            viewHolder.imvBanner.setImageDrawable(listData.get(position).uploadPresp);
        }

        viewHolder.imvBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


    public static class ViewHolder {
        private ImageView imvBanner;
    }
}

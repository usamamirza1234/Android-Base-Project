package com.ast.MyBills.MainAuxilaries.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bill;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.IAdapterCallback;

import java.util.ArrayList;


public class BillAnaylsisRcvAdapter extends RecyclerView.Adapter<BillAnaylsisRcvAdapter.ViewHolder> {

    private final ArrayList<DModel_Bill> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;


    public BillAnaylsisRcvAdapter(Context mContext, ArrayList<DModel_Bill> mData,
                                  IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;

        this.iAdapterCallback = iAdapterCallback;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_month_anaylsis, null);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.txv_payment.setText(mData.get(position).getPAYMENT());
        holder.txv_month.setText(mData.get(position).getMONTH());
        holder.txv_units.setText(mData.get(position).getUNITS());



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }




    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txv_payment, txv_month, txv_units;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_units = itemView.findViewById(R.id.lay_item_month_anay_txv_units);
            txv_month = itemView.findViewById(R.id.lay_item_month_anay_txv_month);
            txv_payment = itemView.findViewById(R.id.lay_item_month_anay_txv_payment);


        }

    }

}
